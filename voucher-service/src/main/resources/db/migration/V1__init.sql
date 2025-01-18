-- Creating the vouchers table
CREATE TABLE vouchers (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    expiry_date DATE NOT NULL,
    image_url VARCHAR(255),
    amount DECIMAL(10, 2) NOT NULL,
    quantity INT NOT NULL,
    owner_phone VARCHAR(15),
    buy_type ENUM('MYSELF_ONLY', 'GIFT_TO_OTHERS') NOT NULL,
    status ENUM('ACTIVE', 'INACTIVE') NOT NULL,
    max_buy_limit INT NOT NULL,
    max_user_limit_from_gift INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Creating the payment_methods table
CREATE TABLE payment_methods (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Creating the voucher_discounts table
CREATE TABLE voucher_discounts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    voucher_id BIGINT NOT NULL,
    payment_method_id BIGINT NOT NULL,
    discount DECIMAL(10, 2) NOT NULL,
    discount_description VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (voucher_id) REFERENCES vouchers(id) ON DELETE CASCADE,
    FOREIGN KEY (payment_method_id) REFERENCES payment_methods(id) ON DELETE CASCADE
);

-- Creating the purchase_histories table
CREATE TABLE purchase_histories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_phone VARCHAR(15) NOT NULL,
    user_name VARCHAR(255),
    to_phone VARCHAR(15),
    to_name VARCHAR(255),
    voucher_count INT NOT NULL,
    voucher_id BIGINT NOT NULL,
    purchase_date DATETIME NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (voucher_id) REFERENCES vouchers(id) ON DELETE CASCADE
);
