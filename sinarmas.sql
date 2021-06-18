# Host: localhost  (Version 5.5.5-10.1.38-MariaDB)
# Date: 2021-06-18 09:53:37
# Generator: MySQL-Front 6.0  (Build 2.20)


#
# Structure for table "driver"
#

DROP TABLE IF EXISTS `driver`;
CREATE TABLE `driver` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Lat` double(5,2) DEFAULT NULL,
  `Lon` double(5,2) DEFAULT NULL,
  `Status` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

#
# Data for table "driver"
#

INSERT INTO `driver` VALUES (1,3.10,100.00,'Active'),(2,3.00,101.00,'Active'),(3,2.90,102.00,'Off'),(4,1.80,99.00,'Active'),(5,4.10,98.00,'Active');

#
# Structure for table "hibernate_sequence"
#

DROP TABLE IF EXISTS `hibernate_sequence`;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

#
# Data for table "hibernate_sequence"
#

INSERT INTO `hibernate_sequence` VALUES (1);
