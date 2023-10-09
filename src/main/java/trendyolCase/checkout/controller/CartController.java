package trendyolCase.checkout.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import trendyolCase.checkout.model.dto.cart.CartDisplayDTO;
import trendyolCase.checkout.model.request.AddItemRequest;
import trendyolCase.checkout.model.request.AddVasItemRequest;
import trendyolCase.checkout.model.request.RemoveItemRequest;
import trendyolCase.checkout.model.entity.cart.Cart;
import trendyolCase.checkout.model.response.CartResponse;
import trendyolCase.checkout.model.response.MessageResponse;
import trendyolCase.checkout.service.CartService;


import java.util.UUID;

@RestController
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;
    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("items/add")
    public ResponseEntity<MessageResponse> addItemToCart(@RequestBody AddItemRequest addItemRequest) throws Exception{

        cartService.addItemToCart(addItemRequest);

        MessageResponse response = new MessageResponse(true, "Item with id " +
                addItemRequest.getItemId() + " is added to cart.");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/items/add/vas-item")
    public ResponseEntity<MessageResponse> addVasItemToItem(@RequestBody AddVasItemRequest addVasItemRequest){
        cartService.addVasItemToItem(addVasItemRequest);
        MessageResponse response = new MessageResponse(true, "VAS item with id " + addVasItemRequest.getVasItemId() + " is added to item with id " + addVasItemRequest.getItemId());
            return ResponseEntity.ok(response);
    }


    //TODO null logic
    //TODO functional programming
    @GetMapping("/reset")
    public ResponseEntity<MessageResponse> resetCart() {
        //TODO empty given cart and persist
        cartService.resetCart();
        MessageResponse response = new MessageResponse(true, "Cart is reset successfully.");
        return ResponseEntity.ok(response);

    }

    @GetMapping("/display")
    public ResponseEntity<CartResponse> displayCart() {
        CartDisplayDTO cartDTO = cartService.displayCart();
        CartResponse response = new CartResponse(true, cartDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/remove")
    public ResponseEntity<MessageResponse> removeItemFromCart(@RequestBody RemoveItemRequest removeItemRequest) {
        cartService.removeItemFromCart(removeItemRequest);
        MessageResponse response = new MessageResponse(true, "Item with id " + removeItemRequest.getItemId() + " is removed from cart.");
        return ResponseEntity.ok(response);
    }



}