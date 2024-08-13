#!/bin/bash

file_path="user_store.txt"

# Loop through each line in the file
while IFS=', ' read -r uuid fname lname email password role ybirth hivStatus diagnosisYear artStatus artYear country; do
  # Check if the role is "patient"
  if [[ "$role" == "patient" ]]; then
    # Display the line if the role is "patient"
    echo "$uuid,$fname,$lname,$email,$password,$role,$ybirth,$hivStatus,$diagnosisYear,$artStatus,$artYear,$country"
  fi
done < "$file_path"
