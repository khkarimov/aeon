package aeon.core.common.helpers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper class for processes.
 */
public class Process {

    private static Logger log = LoggerFactory.getLogger(Process.class);

    /**
     * Private constructor to prevent instantiation.
     */
    private Process() {
        throw new IllegalStateException("Utility class");
    }

    // SR - we should make this agnostic of the OS

    /**
     * Returns as a string list the windows processes by name given a string as input.
     *
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
                String processName = line.substring(0, line.indexOf(',')).replace("\"", "");
                if (processName.equalsIgnoreCase(name)) {
                    output.add(processName);
                }
            }
            return output;
        } catch (IOException e) {
            log.error("Error retrieving processes", e);
        }

        return new ArrayList<>();
    }

    /**
     * Function kills a specific process with a given name as input.
     *
     * @param name the name of the process to kill.
     */
    public static void killProcessByName(String name) {
        OsCheck.OSType osType = OsCheck.getOperatingSystemType();
        String[] command;
        switch (osType) {
            case WINDOWS:
                command = new String[]{"taskkill", "/F", "/IM", name};
                break;
            case LINUX:
                command = new String[]{"pkill", "-9", name};
                break;
            case MAC_OS:
                command = null; // need to look into killing a process with MacOS
                break;
            case OTHER:
                command = null;
                break;
            default:
                command = null;
        }
        if (command != null) {
            try {
                Runtime.getRuntime().exec(command);
            } catch (IOException e) {
                log.error("Error killing processes", e);
            }
        }
    }
}
