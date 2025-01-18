-- Promo Code DB

CREATE TABLE promo_codes (
     id VARCHAR(36) PRIMARY KEY,
     code VARCHAR(255) NOT NULL UNIQUE,
     qr_code VARCHAR(255) NOT NULL,
     voucher_id VARCHAR(255) NOT NULL,
     buy_type ENUM('MYSELF_ONLY', 'GIFT_TO_OTHERS'),
     status ENUM('ACTIVE', 'USED'),
     owner_phone VARCHAR(20),
     generated_date DATETIME NOT NULL,
     bought_date DATETIME
);