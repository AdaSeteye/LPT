# Parameters
EMAIL=$1
PASSWORD=$2

# File containing user data
DATABASE_FILE="user_store.txt"

# Search for user in the database file
USER_INFO=$(grep -P "^([^,]+,){1}${EMAIL},([^,]*,){1}${PASSWORD}," $DATABASE_FILE)

# Check if user exists
if [ -n "$USER_INFO" ]; then
    # Print the user info
    echo "$USER_INFO"
else
    echo "not_found"
fi
