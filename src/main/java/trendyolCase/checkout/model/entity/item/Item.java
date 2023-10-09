package trendyolCase.checkout.model.entity.item;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import trendyolCase.checkout.exception.BusinessException;

import java.util.ArrayList;
import java.util.List;


@Data
@Component
public class Item {
    @Value("${item.max_item_count_vas_item}")
    private int MAX_ITEM_COUNT_VAS_ITEM;
    @Value("${item.max_quantity}")
    private int maxQuantity;
    Integer id;
    private int quantity;
    private int categoryId;
    private int sellerId;
    private double price;
    private String typeOfItem = getClass().toString();
    private List<VasItem> vasItems = new ArrayList<>();
    private int vasItemCount = 0;
    public void addVasItem(VasItem vasItem) {
        if (!(this instanceof DefaultItem)) {
            System.out.println("Vas item can only be added to default item");
            return;
        }
        if (vasItemCount > MAX_ITEM_COUNT_VAS_ITEM) {
            System.out.println("Cart limits exceeded");
            return;
        }
        vasItems.add(vasItem);
        vasItemCount++;
    }
    public boolean isItemQuantityExceedingLimits() {
        return quantity > maxQuantity;
    }

    public void setQuantity(int quantity) {
        if (quantity > maxQuantity) {
            throw new BusinessException("Item quantity is exceeding limits");
        }
        this.quantity = quantity;
    }
    public void updateQuantity(int itemQuantity) {
        this.setQuantity(this.getQuantity() + itemQuantity);
    }

    public double getItemTotalAmount(){
        return this.getPrice() * this.getQuantity();
    }
}
