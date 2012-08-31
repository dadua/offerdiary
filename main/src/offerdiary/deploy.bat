@echo off
setlocal ENABLEDELAYEDEXPANSION
call ant dist
echo Build done..
net stop Tomcat6

if "X%1" == "X" (
    set WAR_NAME=ROOT
) else (
    set WAR_NAME=%1
)

echo Deleting "%TOMCAT_HOME%\webapps\%WAR_NAME%"
rmdir /s /q "%TOMCAT_HOME%\webapps\%WAR_NAME%"
echo Deleting "%TOMCAT_HOME%\webapps\%WAR_NAME%.war"
del /f "%TOMCAT_HOME%\webapps\%WAR_NAME%.war"
echo copying to "%TOMCAT_HOME%"
copy build\release\offerdiary.war "%TOMCAT_HOME%\webapps\%WAR_NAME%.war"
net start Tomcat6
echo Reload done..
