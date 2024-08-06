#!/bin/bash

file_path="../user_store.txt"
email=$1
uuid=$2

if grep -q "$uuid,$email" "$file_path"; then
  exit 0
else
  exit 1
fi