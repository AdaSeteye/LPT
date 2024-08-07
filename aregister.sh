#!/bin/bash

file_path="user-store.txt"

generate_uuid(){
 cat /proc/sys/kernel/random/uuid
}

uuid=$1
email=$2

#read -p "Enter the patient email: " email

if echo "$email" | grep -E -q '^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$'; then

 if grep -q "$email" "$file_path"; then
  echo "Error: User with email $email is already registered."
  exit 1
 fi

 uuid=$(generate_uuid)
 echo "$uuid,$email, PATIENT" >> "$file_path"

 #echo "The uuid for this patient is $uuid "
 #echo "You have successfully initiated registration for patient with email $email"
else
 echo "invalid email Address"
fi