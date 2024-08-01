package sip.bisoke.one;

import java.util.Scanner;

/// --- Models --- ///
// TODO: Move these classes to separate files in the sip.bisoke.one.models package

abstract class User {

    protected String username;

    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public abstract void showMenu();

}

class Admin extends User {

    public Admin(String username) {
        super(username);
    }

    @Override
    public void showMenu() {
        System.out.println("------------------------------------------------------------");
        System.out.println("1. Manage Users");
        System.out.println("2. Export User Data");
        System.out.println("3. Export Analytics Data");
        System.out.println("4. Logout");
    }

}

class Patient extends User {

    public Patient(String username) {
        super(username);
    }

    @Override
    public void showMenu() {
        System.out.println("------------------------------------------------");
        System.out.println("Hello, " + getUsername());

        System.out.println("1. View Profile");
        System.out.println("2. Edit Profile");
        System.out.println("3. Logout");
    }
}

/// --- Utils --- ///
  class Utils {

    public void showLogo() {
        System.out.println("""
        
██████╗ ██╗███████╗ ██████╗ ██╗  ██╗███████╗    ██╗     ██████╗ ████████╗
██╔══██╗██║██╔════╝██╔═══██╗██║ ██╔╝██╔════╝    ██║     ██╔══██╗╚══██╔══╝
██████╔╝██║███████╗██║   ██║█████╔╝ █████╗      ██║     ██████╔╝   ██║   
██╔══██╗██║╚════██║██║   ██║██╔═██╗ ██╔══╝      ██║     ██╔═══╝    ██║   
██████╔╝██║███████║╚██████╔╝██║  ██╗███████╗    ███████╗██║        ██║   
╚═════╝ ╚═╝╚══════╝ ╚═════╝ ╚═╝  ╚═╝╚══════╝    ╚══════╝╚═╝        ╚═╝   
                                                                         
                                                                  
""");

    }

    public void showDivider(String token) {
        for (int i = 0; i < 20; i++) {
            System.out.print(token);

        }
        System.out.println("\n");
    }
}

/// --- Main Program --- ///

public class BisokeLPT {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        User user = authenticate(scanner);

        while (true) {
            user.showMenu();
            System.out.print("> Select an Option: ");
            int choice = scanner.nextInt();

            if (user instanceof Admin) {
                handleAdminChoice(choice, (Admin) user);
            } else if (user instanceof Patient) {
                handlePatientChoice(choice, (Patient) user);
            }
        }
    }

    private static User authenticate(Scanner scanner) {
        // Dummy authentication for demonstration purposes
        Utils utils = new Utils();
        utils.showLogo();
        utils.showDivider("-");
        System.out.println("""
        *** WELCOME, WE NEED TO AUTHENTICATE YOU FIRST 😉 ***
        """);
        System.out.print("-- Please enter your email >_ ");
        String email = scanner.nextLine();
        System.out.print("-- Please enter your password >_ ");
        String password = scanner.nextLine();

        String role = getUserRole(email, password);

        if ("admin".equalsIgnoreCase(role)) {
            return new Admin(email);
        } else {
            return new Patient(email);
        }
    }

    private static String getUserRole(String email, String password) {
        // Simulate a database lookup or an API call to fetch user role based on email and password
        // For demonstration, we assume "admin@example.com" is an admin, others are patients
        if ("admin@bisoke.com".equalsIgnoreCase(email)) {
            return "admin";
        } else {
            return "patient";
        }
    }

    private static void handleAdminChoice(int choice, Admin admin) {
        switch (choice) {
            case 1:
                System.out.println("Managing users...");
                break;
            case 2:
                System.out.println("Exporting user data...");
                break;
            case 3:
                System.out.println("Exporting analytics data...");
                break;
            case 4:
                System.out.println("Logging out...");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid option.");
        }
    }

    private static void handlePatientChoice(int choice, Patient patient) {
        switch (choice) {
            case 1:
                System.out.println("Viewing profile...");
                break;
            case 2:
                System.out.println("Editing profile...");
                break;
            case 3:
                System.out.println("Logging out...");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid option.");
        }
    }

}
