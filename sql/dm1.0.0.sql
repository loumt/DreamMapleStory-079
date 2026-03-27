DROP TABLE IF EXISTS `dm_account`;
CREATE TABLE `dm_account`
(
    `id`           bigint(20)       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `username`     varchar(30)  NOT NULL COMMENT '账号',
    `password`     varchar(128) NOT NULL COMMENT '密码',
    `salt`         varchar(32)  DEFAULT NULL,
    `login_status` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '登录状态',
    `birthday`     date         DEFAULT NULL COMMENT '生日时间',
    `banned`       tinyint(1) NOT NULL DEFAULT '0' COMMENT '禁用',
    `ban_reason`   varchar(255) DEFAULT NULL COMMENT '禁用原因',
    `gm`           tinyint(1) NOT NULL DEFAULT '0' COMMENT 'GM标识位',
    `cash`         int(11) DEFAULT '0' COMMENT '点券数',
    `is_delete`    tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标志位 0未删除 1已删除',
    `create_time`  datetime     DEFAULT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='账号表';

DROP TABLE IF EXISTS `dm_characters`;
CREATE TABLE `dm_characters`
(
    `id`           bigint(20)       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `account_id`   bigint(20)  NOT NULL COMMENT '账号id',
    `world`        int(3)  NOT NULL COMMENT '世界',
    `name`         varchar(30) NOT NULL COMMENT '角色名字',
    `level`        int(3)  NOT NULL COMMENT '等级',
    `exp`          bigint(20)  NOT NULL COMMENT '经验',
    `strength`     int(6)  NOT NULL COMMENT '力量',
    `dexterity`    int(6)  NOT NULL COMMENT '敏捷',
    `luck`         int(6)  NOT NULL COMMENT '运气',
    `intelligence` int(6)  NOT NULL COMMENT '智力',
    `hp`           int(8)  NOT NULL COMMENT 'HP',
    `max_hp`       int(8)  NOT NULL COMMENT '最大HP',
    `mp`           int(8)  NOT NULL COMMENT 'MP',
    `max_mp`       int(8)  NOT NULL COMMENT '最大MP',
    `job`          int(3)  NOT NULL COMMENT '职业',
    `gender`       int(1)  NOT NULL COMMENT '性别',
    `fame`         int(8)  NOT NULL COMMENT '人气',
    `ap` int(5) NOT NULL DEFAULT 0 COMMENT '能力点',
    `sp` VARCHAR(32) DEFAULT NULL COMMENT '技能点',
    `map_id` int(8) DEFAULT NULL COMMENT '地图IP',
    `meso` bigint(12) DEFAULT NULL COMMENT '金币',
    `gm`           tinyint(1)  NOT NULL DEFAULT 0 COMMENT 'GM标志位',
    `hair` int(11) NOT NULL DEFAULT 0 COMMENT '发型',
    `face` int(11) NOT NULL DEFAULT 0 COMMENT '脸型',
    `skin_color` tinyint(1) NOT NULL DEFAULT 0 COMMENT '肤色',
    `spawn_point` int(3) DEFAULT NULL COMMENT '出生点',
    `is_delete`    tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标志位 0未删除 1已删除',
    `create_time`  datetime DEFAULT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='账号角色表';


DROP TABLE IF EXISTS `dm_characters_slot`;
CREATE TABLE `dm_characters_slot`
(
    `id`          bigint(20)       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `account_id`   bigint(20)  NOT NULL COMMENT '账号id',
    `world`        int(3)  NOT NULL COMMENT '世界',
    `slot`      int(6) DEFAULT NULL COMMENT '槽数量',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='账号角色槽数表';

DROP TABLE IF EXISTS `dm_guild`;
CREATE TABLE `dm_guild`
(
    `id`          bigint(20)       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name`        varchar(30) NOT NULL COMMENT '家族名字',
    `notice`      varchar(255) DEFAULT NULL COMMENT '通告',
    `alliance_id` bigint(20)  DEFAULT NULL COMMENT '联盟ID',
    `capacity`      int(6) DEFAULT NULL COMMENT '人数容量',
    `create_time` datetime     DEFAULT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='家族信息表';

DROP TABLE IF EXISTS `dm_guild_rank`;
CREATE TABLE `dm_guild_rank`
(
    `id`          bigint(20)       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `guild_id`   bigint(20)  NOT NULL COMMENT '家族ID',
    `character_id` bigint(20)  DEFAULT NULL COMMENT '角色ID',
    `rank` TINYINT(1)  DEFAULT NULL COMMENT '职级',
    `join_time` datetime     DEFAULT NULL COMMENT '加入时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='家族职级表';

DROP TABLE IF EXISTS `dm_alliance`;
CREATE TABLE `dm_alliance`
(
    `id`          bigint(20)       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name`        varchar(30) NOT NULL COMMENT '联盟名字',
    `notice`      varchar(255) DEFAULT NULL COMMENT '通告',
    `capacity`      INT(3) DEFAULT NULL COMMENT '公会容量',
    `create_time` datetime     DEFAULT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='联盟表';


DROP TABLE IF EXISTS `dm_alliance_guild`;
CREATE TABLE `dm_alliance_guild`
(
    `id`          bigint(20)       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `alliance_id`   bigint(20)  NOT NULL COMMENT '联盟ID',
    `guild_id` bigint(20)  DEFAULT NULL COMMENT '公会ID',
    `join_time` datetime     DEFAULT NULL COMMENT '加入时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='联盟公会表';

DROP TABLE IF EXISTS `dm_alliance_rank`;
CREATE TABLE `dm_alliance_rank`
(
    `id`          bigint(20)       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `alliance_id`   bigint(20)  NOT NULL COMMENT '联盟ID',
    `guild_id` bigint(20)  DEFAULT NULL COMMENT '公会ID',
    `character_id` bigint(20)  DEFAULT NULL COMMENT '角色ID',
    `rank` TINYINT(1)  DEFAULT NULL COMMENT '职级',
    `create_time` datetime     DEFAULT NULL COMMENT '设置时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='家族职级表';

DROP TABLE IF EXISTS `dm_party`;
CREATE TABLE `dm_party`
(
    `id`          bigint(20)       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `character_id` bigint(20)  DEFAULT NULL COMMENT '创建人',
    `create_time` datetime     DEFAULT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='组队表';

DROP TABLE IF EXISTS `dm_party_rank`;
CREATE TABLE `dm_party_rank`
(
    `id`          bigint(20)       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `party_id`   bigint(20)  NOT NULL COMMENT '组队ID',
    `character_id` bigint(20)  DEFAULT NULL COMMENT '联盟ID',
    `rank` TINYINT(1)  DEFAULT NULL COMMENT '职级',
    `join_time` datetime     DEFAULT NULL COMMENT '加入时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='组队职级表';

DROP TABLE IF EXISTS `dm_buddy`;
CREATE TABLE `dm_buddy`
(
    `id`           bigint(20)       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `character_id` bigint(30)  NOT NULL COMMENT '角色ID',
    `buddy_id`     bigint(20)  NOT NULL COMMENT '好友ID',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='好友关系';


DROP TABLE IF EXISTS `sys_fishing_reward`;
CREATE TABLE `sys_fishing_reward`
(
    `id`         bigint(20)       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `item_id`    bigint(20)  NOT NULL COMMENT '物品ID',
    `chance`     INT(8)  NOT NULL COMMENT '几率',
    `is_delete`  tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标志位 0未删除 1已删除',
    `expiration` INT(4)  NOT NULL COMMENT '物品上限',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='钓鱼物品';

DROP TABLE IF EXISTS `sys_shop`;
CREATE TABLE `sys_shop`
(
    `id`     bigint(20)       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `npc_id` bigint(20)  NOT NULL COMMENT 'NPC ID',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='商店';

DROP TABLE IF EXISTS `sys_shop_items`;
CREATE TABLE `sys_shop_items`
(
    `id`       bigint(20)       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `shop_id`  bigint(20)  NOT NULL COMMENT '商店ID',
    `item_id`  bigint(20)  NOT NULL COMMENT '物品ID',
    `price`    INT(8)  NOT NULL DEFAULT '0' COMMENT '价格',
    `position` INT(4)  NOT NULL DEFAULT '0' COMMENT '位置',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='商店物品';

DROP TABLE IF EXISTS `log_boss`;
CREATE TABLE `log_boss`
(
    `id`           bigint(20)       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `character_id` bigint(20)  NOT NULL COMMENT '角色ID',
    `boos_id`      bigint(20)  NOT NULL COMMENT 'BOSS ID',
    `is_delete`    tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标志位 0未删除 1已删除',
    `create_time`  datetime DEFAULT NULL COMMENT '记录时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='击杀BOSS日志';

DROP TABLE IF EXISTS `log_login`;
CREATE TABLE `log_login`
(
    `id`          bigint(20)       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `account_id`  bigint(20)  NOT NULL COMMENT '账号ID',
    `ip`          VARCHAR(32) DEFAULT NULL COMMENT 'ip',
    `is_delete`   tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标志位 0未删除 1已删除',
    `create_time` datetime    DEFAULT NULL COMMENT '记录时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='登录日志';


DROP TABLE IF EXISTS `log_packet`;
CREATE TABLE `log_packet`
(
    `id`         bigint(20)       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `account_id` bigint(20)  NOT NULL COMMENT '账号ID',
    `packet`     VARCHAR(255) DEFAULT NULL COMMENT '数据包',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='数据包日志';


DROP TABLE IF EXISTS `item_inventory`;
CREATE TABLE `item_inventory`
(
    `id` bigint(20)       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `account_id`     bigint(20)  NOT NULL COMMENT '账号ID',
    `character_id`     bigint(20)  NOT NULL COMMENT '角色ID',
    `item_id`          VARCHAR(16) DEFAULT NULL COMMENT '物品ID',
    `package_id`      bigint(20)  NOT NULL,
    `type`   tinyint(4) NOT NULL DEFAULT '0' comment '类别',
    `position`        INT(11) NOT NULL DEFAULT '0',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT= 1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='物品清单';


DROP TABLE IF EXISTS `item_inventory_slot`;
CREATE TABLE `item_inventory_slot`
(
    `id`          bigint(20)       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `character_id`   bigint(20)  NOT NULL COMMENT '角色id',
    `equip`        int(6)  NOT NULL COMMENT '装备栏-槽数量',
    `use`        int(6)  NOT NULL COMMENT '消耗栏-槽数量',
    `setup`        int(6)  NOT NULL COMMENT '设置栏-槽数量',
    `etc`        int(6)  NOT NULL COMMENT '其他栏-槽数量',
    `cash`        int(6)  NOT NULL COMMENT '特殊栏-槽数量',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='角色物品槽数表';


DROP TABLE IF EXISTS `item_inventory_equipment`;
CREATE TABLE `item_inventory_equipment`
(
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `item_id`   VARCHAR(16)  NOT NULL  COMMENT '物品ID',
    `level`       tinyint(3)  NOT NULL DEFAULT '0',
    `strength`     SMALLINT(6)  NOT NULL DEFAULT '0' COMMENT '力量',
    `dexterity`    SMALLINT(6)  NOT NULL DEFAULT '0' COMMENT '敏捷',
    `luck`         SMALLINT(6)  NOT NULL DEFAULT '0' COMMENT '运气',
    `intelligence` SMALLINT(6)  NOT NULL DEFAULT '0' COMMENT '智力',
    `hp`                   smallint(6) NOT NULL DEFAULT '0',
    `mp`                   smallint(6) NOT NULL DEFAULT '0',
    `hands`                smallint(6) NOT NULL DEFAULT '0',
    `speed`                smallint(6) NOT NULL DEFAULT '0',
    `jump`                 smallint(6) NOT NULL DEFAULT '0',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT= 1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='物品清单-已装备';


