CREATE SEQUENCE hibernate_sequence;
CREATE TABLE investor
(
    id BIGINT NOT NULL PRIMARY KEY,
    address VARCHAR(50) NOT NULL,
    date_of_birth timestamp NOT NULL,
    email_address VARCHAR(30) NOT NULL UNIQUE ,
    name VARCHAR(20) NOT NULL,
    surname VARCHAR(20) NOT NULL,
    mobile_number VARCHAR(10) UNIQUE
);

CREATE TABLE product(
                        id INTEGER NOT NULL PRIMARY KEY,
                        product_name  VARCHAR(20) NOT NULL,
                        type VARCHAR(10) NOT NULL,
                        balance DECIMAL NOT NULL,
                        investor_id BIGINT NOT NULL REFERENCES investor
);
INSERT INTO investor (id,name,surname,date_of_birth,address,mobile_number,email_address) values (1,'joe','soap',DATE '1961-01-01','joburg','0823454321','joesoap@mail.com');
INSERT INTO investor (id,name,surname,date_of_birth,address,mobile_number,email_address) values (2,'mike','thompson',DATE '1941-02-10','joburg','0643454320','mike@mail.com');
INSERT INTO product(id,product_name,type,balance,investor_id) values(1,'SAVINGS','INVESTMENT',36000,1);
INSERT INTO product(id,product_name,type,balance,investor_id) values(2,'RETIREMENT','INVESTMENT',500000,1);
INSERT INTO product(id,product_name,type,balance,investor_id) values(3,'RETIREMENT','INVESTMENT',30000,2);
INSERT INTO product(id,product_name,type,balance,investor_id) values(4,'SAVINGS','INVESTMENT',800000,2);
