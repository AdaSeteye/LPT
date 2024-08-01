package sip.bisoke.one;

import java.util.Scanner;

public class BisokeLPT {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            boolean running = true;

            while (running) {
                System.out.println("=== VitaTrack Menu ===");
                System.out.println("1. Login");
                System.out.println("2. Exit");
                System.out.println("3. About");
                System.out.print("Select an option: ");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline left-over

                switch (choice) {
                    case 1:
                        System.out.print("Enter email: ");
                        String email = scanner.nextLine();
                        System.out.print("Enter password: ");
                        String password = scanner.nextLine();
                        login(email, password);
                        break;
                    case 2:
                        System.out.println("Exiting the program. Goodbye!");
                        running = false;
                        break;
                    case 3:
                        showAbout();
                        break;
                    default:
                        System.out.println("Invalid choice. Please select again.");
                }
            }
        }
    }

    private static void login(String email, String password) {
        // Implement login logic here
        System.out.println("Email sent........");
    }

    private static void showAbout() {
        // Display information about the program
        System.out.println("VitaTrack - Life Prognosis Management Tool");
        System.out.println("Version 1.0");
        System.out.println("Developed by Bisoke I Team");
    }
}
