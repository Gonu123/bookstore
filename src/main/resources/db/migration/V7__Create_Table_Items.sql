CREATE TABLE Items
(
    book_id VARCHAR(100),
    order_id VARCHAR(100),
    quantity int,
    FOREIGN KEY (order_id) REFERENCES ORDERS(order_id),
    FOREIGN KEY (book_id) REFERENCES BOOK_DETAILS(isbn),
    PRIMARY KEY (book_id, order_id)
);