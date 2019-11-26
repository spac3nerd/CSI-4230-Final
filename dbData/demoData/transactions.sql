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
-- Table structure for table `transactions`
--

DROP TABLE IF EXISTS `transactions`;
CREATE TABLE IF NOT EXISTS `transactions` (
  `transaction_id` varchar(40) NOT NULL,
  `user_id` int(11) NOT NULL,
  `account_id` varchar(40) NOT NULL,
  `date` date NOT NULL,
  `category_primary` varchar(64) DEFAULT NULL,
  `category_secondary` varchar(64) DEFAULT NULL,
  `amount` float NOT NULL,
  `name` varchar(128) NOT NULL,
  `location` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`transaction_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `transactions`
--

INSERT INTO `transactions` (`transaction_id`, `user_id`, `account_id`, `date`, `category_primary`, `category_secondary`, `amount`, `name`, `location`) VALUES
('ee9rn7xgEQHnogRaa80RS1RVLAeXVgubKVA5V', 1, 'PVmE0vjdkytrMpgzzwdntjenD4oQnxCoyy7VE', '2018-04-08', 'Shops', 'Supermarkets and Groceries', 100.63, 'Kroger', NULL),
('qeBOPE1jmNHqXx6NNw9ds65Amx00VEUqERydZ', 1, 'PVmE0vjdkytrMpgzzwdntjenD4oQnxCoyy7VE', '2018-03-08', 'Service', 'null', 135.5, 'G2GCHARGE.COM', NULL),
('5K1qVOr5aAcVPpxqq9R0TqdypXOOxqImEyjBLK', 1, 're35qdPxkYHXAdLoox6niL4Oao6YwocjdP9eLo', '2018-03-08', 'Shops', 'Supermarkets and Groceries', 18.15, 'Kroger', NULL),
('5K1qVOr5aAcVPpxqq9R0T4xavXjAAJfBQb5Ep', 1, 're35qdPxkYHXAdLoox6niL4Oao6YwocjdP9eLo', '2018-04-07', 'Food and Drink', 'Restaurants', 43.73, 'CLUBHOUSE BFD ROCHESTER HILMIUS', NULL),
('5K1qVOr5aAcVPpxqq9R0TqdypXOOEgHd5807Q', 1, 're35qdPxkYHXAdLoox6niL4Oao6YwocjdP9eLo', '2018-03-12', 'Food and Drink', 'Restaurants', 48.49, 'WOODWARD IMPERIAL', NULL),
('meDR67waqQHmRn5EEVYvcxzy4nddn5FEz0w1BE', 1, 'PVmE0vjdkytrMpgzzwdntjenD4oQnxCoyy7VE', '2018-03-12', 'Travel', 'Gas Stations', 33.41, 'Shell', NULL),
('33L17bRxrKcKEMw99dNAi9mXyVddVAsMnEVyxv', 1, 'PVmE0vjdkytrMpgzzwdntjenD4oQnxCoyy7VE', '2018-03-12', 'Shops', 'Supermarkets and Groceries', 82.74, 'Kroger', NULL),
('denXQmxAMzHvpgEPPJ5DSV5dngNNOQSbge4J9', 1, 're35qdPxkYHXAdLoox6niL4Oao6YwocjdP9eLo', '2018-03-12', 'Community', 'Government Departments and Agencies', 0.75, 'CITY OF FERNDALE P', NULL),
('qeBOPE1jmNHqXx6NNw9ds65Amx00A5sJKLb9O', 1, 'PVmE0vjdkytrMpgzzwdntjenD4oQnxCoyy7VE', '2018-03-13', 'Payment', 'null', 222.97, 'ALLY ALLY PAYMT', NULL),
('ZVp4omJe8xtDmY133KJvi95Rr0QQ0mFpx67mE', 1, 're35qdPxkYHXAdLoox6niL4Oao6YwocjdP9eLo', '2018-03-14', 'Shops', 'Supermarkets and Groceries', 16.03, 'Kroger', NULL),
('RE87rdj6vkUa7xo00Lb8S0pKYkyyOaHojQj0r', 1, 're35qdPxkYHXAdLoox6niL4Oao6YwocjdP9eLo', '2018-03-16', 'Transfer', 'Payroll', -1675.5, 'M5603ORION ADVAN PAYROLL', NULL),
('QVbyj05wAKt41opDD6xRTkpEymLLmLSo7pgqVK', 1, 're35qdPxkYHXAdLoox6niL4Oao6YwocjdP9eLo', '2018-03-16', 'Food and Drink', 'Restaurants', 8.32, 'Chipotle Mexican Grill', NULL),
('denXQmxAMzHvpgEPPJ5DSV5dngNNEYtozaZrJ', 1, 'PVmE0vjdkytrMpgzzwdntjenD4oQnxCoyy7VE', '2018-03-19', 'Travel', 'Gas Stations', 31.96, 'Shell', NULL),
('8vBgE5dR8jfj3KVNN6k4UKMoxwppwVcxE7Y8p', 1, 're35qdPxkYHXAdLoox6niL4Oao6YwocjdP9eLo', '2018-03-19', 'Shops', 'Supermarkets and Groceries', 44.93, 'Kroger', NULL),
('6kPZO4rzxvtvqAwddNrjSd1nyxZZnKIpxD4LM', 1, 'PVmE0vjdkytrMpgzzwdntjenD4oQnxCoyy7VE', '2018-03-19', 'Shops', 'Supermarkets and Groceries', 6.78, 'Kroger', NULL),
('beZrER91V7Hm5Pz88aLjc3md4PNNPYtxNVXzO', 1, 'PVmE0vjdkytrMpgzzwdntjenD4oQnxCoyy7VE', '2018-03-26', 'Shops', 'Clothing and Accessories', 48.76, 'POS FRANCESCA S B151 160 N', NULL),
('33L17bRxrKcKEMw99dNAi9mXyVddYPHPdaVJj', 1, 're35qdPxkYHXAdLoox6niL4Oao6YwocjdP9eLo', '2018-03-19', 'Food and Drink', 'Restaurants', 23.01, 'HUNGRY HOWIES 21', NULL),
('beZrER91V7Hm5Pz88aLjc3md4PNNPJcqK7O1X', 1, 're35qdPxkYHXAdLoox6niL4Oao6YwocjdP9eLo', '2018-03-22', 'Shops', 'Supermarkets and Groceries', 16.45, 'Kroger', NULL),
('meDR67waqQHmRn5EEVYvcxzy4nddgOUMqRb0m', 1, 'PVmE0vjdkytrMpgzzwdntjenD4oQnxCoyy7VE', '2018-03-22', 'Service', 'Personal Care', 13, 'JR S FOR HAIR AND', NULL),
('meDR67waqQHmRn5EEVYvcxzy4nddn8irZD1jJ', 1, 'PVmE0vjdkytrMpgzzwdntjenD4oQnxCoyy7VE', '2018-03-26', 'Shops', 'Supermarkets and Groceries', 72.98, 'Kroger', NULL),
('ap7rM0n19gFK5akdd4JqirV5dgPPRrfym799e', 1, 're35qdPxkYHXAdLoox6niL4Oao6YwocjdP9eLo', '2018-03-26', 'Food and Drink', 'Restaurants', 79.84, 'CLUBHOUSE BFD', NULL),
('veAkKDymjXHY8Lq445O1uqE4kL33LKFPa61ryd', 1, 'PVmE0vjdkytrMpgzzwdntjenD4oQnxCoyy7VE', '2018-03-26', 'Food and Drink', 'Restaurants', 17.91, 'BLAZE PIZZA #1218-', NULL),
('MY89kKZr3qsmr8RVVqjBcby50XvvNkcgJJmLbK', 1, 'PVmE0vjdkytrMpgzzwdntjenD4oQnxCoyy7VE', '2018-03-27', 'Travel', 'Gas Stations', 33.32, 'ExxonMobil', NULL),
('YVZ45qj9JatzpdDPPy13hdzQXZrrZmCzv50Mk', 1, 're35qdPxkYHXAdLoox6niL4Oao6YwocjdP9eLo', '2018-03-27', 'Transfer', 'Withdrawal', 200, 'ATM TCF ROCHESTER HILLS', NULL),
('RE87rdj6vkUa7xo00Lb8S0pKYkyykVCBQgaR7', 1, 're35qdPxkYHXAdLoox6niL4Oao6YwocjdP9eLo', '2018-03-28', 'Payment', 'Rent', 804.05, 'ROCHESTER PLACE RENT', NULL),
('ZVp4omJe8xtDmY133KJvi95Rr0QQ7YcLAjP9y', 1, 're35qdPxkYHXAdLoox6niL4Oao6YwocjdP9eLo', '2018-03-28', 'Shops', 'Supermarkets and Groceries', 16.03, 'Kroger', NULL),
('7Aq3M4mNJ0I0MoXOO3xrIOvbx5jjwdSZKQ0zAV', 1, 're35qdPxkYHXAdLoox6niL4Oao6YwocjdP9eLo', '2018-03-28', 'Service', 'Financial', 27.84, 'MSU FEDERAL CU ACHOrigWEB', NULL),
('ap7rM0n19gFK5akdd4JqirV5dgPPKrt9ZAQk4', 1, 're35qdPxkYHXAdLoox6niL4Oao6YwocjdP9eLo', '2018-03-28', 'Service', 'Utilities', 54.67, 'DTE ENERGY 800-477', NULL),
('PVmE0vjdkytrMpgzzwdntjemQayyx3h5Qeo7Xk', 1, 're35qdPxkYHXAdLoox6niL4Oao6YwocjdP9eLo', '2018-03-28', 'Service', 'Telecommunication Services', 84.31, 'AT&T', NULL),
('LV87KyE0BPtAmRjeegzET3p0QLqq6kfR06Oo1', 1, 're35qdPxkYHXAdLoox6niL4Oao6YwocjdP9eLo', '2018-03-29', 'Recreation', 'Arts and Entertainment', 42, 'DETROIT HOCKEY- CO', NULL),
('QVbyj05wAKt41opDD6xRTkpEymLLwbijx1YxV', 1, 're35qdPxkYHXAdLoox6niL4Oao6YwocjdP9eLo', '2018-03-30', 'Transfer', 'Payroll', -1675.5, 'M5603ORION ADVAN PAYROLL', NULL),
('0Pd6oRrbvBtVwBDbbZ5mTb3EoAPPeEHrbELB3', 1, '4yDP9eJjmkCk1mD77NQvs70bRJxZwvt1eKvo5', '2018-03-30', 'Interest', 'Interest Earned', -0.04, 'INTEREST PAID', NULL),
('PVmE0vjdkytrMpgzzwdntjemQayyxAH5nzAbYm', 1, 'PVmE0vjdkytrMpgzzwdntjenD4oQnxCoyy7VE', '2018-04-02', 'Shops', 'Pharmacies', 3.17, 'Rite Aid', NULL),
('Db85L6knd7F1J4Xoo0MOTEw3AXQQXnho4dNzA', 1, 'PVmE0vjdkytrMpgzzwdntjenD4oQnxCoyy7VE', '2018-04-02', 'Food and Drink', 'Restaurants', 13.22, 'PAPA JOES OAKLAND', NULL),
('EgnXDLN1w9iP8a4qqpgYuJgvV8338jcZ13PKd', 1, 'PVmE0vjdkytrMpgzzwdntjenD4oQnxCoyy7VE', '2018-04-02', 'Shops', 'Supermarkets and Groceries', 65.81, 'Kroger', NULL),
('EgnXDLN1w9iP8a4qqpgYuJgvV833vVu6R665o', 1, 'PVmE0vjdkytrMpgzzwdntjenD4oQnxCoyy7VE', '2018-04-02', 'Food and Drink', 'Restaurants', 14.9, 'GREEN LANTERN PIZZ', NULL),
('yej5vqX3aQHOPkyVVE5BSyo1BkNN1bSpzzoQw', 1, 're35qdPxkYHXAdLoox6niL4Oao6YwocjdP9eLo', '2018-04-02', 'Food and Drink', 'Restaurants', 8.47, 'Burger King', NULL),
('peqo17D0ynH0jxwzzPk6IwEkgxjj0LTQBzP8N', 1, 're35qdPxkYHXAdLoox6niL4Oao6YwocjdP9eLo', '2018-04-02', 'Transfer', 'Debit', 25, 'AUTO TFR TO ******7417', NULL),
('1qwLbR4Z03IV68RggzLKTg4omy55y9Ir3ANyky', 1, '4yDP9eJjmkCk1mD77NQvs70bRJxZwvt1eKvo5', '2018-04-02', 'Transfer', 'Credit', -25, 'AUTO TFR FROM ******3170', NULL),
('re35qdPxkYHXAdLoox6niL4vYdqqJ7fjNopXKw', 1, 'PVmE0vjdkytrMpgzzwdntjenD4oQnxCoyy7VE', '2018-04-03', 'Travel', 'Gas Stations', 33.32, 'SPEEDWAY 08832 ROC', NULL),
('meDR67waqQHmRn5EEVYvcxzy4nddq8cJq10Xx', 1, 'PVmE0vjdkytrMpgzzwdntjenD4oQnxCoyy7VE', '2018-04-03', 'Service', 'Financial', 1000, 'PERSHING BROKERAGE', NULL),
('peqo17D0ynH0jxwzzPk6IwEkgxjj78cL1k3Qz6', 1, 're35qdPxkYHXAdLoox6niL4Oao6YwocjdP9eLo', '2018-04-06', 'Recreation', 'Sports Clubs', 45, 'USA HOCKEY COLORADO SPRGCOUS', NULL),
('ee9rn7xgEQHnogRaa80xF1ZX3geegrfoNMKeY', 1, 'PVmE0vjdkytrMpgzzwdntjenD4oQnxCoyy7VE', '2018-04-04', 'Recreation', 'Gyms and Fitness Centers', 79, 'BIKRAM YOGA OF ROC', NULL),
('veAkKDymjXHY8Lq445O1uqE4kL33LPsPKkqRMN', 1, 're35qdPxkYHXAdLoox6niL4Oao6YwocjdP9eLo', '2018-04-04', 'Shops', 'Supermarkets and Groceries', 14.97, 'Kroger', NULL),
('7Aq3M4mNJ0I0MoXOO3xrIOvbx5jjmLIZgqXgxy', 1, 're35qdPxkYHXAdLoox6niL4Oao6YwocjdP9eLo', '2018-04-06', 'Tax', 'Payment', 11.65, 'FREETAXUSA.COM', NULL),
('QVbyj05wAKt41opDD6xRTkpEymLLBvsOxQ9Vr', 1, 'PVmE0vjdkytrMpgzzwdntjenD4oQnxCoyy7VE', '2018-04-06', 'Travel', 'Gas Stations', 36.82, 'MARATHON PETRO12161', NULL),
('XOQ7ZPjBx4I6X4Lrr39ktgZQpV77Qbi4bKAKv', 1, 're35qdPxkYHXAdLoox6niL4Oao6YwocjdP9eLo', '2018-04-06', 'Tax', 'Payment', 0, 'FREETAXUSA.COM', NULL);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
