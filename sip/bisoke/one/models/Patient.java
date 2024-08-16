package sip.bisoke.one.models;

import java.io.Console;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;
import sip.bisoke.one.BisokeLPT;
import sip.bisoke.one.utils.Utils;

public class Patient extends User {

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
        //Utils.clearConsole();
        Utils.prettyPrintWith("green", "          +--------------------------------------------------+", true);
        Utils.prettyPrintWith("green", "          |      Hello 👋 " + getUsername() + ", Welcome Back!      |", true);
        Utils.prettyPrintWith("green", "          +--------------------------------------------------+", true);
        System.out.println();
        System.out.println("1. 🪪View Profile");
        System.out.println("2. 🕰️View Life Expectancy");
        System.out.println("3. 🔄Update Profile");
        System.out.println("4. 📴Logout");
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
        System.out.println(">-HIV Positive: " + (this.hivStatus ? "Yes" : "No"));
        System.out.println(">-Diagnosis Date: " + (diagnosisDate != null ? diagnosisDate : "N/A"));
        System.out.println(">-On ART: " + (this.onART == true ? "Yes" : "No"));
        System.out.println(">-ART Start Date: " + (artStartDate != null ? artStartDate : "N/A"));
        System.out.println(">-Country ISO Code: " + countryISOCode);
        //System.out.println(">-Computed Lifespan: 34 Years");
        System.out.println(getUserProfileSummary());

    }

    public void viewLifespanExpentancy() {
        int lifespan = computeLifespan();
        Utils.prettyPrintWith("yellow", "Your computed lifespan is " + lifespan + " years. \n", true);
    }

    // Method to compute the estimated lifespan
    public int computeLifespan() {
        // Get the average lifespan based on the patient's country ISO code
        int averageLifespan = BisokeLPT.getAverageLifespanByCountry(this.getCountryISOCode());

        // Convert date strings to LocalDate for calculation
        LocalDate dob = LocalDate.parse(dateOfBirth);
        LocalDate diagnosis = LocalDate.parse(diagnosisDate);

        // Calculate the patient's age at diagnosis
        int ageAtDiagnosis = (int) ChronoUnit.YEARS.between(dob, diagnosis);

        // Calculate initial remaining lifespan based on the average lifespan
        int remainingLifespan = averageLifespan - ageAtDiagnosis;

        if (hivStatus) {
            if (!onART) {
                // If the patient is not on ART, their lifespan is limited to 5 years after diagnosis
                remainingLifespan = 5;
            } else {
                // Patient is on ART, check when ART was started
                LocalDate artStart = LocalDate.parse(artStartDate);
                int yearsDelayed = (int) ChronoUnit.YEARS.between(diagnosis, artStart);

                // Calculate the remaining lifespan after starting ART
                remainingLifespan *= 0.9; // 90% of remaining lifespan if ART started immediately

                // Reduce remaining lifespan by 10% for each additional year delayed
                for (int i = 1; i <= yearsDelayed; i++) {
                    remainingLifespan *= 0.9;
                }
            }
        }

        // Round up the remaining lifespan to the nearest full year and ensure it's non-negative
        //System.out.println("------" + remainingLifespan);
        return Math.max((int) Math.ceil(remainingLifespan), 0);
    }

    public void updateProfile() {
        Console console = System.console();
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("---- | PROFILE UPDATE | ----");

            // Existing user data
            String currentEmail = this.getEmail();
            String currentFirstName = this.getFirstName();
            String currentLastName = this.getLastName();
            String currentPassword = this.getPassword(); // Assuming you have a method to get the hashed password
            String currentDateOfBirth = this.getDateOfBirth();
            String currentHIVStatus = hivStatus ? "Yes" : "No";
            String currentDiagnosisDate = this.getDiagnosisDate();
            String currentOnART = onART ? "Yes" : "No";
            String currentCountry = this.getCountryISOCode();
            String currentARTYear = this.getArtStartDate();

            System.out.print("-- Please enter your email (" + currentEmail + ") >_ ");
            String email = scanner.nextLine().trim();
            if (email.isEmpty()) {
                email = currentEmail;
            }

            System.out.print("-- Enter new first name (" + currentFirstName + ") >_ ");
            String firstName = scanner.nextLine().trim();
            if (firstName.isEmpty()) {
                firstName = currentFirstName;
            }

            System.out.print("-- Enter new last name (" + currentLastName + ") >_ ");
            String lastName = scanner.nextLine().trim();
            if (lastName.isEmpty()) {
                lastName = currentLastName;
            }

            char[] passwordArray = console.readPassword("-- Enter new password (leave blank to keep current) >_ ");
            String password = new String(passwordArray).trim();
            if (password.isEmpty()) {
                password = currentPassword; // Keep the hashed password
            } else {
                // Optionally hash the new password here if needed
            }

            System.out.print("-- Please enter your date of birth (" + currentDateOfBirth + ") >_ ");
            String dateOfBirth = scanner.nextLine().trim();
            if (dateOfBirth.isEmpty()) {
                dateOfBirth = currentDateOfBirth;
            }

            System.out.print("-- Are you HIV positive? (" + currentHIVStatus + ") >_ ");
            String HIVStatus = scanner.nextLine().trim();
            if (HIVStatus.isEmpty()) {
                HIVStatus = currentHIVStatus;
            }

            System.out.print("-- Please enter your diagnosis date (" + currentDiagnosisDate + ") >_ ");
            String diagnosisDate = scanner.nextLine().trim();
            if (diagnosisDate.isEmpty()) {
                diagnosisDate = currentDiagnosisDate;
            }

            System.out.print("-- Are you on ART? (" + currentOnART + ") >_ ");
            String onART = scanner.nextLine().trim();
            if (onART.isEmpty()) {
                onART = currentOnART;
            }

            System.out.print("-- What is your country ISO? (" + currentCountry + ") >_ ");
            String country = scanner.nextLine().trim();
            if (country.isEmpty()) {
                country = currentCountry;
            }

            System.out.print("-- When did you start ART? (" + currentARTYear + ") >_ ");
            String artYear = scanner.nextLine().trim();
            if (artYear.isEmpty()) {
                artYear = currentARTYear;
            }

            String result = BisokeLPT.updateUserProfileWithProcess(email, this.getUuid(), password, firstName, lastName, dateOfBirth, country, HIVStatus, diagnosisDate, onART, artYear, this.getRole());

            if (result != null) {
                System.out.println(result);
            } else {
                System.out.println("An error occurred during profile update.");
            }
        } catch (Exception e) {
            System.out.println("An error occurred during profile update.");
        }
    }

    @Override
    public void login() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void logout() {
        String[] args = {};
        BisokeLPT.main(args);
    }

    @Override
    public void showHelp() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
