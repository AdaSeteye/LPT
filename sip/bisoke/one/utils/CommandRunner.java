// package sip.bisoke.one.utils;
// import java.io.BufferedReader;
// import java.io.IOException;
// import java.io.InputStreamReader;


// public class CommandRunner{
//     public static void main(String[] args) {
//         System.out.println("---Command -|- Runner---");
//     }

//     public Boolean isWindows(){
//         return System.getProperty("os.name").toLowerCase().startsWith("windows");
//     }

//     public String runProcess(String command){
//         try {

//             ProcessBuilder pb = new ProcessBuilder();
//             pb.command("sh", "")
//             pb.redirectErrorStream(true);
//             Process process = pb.start();

//             BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//             StringBuilder output = new StringBuilder();

//             String line;
//             while ((line = reader.readLine()) != null) {
//                 output.append(line).append("\n");
//             }

//             process.waitFor();
//             System.out.println(output.toString());
//             return output.toString();
//         } catch (IOException | InterruptedException e) {
//             return null;
//         }
//     }
// }