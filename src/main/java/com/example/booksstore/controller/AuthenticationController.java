package com.example.booksstore.controller;

import com.example.booksstore.dto.login.UserLoginRequestDto;
import com.example.booksstore.dto.login.UserLoginResponseDto;
import com.example.booksstore.dto.registration.UserRegistrationRequestDto;
import com.example.booksstore.dto.registration.UserResponseDto;
import com.example.booksstore.service.AuthenticationService;
import com.example.booksstore.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Registration management", description = "Endpoints for managing registration")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/auth")
public class AuthenticationController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping("/registration")
    public UserResponseDto register(@RequestBody @Valid UserRegistrationRequestDto request) {
        return userService.register(request);
    }

    @PostMapping("/login")
    public UserLoginResponseDto login(@RequestBody @Valid UserLoginRequestDto request) {
        return authenticationService.login(request);
    }
}
