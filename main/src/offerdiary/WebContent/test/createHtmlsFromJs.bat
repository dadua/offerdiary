@echo off
setlocal ENABLEDELAYEDEXPANSION
for /f "tokens=1" %%I in ('dir /b *.js') do (
    set JS_FILE_NAMES=%%I
    set FILE_NAME=!JS_FILE_NAMES:~0,-3!.html
    echo Going to write: !FILE_NAME!
    echo ^<html^> ^<head^> ^<script src="!JS_FILE_NAMES!" ^> ^</script^> ^</head^>^<body^>^</body^> ^</html^>  > !FILE_NAME!
)