package com.example.booksstore.service.impl;

import com.example.booksstore.dto.registration.UserRegistrationRequestDto;
import com.example.booksstore.dto.registration.UserResponseDto;
import com.example.booksstore.exceptions.NonExistentRoleException;
import com.example.booksstore.exceptions.RegistrationException;
import com.example.booksstore.mappers.UserMapper;
import com.example.booksstore.models.Role;
import com.example.booksstore.models.User;
import com.example.booksstore.repository.RoleRepository;
import com.example.booksstore.repository.UserRepository;
import com.example.booksstore.service.ShoppingCartService;
import com.example.booksstore.service.UserService;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final ShoppingCartService shoppingCartService;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RegistrationException("User with this email " + user.getEmail() + " exists.");
        }
        Role defaultRole = roleRepository.getRoleByName(Role.RoleName.USER)
                .orElseThrow(() -> new NonExistentRoleException("Role "
                        + Role.RoleName.USER
                        + " is not exists."));
        User model = userMapper.toModel(user);
        model.setPassword(passwordEncoder.encode(model.getPassword()));
        model.setRoles(Collections.singleton(defaultRole));
        User createdUser = userRepository.save(model);
        shoppingCartService.createShoppingCartForUser(model);
        return userMapper.toResponseDto(createdUser);
    }
}
