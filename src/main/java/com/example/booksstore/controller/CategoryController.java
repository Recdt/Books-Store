package com.example.booksstore.controller;

import com.example.booksstore.dto.book.BookDtoWithoutCategoryIds;
import com.example.booksstore.dto.category.CategoryDto;
import com.example.booksstore.models.Category;
import com.example.booksstore.service.BookService;
import com.example.booksstore.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/categories")
public class CategoryController {
    private final CategoryService categoryService;
    private final BookService bookService;

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create a category",
            description = "Creates book with auto generated ID")
    @PostMapping
    public CategoryDto createCategory(@RequestBody @Valid CategoryDto category) {
        return categoryService.save(category);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "Returns a page of categories",
            description = "Get a list of all available categories, "
                    + "that your pagination required")
    @GetMapping
    public List<Category> getAll(Pageable pageable) {
        return categoryService.findAll(pageable);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "Get category by id", description = "Gets a category from db")
    @GetMapping("/{id}")
    public CategoryDto getCategoryById(@PathVariable Long id) {
        return categoryService.getById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Updates category", description = "Updates category by ID.")
    @PutMapping("/{id}")
    public CategoryDto updateCategory(@PathVariable Long id,
                                      @RequestBody @Valid CategoryDto category) {
        return categoryService.update(id, category);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Deletes the category", description = "Deletes the category by ID")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id) {
        categoryService.deleteById(id);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "Returns a page of books with some category",
            description = "Get a list of all available books in category")
    @GetMapping("/{id}/books")
    public List<BookDtoWithoutCategoryIds> getAllBooksByCategory(@PathVariable Long id,
                                                                 Pageable pageable) {
        return bookService.getBooksByCategoryId(id, pageable);
    }
}
