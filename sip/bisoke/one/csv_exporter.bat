@echo off
setlocal enabledelayedexpansion

set "INPUT_FILE=C:\Users\STUDENT\Desktop\LPT\sip\bisoke\one\user_store.txt"

set "OUTPUT_FILE=%USERPROFILE%\Downloads\bisokelpt_users_data.csv"

:: Ensure the output file is empty before writing
echo "" > "%OUTPUT_FILE%"

:: Read the input file line by line
for /F "tokens=* delims=" %%A in (%INPUT_FILE%) do (
    set "line=%%A"
    
    echo !line! >> "%OUTPUT_FILE%"
)

:: End of script
endlocal
