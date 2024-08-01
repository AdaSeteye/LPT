@echo off
setlocal enabledelayedexpansion

:: Parameters
set "EMAIL=%1"
set "PASSWORD=%2"

:: File containing user data
set "DATABASE_FILE=user_store.txt"

:: Temp file to store matched user data
set "TEMP_FILE=temp_user_info.txt"

:: Search for user in the database file
findstr /C:"%EMAIL%,%PASSWORD%," "%DATABASE_FILE%" > "%TEMP_FILE%"

:: Check if user exists
if exist "%TEMP_FILE%" (
    set /p "USER_INFO="<"%TEMP_FILE%"
    echo %USER_INFO%
) else (
    echo not_found
)

:: Clean up
del "%TEMP_FILE%"
endlocal
