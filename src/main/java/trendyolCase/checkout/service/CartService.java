package trendyolCase.checkout.service;
import lombok.RequiredArgsConstructor;
import trendyolCase.checkout.exception.BusinessException;
import trendyolCase.checkout.model.dto.cart.CartDisplayDTO;
import trendyolCase.checkout.model.mapper.CartMapper;
import trendyolCase.checkout.service.promotion.PromotionService;
import org.springframework.stereotype.Service;

import trendyolCase.checkout.factory.CartFactory;
import trendyolCase.checkout.model.request.AddItemRequest;
import trendyolCase.checkout.model.request.AddVasItemRequest;
import trendyolCase.checkout.model.request.RemoveItemRequest;
import trendyolCase.checkout.model.entity.cart.Cart;
import trendyolCase.checkout.model.entity.cart.DefaultItemCart;
import trendyolCase.checkout.model.entity.cart.DigitalItemCart;
import trendyolCase.checkout.model.entity.item.DefaultItem;
import trendyolCase.checkout.model.entity.item.DigitalItem;
import trendyolCase.checkout.model.entity.item.Item;

@Service
@RequiredArgsConstructor
public class CartService {
    private final ItemService itemService;
    private final CartFactory cartFactory;
    private final PromotionService promotionService;

    private static final int MAX_ITEM_NUMBER = 30;
    private static final int MAX_UNIQUE_ITEM_NUMBER = 10;
    private static final int MAX_AMOUNT = 500000;


    public Cart addItemToCart(AddItemRequest itemRequest) throws BusinessException {
        Item item = itemService.createItemFromRequest(itemRequest);
        if (!cartFactory.isCartCreated()) {
            createCartForItem(item);
        }
        Cart cart = cartFactory.getCart();


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

    private void createCartForItem(Item item) {
        Cart cart;
        if (item instanceof DigitalItem) {
            cart = new DigitalItemCart();
        } else if (item instanceof DefaultItem) {
            cart = new DefaultItemCart();
        } else {
            System.out.println("Item type is not valid");
            return ;
        }
        cartFactory.setCart(cart);
        cartFactory.setCartCreated(true);
    }
    public void resetCart() {
        cartFactory.setCartCreated(false);
    }

    //TODO burada defaulItemCart ve defaultItem olarak özelleştirdim buna gerek var mı factory e eklenmeli mi
    public Cart addVasItemToItem(AddVasItemRequest addVasItemRequest) {
        Cart cart = cartFactory.getCart();

        if(!(cart instanceof DefaultItemCart)){
            throw new BusinessException("VAS item can only be added to a cart with default items");
        }

        Item vasItem = itemService.createVasItemFromRequest(cart, addVasItemRequest);
        if(vasItem == null){
            return null;
        }
        cart.addItem(vasItem);
        return cart;
    }

    public CartDisplayDTO displayCart() {
        Cart cart;

        if(!cartFactory.isCartCreated()){
            cart = new Cart();
        } else {
            cart = cartFactory.getCart();
            promotionService.applyPromotions(cartFactory.getCart());
        }

        return CartMapper.MAPPER.mapToCartDisplayDTO(cart);
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
    public boolean checkCartConditions(Cart cart, Item item) throws BusinessException {
        if (isCartAmountExtendingLimits(cart, item)) {
            throw new BusinessException("Adding this item would exceed the cart's total limit.");
        }
        if (isCartUniqueItemNumberExtendingLimits(cart)){
            throw new BusinessException("Cart can only contain 10 unique items");
        }
        if (isCartItemNumberExtendingLimits(cart, item)) {
            throw new BusinessException("Cart can only contain 30 items");
        }
        if (isItemQuantityExceedingLimits( item)) {
            throw new BusinessException("Item quantity is exceeding limits");
        }
        if (!isCartCompatible(cart, item)) {
            throw new BusinessException("Item type is not compatible with cart type");
        }
        return true;
    }
}
