

--
CREATE TABLE `fun_panorama_image` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `img_uuid` varchar(50) DEFAULT '' COMMENT '图片唯一标识符',
  `thumbnail` varchar(500) DEFAULT '' COMMENT '缩略图地址',
  `title` varchar(50) DEFAULT '' COMMENT '标题',
  `summary` varchar(50) DEFAULT '' COMMENT '摘要',
  `content` varchar(2000) DEFAULT '' COMMENT '描述',
  `img_url` varchar(500) NOT NULL DEFAULT '' COMMENT '图片地址',
  `img_type` int(2) NOT NULL DEFAULT '1' COMMENT '图片类型，1:jpg',
  `width` int(5) NOT NULL DEFAULT '0' COMMENT '宽度px',
  `height` int(5) NOT NULL DEFAULT '0' COMMENT '高度px',
  `file_size` bigint(11) DEFAULT '0' COMMENT '大小',
  `del` int(2) NOT NULL DEFAULT '0' COMMENT '是否删除，0否，1是',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `creator_id` varchar(19) NOT NULL DEFAULT '' COMMENT '创建人id',
  `creator_name` varchar(25) NOT NULL DEFAULT '' COMMENT '创建人名称',
  `updater_id` varchar(19) NOT NULL DEFAULT '' COMMENT '更新人id',
  `updater_name` varchar(25) NOT NULL DEFAULT '' COMMENT '更新人名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='全景图片表';
--