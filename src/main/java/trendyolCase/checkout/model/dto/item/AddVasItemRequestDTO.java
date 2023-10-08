package trendyolCase.checkout.model.dto.item;

import lombok.Data;

@Data
public class AddVasItemRequestDTO {
    Integer itemId;
    Integer vasItemId;
    Integer categoryId;
    Integer sellerId;
    Double price;
    Integer quantity;
}
