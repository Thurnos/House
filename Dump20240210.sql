CREATE DATABASE  IF NOT EXISTS `building_management` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `building_management`;
-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: building_management
-- ------------------------------------------------------
-- Server version	8.0.34

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `apartment`
--

DROP TABLE IF EXISTS `apartment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `apartment` (
  `ApartmentID` int NOT NULL AUTO_INCREMENT,
  `BuildingID` int DEFAULT NULL,
  `OwnerID` int DEFAULT NULL,
  `ApartmentNumber` varchar(50) DEFAULT NULL,
  `SquareFootage` decimal(10,2) DEFAULT NULL,
  `HasPet` tinyint(1) DEFAULT NULL,
  `LastPaymentDate` date DEFAULT NULL,
  PRIMARY KEY (`ApartmentID`),
  UNIQUE KEY `UK_4q6a2pg9045mwint97ycww21v` (`ApartmentID`),
  KEY `FK_Apartment_Building` (`BuildingID`),
  KEY `FK_Apartment_Owner` (`OwnerID`),
  CONSTRAINT `apartment_ibfk_1` FOREIGN KEY (`BuildingID`) REFERENCES `building` (`BuildingID`),
  CONSTRAINT `apartment_ibfk_2` FOREIGN KEY (`OwnerID`) REFERENCES `owner` (`OwnerID`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `apartment`
--

LOCK TABLES `apartment` WRITE;
/*!40000 ALTER TABLE `apartment` DISABLE KEYS */;
INSERT INTO `apartment` VALUES (1,1,1,'A100',88.70,1,'2024-01-30'),(2,1,2,'A101',80.20,0,'2023-01-06'),(3,1,3,'A102',65.80,1,'2023-01-29'),(4,1,4,'A103',90.00,0,'2024-01-06'),(7,1,5,'A104',120.50,1,'2024-01-06'),(8,2,6,'A105',80.00,0,'2024-01-06'),(9,2,7,'A106',90.70,1,'2023-01-03'),(10,2,8,'A107',105.20,0,'2023-01-04'),(11,2,9,'A108',70.30,1,'2024-01-06'),(12,4,10,'A109',110.00,1,'2024-01-29'),(13,4,11,'A110',75.80,0,'2024-01-29'),(14,5,12,'A111',130.40,1,'2023-01-08'),(15,5,13,'A112',95.50,0,'2023-01-09'),(16,6,14,'A113',60.10,1,'2023-01-10'),(17,6,15,'A114',122.40,1,'2023-01-10'),(18,6,16,'A115',98.74,1,'2023-01-10'),(19,6,17,'A116',62.50,0,'2023-01-10');
/*!40000 ALTER TABLE `apartment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `building`
--

