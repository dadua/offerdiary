@echo off

SETLOCAL

if "X%1" == "X" ( set PORT_TO_BE_CHECKED=8080) else (set PORT_TO_BE_CHECKED=%1)

for /f "tokens=1,4,5" %%i in ('netstat -ano ^| findstr 8080') do if "X%%j" == "XLISTENING" ( set PID_WHICH_HAS_PORT_8080=%%k )

if "X%PID_WHICH_HAS_PORT_8080%" == "X" (
    echo No Process listening on port 8080
    exit /b -1
    )
echo %PID_WHICH_HAS_PORT_8080%
taskkill /f /PID %PID_WHICH_HAS_PORT_8080%
