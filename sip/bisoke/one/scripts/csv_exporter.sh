INPUT_FILE="user_store.txt"
DOWNLOADS_DIR="$HOME/BisokeLPT"

filename="$1"
#read -p "-- Please enter the filename >_ " filename
# Output file path
#OUTPUT_FILE="$DOWNLOADS_DIR/$filename.csv"
OUTPUT_FILE="$filename.csv"
echo "UUID,First Name,Last Name,Email,Password,Role,Year of Birth,HIV Status,Diagnosis Year,ART Status,ART Year,Country" > "$OUTPUT_FILE"

# Read the input file line by line
while IFS= read -r line; do
    echo "$line" >> "$OUTPUT_FILE"
done < "$INPUT_FILE"

# Check if the file was created successfully
if [ -f "$OUTPUT_FILE" ]; then
    echo "Users data exported at $OUTPUT_FILE"
else
    echo "Failed to create CSV file"
fi
