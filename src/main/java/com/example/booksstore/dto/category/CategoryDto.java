package com.example.booksstore.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CategoryDto {
    @NotBlank
    @Size(max = 255)
    private String name;
    @Size(max = 500)
    private String description;
}
