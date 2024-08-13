package sip.bisoke.one.utils;

/// --- Utils --- ///

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Utils {

    public void showLogo() {
        String ribbon = centerText("""
                ••••••••
               ••••••••••
               ••••  ••••   
               ••••  ••••   
                •••• •••    
                 ••••••     
                  ••••      
                 ••••••     
                ••••••••    
               ••••  ••••   
              ••••    ••••  
            """, 28);
        //  Utils.prettyPrintWith("red", ribbon, true);
        Utils.prettyPrintWith("red", """
        
██████╗ ██╗███████╗ ██████╗ ██╗  ██╗███████╗    ██╗     ██████╗ ████████╗
██╔══██╗██║██╔════╝██╔═══██╗██║ ██╔╝██╔════╝    ██║     ██╔══██╗╚══██╔══╝
██████╔╝██║███████╗██║   ██║█████╔╝ █████╗      ██║     ██████╔╝   ██║   
██╔══██╗██║╚════██║██║   ██║██╔═██╗ ██╔══╝      ██║     ██╔═══╝    ██║   
██████╔╝██║███████║╚██████╔╝██║  ██╗███████╗    ███████╗██║        ██║   
╚═════╝ ╚═╝╚══════╝ ╚═════╝ ╚═╝  ╚═╝╚══════╝    ╚══════╝╚═╝        ╚═╝   

••••••••••••••|🎗️The Life Prognosis Tool You can Trust🎗️|•••••••••••••• \n                                                                 
""", false);
        // Utils.prettyPrintWith("red", ribbon, false);

    }

    public void showDivider(String token) {
        for (int i = 0; i < 50; i++) {
            System.out.print(token);

        }
        System.out.println("\n");
    }

    public static void clearConsole() {
        try {
            // If running on Windows
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                // For UNIX-like systems
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (IOException | InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void prettyPrintWith(String theme, String message, Boolean line) {
        String red = "\033[95m";
        String green = "\u001B[32m";
        String yellow = "\u001B[33m";
        String reset = "\u001B[0m";
        String blue = "\u001B[34m";
        String purple = "\u001B[35m";
        String cyan = "\u001B[36m";
        String gray = "\u001B[37m";
        String redBackground = "\u001B[41m";
        String greenBackground = "\u001B[42m";
        String blueBackground = "\u001B[44m";
        String brightYellowBackground = "\u001B[103m";

        String color = switch (theme) {
            case "red" ->
                red;
            case "green" ->
                green;
            case "gray" ->
                gray;

            case "yellow" ->
                yellow;
            case "blue" ->
                blue;
            case "purple" ->
                purple;
            case "cyan" ->
                cyan;
            case "redBG" ->
                redBackground;
            case "greenBG" ->
                greenBackground;
            case "blueBG" ->
                blueBackground;
            case "yellowBG" ->
                brightYellowBackground;
            default ->
                reset;
        };
        if (line) {
            System.out.println(color + message + reset);
        } else {
            System.out.print(color + message + reset);
        }
    }

    public void showBarChart() {
        int[] data = {5, 10, 15, 20, 25};  // Example data

        System.out.println("Simple Bar Chart:");
        for (int i = 0; i < data.length; i++) {
            System.out.printf("%2d | ", i + 1);
            for (int j = 0; j < data[i]; j++) {
                Utils.prettyPrintWith("red", "#", false);  // The bar is made up of '#'
            }
            Utils.prettyPrintWith("blueBG", " (" + data[i] + ")", false);
        }
    }

    public void showLineChart() {
        int[] data = {1, 4, 9, 16, 9, 4, 1};  // Example data

        int max = 0;
        for (int value : data) {
            if (value > max) {
                max = value;
            }
        }

        System.out.println("Simple Line Chart:");
        for (int i = max; i >= 0; i--) {
            for (int j = 0; j < data.length; j++) {
                if (data[j] >= i) {
                    System.out.print(" * ");
                } else {
                    System.out.print("   ");
                }
            }
            System.out.println();
        }

        // X-axis
        for (int i = 0; i < data.length; i++) {
            System.out.print("---");
        }
        System.out.println();

        // X-axis labels
        for (int i = 0; i < data.length; i++) {
            System.out.printf(" %d ", i + 1);
        }
        System.out.println();
    }

    public static void drawHorizontalDashboard(String[] labels, String[] values) {
        String[] colors = {
            "\u001B[41m", // Red background
            "\u001B[42m", // Green background
            "\u001B[44m", // Blue background
            "\u001B[45m"
        };
        String resetColor = "\u001B[0m";

        // Prepare the lines for each container
        String[][] containers = new String[labels.length][];
        for (int i = 0; i < labels.length; i++) {
            containers[i] = createContainerLines(labels[i], values[i], colors[i], resetColor);
        }

        // Print the containers side by side
        for (int line = 0; line < containers[0].length; line++) {
            for (int i = 0; i < labels.length; i++) {
                System.out.print(containers[i][line] + "  "); // Add space between containers
            }
            System.out.println(); // New line after each row
        }
    }

    public static String[] createContainerLines(String label, String value, String color, String resetColor) {
        String[] lines = new String[7];
        lines[0] = color + "✨•••--------------•••✨" + resetColor;
        lines[1] = color + "|                      |" + resetColor;
        lines[2] = color + "•" + centerText(label, 22) + "•" + resetColor;
        lines[3] = color + "|                      |" + resetColor;
        lines[4] = color + "•" + centerText(String.valueOf(value), 22) + "•" + resetColor;
        lines[5] = color + "|                      |" + resetColor;
        lines[6] = color + "✨•••--------------•••✨" + resetColor;
        return lines;
    }

    public static String centerText(String text, int width) {
        int padding = (width - text.length()) / 2;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < padding; i++) {
            sb.append(" ");
        }
        sb.append(text);
        while (sb.length() < width) {
            sb.append(" ");
        }
        return sb.toString();
    }

    public static void showSystemHelp() {

        System.out.println("\n\033[31m---> What is BisokeLPT\033[0m");
        System.out.println("Bisoke LPT is a software solution that is designed by BisokeOne members to predict the lifespan of HIV AIDS patients... Blaablaaablaaaba");

        System.out.println("\033[31m---> How it works\033[0m");
        System.out.println("The user should be authenticated to use the system. The system will ask the user to enter their personal information and the system will calculate the lifespan of the user based on the information provided.");

        System.out.println("\n\033[31m---> How to Download Analytics Data\033[0m");
        System.out.println(".....");

        System.out.println("\n\033[31m---> How to Calculate Lifespan Expectancy\033[0m");

        System.out.println("\n\033[31m---> Are My Data Safe?\033[0m");
        System.out.println("Yes, your data is safe with BisokeLPT. We prioritize data security and hash your passwords and other security measures to protect your information..");

        System.out.println("\n\033[31m--- Other Information\033[0m");
        System.out.println("---");
    }

    public static void printTableWith() {
        // Table Headers
        System.out.println("+------------+------------+------------+------------+------------+------------+");
        System.out.println("|  Column 1  |  Column 2  |  Column 3  |  Column 4  |  Column 5  |  Column 6  |");
        System.out.println("+------------+------------+------------+------------+------------+------------+");

        // Table Rows
        for (int i = 0; i < 3; i++) {
            System.out.println("|    Row " + (i + 1) + "    |    Row " + (i + 1) + "    |    Row " + (i + 1) + "    |    Row " + (i + 1) + "    |    Row " + (i + 1) + "    |    Row " + (i + 1) + "    |");
            System.out.println("+------------+------------+------------+------------+------------+------------+");
        }
    }

    public static void showLoader(int durationInSeconds) throws InterruptedException {
        String[] loaderGraphics = {
            "[          ]",
            "[=         ]",
            "[==        ]",
            "[===       ]",
            "[====      ]",
            "[=====     ]",
            "[======    ]",
            "[=======   ]",
            "[========  ]",
            "[========= ]",
            "[==========]"
        };

        long endTime = System.currentTimeMillis() + durationInSeconds * 1000;

        int i = 0;
        while (System.currentTimeMillis() < endTime) {
            System.out.print("\r" + loaderGraphics[i % loaderGraphics.length]);
            Thread.sleep(200);  // Pause for 200 milliseconds
            i++;
        }

    }

    public static void pauseSystem(String message) {
        System.out.println(message);
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();  // Wait for the user to press Enter
    }

    public static boolean isValidDateFormat(String date) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-mm-yyyy");
            LocalDate.parse(date, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
