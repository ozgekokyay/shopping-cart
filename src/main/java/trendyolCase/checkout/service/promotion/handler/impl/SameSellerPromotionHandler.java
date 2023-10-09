package trendyolCase.checkout.service.promotion.handler.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import trendyolCase.checkout.model.entity.cart.Cart;
import trendyolCase.checkout.model.entity.item.Item;
import trendyolCase.checkout.service.promotion.handler.PromotionHandler;

@Component
public class SameSellerPromotionHandler extends PromotionHandler {

    private final double discountRate;

    public SameSellerPromotionHandler(@Value("${promotion.same_seller.id}") int promotionId,
                                      @Value("${promotion.same_seller.discount_rate}") double discountRate) {
        super(promotionId);
        this.discountRate = discountRate;
    }

    @Override
    protected double calculatePotentialDiscount(Cart cart) {
        if (areAllItemsFromSameSeller(cart)) {
            return cart.getTotalAmount() * discountRate;
        }
        return 0;
    }

    private boolean areAllItemsFromSameSeller(Cart cart) {
        return cart.getItems().stream()
                .map(Item::getSellerId)
                .distinct()
                .count() == 1;
    }
}