@echo off

::
:: ***** DO NOT EDIT THIS FILE.  READ THIS FIRST. *****
::
:: Any changes you make to this file may get commited and will be visible
:: to other developers.
::
:: So if you want to customize your build environment, create a copy first
:: (say mysetenv.cmd) and then edit the copy only.
::


::
:: Define DEFAULT locations for various tools
::

set PROJ_JDK_HOME=c:\Program Files\Java\jdk1.6.0_26
set PROJ_ANT_HOME=d:\code\mcafee\svn\main\prod\ePO\dev-tools\apache-ant-1.7.1
set TOMCAT_HOME=C:\Program Files\Apache Software Foundation\Tomcat 6.0

::
:: Cleanup Environment
::



::
:: Detect and setup environment
::

set PROJ_HOME=%~dp0

::
:: Initalize PATH and other stuff
::

set PATH=%PROJ_JDK_HOME%\bin;%PATH%
set PATH=%EXTN_HOME%;%PROJ_ANT_HOME%\bin;%PATH%

set OD_SERVER_MODE=DEV
:: DEV/LIVE
set OD_DEFAULT_SERVER_URL=http://localhost:8080/offerdiary

set JAVA_HOME=%PROJ_JDK_HOME%

echo Environment Initialized:
echo EXTN_HOME=%PROJ_HOME%
echo EXTN_JDK_HOME=%PROJ_JDK_HOME%
echo PROJ_ANT_HOME=%PROJ_ANT_HOME%
