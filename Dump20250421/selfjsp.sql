-- MariaDB dump 10.17  Distrib 10.4.6-MariaDB, for Win64 (AMD64)
--
-- Host: localhost    Database: selfjsp
-- ------------------------------------------------------
-- Server version	8.0.19

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `address`
--

DROP TABLE IF EXISTS `address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `address` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `address` varchar(150) DEFAULT NULL,
  `tel` varchar(20) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `address`
--

LOCK TABLES `address` WRITE;
/*!40000 ALTER TABLE `address` DISABLE KEYS */;
INSERT INTO `address` VALUES (1,'山田太郎','東京都東京市南町1-1-1','03-9999-XXXX','yamada@example.com'),(2,'鈴木浩二','神奈川県松戸市金町2-4-5','04-1111-XXXX','suzuki@example.com'),(3,'井上春子','埼玉県浦安市本町4-7-9','04-2222-XXXX','inoue@example.com'),(4,'佐々木のぞみ','千葉県横浜市東町1-2-3','04-3333-XXXX','sasaki@example.com'),(5,'川本健三','埼玉県本庄市春日町2-4-3','04-4444-XXXX','kawamoto@example.com'),(6,'佐藤洋一','東京都南東京市白町3-1-2','03-8888-XXXX','satou@example.com'),(7,'田中洋子','茨城県武蔵野市東町4-1-2','04-5555-XXXX','tanaka@example.com'),(8,'中川雄一','茨城県横浜市芳野町2-3-2','04-6666-XXXX','nakagawa@example.com'),(9,'日村和也','東京都千葉市上町1-4-3','03-7777-XXXX','himura@example.com'),(10,'松田幸子','東京都東京市北町2-2-3','03-6666-XXXX','matsuda@example.com');
/*!40000 ALTER TABLE `address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `book`
--

DROP TABLE IF EXISTS `book`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `book` (
  `isbn` char(17) NOT NULL,
  `title` varchar(100) DEFAULT NULL,
  `price` int DEFAULT NULL,
  `publish` varchar(20) DEFAULT NULL,
  `published` date DEFAULT NULL,
  PRIMARY KEY (`isbn`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `book`
--

LOCK TABLES `book` WRITE;
/*!40000 ALTER TABLE `book` DISABLE KEYS */;
INSERT INTO `book` VALUES ('978-4-7980-5759-0','はじめてのAndroidアプリ開発',3200,'秀和システム','2019-08-10'),('978-4-7980-5804-7','はじめてのASP.NET Webフォームアプリ開発',3200,'秀和システム','2019-10-12'),('978-4-7981-5112-0','独習Java 新版',3150,'翔泳社','2019-05-15'),('978-4-7981-5757-3','JavaScript逆引きレシピ',2730,'翔泳社','2018-10-15'),('978-4-7981-6044-3','Androidアプリ開発の教科書',2850,'翔泳社','2019-07-10'),('978-4-7981-6365-9','独習ASP.NET Webフォーム',3800,'翔泳社','2020-02-17'),('978-4-8026-1226-5','SQLデータ分析・活用入門',2600,'ソシム','2019-09-12'),('978-4-8156-0182-9','これからはじめるVue.js実践入門',3990,'SBクリエイティブ','2019-08-22'),('978-4-8222-5391-2','iPhoneアプリ超入門',2200,'日経BP','2020-02-28'),('978-4-8222-8653-8','基礎からしっかり学ぶC#の教科書',2900,'日経BP','2019-12-20');
/*!40000 ALTER TABLE `book` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usr`
--

DROP TABLE IF EXISTS `usr`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usr` (
  `uid` char(10) NOT NULL,
  `passwd` char(128) DEFAULT NULL,
  `unam` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usr`
--

LOCK TABLES `usr` WRITE;
/*!40000 ALTER TABLE `usr` DISABLE KEYS */;
INSERT INTO `usr` VALUES ('rsuzuki','3627909a29c31381a071ec27f7c9ca97726182aed29a7ddd2e54353322cfb30abb9e3a6df2ac2c20fe23436311d678564d0c8d305930575f60e2d3d048184d79','鈴木良子'),('sfujii','3627909a29c31381a071ec27f7c9ca97726182aed29a7ddd2e54353322cfb30abb9e3a6df2ac2c20fe23436311d678564d0c8d305930575f60e2d3d048184d79','藤井正一'),('tyamada','3627909a29c31381a071ec27f7c9ca97726182aed29a7ddd2e54353322cfb30abb9e3a6df2ac2c20fe23436311d678564d0c8d305930575f60e2d3d048184d79','山田太郎');
/*!40000 ALTER TABLE `usr` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usr_role`
--

DROP TABLE IF EXISTS `usr_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usr_role` (
  `uid` char(10) NOT NULL DEFAULT '',
  `role` varchar(30) NOT NULL DEFAULT '',
  PRIMARY KEY (`uid`,`role`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usr_role`
--

LOCK TABLES `usr_role` WRITE;
/*!40000 ALTER TABLE `usr_role` DISABLE KEYS */;
INSERT INTO `usr_role` VALUES ('rsuzuki','admin-gui'),('sfujii','manager-gui'),('tyamada','admin-gui'),('tyamada','manager-gui');
/*!40000 ALTER TABLE `usr_role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-04-02 16:49:01
