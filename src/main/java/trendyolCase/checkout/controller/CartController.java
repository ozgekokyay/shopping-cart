package trendyolCase.checkout.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import trendyolCase.checkout.model.dto.item.AddItemRequestDTO;
import trendyolCase.checkout.model.dto.item.AddVasItemRequestDTO;
import trendyolCase.checkout.model.dto.item.RemoveItemRequest;
import trendyolCase.checkout.model.entity.cart.Cart;
import trendyolCase.checkout.service.CartService;

import java.util.UUID;

@RestController
@RequestMapping("/cart")
public class CartController {
    private CartService cartService;
    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("items/add")
    public ResponseEntity<Cart> addItemToCart(@RequestBody AddItemRequestDTO addItemRequest) {
        Cart updatedCart = cartService.addItemToCart(addItemRequest);

        if (updatedCart != null) {
            // Return a ResponseEntity with the updated cart and HTTP status OK (200)
            return ResponseEntity.ok(updatedCart);
        } else {
            // Return a ResponseEntity with HTTP status BAD REQUEST (400) if the operation fails
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/items/add/vas-item")
    public ResponseEntity<Cart> addVasItemToItem(@RequestBody AddVasItemRequestDTO addVasItemRequestDTO){
        Cart updatedCart = cartService.addVasItemToItem(addVasItemRequestDTO);
        if (updatedCart != null) {
            // Return a ResponseEntity with the updated cart and HTTP status OK (200)
            return ResponseEntity.ok(updatedCart);
        } else {
            // Return a ResponseEntity with HTTP status BAD REQUEST (400) if the operation fails
            return ResponseEntity.badRequest().build();
        }
    }
    //TODO null logic
    //TODO functional programming
    @GetMapping("/reset")
    public ResponseEntity<Cart> resetCart() {
        //TODO empty given cart and persist
        if (cartService.resetCart())
            return ResponseEntity.ok().build();
        else
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @GetMapping("/display")
    public ResponseEntity<Cart> displayCart() {
        Cart cart = cartService.displayCart();
        if (cart != null) {
            return ResponseEntity.ok(cart);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/remove")
    public ResponseEntity<Cart> removeItemFromCart(@RequestBody RemoveItemRequest removeItemRequest) {
        Cart updatedCart = cartService.removeItemFromCart(removeItemRequest);
        if (updatedCart != null) {
            return ResponseEntity.ok(updatedCart);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }





}