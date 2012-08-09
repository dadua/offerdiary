CREATE SCHEMA `couponmgr` ;
CREATE USER 'couponmgr'@'localhost' IDENTIFIED BY 'couponmgr';
GRANT ALL PRIVILEGES ON *.* TO 'couponmgr'@'localhost' WITH GRANT OPTION;
