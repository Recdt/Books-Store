package com.example.booksstore.controller;

import static com.example.booksstore.util.TestConstants.ADMIN_ROLE;
import static com.example.booksstore.util.TestConstants.CATEGORIES_PATH;
import static com.example.booksstore.util.TestConstants.CATEGORY_NOT_FOUND_MESSAGE;
import static com.example.booksstore.util.TestConstants.CLEAN_CATEGORIES_SCRIPT_PATH;
import static com.example.booksstore.util.TestConstants.DEFAULT_CATEGORY_DESCRIPTION;
import static com.example.booksstore.util.TestConstants.DEFAULT_CATEGORY_NAME;
import static com.example.booksstore.util.TestConstants.DEFAULT_ID;
import static com.example.booksstore.util.TestConstants.ID_HTTP_PATH;
import static com.example.booksstore.util.TestConstants.NUMBER_OF_INVOCATIONS;
import static com.example.booksstore.util.TestConstants.SETUP_CATEGORIES_SCRIPT_PATH;
import static com.example.booksstore.util.TestConstants.UNREACHABLE_ID;
import static com.example.booksstore.util.TestConstants.USER_ROLE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.booksstore.dto.category.CategoryDto;
import com.example.booksstore.exceptions.EntityNotFoundException;
import com.example.booksstore.service.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.Connection;
import java.util.Collections;
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
class CategoryControllerTest {
    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private CategoryService categoryService;

    @BeforeAll
    static void beforeAll(@Autowired DataSource dataSource) {
        teardown(dataSource);
    }

    @BeforeEach
    void setUp(@Autowired DataSource dataSource) {
        addCategories(dataSource);
    }

    @AfterEach
    void tearDown(@Autowired DataSource dataSource) {
        teardown(dataSource);
    }

    @Test
    @WithMockUser(roles = {USER_ROLE, ADMIN_ROLE})
    @DisplayName("Get a list of all existing categories")
    void getAll_GivenCategories_ReturnsAllCategories() throws Exception {
        CategoryDto sampleCategory = createSampleCategoryDto();
        when(categoryService.findAll(
                Mockito.any())).thenReturn(Collections.singletonList(sampleCategory));

        MvcResult result = mockMvc.perform(get(CATEGORIES_PATH)
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        CategoryDto[] actual = objectMapper.readValue(result.getResponse().getContentAsString(),
                CategoryDto[].class);

        assertNotNull(actual);
        assertEquals(1, actual.length);
        assertEquals(sampleCategory, actual[0]);
    }

    @Test
    @WithMockUser(roles = {USER_ROLE, ADMIN_ROLE})
    @DisplayName("Get a category by valid ID")
    void getCategoryById_GivenId_ReturnsCategory() throws Exception {
        Long categoryId = 1L;
        CategoryDto sampleCategory = createSampleCategoryDto();
        when(categoryService.getById(categoryId)).thenReturn(sampleCategory);

        MvcResult result = mockMvc.perform(get(CATEGORIES_PATH + ID_HTTP_PATH, categoryId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        CategoryDto actual = objectMapper.readValue(result.getResponse().getContentAsString(),
                CategoryDto.class);

        assertNotNull(actual);
        assertEquals(sampleCategory, actual);
    }

    @Test
    @WithMockUser(roles = ADMIN_ROLE)
    @DisplayName("Create a new category with valid data")
    void createCategory_GivenValidData_ReturnsCategory() throws Exception {
        CategoryDto requestDto = createSampleCategoryDto();
        CategoryDto sampleCategory = createSampleCategoryDto();
        when(categoryService.save(Mockito.any(CategoryDto.class))).thenReturn(sampleCategory);

        MvcResult result = mockMvc.perform(post(CATEGORIES_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andReturn();

        CategoryDto actual = objectMapper.readValue(result.getResponse().getContentAsString(),
                CategoryDto.class);

        assertNotNull(actual);
        assertEquals(sampleCategory, actual);
    }

    @Test
    @WithMockUser(roles = ADMIN_ROLE)
    @DisplayName("Update existing category")
    void updateCategoryById_GivenValidData_ReturnsCategory() throws Exception {
        CategoryDto updateDto = createSampleCategoryDto();
        CategoryDto sampleCategory = createSampleCategoryDto();
        when(categoryService.update(DEFAULT_ID, updateDto)).thenReturn(sampleCategory);

        MvcResult result = mockMvc.perform(put(CATEGORIES_PATH + ID_HTTP_PATH, DEFAULT_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk())
                .andReturn();

        CategoryDto actual = objectMapper.readValue(result.getResponse().getContentAsString(),
                CategoryDto.class);

        assertNotNull(actual);
        assertEquals(sampleCategory, actual);
    }

    @Test
    @WithMockUser(roles = ADMIN_ROLE)
    @DisplayName("Delete non-existing category")
    void deleteCategoryById_GivenInvalidId_NotFoundStatus() throws Exception {
        doThrow(new EntityNotFoundException(CATEGORY_NOT_FOUND_MESSAGE)).when(categoryService)
                .deleteById(UNREACHABLE_ID);

        mockMvc.perform(delete(CATEGORIES_PATH + ID_HTTP_PATH, UNREACHABLE_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = ADMIN_ROLE)
    @DisplayName("Delete existing category")
    void deleteCategoryById_GivenValidId_NoContentStatus() throws Exception {
        Mockito.doNothing().when(categoryService).deleteById(DEFAULT_ID);

        mockMvc.perform(delete(CATEGORIES_PATH + ID_HTTP_PATH, DEFAULT_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(categoryService, times(NUMBER_OF_INVOCATIONS)).deleteById(DEFAULT_ID);
    }

    private CategoryDto createSampleCategoryDto() {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName(DEFAULT_CATEGORY_NAME);
        categoryDto.setDescription(DEFAULT_CATEGORY_DESCRIPTION);
        return categoryDto;
    }

    @SneakyThrows
    private void addCategories(DataSource dataSource) {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(true);
            ScriptUtils.executeSqlScript(
                    connection, new ClassPathResource(SETUP_CATEGORIES_SCRIPT_PATH));
        }
    }

    @SneakyThrows
    private static void teardown(DataSource dataSource) {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(true);
            ScriptUtils.executeSqlScript(
                    connection, new ClassPathResource(CLEAN_CATEGORIES_SCRIPT_PATH));
        }
    }
}
