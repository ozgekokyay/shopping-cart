package trendyolCase.checkout.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import trendyolCase.checkout.service.ItemService;

@RestController
@RequestMapping("/item")
public class ItemController {
    @Autowired
    ItemService itemService;





}
