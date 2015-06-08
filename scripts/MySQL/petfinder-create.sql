-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Server version: 5.6.17
-- PHP Version: 5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

CREATE DATABASE IF NOT EXISTS petfinder;
USE petfinder;

--
-- Database: `petfinder`
--

DROP PROCEDURE IF EXISTS `addUser`;
DROP PROCEDURE IF EXISTS `changePassword`;
DROP PROCEDURE IF EXISTS `deleteUser`;

DELIMITER $$
--
-- Procedures
--

CREATE DEFINER=`root`@`localhost` PROCEDURE `addUser`(IN `newName` TINYTEXT, IN `newID` INT, IN `newPassword` TINYTEXT)
    NO SQL
INSERT INTO users (username, userid, password)
VALUES (newName, newID, newPassword)$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `changePassword`(IN `changeID` INT, IN `newPassword` TINYTEXT)
    NO SQL
UPDATE users
SET password=newPassword
WHERE userID=changeID$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteUser`(IN `deleteID` INT)
    NO SQL
DELETE FROM users
WHERE userID=deleteID$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `username` tinytext,
  `userid` int(11) DEFAULT NULL,
  `password` tinytext
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='Stores user information for validation and identification in the petfinder app.';

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
