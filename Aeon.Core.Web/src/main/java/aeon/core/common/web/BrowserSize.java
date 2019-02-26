package aeon.core.common.web;

/**
 * The browser size on which to automate tests.
 */
public enum BrowserSize {
    /**
     * Default value.
     */
    MAXIMIZED,

    /**
     * Mobile [Portrait]: 320x480.
     */
    MOBILE_PORTRAIT,

    /**
     * Mobile [Landscape]: 480x320.
     */
    MOBILE_LANDSCAPE,

    /**
     * Mobile [Portrait]: 600x800.
     */
    SMALL_TABLET_PORTRAIT,

    /**
     * Mobile [Portrait]: 800x600.
     */
    SMALL_TABLET_LANDSCAPE,

    /**
     * Mobile [Portrait]: 768x1024.
     */
    TABLET_PORTRAIT,

    /**
     * Mobile [Portrait]: 1024x768.
     */
    TABLET_LANDSCAPE,

    /**
     * Desktop [Full HD]: 1920x1080.
     */
    FULL_HD
}
