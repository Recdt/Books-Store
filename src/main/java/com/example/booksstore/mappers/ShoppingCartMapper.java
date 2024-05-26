package com.example.booksstore.mappers;

import com.example.booksstore.config.MapperConfig;
import com.example.booksstore.dto.cartitem.CartItemResponseDto;
import com.example.booksstore.dto.shoppingcart.ShoppingCartResponseDto;
import com.example.booksstore.models.CartItem;
import com.example.booksstore.models.ShoppingCart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(config = MapperConfig.class, uses = CartItemMapper.class,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ShoppingCartMapper {
    @Mapping(target = "userId", source = "user.id")
    ShoppingCartResponseDto toDto(ShoppingCart shoppingCart);

    CartItemResponseDto toCartItemResponseDto(CartItem cartItem);
}
