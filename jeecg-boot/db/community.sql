DROP TABLE IF EXISTS `monitor_car`;

CREATE TABLE `monitor_car` (
  `id` varchar(36) NOT NULL,
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建日期',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新日期',
  `sys_org_code` varchar(64) DEFAULT NULL COMMENT '所属部门',
  `car_number` varchar(100) DEFAULT NULL COMMENT '车牌号',
  `out_in_type` varchar(1) NOT NULL COMMENT '进入类型',
  `out_in_time` datetime NOT NULL COMMENT '进出时间',
  `car_type` varchar(32) NOT NULL COMMENT '人员类型',
  `photo_url` varchar(255) DEFAULT NULL COMMENT '图片地址',
  `address` varchar(255) DEFAULT NULL COMMENT '进出地址',
  `device_code` varchar(32) DEFAULT NULL COMMENT '设备编号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `monitor_person`;

CREATE TABLE `monitor_person` (
  `id` varchar(36) NOT NULL,
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建日期',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新日期',
  `sys_org_code` varchar(64) DEFAULT NULL COMMENT '所属部门',
  `person_name` varchar(100) DEFAULT NULL COMMENT '人员姓名',
  `person_id_card` varchar(32) DEFAULT NULL COMMENT '身份证',
  `out_in_type` varchar(1) NOT NULL COMMENT '进入类型',
  `out_in_time` datetime NOT NULL COMMENT '进出时间',
  `person_type` varchar(32) NOT NULL COMMENT '人员类型',
  `photo_url` varchar(255) DEFAULT NULL COMMENT '图片地址',
  `address` varchar(255) DEFAULT NULL COMMENT '进出地址',
  `device_code` varchar(32) DEFAULT NULL COMMENT '设备编号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;