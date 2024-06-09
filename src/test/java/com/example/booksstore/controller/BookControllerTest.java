package com.example.booksstore.controller;

import static com.example.booksstore.util.TestConstants.ADMIN_ROLE;
import static com.example.booksstore.util.TestConstants.BOOKS_PATH;
import static com.example.booksstore.util.TestConstants.CLEAN_BOOKS_SCRIPT_PATH;
import static com.example.booksstore.util.TestConstants.DEFAULT_AUTHOR;
import static com.example.booksstore.util.TestConstants.DEFAULT_BOOK_DESCRIPTION;
import static com.example.booksstore.util.TestConstants.DEFAULT_BOOK_TITLE;
import static com.example.booksstore.util.TestConstants.DEFAULT_COVER_IMAGE;
import static com.example.booksstore.util.TestConstants.DEFAULT_ID;
import static com.example.booksstore.util.TestConstants.DEFAULT_ISBN;
import static com.example.booksstore.util.TestConstants.DEFAULT_PRICE;
import static com.example.booksstore.util.TestConstants.ID_HTTP_PATH;
import static com.example.booksstore.util.TestConstants.SECOND_ID;
import static com.example.booksstore.util.TestConstants.SETUP_BOOKS_SCRIPT_PATH;
import static com.example.booksstore.util.TestConstants.USER_ROLE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.booksstore.dto.BookDto;
import com.example.booksstore.dto.BookRequestDto;
import com.example.booksstore.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.Connection;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import javax.sql.DataSource;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class BookControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BookService bookService;

    @BeforeAll
    static void beforeAll(@Autowired DataSource dataSource) {
        teardown(dataSource);
    }

    @BeforeEach
    void setUp(@Autowired DataSource dataSource) {
        addBooks(dataSource);
    }

    @AfterEach
    void tearDown(@Autowired DataSource dataSource) {
        teardown(dataSource);
    }

    @Test
    @WithMockUser(roles = {USER_ROLE, ADMIN_ROLE})
    @DisplayName("Get a list of all existing books")
    void getAll_GivenBooks_ReturnsAllBooks() throws Exception {
        BookDto sampleBook = createSampleBookDto();
        Mockito.when(bookService.findAll(any())).thenReturn(
                Collections.singletonList(sampleBook));

        MvcResult result = mockMvc.perform(get(BOOKS_PATH)
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        BookDto[] actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), BookDto[].class);

        assertNotNull(actual);
        assertEquals(1, actual.length);
        assertEquals(sampleBook, actual[0]);
    }

    @Test
    @WithMockUser(roles = {USER_ROLE, ADMIN_ROLE})
    @DisplayName("Get a book by valid ID")
    void getBookById_GivenId_ReturnsBook() throws Exception {
        BookDto sampleBook = createSampleBookDto();
        Mockito.when(bookService.getById(DEFAULT_ID)).thenReturn(sampleBook);

        MvcResult result = mockMvc.perform(get(BOOKS_PATH + ID_HTTP_PATH, DEFAULT_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        BookDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), BookDto.class);

        assertNotNull(actual);
        assertEquals(sampleBook, actual);
    }

    @Test
    @WithMockUser(roles = ADMIN_ROLE)
    @DisplayName("Create a new book with valid data")
    void createBook_GivenValidData_ReturnsBook() throws Exception {
        BookRequestDto requestDto = createSampleBookRequestDto();
        BookDto sampleBook = createSampleBookDto();
        Mockito.when(bookService.save(any(BookRequestDto.class))).thenReturn(sampleBook);

        MvcResult result = mockMvc.perform(post(BOOKS_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andReturn();

        BookDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), BookDto.class);

        assertNotNull(actual);
        assertEquals(sampleBook, actual);
    }

    @Test
    @WithMockUser(roles = ADMIN_ROLE)
    @DisplayName("Update existing book")
    void updateBookById_GivenValidData_ReturnsBook() throws Exception {
        BookRequestDto updateDto = createSampleBookRequestDto();
        BookDto sampleBook = createSampleBookDto();
        Mockito.when(bookService.updateById(eq(DEFAULT_ID), any(BookRequestDto.class)))
                .thenReturn(sampleBook);

        MvcResult result = mockMvc.perform(put(BOOKS_PATH + ID_HTTP_PATH, DEFAULT_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk())
                .andReturn();

        BookDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), BookDto.class);

        assertNotNull(actual);
        assertEquals(sampleBook, actual);
    }

    @Test
    @WithMockUser(roles = ADMIN_ROLE)
    @DisplayName("Delete existing book")
    void deleteBookById_GivenValidId_NoContentStatus() throws Exception {
        Mockito.doNothing().when(bookService).deleteById(DEFAULT_ID);

        mockMvc.perform(delete(BOOKS_PATH + ID_HTTP_PATH, DEFAULT_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    private BookDto createSampleBookDto() {
        BookDto bookDto = new BookDto();
        bookDto.setId(DEFAULT_ID);
        bookDto.setTitle(DEFAULT_BOOK_TITLE);
        bookDto.setAuthor(DEFAULT_AUTHOR);
        bookDto.setIsbn(DEFAULT_ISBN);
        bookDto.setPrice(DEFAULT_PRICE);
        bookDto.setDescription(DEFAULT_BOOK_DESCRIPTION);
        bookDto.setCoverImage(DEFAULT_COVER_IMAGE);
        bookDto.setCategoryIds(new HashSet<>(Arrays.asList(DEFAULT_ID, SECOND_ID)));
        return bookDto;
    }

    private BookRequestDto createSampleBookRequestDto() {
        BookRequestDto bookRequestDto = new BookRequestDto();
        bookRequestDto.setTitle(DEFAULT_BOOK_TITLE);
        bookRequestDto.setAuthor(DEFAULT_AUTHOR);
        bookRequestDto.setIsbn(DEFAULT_ISBN);
        bookRequestDto.setPrice(DEFAULT_PRICE);
        bookRequestDto.setDescription(DEFAULT_BOOK_DESCRIPTION);
        bookRequestDto.setCoverImage(DEFAULT_COVER_IMAGE);
        bookRequestDto.setCategoryIds(new HashSet<>(Arrays.asList(DEFAULT_ID, SECOND_ID)));
        return bookRequestDto;
    }

    @SneakyThrows
    private static void teardown(DataSource dataSource) {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(true);
            ScriptUtils.executeSqlScript(connection,
                    new ClassPathResource(CLEAN_BOOKS_SCRIPT_PATH));
        }
    }

    @SneakyThrows
    private void addBooks(DataSource dataSource) {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(true);
            ScriptUtils.executeSqlScript(connection,
                    new ClassPathResource(SETUP_BOOKS_SCRIPT_PATH));
        }
    }
}
