package com.example.booksstore.mappers;

import com.example.booksstore.config.MapperConfig;
import com.example.booksstore.dto.cartitem.CartItemResponseDto;
import com.example.booksstore.dto.shoppingcart.ShoppingCartResponseDto;
import com.example.booksstore.models.CartItem;
import com.example.booksstore.models.ShoppingCart;
import com.example.booksstore.models.User;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(config = MapperConfig.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ShoppingCartMapper {
    @Mapping(target = "userId", source = "user", qualifiedByName = "getUserIdFromUser")
    ShoppingCartResponseDto toDto(ShoppingCart shoppingCart);

    CartItemResponseDto toCartItemResponseDto(CartItem cartItem);

    @Named("getCartItemsDtoSet")
    default Set<CartItemResponseDto> getCartItemsDtoSet(Set<CartItem> cartItemSet) {
        return cartItemSet.stream()
                .map(this::toCartItemResponseDto)
                .collect(Collectors.toSet());
    }

    @Named("getUserIdFromUser")
    default Long getUserIdFromUser(User user) {
        return user.getId();
    }
}
