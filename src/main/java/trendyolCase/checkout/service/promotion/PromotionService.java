package trendyolCase.checkout.service.promotion;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import trendyolCase.checkout.model.entity.cart.Cart;
import trendyolCase.checkout.service.promotion.handler.PromotionHandler;


@Service
@RequiredArgsConstructor
public class PromotionService {

    private final PromotionHandler categoryPromotionHandler;
    private final PromotionHandler sameSellerPromotionHandler;
    private final PromotionHandler totalPricePromotionHandler;

    public void applyPromotions(Cart cart) {
        getPromotionHandlerChain().applyPromotion(cart);
    }

    private PromotionHandler getPromotionHandlerChain() {
        categoryPromotionHandler.setNextHandler(sameSellerPromotionHandler);
        sameSellerPromotionHandler.setNextHandler(totalPricePromotionHandler);
        return categoryPromotionHandler;
    }
}