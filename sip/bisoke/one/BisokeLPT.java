package sip.bisoke.one;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.Scanner;
import sip.bisoke.one.models.Admin;
import sip.bisoke.one.models.Patient;
import sip.bisoke.one.models.User;
import sip.bisoke.one.utils.Utils;


/// --- Main Program --- ///

public class BisokeLPT {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            showMainMenu();
            System.out.print("--- Select an Option >_ ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline left-over

            switch (choice) {
                case 1:
                    completeRegistration();
                    break;
                case 2:
                    User user = authenticate(scanner);
                    if (user == null) {
                        System.out.println("Authentication failed.");
                        continue; // Restart the menu if authentication fails
                    }
                    while (true) {
                        user.showMenu();
                        System.out.print("--- Select an Option >_ ");
                        int userChoice = scanner.nextInt();
                        scanner.nextLine(); // Consume newline left-over

                        if (user instanceof Admin) {
                            handleAdminChoice(userChoice, (Admin) user);
                        } else if (user instanceof Patient) {
                            handlePatientChoice(userChoice, (Patient) user);
                        }
                    }
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void showMainMenu() {
        Utils utils = new Utils();
        utils.showLogo();
        utils.showDivider("-");
        System.out.println("1. Complete Registration");
        System.out.println("2. Login \n");
    }

    private static void completeRegistration() {
        Scanner scanner = new Scanner(System.in);
        // Implementation for completing registration
        System.out.println("We need Your information to proceed...");

        String path = "C://Users/STUDENT/Desktop/LPT/sip/bisoke/one/checkuuid.bat";
        System.out.print("-- Please enter your uuid >_ ");
        String uuid = scanner.nextLine();

        System.out.print("-- Please enter your email >_ ");
        String email = scanner.nextLine();

        String checkUser = checkUserwithProcess(path, email, uuid);

        if (checkUser.contains("not_found")) {
            System.out.println("\n User not found, please register first.\n ");

        } else {
            System.out.println("User with UUID" + uuid + "found! Let's proceed...");
            System.out.print("-- Please enter your first name >_ ");
            String firstName = scanner.nextLine();
            System.out.print("-- Please enter your last name >_ ");
            String lastName = scanner.nextLine();

            System.out.print("-- Please enter your password >_ ");
            String password = scanner.nextLine();

            System.out.print("-- Please enter your date of birth (yyyy-mm-dd) >_ ");
            String dateOfBirth = scanner.nextLine();

            System.out.print("-- Are you HIV positive? (true/false) >_ ");
            String hivStatus = scanner.nextLine();

            System.out.print("-- Please enter your diagnosis date (yyyy-mm-dd) >_ ");
            String diagnosisDate = scanner.nextLine();

            System.out.print("-- Are you on ART? (true/false) >_ ");
            String onART = scanner.nextLine();
            System.out.print("-- What is your country ISO? >_ ");
            String country = scanner.nextLine();

            System.out.print("-- When did you started ART? (yyyy-mm-dd) >_ ");
            String artYear = scanner.nextLine();

            completeRegistrationWithProcessCall(path, email, uuid, password, firstName, lastName, dateOfBirth, country, hivStatus, diagnosisDate, onART, artYear);
        }
    }

    private static User authenticate(Scanner scanner) {

        System.out.println("""
        *** Welcome, We need to authenticate you first! ***
        """);
        System.out.print("-- Please enter your email >_ ");
        String email = scanner.nextLine();
        System.out.print("-- Please enter your password >_ ");
        String password = scanner.nextLine();

        String userInfo = getUserInfoFromScript(email, password);
        

        if ("not_found".equalsIgnoreCase(userInfo)) {
            System.out.println("Authentication failed.");
            return null;
        }
        else{
            System.out.println("USER DATA: " + userInfo);
             // Split the user info into fields and get each string individually
        String[] fields = userInfo.split(", ");

        // Here we Extract user role from the fields
        String role = fields[5];

        if ("admin".equalsIgnoreCase(role)) {
            return new Admin(fields[0], fields[1], fields[2], fields[3], fields[4], fields[5]);
        } else if ("patient".equalsIgnoreCase(role)) {
            return new Patient(fields[0], fields[1], fields[2], fields[3], fields[4], fields[5], fields[6],
                    Boolean.parseBoolean(fields[7]), fields[8],
                    Boolean.parseBoolean(fields[9]), fields[10], fields[11]);
        } else {
            System.out.println("Unknown role.");
            return null;
        }
        }

       
    }

    /// --- ADMIN FUNCTION TO INITIATE REGISTRATION --- ///
    private static void initiateRegistration() {
        try {
            Utils utils = new Utils();
            utils.showLogo();
            utils.showDivider("-");
            Scanner scanner = new Scanner(System.in);
            System.out.println("""
        **__INITIATE USER REGISTRATION---***
        """);
            System.out.print("-- Please enter the user's email >_ ");
            String email = scanner.nextLine();
            //   String path = getCleanPath("register_user.bat");

            // URL path = ActiveDirectoryQuery.class.getResource("relative/path/to/EmailFQDN.exe");
            String path = "C://Users/STUDENT/Desktop/LPT/sip/bisoke/one/register_user.bat";
            // Call the bash script to initiate registration
            String feedback = registerUserWithProcessCall(path.replace("\\", "/"), email);
            System.out.println(feedback);
        } catch (Exception e) {
            System.out.println("Could not initiate registration");
        }
    }

    // private static String getCleanPath(String fileName) throws IOException {
    //     String path = new File(fileName).getCanonicalPath();
    //     return path;
    // }
    /// --- ADMIN FUNCTION TO EXPORT USER DATA --- ///

   private static String getUserInfoFromScript(String email, String password) {
    try {
        // Construct the path to the bash script
        //System.getProperty("user.dir"),
        String path = Paths.get("io/user_retriever.sh").toString();
        
        // Construct the command array to run the bash script with arguments
        String subcommand = path + " " + email + " " + password;
        String[] command = { "bash", "-c", subcommand };
        
        // Initialize the ProcessBuilder with the command array
        ProcessBuilder pb = new ProcessBuilder(command);
        
        // Redirect error stream to the standard output
        pb.redirectErrorStream(true);
        
        // Start the process
        Process process = pb.start();
        
        // Read the output of the process
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder output = new StringBuilder();
        String line;
        
        while ((line = reader.readLine()) != null) {
            output.append(line).append("\n");
        }
        
        // Wait for the process to complete
        process.waitFor();
        
        // Print and return the output
        System.out.println(output.toString());
        return output.toString().trim();
    } catch (IOException | InterruptedException e) {
        return "not_found";
    }
}

    private static String runProcess(String filePath) {
        try {

            ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", filePath);
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
        } catch (IOException | InterruptedException e) {
            return null;
        }
    }

    private static String completeRegistrationWithProcessCall(String filePath, String email, String uuid, String password, String fname, String lname, String dob, String hivStatus, String diagnosisDate, String onART, String artStartDate, String countryISOCode) {
        try {

            // Create ProcessBuilder instance
            ProcessBuilder pb = new ProcessBuilder(
                    "cmd.exe", "/c", filePath, email, uuid, password, fname, lname, dob, countryISOCode, hivStatus, diagnosisDate, onART, artStartDate
            );

            pb.redirectErrorStream(true);
            Process process = pb.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            process.waitFor();
            System.out.println("OUUUT: " + output.toString());
            return output.toString().trim();
        } catch (IOException | InterruptedException e) {
            return null;
        }
    }

    private static String checkUserwithProcess(String path, String email, String uuid) {
        try {

            ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", path, email, uuid);

            pb.redirectErrorStream(true);
            Process process = pb.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            process.waitFor();

            return output.toString().trim();
        } catch (IOException | InterruptedException e) {
            return null;
        }
    }

    private static String registerUserWithProcessCall(String filePath, String email) {
        try {

            ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", filePath, email);

            pb.redirectErrorStream(true);
            Process process = pb.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            process.waitFor();
            System.out.println("OUUUT: " + output.toString());
            return output.toString().trim();
        } catch (IOException | InterruptedException e) {
            return null;
        }
    }

    private static void handleDataExport() {
        try {
            runProcess("C://Users/STUDENT/Desktop/LPT/sip/bisoke/one/csv_exporter.bat");
            System.out.println("Data saved in Downloads Directory");
        } catch (Exception e) {
            System.out.println("Could not export Data");
        }
    }

    private static void handleAdminChoice(int choice, Admin admin) {
        switch (choice) {
            case 1:
                initiateRegistration();
                break;
            case 2:
                handleDataExport();
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
