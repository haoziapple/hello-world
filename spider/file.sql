CREATE TABLE `presticide_detail` (
	`id` VARCHAR(50) NULL,
	`pesticide_name` VARCHAR(50) NULL COMMENT '农药名称',
	`certificate_code` VARCHAR(50) NULL COMMENT '农药登记证号',
	`pesticide_category_code` VARCHAR(50) NULL COMMENT '农药类别码',
	`pesticide_category` VARCHAR(50) NULL COMMENT '农药类别',
	`total_content` VARCHAR(50) NULL COMMENT '总含量',
	`toxicity_code` VARCHAR(50) NULL COMMENT '毒性码',
	`toxicity` VARCHAR(50) NULL COMMENT '毒性',
	`dosage_code` VARCHAR(50) NULL COMMENT '剂型码',
	`dosage` VARCHAR(50) NULL COMMENT '剂型',
	`valid_start_day` VARCHAR(50) NULL COMMENT '有效期开始日期',
	`valid_last_day` VARCHAR(50) NULL COMMENT '有效期截止日期',
	`status` VARCHAR(50) NULL COMMENT '状态',
	`holder_name` VARCHAR(50) NULL COMMENT '持有人名称',
	`holder_id` VARCHAR(50) NULL COMMENT '持有人id'
)
COMMENT='农药产品信息'
COLLATE='utf8_general_ci'
;

INSERT INTO `presticide_detail` (`id`, `pesticide_name`, `certificate_code`, `pesticide_category_code`, `pesticide_category`, `total_content`, `toxicity_code`, `toxicity`, `dosage_code`, `dosage`, `valid_start_day`, `valid_last_day`, `status`, `holder_name`, `holder_id`) VALUES ('13b43a3502214fccaf3339a4224a38c6','杀虫气雾剂','WP20080258','卫生杀虫剂','卫生杀虫剂','0.15%','微毒','微毒','气雾剂','气雾剂','1385395200000','1700928000000','有效','广汉二仙蚊香厂','a238c22530c9404c95c4b3bfce102155');
