package com.example.booksstore.mappers;

import com.example.booksstore.config.MapperConfig;
import com.example.booksstore.dto.order.OrderRequestDto;
import com.example.booksstore.dto.order.OrderResponseDto;
import com.example.booksstore.models.Order;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface OrderMapper {
    OrderResponseDto toDto(Order order);

    Order toModel(OrderRequestDto requestDto);
}
