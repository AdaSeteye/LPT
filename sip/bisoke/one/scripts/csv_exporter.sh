#!/bin/bash

# Input file path
INPUT_FILE="/mnt/c/Users/STUDENT/Desktop/LPT/sip/bisoke/one/user_store.txt"

# Determine the OS and set the output directory
if grep -qEi "(Microsoft|WSL)" /proc/version &>/dev/null; then
    # WSL or Windows Subsystem for Linux
    OUTPUT_DIR=$(wslpath "$(powershell.exe -Command '[Environment]::GetFolderPath("Downloads")' | tr -d '\r')")
elif [[ "$OSTYPE" == "darwin"* ]]; then
    # MacOS
    OUTPUT_DIR="$HOME/Downloads"
elif [[ "$OSTYPE" == "linux-gnu"* ]]; then
    # Pure Linux (Unix)
    OUTPUT_DIR=$(xdg-user-dir DOWNLOAD 2>/dev/null || echo "$HOME/Downloads")
elif [[ "$OSTYPE" == "cygwin" || "$OSTYPE" == "msys" || "$OSTYPE" == "win32" ]]; then
    # Windows (Cygwin, Git Bash, etc.)
    OUTPUT_DIR=$(powershell.exe -Command '[Environment]::GetFolderPath("Downloads")' | tr -d '\r')
else
    echo "Unsupported OS. Exiting."
    exit 1
fi

# Output file path
OUTPUT_FILE="$OUTPUT_DIR/bisokelpt_users_data.csv"

# Ensure the output file is empty before writing and add column headers
echo "UUID,First Name,Last Name,Email,Password,Role,Year of Birth,HIV Status,Diagnosis Year,ART Status,ART Year,Country" > "$OUTPUT_FILE"

# Read the input file line by line
while IFS= read -r line; do
    echo "$line" >> "$OUTPUT_FILE"
done < "$INPUT_FILE"

echo "CSV file saved successfully to $OUTPUT_FILE."
