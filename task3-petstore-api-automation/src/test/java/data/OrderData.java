package data;

import java.util.HashMap;
import java.util.Map;

public class OrderData {
    public static Map<String, Object> getSampleOrder(long id) {
        Map<String, Object> order = new HashMap<>();
        order.put("id", id);
        order.put("petId", 100);
        order.put("quantity", 1);
        order.put("shipDate", "2025-04-13T10:00:00.000Z");
        order.put("status", "placed");
        order.put("complete", true);
        return order;
    }
}