DROP TABLE IF EXISTS `building`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `building` (
  `BuildingID` int NOT NULL AUTO_INCREMENT,
  `Address` varchar(255) DEFAULT NULL,
  `TotalApartments` int DEFAULT NULL,
  `Floors` int DEFAULT NULL,
  `BuiltUpArea` double DEFAULT NULL,
  `CommonAreas` double DEFAULT NULL,
  `AssignedEmployeeID` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`BuildingID`),
  UNIQUE KEY `BuildingID_UNIQUE` (`BuildingID`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `building`
--

LOCK TABLES `building` WRITE;
/*!40000 ALTER TABLE `building` DISABLE KEYS */;
INSERT INTO `building` VALUES (1,'Address1',50,5,1000,200,'1'),(2,'Address2',60,6,1200,250,'1'),(4,'Sample Address',10,3,5000,1000,'1'),(5,'Sample Address',101,32,5000,1000,'2'),(6,'New Address',10,5,200,50,'3'),(7,'New Address',10,5,200,50,'2'),(9,'New Address',10,5,200,50,'5'),(10,'New Address',10,5,200,50,'2'),(12,'Adress',33,12,3456,345,'2'),(13,'Adress',33,12,3456,345,'5'),(15,'Sessame str.',7,28,8700,349,'3');
/*!40000 ALTER TABLE `building` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `company`
--

DROP TABLE IF EXISTS `company`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `company` (
  `CompanyID` int NOT NULL AUTO_INCREMENT,
  `CompanyName` varchar(255) DEFAULT NULL,
  `collectedfees` double DEFAULT NULL,
  PRIMARY KEY (`CompanyID`),
  UNIQUE KEY `UK_kacov9wd9e0kcvn9j2d7jko02` (`CompanyID`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `company`
--

LOCK TABLES `company` WRITE;
/*!40000 ALTER TABLE `company` DISABLE KEYS */;
INSERT INTO `company` VALUES (1,'imane',1565),(2,'Manager For A Day',1700),(4,'MNG+ Solutions',1120),(5,'Globex Corporation',4450),(8,'Housing Services-1',1500),(9,'Housing Services-1',1500),(10,'Housing Services-1',1500);
/*!40000 ALTER TABLE `company` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee` (
  `EmployeeID` int NOT NULL AUTO_INCREMENT,
  `EmployeeName` varchar(255) DEFAULT NULL,
  `CompanyID` int DEFAULT NULL,
  `ServicedBuildings` int DEFAULT NULL,
  PRIMARY KEY (`EmployeeID`),
  UNIQUE KEY `UK_efdyiuuybute8lt8mhdbuxol9` (`EmployeeID`),
  KEY `FK_Employee_Company` (`CompanyID`),
  CONSTRAINT `employee_ibfk_1` FOREIGN KEY (`CompanyID`) REFERENCES `company` (`CompanyID`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` VALUES (1,'Todor I.',1,6),(2,'Andrew P.',1,1),(3,'Martin J.',2,2),(5,'Jason K.',2,3);
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fee`
--

DROP TABLE IF EXISTS `fee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fee` (
  `FeeID` int NOT NULL AUTO_INCREMENT,
  `BuildingID` int DEFAULT NULL,
  `SquareFootageRate` decimal(10,2) DEFAULT '0.20',
  `ElevatorUsageRate` decimal(10,2) DEFAULT '3.00',
  `PetFee` decimal(10,2) DEFAULT '3.00',
  PRIMARY KEY (`FeeID`),
  KEY `FK_Fee_Building` (`BuildingID`),
  CONSTRAINT `fee_ibfk_1` FOREIGN KEY (`BuildingID`) REFERENCES `building` (`BuildingID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fee`
--

LOCK TABLES `fee` WRITE;
/*!40000 ALTER TABLE `fee` DISABLE KEYS */;
INSERT INTO `fee` VALUES (1,1,0.20,3.00,3.00),(2,2,0.20,3.00,3.00),(3,4,0.20,3.00,3.00);
/*!40000 ALTER TABLE `fee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `owner`
--

DROP TABLE IF EXISTS `owner`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `owner` (
  `OwnerID` int NOT NULL AUTO_INCREMENT,
  `OwnerName` varchar(255) DEFAULT NULL,
  `ContactInfo` varchar(255) DEFAULT NULL,
  `ApartmentNumber` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`OwnerID`),
  UNIQUE KEY `UK_q5q59t608brsg27h8eq72dyvp` (`OwnerID`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `owner`
--

LOCK TABLES `owner` WRITE;
/*!40000 ALTER TABLE `owner` DISABLE KEYS */;
INSERT INTO `owner` VALUES (1,'Ivan Ivanov','ivan@gmail.com','A100'),(2,'John Doe','john.doe@email.com','A101'),(3,'Jane Smith','jane.smith@email.com','A102'),(4,'Alice Johnson','alice.johnson@email.com','A103'),(5,'Bob Brown','bob.brown@email.com','A104'),(6,'Carol White','carol.white@email.com','A105'),(7,'David Green','david.green@email.com','A106'),(8,'Eve Black','eve.black@email.com','A107'),(9,'Frank Gray','frank.gray@email.com','A108'),(10,'Grace Hall','grace.hall@email.com','A109'),(11,'Henry Adams','henry.adams@email.com','A110'),(12,'Ivy Turner','ivy.turner@email.com','A111'),(13,'Jake Carter','jake.carter@email.com','A112'),(14,'Laura Brooks','laura.brooks@email.com','A113'),(15,'Matt Nelson','matt.nelson@email.com','A114'),(16,'Nancy King','nancy.king@email.com','A115'),(17,'Oscar Hill','oscar.hill@email.com','A116');
/*!40000 ALTER TABLE `owner` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `paidfee`
--

DROP TABLE IF EXISTS `paidfee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `paidfee` (
  `FeeId` int NOT NULL AUTO_INCREMENT,
  `OwnerId` int DEFAULT NULL,
  `PaymentDate` date DEFAULT NULL,
  `AmountPaid` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`FeeId`),
  KEY `FK_PaidFee_Owner` (`OwnerId`),
  CONSTRAINT `paidfee_ibfk_1` FOREIGN KEY (`OwnerId`) REFERENCES `owner` (`OwnerID`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `paidfee`
--

LOCK TABLES `paidfee` WRITE;
/*!40000 ALTER TABLE `paidfee` DISABLE KEYS */;
INSERT INTO `paidfee` VALUES (1,1,'2024-01-28',272.88),(2,1,'2024-01-28',272.88),(3,1,'2024-01-28',272.88),(4,1,'2024-01-28',272.88),(5,1,'2024-01-28',272.88),(6,1,'2024-01-28',272.88),(7,1,'2024-01-28',272.88),(8,1,'2024-01-28',545.76),(9,1,'2024-01-28',272.88),(10,10,'2024-01-29',264.00),(11,3,'2024-01-29',205.92),(12,11,'2024-01-29',205.92),(13,1,'2024-01-30',296.88);
/*!40000 ALTER TABLE `paidfee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pet`
--

DROP TABLE IF EXISTS `pet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pet` (
  `PetID` int NOT NULL AUTO_INCREMENT,
  `ResidentID` int DEFAULT NULL,
  `PetType` enum('Dog','Cat','Fish','Bird','Other') NOT NULL,
  `AdditionalFee` tinyint(1) DEFAULT NULL,
  `PetName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`PetID`),
  KEY `FK_Pet_Resident` (`ResidentID`),
  CONSTRAINT `pet_ibfk_1` FOREIGN KEY (`ResidentID`) REFERENCES `resident` (`ResidentID`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pet`
--

LOCK TABLES `pet` WRITE;
/*!40000 ALTER TABLE `pet` DISABLE KEYS */;
INSERT INTO `pet` VALUES (1,1,'Dog',1,'Straho'),(2,37,'Dog',1,'Buddy'),(3,38,'Dog',1,'Max'),(4,39,'Dog',1,'Charlie'),(5,40,'Dog',1,'Bailey'),(6,41,'Dog',1,'Luna'),(7,42,'Dog',1,'Cooper'),(8,43,'Dog',1,'Sadie'),(9,44,'Cat',0,'Oliver'),(10,45,'Fish',0,'Nemo'),(11,46,'Bird',0,'Kiwi');
/*!40000 ALTER TABLE `pet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `resident`
--

DROP TABLE IF EXISTS `resident`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `resident` (
  `ResidentID` int NOT NULL AUTO_INCREMENT,
  `ApartmentID` int DEFAULT NULL,
  `ResidentName` varchar(255) DEFAULT NULL,
  `Age` int DEFAULT NULL,
  `UsesElevator` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`ResidentID`),
  UNIQUE KEY `UK_erva08yhnywcbhorld87lpv18` (`ResidentID`),
  KEY `ApartmentID` (`ApartmentID`),
  CONSTRAINT `resident_ibfk_1` FOREIGN KEY (`ApartmentID`) REFERENCES `apartment` (`ApartmentID`)
) ENGINE=InnoDB AUTO_INCREMENT=78 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `resident`
--

LOCK TABLES `resident` WRITE;
/*!40000 ALTER TABLE `resident` DISABLE KEYS */;
INSERT INTO `resident` VALUES (1,1,'Stoqn Dimitrov',15,1),(37,1,'Liam Smith',40,1),(38,2,'Olivia Brown',28,1),(39,2,'Noah Davis',31,1),(40,3,'Ava Miller',25,1),(41,3,'William Wilson',45,1),(42,4,'Sophia Moore',52,0),(43,4,'James Taylor',60,0),(44,7,'Isabella Anderson',22,1),(45,7,'Logan Thomas',35,0),(46,7,'Mia Jackson',47,0),(47,8,'Lucas White',50,0),(48,8,'Charlotte Harris',30,0),(49,8,'Ethan Martin',33,0),(50,9,'Amelia Thompson',55,1),(51,9,'Mason Garcia',29,0),(52,9,'Harper Martinez',65,0),(53,9,'Benjamin Rodriguez',72,0),(54,10,'Evelyn Lee',38,1),(55,10,'Alexander Walker',41,0),(56,11,'Abigail Hall',27,1),(57,11,'Aiden Young',49,1),(58,12,'Emily Hernandez',32,0),(59,12,'Sebastian King',37,0),(60,13,'Elizabeth Wright',43,0),(61,13,'Matthew Lopez',48,1),(62,14,'Sofia Hill',51,1),(63,14,'Avery Scott',58,0),(64,15,'Ella Green',62,1),(65,15,'Daniel Adams',70,0),(66,16,'Grace Baker',75,0),(67,16,'Jack Nelson',80,0);
/*!40000 ALTER TABLE `resident` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-02-10  0:20:03
