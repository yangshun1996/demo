/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50726
 Source Host           : localhost:3306
 Source Schema         : activiti

 Target Server Type    : MySQL
 Target Server Version : 50726
 File Encoding         : 65001

 Date: 07/07/2021 14:29:37
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission`  (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `URL` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES (1, '申请', 'apply');
INSERT INTO `permission` VALUES (2, '审批', 'agree');
INSERT INTO `permission` VALUES (3, '查看流程', 'query');

-- ----------------------------
-- Table structure for permission_role_rel
-- ----------------------------
DROP TABLE IF EXISTS `permission_role_rel`;
CREATE TABLE `permission_role_rel`  (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `PERMISSIONID` int(11) NOT NULL,
  `ROLEID` int(11) NOT NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = FIXED;

-- ----------------------------
-- Records of permission_role_rel
-- ----------------------------
INSERT INTO `permission_role_rel` VALUES (1, 1, 1);
INSERT INTO `permission_role_rel` VALUES (2, 3, 1);
INSERT INTO `permission_role_rel` VALUES (3, 2, 2);

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `ID` int(11) NOT NULL,
  `ROLENAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = MyISAM CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, 'A');
INSERT INTO `role` VALUES (2, 'B');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `PWD` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'user1', '1234');
INSERT INTO `user` VALUES (2, 'user2', '1234');
INSERT INTO `user` VALUES (3, 'admin1', '1234');
INSERT INTO `user` VALUES (4, 'admin2', '1234');

-- ----------------------------
-- Table structure for user_role_rel
-- ----------------------------
DROP TABLE IF EXISTS `user_role_rel`;
CREATE TABLE `user_role_rel`  (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `USERID` int(11) NOT NULL,
  `ROLEID` int(11) NOT NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = FIXED;

-- ----------------------------
-- Records of user_role_rel
-- ----------------------------
INSERT INTO `user_role_rel` VALUES (1, 1, 1);
INSERT INTO `user_role_rel` VALUES (2, 2, 1);
INSERT INTO `user_role_rel` VALUES (3, 3, 2);
INSERT INTO `user_role_rel` VALUES (4, 4, 2);

-- ----------------------------
-- Table structure for user_task_rel
-- ----------------------------
DROP TABLE IF EXISTS `user_task_rel`;
CREATE TABLE `user_task_rel`  (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `TASKID` int(11) NULL DEFAULT NULL,
  `USERID` int(11) NULL DEFAULT NULL,
  `STATUS` int(11) NULL DEFAULT 1,
  `CREATETIME` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 126 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = FIXED;

-- ----------------------------
-- Records of user_task_rel
-- ----------------------------
INSERT INTO `user_task_rel` VALUES (104, 45, 2, 2, '2021-07-07 11:33:23');
INSERT INTO `user_task_rel` VALUES (103, 37, 2, 2, '2021-07-07 11:33:21');
INSERT INTO `user_task_rel` VALUES (102, 29, 2, 2, '2021-07-07 11:33:20');
INSERT INTO `user_task_rel` VALUES (101, 21, 1, 2, '2021-07-07 11:33:17');
INSERT INTO `user_task_rel` VALUES (100, 13, 1, 2, '2021-07-07 11:33:16');
INSERT INTO `user_task_rel` VALUES (99, 5, 1, 2, '2021-07-07 11:33:14');
INSERT INTO `user_task_rel` VALUES (105, 53, 2, 2, '2021-07-07 11:33:44');
INSERT INTO `user_task_rel` VALUES (106, 73, 2, 2, '2021-07-07 11:34:13');
INSERT INTO `user_task_rel` VALUES (107, 87, 2, 2, '2021-07-07 11:34:21');
INSERT INTO `user_task_rel` VALUES (108, 103, 2, 2, '2021-07-07 11:34:32');
INSERT INTO `user_task_rel` VALUES (109, 111, 1, 2, '2021-07-07 11:34:50');
INSERT INTO `user_task_rel` VALUES (110, 121, 1, 2, '2021-07-07 11:34:57');
INSERT INTO `user_task_rel` VALUES (111, 133, 2, 2, '2021-07-07 11:35:57');
INSERT INTO `user_task_rel` VALUES (112, 143, 1, 2, '2021-07-07 11:40:57');
INSERT INTO `user_task_rel` VALUES (113, 151, 1, 2, '2021-07-07 11:40:58');
INSERT INTO `user_task_rel` VALUES (114, 159, 2, 2, '2021-07-07 11:41:00');
INSERT INTO `user_task_rel` VALUES (115, 167, 2, 2, '2021-07-07 11:41:03');
INSERT INTO `user_task_rel` VALUES (116, 185, 2, 2, '2021-07-07 11:41:58');
INSERT INTO `user_task_rel` VALUES (117, 7503, 2, 2, '2021-07-07 11:57:24');
INSERT INTO `user_task_rel` VALUES (118, 7511, 2, 2, '2021-07-07 11:57:27');
INSERT INTO `user_task_rel` VALUES (119, 7519, 2, 2, '2021-07-07 11:57:31');
INSERT INTO `user_task_rel` VALUES (120, 7527, 2, 2, '2021-07-07 11:57:32');
INSERT INTO `user_task_rel` VALUES (121, 7535, 1, 2, '2021-07-07 11:57:34');
INSERT INTO `user_task_rel` VALUES (122, 7543, 1, 2, '2021-07-07 11:57:35');
INSERT INTO `user_task_rel` VALUES (123, 7551, 1, 2, '2021-07-07 11:57:35');
INSERT INTO `user_task_rel` VALUES (124, 7559, 1, 2, '2021-07-07 11:57:36');
INSERT INTO `user_task_rel` VALUES (125, 7579, 1, 2, '2021-07-07 11:58:21');

SET FOREIGN_KEY_CHECKS = 1;
