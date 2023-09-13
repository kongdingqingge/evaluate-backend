/*
 Navicat Premium Data Transfer

 Source Server         : mysql_8.0
 Source Server Type    : MySQL
 Source Server Version : 80030 (8.0.30)
 Source Host           : localhost:3306
 Source Schema         : evaluate

 Target Server Type    : MySQL
 Target Server Version : 80030 (8.0.30)
 File Encoding         : 65001

 Date: 12/09/2023 16:25:24
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for g_matches
-- ----------------------------
DROP TABLE IF EXISTS `g_matches`;
CREATE TABLE `g_matches`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `m_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '赛程名称',
  `m_status` int NULL DEFAULT 0 COMMENT '赛程状态;0等待中,1进行中,2已结束',
  `playground_count` int NULL DEFAULT 0 COMMENT '场地数量',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `PRIMARY_KEY_52`(`id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '赛程表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of g_matches
-- ----------------------------
INSERT INTO `g_matches` VALUES (1, '2023舞龙舞狮子锦标赛', 0, 3);

-- ----------------------------
-- Table structure for g_playground
-- ----------------------------
DROP TABLE IF EXISTS `g_playground`;
CREATE TABLE `g_playground`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `playground_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '场地名称',
  `match_id` int NULL DEFAULT NULL COMMENT '赛程id',
  `unit_count` int NULL DEFAULT 0 COMMENT '单元数量',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '场地' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of g_playground
-- ----------------------------
INSERT INTO `g_playground` VALUES (1, '开放大学体育馆场地一', 1, 3);
INSERT INTO `g_playground` VALUES (2, '开放大学体育馆场地二', 1, 3);
INSERT INTO `g_playground` VALUES (3, '开放大学体育馆场地三', 1, 3);

-- ----------------------------
-- Table structure for g_projects
-- ----------------------------
DROP TABLE IF EXISTS `g_projects`;
CREATE TABLE `g_projects`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `p_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '项目名',
  `unit_id` int NOT NULL COMMENT '单元id',
  `p_status` tinyint NULL DEFAULT 0 COMMENT '项目状态;0未开始,1进行中,2已结束',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `PRIMARY_KEY_9`(`id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 52 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '项目表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of g_projects
-- ----------------------------
INSERT INTO `g_projects` VALUES (1, '甲组男子传统舞龙', 1, 0);
INSERT INTO `g_projects` VALUES (2, '甲组男子障碍舞龙', 1, 0);
INSERT INTO `g_projects` VALUES (3, '甲组男子竞速舞龙', 1, 0);
INSERT INTO `g_projects` VALUES (4, '甲组男子规定套路舞龙', 1, 0);
INSERT INTO `g_projects` VALUES (5, '甲组男子自选套路舞龙', 1, 0);
INSERT INTO `g_projects` VALUES (6, '甲组女子传统舞龙', 1, 0);
INSERT INTO `g_projects` VALUES (7, '甲组女子障碍舞龙', 1, 0);
INSERT INTO `g_projects` VALUES (8, '甲组女子竞速舞龙', 1, 0);
INSERT INTO `g_projects` VALUES (9, '甲组女子规定套路舞龙', 1, 0);
INSERT INTO `g_projects` VALUES (10, '甲组女子自选套路舞龙', 1, 0);
INSERT INTO `g_projects` VALUES (11, '甲组女子彩带龙规定套路', 2, 0);
INSERT INTO `g_projects` VALUES (12, '甲组女子彩带龙自选套路-三人赛', 2, 0);
INSERT INTO `g_projects` VALUES (13, '甲组女子彩带龙自选套路-团体赛', 2, 0);
INSERT INTO `g_projects` VALUES (14, '乙组男子传统舞龙', 2, 0);
INSERT INTO `g_projects` VALUES (15, '乙组男子障碍舞龙', 2, 0);
INSERT INTO `g_projects` VALUES (16, '乙组男子竞速舞龙', 2, 0);
INSERT INTO `g_projects` VALUES (17, '乙组男子规定套路舞龙', 2, 0);
INSERT INTO `g_projects` VALUES (18, '乙组男子自选套路舞龙', 2, 0);
INSERT INTO `g_projects` VALUES (19, '乙组女子传统舞龙', 2, 0);
INSERT INTO `g_projects` VALUES (20, '乙组女子障碍舞龙', 2, 0);
INSERT INTO `g_projects` VALUES (21, '乙组女子竞速舞龙', 3, 0);
INSERT INTO `g_projects` VALUES (22, '乙组女子规定套路舞龙', 3, 0);
INSERT INTO `g_projects` VALUES (23, '乙组女子自选套路舞龙', 3, 0);
INSERT INTO `g_projects` VALUES (24, '乙组男子传统南狮', 3, 0);
INSERT INTO `g_projects` VALUES (25, '乙组男子自选套路北狮', 3, 0);
INSERT INTO `g_projects` VALUES (26, '乙组女子传统北狮', 4, 0);
INSERT INTO `g_projects` VALUES (27, '乙组女子障碍北狮', 4, 0);
INSERT INTO `g_projects` VALUES (28, '乙组女子竞速北狮', 4, 0);
INSERT INTO `g_projects` VALUES (29, '乙组女子规定套路北狮', 4, 0);
INSERT INTO `g_projects` VALUES (30, '乙组女子自选套路北狮', 4, 0);
INSERT INTO `g_projects` VALUES (31, '乙组男子创意龙狮', 4, 0);
INSERT INTO `g_projects` VALUES (32, '乙组女子创意龙狮', 5, 0);
INSERT INTO `g_projects` VALUES (33, '乙组男子彩带龙规定套路', 5, 0);
INSERT INTO `g_projects` VALUES (34, '乙组男子彩带龙自选套路-三人赛', 5, 0);
INSERT INTO `g_projects` VALUES (35, '乙组男子彩带龙自选套路-团体赛', 5, 0);
INSERT INTO `g_projects` VALUES (36, '乙组女子彩带龙规定套路', 5, 0);
INSERT INTO `g_projects` VALUES (37, '乙组女子彩带龙自选套路-三人赛', 5, 0);
INSERT INTO `g_projects` VALUES (38, '乙组女子彩带龙自选套路-团体赛', 6, 0);
INSERT INTO `g_projects` VALUES (39, '丙组男子传统舞龙', 6, 0);
INSERT INTO `g_projects` VALUES (40, '丙组男子障碍舞龙', 6, 0);
INSERT INTO `g_projects` VALUES (41, '丙组男子竞速舞龙', 6, 0);
INSERT INTO `g_projects` VALUES (42, '丙组男子规定套路舞龙', 7, 0);
INSERT INTO `g_projects` VALUES (43, '丙组男子自选套路舞龙', 7, 0);
INSERT INTO `g_projects` VALUES (44, '丙组女子传统舞龙', 7, 0);
INSERT INTO `g_projects` VALUES (45, '丙组女子障碍舞龙', 8, 0);
INSERT INTO `g_projects` VALUES (46, '丙组女子竞速舞龙', 8, 0);
INSERT INTO `g_projects` VALUES (47, '丙组女子规定套路舞龙', 8, 0);
INSERT INTO `g_projects` VALUES (48, '丙组女子自选套路舞龙', 8, 0);
INSERT INTO `g_projects` VALUES (49, '丙组男子传统南狮', 9, 0);
INSERT INTO `g_projects` VALUES (50, '丙组男子障碍南狮', 9, 0);
INSERT INTO `g_projects` VALUES (51, '丙组男子竞速南狮', 9, 0);

-- ----------------------------
-- Table structure for g_score_umpire
-- ----------------------------
DROP TABLE IF EXISTS `g_score_umpire`;
CREATE TABLE `g_score_umpire`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `umpire_id` int NOT NULL COMMENT '裁判id',
  `team_project_id` int NOT NULL COMMENT '项目队伍表id',
  `project_id` int NOT NULL COMMENT '项目id',
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '队伍名称',
  `score` double(10, 1) NOT NULL COMMENT '分数',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `PRIMARY_KEY_5`(`id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '裁判打分表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of g_score_umpire
-- ----------------------------

-- ----------------------------
-- Table structure for g_team_project
-- ----------------------------
DROP TABLE IF EXISTS `g_team_project`;
CREATE TABLE `g_team_project`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `team_id` int NOT NULL COMMENT '队伍id',
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '队伍名称',
  `p_id` int NOT NULL COMMENT '项目id',
  `project_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '项目名称',
  `time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '队伍用时',
  `de_team_project_score` decimal(32, 0) NULL DEFAULT NULL COMMENT '主裁判扣分',
  `add_team_project_score` decimal(32, 0) NULL DEFAULT NULL COMMENT '队伍项目得分',
  `status` tinyint NULL DEFAULT 0 COMMENT '0:等待中,1:评分中,2:评分完成,3:已发布成绩,4:弃权,5:不予评分',
  `massage` tinyint NULL DEFAULT NULL COMMENT '主裁判提示消息1:过低 ,2:过高',
  `total_score` double(10, 1) NULL DEFAULT NULL COMMENT '最终得分',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `PRIMARY_KEY_7`(`id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '队伍项目表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of g_team_project
-- ----------------------------
INSERT INTO `g_team_project` VALUES (1, 1, '湖南开放大学队', 1, '甲组男子传统舞龙', NULL, NULL, NULL, 0, NULL, 0.0, '磨山救助');
INSERT INTO `g_team_project` VALUES (2, 2, '湖南师范大学队', 1, '甲组男子传统舞龙', NULL, NULL, NULL, 0, NULL, 0.0, '好啊好啊');
INSERT INTO `g_team_project` VALUES (3, 3, '北京大学队', 1, '甲组男子传统舞龙', NULL, NULL, NULL, 0, NULL, 0.0, '噶十多年');
INSERT INTO `g_team_project` VALUES (4, 4, '清华大学队', 9, '甲组女子规定套路舞龙', NULL, NULL, NULL, 0, NULL, 0.0, '');
INSERT INTO `g_team_project` VALUES (5, 5, '南华大学队', 9, '甲组女子规定套路舞龙', NULL, NULL, NULL, 0, NULL, 0.0, '');
INSERT INTO `g_team_project` VALUES (6, 6, '天津大学队', 9, '甲组女子规定套路舞龙', NULL, NULL, NULL, 0, NULL, 0.0, '');
INSERT INTO `g_team_project` VALUES (7, 1, '湖南开放大学队', 8, '甲组女子竞速舞龙', NULL, NULL, NULL, 0, NULL, 0.0, '磨山救助');
INSERT INTO `g_team_project` VALUES (8, 2, '湖南师范大学队', 8, '甲组女子竞速舞龙', NULL, NULL, NULL, 0, NULL, 0.0, '好啊好啊');
INSERT INTO `g_team_project` VALUES (9, 3, '北京大学队', 8, '甲组女子竞速舞龙', NULL, NULL, NULL, 0, NULL, 0.0, '噶十多年');

-- ----------------------------
-- Table structure for g_teams
-- ----------------------------
DROP TABLE IF EXISTS `g_teams`;
CREATE TABLE `g_teams`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `project_id` int NULL DEFAULT NULL COMMENT '项目id',
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '名称',
  `number` int NULL DEFAULT NULL COMMENT '队伍编号',
  `members` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '运动员',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `PRIMARY_KEY_3`(`id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '队伍表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of g_teams
-- ----------------------------
INSERT INTO `g_teams` VALUES (1, NULL, '湖南开放大学队', NULL, '张思/历史/困困/大宝');
INSERT INTO `g_teams` VALUES (2, NULL, '湖南师范大学队', NULL, '理合/大号/技术/思维');
INSERT INTO `g_teams` VALUES (3, NULL, '北京大学队', NULL, '创设/论坛/大哥');
INSERT INTO `g_teams` VALUES (4, NULL, '清华大学队', NULL, '张思/历史/困困/大宝');
INSERT INTO `g_teams` VALUES (5, NULL, '南华大学队', NULL, '理合/大号/技术/思维');
INSERT INTO `g_teams` VALUES (6, NULL, '天津大学队', NULL, '创设/论坛/大哥');

-- ----------------------------
-- Table structure for g_umpires
-- ----------------------------
DROP TABLE IF EXISTS `g_umpires`;
CREATE TABLE `g_umpires`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `playground_id` int NULL DEFAULT NULL COMMENT '场地id',
  `unit_id` int NULL DEFAULT NULL COMMENT '单元id',
  `project_id` int NULL DEFAULT NULL COMMENT '项目id',
  `account` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '账号',
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '姓名',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '头像',
  `type` tinyint NOT NULL DEFAULT 0 COMMENT '类型',
  `location` int NULL DEFAULT NULL COMMENT '机位号',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `PRIMARY_KEY_6`(`id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 41 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '裁判表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of g_umpires
-- ----------------------------
INSERT INTO `g_umpires` VALUES (1, 1, NULL, NULL, '1', '张三', 'http://rzhkikf9h.hn-bkt.clouddn.com/umpires/src-20230912162242.jpg', 2, NULL);
INSERT INTO `g_umpires` VALUES (2, 2, NULL, NULL, '2', '李四', NULL, 2, NULL);
INSERT INTO `g_umpires` VALUES (3, 3, NULL, NULL, '3', '王五', NULL, 2, NULL);
INSERT INTO `g_umpires` VALUES (4, 1, NULL, NULL, '4', '老刘', NULL, 1, NULL);
INSERT INTO `g_umpires` VALUES (5, 1, NULL, NULL, '5', '老六', NULL, 1, NULL);
INSERT INTO `g_umpires` VALUES (6, 1, NULL, NULL, '6', '玩的', NULL, 1, NULL);
INSERT INTO `g_umpires` VALUES (7, 1, NULL, NULL, '7', '大大', NULL, 1, NULL);
INSERT INTO `g_umpires` VALUES (8, 1, NULL, NULL, '8', '打算', NULL, 1, NULL);
INSERT INTO `g_umpires` VALUES (9, 1, NULL, NULL, '9', '放大瑟夫', NULL, 1, NULL);
INSERT INTO `g_umpires` VALUES (10, 1, NULL, NULL, '10', '幅度萨芬', NULL, 1, NULL);
INSERT INTO `g_umpires` VALUES (11, 1, NULL, NULL, '11', '发生的', NULL, 1, NULL);
INSERT INTO `g_umpires` VALUES (12, 1, NULL, NULL, '12', '放无色', NULL, 1, NULL);
INSERT INTO `g_umpires` VALUES (13, 1, NULL, NULL, '13', '型数组', NULL, 1, NULL);
INSERT INTO `g_umpires` VALUES (14, 2, NULL, NULL, '14', '撒', NULL, 1, NULL);
INSERT INTO `g_umpires` VALUES (15, 2, NULL, NULL, '15', '激活工具', NULL, 1, NULL);
INSERT INTO `g_umpires` VALUES (16, 2, NULL, NULL, '16', '的撒', NULL, 1, NULL);
INSERT INTO `g_umpires` VALUES (17, 2, NULL, NULL, '17', '大苏打', NULL, 1, NULL);
INSERT INTO `g_umpires` VALUES (18, 2, NULL, NULL, '18', '交给黑', NULL, 1, NULL);
INSERT INTO `g_umpires` VALUES (19, 2, NULL, NULL, '19', '空军航空', NULL, 1, NULL);
INSERT INTO `g_umpires` VALUES (20, 2, NULL, NULL, '20', '就应该复', NULL, 1, NULL);
INSERT INTO `g_umpires` VALUES (21, 2, NULL, NULL, '21', '机构行家', NULL, 1, NULL);
INSERT INTO `g_umpires` VALUES (22, 2, NULL, NULL, '22', '铎', NULL, 1, NULL);
INSERT INTO `g_umpires` VALUES (23, 2, NULL, NULL, '23', '特瑞特', NULL, 1, NULL);
INSERT INTO `g_umpires` VALUES (24, 2, NULL, NULL, '24', '一塌糊涂', NULL, 1, NULL);
INSERT INTO `g_umpires` VALUES (25, 2, NULL, NULL, '25', '不放过', NULL, 1, NULL);
INSERT INTO `g_umpires` VALUES (26, 2, NULL, NULL, '26', '和法规和', NULL, 1, NULL);
INSERT INTO `g_umpires` VALUES (27, 3, NULL, NULL, '27', '农民工和你', NULL, 1, NULL);
INSERT INTO `g_umpires` VALUES (28, 3, NULL, NULL, '28', '能够和你', NULL, 1, NULL);
INSERT INTO `g_umpires` VALUES (29, 3, NULL, NULL, '29', '你还敢', NULL, 1, NULL);
INSERT INTO `g_umpires` VALUES (30, 3, NULL, NULL, '30', '哪个好', NULL, 1, NULL);
INSERT INTO `g_umpires` VALUES (31, 3, NULL, NULL, '31', '那个号', NULL, 1, NULL);
INSERT INTO `g_umpires` VALUES (32, 3, NULL, NULL, '32', '你嘎哈呢', NULL, 1, NULL);
INSERT INTO `g_umpires` VALUES (33, 3, NULL, NULL, '33', '恐惧与空间', NULL, 1, NULL);
INSERT INTO `g_umpires` VALUES (34, 3, NULL, NULL, '34', '箭头有', NULL, 1, NULL);
INSERT INTO `g_umpires` VALUES (35, 3, NULL, NULL, '35', '今天已经', NULL, 1, NULL);
INSERT INTO `g_umpires` VALUES (36, 3, NULL, NULL, '36', '具有同样', NULL, 1, NULL);
INSERT INTO `g_umpires` VALUES (37, 3, NULL, NULL, '37', '交友推荐', NULL, 1, NULL);
INSERT INTO `g_umpires` VALUES (38, 3, NULL, NULL, '38', '具有同样', NULL, 1, NULL);
INSERT INTO `g_umpires` VALUES (39, 3, NULL, NULL, '39', '跨域', NULL, 1, NULL);
INSERT INTO `g_umpires` VALUES (40, 3, NULL, NULL, '40', '口语课', NULL, 1, NULL);

-- ----------------------------
-- Table structure for g_units
-- ----------------------------
DROP TABLE IF EXISTS `g_units`;
CREATE TABLE `g_units`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `u_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '单元名称',
  `playground_id` int NULL DEFAULT NULL COMMENT '场地id',
  `project_count` int NULL DEFAULT NULL COMMENT '项目数量',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '单元表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of g_units
-- ----------------------------
INSERT INTO `g_units` VALUES (1, '一单元', 1, 10);
INSERT INTO `g_units` VALUES (2, '二单元', 1, 10);
INSERT INTO `g_units` VALUES (3, '三单元', 1, 5);
INSERT INTO `g_units` VALUES (4, '一单元', 2, 6);
INSERT INTO `g_units` VALUES (5, '二单元', 2, 6);
INSERT INTO `g_units` VALUES (6, '三单元', 2, 4);
INSERT INTO `g_units` VALUES (7, '一单元', 3, 3);
INSERT INTO `g_units` VALUES (8, '二单元', 3, 4);
INSERT INTO `g_units` VALUES (9, '三单元', 3, 3);

-- ----------------------------
-- Table structure for g_user
-- ----------------------------
DROP TABLE IF EXISTS `g_user`;
CREATE TABLE `g_user`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `username` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '账号',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of g_user
-- ----------------------------
INSERT INTO `g_user` VALUES (1, 'root', '$2a$10$mOLDZnA8E8ui.FNlppQadOlE0ijppkTUWD/U7nhXhhKrCLZEmkgie');

SET FOREIGN_KEY_CHECKS = 1;
