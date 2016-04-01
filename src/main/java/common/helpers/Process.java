package common.helpers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DionnyS on 4/1/2016.
 */
public class Process {
    public static List<String> GetWindowsProcessesByName(String name) {
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
}
