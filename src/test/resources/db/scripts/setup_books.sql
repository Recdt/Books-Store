INSERT INTO categories (id, name) VALUES (1, 'Fiction');
INSERT INTO categories (id, name) VALUES (2, 'Non-Fiction');

INSERT INTO books (id, title, author, isbn, price, description, cover_image, is_deleted) VALUES
(1, 'Sample Book 1', 'Sample Author 1', '978-3-16-148410-0', 19.99, 'Sample Description 1', 'Sample Cover Image 1', false),
(2, 'Sample Book 2', 'Sample Author 2', '978-3-16-148410-1', 29.99, 'Sample Description 2', 'Sample Cover Image 2', false);

INSERT INTO books_categories (book_id, category_id) VALUES (1, 1);
INSERT INTO books_categories (book_id, category_id) VALUES (1, 2);
INSERT INTO books_categories (book_id, category_id) VALUES (2, 1);

