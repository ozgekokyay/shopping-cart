package trendyolCase.checkout.model.entity.item;

import java.util.ArrayList;
import java.util.List;

// 
public class DefaultItem extends Item {
    private static final int MAX_ITEM_COUNT_CART = 10;
    private static final int MAX_ITEM_COUNT_VAS_ITEM = 3;
    private List<VasItem> vasItems = new ArrayList<>();
    private int vasItemCount = 0;
    // TODO
    public void addVasItem(VasItem vasItem) {
        if (vasItemCount > MAX_ITEM_COUNT_VAS_ITEM) {
            System.out.println("Cart limits exceeded");
            return;
        }
        vasItems.add(vasItem);
        vasItemCount++;
    }


}
