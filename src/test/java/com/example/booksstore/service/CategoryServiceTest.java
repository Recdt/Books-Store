package com.example.booksstore.service;

import static com.example.booksstore.util.TestConstants.DEFAULT_CATEGORY_DESCRIPTION;
import static com.example.booksstore.util.TestConstants.DEFAULT_CATEGORY_NAME;
import static com.example.booksstore.util.TestConstants.DEFAULT_ID;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.example.booksstore.dto.category.CategoryDto;
import com.example.booksstore.exceptions.NoSuchCategoryException;
import com.example.booksstore.mappers.CategoryMapper;
import com.example.booksstore.models.Category;
import com.example.booksstore.repository.CategoryRepository;
import com.example.booksstore.service.impl.CategoryServiceImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private CategoryMapper categoryMapper;
    private CategoryService categoryService;
    private Category category;
    private CategoryDto categoryDto;

    @BeforeEach
    void setUp() {
        categoryService = new CategoryServiceImpl(categoryRepository, categoryMapper);

        category = new Category();
        category.setId(DEFAULT_ID);
        category.setName(DEFAULT_CATEGORY_NAME);
        category.setDescription(DEFAULT_CATEGORY_DESCRIPTION);

        categoryDto = new CategoryDto();
        categoryDto.setName(DEFAULT_CATEGORY_NAME);
        categoryDto.setDescription(DEFAULT_CATEGORY_DESCRIPTION);
    }

    @Test
    @DisplayName("Test findAll() method")
    void testFindAll() {
        List<Category> categories = new ArrayList<>();
        categories.add(category);
        Page<Category> page = new PageImpl<>(categories);

        when(categoryRepository.findAll(any(Pageable.class))).thenReturn(page);
        when(categoryMapper.toDto(category)).thenReturn(categoryDto);

        List<CategoryDto> result = categoryService.findAll(Pageable.unpaged());

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(categoryDto, result.stream().findFirst().get());
    }

    @Test
    @DisplayName("Test getById() method with existing ID")
    void testGetByIdExistingId() {
        when(categoryRepository.findById(DEFAULT_ID)).thenReturn(Optional.of(category));
        when(categoryMapper.toDto(category)).thenReturn(categoryDto);

        CategoryDto result = categoryService.getById(DEFAULT_ID);

        assertNotNull(result);
        assertEquals(categoryDto, result);
    }

    @Test
    @DisplayName("Test getById() method with non-existing ID")
    void testGetByIdNonExistingId() {
        when(categoryRepository.findById(DEFAULT_ID)).thenReturn(Optional.empty());

        assertThrows(NoSuchCategoryException.class, () -> {
            categoryService.getById(DEFAULT_ID);
        });
    }

    @Test
    @DisplayName("Test save() method")
    void testSave() {
        when(categoryMapper.toEntity(categoryDto)).thenReturn(category);
        when(categoryRepository.save(category)).thenReturn(category);
        when(categoryMapper.toDto(category)).thenReturn(categoryDto);

        CategoryDto result = categoryService.save(categoryDto);

        assertNotNull(result);
        assertEquals(categoryDto, result);
    }

    @Test
    @DisplayName("Test update() method with existing ID")
    void testUpdateExistingId() {
        when(categoryRepository.existsById(DEFAULT_ID)).thenReturn(true);
        when(categoryMapper.toEntity(categoryDto)).thenReturn(category);
        when(categoryRepository.save(category)).thenReturn(category);
        when(categoryMapper.toDto(category)).thenReturn(categoryDto);

        CategoryDto result = categoryService.update(DEFAULT_ID, categoryDto);

        assertNotNull(result);
        assertEquals(categoryDto, result);
    }

    @Test
    @DisplayName("Test update() method with non-existing ID")
    void testUpdateNonExistingId() {
        when(categoryRepository.existsById(DEFAULT_ID)).thenReturn(false);

        assertThrows(NoSuchCategoryException.class, () -> {
            categoryService.update(DEFAULT_ID, categoryDto);
        });
    }

    @Test
    @DisplayName("Test deleteById() method with existing ID")
    void testDeleteByIdExistingId() {
        when(categoryRepository.existsById(DEFAULT_ID)).thenReturn(true);
        doNothing().when(categoryRepository).deleteById(DEFAULT_ID);

        assertDoesNotThrow(() -> {
            categoryService.deleteById(DEFAULT_ID);
        });
    }

    @Test
    @DisplayName("Test deleteById() method with non-existing ID")
    void testDeleteByIdNonExistingId() {
        when(categoryRepository.existsById(DEFAULT_ID)).thenReturn(false);

        assertThrows(NoSuchCategoryException.class, () -> {
            categoryService.deleteById(DEFAULT_ID);
        });
    }
}
