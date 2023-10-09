package trendyolCase.checkout.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import trendyolCase.checkout.model.dto.cart.CartDisplayDTO;

@Getter
@Setter
@AllArgsConstructor
public class CartResponse {

    private boolean result;
    private CartDisplayDTO cart;

}