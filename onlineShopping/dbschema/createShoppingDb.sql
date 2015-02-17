CREATE DATABASE  IF NOT EXISTS `shopping` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `shopping`;
-- MySQL dump 10.13  Distrib 5.6.19, for osx10.7 (i386)
--
-- Host: localhost    Database: shopping
-- ------------------------------------------------------
-- Server version	5.6.21

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `cardinfo`
--

DROP TABLE IF EXISTS `cardinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cardinfo` (
  `cardinfo_id` int(16) NOT NULL AUTO_INCREMENT,
  `card_no` varchar(16) NOT NULL,
  `card_type` varchar(255) NOT NULL,
  `expiry` int(6) NOT NULL,
  `security_code` int(4) NOT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`cardinfo_id`),
  UNIQUE KEY `card_no_UNIQUE` (`card_no`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `cardinfo_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cardinfo`
--

LOCK TABLES `cardinfo` WRITE;
/*!40000 ALTER TABLE `cardinfo` DISABLE KEYS */;
INSERT INTO `cardinfo` VALUES (1,'321','Visa',678,97,'test'),(2,'334','Visa',43333,33,'test'),(3,'3343','Visa',33333,333,'test'),(4,'3353','Visa',33333,33,'test'),(5,'899','Visa',99999,888,'test'),(6,'333','Visa',22,22,'test'),(7,'33','Visa',3,33,'test'),(9,'888888888888','Visa',67457,889,'test'),(12,'2233','Visa',22,222,'test'),(13,'22','Visa',3333,222,'test');
/*!40000 ALTER TABLE `cardinfo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `category` (
  `category_id` int(11) NOT NULL,
  `category_name` varchar(255) NOT NULL,
  `category_desc` varchar(1000) NOT NULL,
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (101,'Jeans','Choose from a wide variety of jeans. '),(201,'Sweaters','Shop for our sweaters and stay warm!'),(301,'Jackets','Buy our trendy Jackets.'),(401,'Dresses','Shop for our flattering dresses today ! all under $30 !');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_product_mapping`
--

DROP TABLE IF EXISTS `order_product_mapping`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order_product_mapping` (
  `order_product_mappingId` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  PRIMARY KEY (`order_product_mappingId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_product_mapping`
--

LOCK TABLES `order_product_mapping` WRITE;
/*!40000 ALTER TABLE `order_product_mapping` DISABLE KEYS */;
/*!40000 ALTER TABLE `order_product_mapping` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `orders` (
  `order_no` int(11) NOT NULL AUTO_INCREMENT,
  `order_date` date DEFAULT NULL,
  `order_amt` double NOT NULL,
  `user_id` varchar(255) NOT NULL,
  PRIMARY KEY (`order_no`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product` (
  `product_id` int(11) NOT NULL,
  `product_name` varchar(255) NOT NULL,
  `product_price` int(11) NOT NULL,
  `product_desc` varchar(255) DEFAULT NULL,
  `category_id` int(11) DEFAULT NULL,
  `available` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `product_img_loc` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`product_id`),
  KEY `category_id` (`category_id`),
  CONSTRAINT `product_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (10,'High-Rise Jeans',30,'high rise waist, iconic back pocket stitching, Imported\n',101,1,2,'img/high-rise-jeans-1.jpg'),(11,'Mid Rise Jeans',29,'mid rise waist, iconic back pocket stitching, Imported ',101,1,2,'img/mid-rise-jeans-1.jpg'),(12,'Ankle length Jeans',30,'A pair of ankle-length skinny jeans, imported.',101,1,2,'img/ankle-length-jeans.jpg'),(13,'Bootcut Jeans',29,'A pair of Bootcut jeans featuring Zip fly.',101,1,9,'img/bootcut-jeans.jpg'),(20,'Cardigan',15,'A cardigan is great for adding an extra layer to your outfit.',201,1,1,'img/cardigan.jpg'),(21,'Textured Sweater',50,'This textured sweater has pretty shine detailing, imported!',201,1,1,'img/textured-sweater.jpg'),(22,'Ruffles Sweater',60,'Ruffles Sweater just might be the ideal fashion accent for every occasion',201,1,1,'img/ruffles-sweater.jpg'),(23,'Cropped Sweater',35,'Supersoft and cozy, Easy Fit, ideal for all occasions, Imported',201,1,1,'img/cropped-sweater.jpg'),(33,'Denim Jacket',100,'14.5Oz Enzyme Washed Cotton Denim',301,1,2,'img/denim-jacket.jpg'),(34,'Leather Jacket',50,'Giving a modern kick to a classic design.',301,1,2,'img/leather-jacket.jpg'),(43,'Casual Dress',20,'Every girl should have several types in their wardrobe, especially this polished, belted piece.',401,1,1,'img/casual-dress.jpg'),(44,'Party Dress',29,'Burgundy prom style dress. Waistline with  pleats, creating a flared skirt. Imported !',401,1,1,'img/party-dress.jpg'),(45,'Wedding Gown',300,'Mermaid: fits closely to the body from the chest to the waist, then flares out to the hem',401,1,1,'img/wedding-gown.jpg'),(46,'Office Wear',29,'Business dresses are about professionalism, but also about looking and feeling the best possible.',401,1,1,'img/office-wear.jpg');
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `email_id` varchar(255) NOT NULL,
  `passcode` varchar(255) NOT NULL,
  `user_id` varchar(255) NOT NULL,
  `street` varchar(45) NOT NULL,
  `apt_no` varchar(45) DEFAULT NULL,
  `city` varchar(45) NOT NULL,
  `state` varchar(45) NOT NULL,
  `zip` int(5) NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('namratha','sudarshan','nam@gmail.com','password','namrathas','1063 morse','16-301','Sunnyvale','CA',94089),('John','Dean','test@gmail.com','password','test','mary ave','123','Fremont','CA',96789);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-12-21 17:53:41
