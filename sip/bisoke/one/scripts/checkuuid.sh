# Path to the file that acts as the user store
file_path="user_store.txt"

# Parameters passed to the script
email="$1"
uuid="$2"

# Search for the user in the file and echo the line if found
found_user=$(grep -m 1 "${uuid}, ${email}" "$file_path")

if [ -n "$found_user" ]; then
    echo "Found: $found_user"
else
    echo "not_found"
fi
