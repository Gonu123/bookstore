DROP TABLE IF EXISTS BOOK_DETAILS;
CREATE TABLE BOOK_DETAILS(
    isbn varchar(20) PRIMARY KEY UNIQUE, name VARCHAR(100) NOT NULL, description VARCHAR(1000) NOT NULL, author VARCHAR(100) NOT NULL, publicationYear VARCHAR(5) NOT NULL, imageUrlM VARCHAR(1000) NOT NULL, imageUrlL VARCHAR(1000) NOT NULL, price DOUBLE PRECISION NOT NULL, booksAvailable INT NOT NULL, rating double precision NOT NULL
);