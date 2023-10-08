package trendyolCase.checkout.service;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
import trendyolCase.checkout.service.ItemService;

@Service
public class CartService {
    public ItemService itemService;
    private final CartFactory cartFactory;

    @Autowired
    public CartService(CartFactory cartFactory, ItemService itemService) {
        this.cartFactory = cartFactory;
        this.itemService = itemService;
    }

    public Cart addItemToCart(AddItemRequestDTO itemRequest) {
        Item item = itemService.createItemFromRequest(itemRequest);
        if(item.isItemQuantityExceedingLimits()){
            System.out.println("Item quantity is exceeding limits");
            return null;
        }
        Cart cart = cartFactory.getCart();

        if (cart == null) {
            cart = createCartForItem(item);
        }
        // Check if cart type is same as item type, refuse to add if not
        else if (!isCartCompatible(cart, item)) {
            return null;
        }
        cart.addItem(item);
        return cart;
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
}
