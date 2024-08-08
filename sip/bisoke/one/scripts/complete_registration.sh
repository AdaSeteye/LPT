# Path to the file that acts as the user store
file_path="user_store.txt"

# Function to hash the password
hash_password() {
    local password="$1"
    hashed_password=$(echo -n "$password" | sha256sum | awk '{print $1}')
}

# Parameters passed to the script
email="$1"
uuid="$2"
password="$3"
fname="$4"
lname="$5"
ybirth="$6"
country="$7"
HIVStatus="$8"
Diagnosisyear="$9"
ARTStatus="${10}"
ARTyear="${11}"
role="patient"

# Check if the user exists in the file
if ! grep -q "^${uuid}" "$file_path"; then
    echo "Invalid UUID or email. Registration failed."
    exit 1
fi

# Hash the password
hash_password "$password"

# Prepare the new details to be appended
new_details="${uuid}, ${fname}, ${lname}, ${email}, ${hashed_password}, ${role}, ${ybirth}, ${HIVStatus}, ${Diagnosisyear}, ${ARTStatus}, ${ARTyear}, ${country}"

# Update the file with new details
if grep -q "^${uuid}, ${email}" "$file_path"; then
    # Remove the old line and append the new details
    grep -v "^${uuid}, ${email}" "$file_path" > "${file_path}.tmp"
    echo "${new_details}" >> "${file_path}.tmp"
    mv "${file_path}.tmp" "$file_path"
else
    echo "An error occurred: UUID and email not found during the update."
    exit 1
fi

# Check if the operation was successful
if [ $? -eq 0 ]; then
    echo "success"
else
    echo "An error occurred during registration."
fi
