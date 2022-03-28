/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 80016
Source Host           : localhost:3306
Source Database       : mall_tiny

Target Server Type    : MYSQL
Target Server Version : 80016
File Encoding         : 65001

Date: 2022-03-28 12:30:01
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for ums_admin
-- ----------------------------
DROP TABLE IF EXISTS `ums_admin`;
CREATE TABLE `ums_admin` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(64) DEFAULT NULL,
  `password` varchar(64) DEFAULT NULL,
  `icon` varchar(500) DEFAULT NULL COMMENT '头像',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `nick_name` varchar(200) DEFAULT NULL COMMENT '昵称',
  `note` varchar(500) DEFAULT NULL COMMENT '备注信息',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `status` int(1) DEFAULT '1' COMMENT '帐号启用状态：0->禁用；1->启用',
  `operator_time` datetime(3) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8 COMMENT='后台用户表';

-- ----------------------------
-- Records of ums_admin
-- ----------------------------
INSERT INTO `ums_admin` VALUES ('1', 'test', '$2a$10$NZ5o7r2E.ayT2ZoxgjlI.eJ6OEYqjH7INR/F.mXDbjZJi9HF0YCVG', 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180607/timg.jpg', 'test@qq.com', '测试账号', null, '2018-09-29 13:55:30', '2018-09-29 13:55:39', '1', '2022-02-23 15:10:37.852');
INSERT INTO `ums_admin` VALUES ('3', 'admin', '$2a$10$.dQtenkgqlF1HrLoBAJQs.AD.sTlSt2gleXAdlBtrw8/GcAU8mmx6', 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180607/timg.jpg', 'admin@163.com', '系统管理员', '系统管理员', '2018-10-08 13:32:47', '2022-03-24 16:09:12', '1', '2022-03-25 10:47:22.807');
INSERT INTO `ums_admin` VALUES ('4', 'macro', '$2a$10$Bx4jZPR7GhEpIQfefDQtVeS58GfT5n6mxs/b4nLLK65eMFa16topa', 'string', 'macro@qq.com', 'macro', 'macro专用', '2019-10-06 15:53:51', '2020-02-03 14:55:55', '1', '2022-02-23 15:10:37.852');
INSERT INTO `ums_admin` VALUES ('6', 'productAdmin', '$2a$10$6/.J.p.6Bhn7ic4GfoB5D.pGd7xSiD1a9M6ht6yO0fxzlKJPjRAGm', null, 'product@qq.com', '商品管理员', '只有商品权限', '2020-02-07 16:15:08', null, '1', '2022-02-23 15:10:37.852');
INSERT INTO `ums_admin` VALUES ('7', 'orderAdmin', '$2a$10$UqEhA9UZXjHHA3B.L9wNG.6aerrBjC6WHTtbv1FdvYPUI.7lkL6E.', null, 'order@qq.com', '订单管理员', '只有订单管理权限', '2020-02-07 16:15:50', null, '1', '2022-02-23 15:10:37.852');
INSERT INTO `ums_admin` VALUES ('10', 'ceshi', '$2a$10$RaaNo9CC0RSms8mc/gJpCuOWndDT4pHH0u5XgZdAAYFs1Uq4sOPRi', null, 'ceshi@qq.com', 'ceshi', null, '2020-03-13 16:23:30', null, '1', '2022-02-23 15:10:37.852');
INSERT INTO `ums_admin` VALUES ('20', 'tom', '$2a$10$nvKgVLLBtVFLSpyja0wU7e9lnQN6ROAV3a6Meo3JXpvjLuTl2Ts4C', 'string', 'string', 'string', 'string', '2022-02-11 15:58:17', '2022-02-11 15:58:17', '1', '2022-02-23 15:10:37.852');
INSERT INTO `ums_admin` VALUES ('21', 'update', '$10$Wch8OK9jKI64CHtE986L2Ot3/DelScM4CyJoQTjfTEvmhgCuu/Sfm', 'string', 'xxx@qq.com', 'string', 'string', '2022-02-15 11:05:32', null, '0', '2022-02-23 15:10:37.852');
INSERT INTO `ums_admin` VALUES ('22', 'valerius', '$2a$10$5Jpo5UuO/Qv55f9V670Mpuh5mMqzPY632yZ0.9TmUzjQDHHueYYNS', 'xxx.png', '123@qq.com', '十万少女的梦', 'valerius专用', '2022-02-22 15:55:02', '2022-03-25 13:42:05', '1', '2022-03-25 11:19:15.248');

-- ----------------------------
-- Table structure for ums_admin_login_log
-- ----------------------------
DROP TABLE IF EXISTS `ums_admin_login_log`;
CREATE TABLE `ums_admin_login_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `admin_id` bigint(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `ip` varchar(64) DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,
  `user_agent` varchar(100) DEFAULT NULL COMMENT '浏览器登录类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=382 DEFAULT CHARSET=utf8 COMMENT='后台用户登录日志表';

-- ----------------------------
-- Records of ums_admin_login_log
-- ----------------------------
INSERT INTO `ums_admin_login_log` VALUES ('285', '3', '2020-08-24 14:05:21', '0:0:0:0:0:0:0:1', null, null);
INSERT INTO `ums_admin_login_log` VALUES ('286', '10', '2020-08-24 14:05:39', '0:0:0:0:0:0:0:1', null, null);
INSERT INTO `ums_admin_login_log` VALUES ('287', '3', '2022-02-08 09:31:57', '127.0.0.1', null, null);
INSERT INTO `ums_admin_login_log` VALUES ('288', '3', '2022-02-09 16:34:15', '127.0.0.1', null, null);
INSERT INTO `ums_admin_login_log` VALUES ('289', '3', '2022-02-10 13:56:06', '127.0.0.1', null, null);
INSERT INTO `ums_admin_login_log` VALUES ('290', '3', '2022-02-10 13:56:21', '127.0.0.1', null, null);
INSERT INTO `ums_admin_login_log` VALUES ('291', '3', '2022-02-10 16:40:14', '127.0.0.1', null, null);
INSERT INTO `ums_admin_login_log` VALUES ('292', '3', '2022-02-14 14:07:29', '127.0.0.1', null, null);
INSERT INTO `ums_admin_login_log` VALUES ('293', '3', '2022-02-14 16:26:08', '127.0.0.1', null, null);
INSERT INTO `ums_admin_login_log` VALUES ('294', '3', '2022-02-14 17:24:34', '127.0.0.1', null, null);
INSERT INTO `ums_admin_login_log` VALUES ('295', '3', '2022-02-15 15:30:58', '127.0.0.1', null, null);
INSERT INTO `ums_admin_login_log` VALUES ('296', '3', '2022-02-18 11:15:33', '127.0.0.1', null, null);
INSERT INTO `ums_admin_login_log` VALUES ('297', '3', '2022-02-21 13:59:46', '127.0.0.1', null, null);
INSERT INTO `ums_admin_login_log` VALUES ('301', '111', '2022-02-22 15:18:02', '168.0.0.1', 'test addr', null);
INSERT INTO `ums_admin_login_log` VALUES ('308', '22', '2022-02-22 16:31:59', '0:0:0:0:0:0:0:1', null, null);
INSERT INTO `ums_admin_login_log` VALUES ('309', '22', '2022-02-22 16:34:41', '127.0.0.1', null, null);
INSERT INTO `ums_admin_login_log` VALUES ('310', '22', '2022-02-22 16:51:58', '127.0.0.1', null, null);
INSERT INTO `ums_admin_login_log` VALUES ('311', '22', '2022-02-22 16:52:04', '127.0.0.1', null, null);
INSERT INTO `ums_admin_login_log` VALUES ('312', '22', '2022-02-22 16:52:13', '127.0.0.1', null, null);
INSERT INTO `ums_admin_login_log` VALUES ('313', '22', '2022-02-23 10:10:59', '127.0.0.1', null, null);
INSERT INTO `ums_admin_login_log` VALUES ('314', '22', '2022-02-23 10:12:05', '127.0.0.1', null, null);
INSERT INTO `ums_admin_login_log` VALUES ('315', '22', '2022-02-23 10:35:11', '127.0.0.1', null, null);
INSERT INTO `ums_admin_login_log` VALUES ('316', '22', '2022-02-23 10:38:00', '127.0.0.1', null, null);
INSERT INTO `ums_admin_login_log` VALUES ('317', '22', '2022-02-23 10:40:56', '127.0.0.1', null, null);
INSERT INTO `ums_admin_login_log` VALUES ('318', '22', '2022-02-23 10:44:16', '127.0.0.1', null, null);
INSERT INTO `ums_admin_login_log` VALUES ('319', '22', '2022-02-23 10:47:36', '127.0.0.1', null, null);
INSERT INTO `ums_admin_login_log` VALUES ('320', '22', '2022-02-23 10:59:30', '127.0.0.1', null, null);
INSERT INTO `ums_admin_login_log` VALUES ('321', '22', '2022-02-23 11:04:23', '127.0.0.1', null, null);
INSERT INTO `ums_admin_login_log` VALUES ('322', '22', '2022-02-23 11:06:57', '127.0.0.1', null, null);
INSERT INTO `ums_admin_login_log` VALUES ('323', '22', '2022-02-23 11:07:48', '127.0.0.1', null, null);
INSERT INTO `ums_admin_login_log` VALUES ('324', '22', '2022-02-23 11:26:28', '127.0.0.1', null, null);
INSERT INTO `ums_admin_login_log` VALUES ('325', '22', '2022-02-23 11:28:03', '127.0.0.1', null, null);
INSERT INTO `ums_admin_login_log` VALUES ('326', '22', '2022-02-23 11:29:05', '127.0.0.1', null, null);
INSERT INTO `ums_admin_login_log` VALUES ('327', '22', '2022-02-23 11:32:23', '127.0.0.1', null, null);
INSERT INTO `ums_admin_login_log` VALUES ('328', '22', '2022-02-23 11:33:16', '127.0.0.1', null, null);
INSERT INTO `ums_admin_login_log` VALUES ('329', '22', '2022-02-23 11:34:40', '127.0.0.1', null, null);
INSERT INTO `ums_admin_login_log` VALUES ('330', '22', '2022-02-23 12:16:26', '127.0.0.1', null, null);
INSERT INTO `ums_admin_login_log` VALUES ('331', '22', '2022-02-23 12:18:27', '127.0.0.1', null, null);
INSERT INTO `ums_admin_login_log` VALUES ('332', '22', '2022-02-23 12:22:48', '127.0.0.1', null, null);
INSERT INTO `ums_admin_login_log` VALUES ('333', '22', '2022-02-23 12:23:19', '127.0.0.1', null, null);
INSERT INTO `ums_admin_login_log` VALUES ('334', '22', '2022-02-23 12:30:20', '127.0.0.1', null, null);
INSERT INTO `ums_admin_login_log` VALUES ('335', '22', '2022-02-23 12:34:17', '127.0.0.1', null, null);
INSERT INTO `ums_admin_login_log` VALUES ('336', '22', '2022-02-23 14:04:23', '127.0.0.1', null, null);
INSERT INTO `ums_admin_login_log` VALUES ('337', '22', '2022-02-23 14:07:25', '127.0.0.1', null, null);
INSERT INTO `ums_admin_login_log` VALUES ('338', '22', '2022-02-23 14:10:29', '127.0.0.1', null, null);
INSERT INTO `ums_admin_login_log` VALUES ('339', '22', '2022-02-23 14:12:45', '127.0.0.1', null, null);
INSERT INTO `ums_admin_login_log` VALUES ('340', '22', '2022-02-23 14:14:45', '127.0.0.1', null, null);
INSERT INTO `ums_admin_login_log` VALUES ('341', '22', '2022-02-23 14:20:39', '127.0.0.1', null, null);
INSERT INTO `ums_admin_login_log` VALUES ('342', '22', '2022-02-23 14:22:36', '127.0.0.1', null, null);
INSERT INTO `ums_admin_login_log` VALUES ('343', '22', '2022-02-23 14:27:56', '127.0.0.1', null, null);
INSERT INTO `ums_admin_login_log` VALUES ('344', '22', '2022-02-23 14:33:04', '127.0.0.1', null, null);
INSERT INTO `ums_admin_login_log` VALUES ('345', '22', '2022-02-23 14:34:38', '127.0.0.1', null, null);
INSERT INTO `ums_admin_login_log` VALUES ('346', '22', '2022-02-23 14:36:18', '127.0.0.1', null, null);
INSERT INTO `ums_admin_login_log` VALUES ('347', '22', '2022-02-23 14:37:45', '127.0.0.1', null, null);
INSERT INTO `ums_admin_login_log` VALUES ('348', '22', '2022-02-23 14:38:58', '127.0.0.1', null, null);
INSERT INTO `ums_admin_login_log` VALUES ('349', '22', '2022-02-23 14:39:30', '127.0.0.1', null, null);
INSERT INTO `ums_admin_login_log` VALUES ('350', '22', '2022-02-23 14:41:25', '127.0.0.1', null, null);
INSERT INTO `ums_admin_login_log` VALUES ('351', '22', '2022-02-23 14:41:34', '127.0.0.1', null, null);
INSERT INTO `ums_admin_login_log` VALUES ('352', '22', '2022-02-23 14:53:51', '127.0.0.1', null, null);
INSERT INTO `ums_admin_login_log` VALUES ('353', '22', '2022-02-23 14:54:00', '127.0.0.1', null, null);
INSERT INTO `ums_admin_login_log` VALUES ('354', '22', '2022-02-23 14:54:06', '127.0.0.1', null, null);
INSERT INTO `ums_admin_login_log` VALUES ('355', '22', '2022-02-23 14:54:11', '127.0.0.1', null, null);
INSERT INTO `ums_admin_login_log` VALUES ('356', '22', '2022-02-23 14:54:16', '127.0.0.1', null, null);
INSERT INTO `ums_admin_login_log` VALUES ('357', '22', '2022-02-23 14:54:17', '127.0.0.1', null, null);
INSERT INTO `ums_admin_login_log` VALUES ('358', '22', '2022-02-23 14:54:18', '127.0.0.1', null, null);
INSERT INTO `ums_admin_login_log` VALUES ('359', '22', '2022-02-23 14:54:19', '127.0.0.1', null, null);
INSERT INTO `ums_admin_login_log` VALUES ('360', '22', '2022-02-23 14:54:50', '127.0.0.1', null, null);
INSERT INTO `ums_admin_login_log` VALUES ('361', '22', '2022-02-23 14:56:09', '127.0.0.1', null, null);
INSERT INTO `ums_admin_login_log` VALUES ('362', '22', '2022-02-23 14:57:24', '127.0.0.1', null, null);
INSERT INTO `ums_admin_login_log` VALUES ('363', '22', '2022-02-23 15:09:28', '127.0.0.1', null, null);
INSERT INTO `ums_admin_login_log` VALUES ('364', '22', '2022-02-23 15:11:01', '127.0.0.1', null, null);
INSERT INTO `ums_admin_login_log` VALUES ('365', '3', '2022-02-24 10:39:41', '127.0.0.1', null, null);
INSERT INTO `ums_admin_login_log` VALUES ('366', '22', '2022-02-24 16:17:11', '127.0.0.1', null, null);
INSERT INTO `ums_admin_login_log` VALUES ('367', '22', '2022-02-24 16:17:23', '127.0.0.1', null, null);
INSERT INTO `ums_admin_login_log` VALUES ('368', '22', '2022-02-24 16:18:07', '127.0.0.1', null, null);
INSERT INTO `ums_admin_login_log` VALUES ('369', '22', '2022-03-02 15:33:52', '127.0.0.1', null, null);
INSERT INTO `ums_admin_login_log` VALUES ('370', '22', '2022-03-03 09:35:30', '127.0.0.1', null, null);
INSERT INTO `ums_admin_login_log` VALUES ('371', '22', '2022-03-03 10:58:04', '127.0.0.1', null, null);
INSERT INTO `ums_admin_login_log` VALUES ('372', '22', '2022-03-03 11:12:19', '127.0.0.1', null, null);
INSERT INTO `ums_admin_login_log` VALUES ('373', '22', '2022-03-03 11:25:29', '127.0.0.1', null, null);
INSERT INTO `ums_admin_login_log` VALUES ('374', '22', '2022-03-03 11:30:07', '127.0.0.1', null, null);
INSERT INTO `ums_admin_login_log` VALUES ('375', '22', '2022-03-03 11:34:53', '127.0.0.1', null, null);
INSERT INTO `ums_admin_login_log` VALUES ('376', '22', '2022-03-03 13:37:01', '127.0.0.1', null, null);
INSERT INTO `ums_admin_login_log` VALUES ('377', '22', '2022-03-03 13:37:15', '127.0.0.1', null, null);
INSERT INTO `ums_admin_login_log` VALUES ('378', '22', '2022-03-24 15:13:14', '127.0.0.1', null, null);
INSERT INTO `ums_admin_login_log` VALUES ('379', '3', '2022-03-24 16:09:12', '127.0.0.1', null, null);
INSERT INTO `ums_admin_login_log` VALUES ('380', '22', '2022-03-25 11:18:28', '127.0.0.1', null, null);
INSERT INTO `ums_admin_login_log` VALUES ('381', '22', '2022-03-25 13:42:05', '127.0.0.1', null, null);

-- ----------------------------
-- Table structure for ums_admin_role_relation
-- ----------------------------
DROP TABLE IF EXISTS `ums_admin_role_relation`;
CREATE TABLE `ums_admin_role_relation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `admin_id` bigint(20) DEFAULT NULL,
  `role_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=110 DEFAULT CHARSET=utf8 COMMENT='后台用户和角色关系表';

-- ----------------------------
-- Records of ums_admin_role_relation
-- ----------------------------
INSERT INTO `ums_admin_role_relation` VALUES ('26', '3', '5');
INSERT INTO `ums_admin_role_relation` VALUES ('27', '6', '1');
INSERT INTO `ums_admin_role_relation` VALUES ('28', '7', '2');
INSERT INTO `ums_admin_role_relation` VALUES ('29', '1', '5');
INSERT INTO `ums_admin_role_relation` VALUES ('30', '4', '5');
INSERT INTO `ums_admin_role_relation` VALUES ('31', '8', '5');
INSERT INTO `ums_admin_role_relation` VALUES ('34', '12', '6');
INSERT INTO `ums_admin_role_relation` VALUES ('38', '13', '5');
INSERT INTO `ums_admin_role_relation` VALUES ('39', '10', '8');
INSERT INTO `ums_admin_role_relation` VALUES ('106', '20', '1');
INSERT INTO `ums_admin_role_relation` VALUES ('107', '20', '2');
INSERT INTO `ums_admin_role_relation` VALUES ('109', '22', '15');

-- ----------------------------
-- Table structure for ums_menu
-- ----------------------------
DROP TABLE IF EXISTS `ums_menu`;
CREATE TABLE `ums_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父级ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `title` varchar(100) DEFAULT NULL COMMENT '菜单名称',
  `level` int(4) DEFAULT NULL COMMENT '菜单级数',
  `sort` int(4) DEFAULT NULL COMMENT '菜单排序',
  `name` varchar(100) DEFAULT NULL COMMENT '前端名称',
  `icon` varchar(200) DEFAULT NULL COMMENT '前端图标',
  `hidden` int(1) DEFAULT NULL COMMENT '前端隐藏',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8 COMMENT='后台菜单表';

-- ----------------------------
-- Records of ums_menu
-- ----------------------------
INSERT INTO `ums_menu` VALUES ('1', '0', '2020-02-02 14:50:36', '商品', '0', '0', 'pms', 'product', '1');
INSERT INTO `ums_menu` VALUES ('2', '1', '2020-02-02 14:51:50', '商品列表', '1', '0', 'product', 'product-list', '0');
INSERT INTO `ums_menu` VALUES ('3', '1', '2020-02-02 14:52:44', '添加商品', '1', '0', 'addProduct', 'product-add', '0');
INSERT INTO `ums_menu` VALUES ('4', '1', '2020-02-02 14:53:51', '商品分类', '1', '0', 'productCate', 'product-cate', '0');
INSERT INTO `ums_menu` VALUES ('5', '1', '2020-02-02 14:54:51', '商品类型', '1', '0', 'productAttr', 'product-attr', '0');
INSERT INTO `ums_menu` VALUES ('6', '1', '2020-02-02 14:56:29', '品牌管理', '1', '0', 'brand', 'product-brand', '0');
INSERT INTO `ums_menu` VALUES ('7', '0', '2020-02-02 16:54:07', '订单', '0', '0', 'oms', 'order', '1');
INSERT INTO `ums_menu` VALUES ('8', '7', '2020-02-02 16:55:18', '订单列表', '1', '0', 'order', 'product-list', '0');
INSERT INTO `ums_menu` VALUES ('9', '7', '2020-02-02 16:56:46', '订单设置', '1', '0', 'orderSetting', 'order-setting', '0');
INSERT INTO `ums_menu` VALUES ('10', '7', '2020-02-02 16:57:39', '退货申请处理', '1', '0', 'returnApply', 'order-return', '0');
INSERT INTO `ums_menu` VALUES ('11', '7', '2020-02-02 16:59:40', '退货原因设置', '1', '0', 'returnReason', 'order-return-reason', '0');
INSERT INTO `ums_menu` VALUES ('12', '0', '2020-02-04 16:18:00', '营销', '0', '0', 'sms', 'sms', '1');
INSERT INTO `ums_menu` VALUES ('13', '12', '2020-02-04 16:19:22', '秒杀活动列表', '1', '0', 'flash', 'sms-flash', '0');
INSERT INTO `ums_menu` VALUES ('14', '12', '2020-02-04 16:20:16', '优惠券列表', '1', '0', 'coupon', 'sms-coupon', '0');
INSERT INTO `ums_menu` VALUES ('16', '12', '2020-02-07 16:22:38', '品牌推荐', '1', '0', 'homeBrand', 'product-brand', '0');
INSERT INTO `ums_menu` VALUES ('17', '12', '2020-02-07 16:23:14', '新品推荐', '1', '0', 'homeNew', 'sms-new', '0');
INSERT INTO `ums_menu` VALUES ('18', '12', '2020-02-07 16:26:38', '人气推荐', '1', '0', 'homeHot', 'sms-hot', '0');
INSERT INTO `ums_menu` VALUES ('19', '12', '2020-02-07 16:28:16', '专题推荐', '1', '0', 'homeSubject', 'sms-subject', '0');
INSERT INTO `ums_menu` VALUES ('20', '12', '2020-02-07 16:28:42', '广告列表', '1', '0', 'homeAdvertise', 'sms-ad', '0');
INSERT INTO `ums_menu` VALUES ('21', '0', '2020-02-07 16:29:13', '权限', '0', '0', 'ums', 'ums', '0');
INSERT INTO `ums_menu` VALUES ('22', '21', '2020-02-07 16:29:51', '用户列表', '1', '0', 'admin', 'ums-admin', '0');
INSERT INTO `ums_menu` VALUES ('23', '21', '2020-02-07 16:30:13', '角色列表', '1', '0', 'role', 'ums-role', '0');
INSERT INTO `ums_menu` VALUES ('24', '21', '2020-02-07 16:30:53', '菜单列表', '1', '0', 'menu', 'ums-menu', '0');
INSERT INTO `ums_menu` VALUES ('25', '21', '2020-02-07 16:31:13', '资源列表', '1', '0', 'resource', 'ums-resource', '0');
INSERT INTO `ums_menu` VALUES ('43', '2333', '2022-02-10 17:02:52', 'string', '0', '0', 'string', 'string', '0');
INSERT INTO `ums_menu` VALUES ('44', '321', '2022-02-10 18:10:24', 'string', '0', '0', 'string', 'string', '0');

-- ----------------------------
-- Table structure for ums_resource
-- ----------------------------
DROP TABLE IF EXISTS `ums_resource`;
CREATE TABLE `ums_resource` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `name` varchar(200) DEFAULT NULL COMMENT '资源名称',
  `url` varchar(200) DEFAULT NULL COMMENT '资源URL',
  `description` varchar(500) DEFAULT NULL COMMENT '描述',
  `category_id` bigint(20) DEFAULT NULL COMMENT '资源分类ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `u_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8 COMMENT='后台资源表';

-- ----------------------------
-- Records of ums_resource
-- ----------------------------
INSERT INTO `ums_resource` VALUES ('1', '2020-02-04 17:04:55', '商品品牌管理', '/brand/**', null, '1');
INSERT INTO `ums_resource` VALUES ('2', '2020-02-04 17:05:35', '商品属性分类管理', '/productAttribute/**', null, '1');
INSERT INTO `ums_resource` VALUES ('3', '2020-02-04 17:06:13', '商品属性管理', '/productAttribute/**', null, '1');
INSERT INTO `ums_resource` VALUES ('4', '2020-02-04 17:07:15', '商品分类管理', '/productCategory/**', null, '1');
INSERT INTO `ums_resource` VALUES ('5', '2020-02-04 17:09:16', '商品管理', '/product/**', null, '1');
INSERT INTO `ums_resource` VALUES ('6', '2020-02-04 17:09:53', '商品库存管理', '/sku/**', null, '1');
INSERT INTO `ums_resource` VALUES ('8', '2020-02-05 14:43:37', '订单管理', '/order/**', '', '2');
INSERT INTO `ums_resource` VALUES ('9', '2020-02-05 14:44:22', ' 订单退货申请管理', '/returnApply/**', '', '2');
INSERT INTO `ums_resource` VALUES ('10', '2020-02-05 14:45:08', '退货原因管理', '/returnReason/**', '', '2');
INSERT INTO `ums_resource` VALUES ('11', '2020-02-05 14:45:43', '订单设置管理', '/orderSetting/**', '', '2');
INSERT INTO `ums_resource` VALUES ('12', '2020-02-05 14:46:23', '收货地址管理', '/companyAddress/**', '', '2');
INSERT INTO `ums_resource` VALUES ('13', '2020-02-07 16:37:22', '优惠券管理', '/coupon/**', '', '3');
INSERT INTO `ums_resource` VALUES ('14', '2020-02-07 16:37:59', '优惠券领取记录管理', '/couponHistory/**', '', '3');
INSERT INTO `ums_resource` VALUES ('15', '2020-02-07 16:38:28', '限时购活动管理', '/flash/**', '', '3');
INSERT INTO `ums_resource` VALUES ('16', '2020-02-07 16:38:59', '限时购商品关系管理', '/flashProductRelation/**', '', '3');
INSERT INTO `ums_resource` VALUES ('17', '2020-02-07 16:39:22', '限时购场次管理', '/flashSession/**', '', '3');
INSERT INTO `ums_resource` VALUES ('18', '2020-02-07 16:40:07', '首页轮播广告管理', '/home/advertise/**', '', '3');
INSERT INTO `ums_resource` VALUES ('19', '2020-02-07 16:40:34', '首页品牌管理', '/home/brand/**', '', '3');
INSERT INTO `ums_resource` VALUES ('20', '2020-02-07 16:41:06', '首页新品管理', '/home/newProduct/**', '', '3');
INSERT INTO `ums_resource` VALUES ('21', '2020-02-07 16:42:16', '首页人气推荐管理', '/home/recommendProduct/**', '', '3');
INSERT INTO `ums_resource` VALUES ('22', '2020-02-07 16:42:48', '首页专题推荐管理', '/home/recommendSubject/**', '', '3');
INSERT INTO `ums_resource` VALUES ('23', '2020-02-07 16:44:56', ' 商品优选管理', '/prefrenceArea/**', '', '5');
INSERT INTO `ums_resource` VALUES ('24', '2020-02-07 16:45:39', '商品专题管理', '/subject/**', '', '5');
INSERT INTO `ums_resource` VALUES ('25', '2020-02-07 16:47:34', '后台用户管理', '/admin/**', '', '4');
INSERT INTO `ums_resource` VALUES ('26', '2020-02-07 16:48:24', '后台用户角色管理', '/role/**', '', '4');
INSERT INTO `ums_resource` VALUES ('27', '2020-02-07 16:48:48', '后台菜单管理', '/menu/**', '', '4');
INSERT INTO `ums_resource` VALUES ('28', '2020-02-07 16:49:18', '后台资源分类管理', '/resourceCategory/**', '', '4');
INSERT INTO `ums_resource` VALUES ('29', '2020-02-07 16:49:45', '后台资源管理', '/resource/**', '', '4');
INSERT INTO `ums_resource` VALUES ('42', null, '后台资源纯查', '/resource/list*', null, null);

-- ----------------------------
-- Table structure for ums_resource_category
-- ----------------------------
DROP TABLE IF EXISTS `ums_resource_category`;
CREATE TABLE `ums_resource_category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `name` varchar(200) DEFAULT NULL COMMENT '分类名称',
  `sort` int(4) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='资源分类表';

-- ----------------------------
-- Records of ums_resource_category
-- ----------------------------
INSERT INTO `ums_resource_category` VALUES ('1', '2020-02-05 10:21:44', '商品模块', '0');
INSERT INTO `ums_resource_category` VALUES ('2', '2020-02-05 10:22:34', '订单模块', '0');
INSERT INTO `ums_resource_category` VALUES ('3', '2020-02-05 10:22:48', '营销模块', '0');
INSERT INTO `ums_resource_category` VALUES ('4', '2020-02-05 10:23:04', '权限模块', '0');
INSERT INTO `ums_resource_category` VALUES ('5', '2020-02-07 16:34:27', '内容模块', '0');
INSERT INTO `ums_resource_category` VALUES ('6', '2020-02-07 16:35:49', '其他模块', '0');

-- ----------------------------
-- Table structure for ums_role
-- ----------------------------
DROP TABLE IF EXISTS `ums_role`;
CREATE TABLE `ums_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL COMMENT '名称',
  `description` varchar(500) DEFAULT NULL COMMENT '描述',
  `admin_count` int(11) DEFAULT NULL COMMENT '后台用户数量',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `status` int(1) DEFAULT '1' COMMENT '启用状态：0->禁用；1->启用',
  `sort` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COMMENT='后台用户角色表';

-- ----------------------------
-- Records of ums_role
-- ----------------------------
INSERT INTO `ums_role` VALUES ('1', '商品管理员', '只能查看及操作商品', '0', '2020-02-03 16:50:37', '1', '0');
INSERT INTO `ums_role` VALUES ('2', '订单管理员', '只能查看及操作订单', '0', '2018-09-30 15:53:45', '1', '0');
INSERT INTO `ums_role` VALUES ('5', '超级管理员', '拥有所有查看和操作功能', '0', '2020-02-02 15:11:05', '1', '0');
INSERT INTO `ums_role` VALUES ('8', '权限管理员', '用于权限模块所有操作功能', '0', '2020-08-24 10:57:35', '1', '0');
INSERT INTO `ums_role` VALUES ('15', 'guest', '纯查功能', null, '2022-02-22 16:40:58', '1', '0');

-- ----------------------------
-- Table structure for ums_role_menu_relation
-- ----------------------------
DROP TABLE IF EXISTS `ums_role_menu_relation`;
CREATE TABLE `ums_role_menu_relation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `menu_id` bigint(20) DEFAULT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=113 DEFAULT CHARSET=utf8 COMMENT='后台角色菜单关系表';

-- ----------------------------
-- Records of ums_role_menu_relation
-- ----------------------------
INSERT INTO `ums_role_menu_relation` VALUES ('33', '1', '1');
INSERT INTO `ums_role_menu_relation` VALUES ('34', '1', '2');
INSERT INTO `ums_role_menu_relation` VALUES ('35', '1', '3');
INSERT INTO `ums_role_menu_relation` VALUES ('36', '1', '4');
INSERT INTO `ums_role_menu_relation` VALUES ('37', '1', '5');
INSERT INTO `ums_role_menu_relation` VALUES ('38', '1', '6');
INSERT INTO `ums_role_menu_relation` VALUES ('53', '2', '7');
INSERT INTO `ums_role_menu_relation` VALUES ('54', '2', '8');
INSERT INTO `ums_role_menu_relation` VALUES ('55', '2', '9');
INSERT INTO `ums_role_menu_relation` VALUES ('56', '2', '10');
INSERT INTO `ums_role_menu_relation` VALUES ('57', '2', '11');
INSERT INTO `ums_role_menu_relation` VALUES ('72', '5', '1');
INSERT INTO `ums_role_menu_relation` VALUES ('73', '5', '2');
INSERT INTO `ums_role_menu_relation` VALUES ('74', '5', '3');
INSERT INTO `ums_role_menu_relation` VALUES ('75', '5', '4');
INSERT INTO `ums_role_menu_relation` VALUES ('76', '5', '5');
INSERT INTO `ums_role_menu_relation` VALUES ('77', '5', '6');
INSERT INTO `ums_role_menu_relation` VALUES ('78', '5', '7');
INSERT INTO `ums_role_menu_relation` VALUES ('79', '5', '8');
INSERT INTO `ums_role_menu_relation` VALUES ('80', '5', '9');
INSERT INTO `ums_role_menu_relation` VALUES ('81', '5', '10');
INSERT INTO `ums_role_menu_relation` VALUES ('82', '5', '11');
INSERT INTO `ums_role_menu_relation` VALUES ('83', '5', '12');
INSERT INTO `ums_role_menu_relation` VALUES ('84', '5', '13');
INSERT INTO `ums_role_menu_relation` VALUES ('85', '5', '14');
INSERT INTO `ums_role_menu_relation` VALUES ('86', '5', '16');
INSERT INTO `ums_role_menu_relation` VALUES ('87', '5', '17');
INSERT INTO `ums_role_menu_relation` VALUES ('88', '5', '18');
INSERT INTO `ums_role_menu_relation` VALUES ('89', '5', '19');
INSERT INTO `ums_role_menu_relation` VALUES ('90', '5', '20');
INSERT INTO `ums_role_menu_relation` VALUES ('91', '5', '21');
INSERT INTO `ums_role_menu_relation` VALUES ('92', '5', '22');
INSERT INTO `ums_role_menu_relation` VALUES ('93', '5', '23');
INSERT INTO `ums_role_menu_relation` VALUES ('94', '5', '24');
INSERT INTO `ums_role_menu_relation` VALUES ('95', '5', '25');
INSERT INTO `ums_role_menu_relation` VALUES ('96', '6', '21');
INSERT INTO `ums_role_menu_relation` VALUES ('97', '6', '22');
INSERT INTO `ums_role_menu_relation` VALUES ('98', '6', '23');
INSERT INTO `ums_role_menu_relation` VALUES ('99', '6', '24');
INSERT INTO `ums_role_menu_relation` VALUES ('100', '6', '25');
INSERT INTO `ums_role_menu_relation` VALUES ('101', '7', '21');
INSERT INTO `ums_role_menu_relation` VALUES ('102', '7', '22');
INSERT INTO `ums_role_menu_relation` VALUES ('103', '7', '23');
INSERT INTO `ums_role_menu_relation` VALUES ('104', '7', '24');
INSERT INTO `ums_role_menu_relation` VALUES ('105', '7', '25');
INSERT INTO `ums_role_menu_relation` VALUES ('106', '8', '21');
INSERT INTO `ums_role_menu_relation` VALUES ('107', '8', '22');
INSERT INTO `ums_role_menu_relation` VALUES ('108', '8', '23');
INSERT INTO `ums_role_menu_relation` VALUES ('109', '8', '24');
INSERT INTO `ums_role_menu_relation` VALUES ('110', '8', '25');
INSERT INTO `ums_role_menu_relation` VALUES ('111', '14', '44');
INSERT INTO `ums_role_menu_relation` VALUES ('112', '14', '44');

-- ----------------------------
-- Table structure for ums_role_resource_relation
-- ----------------------------
DROP TABLE IF EXISTS `ums_role_resource_relation`;
CREATE TABLE `ums_role_resource_relation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `resource_id` bigint(20) DEFAULT NULL COMMENT '资源ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=222 DEFAULT CHARSET=utf8 COMMENT='后台角色资源关系表';

-- ----------------------------
-- Records of ums_role_resource_relation
-- ----------------------------
INSERT INTO `ums_role_resource_relation` VALUES ('103', '2', '8');
INSERT INTO `ums_role_resource_relation` VALUES ('104', '2', '9');
INSERT INTO `ums_role_resource_relation` VALUES ('105', '2', '10');
INSERT INTO `ums_role_resource_relation` VALUES ('106', '2', '11');
INSERT INTO `ums_role_resource_relation` VALUES ('107', '2', '12');
INSERT INTO `ums_role_resource_relation` VALUES ('142', '5', '1');
INSERT INTO `ums_role_resource_relation` VALUES ('143', '5', '2');
INSERT INTO `ums_role_resource_relation` VALUES ('144', '5', '3');
INSERT INTO `ums_role_resource_relation` VALUES ('145', '5', '4');
INSERT INTO `ums_role_resource_relation` VALUES ('146', '5', '5');
INSERT INTO `ums_role_resource_relation` VALUES ('147', '5', '6');
INSERT INTO `ums_role_resource_relation` VALUES ('148', '5', '8');
INSERT INTO `ums_role_resource_relation` VALUES ('149', '5', '9');
INSERT INTO `ums_role_resource_relation` VALUES ('150', '5', '10');
INSERT INTO `ums_role_resource_relation` VALUES ('151', '5', '11');
INSERT INTO `ums_role_resource_relation` VALUES ('152', '5', '12');
INSERT INTO `ums_role_resource_relation` VALUES ('153', '5', '13');
INSERT INTO `ums_role_resource_relation` VALUES ('154', '5', '14');
INSERT INTO `ums_role_resource_relation` VALUES ('155', '5', '15');
INSERT INTO `ums_role_resource_relation` VALUES ('156', '5', '16');
INSERT INTO `ums_role_resource_relation` VALUES ('157', '5', '17');
INSERT INTO `ums_role_resource_relation` VALUES ('158', '5', '18');
INSERT INTO `ums_role_resource_relation` VALUES ('159', '5', '19');
INSERT INTO `ums_role_resource_relation` VALUES ('160', '5', '20');
INSERT INTO `ums_role_resource_relation` VALUES ('161', '5', '21');
INSERT INTO `ums_role_resource_relation` VALUES ('162', '5', '22');
INSERT INTO `ums_role_resource_relation` VALUES ('163', '5', '23');
INSERT INTO `ums_role_resource_relation` VALUES ('164', '5', '24');
INSERT INTO `ums_role_resource_relation` VALUES ('165', '5', '25');
INSERT INTO `ums_role_resource_relation` VALUES ('166', '5', '26');
INSERT INTO `ums_role_resource_relation` VALUES ('167', '5', '27');
INSERT INTO `ums_role_resource_relation` VALUES ('168', '5', '28');
INSERT INTO `ums_role_resource_relation` VALUES ('169', '5', '29');
INSERT INTO `ums_role_resource_relation` VALUES ('170', '1', '1');
INSERT INTO `ums_role_resource_relation` VALUES ('171', '1', '2');
INSERT INTO `ums_role_resource_relation` VALUES ('172', '1', '3');
INSERT INTO `ums_role_resource_relation` VALUES ('173', '1', '4');
INSERT INTO `ums_role_resource_relation` VALUES ('174', '1', '5');
INSERT INTO `ums_role_resource_relation` VALUES ('175', '1', '6');
INSERT INTO `ums_role_resource_relation` VALUES ('176', '1', '23');
INSERT INTO `ums_role_resource_relation` VALUES ('177', '1', '24');
INSERT INTO `ums_role_resource_relation` VALUES ('178', '6', '25');
INSERT INTO `ums_role_resource_relation` VALUES ('179', '6', '26');
INSERT INTO `ums_role_resource_relation` VALUES ('180', '6', '27');
INSERT INTO `ums_role_resource_relation` VALUES ('181', '6', '28');
INSERT INTO `ums_role_resource_relation` VALUES ('182', '6', '29');
INSERT INTO `ums_role_resource_relation` VALUES ('205', '7', '25');
INSERT INTO `ums_role_resource_relation` VALUES ('206', '7', '26');
INSERT INTO `ums_role_resource_relation` VALUES ('207', '7', '27');
INSERT INTO `ums_role_resource_relation` VALUES ('208', '7', '28');
INSERT INTO `ums_role_resource_relation` VALUES ('209', '7', '29');
INSERT INTO `ums_role_resource_relation` VALUES ('210', '7', '31');
INSERT INTO `ums_role_resource_relation` VALUES ('211', '8', '25');
INSERT INTO `ums_role_resource_relation` VALUES ('212', '8', '26');
INSERT INTO `ums_role_resource_relation` VALUES ('213', '8', '27');
INSERT INTO `ums_role_resource_relation` VALUES ('214', '8', '28');
INSERT INTO `ums_role_resource_relation` VALUES ('215', '8', '29');
INSERT INTO `ums_role_resource_relation` VALUES ('221', '15', '42');
