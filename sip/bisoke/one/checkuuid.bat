@echo off
setlocal

set "file_path=C:\Users\STUDENT\Desktop\LPT\sip\bisoke\one\user_store.txt"

set "email=%1"
set "uuid=%2"

:: We Search for the user in the file and echo the line if found
for /F "tokens=* delims=" %%A in ('findstr /C:"%uuid%,%email%" "%file_path%"') do (
    echo Found: %%A
)

:: Here, we check Check if the user was found
if errorlevel 1 (
    echo not_found
)

endlocal
