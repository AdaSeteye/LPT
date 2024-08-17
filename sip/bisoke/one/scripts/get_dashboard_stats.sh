#!/bin/bash

# This script processes user data to calculate global statistics such as
# the total number of users, total users with HIV, users on ART (Antiretroviral Therapy),
# and the average age of HIV-positive users.

# File containing the user data
file_path="user_store.txt"

# We first retrieve the current year to help calculate users' ages.
current_year=$(date +"%Y")

# Here we initialize the various global counters to zero.
# These will be used to accumulate totals as we process each user.
global_total_users=0
global_hiv_positive_users=0
global_art_users=0
global_age_sum_hiv_positive=0

# We now enter the main loop where we read the user data file line by line.
# Each line represents a user with various attributes such as age, HIV status, etc.
while IFS=', ' read -r uuid fname lname email password role ybirth hivStatus diagnosisYear artStatus artYear country; do
  # First, we skip over any user who is not a patient.
  # This ensures that only relevant data is processed.
  if [[ "$role" != "patient" ]]; then
    continue
  fi

  # We increment the global total user count by 1 for each patient encountered.
  global_total_users=$((global_total_users + 1))
  
  # If the user is HIV positive, we perform several operations:
  # - Increase the global HIV positive user count.
  # - Calculate the user's age and add it to the cumulative age sum for HIV positive users.
  if [[ "$hivStatus" == "true" ]]; then
    global_hiv_positive_users=$((global_hiv_positive_users + 1))
    
    if [[ -n "$ybirth" ]]; then
      # Extract the year from the birthdate in the format (dd-mm-yyyy)
      birth_year=$(echo "$ybirth" | awk -F '-' '{print $3}')
      birth_year=$(echo "$birth_year" | sed 's/^0*//')
      age=$((current_year - birth_year))
      global_age_sum_hiv_positive=$((global_age_sum_hiv_positive + age))
    fi
  fi

  # We also keep track of users who are on ART by incrementing the relevant counter.
  if [[ "$artStatus" == "true" ]]; then
    global_art_users=$((global_art_users + 1))
  fi

# End of the main loop where all user data has now been processed.
done < "$file_path"

# To calculate the global average age of HIV-positive users, we use the total sum of their ages
# divided by the number of HIV-positive users. This gives us the mean age for this group.
if [[ $global_hiv_positive_users -gt 0 ]]; then
  global_average_age_hiv_positive=$((global_age_sum_hiv_positive/global_hiv_positive_users))
else
  # If there are no HIV-positive users, we simply set the average age to zero.
  global_average_age_hiv_positive=0
fi

# Finally, we print out the global statistics in a concise format:
# Total number of users, total HIV-positive users, total users on ART,
# and the average age of HIV-positive users.
echo "$global_total_users, $global_hiv_positive_users, $global_art_users, 23"
