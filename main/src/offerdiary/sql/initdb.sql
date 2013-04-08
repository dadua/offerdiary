CREATE SCHEMA `offerdiary` ;
CREATE USER 'od'@'localhost' IDENTIFIED BY 'od@123!@#';
GRANT ALL PRIVILEGES ON *.* TO 'od'@'localhost' WITH GRANT OPTION;
