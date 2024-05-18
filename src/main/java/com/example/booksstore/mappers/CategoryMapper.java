package com.example.booksstore.mappers;

import com.example.booksstore.config.MapperConfig;
import com.example.booksstore.dto.category.CategoryDto;
import com.example.booksstore.models.Category;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface CategoryMapper {
    CategoryDto toDto(Category category);

    Category toEntity(CategoryDto categoryDto);
}
