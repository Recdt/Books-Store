package com.example.booksstore.service;

import com.example.booksstore.dto.login.UserLoginRequestDto;
import com.example.booksstore.dto.login.UserLoginResponseDto;

public interface AuthenticationService {
    UserLoginResponseDto login(UserLoginRequestDto requestDto);
}
