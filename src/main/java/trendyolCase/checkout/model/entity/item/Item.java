package trendyolCase.checkout.model.entity.item;

import lombok.Data;

/*Item
Cart üzerinde bulunan ürünlerdir. Cart'a item eklenip, çıkartılabilir. Cart item'ları sıfırlanabilir.
Itemlar birden fazla türde olabilir VasItem, DefaultItem, DigitalItem gibi. Item birden fazla
adette eklenebilir. Bir itemin maksimum eklenme adedi 10'dur. Her item'in fiyatı farklı şekilde
belirlenir ve input olarak uygulamaya verilir. Sepetteki itemlarin satıcı ve kategori ID'leri
vardır*/

//TODO should it be abstract, defaulItem has vasItem methods
@Data
public class Item {
    Integer id;
    private int quantity;
    private int categoryId;
    private int sellerId;
    private double price;
    private int maxQuantity = 10;
    private String typeOfItem = getClass().toString();
    //TODO check separate
    public boolean isItemQuantityExceedingLimits() {
        return quantity > maxQuantity;
    }
}
