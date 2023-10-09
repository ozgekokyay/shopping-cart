package trendyolCase.checkout.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import trendyolCase.checkout.model.request.AddItemRequest;
import trendyolCase.checkout.model.entity.cart.Cart;
import trendyolCase.checkout.service.CartService;

import java.util.UUID;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CartControllerTest {
    @Mock
    CartService mockCartService;
    @InjectMocks
    CartController cartControllerUnderTest;
/*

    @Test
    void addItemToCart()throws Exception {
        AddItemRequest addItemRequest = new AddItemRequest();
        addItemRequest.setItemId(123);
        addItemRequest.setCategoryId(456);
        addItemRequest.setSellerId(789);
        addItemRequest.setPrice(19.99);
        addItemRequest.setQuantity(2);

        // Create an instance of Cart representing the updated cart
        Cart updatedCart = new Cart();
        updatedCart.setId(UUID.randomUUID());
        // Assume the cartService returns the updated cart upon success
        when(mockCartService.addItemToCart(addItemRequest)).thenReturn(updatedCart);
        assertEquals(updatedCart, cartControllerUnderTest.addItemToCart(addItemRequest).getBody());
    }*/

    @Test
    void addItemToCart_QuantityExceedingLimits(){

    }


    @Test
    void addVasItemToItem() {
    }

    @Test
    void resetCart() {
    }

    @Test
    void displayCart() {
    }

    @Test
    void removeItemFromCart() {
    }


}