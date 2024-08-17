INPUT_FILE="user_store.txt"
DOWNLOADS_DIR="$HOME/BisokeLPT"
#read -p "-- Please enter the filename >_ " filename
# Output file path
#OUTPUT_FILE="$DOWNLOADS_DIR/$filename.csv"
name="$1"
output_dir="$HOME/BisokeLPT"
mkdir -p "$output_dir" 
OUTPUT_FILE="$output_dir/$name.csv"

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
