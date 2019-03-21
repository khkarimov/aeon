package com.ultimatesoftware.aeon.core.common.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The browser size on which to automate tests.
 */
public enum BrowserSize {
    /**
     * Default value.
     */
    MAXIMIZED("Maximized"),

    /**
     * Mobile [Portrait]: 320x480.
     */
    MOBILE_PORTRAIT("MobilePortrait"),

    /**
     * Mobile [Landscape]: 480x320.
     */
    MOBILE_LANDSCAPE("MobileLandscape"),

    /**
     * Mobile [Portrait]: 600x800.
     */
    SMALL_TABLET_PORTRAIT("SmallTabletPortrait"),

    /**
     * Mobile [Portrait]: 800x600.
     */
    SMALL_TABLET_LANDSCAPE("SmallTabletLandscape"),

    /**
     * Mobile [Portrait]: 768x1024.
     */
    TABLET_PORTRAIT("TabletPortrait"),

    /**
     * Mobile [Portrait]: 1024x768.
     */
    TABLET_LANDSCAPE("TabletLandscape"),

    /**
     * Desktop [Full HD]: 1920x1080.
     */
    FULL_HD("FullHD");

    private static Logger log = LoggerFactory.getLogger(BrowserSize.class);
    private final String key;

    BrowserSize(String key) {
        this.key = key;
    }

    /**
     * Find the BrowserSize enum based on a String.
     *
     * @param size BrowserSize string
     * @return BrowserSize
     */
    public static BrowserSize findBrowserSize(String size) {
        for (BrowserSize browserSize : BrowserSize.values()) {
            if (browserSize.key.equalsIgnoreCase(size)) {
                return browserSize;
            }
        }

        log.warn("Illegal browser size selected. Set to default value: 'FULL_HD'");
        return FULL_HD;
    }
}
