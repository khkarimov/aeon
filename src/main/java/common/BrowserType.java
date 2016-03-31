package common;

/**
 * The browser on which to automate tests.
 */
public enum BrowserType {
    /**
     * Microsoft Internet Explorer.
     */
    InternetExplorer,

    /**
     * Google Chrome.
     */
    Chrome,

    /**
     * Mozilla Firefox.
     */
    Firefox,

    /**
     * Safari Apple iOS.
     */
    SafariOniOS,

    /**
     * Chrome Android.
     */
    ChromeOnAndroid;

    public int getValue() {
        return this.ordinal();
    }

    public static BrowserType forValue(int value) {
        return values()[value];
    }
}