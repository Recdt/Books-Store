package com.example.booksstore.service;

import static com.example.booksstore.util.TestConstants.DEFAULT_AUTHOR;
import static com.example.booksstore.util.TestConstants.DEFAULT_BOOK_DESCRIPTION;
import static com.example.booksstore.util.TestConstants.DEFAULT_BOOK_TITLE;
import static com.example.booksstore.util.TestConstants.DEFAULT_CATEGORY_DESCRIPTION;
import static com.example.booksstore.util.TestConstants.DEFAULT_CATEGORY_NAME;
import static com.example.booksstore.util.TestConstants.DEFAULT_COVER_IMAGE;
import static com.example.booksstore.util.TestConstants.DEFAULT_ID;
import static com.example.booksstore.util.TestConstants.DEFAULT_ISBN;
import static com.example.booksstore.util.TestConstants.DEFAULT_PRICE;
import static com.example.booksstore.util.TestConstants.NUMBER_OF_INVOCATIONS;
import static com.example.booksstore.util.TestConstants.SECOND_CATEGORY_DESCRIPTION;
import static com.example.booksstore.util.TestConstants.SECOND_CATEGORY_NAME;
import static com.example.booksstore.util.TestConstants.SECOND_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.booksstore.dto.BookDto;
import com.example.booksstore.dto.BookRequestDto;
import com.example.booksstore.dto.book.BookDtoWithoutCategoryIds;
import com.example.booksstore.exceptions.CategoryDoesNotExistsException;
import com.example.booksstore.mappers.BookMapper;
import com.example.booksstore.models.Book;
import com.example.booksstore.models.Category;
import com.example.booksstore.repository.BookRepository;
import com.example.booksstore.repository.CategoryRepository;
import com.example.booksstore.service.impl.BookServiceImpl;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {
    @Mock
    private BookRepository bookRepository;
    @Mock
    private BookMapper bookMapper;
    @Mock
    private CategoryRepository categoryRepository;

    private BookService bookService;
    private Book book;
    private BookRequestDto requestDto;
    private BookDto bookDto;
    private Category category1;
    private Category category2;

    @BeforeEach
    void setUp() {
        bookService = new BookServiceImpl(bookRepository, bookMapper, categoryRepository);

        book = new Book();
        book.setId(DEFAULT_ID);
        book.setTitle(DEFAULT_BOOK_TITLE);
        book.setAuthor(DEFAULT_AUTHOR);
        book.setIsbn(DEFAULT_ISBN);
        book.setPrice(DEFAULT_PRICE);
        book.setDescription(DEFAULT_BOOK_DESCRIPTION);
        book.setCoverImage(DEFAULT_COVER_IMAGE);
        book.setCategories(new HashSet<>());

        requestDto = new BookRequestDto();
        requestDto.setTitle(DEFAULT_BOOK_TITLE);
        requestDto.setAuthor(DEFAULT_AUTHOR);
        requestDto.setIsbn(DEFAULT_ISBN);
        requestDto.setPrice(DEFAULT_PRICE);
        requestDto.setDescription(DEFAULT_BOOK_DESCRIPTION);
        requestDto.setCoverImage(DEFAULT_COVER_IMAGE);
        requestDto.setCategoryIds(new HashSet<>(Arrays.asList(DEFAULT_ID, SECOND_ID)));

        bookDto = new BookDto();
        bookDto.setId(DEFAULT_ID);
        bookDto.setTitle(DEFAULT_BOOK_TITLE);
        bookDto.setAuthor(DEFAULT_AUTHOR);
        bookDto.setIsbn(DEFAULT_ISBN);
        bookDto.setPrice(DEFAULT_PRICE);
        bookDto.setDescription(DEFAULT_BOOK_DESCRIPTION);
        bookDto.setCoverImage(DEFAULT_COVER_IMAGE);
        bookDto.setCategoryIds(new HashSet<>(Arrays.asList(DEFAULT_ID, SECOND_ID)));

        category1 = new Category();
        category1.setId(DEFAULT_ID);
        category1.setName(DEFAULT_CATEGORY_NAME);
        category1.setDescription(DEFAULT_CATEGORY_DESCRIPTION);

        category2 = new Category();
        category2.setId(SECOND_ID);
        category2.setName(SECOND_CATEGORY_NAME);
        category2.setDescription(SECOND_CATEGORY_DESCRIPTION);
    }

    @Test
    @DisplayName("Verify save() method works with valid categories")
    void save_ValidBookRequestDto_ReturnsBookDto() {
        when(categoryRepository.findAllById(requestDto.getCategoryIds()))
                .thenReturn(Arrays.asList(category1, category2));
        when(bookMapper.toModel(requestDto)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(book);
        when(bookMapper.toDto(book)).thenReturn(bookDto);

        BookDto result = bookService.save(requestDto);

        assertNotNull(result);
        assertEquals(result.getTitle(), requestDto.getTitle());
    }

    @Test
    @DisplayName("Verify save() method throws exception for invalid categories")
    void save_InvalidCategories_ThrowsException() {
        when(categoryRepository.findAllById(requestDto.getCategoryIds()))
                .thenReturn(Collections.singletonList(category1));

        CategoryDoesNotExistsException exception = assertThrows(
                    CategoryDoesNotExistsException.class, () -> {
                        bookService.save(requestDto);
                    }
        );

        assertEquals("Categories with ids [2] do not exist", exception.getMessage());
    }

    @Test
    @DisplayName("Verify getById() method works")
    void getById_ValidId_ReturnsBookDto() {
        when(bookRepository.findById(DEFAULT_ID)).thenReturn(Optional.of(book));
        when(bookMapper.toDto(book)).thenReturn(bookDto);

        BookDto result = bookService.getById(DEFAULT_ID);

        assertNotNull(result);
        assertEquals(result.getTitle(), bookDto.getTitle());
    }

    @Test
    @DisplayName("Verify findAll() method works")
    void findAll_ValidPageable_ReturnsBookDtoList() {
        when(bookRepository.findAll(any(Pageable.class))).thenReturn(
                new PageImpl<>(Collections.singletonList(book)));
        when(bookMapper.toDto(any(Book.class))).thenReturn(bookDto);

        List<BookDto> result = bookService.findAll(Pageable.unpaged());

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(result.stream().findFirst().get().getTitle(), bookDto.getTitle());
    }

    @Test
    @DisplayName("Verify updateById() method works")
    void updateById_ValidId_ReturnsUpdatedBookDto() {
        when(bookRepository.existsById(DEFAULT_ID)).thenReturn(true);
        when(bookRepository.existsByIsbnAndIdNot(book.getIsbn(), DEFAULT_ID)).thenReturn(false);
        when(bookMapper.toModel(requestDto)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(book);
        when(bookMapper.toDto(book)).thenReturn(bookDto);

        BookDto result = bookService.updateById(DEFAULT_ID, requestDto);

        assertNotNull(result);
        assertEquals(result.getTitle(), requestDto.getTitle());
    }

    @Test
    @DisplayName("Verify deleteById() method works")
    void deleteById_ValidId_Success() {
        when(bookRepository.existsById(DEFAULT_ID)).thenReturn(true);
        doNothing().when(bookRepository).deleteById(DEFAULT_ID);

        bookService.deleteById(DEFAULT_ID);

        verify(bookRepository, times(NUMBER_OF_INVOCATIONS)).deleteById(DEFAULT_ID);
    }

    @Test
    @DisplayName("Verify getBooksByCategoryId() method works")
    void getBooksByCategoryId_ValidId_ReturnsBookDtoList() {
        when(bookRepository.findAllByCategoryId(DEFAULT_ID,
                Pageable.unpaged())).thenReturn(Collections.singletonList(book));
        when(bookMapper.toDtoWithoutCategories(
                any(Book.class))).thenReturn(new BookDtoWithoutCategoryIds());

        List<BookDtoWithoutCategoryIds> result = bookService.getBooksByCategoryId(DEFAULT_ID,
                Pageable.unpaged());

        assertNotNull(result);
        assertEquals(1, result.size());
    }
}
