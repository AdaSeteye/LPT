#!/bin/bash

# Path to the file that acts as the user store
file_path="C://Users/STUDENT/Desktop/LPT/sip/bisoke/one/user_store.txt"

# Parameters passed to the script
email="$1"
uuid="$2"
fname="$3"
lname="$4"
ybirth="$5"
country="$6"
HIVStatus="$7"
Diagnosisyear="$8"
ARTStatus="$9"
ARTyear="${10}"

# Check if the user exists in the file
if ! grep -q "${uuid},${email}" "$file_path"; then
    echo "Invalid UUID or email. Update failed."
    exit 1
fi

# Prepare the new details
new_details="${uuid},${email},${fname},${lname},${ybirth},${country},${HIVStatus},${Diagnosisyear},${ARTStatus},${ARTyear}"

# Update the file with new details
grep -v "${uuid},${email}" "$file_path" > "${file_path}.tmp"
echo "${new_details}" >> "${file_path}.tmp"
mv "${file_path}.tmp" "$file_path"

echo "Profile updated successfully."
