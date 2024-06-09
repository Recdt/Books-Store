package com.example.booksstore.util;

import java.math.BigDecimal;

public class TestConstants {
    public static final Long DEFAULT_ID = 1L;
    public static final Long SECOND_ID = 2L;
    public static final Long UNREACHABLE_ID = 100L;
    public static final Integer NUMBER_OF_INVOCATIONS = 1;
    public static final String USER_ROLE = "USER";
    public static final String ADMIN_ROLE = "ADMIN";
    public static final String DEFAULT_CATEGORY_NAME = "Default Category";
    public static final String SECOND_CATEGORY_NAME = "Second Category";
    public static final String DEFAULT_CATEGORY_DESCRIPTION = "Default Description";
    public static final String SECOND_CATEGORY_DESCRIPTION = "Second Description";
    public static final String DEFAULT_BOOK_TITLE = "Default Book";
    public static final String DEFAULT_AUTHOR = "Author";
    public static final String DEFAULT_ISBN = "978-3-16-148410-0";
    public static final String DEFAULT_BOOK_DESCRIPTION = "Description";
    public static final String DEFAULT_COVER_IMAGE = "Cover Image";
    public static final String CATEGORIES_PATH = "/api/categories";
    public static final String BOOKS_PATH = "/api/books";
    public static final String ID_HTTP_PATH = "/{id}";
    public static final String SETUP_CATEGORIES_SCRIPT_PATH = "db/scripts/setup_categories.sql";
    public static final String SETUP_BOOKS_SCRIPT_PATH = "db/scripts/setup_books.sql";
    public static final String CLEAN_CATEGORIES_SCRIPT_PATH = "db/scripts/clean_categories.sql";
    public static final String CLEAN_BOOKS_SCRIPT_PATH = "db/scripts/clean_books.sql";
    public static final String CATEGORY_NOT_FOUND_MESSAGE = "Category not found";
    public static final BigDecimal DEFAULT_PRICE = BigDecimal.valueOf(19.99);
}
