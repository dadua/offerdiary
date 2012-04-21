CREATE  TABLE IF NOT EXISTS `USERS` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` VARCHAR(45) NULL ,
  `login_type` VARCHAR(20) NULL ,
  `password` VARCHAR(45) NULL ,
  `name` VARCHAR(45) NULL ,
  `age` INT NULL ,
  `gender` VARCHAR(10) NULL ,
  `location` VARCHAR(45) NULL ,
  `language` VARCHAR(45) NULL ,
  `email_id` VARCHAR(45) NULL ,
  PRIMARY KEY (`id`) );

CREATE  TABLE IF NOT EXISTS `STORES` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL ,
  `location` VARCHAR(45) NULL ,
  `description` VARCHAR(45) NULL ,
  `web_url` VARCHAR(45) NULL ,
  `image_url` VARCHAR(45) NULL ,
  `tags` VARCHAR(500) NULL ,
  PRIMARY KEY (`id`) );

CREATE  TABLE IF NOT EXISTS `COUPONS` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `store_id` INT NULL ,
  `code` VARCHAR(45) NULL ,
  `discount` DECIMAL NULL ,
  `detail` VARCHAR(500) NULL ,
  `creation_date` DATETIME NULL ,
  `expiry_date` DATETIME NULL ,
  `owner_id` INT NULL ,
  `permission` VARCHAR(45) NULL ,
  `tags` VARCHAR(500) NULL ,
  `AUTOGUID` VARCHAR(100) NULL ,
  PRIMARY KEY (`id`));
  
CREATE  TABLE IF NOT EXISTS `ALERT_CONFIG` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `data_type` VARCHAR(50) NOT NULL ,
  `data_id` INT NOT NULL ,
  `alert_type` VARCHAR(50) NULL,
  `trigger_time` DATETIME NOT NULL ,
  `creation_time` DATETIME NOT NULL ,
  `status` ENUM('active','suspended','handled') NOT NULL ,
  PRIMARY KEY (`id`) );

CREATE  TABLE IF NOT EXISTS `ALERT` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `data_type` VARCHAR(50) NOT NULL ,
  `data_id` INT NOT NULL ,
  `alert_status` ENUM('new','read') NOT NULL ,
  `creation_time` DATETIME NOT NULL ,
  `message` TEXT NULL,
  `html_message` TEXT NULL,
  PRIMARY KEY (`id`) );
  
  
  
	

	
	
