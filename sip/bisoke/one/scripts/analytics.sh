#!/bin/bash

file_path="user_store.txt"
name="$1"
output_dir="$HOME/BisokeLPT"
mkdir -p "$output_dir"  # Create the directory if it doesn't exist
output_file="$output_dir/$name.csv"

declare -A country_user_count
declare -A country_hiv_positive_count
declare -A country_age_sum
declare -A country_life_expectancy_values

current_year=$(date +"%Y")

# Initialize global counters
global_total_users=0
global_hiv_positive_users=0
global_age_sum_hiv_positive=0
global_life_expectancy_list=""

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

# Function to calculate standard deviation
calculate_sd() {
  local arr=($(echo "$1" | tr ' ' '\n'))
  local count=${#arr[@]}
  local sum=0
  local sumsq=0

  for i in "${arr[@]}"; do
    sum=$(echo "$sum + $i" | bc)
    sumsq=$(echo "$sumsq + ($i * $i)" | bc)
  done

  local mean=$(echo "$sum / $count" | bc -l)
  local variance=$(echo "($sumsq / $count) - ($mean * $mean)" | bc -l)
  local sd=$(echo "scale=10; sqrt($variance)" | bc -l)
  
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
    
    # Add age to global age sum for HIV positive users
    if [[ -n "$ybirth" ]]; then
      age=$((current_year - ybirth))
      global_age_sum_hiv_positive=$((global_age_sum_hiv_positive + age))
    fi
  fi

  # Increment country-specific counters
  country_user_count["$country"]=$((country_user_count["$country"] + 1))
  if [[ "$hivStatus" == "true" ]]; then
    country_hiv_positive_count["$country"]=$((country_hiv_positive_count["$country"] + 1))
  fi

  # Calculate life expectancy and store country-specific data
  if [[ -n "$ybirth" ]]; then
    age=$((current_year - ybirth))
    life_expectancy=$((70 - age))
    country_life_expectancy_values["$country"]+="$life_expectancy "
    global_life_expectancy_list+="$life_expectancy "
  fi

done < "$file_path"

# Write CSV header
echo "country,total_patients,hiv_positive_patients,median_life_expectancy,sd_life_expectancy,mean_life_expectancy,avg_age_hiv_positive,avg_life_expectancy" > "$output_file"

# Calculate and write statistics per country
for country in "${!country_user_count[@]}"; do
  total_patients=${country_user_count["$country"]}
  hiv_positive_patients=${country_hiv_positive_count["$country"]}
  age_sum_hiv_positive=${country_age_sum["$country"]}
  life_expectancy_list=${country_life_expectancy_values["$country"]}
  
  # Calculate statistics
  median_life_expectancy=$(calculate_median "$life_expectancy_list")
  sd_life_expectancy=$(calculate_sd "$life_expectancy_list")
  mean_life_expectancy=$(echo "$life_expectancy_list" | awk '{s=0; n=0; for (i=1;i<=NF;i++) {s+=$i; n++} if (n > 0) print s/n}')
  avg_age_hiv_positive=$(( (hiv_positive_patients > 0) ? (global_age_sum_hiv_positive / hiv_positive_patients) : 0 ))
  avg_life_expectancy=$(echo "$life_expectancy_list" | awk '{s=0; n=0; for (i=1;i<=NF;i++) {s+=$i; n++} if (n > 0) print s/n}')

  # Trim spaces from country name
  country=$(echo "$country" | tr -d '\n' | xargs)
  
  # Write to CSV
  echo "$country,$total_patients,$hiv_positive_patients,$median_life_expectancy,$sd_life_expectancy,$mean_life_expectancy,$avg_age_hiv_positive,$avg_life_expectancy">>"$output_file"
done


# Calculate and print global statistics
global_mean_life_expectancy=$(echo "$global_life_expectancy_list" | awk '{s=0; n=0; for (i=1;i<=NF;i++) {s+=$i; n++} if (n > 0) print s/n}')
global_sd_life_expectancy=$(calculate_sd "$global_life_expectancy_list")
global_median_life_expectancy=$(calculate_median "$global_life_expectancy_list")

# Add a new row TOTAL (intead of Country) and add the global statistics
echo "TOTAL,$global_total_users,$global_hiv_positive_users,$global_median_life_expectancy,$global_sd_life_expectancy,$global_mean_life_expectancy,$global_average_age_hiv_positive,$global_mean_life_expectancy">>"$output_file"

# Clean up the CSV file
sed '/^$/d' "$output_file" | sed 's/\r//' > "${output_file}.tmp" && mv "${output_file}.tmp" "$output_file"


# check if there is an error at any point, print sucess if not
if [ $? -eq 0 ]; then
    echo "success"
else
    echo "An error occurred during data export."
fi