#!/bin/bash

file_path="user-store.txt"

hash_password() {
 local password=$1
 echo -n "$password" | openssl dgst -sha256
}

read -p "Enter your email: " email
read -p "Enter your UUID: " uuid

if grep -q "$uuid,$email" "$file_path"; then
 echo " Successful authentication. Please enter your password to complete registration."

 read -sp "Enter your password:" password
 hashed_password=$(hash_password "$password")

 # sed -i "s/^$uuid,$email,[^,]*,patient/$uuid,$email,$hashed_password,patient/" "$file_path" (to be updated to append the addeded password)

 echo -e "\nRegistration completed successfuly."

else
 echo "invalid UUID or email. Registration failed."
fi