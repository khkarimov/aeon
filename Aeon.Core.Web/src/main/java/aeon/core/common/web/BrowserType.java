package aeon.core.common.web;

/**
 * The browser on which to automate tests.
 */
public enum BrowserType {
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

    /**
     * Safari.
     */
    Safari,

    /**
     * Opera.
     */
    Opera,

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
     * iOS app.
     */
    IOSHybridApp,

    /**
     * Android app.
     */
    AndroidHybridApp;
}
