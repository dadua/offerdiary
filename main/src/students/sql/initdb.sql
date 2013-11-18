CREATE SCHEMA `mystudents` ;
CREATE USER 'studentadmin'@'localhost' IDENTIFIED BY 'studentadmin';
GRANT ALL PRIVILEGES ON *.* TO 'studentadmin'@'localhost' WITH GRANT OPTION;
