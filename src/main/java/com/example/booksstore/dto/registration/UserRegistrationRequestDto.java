package com.example.booksstore.dto.registration;

import com.example.booksstore.constraints.FieldMatch;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@FieldMatch(first = "password", second = "repeatPassword")
@Data
public class UserRegistrationRequestDto {
    @Email
    @NotBlank
    private String email;
    @Size(min = 8)
    @NotBlank
    private String password;
    @Size(min = 8)
    @NotBlank
    private String repeatPassword;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    private String shippingAddress;
}
