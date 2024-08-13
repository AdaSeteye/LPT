package sip.bisoke.one.models;

import sip.bisoke.one.BisokeLPT;
import sip.bisoke.one.utils.Utils;

public class Admin extends User {

    public Admin(String uuid, String firstName, String lastName, String email, String password, String role) {
        super(uuid, firstName, lastName, email, password, role);
    }

    @Override
    public void showMenu() {
        String[] labels = {"Total Users", "onART Users", "New Users Today"};
        int[] values = {1500, 1200, 45};
        Utils.prettyPrintWith("yellow", "          +--------------------------------------------------+", true);
        Utils.prettyPrintWith("yellow", "          |      Hello 👋 " + getUsername() + ", Welcome Back!       |", true);
        Utils.prettyPrintWith("yellow", "          +--------------------------------------------------+", true);
        System.out.println();
        Utils.drawHorizontalDashboard(labels, values);
        System.out.println();
        System.out.println("1. 📂Initiate Registration");
        System.out.println("2. 📊Export User Data");
        System.out.println("3. 📈Export Analytics Data");
        System.out.println("4. 📴Logout");
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
