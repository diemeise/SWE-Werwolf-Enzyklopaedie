-- MySQL dump 10.13  Distrib 8.0.27, for Win64 (x86_64)
--
-- Host: localhost    Database: werwolf
-- ------------------------------------------------------
-- Server version	8.0.27

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
-- Table structure for table `regeln`
--

DROP TABLE IF EXISTS `regeln`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `regeln` (
  `idRegeln` varchar(45) NOT NULL,
  `Regelname` varchar(45) DEFAULT NULL,
  `Regelbeschreibung` varchar(255) DEFAULT NULL,
  `Rolle_idRolle` varchar(45) NOT NULL,
  PRIMARY KEY (`idRegeln`,`Rolle_idRolle`),
  UNIQUE KEY `Rolle_idRolle_UNIQUE` (`Rolle_idRolle`),
  UNIQUE KEY `Rolle_idRolle` (`Rolle_idRolle`),
  KEY `fk_Regeln_Rolle1_idx` (`Rolle_idRolle`),
  CONSTRAINT `fk_Regeln_Rolle1` FOREIGN KEY (`Rolle_idRolle`) REFERENCES `rolle` (`idRolle`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `regeln`
--

LOCK TABLES `regeln` WRITE;
/*!40000 ALTER TABLE `regeln` DISABLE KEYS */;
INSERT INTO `regeln` VALUES ('k_wolf','Werwolf Regel','Werwölfe wachen Nachts auf und suchen sich ein Opfer (einstimmig). Das Opfer wacht am nächsten Morgen nicht auf (Ausnahme: wurde geschützt durch Sonderrollen)','b0_wolf');
/*!40000 ALTER TABLE `regeln` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-11-18 15:52:48
