package sip.bisoke.one.models;
import sip.bisoke.one.models.User;
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
        System.out.println("---------------------------------------------------------------------------------");
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