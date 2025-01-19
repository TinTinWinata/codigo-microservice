/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

DROP TABLE IF EXISTS `flyway_schema_history`;
CREATE TABLE `flyway_schema_history` (
  `installed_rank` int NOT NULL,
  `version` varchar(50) DEFAULT NULL,
  `description` varchar(200) NOT NULL,
  `type` varchar(20) NOT NULL,
  `script` varchar(1000) NOT NULL,
  `checksum` int DEFAULT NULL,
  `installed_by` varchar(100) NOT NULL,
  `installed_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `execution_time` int NOT NULL,
  `success` tinyint(1) NOT NULL,
  PRIMARY KEY (`installed_rank`),
  KEY `flyway_schema_history_s_idx` (`success`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `payment_methods`;
CREATE TABLE `payment_methods` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `purchase_histories`;
CREATE TABLE `purchase_histories` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_phone` varchar(15) NOT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `to_phone` varchar(15) DEFAULT NULL,
  `to_name` varchar(255) DEFAULT NULL,
  `voucher_count` int NOT NULL,
  `voucher_id` bigint NOT NULL,
  `purchase_date` datetime NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `voucher_id` (`voucher_id`),
  CONSTRAINT `purchase_histories_ibfk_1` FOREIGN KEY (`voucher_id`) REFERENCES `vouchers` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `voucher_discounts`;
CREATE TABLE `voucher_discounts` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `voucher_id` bigint NOT NULL,
  `payment_method_id` bigint NOT NULL,
  `discount` decimal(10,2) NOT NULL,
  `discount_description` varchar(255) NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `voucher_id` (`voucher_id`),
  KEY `payment_method_id` (`payment_method_id`),
  CONSTRAINT `voucher_discounts_ibfk_1` FOREIGN KEY (`voucher_id`) REFERENCES `vouchers` (`id`) ON DELETE CASCADE,
  CONSTRAINT `voucher_discounts_ibfk_2` FOREIGN KEY (`payment_method_id`) REFERENCES `payment_methods` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `vouchers`;
CREATE TABLE `vouchers` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `description` text,
  `expiry_date` date NOT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `amount` decimal(10,2) NOT NULL,
  `quantity` int NOT NULL,
  `buy_type` enum('MYSELF_ONLY','GIFT_TO_OTHERS') NOT NULL,
  `status` enum('ACTIVE','INACTIVE') NOT NULL,
  `max_buy_limit` int NOT NULL,
  `max_user_limit_from_gift` int NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `flyway_schema_history` (`installed_rank`, `version`, `description`, `type`, `script`, `checksum`, `installed_by`, `installed_on`, `execution_time`, `success`) VALUES
(1, '1', 'init', 'SQL', 'V1__init.sql', 69677563, 'root', '2025-01-19 07:42:11', 245, 1);


INSERT INTO `payment_methods` (`id`, `name`, `created_at`, `updated_at`) VALUES
(1, 'VISA', '2025-01-19 07:44:36', '2025-01-19 07:44:36');
INSERT INTO `payment_methods` (`id`, `name`, `created_at`, `updated_at`) VALUES
(2, 'MasterCard', '2025-01-19 07:44:36', '2025-01-19 07:44:36');


INSERT INTO `purchase_histories` (`id`, `user_phone`, `user_name`, `to_phone`, `to_name`, `voucher_count`, `voucher_id`, `purchase_date`, `created_at`, `updated_at`) VALUES
(1, '123', 'Justine Winata', '123', 'Justine Winata', 0, 1, '2025-01-19 14:45:19', '2025-01-19 07:45:18', '2025-01-19 07:45:18');
INSERT INTO `purchase_histories` (`id`, `user_phone`, `user_name`, `to_phone`, `to_name`, `voucher_count`, `voucher_id`, `purchase_date`, `created_at`, `updated_at`) VALUES
(2, '123', 'Justine Winata', '123', 'Justine Winata', 0, 1, '2025-01-19 14:45:20', '2025-01-19 07:45:20', '2025-01-19 07:45:20');


INSERT INTO `voucher_discounts` (`id`, `voucher_id`, `payment_method_id`, `discount`, `discount_description`, `created_at`, `updated_at`) VALUES
(2, 1, 1, '50.00', '50% Off', '2025-01-19 07:44:38', '2025-01-19 07:44:38');


INSERT INTO `vouchers` (`id`, `title`, `description`, `expiry_date`, `image_url`, `amount`, `quantity`, `buy_type`, `status`, `max_buy_limit`, `max_user_limit_from_gift`, `created_at`, `updated_at`) VALUES
(1, 'Updated Holiday Discount', 'Get 20% off on your next purchase', '2025-12-31', 'https://picsum.photos/200', '50.00', 3, 'MYSELF_ONLY', 'ACTIVE', 3, 3, '2025-01-19 07:44:21', '2025-01-19 07:45:20');



/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;