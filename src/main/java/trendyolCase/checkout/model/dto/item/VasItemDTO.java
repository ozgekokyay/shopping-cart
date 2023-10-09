package trendyolCase.checkout.model.dto.item;

import lombok.Data;

@Data
public class VasItemDTO {
    private Integer vasItemId;
    private Integer categoryId;
    private Integer sellerId;
    private Double price;
    private Integer quantity;
}