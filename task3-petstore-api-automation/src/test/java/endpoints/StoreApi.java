package endpoints;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import config.Config;

import java.util.Map;

public class StoreApi {
    private static RequestSpecification baseSpec() {
        return RestAssured.given()
                .baseUri(Config.BASE_URL)
                .contentType("application/json")
                .log().ifValidationFails(); // Logs only if assertions fail
    }

    @Step("Place an order with payload: {payload}")
    public static Response placeOrder(Map<String, Object> payload) {
        return baseSpec()
                .body(payload)
                .when()
                .post("/store/order");
    }

    @Step("Get order by ID: {id}")
    public static Response getOrderById(long id) {
        return baseSpec()
                .pathParam("orderId", id)
                .when()
                .get("/store/order/{orderId}");
    }

    @Step("Delete order by ID: {id}")
    public static Response deleteOrder(long id) {
        return baseSpec()
                .pathParam("orderId", id)
                .when()
                .delete("/store/order/{orderId}");
    }

    @Step("Get store inventory")
    public static Response getInventory() {
        return baseSpec()
                .when()
                .get("/store/inventory");
    }
}
