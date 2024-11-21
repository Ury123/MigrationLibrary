CREATE SEQUENCE author_id_seq START 101;
CREATE TABLE Authors (
    id INT DEFAULT nextval('author_id_seq') PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    birth_year INT
);

CREATE SEQUENCE book_id_seq START 101;
CREATE TABLE Books (
    id INT DEFAULT nextval('book_id_seq') PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author_id INT REFERENCES Authors(id) ON DELETE SET NULL,
    published_year INT
);

CREATE SEQUENCE user_id_seq START 101;
CREATE TABLE Users (
    id INT DEFAULT nextval('user_id_seq') PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    registration_date DATE DEFAULT CURRENT_DATE
);

CREATE SEQUENCE borrowing_id_seq START 101;
CREATE TABLE Borrowings (
    id INT DEFAULT nextval('borrowing_id_seq') PRIMARY KEY,
    user_id INT REFERENCES Users(id) ON DELETE CASCADE,
    book_id INT REFERENCES Books(id) ON DELETE CASCADE,
    borrowed_date DATE NOT NULL,
    return_date DATE
);