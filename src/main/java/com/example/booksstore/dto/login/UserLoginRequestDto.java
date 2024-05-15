package com.example.booksstore.dto.login;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserLoginRequestDto {
    @Email
    @Size(max = 255)
    @NotBlank
    private String email;
    @Size(min = 8)
    @NotBlank
    private String password;
}
