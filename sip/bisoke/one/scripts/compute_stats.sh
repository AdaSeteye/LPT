#!/bin/bash

file_path="user_store.txt"

declare -A country_user_count
declare -A country_hiv_positive_count
declare -A country_age_sum
declare -A country_life_expectancy_values

current_year=$(date +"%Y")

# Initialize global counters
global_total_users=0
global_hiv_positive_users=0
global_art_users=0
global_age_sum_hiv_positive=0
hiv_positive_user_count=0

# Function to calculate median
calculate_median() {
  local arr=($(echo "$1" | tr ' ' '\n' | sort -n))
  local count=${#arr[@]}
  if (( count % 2 == 1 )); then
    median=${arr[$((count/2))]}
  else
    median=$(( (${arr[$((count/2 - 1))]} + ${arr[$((count/2))]} ) / 2 ))
  fi
  echo $median
}

calculate_sd() {
  local arr=($(echo "$1" | tr ' ' '\n'))
  local sum=0
  local sumsq=0
  local count=${#arr[@]}

  for i in "${arr[@]}"; do
    sum=$((sum + i))
    sumsq=$((sumsq + (i * i)))
  done

  local mean=$((sum / count))
  local variance=$(( (sumsq / count) - (mean * mean) ))
  local sd=$(echo "sqrt($variance)" | awk '{print int($1)}')

  echo "$sd"
}

while IFS=', ' read -r uuid fname lname email password role ybirth hivStatus diagnosisYear artStatus artYear country; do
  # Skip the line if it's not a patient
  if [[ "$role" != "patient" ]]; then
    continue
  fi

  # Increment global counters
  global_total_users=$((global_total_users + 1))
  
  if [[ "$hivStatus" == "true" ]]; then
    global_hiv_positive_users=$((global_hiv_positive_users + 1))
    hiv_positive_user_count=$((hiv_positive_user_count + 1))
    
    # Add age to global age sum for HIV positive users
    if [[ -n "$ybirth" ]]; then
      age=$((current_year - ybirth))
      global_age_sum_hiv_positive=$((global_age_sum_hiv_positive + age))
    fi
  fi

  if [[ "$artStatus" == "true" ]]; then
    global_art_users=$((global_art_users + 1))
  fi

  # Increment country-specific counters
  country_user_count["$country"]=$((country_user_count["$country"] + 1))
  if [[ "$hivStatus" == "true" ]]; then
    country_hiv_positive_count["$country"]=$((country_hiv_positive_count["$country"] + 1))
  fi

  # Store country-specific life expectancy data
  life_expectancy=$((70 - age))
  country_life_expectancy_values["$country"]+="$life_expectancy "

done < "$file_path"

# Calculate global average age of HIV positive users
if [[ $global_hiv_positive_users -gt 0 ]]; then
  global_average_age_hiv_positive=$((global_age_sum_hiv_positive / global_hiv_positive_users))
else
  global_average_age_hiv_positive=0
fi

# Output the global statistics
echo "Global System Stats:"
echo "Total Users: $global_total_users"
echo "Total Users with HIV: $global_hiv_positive_users"
echo "Total Users on ART: $global_art_users"
echo "Average Age of HIV Positive Users: $global_average_age_hiv_positive"
echo "==========================="

# Output the statistics per country in a simple format
for country in "${!country_user_count[@]}"; do
  total_patients=${country_user_count["$country"]}
  hiv_positive_patients=${country_hiv_positive_count["$country"]}
  average_age=$((country_age_sum["$country"] / total_patients))

  life_expectancy_list=${country_life_expectancy_values["$country"]}
  median_life_expectancy=$(calculate_median "$life_expectancy_list")
  sd_life_expectancy=$(calculate_sd "$life_expectancy_list")

  echo "Country: $country"
  echo "Total Patients: $total_patients"
  echo "HIV Positive Patients: $hiv_positive_patients"
  echo "Average Age: $average_age"
  echo "Median Life Expectancy: $median_life_expectancy"
  echo "Life Expectancy SD: $sd_life_expectancy"
  echo "---------------------------"
done