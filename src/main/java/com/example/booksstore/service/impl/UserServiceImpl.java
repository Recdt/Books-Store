package com.example.booksstore.service.impl;

import com.example.booksstore.dto.UserRegistrationRequestDto;
import com.example.booksstore.dto.UserResponseDto;
import com.example.booksstore.exceptions.RegistrationException;
import com.example.booksstore.mappers.UserMapper;
import com.example.booksstore.repository.UserRepository;
import com.example.booksstore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RegistrationException("User with this email " + user.getEmail() + " exists.");
        }
        return userMapper.toResponseDto(userRepository.save(userMapper.toModel(user)));
    }
}
