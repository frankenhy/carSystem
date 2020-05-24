DROP TABLE IF EXISTS `carsys`.`carinventory`;
CREATE TABLE `carsys`.`carinventory` (
  `id` int(11) NOT NULL,
  `quantity` int(11) DEFAULT '0',
  `checkKey` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8