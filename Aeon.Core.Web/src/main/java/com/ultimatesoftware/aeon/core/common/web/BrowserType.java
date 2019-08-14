package com.ultimatesoftware.aeon.core.common.web;

import com.ultimatesoftware.aeon.core.common.web.interfaces.IBrowserType;

/**
 * The browser on which to automate tests.
 */
public enum BrowserType implements IBrowserType {

    INTERNET_EXPLORER("InternetExplorer"),
    EDGE("Edge"),
    CHROME("Chrome"),
    FIREFOX("Firefox"),
    SAFARI("Safari"),
    OPERA("Opera"),

    // Mobile Browsers
    IOS_SAFARI("IOSSafari"),
    ANDROID_CHROME("AndroidChrome");

    private final String key;

    BrowserType(String key) {
        this.key = key;
    }

    @Override
    public String getKey() {
        return key;
    }
}
