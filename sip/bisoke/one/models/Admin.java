package sip.bisoke.one.models;

import java.nio.file.Paths;
import java.util.Scanner;
import sip.bisoke.one.BisokeLPT;
import sip.bisoke.one.utils.Utils;

public class Admin extends User {

    public Admin(String uuid, String firstName, String lastName, String email, String password, String role) {
        super(uuid, firstName, lastName, email, password, role);
    }

    @Override
    public void showMenu() {

        Utils.prettyPrintWith("yellow", "                          +--------------------------------------------------+", true);
        Utils.prettyPrintWith("yellow", "                          |      Hello 👋 " + getUsername() + ", Welcome Back!       |", true);
        Utils.prettyPrintWith("yellow", "                          +--------------------------------------------------+", true);
        System.out.println();

        /// ---- GET DASHBOARD STATS ---- ///
       
        System.out.println("");
        String path = Paths.get(System.getProperty("user.dir"), "scripts/get_dashboard_stats.sh").toString();
        String dashbordData = BisokeLPT.runStatsProcess(path);
        if (dashbordData != null && !dashbordData.isEmpty()) {
            String[] dashboardStats = dashbordData.split(",");
            String[] labels = {"TOTAL PATIENTS", "HIV+ PATIENTS", "PATIENTS ON ART", "AVG HIV+ AGE"};
            Utils.drawHorizontalDashboard(labels, dashboardStats);
        }

        /// ---- GET DASHBOARD STATS ---- ///
        System.out.println();
        System.out.println("1. 📂Initiate Registration");
        System.out.println("2. 📊Export User Data");
        System.out.println("3. 📈Export Analytics Data");
        System.out.println("4. 📴Logout");
    }

    public static void exportAnalyticsData() {
        String path = Paths.get(System.getProperty("user.dir"), "scripts/export_analytics_data.sh").toString();
        // -- ASK USER TO ENTER FILE NAME -- //
        Scanner scanner = new Scanner(System.in);
        Utils.prettyPrintWith("yellow", "---Enter the file name to save the data >_", false);
        String fileName = scanner.nextLine();

        String data = BisokeLPT.runAnalyticsExportProcess(path, fileName);
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
