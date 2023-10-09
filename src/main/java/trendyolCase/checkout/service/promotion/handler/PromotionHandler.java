package trendyolCase.checkout.service.promotion.handler;

import lombok.*;
import trendyolCase.checkout.model.entity.cart.Cart;

@Getter
@Setter
@RequiredArgsConstructor
public abstract class PromotionHandler {

    private final int promotionId;
    public PromotionHandler nextHandler;

    public void applyPromotion(Cart cart) {
        double potentialDiscount = calculatePotentialDiscount(cart);

        if (potentialDiscount > cart.getTotalDiscount()) {
            cart.setTotalDiscount(potentialDiscount);
            cart.setAppliedPromotionId(getPromotionId());
        }

        if (getNextHandler() != null) {
            getNextHandler().applyPromotion(cart);
        }
    }

    protected abstract double calculatePotentialDiscount(Cart cart);

}