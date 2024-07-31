#!/bin/bash

file_path="user-store.txt"

generate_uuid(){
 cat /proc/sys/kernel/random/uuid
}

if [[ "$(id -u)" -ne 0 ]]
then
  echo " You need admin priviledges"
  exit 1
fi

read -p "Enter the patient email: " email

if grep -q "$email" "$file_path"; then
  echo "Error: User with email $email is already registered."
  exit 1
fi

uuid=$(generate_uuid)
hashed_password=0
echo "$uuid,$email, $hashed_password, patient" >> "$file_path"

echo "your uuid is $uuid "
echo "end"