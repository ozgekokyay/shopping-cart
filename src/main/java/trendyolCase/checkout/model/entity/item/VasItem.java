package trendyolCase.checkout.model.entity.item;
/*
VasItem
Value Added Service item sigorta, montaj gibi hizmetleri ifade eder. Bu ürünler fiziksel bir
ürünü değil belli bir ürüne bağlı hizmeti ifade eder ve tek başlarına bir anlamı yoktur.
Bu nedenle sadece Mobilya (CategoryID: 1001) ve Elektronik(CategoryID: 3004)
kategorisindeki default itemlara alt item olarak eklenebilir. Bir DefaultItem'a maksimum 3
adet VASItem eklenebilir. VasItem'in CategoryID'si 3242'dir. VasItem'ların satıcı ID'si
5003'tur. Satıcı ID'si 5003 olmayan VasItemlar tanımlanamaz. Satıcı ID'si 5003 olan baska
türde bir Item türü yoktur.
 */
public class VasItem extends Item {
    private static final int MAX_ITEM_CAN_BE_PURCHASED = 3;
    //todo validation

    private final int categoryId = 3242;
    private final int sellerId = 5003;
    private int maxQuantity = 3;

}
