CREATE TABLE `tb_user` (
  `id` bigint(20) PRIMARY KEY NOT NULL AUTO_INCREMENT COMMENT '主键' ,
  `open_id` varchar(50) COMMENT 'openId',
  `other_party_id` varchar(50) COMMENT '第三方id',
  `user_id` varchar(50) COMMENT '用户id',
  `user_name` varchar(50) COMMENT '用户昵称',
  `interest_title` varchar(50) COMMENT '感兴趣的标题',
  `interest_province_name` varchar(50) COMMENT '感兴趣的省份名称',
  `create_by` varchar(50) COMMENT '创建人',
  `update_by` varchar(50) COMMENT '修改人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `tb_insurance_message` (
  `id` bigint(20) PRIMARY KEY NOT NULL AUTO_INCREMENT COMMENT '主键',
  `title` varchar(100) COMMENT '标题',
  `detail_url` varchar(50) COMMENT '详情连接',
  `short_message` varchar(1000) COMMENT '简单描述',
  `province_name` varchar(50) COMMENT '省份名称',
  `purchaser` varchar(100) COMMENT '采购人',
  `agency` varchar(100) COMMENT '代理机构',
  `public_time` datetime COMMENT '发布时间',
  `create_by` varchar(50) COMMENT '创建人',
  `update_by` varchar(50) COMMENT '修改人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
