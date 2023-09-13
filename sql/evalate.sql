DROP TABLE IF EXISTS g_user;
CREATE TABLE g_user(
    `id` INT NOT NULL AUTO_INCREMENT  COMMENT 'id' ,
    `username` VARCHAR(30) NOT NULL   COMMENT '账号' ,
    `password` VARCHAR(255) NOT NULL   COMMENT '密码' ,
    PRIMARY KEY (id)
)  COMMENT = '用户表';

DROP TABLE IF EXISTS g_umpires;
CREATE TABLE g_umpires(
    `id` INT(10) NOT NULL AUTO_INCREMENT  COMMENT 'id' ,
    `playground_id` INT(10)    COMMENT '场地id' ,
    `unit_id` INT(10)    COMMENT '单元id' ,
    `project_id` INT(10)    COMMENT '项目id' ,
    `account` VARCHAR(30) NOT NULL   COMMENT '账号' ,
    `name` VARCHAR(30) NOT NULL   COMMENT '姓名' ,
    `avatar` VARCHAR(255) NOT NULL   COMMENT '头像' ,
    `type` TINYINT NOT NULL  DEFAULT 0 COMMENT '类型' ,
    `location` INT(10)    COMMENT '机位号' ,
    PRIMARY KEY (id)
)  COMMENT = '裁判表';


CREATE UNIQUE INDEX PRIMARY_KEY_6 ON g_umpires(id);

DROP TABLE IF EXISTS g_teams;
CREATE TABLE g_teams(
    `id` INT(10) NOT NULL AUTO_INCREMENT  COMMENT 'id' ,
    `project_id` INT(10)    COMMENT '项目id' ,
    `name` VARCHAR(32)    COMMENT '名称' ,
    `number` INT(32)    COMMENT '队伍编号' ,
    `members` VARCHAR(255)    COMMENT '运动员' ,
    PRIMARY KEY (id)
)  COMMENT = '队伍表';


CREATE UNIQUE INDEX PRIMARY_KEY_3 ON g_teams(id);

DROP TABLE IF EXISTS g_matches;
CREATE TABLE g_matches(
    `id` INT(10) NOT NULL AUTO_INCREMENT  COMMENT 'id' ,
    `m_name` VARCHAR(255) NOT NULL   COMMENT '赛程名称' ,
    `m_status` INT   DEFAULT 0 COMMENT '赛程状态;0等待中,1进行中,2已结束' ,
    `playground_count` INT(10)   DEFAULT 0 COMMENT '场地数量' ,
    PRIMARY KEY (id)
)  COMMENT = '赛程表';


CREATE UNIQUE INDEX PRIMARY_KEY_52 ON g_matches(id);

DROP TABLE IF EXISTS g_units;
CREATE TABLE g_units(
    `id` INT(10) NOT NULL AUTO_INCREMENT  COMMENT 'id' ,
    `u_name` VARCHAR(32) NOT NULL   COMMENT '单元名称' ,
    `playground_id` INT(10)    COMMENT '场地id' ,
    `project_count` INT(10)    COMMENT '项目数量' ,
    PRIMARY KEY (id)
)  COMMENT = '单元表';

DROP TABLE IF EXISTS g_projects;
CREATE TABLE g_projects(
    `id` INT(10) NOT NULL AUTO_INCREMENT  COMMENT 'id' ,
    `p_name` VARCHAR(30) NOT NULL   COMMENT '项目名' ,
    `unit_id` INT(10) NOT NULL   COMMENT '单元id' ,
    `p_status` TINYINT   DEFAULT 0 COMMENT '项目状态;0未开始,1进行中,2已结束' ,
    PRIMARY KEY (id)
)  COMMENT = '项目表';


CREATE UNIQUE INDEX PRIMARY_KEY_9 ON g_projects(id);

DROP TABLE IF EXISTS g_team_project;
CREATE TABLE g_team_project(
    `id` INT(10) NOT NULL AUTO_INCREMENT  COMMENT 'id' ,
    `team_id` INT(10) NOT NULL   COMMENT '队伍id' ,
    `name` VARCHAR(30) NOT NULL   COMMENT '队伍名称' ,
    `p_id` INT(10) NOT NULL   COMMENT '项目id' ,
    `project_name` VARCHAR(30) NOT NULL   COMMENT '项目名称' ,
    `time` VARCHAR(255)    COMMENT '队伍用时' ,
    `de_team_project_score` DECIMAL(32)    COMMENT '主裁判扣分' ,
    `add_team_project_score` DECIMAL(32)    COMMENT '队伍项目得分' ,
    `status` TINYINT   DEFAULT 0 COMMENT '0:等待中,1:评分中,2:评分完成,3:已发布成绩,4:弃权,5:不予评分' ,
    `massage` TINYINT    COMMENT '主裁判提示消息1:过低 ,2:过高' ,
    `total_score` double(10,1)    COMMENT '最终得分' ,
    `content` VARCHAR(255)    COMMENT '备注' ,
    PRIMARY KEY (id)
)  COMMENT = '队伍项目表';


CREATE UNIQUE INDEX PRIMARY_KEY_7 ON g_team_project(id);

DROP TABLE IF EXISTS g_score_umpire;
CREATE TABLE g_score_umpire(
    `id` INT(10) NOT NULL AUTO_INCREMENT  COMMENT 'id' ,
    `umpire_id` INT(10) NOT NULL   COMMENT '裁判id' ,
    `team_project_id` INT(10) NOT NULL   COMMENT '项目队伍表id' ,
    `project_id` INT(10) NOT NULL   COMMENT '项目id' ,
    `name` VARCHAR(30) NOT NULL   COMMENT '队伍名称' ,
    `score` double(10,1) NOT NULL   COMMENT '分数' ,
    PRIMARY KEY (id)
)  COMMENT = '裁判打分表';


CREATE UNIQUE INDEX PRIMARY_KEY_5 ON g_score_umpire(id);

DROP TABLE IF EXISTS g_playground;
CREATE TABLE g_playground(
    `id` INT NOT NULL AUTO_INCREMENT  COMMENT 'id' ,
    `playground_name` VARCHAR(32)    COMMENT '场地名称' ,
    `match_id` INT(10)    COMMENT '赛程id' ,
    `unit_count` INT(10)   DEFAULT 0 COMMENT '单元数量' ,
    PRIMARY KEY (id)
)  COMMENT = '场地';

