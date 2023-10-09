package trendyolCase.checkout.service.promotion.handler.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import trendyolCase.checkout.model.entity.cart.Cart;
import trendyolCase.checkout.service.promotion.handler.PromotionHandler;

@Component
public class TotalPricePromotionHandler extends PromotionHandler {

    private final double[] thresholds;
    private final double[] discounts;


    public TotalPricePromotionHandler(@Value("${promotion.total_price.id}") int promotionId,
                                      @Value("${promotion.total_price.thresholds}") double[] priceThresholds,
                                      @Value("${promotion.total_price.discounts}") double[] discounts) {
        super(promotionId);
        this.thresholds = priceThresholds;
        this.discounts = discounts;
    }

    @Override
    protected double calculatePotentialDiscount(Cart cart) {
        double promotion = 0;
        for (int i = 0; i < thresholds.length; i++) {
            if (cart.getTotalAmount() >= thresholds[i]) {
                // Promotion can't be more than the total amount
                promotion = Math.min(discounts[i], cart.getTotalAmount());
            } else{
                break;
            }
        }
        return promotion;
    }

}