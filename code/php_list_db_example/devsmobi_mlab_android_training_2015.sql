-- phpMyAdmin SQL Dump
-- version 3.5.8.2
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Jul 07, 2015 at 02:10 AM
-- Server version: 5.5.42-37.1-log
-- PHP Version: 5.4.23

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `devsmobi_mlab_android_training_2015`
--

-- --------------------------------------------------------

--
-- Table structure for table `enquiries`
--

DROP TABLE IF EXISTS `enquiries`;
CREATE TABLE IF NOT EXISTS `enquiries` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `university_id` int(11) NOT NULL,
  `email` text NOT NULL,
  `subject` text NOT NULL,
  `message` text NOT NULL,
  `time_created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=26 ;

--
-- Dumping data for table `enquiries`
--

INSERT INTO `enquiries` (`id`, `university_id`, `email`, `subject`, `message`, `time_created`) VALUES
(1, 1, 'macharia@devs.mobi', 'Enquiry about any availabel IT courses', 'I would like to enquire about your IT courses', '2015-04-21 01:54:23'),
(2, 1, 'macharia@devs.mobi', 'Enquiry about any availabel IT courses', 'I would like to enquire about your IT courses', '2015-04-21 08:37:44'),
(3, 0, 'hhhdhhd', 'Subject', 'Type your message', '2015-04-22 18:41:07'),
(4, 0, 'hhhdhhd', 'Subject', 'Type your message', '2015-04-22 18:41:23'),
(5, 0, 'hhhdhhd', 'Subject', 'Type your message', '2015-04-22 18:41:24'),
(6, 0, 'hhhdhhd', 'Subject', 'Type your message', '2015-04-22 18:41:25'),
(7, 0, 'hhhdhhd', 'Subject', 'Type your message', '2015-04-22 18:41:25'),
(8, 0, 'hhhdhhd', 'Subject', 'Type your message', '2015-04-22 18:41:25'),
(9, 0, 'hhhdhhd', 'Subject', 'Type your message', '2015-04-22 18:41:25'),
(10, 0, 'hhhdhhd', 'Subject', 'Type your message', '2015-04-22 18:41:27'),
(11, 0, 'hhhdhhd', 'Subject', 'Type your message', '2015-04-22 18:41:27'),
(12, 0, 'hhhdhhd', 'Subject', 'Type your message', '2015-04-22 18:41:28'),
(13, 0, 'hhhdhhd', 'Subject', 'Type your message', '2015-04-22 18:41:28'),
(14, 0, 'hhhdhhd', 'Subject', 'Type your message', '2015-04-22 18:41:29'),
(15, 0, 'hhhdhhd', 'Subject', 'Type your message', '2015-04-22 18:41:30'),
(16, 0, 'hhhdhhd', 'Subject', 'Type your message', '2015-04-22 18:41:32'),
(17, 0, 'The other dayw', 'The one stop the the UK', 'The only one of our website, but it would like a bj to you, but will', '2015-04-22 18:51:04'),
(18, 0, 'The one that has', 'The onlyway only Way new w', 'The only thing that is working. w', '2015-04-22 19:01:07'),
(19, 0, 'macharialawrence2011@gmail.com', 'The only way we do.', 'The other thing, but I think', '2015-04-22 19:02:32'),
(20, 1, 'macharialawrence2011@gmail.com', 'hhshhhhjjd', 'jjsjjjsnn snnd', '2015-04-22 19:06:06'),
(21, 1, 'macharialawrence2011@gmail.com', 'hhshhhhjjd', 'jjsjjjsnn snnd', '2015-04-22 19:06:07'),
(22, 1, 'macharialawrence2011@gmail.com', 'The one stop the, the', 'The only way we can always do that?', '2015-04-22 19:12:09'),
(23, 1, 'dsalaash@gmail.com', 'test', 'testing', '2015-04-23 06:58:41'),
(24, 1, 'macharia@devs.mobi', 'Enquiry about any availabel IT courses', 'I would like to enquire about your IT courses', '2015-04-23 07:09:56'),
(25, 1, 'jimmymugo1@gmail.com', 'testing', 'Testing application', '2015-04-23 08:26:52');

-- --------------------------------------------------------

--
-- Table structure for table `universities`
--

DROP TABLE IF EXISTS `universities`;
CREATE TABLE IF NOT EXISTS `universities` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(100) NOT NULL,
  `description` text NOT NULL,
  `phone_no` varchar(30) NOT NULL,
  `website` text NOT NULL,
  `address` text NOT NULL,
  `latitude` double(9,7) NOT NULL,
  `longitude` double(9,7) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `universities`
--

INSERT INTO `universities` (`id`, `title`, `description`, `phone_no`, `website`, `address`, `latitude`, `longitude`) VALUES
(1, 'University of Nairobi', 'The University of Nairobi (UoN) is a collegiate research university based in Nairobi. It is the largest university in Kenya. Although its history as an educational institution goes back to 1956, it did not become an independent university until 1970 when the University of East Africa was split into three independent universities: Makerere University in Uganda, the University of Dar es Salaam in Tanzania, and the University of Nairobi.', '+254 20 318262', 'http://www.uonbi.ac.ke/', 'Uhuru Highway, Nairobi 00100, Kenya', -1.2807100, 36.8163500),
(2, 'Kenyatta University', 'Kenyatta University (KU), is a multi-campus public university in Kenya, the largest economy in the East African Community. As of October 2011, it is one of seven public universities in the country.[', '+254 20 810901', 'http://www.ku.ac.ke/', 'Kenyatta University, P.O. Box 43844, Nairobi, Kenya', -1.1822300, 36.9247100),
(3, 'Jomo Kenyatta University of Agriculture and Technology', 'Jomo Kenyatta University of Agriculture and Technology (JKUAT) is a public university near Nairobi, Kenya. It is situated in Juja, 36 kilometres northeast of Nairobi, along the Nairobi-Thika SuperHighway. It offers courses in Technology, Engineering, Science, Architecture and Building sciences. The university has a strong research interest in the areas of biotechnology and engineering.', '+254 67 52028', 'http://www.jkuat.ac.ke/', 'P.O. Box 62,000 -  00200 NAIROBI, KENYA', -1.0891000, 37.0104700);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
