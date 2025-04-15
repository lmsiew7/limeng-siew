package tests;

import data.OrderData;
import endpoints.StoreApi;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import io.qameta.allure.testng.AllureTestNg;
import org.testng.annotations.BeforeMethod;

import java.util.Map;
import java.util.Random;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@Epic("Petstore API - STORE Domain")
@Feature("Order Management and Inventory")
@Story("Verify end-to-end order lifecycle and inventory tracking")
@Listeners({AllureTestNg.class})
public class StoreCrudTest {

    Map<String, Object> order;
    private long orderId;

    @BeforeMethod
    public void setupOrderData() {
        orderId = System.currentTimeMillis() + new Random().nextInt(1000); // Unique ID
        order = OrderData.getSampleOrder(orderId);
    }

    @Test(priority = 1, description = "Place a new order")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Create a new order and validate schema, ID and status code")
    public void placeOrderTest() {
        Allure.step("Send POST request to /store/order");
        Response response = StoreApi.placeOrder(order);

        Allure.step("Validate status code is 200");
        Assert.assertEquals(response.getStatusCode(), 200, "Failed to place order");

        Allure.step("Validate response matches order schema");
        response.then().assertThat()
                .body(matchesJsonSchemaInClasspath("schemas/order-schema.json"));

        Allure.step("Assert returned ID matches expected order ID");
        long returnedId = response.jsonPath().getLong("id");
        Assert.assertEquals(returnedId, orderId, "Returned order ID mismatch");
    }

    @Test(priority = 2, description = "Get an order by ID")
    @Severity(SeverityLevel.NORMAL)
    @Description("Fetch the order and validate structure and content")
    public void getOrderByIdTest() {
        StoreApi.placeOrder(order); // Ensure the order exists

        Allure.step("Send GET request to /store/order/{id}");
        Response response = StoreApi.getOrderById(orderId);

        Allure.step("Validate status code is 200");
        Assert.assertEquals(response.getStatusCode(), 200, "Order not found");

        Allure.step("Validate response matches order schema");
        response.then().assertThat()
                .body(matchesJsonSchemaInClasspath("schemas/order-schema.json"));

        Allure.step("Assert order ID matches");
        long returnedId = response.jsonPath().getLong("id");
        Assert.assertEquals(returnedId, orderId, "Fetched order ID does not match");
    }

    @Test(priority = 3, description = "Delete an order by ID")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Delete the order and ensure successful deletion")
    public void deleteOrderTest() {
        StoreApi.placeOrder(order); // Ensure the order exists

        Allure.step("Send DELETE request to /store/order/{id}");
        Response response = StoreApi.deleteOrder(orderId);

        Allure.step("Validate status code is 200");
        Assert.assertEquals(response.getStatusCode(), 200, "Order deletion failed");

        Allure.step("Validate order is deleted");
        Response responseAfterDeletion = StoreApi.getOrderById(orderId);

        Allure.step("Validate status code is 404");
        Assert.assertEquals(responseAfterDeletion.getStatusCode(), 404, "Order deletion failed");
    }

    @Test(priority = 4, description = "Check store inventory")
    @Severity(SeverityLevel.MINOR)
    @Description("Validate inventory schema and required keys")
    public void inventoryTest() {
        Allure.step("Send GET request to /store/inventory");
        Response response = StoreApi.getInventory();

        Allure.step("Validate status code is 200");
        Assert.assertEquals(response.getStatusCode(), 200, "Inventory API unreachable");

        Allure.step("Validate response matches inventory schema");
        response.then().assertThat()
                .body(matchesJsonSchemaInClasspath("schemas/inventory-schema.json"));
    }
}
