set -e
set -u

# Path to the file that acts as the user store
db="user_store.txt"

# Generate a UUID
uuid=$(uuidgen)

# Get the email from the first argument
email="$1"

# Simplified regex pattern for validating email addresses
email_regex="^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$"

# Check if email matches the regex pattern
if [[ ! "$email" =~ $email_regex ]]; then
    echo "Invalid email address"
    exit 1
fi

# Check if the email already exists in the file
if grep -q "$email" "$db"; then
    echo "Error: User with email $email is already registered."
    exit 1
fi

# Append user data to the file
echo "$uuid, $email, patient" >> "$db"

# Provide feedback
echo "The UUID for this patient is $uuid"
echo "You have successfully initiated registration for a patient with email $email"