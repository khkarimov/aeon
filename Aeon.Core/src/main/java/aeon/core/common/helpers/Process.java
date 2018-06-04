package aeon.core.common.helpers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper class for processes.
 */
public class Process {

    // SR - we should make this agnostic of the OS

    /**
     * Returns as a string list the windows processes by name given a string as input.
     * @param name the input string to match to the windows process.
     * @return a list of strings of matching windows processes.
     */
    public static List<String> getWindowsProcessesByName(String name) {
        try {
            List<String> output = new ArrayList<>();
            Runtime rt = Runtime.getRuntime();
            String[] commands = {(System.getenv("windir") + "\\system32\\" + "tasklist.exe"), "/fo", "csv", "/nh"};
            java.lang.Process proc = rt.exec(commands);

            BufferedReader br = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line;
            while ((line = br.readLine()) != null) {
                String processName = line.substring(0, line.indexOf(",")).replace("\"", "");
                if (processName.toLowerCase().equals(name.toLowerCase())) {
                    output.add(processName);
                }
            }
            return output;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Function kills a specific process with a given name as input.
     * @param name the name of the process to kill.
     */
    public static void killProcessByName(String name) {
        OsCheck.OSType osType = OsCheck.getOperatingSystemType();
        String[] command;
        switch (osType) {
            case Windows:
                command = new String[]{"taskkill", "/F", "/IM", name};
                break;
            case Linux:
                command = new String[]{"pkill", "-9", name};
                break;
            case MacOS:
                command = null; // need to look into killing a process with MacOS
                break;
            case Other:
                command = null;
                break;
            default:
                command = null;
        }
        if (command != null) {
            try {
                Runtime.getRuntime().exec(command);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
