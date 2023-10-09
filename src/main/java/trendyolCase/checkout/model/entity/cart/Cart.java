package trendyolCase.checkout.model.entity.cart;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import trendyolCase.checkout.model.entity.item.Item;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/*Cart
Bütün nesneleri içinde bulunduran bir nesnedir. Bütün nesneler Cart kümesine uygulanır. Bir
sepette maksimum 10 adet benzersiz item (VasItem dahil değil) bulunabilir. Toplam ürün
adedi 30'dan fazla olamaz. Cart'ın toplam tutarı 500.000 TL'den fazla olamaz.

* */

@Data
@Component
public class Cart {
    @Value("${cart.max_item_number}")
    private int MAX_ITEM_NUMBER = 30;
    @Value("${cart.max_unique_item_number}")
    private int MAX_UNIQUE_ITEM_NUMBER = 10;
    @Value("${cart.max_amount}")
    private double MAX_AMOUNT;

    private UUID id;
    private double totalAmount;
    private double totalDiscount;
    private int appliedPromotionId;
    private int itemCount;
    private List<Item> items;

    public Cart(){
        items = new ArrayList<>();
        itemCount = 0;
    }

    private int calculateItemCountByItemId(int itemId) {
        return (int) this.items.stream()
                .filter(item -> item.getId() == itemId)
                .mapToInt(Item::getQuantity)
                .sum();
    }
    public Item getItemByIdFromCart(int itemId){
        return items.stream()
                .filter(item -> item.getId() == itemId)
                .findFirst()
                .orElse(null);
    }
    public boolean isItemQuantityExtendingLimits(Item item){
        Item existingItem = getItemByIdFromCart(item.getId());
        if(existingItem == null)
            return false;
        else{
            return existingItem.getQuantity() + item.getQuantity() > item.getMaxQuantity();
        }
    }
    public void increaseItemCount(int quantity) {
        setItemCount(this.itemCount + quantity);
    }
    public void addItem(Item itemToBeAdded) {
        Item existingItem = getItemByIdFromCart(itemToBeAdded.getId());
        if(existingItem == null){
            items.add(itemToBeAdded);
        }else {
            existingItem.updateQuantity(itemToBeAdded.getQuantity());
        }
        increaseItemCount(itemToBeAdded.getQuantity());
        totalAmount += itemToBeAdded.getItemTotalAmount();
    }

    public void removeItem(int itemId) {
        setItemCount(calculateItemCountByItemId(itemId));
        items.removeIf(item -> item.getId() == itemId);
        totalAmount -= getItemByIdFromCart(itemId).getItemTotalAmount();
    }

    public void resetCart() {
        this.id = UUID.randomUUID();
        this.totalAmount = 0;
        this.totalDiscount = 0;
        this.appliedPromotionId = 0;
        this.itemCount = 0;
        this.items.clear();
    }
}
