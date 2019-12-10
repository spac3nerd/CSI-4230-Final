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
-- Table structure for table `account_balances`
--

DROP TABLE IF EXISTS `account_balances`;
CREATE TABLE IF NOT EXISTS `account_balances` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account_id` varchar(40) NOT NULL,
  `date` date NOT NULL,
  `available` float DEFAULT NULL,
  `current` float DEFAULT NULL,
  `credit_limit` float DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=533 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `account_balances`
--

INSERT INTO `account_balances` (`id`, `account_id`, `date`, `available`, `current`, `credit_limit`) VALUES
(532, '4yDP9eJjmkCk1mD77NQvs70bRJxZwvt1eKvo5', '2019-08-06', 1625, 1625, NULL),
(531, 'PVmE0vjdkytrMpgzzwdntjenD4oQnxCoyy7VE', '2019-08-06', 6600, 6600, NULL),
(530, '4yDP9eJjmkCk1mD77NQvs70bRJxZwvt1eKvo5', '2019-10-03', 1650.7, 1650.7, NULL),
(529, 'PVmE0vjdkytrMpgzzwdntjenD4oQnxCoyy7VE', '2019-10-03', 7519.48, 7519.48, NULL),
(528, 're35qdPxkYHXAdLoox6niL4Oao6YwocjdP9eLo', '2019-10-03', 13736.6, 13736.6, NULL),
(527, 're35qdPxkYHXAdLoox6niL4Oao6YwocjdP9eLo', '2019-08-06', 15000, 15000, NULL),
(526, '4yDP9eJjmkCk1mD77NQvs70bRJxZwvt1eKvo5', '2019-10-03', 1650.7, 1650.7, NULL),
(525, 'PVmE0vjdkytrMpgzzwdntjenD4oQnxCoyy7VE', '2019-10-03', 7620.11, 7620.11, NULL),
(524, 're35qdPxkYHXAdLoox6niL4Oao6YwocjdP9eLo', '2019-10-03', 13736.6, 13736.6, NULL),
(523, '4yDP9eJjmkCk1mD77NQvs70bRJxZwvt1eKvo5', '2019-10-03', 1650.7, 1650.7, NULL),
(522, 'PVmE0vjdkytrMpgzzwdntjenD4oQnxCoyy7VE', '2019-10-03', 7620.11, 7620.11, NULL),
(521, 're35qdPxkYHXAdLoox6niL4Oao6YwocjdP9eLo', '2019-10-03', 13736.6, 13736.6, NULL),
(520, '4yDP9eJjmkCk1mD77NQvs70bRJxZwvt1eKvo5', '2019-09-06', 1650.7, 1650.7, NULL),
(519, 'PVmE0vjdkytrMpgzzwdntjenD4oQnxCoyy7VE', '2019-09-06', 7620.11, 7620.11, NULL),
(518, 're35qdPxkYHXAdLoox6niL4Oao6YwocjdP9eLo', '2019-09-06', 13736.6, 13736.6, NULL),
(517, '4yDP9eJjmkCk1mD77NQvs70bRJxZwvt1eKvo5', '2019-09-06', 1650.7, 1650.7, NULL),
(516, 'PVmE0vjdkytrMpgzzwdntjenD4oQnxCoyy7VE', '2019-09-06', 7620.11, 7620.11, NULL),
(515, 're35qdPxkYHXAdLoox6niL4Oao6YwocjdP9eLo', '2019-09-06', 13736.6, 13736.6, NULL),
(514, '4yDP9eJjmkCk1mD77NQvs70bRJxZwvt1eKvo5', '2019-09-06', 1650.7, 1650.7, NULL),
(513, 'PVmE0vjdkytrMpgzzwdntjenD4oQnxCoyy7VE', '2019-09-06', 7620.11, 7620.11, NULL),
(512, 're35qdPxkYHXAdLoox6niL4Oao6YwocjdP9eLo', '2019-09-06', 13736.6, 13736.6, NULL),
(511, '4yDP9eJjmkCk1mD77NQvs70bRJxZwvt1eKvo5', '2019-09-06', 1650.7, 1650.7, NULL),
(510, 'PVmE0vjdkytrMpgzzwdntjenD4oQnxCoyy7VE', '2019-09-06', 7620.11, 7620.11, NULL),
(509, 're35qdPxkYHXAdLoox6niL4Oao6YwocjdP9eLo', '2019-09-06', 13736.6, 13736.6, NULL),
(508, '4yDP9eJjmkCk1mD77NQvs70bRJxZwvt1eKvo5', '2019-09-06', 1650.7, 1650.7, NULL),
(507, 'PVmE0vjdkytrMpgzzwdntjenD4oQnxCoyy7VE', '2019-09-06', 7620.11, 7620.11, NULL),
(506, 're35qdPxkYHXAdLoox6niL4Oao6YwocjdP9eLo', '2019-09-06', 13736.6, 13736.6, NULL),
(505, '4yDP9eJjmkCk1mD77NQvs70bRJxZwvt1eKvo5', '2019-09-06', 1650.7, 1650.7, NULL),
(504, 'PVmE0vjdkytrMpgzzwdntjenD4oQnxCoyy7VE', '2019-09-06', 7620.11, 7620.11, NULL),
(503, 're35qdPxkYHXAdLoox6niL4Oao6YwocjdP9eLo', '2019-09-06', 13736.6, 13736.6, NULL),
(502, '4yDP9eJjmkCk1mD77NQvs70bRJxZwvt1eKvo5', '2019-09-06', 1650.7, 1650.7, NULL),
(501, 'PVmE0vjdkytrMpgzzwdntjenD4oQnxCoyy7VE', '2019-09-06', 7620.11, 7620.11, NULL),
(500, 're35qdPxkYHXAdLoox6niL4Oao6YwocjdP9eLo', '2019-09-06', 13780.3, 13780.3, NULL),
(499, '4yDP9eJjmkCk1mD77NQvs70bRJxZwvt1eKvo5', '2019-09-06', 1650.7, 1650.7, NULL),
(498, 'PVmE0vjdkytrMpgzzwdntjenD4oQnxCoyy7VE', '2019-09-06', 7620.11, 7620.11, NULL),
(497, 're35qdPxkYHXAdLoox6niL4Oao6YwocjdP9eLo', '2019-09-06', 13780.3, 13780.3, NULL),
(496, '4yDP9eJjmkCk1mD77NQvs70bRJxZwvt1eKvo5', '2019-09-06', 1650.7, 1650.7, NULL),
(495, 'PVmE0vjdkytrMpgzzwdntjenD4oQnxCoyy7VE', '2019-09-06', 7620.11, 7620.11, NULL),
(494, 're35qdPxkYHXAdLoox6niL4Oao6YwocjdP9eLo', '2019-09-06', 13780.3, 13780.3, NULL),
(493, '4yDP9eJjmkCk1mD77NQvs70bRJxZwvt1eKvo5', '2019-09-06', 1650.7, 1650.7, NULL),
(492, 'PVmE0vjdkytrMpgzzwdntjenD4oQnxCoyy7VE', '2019-09-06', 7620.11, 7620.11, NULL),
(491, 're35qdPxkYHXAdLoox6niL4Oao6YwocjdP9eLo', '2019-09-06', 13780.3, 13780.3, NULL),
(490, '4yDP9eJjmkCk1mD77NQvs70bRJxZwvt1eKvo5', '2019-09-06', 1650.7, 1650.7, NULL),
(489, 'PVmE0vjdkytrMpgzzwdntjenD4oQnxCoyy7VE', '2019-09-06', 7620.11, 7620.11, NULL),
(488, 're35qdPxkYHXAdLoox6niL4Oao6YwocjdP9eLo', '2019-09-06', 13780.3, 13780.3, NULL),
(487, '4yDP9eJjmkCk1mD77NQvs70bRJxZwvt1eKvo5', '2019-09-06', 1650.7, 1650.7, NULL),
(486, 'PVmE0vjdkytrMpgzzwdntjenD4oQnxCoyy7VE', '2019-09-06', 7620.11, 7620.11, NULL),
(483, 'PVmE0vjdkytrMpgzzwdntjenD4oQnxCoyy7VE', '2019-09-06', 7620.11, 7620.11, NULL),
(484, '4yDP9eJjmkCk1mD77NQvs70bRJxZwvt1eKvo5', '2019-09-06', 1650.7, 1650.7, NULL),
(485, 're35qdPxkYHXAdLoox6niL4Oao6YwocjdP9eLo', '2019-09-06', 13780.3, 13780.3, NULL),
(480, 'PVmE0vjdkytrMpgzzwdntjenD4oQnxCoyy7VE', '2019-09-06', 7620.11, 7620.11, NULL),
(481, '4yDP9eJjmkCk1mD77NQvs70bRJxZwvt1eKvo5', '2019-09-06', 1650.7, 1650.7, NULL),
(482, 're35qdPxkYHXAdLoox6niL4Oao6YwocjdP9eLo', '2019-09-06', 13780.3, 13780.3, NULL),
(477, 'PVmE0vjdkytrMpgzzwdntjenD4oQnxCoyy7VE', '2019-09-06', 7620.11, 7620.11, NULL),
(478, '4yDP9eJjmkCk1mD77NQvs70bRJxZwvt1eKvo5', '2019-09-06', 1650.7, 1650.7, NULL),
(479, 're35qdPxkYHXAdLoox6niL4Oao6YwocjdP9eLo', '2019-09-06', 13780.3, 13780.3, NULL),
(476, 're35qdPxkYHXAdLoox6niL4Oao6YwocjdP9eLo', '2019-09-06', 13780.3, 13780.3, NULL);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
