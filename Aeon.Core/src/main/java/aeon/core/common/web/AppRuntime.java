package aeon.core.common.web;

/**
 * The browser on which to automate tests.
 */
public enum AppRuntime {
    /**
     * Microsoft Internet Explorer.
     */
    InternetExplorer,
    /**
     * Microsoft Edge.
     */
    Edge,

    /**
     * Google Chrome.
     */
    Chrome,

    /**
     * Mozilla Firefox.
     */
    Firefox,

    // Mobile Browsers
    /**
     * iOS Safari.
     */
    IOSSafari,

    /**
     * Android Chrome.
     */
    AndroidChrome,

    //Apps
    /**
     * iOS app
     */
    IOSApp,

    /**
     * Android app
     */
    AndroidApp
}
