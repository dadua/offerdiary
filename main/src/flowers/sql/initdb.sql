CREATE SCHEMA `myflowers` ;
CREATE USER 'fladmin'@'localhost' IDENTIFIED BY 'fladmin';
GRANT ALL PRIVILEGES ON *.* TO 'fladmin'@'localhost' WITH GRANT OPTION;
