package trendyolCase.checkout.model.entity.item;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import trendyolCase.checkout.exception.BusinessException;
@Component
public class VasItem extends Item {
    @Value("${vas_item.category_id}")
    private int categoryId;
    @Value("${vas_item.seller_id}")
    private int sellerId;
    @Value("${vas_item.max_quantity}")
    private int maxQuantity;
    @Override
    public void setQuantity(int quantity) {
        if ((getQuantity() + quantity) > this.maxQuantity) {
            throw new BusinessException("Item quantity is exceeding limits");
        }
        super.setQuantity(quantity);
    }

}
