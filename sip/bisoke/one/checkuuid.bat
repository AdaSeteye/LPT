@echo off
setlocal

:: Path to the file that acts as the user store
set "file_path=C:\Users\STUDENT\Desktop\LPT\sip\bisoke\one\user_store.txt"

:: Parameters passed to the script
set "email=%1"
set "uuid=%2"

:: Search for the user in the file and echo the line if found
for /F "tokens=* delims=" %%A in ('findstr /C:"%uuid%,%email%" "%file_path%"') do (
    echo Found: %%A
)

:: Check if the user was found
if errorlevel 1 (
    echo not_found
)

endlocal
