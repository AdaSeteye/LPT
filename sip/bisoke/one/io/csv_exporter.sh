#!/bin/bash

# Set the input and output file paths
INPUT_FILE="/home/USER/Desktop/LPT/sip/bisoke/one/user_store.txt"
OUTPUT_FILE="/home/USER/Downloads/bisokelpt_users_data.csv"

# Ensure the output file is empty before writing
> "$OUTPUT_FILE"

# Read the input file line by line
while IFS= read -r line
do
    echo "$line" >> "$OUTPUT_FILE"
done < "$INPUT_FILE"
