package trendyolCase.checkout.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import trendyolCase.checkout.factory.CartFactory;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class CartServiceTest {
    @Mock
    private ItemService itemServiceUnderTest;
    @Mock
    private CartFactory cartFactoryUnderTest;

    @Test
    void addItemToCart() {
    }

    @Test
    void resetCart() {
    }

    @Test
    void addVasItemToItem() {
    }

    @Test
    void displayCart() {
    }

    @Test
    void removeItemFromCart() {
    }
}