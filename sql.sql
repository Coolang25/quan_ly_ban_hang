-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: quanlybanhang
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
-- Table structure for table `bill`
--

DROP TABLE IF EXISTS `bill`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bill` (
  `ID` bigint NOT NULL AUTO_INCREMENT,
  `CREATED_TIME` timestamp NULL DEFAULT NULL,
  `STATUS` int DEFAULT NULL,
  `USER_ID` bigint DEFAULT NULL,
  `TOTAL_PRICE` int DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bill`
--

LOCK TABLES `bill` WRITE;
/*!40000 ALTER TABLE `bill` DISABLE KEYS */;
INSERT INTO `bill` VALUES (1,'2023-11-14 19:37:53',0,2,16000000),(2,'2023-11-14 19:38:09',3,2,999000),(3,'2023-11-14 19:39:25',1,3,2000000),(4,'2023-11-14 19:39:32',3,3,2500000),(5,'2023-11-15 03:11:13',1,2,16000000);
/*!40000 ALTER TABLE `bill` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `brands`
--

DROP TABLE IF EXISTS `brands`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `brands` (
  `ID` bigint NOT NULL AUTO_INCREMENT,
  `LOGO` varchar(128) DEFAULT NULL,
  `NAME` varchar(45) NOT NULL,
  `STATUS` bit(1) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `brands`
--

LOCK TABLES `brands` WRITE;
/*!40000 ALTER TABLE `brands` DISABLE KEYS */;
INSERT INTO `brands` VALUES (1,'adidas.jpg','Adidas',_binary ''),(2,'bitis.jpg','Bitis',_binary ''),(3,'nike.jpg','Nike',_binary ''),(4,'puma.jpg','Puma',_binary ''),(5,'vans.jpg','Vans',_binary '');
/*!40000 ALTER TABLE `brands` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cart_item`
--

DROP TABLE IF EXISTS `cart_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart_item` (
  `ID` bigint NOT NULL AUTO_INCREMENT,
  `INTENDED_QUANTITY` int DEFAULT NULL,
  `STATUS` bit(1) DEFAULT NULL,
  `PRODUCT_SIZE_ID` bigint NOT NULL,
  `USER_ID` bigint NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart_item`
--

LOCK TABLES `cart_item` WRITE;
/*!40000 ALTER TABLE `cart_item` DISABLE KEYS */;
INSERT INTO `cart_item` VALUES (1,1,_binary '',9,2),(2,1,_binary '',24,2),(3,1,_binary '',35,2),(4,1,_binary '',48,3),(5,1,_binary '',71,3),(6,1,_binary '',7,2);
/*!40000 ALTER TABLE `cart_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categories` (
  `ID` bigint NOT NULL AUTO_INCREMENT,
  `IMAGE` varchar(128) DEFAULT NULL,
  `NAME` varchar(128) NOT NULL,
  `STATUS` bit(1) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` VALUES (1,'boot.jpg','Boot',_binary ''),(2,'giayda.jpg','Giày da',_binary ''),(3,'giayluoi.jpg','Giày lười',_binary ''),(4,'giaytay.jpg','Giày tây',_binary ''),(5,'sandal.jpg','Sandal',_binary ''),(6,'sneaker.jpg','Sneaker',_binary '');
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comment` (
  `ID` bigint NOT NULL AUTO_INCREMENT,
  `CONTENT` varchar(256) DEFAULT NULL,
  `USER_ID` bigint DEFAULT NULL,
  `PRODUCT_ID` bigint DEFAULT NULL,
  `TIME` timestamp(6) NULL DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
INSERT INTO `comment` VALUES (1,'Đẹp quá',2,22,'2023-11-15 06:36:38.086000'),(2,'Cho xin giá!',3,16,'2023-11-15 07:04:11.695000'),(3,'Đẹp quá',3,16,'2023-11-15 07:04:20.319000');
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ordered_item`
--

DROP TABLE IF EXISTS `ordered_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ordered_item` (
  `ID` bigint NOT NULL AUTO_INCREMENT,
  `QUANTITY` int DEFAULT NULL,
  `CART_ITEM_ID` bigint DEFAULT NULL,
  `BILL_ID` bigint DEFAULT NULL,
  `PRODUCT_SIZE_ID` bigint DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ordered_item`
--

LOCK TABLES `ordered_item` WRITE;
/*!40000 ALTER TABLE `ordered_item` DISABLE KEYS */;
INSERT INTO `ordered_item` VALUES (1,1,3,1,35),(2,1,1,2,9),(3,1,4,3,48),(4,1,5,4,71),(5,1,3,5,35);
/*!40000 ALTER TABLE `ordered_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_images`
--

DROP TABLE IF EXISTS `product_images`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_images` (
  `ID` bigint NOT NULL AUTO_INCREMENT,
  `NAME` varchar(255) NOT NULL,
  `PRODUCT_ID` bigint NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=65 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_images`
--

LOCK TABLES `product_images` WRITE;
/*!40000 ALTER TABLE `product_images` DISABLE KEYS */;
INSERT INTO `product_images` VALUES (7,'vn-11134207-7r98o-llxbyb8etcdb98.jpg',1),(8,'vn-11134207-7r98o-llxbyb8erxsv94.jpg',1),(9,'vn-11134207-7r98o-llxbyb8ew5i775.jpg',1),(10,'giay-ho-got-mlb-playball-origin-mule-new-york-yankees-3amuua11n-50bks-mau-den-ss2022-62bc14170fbbc-29062022155759.jpg',2),(11,'giay-the-thao-lacoste-l001-123-4-sma-mau-trang-xanh-navy-size-41-63f5cf49677a5-22022023151609.jpg',3),(12,'giay-the-thao-lacoste-l001-123-4-sma-mau-trang-xanh-navy-size-41-63f5cf496489f-22022023151609.jpg',3),(13,'giay-the-thao-lacoste-l001-123-4-sma-mau-trang-xanh-navy-size-41-63f5cf4948294-22022023151609.jpg',3),(14,'giay-luoi-nam-gucci-leather-signature-driver-mau-den-466904-size-42-62fa0a9c4bc8c-15082022155804.jpg',4),(15,'giay-luoi-nam-gucci-leather-signature-driver-mau-den-size-42-5efef1ac708fa-03072020155156.jpg',4),(16,'giay-luoi-nam-gucci-leather-signature-driver-mau-den-size-42-5efef1acdcdf3-03072020155156.jpg',4),(20,'giay-sneaker-vans-ua-style-36-vintage-pop-vn0a54f66qu-mau-xanh-trang-64e96e3c0eabd-26082023101508.jpg',5),(21,'giay-sneaker-vans-ua-style-36-vintage-pop-vn0a54f66qu-mau-xanh-trang-64e96e3c0ed28-26082023101508.jpg',5),(22,'giay-sneaker-vans-ua-style-36-vintage-pop-vn0a54f66qu-mau-xanh-trang-64e96e3c0f480-26082023101508.jpg',5),(23,'sandals-da-nam-aokang-19173103143-63c7506848b4a-18012023085032.jpg',6),(24,'4-19173103143.jpg',6),(25,'8056536828368.1.jpg',7),(26,'8056536828368.jpg',7),(27,'12992850362398.2.jpg',7),(28,'8051516580960.2.jpg',8),(29,'8051516580960.4.jpg',8),(30,'sandals-be-trai-geox-j-borealis-b-d-canvas-mesh-mau-xam-size-32-6462007f7f6ed-15052023165055.jpg',8),(31,'giay-boot-celine-margaret-chelsea-boot-in-calfskin-black-mau-den-632828881b5e5-19092022153000.jpg',9),(32,'giay-boot-celine-margaret-chelsea-boot-in-calfskin-black-mau-den-632828881eed5-19092022153000.jpg',9),(33,'giay-boot-celine-margaret-chelsea-boot-in-calfskin-black-mau-den-63282888181eb-19092022153000.jpg',9),(34,'giay-boot-duca-di-morrone-noah_taupe-mau-nau-size-41-636dd2dab775a-11112022114306.jpg',10),(35,'giay-boot-duca-di-morrone-noah_taupe-mau-nau-size-41-636dd2dabde86-11112022114306.jpg',10),(36,'giay-boot-duca-di-morrone-noah_taupe-mau-nau-size-41-636dd2dac0baa-11112022114306.jpg',10),(37,'giay-boot-duca-di-morrone-juri-cam_taupe-mau-nau-size-40-6364e0762b719-04112022165046.jpg',11),(38,'giay-boot-duca-di-morrone-juri-cam_taupe-mau-nau-size-40-6364e07614262-04112022165046.jpg',11),(39,'giay-boot-duca-di-morrone-juri-cam_taupe-mau-nau-size-40-6364e07622405-04112022165046.jpg',11),(40,'giay-boot-nam-duca-di-morrone-noah_black-mau-den-size-41-64e02d76e3aef-19082023094822.jpg',12),(41,'giay-boot-nam-duca-di-morrone-noah_black-mau-den-size-41-64e02d76e3d13-19082023094822.jpg',12),(42,'giay-boot-nam-duca-di-morrone-noah_black-mau-den-size-41-64e02d76e36ec-19082023094822.jpg',12),(43,'giay-tay-be-classy-charles-captoe-oxford-of02-mau-nau-61f34977dec6a-28012022084007.jpg',13),(44,'giay-tay-be-classy-charles-captoe-oxford-of02-mau-nau-61f34977e45d9-28012022084007.jpg',13),(45,'giay-tay-be-classy-charles-captoe-oxford-of02-mau-nau-61f34977eb83e-28012022084007.jpg',13),(46,'giay-tay-be-classy-classic-brogues-derby-db18-mau-den-61f4a9205c0e0-29012022094032.jpg',14),(47,'giay-tay-be-classy-classic-brogues-derby-db18-mau-den-61f4a92049d98-29012022094032.jpg',14),(48,'giay-tay-be-classy-classic-brogues-derby-db18-mau-den-61f4a92050f6d-29012022094032.jpg',14),(49,'giay-tay-nam-germano-bellesi-1089-smoth-mau-den-63a13b4c6dcfe-20122022113420.jpg',15),(50,'giay-tay-nam-germano-bellesi-1089-smoth-mau-den-63a13b4c7b662-20122022113420.jpg',15),(51,'giay-tay-nam-germano-bellesi-1089-smoth-mau-den-63a13b4c7575a-20122022113420.jpg',15),(52,'giay-tay-unisex-made-in-italia-da-lon-il-cielo_taupe-mau-nau-size-41-62e22adf2ec5a-28072022132119.jpg',19),(53,'giay-tay-unisex-made-in-italia-da-lon-il-cielo_taupe-mau-nau-size-41-62e22adf3aa57-28072022132119.jpg',19),(54,'giay-tay-unisex-made-in-italia-da-lon-il-cielo_taupe-mau-nau-size-41-62e22adf4413b-28072022132119.jpg',19),(55,'giay-luoi-nam-duca-di-morrone-da-lon-elia-cam_jeans-mau-xanh-size-40-64df3728cec73-18082023161728.jpg',20),(56,'giay-luoi-nam-duca-di-morrone-da-lon-elia-cam_jeans-mau-xanh-size-40-64df3728cf1a5-18082023161728.jpg',20),(57,'giay-luoi-nam-duca-di-morrone-da-lon-elia-cam_jeans-mau-xanh-size-40-64df3728cf3d6-18082023161728.jpg',20),(58,'giay-luoi-nam-duca-di-morrone-da-lon-elia-cam_tortora-mau-nau-size-40-64e01d862e46e-19082023084022.jpg',21),(59,'giay-luoi-nam-duca-di-morrone-da-lon-elia-cam_tortora-mau-nau-size-40-64e01d862ea58-19082023084022.jpg',21),(60,'giay-luoi-nam-duca-di-morrone-da-lon-elia-cam_tortora-mau-nau-size-40-64e01d862ecd9-19082023084022.jpg',21),(61,'giay-luoi-nam-duca-di-morrone-da-lon-enea-cam_grigio-mau-ghi-size-40-6476ad1e7ab12-31052023091246.jpg',22),(62,'giay-luoi-nam-duca-di-morrone-da-lon-enea-cam_grigio-mau-ghi-size-40-6476ad1e69ef0-31052023091246.jpg',22),(63,'giay-luoi-nam-duca-di-morrone-da-lon-enea-cam_grigio-mau-xam-size-42-6476ea0d4d744-31052023133245.jpg',22),(64,'8051516580960.2.jpg',23);
/*!40000 ALTER TABLE `product_images` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_size`
--

DROP TABLE IF EXISTS `product_size`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_size` (
  `ID` bigint NOT NULL AUTO_INCREMENT,
  `SOLD` int DEFAULT NULL,
  `IN_STOCK` int DEFAULT NULL,
  `PRODUCT_ID` bigint DEFAULT NULL,
  `SIZE_ID` bigint DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=81 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_size`
--

LOCK TABLES `product_size` WRITE;
/*!40000 ALTER TABLE `product_size` DISABLE KEYS */;
INSERT INTO `product_size` VALUES (7,2,50,1,2),(8,3,45,1,3),(9,3,59,1,4),(10,1,105,2,6),(11,2,50,2,8),(12,3,65,2,7),(13,1,100,3,3),(14,3,500,3,4),(15,6,35,3,5),(16,5,150,4,4),(17,2,200,4,2),(18,4,50,4,3),(19,5,45,4,5),(23,20,50,5,5),(24,2,50,5,6),(25,2,68,5,7),(26,7,50,6,4),(27,8,50,6,5),(28,8,53,6,6),(29,3,45,7,2),(30,5,45,7,6),(31,35,22,7,7),(32,23,235,8,2),(33,3,35,8,3),(34,33,52,8,5),(35,4,342,9,3),(36,46,634,9,1),(37,4,64,9,2),(38,6,34,9,9),(39,4,45,10,2),(40,6,346,10,4),(41,44,46,10,6),(42,3,335,11,5),(43,33,35,11,6),(44,25,355,11,7),(45,5,255,12,2),(46,4,356,12,5),(47,44,34,13,3),(48,46,63,13,5),(49,34,46,13,7),(50,23,35,14,6),(51,33,525,14,5),(52,355,55,14,4),(53,35,35,15,3),(54,32,255,15,5),(55,3,235,15,6),(56,35,35,16,3),(57,35,55,16,5),(58,355,33,16,7),(59,14,23,17,4),(60,42,24,17,5),(61,2,345,18,1),(62,35,53,18,4),(63,3,35,18,6),(64,53,35,19,2),(65,35,35,19,1),(66,235,23,19,3),(67,355,222,19,8),(68,52,35,20,4),(69,35,5,20,5),(70,355,55,20,7),(71,35,34,20,6),(72,33,23,21,2),(73,32,235,21,4),(74,35,53,21,3),(75,5,35,21,8),(76,35,52,22,3),(77,35,35,22,5),(78,32,35,22,6),(79,2,2,23,1),(80,2,2,23,2);
/*!40000 ALTER TABLE `product_size` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products` (
  `ID` bigint NOT NULL AUTO_INCREMENT COMMENT 'khóa chính',
  `NAME` varchar(256) NOT NULL,
  `COST` int NOT NULL,
  `PRICE` int NOT NULL,
  `MAIN_IMAGE` varchar(255) NOT NULL,
  `SHORT_DESCRIPTION` varchar(512) NOT NULL,
  `FULL_DESCRIPTION` varchar(4096) NOT NULL,
  `CREATED_TIME` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `UPDATED_TIME` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `DISCOUNT_PERCENT` float DEFAULT NULL,
  `BRAND_ID` bigint NOT NULL,
  `CATEGORY_ID` bigint NOT NULL,
  `PRICE_LEVEL` varchar(45) DEFAULT NULL,
  `STATUS` bit(1) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (1,'Giày Jordan Cao Cổ Phối Màu-Ghi Xám',900000,999000,'vn-11134207-7r98o-llxbyb8euqxr4b.jpg','Giày Jordan Cao Cổ Phối Màu-Ghi Xám, Giày Thể Thảo JD Cao Cổ Ghi Xám Trẻ Trung Năng Động 2023','Màu của giày thể thao sneaker nam nữ có thể sẽ chênh lệch thực tế một phần nhỏ, do ảnh hưởng về độ lệch màu của ánh sáng nhưng vẫn đảm bảo chất lượng. \r\nShop chỉ giải quyết những khiếu nại của khách hàng khi có bằng chứng rõ ràng (Quay video khi nhận hàng bóc hàng, phiếu giao hàng của bên shop..).\r\nQuý khách vui lòng đọc kỹ thông tin sản phẩm rồi quyết định mua hàng, chứ đừng đặt cho vui hoặc từ chối khi đã mua hàng, tội nghiệp shop và các bạn giao hàng lắm ạ ^^','2023-11-14 18:16:29','2023-11-14 18:38:32',10,3,6,'level 2',_binary ''),(2,'Giày Hở Gót MLB Playball Origin Mule',1000000,1140000,'giay-ho-got-mlb-playball-origin-mule-new-york-yankees-3amuua11n-50bks-mau-den-ss2022-62bc141725b03-29062022155759.jpg','Giày Hở Gót MLB Playball Origin Mule New York Yankees 3AMUUA11N-50BKS Màu Đen SS2022','Màu của giày thể thao sneaker nam nữ có thể sẽ chênh lệch thực tế một phần nhỏ, do ảnh hưởng về độ lệch màu của ánh sáng nhưng vẫn đảm bảo chất lượng. \r\n Shop chỉ giải quyết những khiếu nại của khách hàng khi có bằng chứng rõ ràng (Quay video khi nhận hàng bóc hàng, phiếu giao hàng của bên shop..).\r\n Quý khách vui lòng đọc kỹ thông tin sản phẩm rồi quyết định mua hàng, chứ đừng đặt cho vui hoặc từ chối khi đã mua hàng, tội nghiệp shop và các bạn giao hàng lắm ạ ^^','2023-11-14 18:44:11','2023-11-14 18:44:11',10,5,6,'level 3',_binary ''),(3,'Giày Thể Thao Lacoste',2000000,2500000,'giay-the-thao-lacoste-l001-123-4-sma-mau-trang-xanh-navy-size-41-63f5cf495745a-22022023151609.jpg','Giày Thể Thao Lacoste L001 123 4 SMA Màu Trắng Xanh Navy','Màu của giày thể thao sneaker nam nữ có thể sẽ chênh lệch thực tế một phần nhỏ, do ảnh hưởng về độ lệch màu của ánh sáng nhưng vẫn đảm bảo chất lượng. \r\n Shop chỉ giải quyết những khiếu nại của khách hàng khi có bằng chứng rõ ràng (Quay video khi nhận hàng bóc hàng, phiếu giao hàng của bên shop..).\r\n Quý khách vui lòng đọc kỹ thông tin sản phẩm rồi quyết định mua hàng, chứ đừng đặt cho vui hoặc từ chối khi đã mua hàng, tội nghiệp shop và các bạn giao hàng lắm ạ ^^','2023-11-14 18:47:37','2023-11-14 18:47:37',10,2,6,'level 3',_binary ''),(4,'Giày Lười Nam Gucci',8000000,10000000,'giay-luoi-nam-gucci-leather-signature-driver-mau-den-466904-size-42-62fa0a9c55cd8-15082022155804.jpg','Giày Lười Nam Gucci Leather Signature Driver Màu Đen 466904','Màu của giày thể thao sneaker nam nữ có thể sẽ chênh lệch thực tế một phần nhỏ, do ảnh hưởng về độ lệch màu của ánh sáng nhưng vẫn đảm bảo chất lượng. \r\n  Shop chỉ giải quyết những khiếu nại của khách hàng khi có bằng chứng rõ ràng (Quay video khi nhận hàng bóc hàng, phiếu giao hàng của bên shop..).\r\n  Quý khách vui lòng đọc kỹ thông tin sản phẩm rồi quyết định mua hàng, chứ đừng đặt cho vui hoặc từ chối khi đã mua hàng, tội nghiệp shop và các bạn giao hàng lắm ạ ^^','2023-11-14 18:51:16','2023-11-14 18:51:16',10,4,3,'level 4',_binary ''),(5,'Giày Sneaker Vans UA ',1500000,2000000,'giay-sneaker-vans-ua-style-36-vintage-pop-vn0a54f66qu-mau-xanh-trang-64e96e3c0e648-26082023101508.jpg','Giày Sneaker Vans UA Style 36 Vintage Pop - VN0A54F66QU Màu Xanh Trắng','Màu của giày thể thao sneaker nam nữ có thể sẽ chênh lệch thực tế một phần nhỏ, do ảnh hưởng về độ lệch màu của ánh sáng nhưng vẫn đảm bảo chất lượng. \r\n  Shop chỉ giải quyết những khiếu nại của khách hàng khi có bằng chứng rõ ràng (Quay video khi nhận hàng bóc hàng, phiếu giao hàng của bên shop..).\r\n  Quý khách vui lòng đọc kỹ thông tin sản phẩm rồi quyết định mua hàng, chứ đừng đặt cho vui hoặc từ chối khi đã mua hàng, tội nghiệp shop và các bạn giao hàng lắm ạ ^^','2023-11-14 18:53:45','2023-11-14 18:54:08',10,5,6,'level 3',_binary ''),(6,'Sandals Da Nam ',800000,900000,'0-19173103143.jpg','Sandals Da Nam Aokang 19173103143','Màu của giày thể thao sneaker nam nữ có thể sẽ chênh lệch thực tế một phần nhỏ, do ảnh hưởng về độ lệch màu của ánh sáng nhưng vẫn đảm bảo chất lượng. \r\n  Shop chỉ giải quyết những khiếu nại của khách hàng khi có bằng chứng rõ ràng (Quay video khi nhận hàng bóc hàng, phiếu giao hàng của bên shop..).\r\n  Quý khách vui lòng đọc kỹ thông tin sản phẩm rồi quyết định mua hàng, chứ đừng đặt cho vui hoặc từ chối khi đã mua hàng, tội nghiệp shop và các bạn giao hàng lắm ạ ^^','2023-11-14 18:57:26','2023-11-14 18:57:26',10,1,5,'level 2',_binary ''),(7,'Sandals Geox ',1500000,2000000,'sandals-geox-j-borealis-g-b-lycra-dbk-mau-xanh-bien-phoi-tim-size-32-64634ddb66575-16052023163315.jpg','Sandals Geox J BOREALIS G. B LYCRA+DBK Màu Xanh Biển Phối Tím','Màu của giày thể thao sneaker nam nữ có thể sẽ chênh lệch thực tế một phần nhỏ, do ảnh hưởng về độ lệch màu của ánh sáng nhưng vẫn đảm bảo chất lượng. \r\n  Shop chỉ giải quyết những khiếu nại của khách hàng khi có bằng chứng rõ ràng (Quay video khi nhận hàng bóc hàng, phiếu giao hàng của bên shop..).\r\n  Quý khách vui lòng đọc kỹ thông tin sản phẩm rồi quyết định mua hàng, chứ đừng đặt cho vui hoặc từ chối khi đã mua hàng, tội nghiệp shop và các bạn giao hàng lắm ạ ^^','2023-11-14 19:00:32','2023-11-14 19:00:32',10,4,5,'level 3',_binary ''),(9,'Giày Boot Celine Margaret Chelsea',15000000,16000000,'giay-boot-celine-margaret-chelsea-boot-in-calfskin-black-mau-den-63a66ab229bb2-24122022095754.jpg','Giày Boot Celine Margaret Chelsea Boot In Calfskin Black Màu Đen','Màu của giày thể thao sneaker nam nữ có thể sẽ chênh lệch thực tế một phần nhỏ, do ảnh hưởng về độ lệch màu của ánh sáng nhưng vẫn đảm bảo chất lượng. \r\n  Shop chỉ giải quyết những khiếu nại của khách hàng khi có bằng chứng rõ ràng (Quay video khi nhận hàng bóc hàng, phiếu giao hàng của bên shop..).\r\n  Quý khách vui lòng đọc kỹ thông tin sản phẩm rồi quyết định mua hàng, chứ đừng đặt cho vui hoặc từ chối khi đã mua hàng, tội nghiệp shop và các bạn giao hàng lắm ạ ^^','2023-11-14 19:06:28','2023-11-14 19:06:28',10,2,1,'level 4',_binary ''),(10,'Giày Boot Duca Di Morrone',1200000,1500000,'giay-boot-duca-di-morrone-noah_taupe-mau-nau-size-41-636dd2dabaff7-11112022114306.jpg','Giày Boot Duca Di Morrone NOAH_TAUPE Màu Nâu','Màu của giày thể thao sneaker nam nữ có thể sẽ chênh lệch thực tế một phần nhỏ, do ảnh hưởng về độ lệch màu của ánh sáng nhưng vẫn đảm bảo chất lượng. \r\n  Shop chỉ giải quyết những khiếu nại của khách hàng khi có bằng chứng rõ ràng (Quay video khi nhận hàng bóc hàng, phiếu giao hàng của bên shop..).\r\n  Quý khách vui lòng đọc kỹ thông tin sản phẩm rồi quyết định mua hàng, chứ đừng đặt cho vui hoặc từ chối khi đã mua hàng, tội nghiệp shop và các bạn giao hàng lắm ạ ^^','2023-11-14 19:08:40','2023-11-14 19:08:40',10,3,1,'level 3',_binary ''),(11,'Giày Boot Nam Germano Bellesi',1200000,1500000,'giay-boot-duca-di-morrone-juri-cam_taupe-mau-nau-size-40-6364e075f266a-04112022165045.jpg','Giày Boot Nam Germano Bellesi 796 Màu Đen','Màu của giày thể thao sneaker nam nữ có thể sẽ chênh lệch thực tế một phần nhỏ, do ảnh hưởng về độ lệch màu của ánh sáng nhưng vẫn đảm bảo chất lượng. \r\n  Shop chỉ giải quyết những khiếu nại của khách hàng khi có bằng chứng rõ ràng (Quay video khi nhận hàng bóc hàng, phiếu giao hàng của bên shop..).\r\n  Quý khách vui lòng đọc kỹ thông tin sản phẩm rồi quyết định mua hàng, chứ đừng đặt cho vui hoặc từ chối khi đã mua hàng, tội nghiệp shop và các bạn giao hàng lắm ạ ^^','2023-11-14 19:11:06','2023-11-14 19:11:06',10,4,1,'level 3',_binary ''),(12,'Giày Boot Nam Duca Di Morrone',1500000,1800000,'giay-boot-nam-duca-di-morrone-noah_black-mau-den-size-41-64e02d76e3f37-19082023094822.jpg','Giày Boot Nam Duca Di Morrone NOAH_BLACK Màu Đen ','Màu của giày thể thao sneaker nam nữ có thể sẽ chênh lệch thực tế một phần nhỏ, do ảnh hưởng về độ lệch màu của ánh sáng nhưng vẫn đảm bảo chất lượng. \r\n  Shop chỉ giải quyết những khiếu nại của khách hàng khi có bằng chứng rõ ràng (Quay video khi nhận hàng bóc hàng, phiếu giao hàng của bên shop..).\r\n  Quý khách vui lòng đọc kỹ thông tin sản phẩm rồi quyết định mua hàng, chứ đừng đặt cho vui hoặc từ chối khi đã mua hàng, tội nghiệp shop và các bạn giao hàng lắm ạ ^^','2023-11-14 19:12:54','2023-11-14 19:12:54',10,5,1,'level 3',_binary ''),(13,'Giày Tây Be Classy Charles Captoe Oxford',1600000,2000000,'giay-tay-be-classy-charles-captoe-oxford-of02-mau-nau-63b63f70f00af-05012023100936.jpg','Giày Tây Be Classy Charles Captoe Oxford - OF02 Màu Nâu','Màu của giày thể thao sneaker nam nữ có thể sẽ chênh lệch thực tế một phần nhỏ, do ảnh hưởng về độ lệch màu của ánh sáng nhưng vẫn đảm bảo chất lượng. \r\n  Shop chỉ giải quyết những khiếu nại của khách hàng khi có bằng chứng rõ ràng (Quay video khi nhận hàng bóc hàng, phiếu giao hàng của bên shop..).\r\n  Quý khách vui lòng đọc kỹ thông tin sản phẩm rồi quyết định mua hàng, chứ đừng đặt cho vui hoặc từ chối khi đã mua hàng, tội nghiệp shop và các bạn giao hàng lắm ạ ^^','2023-11-14 19:15:38','2023-11-14 19:15:38',10,2,4,'level 3',_binary ''),(14,'Giày Tây Be Classy Classic Brogues Derby',1500000,1900000,'giay-tay-be-classy-classic-brogues-derby-db18-mau-den-63b639500decc-05012023094328.jpg','Giày Tây Be Classy Classic Brogues Derby - DB18 Màu Đen','Màu của giày thể thao sneaker nam nữ có thể sẽ chênh lệch thực tế một phần nhỏ, do ảnh hưởng về độ lệch màu của ánh sáng nhưng vẫn đảm bảo chất lượng. \r\n  Shop chỉ giải quyết những khiếu nại của khách hàng khi có bằng chứng rõ ràng (Quay video khi nhận hàng bóc hàng, phiếu giao hàng của bên shop..).\r\n  Quý khách vui lòng đọc kỹ thông tin sản phẩm rồi quyết định mua hàng, chứ đừng đặt cho vui hoặc từ chối khi đã mua hàng, tội nghiệp shop và các bạn giao hàng lắm ạ ^^','2023-11-14 19:17:23','2023-11-14 19:17:23',10,5,4,'level 3',_binary ''),(15,'Giày Tây Nam Germano Bellesi',9000000,9700000,'giay-tay-nam-germano-bellesi-1089-smoth-mau-den-63a13b4c6a1db-20122022113420.jpg','Giày Tây Nam Germano Bellesi 1089 SMOTH Màu Đen','Màu của giày thể thao sneaker nam nữ có thể sẽ chênh lệch thực tế một phần nhỏ, do ảnh hưởng về độ lệch màu của ánh sáng nhưng vẫn đảm bảo chất lượng. \r\n  Shop chỉ giải quyết những khiếu nại của khách hàng khi có bằng chứng rõ ràng (Quay video khi nhận hàng bóc hàng, phiếu giao hàng của bên shop..).\r\n  Quý khách vui lòng đọc kỹ thông tin sản phẩm rồi quyết định mua hàng, chứ đừng đặt cho vui hoặc từ chối khi đã mua hàng, tội nghiệp shop và các bạn giao hàng lắm ạ ^^','2023-11-14 19:20:06','2023-11-14 19:20:06',10,5,4,'level 4',_binary ''),(16,'Giày Tây Nam Germano Bellesi ',5000000,6000000,'giay-tay-nam-germano-bellesi-1313-mau-nau-63a503bc06311-23122022082620.jpg','Giày Tây Nam Germano Bellesi 1313 Màu Nâu','Màu của giày thể thao sneaker nam nữ có thể sẽ chênh lệch thực tế một phần nhỏ, do ảnh hưởng về độ lệch màu của ánh sáng nhưng vẫn đảm bảo chất lượng. \r\n  Shop chỉ giải quyết những khiếu nại của khách hàng khi có bằng chứng rõ ràng (Quay video khi nhận hàng bóc hàng, phiếu giao hàng của bên shop..).\r\n  Quý khách vui lòng đọc kỹ thông tin sản phẩm rồi quyết định mua hàng, chứ đừng đặt cho vui hoặc từ chối khi đã mua hàng, tội nghiệp shop và các bạn giao hàng lắm ạ ^^','2023-11-14 19:21:25','2023-11-14 19:21:25',10,3,4,'level 4',_binary ''),(17,'Giày Da Nam Germano Bellesi',2000000,2500000,'giay-luoi-nam-germano-bellesi-da-lon-318-mau-vang-63abadfe5f96d-28122022094622.jpg','Giày Lười Nam Germano Bellesi Da Lộn 318 Màu Vàng','Màu của giày thể thao sneaker nam nữ có thể sẽ chênh lệch thực tế một phần nhỏ, do ảnh hưởng về độ lệch màu của ánh sáng nhưng vẫn đảm bảo chất lượng. \r\n  Shop chỉ giải quyết những khiếu nại của khách hàng khi có bằng chứng rõ ràng (Quay video khi nhận hàng bóc hàng, phiếu giao hàng của bên shop..).\r\n  Quý khách vui lòng đọc kỹ thông tin sản phẩm rồi quyết định mua hàng, chứ đừng đặt cho vui hoặc từ chối khi đã mua hàng, tội nghiệp shop và các bạn giao hàng lắm ạ ^^','2023-11-14 19:24:42','2023-11-14 19:24:42',10,5,2,'level 3',_binary ''),(18,'Giày Da Nam Germano Bellesi',2000000,2400000,'giay-luoi-nam-germano-bellesi-da-lon-287-mau-nau-63abc00318a7c-28122022110315.jpg','Giày Da Nam Germano Bellesi Da Lộn 287 Màu Nâu','Màu của giày thể thao sneaker nam nữ có thể sẽ chênh lệch thực tế một phần nhỏ, do ảnh hưởng về độ lệch màu của ánh sáng nhưng vẫn đảm bảo chất lượng. \r\n  Shop chỉ giải quyết những khiếu nại của khách hàng khi có bằng chứng rõ ràng (Quay video khi nhận hàng bóc hàng, phiếu giao hàng của bên shop..).\r\n  Quý khách vui lòng đọc kỹ thông tin sản phẩm rồi quyết định mua hàng, chứ đừng đặt cho vui hoặc từ chối khi đã mua hàng, tội nghiệp shop và các bạn giao hàng lắm ạ ^^','2023-11-14 19:26:16','2023-11-14 19:26:16',10,4,2,'level 3',_binary ''),(19,'Giày Da Unisex ',1200000,1500000,'giay-tay-unisex-made-in-italia-da-lon-il-cielo_taupe-mau-nau-size-41-62e22adf50e88-28072022132119.jpg','Giày Da Unisex Made In Italy Da Lộn Il-Cielo_Taupe Màu Nâu','Màu của giày thể thao sneaker nam nữ có thể sẽ chênh lệch thực tế một phần nhỏ, do ảnh hưởng về độ lệch màu của ánh sáng nhưng vẫn đảm bảo chất lượng. \r\n  Shop chỉ giải quyết những khiếu nại của khách hàng khi có bằng chứng rõ ràng (Quay video khi nhận hàng bóc hàng, phiếu giao hàng của bên shop..).\r\n  Quý khách vui lòng đọc kỹ thông tin sản phẩm rồi quyết định mua hàng, chứ đừng đặt cho vui hoặc từ chối khi đã mua hàng, tội nghiệp shop và các bạn giao hàng lắm ạ ^^','2023-11-14 19:28:28','2023-11-14 19:28:28',10,2,2,'level 3',_binary ''),(20,'Giày Lười Nam Duca Di Morrone',2000000,2500000,'giay-luoi-nam-duca-di-morrone-da-lon-elia-cam_jeans-mau-xanh-size-40-64df3728cf5b5-18082023161728.jpg','Giày Lười Nam Duca Di Morrone Da Lộn ELIA-CAM_JEANS Màu Xanh','Màu của giày thể thao sneaker nam nữ có thể sẽ chênh lệch thực tế một phần nhỏ, do ảnh hưởng về độ lệch màu của ánh sáng nhưng vẫn đảm bảo chất lượng. \r\n  Shop chỉ giải quyết những khiếu nại của khách hàng khi có bằng chứng rõ ràng (Quay video khi nhận hàng bóc hàng, phiếu giao hàng của bên shop..).\r\n  Quý khách vui lòng đọc kỹ thông tin sản phẩm rồi quyết định mua hàng, chứ đừng đặt cho vui hoặc từ chối khi đã mua hàng, tội nghiệp shop và các bạn giao hàng lắm ạ ^^','2023-11-14 19:31:59','2023-11-14 19:31:59',10,1,3,'level 3',_binary ''),(21,'Giày Lười Nam Duca Di Morrone',200000,450000,'giay-luoi-nam-duca-di-morrone-da-lon-elia-cam_tortora-mau-nau-size-40-64e01d862f048-19082023084022.jpg','Giày Lười Nam Duca Di Morrone Da Lộn ELIA-CAM_TORTORA Màu Nâu ','Màu của giày thể thao sneaker nam nữ có thể sẽ chênh lệch thực tế một phần nhỏ, do ảnh hưởng về độ lệch màu của ánh sáng nhưng vẫn đảm bảo chất lượng. \r\n  Shop chỉ giải quyết những khiếu nại của khách hàng khi có bằng chứng rõ ràng (Quay video khi nhận hàng bóc hàng, phiếu giao hàng của bên shop..).\r\n  Quý khách vui lòng đọc kỹ thông tin sản phẩm rồi quyết định mua hàng, chứ đừng đặt cho vui hoặc từ chối khi đã mua hàng, tội nghiệp shop và các bạn giao hàng lắm ạ ^^','2023-11-14 19:33:33','2023-11-14 19:33:33',10,1,3,'level 1',_binary ''),(22,'Giày Lười Nam Duca Di Morrone',200000,250000,'giay-luoi-nam-duca-di-morrone-da-lon-enea-cam_grigio-mau-ghi-size-40-6476adbcc17aa-31052023091524.jpg','Giày Lười Nam Duca Di Morrone Da Lộn Enea-Cam_Grigio Màu Xám','Màu của giày thể thao sneaker nam nữ có thể sẽ chênh lệch thực tế một phần nhỏ, do ảnh hưởng về độ lệch màu của ánh sáng nhưng vẫn đảm bảo chất lượng. \r\n  Shop chỉ giải quyết những khiếu nại của khách hàng khi có bằng chứng rõ ràng (Quay video khi nhận hàng bóc hàng, phiếu giao hàng của bên shop..).\r\n  Quý khách vui lòng đọc kỹ thông tin sản phẩm rồi quyết định mua hàng, chứ đừng đặt cho vui hoặc từ chối khi đã mua hàng, tội nghiệp shop và các bạn giao hàng lắm ạ ^^','2023-11-14 19:35:34','2023-11-14 19:35:34',10,1,3,'level 1',_binary ''),(23,'test',3,3,'8051516580960.3.jpg','y','l','2023-11-15 03:14:51','2023-11-15 03:14:51',3,2,2,'level 1',_binary '');
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sizes`
--

DROP TABLE IF EXISTS `sizes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sizes` (
  `ID` bigint NOT NULL AUTO_INCREMENT,
  `NAME` varchar(45) DEFAULT NULL,
  `STATUS` bit(1) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sizes`
--

LOCK TABLES `sizes` WRITE;
/*!40000 ALTER TABLE `sizes` DISABLE KEYS */;
INSERT INTO `sizes` VALUES (1,'35',_binary ''),(2,'36',_binary ''),(3,'37',_binary ''),(4,'38',_binary ''),(5,'39',_binary ''),(6,'40',_binary ''),(7,'41',_binary ''),(8,'42',_binary ''),(9,'43',_binary ''),(10,'44',_binary ''),(11,'45',_binary '');
/*!40000 ALTER TABLE `sizes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `USERNAME` varchar(45) NOT NULL,
  `PASSWORD` varchar(255) DEFAULT NULL,
  `FULL_NAME` varchar(45) DEFAULT NULL,
  `ADDRESS` varchar(45) DEFAULT NULL,
  `PHONE` varchar(45) DEFAULT NULL,
  `EMAIL` varchar(45) DEFAULT NULL,
  `STATUS` bit(1) DEFAULT NULL,
  `SEX` int DEFAULT NULL,
  `AVATAR` varchar(255) DEFAULT NULL,
  `ROLE` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `USERNAME_UNIQUE` (`USERNAME`),
  UNIQUE KEY `PHONE_UNIQUE` (`PHONE`),
  UNIQUE KEY `EMAIL_UNIQUE` (`EMAIL`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'admin','$2a$10$79jNPDhLNDDB1bNjVp3ZJuNxo2DySdEWqTZHgl8ZK9GYIyFZoQlmS','Trịnh Quát','Hà Nội','0988856466','trinhquat99@gmail.com',_binary '',1,'tải xuống.jpg','ADMIN'),(2,'tonvu','$2a$10$IRKJnAbyJ//tE.ZP/uc32umj7tTDSRFpnqlaiDMbf6hERHCpIWmu6','Vũ Tôn','Hà Nội','0969975315','tonvu25@gmail.com',_binary '',1,'images.jpg','USER'),(3,'truong','$2a$10$IRKJnAbyJ//tE.ZP/uc32umj7tTDSRFpnqlaiDMbf6hERHCpIWmu6','Trường Vũ','Hà Nội','0969975365','truong@gmail.com',_binary '',1,'tải xuống (1).jpg','USER');
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

-- Dump completed on 2023-11-15 14:15:44
