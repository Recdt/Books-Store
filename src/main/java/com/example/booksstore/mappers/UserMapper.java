package com.example.booksstore.mappers;

import com.example.booksstore.config.MapperConfig;
import com.example.booksstore.dto.registration.UserRegistrationRequestDto;
import com.example.booksstore.dto.registration.UserResponseDto;
import com.example.booksstore.models.User;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    UserResponseDto toResponseDto(User user);

    User toModel(UserRegistrationRequestDto requestDto);
}
