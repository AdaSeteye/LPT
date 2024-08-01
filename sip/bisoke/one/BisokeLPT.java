package sip.bisoke.one;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

/// --- Models --- ///
// TODO: Move these classes to separate files in the sip.bisoke.one.models package

abstract class User {

    protected String firstName;
    protected String lastName;
    protected String email;
    protected String password;
    protected String role;
    protected String uuid;

    public User(String uuid, String firstName, String lastName, String email, String password, String role) {
        this.uuid = uuid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return this.firstName + " " + this.lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public String getUuid() {
        return uuid;
    }

    public abstract void showMenu();

}

class Admin extends User {

    public Admin(String uuid, String firstName, String lastName, String email, String password, String role) {
        super(uuid, firstName, lastName, email, password, role);
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

    private final String dateOfBirth;
    private final boolean hivStatus;
    private final String diagnosisDate;
    private final boolean onART;
    private final String artStartDate;
    private final String countryISOCode;

    public Patient(String uuid, String firstName, String lastName, String email, String password, String role,
            String dateOfBirth, boolean hivStatus, String diagnosisDate, boolean onART, String artStartDate, String countryISOCode) {
        super(uuid, firstName, lastName, email, password, role);
        this.dateOfBirth = dateOfBirth;
        this.hivStatus = hivStatus;
        this.diagnosisDate = diagnosisDate;
        this.onART = onART;
        this.artStartDate = artStartDate;
        this.countryISOCode = countryISOCode;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public boolean isHivStatus() {
        return hivStatus;
    }

    public String getDiagnosisDate() {
        return diagnosisDate;
    }

    public boolean isOnART() {
        return onART;
    }

    public String getArtStartDate() {
        return artStartDate;
    }

    public String getCountryISOCode() {
        return countryISOCode;
    }

    @Override
    public void showMenu() {
        System.out.println("------------------------------------------------");
        System.out.println("Hello, " + getUsername());

        System.out.println("1. View Profile");
        System.out.println("2. Edit Profile");
        System.out.println("3. Logout");
    }

    public void viewProfile() {
        System.out.println("Profile Information:");
        // System.out.println("Identifier: " + this.getU());
        System.out.println("Name: " + this.getFirstName() + " " + getLastName());
        System.out.println("Email: " + getEmail());
        System.out.println("Date of Birth: " + dateOfBirth);
        System.out.println("HIV Positive: " + this.hivStatus);
        System.out.println("Diagnosis Date: " + (diagnosisDate != null ? diagnosisDate : "N/A"));
        System.out.println("On ART: " + this.onART);
        System.out.println("ART Start Date: " + (artStartDate != null ? artStartDate : "N/A"));
        System.out.println("Country ISO Code: " + countryISOCode);
        System.out.println("Computed Lifespan: 34 Years");

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
        Utils utils = new Utils();
        utils.showLogo();
        utils.showDivider("-");
        System.out.println("""
        *** Welcome, We need to authenticate you first! ***
        """);
        System.out.print("-- Please enter your email >_ ");
        String email = scanner.nextLine();
        System.out.print("-- Please enter your password >_ ");
        String password = scanner.nextLine();

        String userInfo = getUserInfoFromScript(email, password);
        System.out.println("USER DATA: " + userInfo);

        if ("not_found".equalsIgnoreCase(userInfo.trim())) {
            System.out.println("Authentication failed.");
            return null;
        }

        // Split the user info into fields
        String[] fields = userInfo.split(",");

        // Extract user role from the fields
        String role = fields[5]; // Assuming role is the 6th field

        if ("admin".equalsIgnoreCase(role)) {
            return new Admin(fields[0], fields[2], fields[3], fields[1], password, role);
        } else if ("patient".equalsIgnoreCase(role)) {
            return new Patient(fields[0], fields[2], fields[3], fields[1], password, role,
                    fields[6], Boolean.parseBoolean(fields[7]), fields[8],
                    Boolean.parseBoolean(fields[9]), fields[10], fields[11]);
        } else {
            System.out.println("Unknown role.");
            return null;
        }
    }

    private static String getUserInfoFromScript(String email, String password) {
        try {

            ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", "./user_retriever.bat", email, password);
            pb.redirectErrorStream(true);
            Process process = pb.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            process.waitFor();
            System.out.println(output.toString());
            return output.toString().trim();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // private static String getUserInfoFromScript(String email, String password) {
    //     try {
    //         ProcessBuilder pb = new ProcessBuilder("/path/to/retrieve_user_info.sh", email, password);
    //         pb.redirectErrorStream(true);
    //         Process process = pb.start();
    //         BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
    //         StringBuilder output = new StringBuilder();
    //         String line;
    //         while ((line = reader.readLine()) != null) {
    //             output.append(line).append("\n");
    //         }
    //         process.waitFor();
    //         return output.toString().trim();
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         return null;
    //     }
    // }
    private static String getUserRole(String email, String password) {
        // TODO: Implement a real authentication mechanism
        // For now, we'll just use a dummy check
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
