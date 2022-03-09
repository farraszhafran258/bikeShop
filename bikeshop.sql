-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Feb 15, 2022 at 08:23 AM
-- Server version: 10.4.19-MariaDB
-- PHP Version: 8.0.7

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `bikeshop`
--

-- --------------------------------------------------------

--
-- Table structure for table `bike`
--

CREATE TABLE `bike` (
  `bikeID` int(11) NOT NULL,
  `bikeName` varchar(50) NOT NULL,
  `bikePrice` int(11) NOT NULL,
  `stock` int(11) NOT NULL,
  `img` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `bike`
--

INSERT INTO `bike` (`bikeID`, `bikeName`, `bikePrice`, `stock`, `img`) VALUES
(1, 'Giant', 12000000, 12, 'Giant.jpg'),
(2, 'Look', 26000000, 5, 'Look.jpg'),
(3, 'Triban', 14000000, 15, 'Triban.jpg'),
(4, 'United', 5000000, 30, 'United.jpg'),
(5, 'Stratos 3', 8500000, 25, 'S3.png');

-- --------------------------------------------------------

--
-- Table structure for table `memberloyalty`
--

CREATE TABLE `memberloyalty` (
  `memberID` int(11) NOT NULL,
  `namaMember` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL CHECK (`email` like '%@%.com')
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `memberloyalty`
--

INSERT INTO `memberloyalty` (`memberID`, `namaMember`, `email`) VALUES
(1, 'Linnet', 'Linnet@gmail.com'),
(2, 'Jimmy Nantz', 'jimNantz@gmail.com'),
(3, 'Karen Page', 'page@gmail.com'),
(4, 'Ardrian Chase', 'chase@gmail.com'),
(5, 'Felicity Smoak', 'feli@gmail.com');

-- --------------------------------------------------------

--
-- Table structure for table `operator`
--

CREATE TABLE `operator` (
  `operatorID` int(11) NOT NULL,
  `namaOperator` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL CHECK (`email` like '%@%.com'),
  `password` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `operator`
--

INSERT INTO `operator` (`operatorID`, `namaOperator`, `email`, `password`) VALUES
(1, 'Franklin Nelson', 'foggy@gmail.com', 'abcd1234'),
(2, 'Jonathan Kent', 'john@gmail.com', 'abcd123'),
(3, 'Dinah Drake', 'drake@gmail.com', 'abcd1234');

-- --------------------------------------------------------

--
-- Table structure for table `purchasedetail`
--

CREATE TABLE `purchasedetail` (
  `purchaseID` int(11) NOT NULL,
  `bikeID` int(11) NOT NULL,
  `Qty` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `purchasedetail`
--

INSERT INTO `purchasedetail` (`purchaseID`, `bikeID`, `Qty`) VALUES
(3, 1, 1),
(3, 5, 1),
(5, 4, 2),
(6, 1, 2),
(7, 4, 1),
(7, 5, 1),
(10, 5, 1),
(11, 4, 1);

-- --------------------------------------------------------

--
-- Table structure for table `purchaseheader`
--

CREATE TABLE `purchaseheader` (
  `purchaseID` int(11) NOT NULL,
  `tanggal` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `OperatorID` int(11) NOT NULL,
  `supervisorID` int(11) DEFAULT NULL,
  `memberID` int(11) DEFAULT NULL,
  `totalPrice` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `purchaseheader`
--

INSERT INTO `purchaseheader` (`purchaseID`, `tanggal`, `OperatorID`, `supervisorID`, `memberID`, `totalPrice`) VALUES
(3, '2022-02-14 01:32:08', 1, NULL, NULL, 12000000),
(4, '2022-02-14 01:53:57', 1, NULL, 1, 8500000),
(5, '2022-02-14 02:02:53', 1, NULL, 2, 10000000),
(6, '2022-02-14 02:17:40', 2, NULL, 2, 24000000),
(7, '2022-02-14 07:40:15', 2, NULL, 1, 10000000),
(10, '2022-02-14 09:32:49', 2, 1, NULL, 8500000),
(11, '2022-02-14 10:17:19', 2, NULL, NULL, 5000000);

-- --------------------------------------------------------

--
-- Table structure for table `supervisor`
--

CREATE TABLE `supervisor` (
  `supervisorID` int(11) NOT NULL,
  `namaSupervisor` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL CHECK (`email` like '%@%.com'),
  `password` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `supervisor`
--

INSERT INTO `supervisor` (`supervisorID`, `namaSupervisor`, `email`, `password`) VALUES
(1, 'Matthew Murdock', 'matty@gmail.com', 'abc12345'),
(2, 'Oliver Queen', 'ollie@gmail.com', 'abcde123'),
(3, 'James Rodriguez', 'james@gmail.com', 'Abcd1234');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `bike`
--
ALTER TABLE `bike`
  ADD PRIMARY KEY (`bikeID`);

--
-- Indexes for table `memberloyalty`
--
ALTER TABLE `memberloyalty`
  ADD PRIMARY KEY (`memberID`);

--
-- Indexes for table `operator`
--
ALTER TABLE `operator`
  ADD PRIMARY KEY (`operatorID`);

--
-- Indexes for table `purchasedetail`
--
ALTER TABLE `purchasedetail`
  ADD PRIMARY KEY (`purchaseID`,`bikeID`),
  ADD KEY `bikeID` (`bikeID`);

--
-- Indexes for table `purchaseheader`
--
ALTER TABLE `purchaseheader`
  ADD PRIMARY KEY (`purchaseID`),
  ADD KEY `OperatorID` (`OperatorID`),
  ADD KEY `supervisorID` (`supervisorID`),
  ADD KEY `memberID` (`memberID`);

--
-- Indexes for table `supervisor`
--
ALTER TABLE `supervisor`
  ADD PRIMARY KEY (`supervisorID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `bike`
--
ALTER TABLE `bike`
  MODIFY `bikeID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `memberloyalty`
--
ALTER TABLE `memberloyalty`
  MODIFY `memberID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `operator`
--
ALTER TABLE `operator`
  MODIFY `operatorID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `purchaseheader`
--
ALTER TABLE `purchaseheader`
  MODIFY `purchaseID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `supervisor`
--
ALTER TABLE `supervisor`
  MODIFY `supervisorID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `purchasedetail`
--
ALTER TABLE `purchasedetail`
  ADD CONSTRAINT `purchasedetail_ibfk_1` FOREIGN KEY (`purchaseID`) REFERENCES `purchaseheader` (`purchaseID`),
  ADD CONSTRAINT `purchasedetail_ibfk_2` FOREIGN KEY (`bikeID`) REFERENCES `bike` (`bikeID`);

--
-- Constraints for table `purchaseheader`
--
ALTER TABLE `purchaseheader`
  ADD CONSTRAINT `purchaseheader_ibfk_1` FOREIGN KEY (`OperatorID`) REFERENCES `operator` (`operatorID`),
  ADD CONSTRAINT `purchaseheader_ibfk_2` FOREIGN KEY (`supervisorID`) REFERENCES `supervisor` (`supervisorID`),
  ADD CONSTRAINT `purchaseheader_ibfk_3` FOREIGN KEY (`memberID`) REFERENCES `memberloyalty` (`memberID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
