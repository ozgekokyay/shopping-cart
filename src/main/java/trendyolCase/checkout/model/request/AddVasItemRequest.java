package trendyolCase.checkout.model.request;

import lombok.Data;

@Data
public class AddVasItemRequest {
    Integer itemId;
    Integer vasItemId;
    Integer categoryId;
    Integer sellerId;
    Double price;
    Integer quantity;
}
