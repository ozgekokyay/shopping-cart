package trendyolCase.checkout.model.dto.item;

import lombok.Data;

// "itemId":int, "categoryId":int, "sellerId":int, "price":double, "quantity":int, "itemType":string
@Data
public class AddItemRequestDTO {
    Integer itemId;
    Integer categoryId;
    Integer sellerId;
    Double price;
    Integer quantity;

}
