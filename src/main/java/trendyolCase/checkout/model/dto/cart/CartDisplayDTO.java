package trendyolCase.checkout.model.dto.cart;

import lombok.Data;
import trendyolCase.checkout.model.dto.item.ItemDTO;

@Data
public class CartDisplayDTO {

    private Double totalPrice;
    private Integer appliedPromotionId;
    private Double totalDiscount;
    private ItemDTO[] items;

}