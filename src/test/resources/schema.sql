CREATE TABLE if NOT EXISTS bank_operations (
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    date DATETIME,
    operation_type VARCHAR(255),
    operation_amount DOUBLE,
    balance DOUBLE
);

INSERT INTO bank_operations(date, operation_type, operation_amount, balance)
	VALUES ('2024-12-17 00:00:00', 'DEPOSIT', 500, 500);

INSERT INTO bank_operations(date, operation_type, operation_amount, balance)
    VALUES ('2024-12-18 00:00:00', 'DEPOSIT', 1750, 2250);

INSERT INTO bank_operations(date, operation_type, operation_amount, balance)
    VALUES ('2024-12-19 00:00:00', 'WITHDRAW', 250, 2000);
