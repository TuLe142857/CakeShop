-- --------------------------------------------
--              START TRANSACTION
-- --------------------------------------------
START TRANSACTION;

CREATE DATABASE CakeShop;
USE CakeShop;

CREATE TABLE Categories (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL UNIQUE,
    description VARCHAR(512)
);

-- UPDATE: check discount between 0 and 100%; ADD CONSTRAIN image extexsion
/* @NOTE
- price: VND
- discout: % discount
- status = true: con ban, status = false: ngung kinh doand
*/
CREATE TABLE Products (
    id INT PRIMARY KEY AUTO_INCREMENT,
    category_id INT,
    name VARCHAR(255) NOT NULL UNIQUE,
    description VARCHAR(512),
    price DECIMAL(10, 2) NOT NULL,
    quantity INT NOT NULL,
    discount INT  DEFAULT 0 CHECK(discount BETWEEN 0 AND 100),
    image_url VARCHAR(255),
    status boolean DEFAULT TRUE,
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (category_id) REFERENCES Categories(id)
);
ALTER TABLE Products
	ADD CONSTRAINT image_extensions_check CHECK(
		image_url LIKE '%.png' OR
		image_url LIKE '%.jpg' OR
		image_url LIKE '%.jpeg'
	);

-- UPDATE: phone be UINIQUE
/*@Note
user login by email
*/
CREATE TABLE Users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    phone VARCHAR(15) UNIQUE,
    address VARCHAR(255),
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE Orders (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    total_amount DECIMAL(10, 2) NOT NULL,
    status ENUM('Pending', 'Confirmed', 'Shipped', 'Delivered', 'Cancelled') DEFAULT 'Pending',
    payment_method ENUM('Credit Card', 'Bank Transfer', 'Cash', 'PayPal') DEFAULT 'Cash',
    payment_status ENUM('Unpaid', 'Paid', 'Refunded') DEFAULT 'Unpaid',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES Users(id)
);


CREATE TABLE OrderItems (
    order_id INT,
    product_id INT,
    quantity INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES Orders(id),
    FOREIGN KEY (product_id) REFERENCES Products(id)
);

CREATE TABLE Reviews (
    id INT PRIMARY KEY AUTO_INCREMENT,
    product_id INT,
    user_id INT,
    rating INT CHECK (rating BETWEEN 1 AND 5),
    comment varchar(512),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (product_id) REFERENCES Products(id),
    FOREIGN KEY (user_id) REFERENCES Users(id)
);

-- UPDATE Cart -> Carts
CREATE TABLE Carts (                                    
    user_id INT,
    product_id INT,
    quantity INT NOT NULL,
    added_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES Users(id),
    FOREIGN KEY (product_id) REFERENCES Products(id)
);

-- Xóa bảng role, admin nào cũng full quyền

CREATE TABLE Admins (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);



INSERT INTO Categories (name, description) VALUES
('Bánh ngọt', 'Các loại bánh ngọt như bánh kem, bánh bông lan'),
('Bánh mặn', 'Các loại bánh mặn như bánh pizza, bánh mì kẹp thịt'),
('Bánh chay', 'Các loại bánh dành cho người ăn chay');


INSERT INTO Products (category_id, name, description, price, quantity, image_url) VALUES
(1, 'Bánh kem sô cô la', 'Bánh kem với hương vị sô cô la đậm đà', 150000, 20, 'chocolate_cake.jpg'),
(1, 'Bánh bông lan trứng muối', 'Bánh bông lan thơm ngon với trứng muối', 120000, 15, 'egg_yolk_cake.jpg'),
(2, 'Pizza hải sản', 'Pizza với các loại hải sản tươi', 200000, 10, 'seafood_pizza.jpg'),
(2, 'Bánh mì kẹp thịt', 'Bánh mì kẹp thịt bò và phô mai', 80000, 30, 'burger.jpg'),
(3, 'Bánh quy chay', 'Bánh quy giòn dành cho người ăn chay', 50000, 50, 'vegan_cookie.jpg');


INSERT INTO Users (name, email, password, phone, address) VALUES
('Nguyễn Văn A', 'vana@gmail.com', 'hashed_password_1', '0901234567', '123 Đường A, TP. HCM'),
('Trần Thị B', 'thib@gmail.com', 'hashed_password_2', '0902345678', '456 Đường B, TP. HCM'),
('Lê Văn C', 'vanc@gmail.com', 'hashed_password_3', '0903456789', '789 Đường C, TP. HCM');



INSERT INTO Orders (user_id, total_amount, status, payment_method, payment_status) VALUES
(1, 150000, 'Pending', 'Credit Card', 'Unpaid'),
(2, 200000, 'Shipped', 'Cash', 'Paid'),
(3, 80000, 'Delivered', 'Paypal', 'Paid');



INSERT INTO OrderItems (order_id, product_id, quantity, price) VALUES
(1, 1, 1, 150000),
(2, 3, 1, 200000),
(3, 4, 1, 80000);



INSERT INTO Reviews (product_id, user_id, rating, comment) VALUES
(1, 1, 5, 'Bánh rất ngon, sô cô la đậm đà!'),
(2, 2, 4, 'Trứng muối hơi mặn nhưng bánh vẫn rất ngon'),
(3, 3, 5, 'Pizza hải sản tuyệt vời!');


INSERT INTO Carts (user_id, product_id, quantity) VALUES
(1, 2, 2),
(2, 4, 1),
(3, 5, 3);

INSERT INTO Admins (name, email, password) VALUES
('Admin1', 'admin1@cakeshop.com', 'hashed_admin_password_1'),
('Editor1', 'editor1@cakeshop.com', 'hashed_editor_password_1'),
('Viewer1', 'viewer1@cakeshop.com', 'hashed_viewer_password_1');


-- --------------------------------------------
--              END TRANSACTION
-- --------------------------------------------
COMMIT;