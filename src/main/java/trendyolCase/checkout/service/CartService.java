package trendyolCase.checkout.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import trendyolCase.checkout.exceptions.CartTotalExceededException;
import trendyolCase.checkout.factory.CartFactory;
import trendyolCase.checkout.model.dto.item.AddItemRequestDTO;
import trendyolCase.checkout.model.dto.item.AddVasItemRequestDTO;
import trendyolCase.checkout.model.dto.item.RemoveItemRequest;
import trendyolCase.checkout.model.entity.cart.Cart;

import trendyolCase.checkout.model.entity.cart.DefaultItemCart;
import trendyolCase.checkout.model.entity.cart.DigitalItemCart;
import trendyolCase.checkout.model.entity.item.DefaultItem;
import trendyolCase.checkout.model.entity.item.DigitalItem;
import trendyolCase.checkout.model.entity.item.Item;

@Service
public class CartService {
    public ItemService itemService;
    private final CartFactory cartFactory;
    private static final int MAX_ITEM_NUMBER = 30;
    private static final int MAX_UNIQUE_ITEM_NUMBER = 10;
    private static final int MAX_AMOUNT = 500000;

    @Autowired
    public CartService(CartFactory cartFactory, ItemService itemService) {
        this.cartFactory = cartFactory;
        this.itemService = itemService;
    }

    public Cart addItemToCart(AddItemRequestDTO itemRequest) throws CartTotalExceededException {
        Item item = itemService.createItemFromRequest(itemRequest);
        Cart cart = cartFactory.getCart();
        if (cart == null) {
            cart = createCartForItem(item);
        }
        if (checkCartConditions(cart, item)) {
            cart.addItem(item);
            return cart;
        } else {
            return null;
        }
    }

    private boolean isCartCompatible(Cart cart, Item item) {
        if (cart instanceof DigitalItemCart && item instanceof DefaultItem) {
            System.out.println("Default item cannot be added to a cart with digital items");
            return false;
        } else if (cart instanceof DefaultItemCart && item instanceof DigitalItem) {
            System.out.println("Digital item cannot be added to a cart with default items");
            return false;
        }
        return true;
    }

    private Cart createCartForItem(Item item) {
        Cart cart;
        if (item instanceof DigitalItem) {
            cart = new DigitalItemCart();
        } else if (item instanceof DefaultItem) {
            cart = new DefaultItemCart();
        } else {
            System.out.println("Item type is not valid");
            return null;
        }
        cartFactory.setCart(cart);
        return cartFactory.getCart();
    }
    public boolean resetCart() {
        cartFactory.setCart(null);
        return true;
    }
    //TODO burada defaulItemCart ve defaultItem olarak özelleştirdim buna gerek var mı factory e eklenmeli mi
    public Cart addVasItemToItem(AddVasItemRequestDTO addVasItemRequest) {
        Cart cart = cartFactory.getCart();
        Item vasItem = itemService.createVasItemFromRequest((DefaultItemCart) cart, addVasItemRequest);
        if(vasItem == null){
            return null;
        }
        cart.addItem(vasItem);
        return cart;
    }

    public Cart displayCart() {
        return cartFactory.getCart();
    }

    public Cart removeItemFromCart(RemoveItemRequest removeItemRequest) {
        Cart cart = cartFactory.getCart();
        cart.removeItem(removeItemRequest.getItemId());
        return cart;
    }

    public boolean isCartAmountExtendingLimits(Cart cart, Item item) {
        return cart.getTotalAmount() + item.getItemTotalAmount() > MAX_AMOUNT;
    }
    public boolean isCartItemNumberExtendingLimits(Cart cart, Item item) {
        return cart.getItemCount() + item.getQuantity() > MAX_ITEM_NUMBER;
    }
    ////TODO
    public boolean isItemQuantityExceedingLimits(Item item) {
        return item.getQuantity() > item.getMaxQuantity();
    }
    public boolean isCartUniqueItemNumberExtendingLimits(Cart cart){
        return cart.getItems().size() > MAX_UNIQUE_ITEM_NUMBER;
    }
    public boolean checkCartConditions(Cart cart, Item item) throws CartTotalExceededException {
        if (isCartAmountExtendingLimits(cart, item)) {
            throw new CartTotalExceededException("Adding this item would exceed the cart's total limit.");
        }
        if (isCartUniqueItemNumberExtendingLimits(cart)){
            throw new CartTotalExceededException("Cart can only contain 10 unique items");
        }
        if (isCartItemNumberExtendingLimits(cart, item)) {
            throw new CartTotalExceededException("Cart can only contain 30 items");
        }
        if (isItemQuantityExceedingLimits( item)) {
            throw new CartTotalExceededException("Item quantity is exceeding limits");
        }
        if (!isCartCompatible(cart, item)) {
            throw new CartTotalExceededException("Item type is not compatible with cart type");
        }
        return true;
    }
}
