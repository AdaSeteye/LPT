package sip.bisoke.one.models;

public abstract class User {

    protected final String uuid;
    protected final String firstName;
    protected final String lastName;
    protected final String email;
    protected final String password;
    protected final String role;

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

    /// --- Abstract Methods --- ///
    public abstract void showMenu();

}
