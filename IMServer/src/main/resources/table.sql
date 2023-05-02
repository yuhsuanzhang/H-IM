CREATE TABLE `im_message`
(
    `id`                  bigint(20) unsigned NOT NULL COMMENT '消息ID',
    `content`             varchar(1024)       NOT NULL COMMENT '消息正文',
    `sender_id`           bigint(20) unsigned NOT NULL COMMENT '发送者ID',
    `receiver_id`         bigint(20) unsigned NOT NULL COMMENT '接收者ID',
    `previous_message_id` bigint(20) unsigned NOT NULL COMMENT '上一条消息ID',
    `receiver_type`       tinyint(4)          NOT NULL COMMENT '接收者类型',
    `message_type`        tinyint(4)          NOT NULL COMMENT '消息类型',
    `send_time`           datetime            NOT NULL COMMENT '发送时间',
    `device_type`         tinyint(4)          NOT NULL DEFAULT '0' COMMENT '设备类型，0：未知，1：iOS，2：Android，3：PC',
    `create_time`         datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`         datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `version`             int(11)             NOT NULL DEFAULT '0' COMMENT '版本号',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='IM消息表';

CREATE TABLE `group`
(
    `id`           bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '群组ID',
    `group_name`   varchar(255)        NOT NULL COMMENT '群组名称',
    `group_avatar` varchar(255)        NOT NULL COMMENT '群组头像',
    `creator`      varchar(255)        NOT NULL COMMENT '创建人',
    `owner`        varchar(255)        NOT NULL COMMENT '群主',
    `create_time`  datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`  datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `version`      bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '版本号',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='群组表';

CREATE TABLE `group_message`
(
    `id`          bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '关系ID',
    `message_id`  bigint(20) unsigned NOT NULL COMMENT '消息ID',
    `group_id`    bigint(20) unsigned NOT NULL COMMENT '群组ID',
    `create_time` datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `version`     bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '版本号',
    PRIMARY KEY (`id`),
    KEY `idx_message_id` (`message_id`),
    KEY `idx_group_id` (`group_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='群组消息关系表';

CREATE TABLE `user`
(
    `id`          bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    `account`     varchar(255)        NOT NULL COMMENT '用户名/账号',
    `password`    varchar(255)        NOT NULL COMMENT '密码',
    `phone`       varchar(255)        NOT NULL COMMENT '手机',
    `email`       varchar(255)        NOT NULL COMMENT '邮箱',
    `nickname`    varchar(255)        NOT NULL COMMENT '昵名',
    `avatar`      varchar(255)        NOT NULL COMMENT '头像',
    `create_time` datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `version`     bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '版本号',
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_account` (`account`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户表';

CREATE TABLE `user_group`
(
    `id`          bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '关系ID',
    `user_id`     bigint(20) unsigned NOT NULL COMMENT '用户ID',
    `group_id`    bigint(20) unsigned NOT NULL COMMENT '群组ID',
    `create_time` datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `version`     bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '版本号',
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_group_id` (`group_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户群组关系表';

CREATE TABLE `user_message`
(
    `id`          bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '关系ID',
    `message_id`  bigint(20) unsigned NOT NULL COMMENT '消息ID',
    `user_id`     bigint(20) unsigned NOT NULL COMMENT '用户ID',
    `create_time` datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `version`     bigint(20)          NOT NULL DEFAULT '0' COMMENT '版本号',
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_message_user` (`message_id`, `user_id`),
    KEY `idx_user_message` (`user_id`, `message_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户-消息关系表';