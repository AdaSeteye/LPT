@echo off
setlocal enabledelayedexpansion

:: Input file path (replace with your actual file path)
set "INPUT_FILE=C:\Users\STUDENT\Desktop\LPT\sip\bisoke\one\user_store.txt"

:: Output CSV file path (replace with your actual desired output path)  
set "OUTPUT_FILE=%USERPROFILE%\Downloads\bisokelpt_users_data.csv"

:: Ensure the output file is empty before writing
echo "" > "%OUTPUT_FILE%"

:: Read the input file line by line
for /F "tokens=* delims=" %%A in (%INPUT_FILE%) do (
    set "line=%%A"
    :: Process the line here if needed, e.g., replace spaces with commas
    :: set "line=!line: =,!"
    echo !line! >> "%OUTPUT_FILE%"
)

:: End of script
endlocal
