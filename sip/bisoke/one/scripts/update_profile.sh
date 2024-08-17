file_path="user_store.txt"
# /mnt/c/Users/STUDENT/Desktop/LPT/sip/bisoke/one/
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
role="${12}"

hash_password() {
    local password="$1"
    echo "$password"
    #echo -n "$password" | sha256sum | awk '{print $1}'
}

# Check if the user exists in the file
if ! grep -q "^${uuid}" "$file_path"; then
    echo "UUID ${uuid} not found in our records!"
    exit 1
fi

hashed_password=$(hash_password "$password")
# Prepare the new details
new_details="${uuid}, ${fname}, ${lname}, ${email}, ${hashed_password}, ${role}, ${ybirth}, ${HIVStatus}, ${Diagnosisyear}, ${ARTStatus}, ${ARTyear}, ${country}"

# Update the file with new details
grep -v "^${uuid}" "$file_path" > "${file_path}.tmp"
echo "${new_details}" >> "${file_path}.tmp"
mv "${file_path}.tmp" "$file_path"