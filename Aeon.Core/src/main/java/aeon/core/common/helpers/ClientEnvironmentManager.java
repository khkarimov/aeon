package aeon.core.common.helpers;

import com.sun.glass.ui.Size;
import aeon.core.common.web.BrowserType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;

public final class ClientEnvironmentManager {
    private static final Size[] supportedResolutions = {new Size(1024, 768)};
    private static Logger log = LogManager.getLogger(ClientEnvironmentManager.class);

    public static void ManageEnvironment(BrowserType browserType, String browserAcceptedLanguageCodes, boolean ensureCleanEnvironment) {
        if (ensureCleanEnvironment) {
            log.info("Cleaning Client Environment.");
            EnsureCleanEnvironment(browserType);
        }

        verifyScreenResolution();

        switch (browserType) {
            case InternetExplorer:
                // TODO(DionnyS): JAVA_CONVERSION
                // ConfigureInternetExplorerSettings(browserAcceptedLanguageCodes);
                break;
            case Firefox:
                EnforceDPI();
                break;
        }
    }

    private static void EnsureCleanEnvironment(BrowserType browserType) {
        switch (browserType) {
            case InternetExplorer:
                log.info("Killing Internet Explorer related processes.");
                // TODO(DionnyS): JAVA_CONVERSION
                // KillProcesses(new String[]{"iexplore", "IEDriverServer"});
                break;
            case Firefox:
                log.info("Killing Firefox related processes.");
                // TODO(DionnyS): JAVA_CONVERSION
                // KillProcesses(new String[]{"firefox"});
                break;
            case Chrome:
                log.info("Killing Chrome related processes.");
                // TODO(DionnyS): JAVA_CONVERSION
                // KillProcesses(new String[]{"chrome", "chromedriver"});
                break;
        }
    }

    private static void EnforceDPI() {
        if (OsCheck.getOperatingSystemType() == OsCheck.OSType.Windows) {
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
        for (Size res : supportedResolutions) {
            if (res.width == width && res.height == height) {
                resolutionSupported = true;
            }
        }

        if (!resolutionSupported) {
            String supportedResolutionsListString = "";

            for (Size res : supportedResolutions) {
                supportedResolutionsListString = String.format("%1$s%2$s%3$s", supportedResolutionsListString, res.width + "x" + res.height, System.lineSeparator());
            }

            log.info(String.format("The Resolution %1$sx%2$s is not in the list of supported resolutions.%3$sSupported Resolutions:%3$s%4$s",
                    width, height, System.lineSeparator(), supportedResolutionsListString));
        }
    }
}
