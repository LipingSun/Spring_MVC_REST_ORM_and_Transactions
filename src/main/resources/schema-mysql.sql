DROP DATABASE IF EXISTS `CMPE275LAB2`;
CREATE DATABASE IF NOT EXISTS `CMPE275LAB2`;
USE `CMPE275LAB2`;

DROP TABLE IF EXISTS `ORGANIZATION`;
CREATE TABLE `ORGANIZATION` (
  `org_id`      INT(11) NOT NULL AUTO_INCREMENT,
  `name`        VARCHAR(45)      DEFAULT NULL,
  `description` VARCHAR(45)      DEFAULT NULL,
  `street`      VARCHAR(45)      DEFAULT NULL,
  `city`        VARCHAR(45)      DEFAULT NULL,
  `state`       VARCHAR(45)      DEFAULT NULL,
  `zip`         VARCHAR(45)      DEFAULT NULL,
  PRIMARY KEY (`org_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS `PERSON`;
CREATE TABLE `PERSON` (
  `person_id`   INT(11) NOT NULL AUTO_INCREMENT,
  `firstname`   VARCHAR(45)      DEFAULT NULL,
  `lastname`    VARCHAR(45)      DEFAULT NULL,
  `email`       VARCHAR(45)      DEFAULT NULL UNIQUE,
  `description` VARCHAR(45)      DEFAULT NULL,
  `street`      VARCHAR(45)      DEFAULT NULL,
  `city`        VARCHAR(45)      DEFAULT NULL,
  `state`       VARCHAR(45)      DEFAULT NULL,
  `zip`         VARCHAR(45)      DEFAULT NULL,
  `org_id`      INT(11)          DEFAULT NULL,
  PRIMARY KEY (`person_id`),
  KEY `org_id_idx` (`org_id`),
  CONSTRAINT `org_id` FOREIGN KEY (`org_id`) REFERENCES `ORGANIZATION` (`org_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS `FRIENDSHIP`;
CREATE TABLE `FRIENDSHIP` (
  `person1_id` INT(11) NOT NULL,
  `person2_id` INT(11) NOT NULL,
  KEY `person1_id_idx` (`person1_id`),
  KEY `person2_id_idx` (`person2_id`),
  CONSTRAINT `person1_id` FOREIGN KEY (`person1_id`) REFERENCES `PERSON` (`person_id`),
  CONSTRAINT `person2_id` FOREIGN KEY (`person2_id`) REFERENCES `PERSON` (`person_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;