@echo off
setlocal enabledelayedexpansion

:: Path to the file that acts as the user store
set "file_path=C:\Users\STUDENT\Desktop\LPT\sip\bisoke\one\user_store.txt"
::echo HERE WE ARE, THE SCRIPT IS EXECUTING

:: Function to generate a UUID (using Windows PowerShell)
for /f "delims=" %%i in ('powershell -Command "[guid]::NewGuid().ToString()"') do set "uuid=%%i"

:: Get the email from the first argument
set "email=%1"

:: Simplified regex pattern for validating email addresses
set "email_regex=[a-zA-Z0-9._%%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}"

:: Check if email matches the regex pattern
echo %email% | findstr /R /C:"%email_regex%" >nul
@REM if %errorlevel% neq 0 (
@REM     echo invalid email address
@REM     exit /b 1
@REM )

:: Check if the email already exists in the file
findstr /C:"%email%" "%file_path%" >nul
if %errorlevel% equ 0 (
    echo Error: User with email %email% is already registered.
    exit /b 1
)

:: Append user data to the file
(
    echo %uuid%, %email%, patient
) >> "%file_path%"

:: Provide feedback
echo The uuid for this patient is %uuid%
echo You have successfully initiated registration for patient with email %email%

endlocal
