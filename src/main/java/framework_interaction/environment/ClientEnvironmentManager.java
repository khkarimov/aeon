package framework_interaction.environment;

import com.sun.glass.ui.Size;
import common.BrowserType;
import common.helpers.OsCheck;
import common.logging.ILog;

import java.awt.*;
import java.util.UUID;

public final class ClientEnvironmentManager {
    private static final Size[] supportedResolutions = {new Size(1024, 768)};

    public static void ManageEnvironment(UUID guid, ILog log, BrowserType browserType, String browserAcceptedLanguageCodes, boolean ensureCleanEnvironment) {
        if (ensureCleanEnvironment) {
            log.Info(guid, "Cleaning Client Environment.");
            EnsureCleanEnvironment(guid, log, browserType);
        }

        verifyScreenResolution(guid, log);

        switch (browserType) {
            case InternetExplorer:
                // TODO: JAVA_CONVERSION
                // ConfigureInternetExplorerSettings(guid, log, browserAcceptedLanguageCodes);
                break;
            case Firefox:
                EnforceDPI(guid, log);
                break;
        }
    }

    private static void EnsureCleanEnvironment(UUID guid, ILog log, BrowserType browserType) {
        switch (browserType) {
            case InternetExplorer:
                log.Info(guid, "Killing Internet Explorer related processes.");
                // TODO: JAVA_CONVERSION
                // KillProcesses(guid, log, new String[]{"iexplore", "IEDriverServer"});
                break;
            case Firefox:
                log.Info(guid, "Killing Firefox related processes.");
                // TODO: JAVA_CONVERSION
                // KillProcesses(guid, log, new String[]{"firefox"});
                break;
            case Chrome:
                log.Info(guid, "Killing Chrome related processes.");
                // TODO: JAVA_CONVERSION
                // KillProcesses(guid, log, new String[]{"chrome", "chromedriver"});
                break;
        }
    }

    private static void EnforceDPI(UUID guid, ILog log) {
        if (OsCheck.getOperatingSystemType() == OsCheck.OSType.Windows) {
            int pixelPerInch = java.awt.Toolkit.getDefaultToolkit().getScreenResolution();
            log.Info(guid, "Checking DPI setting are set to 100%");
            if (pixelPerInch / 96f != 1.00) {
                throw new RuntimeException(String.format(
                        "Environment DPI not set to 100%%.%1$sModify value in \"Control Panel\\Appearance and Personalization\\Display\" to 100%%",
                        System.lineSeparator()));
            }
        }
    }

    private static void verifyScreenResolution(UUID guid, ILog log) {
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

            log.Info(guid, String.format("The Resolution %1$sx%2$s is not in the list of supported resolutions.%3$sSupported Resolutions:%3$s%4$s",
                    width, height, System.lineSeparator(), supportedResolutionsListString));
        }
    }
}
