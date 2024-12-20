CREATE TABLE if NOT EXISTS bank_operations (
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    date DATETIME,
    operation_type VARCHAR(255),
    operation_amount DOUBLE,
    balance DOUBLE
);