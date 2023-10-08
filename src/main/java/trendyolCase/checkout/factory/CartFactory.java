package trendyolCase.checkout.factory;

import org.springframework.stereotype.Component;
import trendyolCase.checkout.model.entity.cart.Cart;

@Component
public class CartFactory {
    private Cart cart;

    public Cart getCart() {
        return this.cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }
}
