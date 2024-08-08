package sip.bisoke.one;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.Scanner;
import static sip.bisoke.one.BisokeLPT.updateUserProfileWithProcess;

//import sip.bisoke.one.models.Admin;
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
        System.out.println("1. Initiate Registration");
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
        System.out.println("---------------------------------------------------------------------------------");
        System.out.println("Hello, " + getUsername());

        System.out.println("1. View Profile");
        System.out.println("2. Update Profile");
        System.out.println("3. Logout");
    }

    public String getUserProfileSummary() {
        String diagnosisDateText = (diagnosisDate != null ? diagnosisDate : "N/A");
        String artStartDateText = (artStartDate != null ? artStartDate : "N/A");

        return String.format(
                "User Profile: %s %s (Email: %s) was born on %s, diagnosed HIV positive on %s, and started ART on %s. Current status: On ART: %s, Country: %s. Computed Lifespan: 34 Years.",
                getFirstName(),
                getLastName(),
                getEmail(),
                dateOfBirth,
                diagnosisDateText,
                artStartDateText,
                onART,
                countryISOCode
        );
    }

    public void showProfile() {

        System.out.println("--------------| USER PROFILE |--------------");
        System.out.println(">-Name: " + this.getFirstName() + " " + getLastName());
        System.out.println(">-Email: " + getEmail());
        System.out.println(">-Date of Birth: " + dateOfBirth);
        System.out.println(">-HIV Positive: " + this.hivStatus);
        System.out.println(">-Diagnosis Date: " + (diagnosisDate != null ? diagnosisDate : "N/A"));
        System.out.println(">-On ART: " + this.onART);
        System.out.println(">-ART Start Date: " + (artStartDate != null ? artStartDate : "N/A"));
        System.out.println(">-Country ISO Code: " + countryISOCode);
        System.out.println(">-Computed Lifespan: 34 Years");
        System.out.println(getUserProfileSummary());

    }

    public void updateProfile() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("---- | PROFILE UPDATE | ----");
            System.out.print("-- Please enter your email >_ ");
            String email = scanner.nextLine();

            System.out.print("-- Enter new first name >_ ");
            String firstName = scanner.nextLine();

            System.out.print("-- Enter new last name >_ ");
            String lastName = scanner.nextLine();

            System.out.print("-- Enter new password >_ ");
            String password = scanner.nextLine();

            System.out.print("-- Please enter your date of birth (yyyy-mm-dd) >_ ");
            String dateOfBirth = scanner.nextLine();

            System.out.print("-- Are you HIV positive? (true/false) >_ ");
            String HIVStatus = scanner.nextLine();

            System.out.print("-- Please enter your diagnosis date (yyyy-mm-dd) >_ ");
            String diagnosisDate = scanner.nextLine();

            System.out.print("-- Are you on ART? (true/false) >_ ");
            String onART = scanner.nextLine();

            System.out.print("-- What is your country ISO? >_ ");
            String country = scanner.nextLine();

            System.out.print("-- When did you start ART? (yyyy-mm-dd) >_ ");
            String artYear = scanner.nextLine();

            String result = updateUserProfileWithProcess(email, this.getUuid(), password, firstName, lastName, dateOfBirth, country, HIVStatus, diagnosisDate, onART, artYear);

            if (result != null) {
                System.out.println("Profile update result: " + result);
            } else {
                System.out.println("An error occurred during profile update.");
            }
        }
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
        for (int i = 0; i < 50; i++) {
            System.out.print(token);

        }
        System.out.println("\n");
    }
}

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

        String path = "C://Users/STUDENT/Desktop/LPT/sip/bisoke/one/checkuuid.sh";
        String completePath = "C://Users/STUDENT/Desktop/LPT/sip/bisoke/one/complete_registration.sh";
        System.out.print("-- Please enter your uuid >_ ");
        String uuid = scanner.nextLine();

        System.out.print("-- Please enter your email >_ ");
        String email = scanner.nextLine();

        String checkUser = checkUserwithProcess(path, email, uuid);

        if (checkUser.contains("not_found")) {
            System.out.println("\n User not found, please register first.\n ");

        } else {
            System.out.println("User with UUID " + uuid + " found! Let's proceed...");
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

            completeRegistrationWithProcessCall(completePath, email, uuid, password, firstName, lastName, dateOfBirth, country, hivStatus, diagnosisDate, onART, artYear);
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

        if ("not_found".equalsIgnoreCase(userInfo.trim())) {
            System.out.println("Authentication failed.");
            return null;
        }

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

            ProcessBuilder pb = new ProcessBuilder("bash", "-c", "mnt/c/Users/STUDENT/Desktop/SIP/LPT/sip/bisoke/one/user_retriever.sh", email, password
            );
            // pb.directory();
            // pb.redirectErrorStream(true);
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
                    "sh", filePath, email, uuid, password, fname, lname, dob, countryISOCode, hivStatus, diagnosisDate, onART, artStartDate
            );

            pb.redirectErrorStream(true);
            pb.directory(new File("./"));
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

    public static String updateUserProfileWithProcess(String email, String uuid, String password, String firstName, String lastName, String dateOfBirth, String country, String HIVStatus, String diagnosisDate, String onART, String artYear) {
        try {
            String path = Paths.get(System.getProperty("user.dir"), "scripts/update_profile.sh").toString();
            String[] command = {
                "sh",
                "-c",
                path + " " + email + " " + uuid + " " + password + " " + firstName + " " + lastName + " " + dateOfBirth + " " + country + " " + HIVStatus + " " + diagnosisDate + " " + onART + " " + artYear
            };
            System.out.println("Command: " + String.join(" ", command));

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
            case 1 ->
                patient.showProfile();
            case 2 ->
                patient.updateProfile();
            case 3 -> {
                System.out.println("Logging out...");
                System.exit(0);
            }
            default ->
                System.out.println("Invalid option.");
        }
    }

}
