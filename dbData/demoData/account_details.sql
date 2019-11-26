-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Apr 08, 2018 at 08:44 PM
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
-- Table structure for table `account_details`
--

DROP TABLE IF EXISTS `account_details`;
CREATE TABLE IF NOT EXISTS `account_details` (
  `account_id` varchar(40) NOT NULL,
  `user_id` int(10) NOT NULL,
  `name` varchar(64) NOT NULL,
  `official_name` varchar(128) NOT NULL,
  `type` varchar(64) DEFAULT NULL,
  `subtype` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`account_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `account_details`
--

INSERT INTO `account_details` (`account_id`, `user_id`, `name`, `official_name`, `type`, `subtype`) VALUES
('4yDP9eJjmkCk1mD77NQvs70bRJxZwvt1eKvo5', 1, 'TCF Power Savings', 'TCF Power Savings', 'depository', 'savings'),
('PVmE0vjdkytrMpgzzwdntjenD4oQnxCoyy7VE', 1, 'TCF Free Checking', 'TCF Free Checking', 'depository', 'checking'),
('re35qdPxkYHXAdLoox6niL4Oao6YwocjdP9eLo', 1, 'TCF Free Checking', 'TCF Free Checking', 'depository', 'checking');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
