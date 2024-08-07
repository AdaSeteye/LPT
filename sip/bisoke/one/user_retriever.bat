@echo off
setlocal enabledelayedexpansion

:: -- These parameters are passed from the Java Code, using ProcessBuilder --
set "EMAIL=%1"
set "PASSWORD=%2"

:: -- Path to our TXT file that acts as a Database --
set "DATABASE_FILE=C:\Users\STUDENT\Desktop\LPT\sip\bisoke\one\user_store.txt"

:: Search for the email in the input file and echo the line if found
for /F "tokens=* delims=" %%A in ('findstr /I /C:"%EMAIL%" "%DATABASE_FILE%"') do (
    echo Found: %%A
)

:: If no result is found, notify the user
if errorlevel 1 (
    echo No match found for email: %EMAIL%
)

endlocal

























@REM :: Temp file to store matched user data
@REM set "TEMP_FILE=temp_user_info.txt"

@REM :: Search for user in the database file
@REM :: Use double quotes around DATABASE_FILE in case of spaces in the path
@REM findstr /C:"%EMAIL%,%PASSWORD%," "%DATABASE_FILE%" > "%TEMP_FILE%" 2> error.log

@REM :: Check for errors
@REM if %ERRORLEVEL% NEQ 0 (
@REM     echo Error: An issue occurred while searching for the user.
@REM     echo Error details:
@REM     type error.log
@REM     goto cleanup
@REM )

@REM :: Echo the content of TEMP_FILE before condition check
@REM echo Content of TEMP_FILE:
@REM type "%TEMP_FILE%"

@REM :: Check if user exists
@REM if exist "%TEMP_FILE%" (
@REM     set /p "USER_INFO="<"%TEMP_FILE%"
@REM     echo %USER_INFO%
@REM ) else (
@REM     echo not_found
@REM )

@REM :cleanup
@REM :: Clean up
@REM del "%TEMP_FILE%"
@REM del error.log
@REM endlocal
