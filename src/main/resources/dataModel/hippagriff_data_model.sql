/*
 Navicat MariaDB Data Transfer

 Source Server         : local
 Source Server Version : 100013
 Source Host           : localhost
 Source Database       : hippagriff

 Target Server Version : 100013
 File Encoding         : utf-8

 Date: 09/16/2014 13:33:08 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `Organization`
-- ----------------------------
DROP TABLE IF EXISTS `Organization`;
CREATE TABLE `Organization` (
  `organization_id` varchar(255) NOT NULL,
  `parent_organization_id` varchar(255) NOT NULL,
  `created_date` datetime NOT NULL,
  `updated_date` datetime NOT NULL,
  `status` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`organization_id`),
  KEY `organization_id` (`organization_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `Patient`
-- ----------------------------
DROP TABLE IF EXISTS `Patient`;
CREATE TABLE `Patient` (
  `patient_mpi` varchar(100) NOT NULL COMMENT 'Patient Identifier in the application',
  `created_date` datetime NOT NULL COMMENT 'Indicates when this record was Created',
  `updated_date` datetime DEFAULT NULL COMMENT 'Indicates when this record was last updated',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'Indicates if the Patient is active or not. If Active then set to 1 else 0',
  PRIMARY KEY (`patient_mpi`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `Patient_System`
-- ----------------------------
DROP TABLE IF EXISTS `Patient_System`;
CREATE TABLE `Patient_System` (
  `patient_system_id` varchar(100) NOT NULL,
  `patient_mpi` varchar(100) NOT NULL,
  `system_id` varchar(255) NOT NULL,
  `organization_id` varchar(255) NOT NULL,
  `patient_mrn` varchar(255) NOT NULL,
  `patient_first_name` varchar(255) NOT NULL,
  `patient_last_name` varchar(255) NOT NULL,
  `patient_middle_name` varchar(255) DEFAULT NULL,
  `patient_prefix_name` varchar(255) DEFAULT NULL,
  `patient_suffix_name` varchar(255) DEFAULT NULL,
  `created_date` datetime NOT NULL,
  `updated_date` datetime NOT NULL,
  `status` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`patient_system_id`),
  KEY `mpi` (`patient_system_id`),
  KEY `patient_mpi` (`patient_mpi`),
  KEY `organization_id` (`organization_id`),
  CONSTRAINT `patient_system_system_id` FOREIGN KEY (`patient_system_id`) REFERENCES `System` (`system_id`),
  CONSTRAINT `patient_system_organization_id` FOREIGN KEY (`organization_id`) REFERENCES `Organization` (`organization_id`),
  CONSTRAINT `patient_system_ibfk_1` FOREIGN KEY (`patient_mpi`) REFERENCES `Patient` (`patient_mpi`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `System`
-- ----------------------------
DROP TABLE IF EXISTS `System`;
CREATE TABLE `System` (
  `system_id` varchar(255) NOT NULL,
  `created_date` datetime NOT NULL,
  `updated_date` datetime NOT NULL,
  `status` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`system_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;
