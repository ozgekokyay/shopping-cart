package trendyolCase.checkout.model.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import trendyolCase.checkout.model.dto.cart.CartDisplayDTO;
import trendyolCase.checkout.model.dto.item.ItemDTO;
import trendyolCase.checkout.model.dto.item.VasItemDTO;
import trendyolCase.checkout.model.entity.cart.Cart;
import org.mapstruct.factory.Mappers;
import trendyolCase.checkout.model.entity.item.Item;
import trendyolCase.checkout.model.entity.item.VasItem;


@Mapper(componentModel = "spring")
public interface CartMapper {

    CartMapper MAPPER = Mappers.getMapper(CartMapper.class);

    @Mapping(target = "totalPrice", expression = "java(cart.getTotalAmount() - cart.getTotalDiscount())")
    CartDisplayDTO mapToCartDisplayDTO(Cart cart);

    @Mapping(target = "itemId", source = "id")
    ItemDTO mapToItemDTO(Item item);

    @Mapping(target = "vasItemId", source = "id")
    VasItemDTO mapToVasItemDTO(VasItem vasItem);

}
