package trendyolCase.checkout.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import trendyolCase.checkout.model.dto.item.AddItemRequestDTO;
import trendyolCase.checkout.model.entity.cart.Cart;
import trendyolCase.checkout.model.entity.item.Item;
import trendyolCase.checkout.service.CartService;
import trendyolCase.checkout.service.ItemService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/item")
public class ItemController {
    @Autowired
    ItemService itemService;





}
