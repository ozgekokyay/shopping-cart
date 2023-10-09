package trendyolCase.checkout.service;

import org.springframework.stereotype.Service;
import trendyolCase.checkout.exception.BusinessException;
import trendyolCase.checkout.model.request.AddItemRequest;
import trendyolCase.checkout.model.request.AddVasItemRequest;
import trendyolCase.checkout.model.entity.cart.DefaultItemCart;
import trendyolCase.checkout.model.entity.item.DefaultItem;
import trendyolCase.checkout.model.entity.item.DigitalItem;
import trendyolCase.checkout.model.entity.item.Item;
import trendyolCase.checkout.model.entity.item.VasItem;
import trendyolCase.checkout.model.entity.cart.Cart;

@Service
public class ItemService {
    public Item createItemFromRequest(AddItemRequest addItemRequest) {
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

    public void addVasItemToItem(Cart defaultItemCart, AddVasItemRequest addVasItemRequest) {
        DefaultItem defaultItem = (DefaultItem) defaultItemCart.getItemByIdFromCart(addVasItemRequest.getItemId());
        if (defaultItem == null) {
            throw new BusinessException("Item not found in cart");
        } else if (defaultItem.getCategoryId() == 1001 || defaultItem.getCategoryId() == 5003) {
            VasItem existingVasItem = defaultItem.getVasItems()
                    .stream()
                    .filter(vasItem -> vasItem.getId().equals(addVasItemRequest.getVasItemId()))
                    .findAny()
                    .orElse(new VasItem());

            if (existingVasItem.getId() == null) {
                VasItem newVasItem = new VasItem();
                newVasItem.setId(addVasItemRequest.getVasItemId());
                newVasItem.setCategoryId(addVasItemRequest.getCategoryId());
                newVasItem.setSellerId(addVasItemRequest.getSellerId());
                newVasItem.setPrice(addVasItemRequest.getPrice());
                newVasItem.setQuantity(addVasItemRequest.getQuantity());
                defaultItem.addVasItem((newVasItem));
            } else {
                existingVasItem.setQuantity(existingVasItem.getQuantity() + addVasItemRequest.getQuantity());
            }

        }
    }


}
