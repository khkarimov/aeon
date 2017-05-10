package aeon.core.common.helpers;

import java.util.Locale;

public final class OsCheck {

    protected static OSType detectedOS;

    /**
     * detect the operating system from the os.name System property and cache
     * the result
     *
     * @return - the operating system detected
     */
    public static OSType getOperatingSystemType() {
        if (detectedOS == null) {
            String os = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);
            if ((os.indexOf("mac") >= 0) || (os.indexOf("darwin") >= 0)) {
                detectedOS = OSType.MacOS;
            } else if (os.indexOf("win") >= 0) {
                detectedOS = OSType.Windows;
            } else if (os.indexOf("nux") >= 0) {
                detectedOS = OSType.Linux;
            } else {
                detectedOS = OSType.Other;
            }
        }
        return detectedOS;
    }

    /**
     * types of Operating Systems
     */
    public enum OSType {
        Windows, MacOS, Linux, Other
    }
}
