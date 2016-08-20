/* 新闻轻应用的表结构 */
DROP TABLE IF EXISTS `news`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `news` (
  `id` varchar(36) NOT NULL,
  `title` varchar(128) default NULL,
  `link` varchar(128) default NULL,
  `pubDate` timestamp NULL DEFAULT NULL,
  `description` varchar(512) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `orgadmin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `orgadmin` (
  `id` varchar(36) NOT NULL,
  `openId` varchar(45) default NULL,
  `department` varchar(45) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;