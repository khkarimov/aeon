package aeon.core.common.helpers;

import java.util.Locale;

/**
 * Detects the type of OS being used.
 */
public final class OsCheck {

    protected static OSType detectedOS;

    /**
     * Detect the operating system from the os.name System property and cache
     * the result.
     *
     * @return - the operating system detected
     */
    public static OSType getOperatingSystemType() {
        if (detectedOS == null) {
            String os = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);
            if ((os.contains("mac") || os.contains("darwin"))) {
                detectedOS = OSType.MacOS;
            } else if (os.contains("win")) {
                detectedOS = OSType.Windows;
            } else if (os.contains("nux")) {
                detectedOS = OSType.Linux;
            } else {
                detectedOS = OSType.Other;
            }
        }
        return detectedOS;
    }

    /**
     * Types of Operating Systems.
     */
    public enum OSType {
        Windows, MacOS, Linux, Other
    }
}
