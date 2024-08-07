

# Path to the TXT file that acts as a database
DATABASE_FILE="C://Users/STUDENT/Desktop/LPT/sip/bisoke/one/user_store.txt"

# Function to search for an email in the input file and print the line if found
search_email() {
    local email="$1"
    local password="$2"

    # Check if file exists
    if [ ! -f "$DATABASE_FILE" ]; then
        echo "Database file does not exist at $DATABASE_FILE"
        exit 1
    fi

    # Search for the email in the input file
    local result=$(grep -i "$email" "$DATABASE_FILE")

    if [ -n "$result" ]; then
        echo "Found: $result"
    else
        echo "No match found for email: $email"
    fi
}

# Ensure the script is run with required arguments
if [ "$#" -ne 2 ]; then
    echo "Usage: $0 <email> <password>"
    exit 1
fi

# Read the email and password from arguments
EMAIL="$1"
PASSWORD="$2"

# Call the function to search for the email
search_email "$EMAIL" "$PASSWORD"
