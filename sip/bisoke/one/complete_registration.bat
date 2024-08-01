@echo off
setlocal enabledelayedexpansion

:: Path to the file that acts as the user store
set "file_path=C:\Users\STUDENT\Desktop\LPT\sip\bisoke\one\user_store.txt"

:: Function to generate a hashed password using PowerShell
:hash_password
setlocal
set "password=%~1"
for /f "delims=" %%i in ('powershell -Command "[System.Text.Encoding]::UTF8.GetBytes('%password%') | [System.Security.Cryptography.SHA256]::Create().ComputeHash([System.Text.Encoding]::UTF8.GetBytes('%password%')) | ForEach-Object { $_.ToString('x2') } -join ''"') do set "hashed_password=%%i"
endlocal & set "hashed_password=%hashed_password%"
goto :eof

:: Parameters passed to the script
set "email=%1"
set "uuid=%2"
set "password=%3"
set "fname=%4"
set "lname=%5"
set "ybirth=%6"
set "country=%7"
set "HIVStatus=%8"
set "Diagnosisyear=%9"
set "ARTStatus=%10"
set "ARTyear=%11"

:: Check if the user exists in the file
findstr /C:"%uuid%,%email%" "%file_path%" >nul
if %errorlevel% neq 0 (
    echo invalid UUID or email. Registration failed.
    exit /b 1
)

:: Hash the password
call :hash_password "%password%"

:: Prepare the new details to be appended
set "new_details=%hashed_password%, %fname%, %lname%, %ybirth%, %country%, %HIVStatus%, %Diagnosisyear%, %ARTStatus%, %ARTyear%"

:: Update the file with new details
(for /f "tokens=*" %%a in ('findstr /v /c:"%uuid%,%email%" "%file_path%"') do echo %%a) > "%file_path%.tmp"
(for /f "tokens=*" %%a in ('findstr /c:"%uuid%,%email%" "%file_path%"') do echo %%a,%new_details%) >> "%file_path%.tmp"
move /y "%file_path%.tmp" "%file_path%"

echo Registration completed successfully.
