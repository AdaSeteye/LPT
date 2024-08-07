#!/bin/bash

file_path="user-store.txt"

hash_password() {
 local password=$1
 echo -n "$password" | openssl dgst -sha256
}

#read -p "Enter your email: " email
#read -p "Enter your UUID: " uuid

email=$1
uuid=$2
password=$3
fname=$4
lname=$5
ybirth=$6
country=$7
HIVStatus=$8
Diagnosisyear=$9
ARTStatus=${10}
ARTyear=${11}

#if grep -q "$uuid,$email" "$file_path"; then

 # read -sp "Enter your password:" password
 hashed_password=$(hash_password "$password")

 # sed -i "s/^$uuid,$email,[^,]*,patient/$uuid,$email,$hashed_password,patient/" "$file_path" (to be updated to append the addeded password)

 #echo -e "\nRegistration completed successfuly."

#else
 #echo "invalid UUID or email. Registration failed."
#fi

new_details="$hashed_password, $fname, $lname, $ybirth, $country, $HIVStatus, $Diagnosisyear, $ARTStatus, $ARTyear"

sed -i "/$uuid.*$email/ s/$/$new_details/" "$file_path"