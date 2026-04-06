CREATE TABLE `config`
(
    `id`           bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
    `namespace`    varchar(128) NOT NULL COMMENT '配置命名空间',
    `key`          varchar(128) NOT NULL COMMENT '配置key',
    `content`      longtext     NOT NULL COMMENT '配置内容',
    `deleted`      bigint unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除',
    `deleted_time` datetime              DEFAULT NULL COMMENT '删除时间',
    `created_time` datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time` datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录修改时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uni_user_id` (`key`,`namespace`,`deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='配置信息表'

