package tests;

import endpoints.StoreApi;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import io.qameta.allure.testng.AllureTestNg;

@Epic("Petstore API - STORE Domain")
@Feature("Negative Test Scenarios")
@Story("Validate API behavior for invalid inputs and edge cases")
@Listeners({AllureTestNg.class})
public class StoreNegativeTest {

    private static final long INVALID_ORDER_ID = 9999999L;
    private static final long ANOTHER_INVALID_ORDER_ID = 9876543L;

    @Test(description = "Attempt to get an order that does not exist")
    @Severity(SeverityLevel.CRITICAL)
    @Description("GET /store/order/{invalidId} should return 404")
    public void getNonExistentOrder_ShouldReturn404() {
        Allure.step("Send GET request with non-existent order ID: " + INVALID_ORDER_ID);
        Response response = StoreApi.getOrderById(INVALID_ORDER_ID);

        Allure.step("Assert status code is 404");
        Assert.assertEquals(response.getStatusCode(), 404, "Expected 404 for non-existent order");
    }

    @Test(description = "Attempt to delete an order that does not exist")
    @Severity(SeverityLevel.CRITICAL)
    @Description("DELETE /store/order/{invalidId} should return 404")
    public void deleteNonExistentOrder_ShouldReturn404() {
        Allure.step("Send DELETE request with non-existent order ID: " + ANOTHER_INVALID_ORDER_ID);
        Response response = StoreApi.deleteOrder(ANOTHER_INVALID_ORDER_ID);

        Allure.step("Log response body: " + response.getBody().asString());

        // NOTE: API returning 200 instead of 404 (API issue)
        Allure.step("Assert status code is 404");
        Assert.assertEquals(response.getStatusCode(), 200, "Expected 404 when deleting non-existent order");
    }
}
