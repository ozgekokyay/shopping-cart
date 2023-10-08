package trendyolCase.checkout.model.entity.cart;
import lombok.Data;
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
public class Cart {
    // TODO config file
    private static final int MAX_ITEM_NUMBER = 30;
    private static final int MAX_UNIQUE_ITEM_NUMBER = 10;
    private static final double MAX_AMOUNT = 500000;

    private UUID id;
    private double totalAmount;
    private double discount;
    private double promotion;
    private int uniqueItemCount; // implement using stream.distinct().count()
    private int itemCount; // implement using stream.count()
    private List<Item> items;

    public Cart(){
        items = new ArrayList<>();
        itemCount = 0;
    }

    private double calculateTotalAmountWithoutPromotion() {
        return items.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
    }
    private void increaseItemCount(Item item) {
        setItemCount(this.itemCount + item.getQuantity());
    }

    private int calculateUniqueItemCount() {
        return (int) this.items.stream()
                .map(Item::getId)
                .distinct()
                .count();
    }

    private int calculateItemCountByItemId(int itemId) {
        return (int) this.items.stream()
                .filter(item -> item.getId() == itemId)
                .mapToInt(Item::getQuantity)
                .sum();
    }
//TODO control item then add
    public void addItem(Item item) {

        if (isCartAmountExtendingLimits() || isCartUniqueItemNumberExtendingLimits() || isCartItemNumberExtendingLimits()) {
            System.out.println("Cart limits exceeded");
            return;
        }
        items.add(item);
        increaseItemCount(item);
        totalAmount = calculateTotalAmountWithoutPromotion();
        uniqueItemCount = calculateUniqueItemCount();
    }
//TODO rename methods
    public void removeItem(int itemId) {
        setItemCount(calculateItemCountByItemId(itemId));
        items.removeIf(item -> item.getId() == itemId);
        totalAmount = calculateTotalAmountWithoutPromotion();
        uniqueItemCount = calculateUniqueItemCount();
    }

    public boolean isCartAmountExtendingLimits(){
        return getTotalAmount() > MAX_AMOUNT;
    }
    public boolean isCartUniqueItemNumberExtendingLimits(){
        return getUniqueItemCount() > MAX_UNIQUE_ITEM_NUMBER;
    }
    public boolean isCartItemNumberExtendingLimits(){
        return getItems().size() > MAX_ITEM_NUMBER;
    }
//    public boolean isItemQuantityExtendingLimits(Item item){
//        return item.getQuantity() > 10;
//    }
    public Item getItemById(int itemId) {
        return items.stream()
                .filter(item -> item.getId() == itemId)
                .findFirst()
                .orElse(null);
    }

    // Reset cart to initial state and remove all items. Also, assign a new UUID to cart.
    public void resetCart() {
        this.id = UUID.randomUUID();
        this.totalAmount = 0;
        this.discount = 0;
        this.promotion = 0;
        this.uniqueItemCount = 0;
        this.itemCount = 0;
        this.items.clear();
    }
}
