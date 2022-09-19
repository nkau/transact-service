CREATE TABLE investor
(
    id INTEGER NOT NULL PRIMARY KEY,
    address VARCHAR(50) NOT NULL,
    date_of_birth date NOT NULL,
    email_address VARCHAR(30) NOT NULL,
    name VARCHAR(20) NOT NULL,
    surname VARCHAR(20) NOT NULL,
    mobile_number VARCHAR(10)
);

CREATE TABLE product(
                        id INTEGER NOT NULL PRIMARY KEY,
                        product_name  VARCHAR(20) NOT NULL,
                        type VARCHAR(10) NOT NULL,
                        balance DECIMAL NOT NULL,
                        investor_id INTEGER NOT NULL REFERENCES investor
);
INSERT INTO investor (id,name,surname,date_of_birth,address,mobile_number,email_address) values (1,'joe','soap',DATE '1964-01-01','joburg','0823454321','joesoap@mail.com');
INSERT INTO product(id,product_name,type,balance,investor_id) values(1,'SAVINGS','INVESTMENT',2000000,1);
