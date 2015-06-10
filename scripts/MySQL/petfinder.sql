-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jun 10, 2015 at 08:51 PM
-- Server version: 5.6.17
-- PHP Version: 5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `petfinder`
--
CREATE DATABASE IF NOT EXISTS `petfinder` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `petfinder`;

DELIMITER $$
--
-- Procedures
--
DROP PROCEDURE IF EXISTS `addUser`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `addUser`(IN `newName` TINYTEXT, IN `newID` INT, IN `newPassword` TINYTEXT)
    NO SQL
INSERT INTO users (username, userid, password)
VALUES (newName, newID, newPassword)$$

DROP PROCEDURE IF EXISTS `changePassword`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `changePassword`(IN `changeID` INT, IN `newPassword` TINYTEXT)
    NO SQL
UPDATE users
SET password=newPassword
WHERE userID=changeID$$

DROP PROCEDURE IF EXISTS `deleteUser`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteUser`(IN `deleteID` INT)
    NO SQL
DELETE FROM users
WHERE userID=deleteID$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `authorization`
--

DROP TABLE IF EXISTS `authorization`;
CREATE TABLE IF NOT EXISTS `authorization` (
  `roleid` tinyint(4) NOT NULL,
  `userid` int(11) NOT NULL,
  `role` tinytext NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='Stores user authorization information for the petfinder app.';

--
-- Dumping data for table `authorization`
--

INSERT INTO `authorization` (`roleid`, `userid`, `role`) VALUES
(1, 1, 'USER'),
(0, 2, 'ADMIN');
(1, 3, 'USER'),
(1, 4, 'USER'),

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
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
