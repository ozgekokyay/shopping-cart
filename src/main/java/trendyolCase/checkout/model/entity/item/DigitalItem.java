package trendyolCase.checkout.model.entity.item;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/*
DigitalItem
DigitalItem olan bir cart'a sadece digital item eklenebilir. Digital item; steam card, bağış kartı
vb. gibi itemlardir. DigitalItem'ın maksimum eklenme adedi 5'tir. d. CategoryID'si 7889 olan
itemlar DigitalItem olarak tanımlanır. Bu CategoryID ile başka türden bir Item tanımlanamaz.
 */
@Data
@Component
public class DigitalItem extends Item {
    @Value("${digital_item.max_item_count}")
    private int MAX_ITEM_COUNT;
    private int maxQuantity = 5;
    private int categoryId = 7889;

}
