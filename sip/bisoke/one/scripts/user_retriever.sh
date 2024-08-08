# Path to the TXT file that acts as a database
DATABASE_FILE="user_store.txt"

# Function to hash the provided password
hash_password() {
    local password="$1"
    echo -n "$password" | sha256sum | awk '{print $1}'
}

# Function to search for an email in the input file and verify the password
function search_email_and_verify_password() {
    local email="$1"
    local password="$2"
    local hashed_password

    # Check if the database file exists
    if [ ! -f "$DATABASE_FILE" ]; then
        echo "Database file does not exist at $DATABASE_FILE"
    fi

    # Search for the email in the input file (case insensitive)
    local result=$(grep -i "^.*, $email,.*$" "$DATABASE_FILE")

    if [ -n "$result" ]; then
        # Extract the hashed password from the matching line
        hashed_password=$(echo "$result" | awk -F', ' '{print $5}')

        # Hash the provided password
        local input_hashed_password=$(hash_password "$password")

        # Compare the input hashed password with the stored hashed password
        if [ "$input_hashed_password" = "$hashed_password" ]; then
            echo "$result"
        else
            echo "Invalid password for email: $email"
        fi
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

# Call the function to search for the email and verify the password
search_email_and_verify_password "$EMAIL" "$PASSWORD"