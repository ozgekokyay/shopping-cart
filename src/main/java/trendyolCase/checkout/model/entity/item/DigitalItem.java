package trendyolCase.checkout.model.entity.item;


import lombok.Data;

/*
DigitalItem
DigitalItem olan bir cart'a sadece digital item eklenebilir. Digital item; steam card, bağış kartı
vb. gibi itemlardir. DigitalItem'ın maksimum eklenme adedi 5'tir. d. CategoryID'si 7889 olan
itemlar DigitalItem olarak tanımlanır. Bu CategoryID ile başka türden bir Item tanımlanamaz.
 */
@Data
public class DigitalItem extends Item {
    private static final int MAX_ITEM_COUNT = 5;
    private int maxQuantity = 5;
    private int categoryId = 7889;



}
