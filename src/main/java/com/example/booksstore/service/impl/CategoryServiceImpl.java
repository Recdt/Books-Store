package com.example.booksstore.service.impl;

import com.example.booksstore.dto.category.CategoryDto;
import com.example.booksstore.exceptions.EntityNotFoundException;
import com.example.booksstore.exceptions.NoSuchCategoryException;
import com.example.booksstore.mappers.CategoryMapper;
import com.example.booksstore.models.Category;
import com.example.booksstore.repository.CategoryRepository;
import com.example.booksstore.service.CategoryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryDto> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable).stream()
                .map(categoryMapper::toDto)
                .toList();
    }

    @Override
    public CategoryDto getById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() ->
                new NoSuchCategoryException("Category with id: " + id + " not exists."));
        return categoryMapper.toDto(category);
    }

    @Override
    public CategoryDto save(CategoryDto categoryDto) {
        Category category = categoryMapper.toEntity(categoryDto);
        return categoryMapper.toDto(categoryRepository.save(category));
    }

    @Override
    public CategoryDto update(Long id, CategoryDto categoryDto) {
        checkExistanse(id);
        Category entity = categoryMapper.toEntity(categoryDto);
        entity.setId(id);
        return categoryMapper.toDto(categoryRepository.save(entity));
    }

    @Override
    public void deleteById(Long id) {
        checkExistanse(id);
        categoryRepository.deleteById(id);
    }

    private void checkExistanse(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new EntityNotFoundException("Can't find category by id " + id);
        }
    }
}
