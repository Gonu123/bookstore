DROP TABLE IF EXISTS BOOK_DETAILS;
CREATE TABLE BOOK_DETAILS(
    isbn INT PRIMARY KEY, name VARCHAR(50) NOT NULL, author VARCHAR(50) NOT NULL, image_url VARCHAR(100) NOT NULL, price DOUBLE PRECISION NOT NULL, books_available INT NOT NULL, publication_year VARCHAR(4) NOT NULL, description VARCHAR(100) NOT NULL , rating INT NOT NULL
)