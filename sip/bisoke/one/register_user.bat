@echo off
setlocal enabledelayedexpansion

:: Path to the file that acts as the user store
set "file_path=C:\Users\STUDENT\Desktop\LPT\sip\bisoke\one\user_store.txt"
echo HERE WE ARE, THE SCRIPT IS EXECUTING

:: Function to generate a UUID (using Windows PowerShell)
set "generate_uuid=powershell -Command "[guid]::NewGuid().ToString()""
    
:: Get the email as an argument
set "email=%1"

:: Regular expression for validating email addresses
set "email_regex=^[a-zA-Z0-9._%%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$"

:: Check if the email format is valid
echo %email% | findstr /R "%email_regex%" >nul
if errorlevel 1 (
    echo invalid email Address
    exit /b 1
)

:: Check if the email is already registered
findstr /C:"%email%" "%file_path%" >nul
if not errorlevel 1 (
    echo Error: User with email %email% is already registered.
    exit /b 1
)

:: Generate UUID and add the new patient to the store file
for /f "delims=" %%A in ('%generate_uuid%') do set "uuid=%%A"
echo %uuid%,%email%, PATIENT >> "%file_path%"
echo The uuid for this patient is %uuid%
echo You have successfully initiated registration for patient with email %email%

endlocal
