use test;

-- STORE 테이블 생성
CREATE TABLE STORE (
                       store_id INT AUTO_INCREMENT PRIMARY KEY,
                       store_name VARCHAR(100) NOT NULL,
                       location VARCHAR(255)
);

-- PRODUCT 테이블 생성
CREATE TABLE PRODUCT (
                         product_id INT AUTO_INCREMENT PRIMARY KEY,
                         product_name VARCHAR(100) NOT NULL,
                         price DECIMAL(10, 2) NOT NULL,
                         description TEXT
);

-- SALES 테이블 생성
CREATE TABLE SALES (
                       sales_id INT AUTO_INCREMENT PRIMARY KEY,
                       store_id INT NOT NULL,
                       product_id INT NOT NULL,
                       quantity INT NOT NULL,
                       sale_date DATE NOT NULL,
                       FOREIGN KEY (store_id) REFERENCES STORE(store_id),
                       FOREIGN KEY (product_id) REFERENCES PRODUCT(product_id)
);

-- 샘플 데이터 삽입
INSERT INTO STORE (store_name, location) VALUES
                                             ('New York', 'Location A'),
                                             ('Los Angeles', 'Location B'),
                                             ('Chicago', 'Location C');

INSERT INTO PRODUCT (product_name, price, description) VALUES
                                                           ('Toy Story 3', 19.99, 'Toy Story 3 Blu-ray edition'),
                                                           ('Harry Potter and the Philosopher''s Stone', 15.49, 'First book in the Harry Potter series'),
                                                           ('iPhone 13', 999.99, 'Latest iPhone model');

INSERT INTO SALES (store_id, product_id, quantity, sale_date) VALUES
                                                                  (1, 1, 5, '2024-05-01'),
                                                                  (2, 2, 3, '2024-05-02'),
                                                                  (3, 3, 7, '2024-05-03');
