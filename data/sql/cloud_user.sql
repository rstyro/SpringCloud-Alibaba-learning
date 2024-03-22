/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80027
 Source Host           : localhost:3306
 Source Schema         : cloud_user

 Target Server Type    : MySQL
 Target Server Version : 80027
 File Encoding         : 65001

 Date: 22/03/2024 17:38:18
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
) ENGINE = InnoDB AUTO_INCREMENT = 118 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'AT transaction mode undo table' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of undo_log
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '用户名称',
  `nick_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '昵称',
  `password` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '密码',
  `update_by` bigint NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'rstyro', 'rstyro', 'rstyro', 1, '2024-03-14 17:33:42', 1, '2024-03-14 17:33:45');
INSERT INTO `user` VALUES (2, 'admin', 'admin', 'rstyro', 1, '2024-03-14 17:33:42', 1, '2024-03-14 17:33:45');
INSERT INTO `user` VALUES (3, 'root', 'admin', 'rstyro', 1, '2024-03-14 17:33:42', 1, '2024-03-14 17:33:45');
INSERT INTO `user` VALUES (4, 'user', 'admin', 'rstyro', 1, '2024-03-14 17:33:42', 1, '2024-03-14 17:33:45');

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
INSERT INTO `user_account` VALUES (1, 1, 1000.00000, 0.00000, 1, '2024-03-04 17:14:30', '2024-03-19 17:06:58');
INSERT INTO `user_account` VALUES (2, 2, 1000.00000, 0.00000, 1, '2024-03-04 17:14:30', '2024-03-19 17:06:58');
INSERT INTO `user_account` VALUES (3, 3, 1000.00000, 0.00000, 1, '2024-03-04 17:14:30', '2024-03-19 17:06:58');
INSERT INTO `user_account` VALUES (4, 4, 1000.00000, 0.00000, 1, '2024-03-04 17:14:30', '2024-03-19 17:06:58');

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
  `source` int NULL DEFAULT NULL COMMENT '收入或支出类型：1--购物，2---转账',
  `order_number` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '订单号',
  `income` tinyint(1) NULL DEFAULT NULL COMMENT '是否是收入：1--是收入，0--支出',
  `update_time` datetime NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_account_detail
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
