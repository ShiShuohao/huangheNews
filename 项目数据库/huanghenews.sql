/*
SQLyog Ultimate v13.1.1 (64 bit)
MySQL - 5.6.50-log : Database - huanghenews
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`huanghenews` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `huanghenews`;

/*Table structure for table `collect` */

DROP TABLE IF EXISTS `collect`;

CREATE TABLE `collect` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '收藏编号',
  `user` varchar(100) NOT NULL COMMENT '收藏人',
  `news_id` int(11) NOT NULL COMMENT '收藏新闻编号',
  `date` date NOT NULL COMMENT '收藏时间',
  PRIMARY KEY (`id`),
  KEY `user` (`user`),
  KEY `news_Id` (`news_id`),
  CONSTRAINT `collect_ibfk_1` FOREIGN KEY (`user`) REFERENCES `user` (`email`),
  CONSTRAINT `collect_ibfk_2` FOREIGN KEY (`news_id`) REFERENCES `news` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4;

/*Data for the table `collect` */

/*Table structure for table `comment` */

DROP TABLE IF EXISTS `comment`;

CREATE TABLE `comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '评论编号',
  `news_id` int(11) NOT NULL COMMENT '评论新闻编号',
  `author` varchar(100) NOT NULL COMMENT '评论作者',
  `content` mediumtext NOT NULL COMMENT '评论内容',
  `date` date NOT NULL COMMENT '评论时间',
  PRIMARY KEY (`id`),
  KEY `news_id` (`news_id`),
  KEY `author` (`author`),
  CONSTRAINT `comment_ibfk_1` FOREIGN KEY (`news_id`) REFERENCES `news` (`id`),
  CONSTRAINT `comment_ibfk_2` FOREIGN KEY (`author`) REFERENCES `user` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4;

/*Data for the table `comment` */

/*Table structure for table `focus` */

DROP TABLE IF EXISTS `focus`;

CREATE TABLE `focus` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '关注编号',
  `user_from` varchar(100) NOT NULL COMMENT '关注者',
  `user_to` varchar(100) NOT NULL COMMENT '被关注者',
  PRIMARY KEY (`id`),
  KEY `user_from` (`user_from`),
  KEY `user_to` (`user_to`),
  CONSTRAINT `focus_ibfk_1` FOREIGN KEY (`user_from`) REFERENCES `user` (`email`),
  CONSTRAINT `focus_ibfk_2` FOREIGN KEY (`user_to`) REFERENCES `user` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;

/*Data for the table `focus` */

/*Table structure for table `log` */

DROP TABLE IF EXISTS `log`;

CREATE TABLE `log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '日志编号',
  `version` varchar(100) NOT NULL COMMENT '日志版本号',
  `date` date NOT NULL COMMENT '发布日期',
  `content` mediumtext COMMENT '日志内容',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;

/*Data for the table `log` */

/*Table structure for table `message` */

DROP TABLE IF EXISTS `message`;

CREATE TABLE `message` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '私信编号',
  `user_from` varchar(100) NOT NULL COMMENT '发信人',
  `user_to` varchar(100) NOT NULL COMMENT '收信人',
  `headline` varchar(255) NOT NULL COMMENT '私信标题',
  `content` mediumtext NOT NULL COMMENT '私信内容',
  `date` date NOT NULL COMMENT '私信日期',
  `state` int(11) NOT NULL COMMENT '私信状态（0代表未读，1代表已读）',
  PRIMARY KEY (`id`),
  KEY `user_from` (`user_from`),
  KEY `user_to` (`user_to`),
  CONSTRAINT `message_ibfk_1` FOREIGN KEY (`user_from`) REFERENCES `user` (`email`),
  CONSTRAINT `message_ibfk_2` FOREIGN KEY (`user_to`) REFERENCES `user` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4;

/*Data for the table `message` */

/*Table structure for table `news` */

DROP TABLE IF EXISTS `news`;

CREATE TABLE `news` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '新闻编号',
  `headline` varchar(255) NOT NULL COMMENT '新闻标题',
  `author` varchar(100) NOT NULL COMMENT '新闻作者',
  `content` mediumtext NOT NULL COMMENT '新闻内容',
  `date` date NOT NULL COMMENT '发布时间',
  `type` varchar(20) NOT NULL COMMENT '板块',
  `click` int(11) NOT NULL COMMENT '点击量',
  `comment` int(11) NOT NULL COMMENT '评论量',
  `first` int(11) NOT NULL COMMENT '是否置顶（0代表否，1代表是）',
  PRIMARY KEY (`id`),
  KEY `author` (`author`),
  KEY `type` (`type`),
  CONSTRAINT `news_ibfk_1` FOREIGN KEY (`author`) REFERENCES `user` (`email`),
  CONSTRAINT `news_ibfk_2` FOREIGN KEY (`type`) REFERENCES `type` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8mb4;

