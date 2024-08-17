package sip.bisoke.one.models;

public abstract class User {

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

    public abstract void login();

    public abstract void logout();

    public abstract void showHelp();

}
