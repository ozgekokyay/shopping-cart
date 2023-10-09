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


    public void addItemToCart(AddItemRequest itemRequest) throws BusinessException {
        if (itemRequest.getQuantity() < 1) {
            throw new BusinessException("Item quantity to be added cannot be less than 1");
        }
        Item item = itemService.createItemFromRequest(itemRequest);
        if (!cartFactory.isCartCreated()) {
            createCartForItem(item);
        }
        Cart cart = cartFactory.getCart();

        checkCartConditions(cart, item);
        cart.addItem(item);
    }

    private void isCartCompatible(Cart cart, Item item) {
        if (cart instanceof DigitalItemCart && item instanceof DefaultItem) {
            throw new BusinessException("Default item cannot be added to a cart with digital items");
        } else if (cart instanceof DefaultItemCart && item instanceof DigitalItem) {
            throw new BusinessException("Digital item cannot be added to a cart with default items");
        }
    }

    private void createCartForItem(Item item) {
        Cart cart;
        if (item instanceof DigitalItem) {
            cart = new DigitalItemCart();
        } else if (item instanceof DefaultItem) {
            cart = new DefaultItemCart();
        } else {
            throw new BusinessException("Item type is not supported");
        }
        cartFactory.setCart(cart);
        cartFactory.setCartCreated(true);
    }
    public void resetCart() {
        cartFactory.setCartCreated(false);
    }

    public void addVasItemToItem(AddVasItemRequest addVasItemRequest) {
        if (addVasItemRequest.getQuantity() < 1) {
            throw new BusinessException("VAS item quantity to be added cannot be less than 1");
        }
        Cart cart = cartFactory.getCart();

        if(!(cart instanceof DefaultItemCart)){
            throw new BusinessException("VAS item can only be added to a cart with default items");
        }
        itemService.addVasItemToItem(cart, addVasItemRequest);

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

    public void isCartAmountExceedingLimits(Cart cart, Item item) {
        if(cart.getTotalAmount() + item.getItemTotalAmount() > MAX_AMOUNT) {
            throw new BusinessException("Adding this item would exceed the cart's max amount " + MAX_AMOUNT);
        }
    }
    public void isCartItemNumberExceedingLimits(Cart cart, Item item) {
        if (cart.getItemCount() + item.getQuantity() > MAX_ITEM_NUMBER) {
            throw new BusinessException("Adding this item would exceed the cart's max item number " + MAX_ITEM_NUMBER);
        }
    }

    public void isItemQuantityExceedingLimits(Item item) {
        if( item.getQuantity() > item.getMaxQuantity()){
            throw new BusinessException("At most " + item.getMaxQuantity() + " items can be added to cart with same id");
        }
    }

    public void isCartUniqueItemNumberExtendingLimits(Cart cart){
        if (cart.getItems().size() > MAX_UNIQUE_ITEM_NUMBER){
            throw new BusinessException("Cart can only contain 10 unique items");
        }
    }
    public void checkCartConditions(Cart cart, Item item) throws BusinessException {
        isCartCompatible(cart, item);

        isCartAmountExceedingLimits(cart, item);

        isCartUniqueItemNumberExtendingLimits(cart);

        isCartItemNumberExceedingLimits(cart, item);

        isItemQuantityExceedingLimits(item);
    }
}
