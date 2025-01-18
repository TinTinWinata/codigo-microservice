-- Creating the vouchers table
CREATE TABLE vouchers (
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY, -- Fixed PRIMARY and NOT NULL order
    title VARCHAR(255) NOT NULL,
    description VARCHAR(1000),
    expiry_date DATE NOT NULL,
    image_url VARCHAR(255),
    amount DECIMAL(10, 2) NOT NULL,
    quantity INT NOT NULL,
    owner_phone VARCHAR(15),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Creating the payment_methods table
CREATE TABLE payment_methods (
     id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY, -- Fixed PRIMARY and NOT NULL order
     name VARCHAR(255) NOT NULL UNIQUE,
     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
     updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Creating the voucher_discounts table
CREATE TABLE voucher_discounts (
   id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY, -- Fixed PRIMARY and NOT NULL order
   voucher_id INT UNSIGNED NOT NULL, -- Changed from BINARY(16) to INT UNSIGNED
   payment_method_id INT UNSIGNED NOT NULL, -- Changed from BINARY(16) to INT UNSIGNED
   discount DECIMAL(10, 2) NOT NULL,
   discount_description VARCHAR(255) NOT NULL,
   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   FOREIGN KEY (voucher_id) REFERENCES vouchers(id) ON DELETE CASCADE,
   FOREIGN KEY (payment_method_id) REFERENCES payment_methods(id) ON DELETE CASCADE
);

-- Creating the purchase_histories table
CREATE TABLE purchase_histories (
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY, -- Fixed PRIMARY and NOT NULL order
    user_phone VARCHAR(15) NOT NULL,
    voucher_id INT UNSIGNED NOT NULL, -- Changed from BINARY(16) to INT UNSIGNED
    purchase_date DATETIME NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (voucher_id) REFERENCES vouchers(id) ON DELETE CASCADE
);
