-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versión del servidor:         11.6.2-MariaDB - mariadb.org binary distribution
-- SO del servidor:              Win64
-- HeidiSQL Versión:             12.8.0.6908
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Volcando estructura de base de datos para tenfe
CREATE DATABASE IF NOT EXISTS `tenfe` /*!40100 DEFAULT CHARACTER SET armscii8 COLLATE armscii8_bin */;
USE `tenfe`;

-- Volcando estructura para tabla tenfe.companyia
CREATE TABLE IF NOT EXISTS `companyia` (
  `id` int(11) NOT NULL,
  `nom` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=armscii8 COLLATE=armscii8_bin;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla tenfe.conductor
CREATE TABLE IF NOT EXISTS `conductor` (
  `id` int(11) NOT NULL,
  `nom` varchar(50) DEFAULT NULL,
  `companyia` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_conductor_companyia` (`companyia`),
  CONSTRAINT `FK_conductor_companyia` FOREIGN KEY (`companyia`) REFERENCES `companyia` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=armscii8 COLLATE=armscii8_bin;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla tenfe.estacio
CREATE TABLE IF NOT EXISTS `estacio` (
  `id` int(11) NOT NULL,
  `nom` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=armscii8 COLLATE=armscii8_bin;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla tenfe.maquinista
CREATE TABLE IF NOT EXISTS `maquinista` (
  `id` int(11) NOT NULL,
  `nom` varchar(50) DEFAULT NULL,
  `companyia` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_maquinista_companyia` (`companyia`),
  CONSTRAINT `FK_maquinista_companyia` FOREIGN KEY (`companyia`) REFERENCES `companyia` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=armscii8 COLLATE=armscii8_bin;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla tenfe.trajecte
CREATE TABLE IF NOT EXISTS `trajecte` (
  `id` int(11) NOT NULL,
  `nom` varchar(50) DEFAULT NULL,
  `estacioInicialId` int(11) DEFAULT NULL,
  `estacioFinalId` int(11) DEFAULT NULL,
  `preu` double DEFAULT NULL,
  `parades` int(11) DEFAULT NULL,
  `companyiaId` int(11) DEFAULT NULL,
  `horaSortida` int(11) DEFAULT NULL,
  `horaArribada` int(11) DEFAULT NULL,
  `idTren` int(11) DEFAULT NULL,
  `idMaquinista` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_trajecte_estacio_inici` (`estacioInicialId`),
  KEY `FK_trajecte_estacio_final` (`estacioFinalId`),
  KEY `FK_trajecte_companyia` (`companyiaId`),
  KEY `FK_trajecte_tren` (`idTren`),
  KEY `FK_trajecte_maquinista` (`idMaquinista`),
  CONSTRAINT `FK_trajecte_companyia` FOREIGN KEY (`companyiaId`) REFERENCES `companyia` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_trajecte_estacio_final` FOREIGN KEY (`estacioFinalId`) REFERENCES `estacio` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_trajecte_estacio_inici` FOREIGN KEY (`estacioInicialId`) REFERENCES `estacio` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_trajecte_maquinista` FOREIGN KEY (`idMaquinista`) REFERENCES `maquinista` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_trajecte_tren` FOREIGN KEY (`idTren`) REFERENCES `tren` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=armscii8 COLLATE=armscii8_bin;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla tenfe.tren
CREATE TABLE IF NOT EXISTS `tren` (
  `id` int(11) NOT NULL,
  `nom` varchar(50) DEFAULT NULL,
  `companyia` int(11) DEFAULT NULL,
  `capacitat` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_tren_companyia` (`companyia`),
  CONSTRAINT `FK_tren_companyia` FOREIGN KEY (`companyia`) REFERENCES `companyia` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=armscii8 COLLATE=armscii8_bin;

-- La exportación de datos fue deseleccionada.

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
