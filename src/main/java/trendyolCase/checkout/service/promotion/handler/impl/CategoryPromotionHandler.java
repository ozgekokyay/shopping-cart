package trendyolCase.checkout.service.promotion.handler.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import trendyolCase.checkout.model.entity.cart.Cart;
import trendyolCase.checkout.service.promotion.handler.PromotionHandler;

import java.util.HashSet;

@Component
public class CategoryPromotionHandler extends PromotionHandler {

    private final HashSet<Integer> targetCategoryIds;
    private final double discountRateForCategory;

    public CategoryPromotionHandler(@Value("${promotion.category.id}") int promotionId,
                                    @Value("${promotion.category.target_category_ids}") HashSet<Integer> targetCategoryIds,
                                    @Value("${promotion.category.discount_rate}") double discountRateForCategory) {
        super(promotionId);
        this.targetCategoryIds = targetCategoryIds;
        this.discountRateForCategory = discountRateForCategory;
    }


    @Override
    // Calculate the total promotion by summing the individual promotions for items belonging to the target categories
    protected double calculatePotentialDiscount(Cart cart) {
        return cart.getItems().stream()
                .filter(item -> targetCategoryIds.contains(item.getCategoryId()))
                .mapToDouble(item -> item.getPrice() * item.getQuantity() * discountRateForCategory)
                .sum();
    }

}
