package trendyolCase.checkout.factory;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import trendyolCase.checkout.model.entity.cart.Cart;
@Getter
@Setter
@Component
public class CartFactory {
    private Cart cart;
    private boolean isCartCreated = false;

}
