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

DROP TABLE IF EXISTS `promo_codes`;
CREATE TABLE `promo_codes` (
  `id` varchar(36) NOT NULL,
  `code` varchar(255) NOT NULL,
  `qr_code` varchar(255) NOT NULL,
  `voucher_id` varchar(255) NOT NULL,
  `status` enum('ACTIVE','USED') DEFAULT NULL,
  `owner_phone` varchar(20) DEFAULT NULL,
  `generated_date` datetime NOT NULL,
  `bought_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `flyway_schema_history` (`installed_rank`, `version`, `description`, `type`, `script`, `checksum`, `installed_by`, `installed_on`, `execution_time`, `success`) VALUES
(1, '1', 'init', 'SQL', 'V1__init.sql', 1906318545, 'root', '2025-01-18 19:52:12', 68, 1);


INSERT INTO `promo_codes` (`id`, `code`, `qr_code`, `voucher_id`, `status`, `owner_phone`, `generated_date`, `bought_date`) VALUES
('1cb5e828-46b4-498f-8812-6d4e4caf7731', '014335ZCXQI', 'qr-014335ZCXQI.png', '2', 'ACTIVE', '123', '2025-01-19 08:42:23', '2025-01-19 08:43:08');
INSERT INTO `promo_codes` (`id`, `code`, `qr_code`, `voucher_id`, `status`, `owner_phone`, `generated_date`, `bought_date`) VALUES
('25d4225d-90b8-489b-9bad-22d3082b6c2d', '701561GJWBJ', 'qr-701561GJWBJ.png', '2', 'ACTIVE', NULL, '2025-01-19 08:51:19', NULL);
INSERT INTO `promo_codes` (`id`, `code`, `qr_code`, `voucher_id`, `status`, `owner_phone`, `generated_date`, `bought_date`) VALUES
('303fbc78-a082-4846-9192-45e97140ccc1', '791723IAOHS', 'qr-791723IAOHS.png', '2', 'ACTIVE', NULL, '2025-01-19 12:45:17', NULL);
INSERT INTO `promo_codes` (`id`, `code`, `qr_code`, `voucher_id`, `status`, `owner_phone`, `generated_date`, `bought_date`) VALUES
('3381152f-8d39-4e83-940c-1034be34a4df', '337949XRPDA', 'qr-337949XRPDA.png', '1', 'ACTIVE', '5123', '2025-01-19 08:50:38', '2025-01-19 13:35:44'),
('40ee5408-afd8-4e6d-9330-b36c7a75c5cc', '492199DUVTI', 'qr-492199DUVTI.png', '1', 'ACTIVE', '123', '2025-01-19 08:50:38', '2025-01-19 14:45:18'),
('4d8a1dda-9e66-4547-a2e1-09a12e16506d', '919748OIXDW', 'qr-919748OIXDW.png', '2', 'USED', '123', '2025-01-19 08:42:23', '2025-01-19 08:44:17'),
('5b5d26af-29e3-41fe-b877-7a319765a0e3', '042824FPISM', 'qr-042824FPISM.png', '1', 'ACTIVE', '123', '2025-01-19 14:44:23', '2025-01-19 14:45:20'),
('60df5ce2-8c9f-4d9a-a304-401c6528dbe2', '102596BANYP', 'qr-102596BANYP.png', '2', 'ACTIVE', NULL, '2025-01-19 08:42:23', NULL),
('67938af4-8baf-4862-a77f-963ba0123f77', '417063KDBMQ', 'qr-417063KDBMQ.png', '1', 'ACTIVE', NULL, '2025-01-19 12:44:39', NULL),
('6e1ed60b-0b88-4ce3-a7d0-4265d662e4e0', '685733OUOHA', 'qr-685733OUOHA.png', '1', 'ACTIVE', NULL, '2025-01-19 12:44:39', NULL),
('7dc60795-44fa-413a-af22-51bf6f1a1d7e', '883119MOCLQ', 'qr-883119MOCLQ.png', '1', 'ACTIVE', '087878766892', '2025-01-19 03:08:29', '2025-01-19 08:35:06'),
('883f4371-b790-4ae8-bae0-7835cf2f5368', '489795RFFSZ', 'qr-489795RFFSZ.png', '1', 'ACTIVE', NULL, '2025-01-19 12:44:39', NULL),
('921e3d35-f11f-4c06-9bef-0286e3733994', '967787DTXMA', 'qr-967787DTXMA.png', '1', 'ACTIVE', '123', '2025-01-19 03:08:29', '2025-01-19 08:37:06'),
('9a3f3c53-c262-4656-bf5f-7fc205035c69', '422996XJUGB', 'qr-422996XJUGB.png', '1', 'USED', '123', '2025-01-19 03:08:25', '2025-01-19 08:37:37'),
('b3f4fa60-072f-4d68-be12-2c7341a85588', '673954MMQRR', 'qr-673954MMQRR.png', '2', 'ACTIVE', NULL, '2025-01-19 12:45:17', NULL),
('be325644-6809-49e8-abd7-0c95c3692fb4', '260864IMFKZ', 'qr-260864IMFKZ.png', '1', 'ACTIVE', NULL, '2025-01-19 08:50:38', NULL),
('d2aa7649-bcf6-4a01-b0f8-5ad9feef381a', '946546RVGZK', 'qr-946546RVGZK.png', '1', 'ACTIVE', NULL, '2025-01-19 14:44:23', NULL),
('d5dc2b2e-7fe1-4dff-8223-6795f32b84b9', '722724OSRUE', 'qr-722724OSRUE.png', '2', 'ACTIVE', NULL, '2025-01-19 12:45:17', NULL),
('d9a2741f-0ab9-45ce-a958-e3c31cfea216', '467383YHGCZ', 'qr-467383YHGCZ.png', '2', 'ACTIVE', NULL, '2025-01-19 08:51:18', NULL),
('da61820d-4d8b-4a47-a80c-5a38b8f1cb0d', '074514DFFEB', 'qr-074514DFFEB.png', '1', 'ACTIVE', NULL, '2025-01-19 14:44:23', NULL),
('fa7940f3-fbf5-4497-810d-8443556a7337', '481059HAJZJ', 'qr-481059HAJZJ.png', '2', 'ACTIVE', NULL, '2025-01-19 08:51:19', NULL);


/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;