package trendyolCase.checkout.model.entity.item;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Getter
@Component
public class DefaultItem extends Item {
    @Value("${default_item.max_item_count}")
    private int MAX_ITEM_COUNT_CART;

}
