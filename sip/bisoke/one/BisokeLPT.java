package sip.bisoke.one;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import static java.lang.System.exit;
import java.nio.file.Paths;
import java.util.Scanner;
import sip.bisoke.one.models.Admin;
import sip.bisoke.one.models.Patient;
import sip.bisoke.one.models.User;
import sip.bisoke.one.utils.Utils;
//import static sip.bisoke.one.BisokeLPT.updateUserProfileWithProcess;

public class BisokeLPT {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            Utils.clearConsole();
            showMainMenu();
            // try {
            //     Utils.showLoader(10);
            // } catch (InterruptedException ex) {
            // }
            boolean validChoice = false;

            while (!validChoice) {
                System.out.print("--- Select an Option >_ ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline left-over

                switch (choice) {
                    case 1 -> {
                        completeRegistration();
                        validChoice = true;
                    }
                    case 2 -> {
                        User user = authenticate(scanner);
                        if (user == null) {
                            System.out.println("Authentication failed.");
                            validChoice = true; // To exit the inner loop and restart the main menu
                        } else {
                            while (true) {
                                Utils.clearConsole();
                                user.showMenu();
                                System.out.print("--- Select an Option >_ ");
                                int userChoice = scanner.nextInt();
                                scanner.nextLine(); // Consume newline left-over
                                switch (user) {
                                    case Admin admin ->
                                        handleAdminChoice(userChoice, admin);
                                    case Patient patient ->
                                        handlePatientChoice(userChoice, patient);
                                    default ->
                                        System.out.println("Invalid user type.");
                                }
                            }
                        }
                    }
                    case 3 ->
                        Utils.showSystemHelp();
                    case 4 ->
                        exit(0);

                    default ->
                        System.out.println("❌ The option you selected is invalid.");
                }
            }
        }
    }

    private static void showMainMenu() {
        Utils utils = new Utils();

        utils.showLogo();
        //utils.showDivider("-");
        //utils.showBarChart();
        //utils.showDivider("-");
        //utils.showLineChart();
        // Utils.drawHorizontalDashboard(labels, values);
        String[] menuItems = {"📝Complete Registration", "🔐Login", "❓Help & FAQs", " Exit \n"};
        for (int i = 0; i < menuItems.length; i++) {
            Utils.prettyPrintWith("gray", (i + 1) + ". " + menuItems[i], true);
        }

    }

    private static void completeRegistration() {
        Scanner scanner = new Scanner(System.in);
        // Implementation for completing registration
        System.out.println("We need Your information to proceed...");

        String path = Paths.get(System.getProperty("user.dir"), "scripts/checkuuid.sh").toString();

        String completePath = Paths.get(System.getProperty("user.dir"), "scripts/complete_registration.sh").toString();
        System.out.print("-- Please enter your uuid >_ ");
        String uuid = scanner.nextLine();

        System.out.print("-- Please enter your email >_ ");
        String email = scanner.nextLine();

        String checkUser = checkUserwithProcess(path, email, uuid);

        if (checkUser.contains("not_found")) {
            System.out.println("\n User not found, please register first.\n ");

        } else {
            Console console = System.console();
            System.out.println("User with UUID " + uuid + " found! Let's proceed...");
            System.out.print("-- Please enter your first name >_ ");
            String firstName = scanner.nextLine();
            System.out.print("-- Please enter your last name >_ ");
            String lastName = scanner.nextLine();

            char[] passwordArray = console.readPassword("-- Please enter your password >_ ");
            String password = new String(passwordArray);

            System.out.print("-- Please enter your date of birth (dd-mm-yyyy) >_ ");
            String dateOfBirth = scanner.nextLine();

            System.out.print("-- Are you HIV positive? (true/false) >_ ");
            String hivStatus = scanner.nextLine();

            System.out.print("-- Please enter your diagnosis date (dd-mm-yyyy) >_ ");
            String diagnosisDate = scanner.nextLine();

            System.out.print("-- Are you on ART? (true/false) >_ ");
            String onART = scanner.nextLine();
            System.out.print("-- What is your country ISO? >_ ");
            String country = scanner.nextLine();

            System.out.print("-- When did you started ART? (dd-mm-yyyy) >_ ");
            String artYear = scanner.nextLine();

            String response = completeRegistrationWithProcessCall(completePath, email, uuid, password, firstName, lastName, dateOfBirth, country, hivStatus, diagnosisDate, onART, artYear);

            if ("success".equals(response)) {
                System.out.println("All good! You can now login.");
            } else {
                System.out.println("Registration failed: " + response);
            }
        }
    }

    private static User authenticate(Scanner scanner) {
        Console console = System.console();
        System.out.println("""
        *** Welcome, We need to authenticate you first! ***
        """);
        System.out.print("-- Please enter your email >_ ");
        String email = scanner.nextLine();
        //System.out.print("-- Please enter your password >_ ");

        char[] passwordArray = console.readPassword("-- Please enter your password >_ ");
        String password = new String(passwordArray);

        String userInfo = getUserInfoFromScript(email, password);

        if ("not_found".equalsIgnoreCase(userInfo.trim())) {
            System.out.println("Authentication failed.");
            return null;
        }
        //Utils.prettyPrintWith("yellow", userInfo, true);
        // Split the user info into fields and get each string individually
        String[] fields = userInfo.split(", ");

        // Here we Extract user role from the fields
        String role = fields[5];
        //System.out.println("Role: " + role);

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

    /// --- ADMIN FUNCTION TO INITIATE REGISTRATION --- ///
    private static void initiateRegistration() {
        try {
            Utils utils = new Utils();
            utils.showLogo();
            // utils.showDivider("-");
            Scanner scanner = new Scanner(System.in);
            System.out.println("""
        **__INITIATE USER REGISTRATION---***
        """);
            System.out.print("-- Please enter the user's email >_ ");
            String email = scanner.nextLine();
            //   String path = getCleanPath("register_user.bat");

            // URL path = ActiveDirectoryQuery.class.getResource("relative/path/to/EmailFQDN.exe");
            //  String path = "C://Users/STUDENT/Desktop/LPT/sip/bisoke/one/register_user.bat";
            String path = Paths.get(System.getProperty("user.dir"), "scripts/register_user.sh").toString();
            // Call the bash script to initiate registration
            String feedback = registerUserWithProcessCall(path, email);
            System.out.println(feedback);
            Utils.pauseSystem("Press Enter key to continue...");
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
            String path = Paths.get(System.getProperty("user.dir"), "scripts/user_retriever.sh").toString();
            String[] command = {
                "bash",
                "-c",
                path + " " + email + " " + password
            };
            ProcessBuilder pb = new ProcessBuilder(command);
            //pb.directory();
            pb.redirectErrorStream(true);
            Process process = pb.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            process.waitFor();
            // System.out.println("PROCESS OUT: " + output.toString());

            return output.toString().trim();

        } catch (IOException | InterruptedException e) {
            return null;
        }
    }

    private static String runProcess(String filePath) {
        try {

            ProcessBuilder pb = new ProcessBuilder("bash", "-c", filePath);
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
            String args = String.join(" ", email, uuid, password, fname, lname, dob, countryISOCode, hivStatus, diagnosisDate, onART, artStartDate);

            // Create ProcessBuilder instance
            ProcessBuilder pb = new ProcessBuilder(
                    "bash",
                    "-c",
                    filePath + " " + args
            );
            System.out.println("COMMAND: " + filePath + " " + args);
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

    public String getSystemPath(String relativePath) {
        return "";
    }

    public static String updateUserProfileWithProcess(String email, String uuid, String password, String firstName, String lastName, String dateOfBirth, String country, String HIVStatus, String diagnosisDate, String onART, String artYear, String role) {
        try {
            String path = Paths.get(System.getProperty("user.dir"), "scripts/update_profile.sh").toString();
            String args = String.join(" ", email, uuid, password, firstName, lastName, dateOfBirth, country, HIVStatus, diagnosisDate, onART, artYear, role);

            String[] command = {
                "bash",
                "-c",
                path + " " + args
            };
            //System.out.println("*************TO BE EXECUTED: " + String.join(" ", command));

            ProcessBuilder pb = new ProcessBuilder(command);
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

    private static String checkUserwithProcess(String path, String email, String uuid) {
        try {

            ProcessBuilder pb = new ProcessBuilder("bash", "-c", path + " " + email + " " + uuid);

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

            ProcessBuilder pb = new ProcessBuilder("bash", "-c", filePath + " " + email);

            pb.redirectErrorStream(true);
            Process process = pb.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            process.waitFor();
            // System.out.println("OUUUT: " + output.toString());
            return output.toString().trim();
        } catch (IOException | InterruptedException e) {
            return null;
        }
    }

    private static void handleDataExport() {
        try {
            // Get filename from user
            System.out.print("-- What should be the name of the file containing the data? >_ ");
            Scanner scanner = new Scanner(System.in);
            String filename = scanner.nextLine();

            final String path = Paths.get(System.getProperty("user.dir"), "scripts/csv_exporter.sh" + " " + filename).toString();
            runProcess(path);
            System.out.println("Data saved in Downloads Directory ✅");
        } catch (Exception e) {
            System.out.println("Could not export Data");
        }
    }

    public static int getAverageLifespanByCountry(String countryISOCode) {
        try {
            // Build the command to execute the Bash script
            ProcessBuilder processBuilder = new ProcessBuilder("bash", "-c", "scripts/lifespan_retriever.sh" + " " + countryISOCode
            );
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            // Capture the output from the Bash script
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String lifespanStr = reader.readLine();
            System.out.println("--> Lifespan: " + lifespanStr);
            return Integer.parseInt(lifespanStr);
        } catch (IOException | NumberFormatException e) {
            return 70;
        }
    }

    private static void handleAdminChoice(int choice, Admin admin) {
        switch (choice) {
            case 1 ->
                initiateRegistration();
            case 2 -> {
                handleDataExport();
                Utils.pauseSystem("\n Press Enter key to continue...");

            }
            case 3 ->
                System.out.println("Exporting analytics data...");
            case 4 ->
                admin.logout();
            default ->
                System.out.println("Invalid option.");
        }

    }

    private static void handlePatientChoice(int choice, Patient patient) {
        switch (choice) {
            case 1 -> {

                patient.showProfile();
                Utils.pauseSystem("\n Press Enter key to continue...");
            }
            case 2 -> {
                patient.viewLifespanExpentancy();
                Utils.pauseSystem("\n Press Enter key to continue...");
            }
            case 3 -> {
                patient.updateProfile();
                Utils.pauseSystem("\n Press Enter key to continue...");
            }
            case 4 -> {
                patient.logout();
            }
            default ->
                System.out.println("Invalid option.");
        }
    }

}
