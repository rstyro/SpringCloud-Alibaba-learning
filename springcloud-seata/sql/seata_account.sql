/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80027
 Source Host           : localhost:3306
 Source Schema         : seata_account

 Target Server Type    : MySQL
 Target Server Version : 80027
 File Encoding         : 65001

 Date: 18/09/2023 17:15:27
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for undo_log
-- ----------------------------
DROP TABLE IF EXISTS `undo_log`;
CREATE TABLE `undo_log`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'increment id',
  `branch_id` bigint NOT NULL COMMENT 'branch transaction id',
  `xid` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'global transaction id',
  `context` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'undo_log context,such as serialization',
  `rollback_info` longblob NOT NULL COMMENT 'rollback info',
  `log_status` int NOT NULL COMMENT '0:normal status,1:defense status',
  `log_created` datetime NOT NULL COMMENT 'create datetime',
  `log_modified` datetime NOT NULL COMMENT 'modify datetime',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `ux_undo_log`(`xid`, `branch_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 101 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'AT transaction mode undo table' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of undo_log
-- ----------------------------

-- ----------------------------
-- Table structure for user_account
-- ----------------------------
DROP TABLE IF EXISTS `user_account`;
CREATE TABLE `user_account`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NULL DEFAULT NULL,
  `balance` decimal(50, 5) NULL DEFAULT NULL COMMENT '余额',
  `freeze_amount` decimal(50, 5) NULL DEFAULT NULL COMMENT '冻结余额',
  `version` bigint NULL DEFAULT 0,
  `update_time` datetime NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `userId`(`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_account
-- ----------------------------
INSERT INTO `user_account` VALUES (1, 1, 960.00000, 0.00000, 293, '2023-09-18 17:07:29', '2021-06-04 11:49:41');

-- ----------------------------
-- Table structure for user_account_detail
-- ----------------------------
DROP TABLE IF EXISTS `user_account_detail`;
CREATE TABLE `user_account_detail`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NULL DEFAULT NULL,
  `befor_balance` decimal(50, 5) NULL DEFAULT NULL COMMENT '操作前的余额',
  `after_balance` decimal(50, 5) NULL DEFAULT NULL COMMENT '操作后的余额',
  `amount` decimal(50, 5) NULL DEFAULT NULL COMMENT '操作的金额',
  `source` tinyint NULL DEFAULT NULL COMMENT '收入或支出类型：1--购物，2---转账',
  `order_number` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '订单号',
  `income` tinyint(1) NULL DEFAULT NULL COMMENT '是否是收入：1--是收入，0--支出',
  `update_time` datetime NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 72 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_account_detail
-- ----------------------------
INSERT INTO `user_account_detail` VALUES (73, 1, 1000.00000, 990.00000, 10.00000, 1, '33cb6f6e00934edbb7291cdddc9542b9', 0, '2023-09-18 16:01:24', '2023-09-18 16:01:24');
INSERT INTO `user_account_detail` VALUES (74, 1, 990.00000, 980.00000, 10.00000, 1, '5464d488f3bc4d2398afeba1c8e058e6', 0, '2023-09-18 16:03:29', '2023-09-18 16:03:29');
INSERT INTO `user_account_detail` VALUES (75, 1, 980.00000, 970.00000, 10.00000, 1, 'd5d83083e4a246f69da44ff8898d7424', 0, '2023-09-18 17:04:22', '2023-09-18 17:04:22');
INSERT INTO `user_account_detail` VALUES (76, 1, 970.00000, 960.00000, 10.00000, 1, 'bc1ef1c0123f4a999100442d9e866d2e', 0, '2023-09-18 17:07:29', '2023-09-18 17:07:29');

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `id` bigint NOT NULL,
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `password` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `status` tinyint NULL DEFAULT 1,
  `create_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES (1, 'rstyro', 'abc', 1, '2021-06-04 11:49:14');

SET FOREIGN_KEY_CHECKS = 1;
