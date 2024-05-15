package com.example.booksstore.service;

import com.example.booksstore.dto.registration.UserRegistrationRequestDto;
import com.example.booksstore.dto.registration.UserResponseDto;

public interface UserService {
    UserResponseDto register(UserRegistrationRequestDto user);
}
