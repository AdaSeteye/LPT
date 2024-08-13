#!/bin/bash

# Define the data
countries=("Cameroon" "Rwanda" "Uganda")
percentages=(30 20 50)

# Function to draw the bar chart
draw_bar_chart() {
  #echo "Bar Chart:"
  max_length=40  # Maximum length of the bar in characters

  for i in "${!countries[@]}"; do
    country=${countries[$i]}
    percentage=${percentages[$i]}
    bar_length=$((percentage * max_length / 100))
    printf "%-15s | %s %d%%\n" "$country" "$(printf '%*s' "$bar_length" | tr ' ' '#')" "$percentage"
  done
}

# Call the function to draw the bar chart
draw_bar_chart
