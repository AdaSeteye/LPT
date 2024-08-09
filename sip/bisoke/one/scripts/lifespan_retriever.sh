#!/bin/bash
COUNTRY_ISO_CODE="$1"
CSV_FILE="life-expectancy.csv"

# Check if CSV file exists
if [ ! -f "$CSV_FILE" ]; then
    echo "CSV file not found!"
    exit 1
fi

# Search for the country ISO code in the CSV file and print the corresponding life expectancy
lifespan=$(awk -F',' -v code="$COUNTRY_ISO_CODE" '$4 == code {print $NF}' "$CSV_FILE")

# Round the lifespan value to the nearest integer
if [ -n "$lifespan" ]; then
    rounded_lifespan=$(printf "%.0f\n" "$lifespan")
    echo "$lifespan"
else
    echo "70"
fi
