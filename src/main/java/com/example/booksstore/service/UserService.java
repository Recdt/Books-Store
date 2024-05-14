package com.example.booksstore.service;

import com.example.booksstore.dto.UserRegistrationRequestDto;
import com.example.booksstore.dto.UserResponseDto;

public interface UserService {
    UserResponseDto register(UserRegistrationRequestDto user);
}
