-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jun 08, 2015 at 06:05 PM
-- Server version: 5.6.17
-- PHP Version: 5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

USE petfinder;

--
-- Database: `petfinder`
--

-- --------------------------------------------------------

DROP TABLE IF EXISTS 'users';

--
-- Table structure for table `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `username` tinytext,
  `userid` int(11) DEFAULT NULL,
  `password` tinytext
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='Stores user information for validation and identification in the petfinder app.';

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`username`, `userid`, `password`) VALUES
('Ian', 1, 'bob'),
('Daniel', 2, 'pizza'),
('Cenk', 3, '12345'),
('Sam', 4, '2cool4us');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
