package trendyolCase.checkout.model.entity.item;

import lombok.Data;
import trendyolCase.checkout.exception.BusinessException;

import java.util.ArrayList;
import java.util.List;

/*Item
Cart üzerinde bulunan ürünlerdir. Cart'a item eklenip, çıkartılabilir. Cart item'ları sıfırlanabilir.
Itemlar birden fazla türde olabilir VasItem, DefaultItem, DigitalItem gibi. Item birden fazla
adette eklenebilir. Bir itemin maksimum eklenme adedi 10'dur. Her item'in fiyatı farklı şekilde
belirlenir ve input olarak uygulamaya verilir. Sepetteki itemlarin satıcı ve kategori ID'leri
vardır*/

//TODO should it be abstract, defaulItem has vasItem methods
@Data
public class Item {
    private static final int MAX_ITEM_COUNT_VAS_ITEM = 3;
    Integer id;
    private int quantity;
    private int categoryId;
    private int sellerId;
    private double price;
    private int maxQuantity = 10;
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
