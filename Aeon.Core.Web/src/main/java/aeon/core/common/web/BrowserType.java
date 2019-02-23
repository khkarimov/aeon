package aeon.core.common.web;

/**
 * The browser on which to automate tests.
 */
public enum BrowserType {
    /**
     * Microsoft Internet Explorer.
     */
    INTERNET_EXPLORER,
    /**
     * Microsoft Edge.
     */
    EDGE,

    /**
     * Google Chrome.
     */
    CHROME,

    /**
     * Mozilla Firefox.
     */
    FIREFOX,

    /**
     * Safari.
     */
    SAFARI,

    /**
     * Opera.
     */
    OPERA,

    // Mobile Browsers
    /**
     * iOS Safari.
     */
    IOS_SAFARI,

    /**
     * Android Chrome.
     */
    ANDROID_CHROME,

    //Apps
    /**
     * iOS app.
     */
    IOS_HYBRID_APP,

    /**
     * Android app.
     */
    ANDROID_HYBRID_APP
}
