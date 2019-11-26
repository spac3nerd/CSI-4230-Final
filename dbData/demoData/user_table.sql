-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Apr 08, 2018 at 08:43 PM
-- Server version: 5.7.19
-- PHP Version: 5.6.31

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `csi5510`
--

-- --------------------------------------------------------

--
-- Table structure for table `user_table`
--

DROP TABLE IF EXISTS `user_table`;
CREATE TABLE IF NOT EXISTS `user_table` (
  `user_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_email` varchar(50) NOT NULL,
  `password` varchar(70) NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `user_table`
--

INSERT INTO `user_table` (`user_id`, `user_email`, `password`) VALUES
(1, 'test@test.com', '$2a$10$2pue8UkToY/dqotJLO3ivOFrHjQ09bsRgBTG2ZFxGxC/dK7JSuCaC'),
(2, 'test@test2', '$2a$10$2pue8UkToY/dqotJLO3ivOudGL42FWmYArwcCHKsHA8Vjqnc2OrKu'),
(3, 'abc', '$2a$10$2pue8UkToY/dqotJLO3ivOty2ETXi00FMNR21Kc8c07aoPB9A.tf6'),
(4, 'test', '$2a$10$2pue8UkToY/dqotJLO3ivOhY/3G/PRyJzjC1R8ZpbNP6z98VkAm36'),
(5, 'aaaaaaaa', '$2a$10$2pue8UkToY/dqotJLO3ivOty2ETXi00FMNR21Kc8c07aoPB9A.tf6'),
(6, 'nicholas.rafalski@gmail.com', '$2a$10$2pue8UkToY/dqotJLO3ivOty2ETXi00FMNR21Kc8c07aoPB9A.tf6'),
(7, '', '$2a$10$2pue8UkToY/dqotJLO3ivOcAKa3QgyanjS/nykWZeiU3EpSobTi2m'),
(8, '123', '$2a$10$2pue8UkToY/dqotJLO3ivOGkJtonZG7Zo4fZ8Ht8PtV205MM0ahTG'),
(9, 'nick@nick', '$2a$10$2pue8UkToY/dqotJLO3ivOtVRAi5qWpAQrZJV1gLP9jz68IcooFwS'),
(10, 'asdfasdf', '$2a$10$2pue8UkToY/dqotJLO3ivO0QXEv1y4mcKa28zokFFuoMfOAPb9Lze'),
(11, '1', '$2a$10$2pue8UkToY/dqotJLO3ivOD8vGjesjKkB0QTsLmfMH/PLcPK.qCvK'),
(12, 'michelle@not_real.com', '$2a$10$2pue8UkToY/dqotJLO3ivOhY/3G/PRyJzjC1R8ZpbNP6z98VkAm36'),
(13, '123123123aaaaa', '$2a$10$2pue8UkToY/dqotJLO3ivOmC5LyttSfqTDAZyDt/Hwon1puek4IXq'),
(14, 'abc12345@lll', '$2a$10$2pue8UkToY/dqotJLO3ivOuifxE9mRgFzRWI.VbDQVNdZPQzwB2FW'),
(15, 'asdfasdfasdf', '$2a$10$2pue8UkToY/dqotJLO3ivOYPlbumwcPC6JZk4FACnW1BfoDhfTOLu'),
(16, 'crafalski@yahoo.com', '$2a$10$2pue8UkToY/dqotJLO3ivOrWpnK/cvbjRNR3iI.nSnSh0P3m8Lm6.'),
(17, 'asdasdf', '$2a$10$2pue8UkToY/dqotJLO3ivOIKIJhdRuOFCbMxIHQY0iQ9Pg3QE44BC'),
(18, 'test2@test.com', '$2a$10$2pue8UkToY/dqotJLO3ivOwqZ77hjHGA9ZngJS2UhI0.n6KhRWWWy');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
