#!/bin/bash

# Path to the file that acts as the user store
file_path="C://Users/STUDENT/Desktop/LPT/sip/bisoke/one/user_store.txt"

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

# Check if the user exists in the file
if ! grep -q "${uuid},${email}" "$file_path"; then
    echo "Invalid UUID or email. Registration failed."
    exit 1
fi

# Hash the password
hash_password "$password"

# Prepare the new details to be appended
new_details="${hashed_password}, ${fname}, ${lname}, ${ybirth}, ${country}, ${HIVStatus}, ${Diagnosisyear}, ${ARTStatus}, ${ARTyear}"

# Update the file with new details
grep -v "${uuid},${email}" "$file_path" > "${file_path}.tmp"
grep "${uuid},${email}" "$file_path" | while IFS= read -r line; do
    echo "${line},${new_details}"
done >> "${file_path}.tmp"
mv "${file_path}.tmp" "$file_path"

echo "Registration completed successfully."
