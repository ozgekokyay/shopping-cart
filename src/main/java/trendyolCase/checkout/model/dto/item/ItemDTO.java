package trendyolCase.checkout.model.dto.item;

import lombok.Data;

@Data
public class ItemDTO {
    private Integer itemId;
    private Integer categoryId;
    private Integer sellerId;
    private Double price;
    private Integer quantity;
    private VasItemDTO[] vasItems;
}