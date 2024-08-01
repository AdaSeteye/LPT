package sip.bisoke.one.models;

public abstract class User {

    private final String uuid;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String password;
    private final String role;

    /// --- Constructor --- ///
    public User(String uuid, String firstName, String lastName, String email, String password, String role) {
        this.uuid = uuid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    /// --- Getters --- ///
    public String getUuid() {
        return uuid;
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

}
