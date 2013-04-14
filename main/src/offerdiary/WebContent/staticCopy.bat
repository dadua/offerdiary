@echo off
setlocal ENABLEDELAYEDEXPANSION

set WAR_NAME=ROOT
set WEBAPP_ROOT_DIR="%TOMCAT_HOME%\webapps\%WAR_NAME%"
if "X%1" == "X" (
    echo Enter static files to copy to %WEBAPP_ROOT_DIR%
    echo Filepaths entered should be relative to WebContent
    echo %0 index.html code.js something.css
    exit /b -1
) 

if not EXIST %WEBAPP_ROOT_DIR% (
    echo Cant find WEBAPP_ROOT_DIR: %WEBAPP_ROOT_DIR% 
    echo Probably TOMCAT_HOME set incorrectly..
    exit /b -1
)

echo Files to copy are:
for %%I in (%*) do echo %%I
pause
echo Running the following commands:
for %%I in (%*) do echo copy /Y %%I "%TOMCAT_HOME%\webapps\%WAR_NAME%"\%%I
pause
for %%I in (%*) do copy /Y %%I "%TOMCAT_HOME%\webapps\%WAR_NAME%"\%%I

