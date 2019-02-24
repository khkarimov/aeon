package aeon.core.common.helpers;

import aeon.core.common.web.BrowserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;

/**
 * Class manages the environment of the client.
 */
public final class ClientEnvironmentManager {

    private static final Dimension[] supportedResolutions = {new Dimension(1024, 768)};
    private static Logger log = LoggerFactory.getLogger(ClientEnvironmentManager.class);

    /**
     * Function that ensures each environment browser is clean.
     *
     * @param browserType                  the browser type input.
     * @param browserAcceptedLanguageCodes the browser's accepted language codes.
     * @param ensureCleanEnvironment       boolean of the environment's cleanliness.
     */
    public static void manageEnvironment(BrowserType browserType, String browserAcceptedLanguageCodes, boolean ensureCleanEnvironment) {
        if (ensureCleanEnvironment) {
            log.info("Cleaning Client Environment.");
            ensureCleanEnvironment(browserType);
        }

        verifyScreenResolution();

        switch (browserType) {
            case INTERNET_EXPLORER:
                // TODO(DionnyS): JAVA_CONVERSION
                // ConfigureInternetExplorerSettings(browserAcceptedLanguageCodes);
                break;
            case FIREFOX:
                enforceDPI();
                break;
        }
    }

    private static void ensureCleanEnvironment(BrowserType browserType) {
        switch (browserType) {
            case INTERNET_EXPLORER:
                log.info("Killing INTERNET_EXPLORER related processes.");
                // TODO(DionnyS): JAVA_CONVERSION
                // KillProcesses(new String[]{"Internet_Explorer", "IEDriverServer"});
                break;
            case FIREFOX:
                log.info("Killing FIREFOX related processes.");
                // TODO(DionnyS): JAVA_CONVERSION
                // KillProcesses(new String[]{"FIREFOX"});
                break;
            case CHROME:
                log.info("Killing CHROME related processes.");
                // TODO(DionnyS): JAVA_CONVERSION
                // KillProcesses(new String[]{"CHROME", "chromedriver"});
                break;
        }
    }

    private static void enforceDPI() {
        if (OsCheck.getOperatingSystemType() == OsCheck.OSType.WINDOWS) {
            int pixelPerInch = java.awt.Toolkit.getDefaultToolkit().getScreenResolution();
            log.info("Checking DPI setting are set to 100%");
            if (pixelPerInch / 96f != 1.00) {
                throw new RuntimeException(String.format(
                        "Environment DPI not set to 100%%.%1$sModify value in \"Control Panel\\Appearance and Personalization\\Display\" to 100%%",
                        System.lineSeparator()));
            }
        }
    }

    private static void verifyScreenResolution() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();

        boolean resolutionSupported = false;
        for (Dimension res : supportedResolutions) {
            if (res.width == width && res.height == height) {
                resolutionSupported = true;
            }
        }

        if (!resolutionSupported) {
            String supportedResolutionsListString = "";

            for (Dimension res : supportedResolutions) {
                supportedResolutionsListString = String.format("%1$s%2$s%3$s", supportedResolutionsListString, res.width + "x" + res.height, System.lineSeparator());
            }

            log.info(String.format("The Resolution %1$sx%2$s is not in the list of supported resolutions.%3$sSupported Resolutions:%3$s%4$s",
                    width, height, System.lineSeparator(), supportedResolutionsListString));
        }
    }
}
