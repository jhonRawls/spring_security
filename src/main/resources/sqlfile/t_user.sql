/*
Navicat MySQL Data Transfer

Source Server         : local-dev
Source Server Version : 50554
Source Host           : localhost:3306
Source Database       : testdb

Target Server Type    : MYSQL
Target Server Version : 50554
File Encoding         : 65001

Date: 2017-06-06 11:18:53
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `userName` varchar(255) DEFAULT NULL,
  `passWord` varchar(255) DEFAULT NULL,
  `userId` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user
-- ----------------------------

INSERT INTO `t_user` VALUES ('abc', '$2a$10$ZgABIRDeYnFuk7GX2JOXzOxE798ANfJHelyKSGtnvGKnvAHxFuYPq', '4');
INSERT INTO `t_user` VALUES ('123', '$2a$10$76sUW6F9c40sem8Ne6uxiuvExR3oJfnqKJFa8OSCqm6F4QrCsCCSK', '5');
INSERT INTO `t_user` VALUES ('456', '$2a$10$iGDqAeN2SmZZAhYCiIscA.S3N14B8QM1q/a2E3U5CD9.aZuAx.Cee', '6');
INSERT INTO `t_user` VALUES ('789', '$2a$10$7a.MXdYHKUyFcIexrfdsFuy9W2Pfpjh8jqd68K0WzP//D.P9xxJGS', '7');
INSERT INTO `t_user` VALUES ('159', '$2a$10$XvZxq.qPnVw3DE764PPomOn2HdXoucCXdKSxvV3Q1SzEg2HGvahZC', '8');
