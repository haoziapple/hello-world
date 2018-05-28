CREATE TABLE `ius_thirdparty_oauth` (
	`id` INT NOT NULL AUTO_INCREMENT COMMENT '自增主键',
	`app_id` VARCHAR(128) NULL COMMENT '应用id',
	`user_id` VARCHAR(20) NOT NULL COMMENT '用户id',
	`thirdparty_id` VARCHAR(128) NOT NULL COMMENT '第三方账户id(微信openId)',
	`bind_type` TINYINT NOT NULL DEFAULT '1' COMMENT '绑定类型(1-微信)',
	`meta_data` VARCHAR(2000) NULL COMMENT '第三方元数据',
	`remark` VARCHAR(100) NULL COMMENT '备注',
	`bind_status` TINYINT NOT NULL DEFAULT '0' COMMENT '绑定状态(0-未绑定 1-已绑定 2-已解绑)',
	`bind_time` DATETIME NULL COMMENT '绑定时间',
	`update_time` DATETIME NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
	PRIMARY KEY (`id`)
)
COMMENT='第三方授权绑定表'
COLLATE='utf8_general_ci'
;

INSERT INTO `ius_thirdparty_oauth` (`id`, `app_id`, `user_id`, `thirdparty_id`, `bind_type`, `meta_data`, `remark`, `bind_status`, `bind_time`, `update_time`) VALUES (2, 'wxe993139b11ca5cdb', '117744196566745088', 'o0Kzu0mMkWtS3Dg2E-Lfj5_PqVgI', 1, '{"subscribe":null,"openId":"o0Kzu0mMkWtS3Dg2E-Lfj5_PqVgI","nickname":"耗子小王","sexDesc":"男","sex":1,"language":"zh_CN","city":"","province":"","country":"毛里求斯","headImgUrl":"http://thirdwx.qlogo.cn/mmopen/vi_32/pyuWDUR5iaF5ESpPicSWXgS20n9rAPxWopBN1sAfdwommRYlQMicibZFYA6iaMZQtc6w455IFLIzwywEOQr83dMCEZw/132","subscribeTime":null,"unionId":null,"remark":null,"groupId":null,"tagIds":null,"privileges":[]}', NULL, 2, '2018-05-25 14:58:38', '2018-05-25 14:58:38');
INSERT INTO `ius_thirdparty_oauth` (`id`, `app_id`, `user_id`, `thirdparty_id`, `bind_type`, `meta_data`, `remark`, `bind_status`, `bind_time`, `update_time`) VALUES (4, 'wxe993139b11ca5cdb', '184696463954018304', 'o0Kzu0mMkWtS3Dg2E-Lfj5_PqVgI', 1, '{"subscribe":null,"openId":"o0Kzu0mMkWtS3Dg2E-Lfj5_PqVgI","nickname":"耗子小王","sexDesc":"男","sex":1,"language":"zh_CN","city":"","province":"","country":"毛里求斯","headImgUrl":"http://thirdwx.qlogo.cn/mmopen/vi_32/pyuWDUR5iaF5ESpPicSWXgS20n9rAPxWopBN1sAfdwommRYlQMicibZFYA6iaMZQtc6w455IFLIzwywEOQr83dMCEZw/132","subscribeTime":null,"unionId":null,"remark":null,"groupId":null,"tagIds":null,"privileges":[]}', NULL, 1, '2018-05-25 15:57:48', '2018-05-25 15:57:48');

