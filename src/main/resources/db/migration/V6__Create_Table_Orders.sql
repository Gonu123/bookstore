CREATE TABLE ORDERS
(
    order_id VARCHAR(100) PRIMARY KEY,
    address  VARCHAR(1000) NOT NULL,
    order_date  timestamp(100) default NOW(),
    user_id  VARCHAR(50) NOT NULL,
    mode_of_payment VARCHAR(100) NOT NULL,
    FOREIGN KEY (user_Id) REFERENCES USERS(id)
);