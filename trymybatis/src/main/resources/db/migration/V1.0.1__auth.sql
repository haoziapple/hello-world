CREATE TABLE `ius_thirdparty_secret` (
	`id` INT NOT NULL AUTO_INCREMENT COMMENT '自增主键',
	`app_id` VARCHAR(200) NOT NULL COMMENT '应用id',
	`app_secret` VARCHAR(200) NOT NULL COMMENT '应用秘钥',
	`token` VARCHAR(200) NULL COMMENT 'token',
	`aes_key` VARCHAR(200) NULL COMMENT 'AES加密key',
	`workspace_id` VARCHAR(100) NULL COMMENT '工作空间id',
	`setting_status` TINYINT NULL DEFAULT '0' COMMENT '设定状态(0-启用 1-停用)',
	`update_time` DATETIME NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
	PRIMARY KEY (`id`)
)
COMMENT='第三方授权秘钥表'
COLLATE='utf8_general_ci'
;

INSERT INTO `ius_thirdparty_secret` (`id`, `app_id`, `app_secret`, `token`, `aes_key`, `workspace_id`, `setting_status`, `update_time`) VALUES (1, 'wxe993139b11ca5cdb', '5f64c243c0da0461614939fabe9537fa', 'haozixixi', 'OL0mou2l1U2qCtnhHn2xmt1toVHAvf8nK4avbEIYU4k', NULL, 0, '2018-05-24 16:35:17');
