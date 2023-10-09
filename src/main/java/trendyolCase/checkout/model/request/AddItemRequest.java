package trendyolCase.checkout.model.request;

import lombok.Data;

// "itemId":int, "categoryId":int, "sellerId":int, "price":double, "quantity":int, "itemType":string
@Data
public class AddItemRequest {
    Integer itemId;
    Integer categoryId;
    Integer sellerId;
    Double price;
    Integer quantity;

}
