INSERT INTO Authors (name, birth_year) VALUES
('J.K. Rowling', 1965),
('George Orwell', 1903),
('Leo Tolstoy', 1828);

INSERT INTO Books (title, author_id, published_year) VALUES
('Harry Potter and the Philosopher''s Stone', 101, 1997),
('1984', 102, 1949),
('War and Peace', 103, 1869);

INSERT INTO Users (name, email, registration_date) VALUES
('Alice Johnson', 'alice.johnson@example.com', '2023-01-15'),
('Bob Smith', 'bob.smith@example.com', '2023-03-20'),
('Charlie Brown', 'charlie.brown@example.com', '2023-05-12');

INSERT INTO Borrowings (user_id, book_id, borrowed_date, return_date) VALUES
(101, 101, '2023-10-01', '2023-10-15'),
(102, 102, '2023-10-05', NULL),
(103, 103, '2023-10-07', '2023-10-20');