/*Data for the table `news` */

/*Table structure for table `search` */

DROP TABLE IF EXISTS `search`;

CREATE TABLE `search` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '搜索记录编号',
  `user` varchar(100) NOT NULL COMMENT '用户邮箱',
  `content` varchar(255) NOT NULL COMMENT '用户搜索内容',
  PRIMARY KEY (`id`),
  KEY `search_ibfk_1` (`user`),
  CONSTRAINT `search_ibfk_1` FOREIGN KEY (`user`) REFERENCES `user` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4;

/*Data for the table `search` */

/*Table structure for table `type` */

DROP TABLE IF EXISTS `type`;

CREATE TABLE `type` (
  `name` varchar(20) NOT NULL COMMENT '板块名',
  `news_number` int(11) NOT NULL COMMENT '板块新闻数量',
  `state` int(11) NOT NULL COMMENT '板块状态（0代表固定板块，1代表可变板块）',
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `type` */

insert  into `type`(`name`,`news_number`,`state`) values 
('军事',0,1),
('医疗',0,1),
('国内新闻',0,0),
('国际新闻',0,0),
('教育',0,1),
('河南新闻',0,0),
('科技',0,1),
('财经',0,1),
('郑州新闻',0,0);

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `email` varchar(100) NOT NULL COMMENT '用户邮箱',
  `petname` varchar(255) NOT NULL COMMENT '用户昵称',
  `sex` varchar(10) NOT NULL COMMENT '用户性别',
  `birthday` date NOT NULL COMMENT '用户生日',
  `area` varchar(100) NOT NULL COMMENT '用户地区',
  `role` int(11) NOT NULL COMMENT '用户角色（0代表用户，1代表普通管理员，2代表超级管理员，3代表待审核的管理员）',
  PRIMARY KEY (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `user` */

insert  into `user`(`email`,`petname`,`sex`,`birthday`,`area`,`role`) values 
('e867cda2b@126.com','石硕灏','男','2000-11-29','河南省',2);

/*Table structure for table `collect_news` */

DROP TABLE IF EXISTS `collect_news`;

/*!50001 DROP VIEW IF EXISTS `collect_news` */;
/*!50001 DROP TABLE IF EXISTS `collect_news` */;

/*!50001 CREATE TABLE  `collect_news`(
 `id` int(11) ,
 `user` varchar(100) ,
 `news_id` int(11) ,
 `date` date ,
 `headline` varchar(255) 
)*/;

/*Table structure for table `comment_news` */

DROP TABLE IF EXISTS `comment_news`;

/*!50001 DROP VIEW IF EXISTS `comment_news` */;
/*!50001 DROP TABLE IF EXISTS `comment_news` */;

/*!50001 CREATE TABLE  `comment_news`(
 `id` int(11) ,
 `news_id` int(11) ,
 `author` varchar(100) ,
 `content` mediumtext ,
 `date` date ,
 `headline` varchar(255) 
)*/;

/*Table structure for table `comment_user` */

DROP TABLE IF EXISTS `comment_user`;

/*!50001 DROP VIEW IF EXISTS `comment_user` */;
/*!50001 DROP TABLE IF EXISTS `comment_user` */;

/*!50001 CREATE TABLE  `comment_user`(
 `id` int(11) ,
 `news_id` int(11) ,
 `author` varchar(100) ,
 `content` mediumtext ,
 `date` date ,
 `petname` varchar(255) ,
 `sex` varchar(10) 
)*/;

/*Table structure for table `focus_user` */

DROP TABLE IF EXISTS `focus_user`;

/*!50001 DROP VIEW IF EXISTS `focus_user` */;
/*!50001 DROP TABLE IF EXISTS `focus_user` */;

/*!50001 CREATE TABLE  `focus_user`(
 `id` int(11) ,
 `user_from` varchar(100) ,
 `user_to` varchar(100) ,
 `petname` varchar(255) 
)*/;

/*Table structure for table `message_user` */

DROP TABLE IF EXISTS `message_user`;

/*!50001 DROP VIEW IF EXISTS `message_user` */;
/*!50001 DROP TABLE IF EXISTS `message_user` */;

/*!50001 CREATE TABLE  `message_user`(
 `id` int(11) ,
 `user_from` varchar(100) ,
 `user_to` varchar(100) ,
 `headline` varchar(255) ,
 `content` mediumtext ,
 `date` date ,
 `state` int(11) ,
 `petname` varchar(255) 
)*/;

/*Table structure for table `news_user` */

DROP TABLE IF EXISTS `news_user`;

/*!50001 DROP VIEW IF EXISTS `news_user` */;
/*!50001 DROP TABLE IF EXISTS `news_user` */;

/*!50001 CREATE TABLE  `news_user`(
 `id` int(11) ,
 `headline` varchar(255) ,
 `author` varchar(100) ,
 `content` mediumtext ,
 `date` date ,
 `type` varchar(20) ,
 `click` int(11) ,
 `comment` int(11) ,
 `first` int(11) ,
 `petname` varchar(255) ,
 `sex` varchar(10) 
)*/;

/*View structure for view collect_news */

/*!50001 DROP TABLE IF EXISTS `collect_news` */;
/*!50001 DROP VIEW IF EXISTS `collect_news` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`shidongdong`@`%` SQL SECURITY DEFINER VIEW `collect_news` AS select `collect`.`id` AS `id`,`collect`.`user` AS `user`,`collect`.`news_id` AS `news_id`,`collect`.`date` AS `date`,`news`.`headline` AS `headline` from (`collect` join `news` on((`collect`.`news_id` = `news`.`id`))) where (`collect`.`news_id` = `news`.`id`) */;

/*View structure for view comment_news */

/*!50001 DROP TABLE IF EXISTS `comment_news` */;
/*!50001 DROP VIEW IF EXISTS `comment_news` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`shidongdong`@`%` SQL SECURITY DEFINER VIEW `comment_news` AS select `comment`.`id` AS `id`,`comment`.`news_id` AS `news_id`,`comment`.`author` AS `author`,`comment`.`content` AS `content`,`comment`.`date` AS `date`,`news`.`headline` AS `headline` from (`comment` join `news` on((`comment`.`news_id` = `news`.`id`))) where (`comment`.`news_id` = `news`.`id`) */;

/*View structure for view comment_user */

/*!50001 DROP TABLE IF EXISTS `comment_user` */;
/*!50001 DROP VIEW IF EXISTS `comment_user` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`shidongdong`@`%` SQL SECURITY DEFINER VIEW `comment_user` AS select `comment`.`id` AS `id`,`comment`.`news_id` AS `news_id`,`comment`.`author` AS `author`,`comment`.`content` AS `content`,`comment`.`date` AS `date`,`user`.`petname` AS `petname`,`user`.`sex` AS `sex` from (`comment` join `user` on((`comment`.`author` = `user`.`email`))) where (`comment`.`author` = `user`.`email`) */;

/*View structure for view focus_user */

/*!50001 DROP TABLE IF EXISTS `focus_user` */;
/*!50001 DROP VIEW IF EXISTS `focus_user` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`shidongdong`@`%` SQL SECURITY DEFINER VIEW `focus_user` AS (select `focus`.`id` AS `id`,`focus`.`user_from` AS `user_from`,`focus`.`user_to` AS `user_to`,`user`.`petname` AS `petname` from (`focus` join `user`) where (`focus`.`user_to` = `user`.`email`)) */;

/*View structure for view message_user */

/*!50001 DROP TABLE IF EXISTS `message_user` */;
/*!50001 DROP VIEW IF EXISTS `message_user` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`shidongdong`@`%` SQL SECURITY DEFINER VIEW `message_user` AS (select `message`.`id` AS `id`,`message`.`user_from` AS `user_from`,`message`.`user_to` AS `user_to`,`message`.`headline` AS `headline`,`message`.`content` AS `content`,`message`.`date` AS `date`,`message`.`state` AS `state`,`user`.`petname` AS `petname` from (`message` join `user`) where (`message`.`user_from` = `user`.`email`)) */;

/*View structure for view news_user */

/*!50001 DROP TABLE IF EXISTS `news_user` */;
/*!50001 DROP VIEW IF EXISTS `news_user` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`shidongdong`@`%` SQL SECURITY DEFINER VIEW `news_user` AS select `news`.`id` AS `id`,`news`.`headline` AS `headline`,`news`.`author` AS `author`,`news`.`content` AS `content`,`news`.`date` AS `date`,`news`.`type` AS `type`,`news`.`click` AS `click`,`news`.`comment` AS `comment`,`news`.`first` AS `first`,`user`.`petname` AS `petname`,`user`.`sex` AS `sex` from (`news` join `user` on((`news`.`author` = `user`.`email`))) where (`news`.`author` = `user`.`email`) */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
