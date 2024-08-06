package sip.bisoke.one.models;
import sip.bisoke.one.models.User;

public class Admin extends User {

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

