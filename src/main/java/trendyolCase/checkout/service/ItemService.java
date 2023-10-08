package trendyolCase.checkout.service;

import org.springframework.stereotype.Service;
import trendyolCase.checkout.model.dto.item.AddItemRequestDTO;
import trendyolCase.checkout.model.dto.item.AddVasItemRequestDTO;
import trendyolCase.checkout.model.entity.cart.Cart;
import trendyolCase.checkout.model.entity.cart.DefaultItemCart;
import trendyolCase.checkout.model.entity.item.DefaultItem;
import trendyolCase.checkout.model.entity.item.DigitalItem;
import trendyolCase.checkout.model.entity.item.Item;
import trendyolCase.checkout.model.entity.item.VasItem;

@Service
public class ItemService {
    public Item createItemFromRequest(AddItemRequestDTO addItemRequest) {
        Item item;
        //TODO: no hardcoded values
        if (addItemRequest.getCategoryId() == 7889) {
            item = new DigitalItem();
        }
        else{
            item = new DefaultItem();
        }
        item.setId(addItemRequest.getItemId());
        item.setCategoryId(addItemRequest.getCategoryId());
        item.setSellerId(addItemRequest.getSellerId());
        item.setPrice(addItemRequest.getPrice());
        item.setQuantity(addItemRequest.getQuantity());
        return item;
    }

    public VasItem createVasItemFromRequest(DefaultItemCart defaultItemCart, AddVasItemRequestDTO addVasItemRequest) {
        VasItem vasItem = new VasItem();
        DefaultItem defaultItem = (DefaultItem) defaultItemCart.getItemByIdFromCart(addVasItemRequest.getItemId());
        if(defaultItem == null){
            System.out.println("Item not found in cart");
            return null;
        }
        else if(defaultItem.getCategoryId() == 1001 || defaultItem.getCategoryId() == 5003){
            vasItem.setId(addVasItemRequest.getItemId());
            vasItem.setCategoryId(addVasItemRequest.getCategoryId());
            vasItem.setSellerId(addVasItemRequest.getSellerId());
            vasItem.setPrice(addVasItemRequest.getPrice());
            vasItem.setQuantity(addVasItemRequest.getQuantity());
            defaultItem.addVasItem((vasItem));

            return vasItem;
        }
        else{
            System.out.println("VasItem cannot be added to this cart");
            return null;

        }        //TODO: 1. o item sepette mi sepette id ara, 2. o itemin category id si 1001 mi 5003 mü, 3 vasItemin category id si 3242 mi

    }


}
