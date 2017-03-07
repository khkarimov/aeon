package echo.core.common.web;

/**
 * The browser size on which to automate tests.
 */
public enum BrowserSize {
    /**
     * Default value.
     */
    Maximized,

    /**
     * Mobile [Portrait]: 320x480.
     */
    MobilePortrait,

    /**
     * Mobile [Landscape]: 480x320.
     */
    MobileLandscape,

    /**
     * Mobile [Portrait]: 600x800.
     */
    SmallTabletPortrait,

    /**
     * Mobile [Portrait]: 800x600.
     */
    SmallTabletLandscape,

    /**
     * Mobile [Portrait]: 768x1024.
     */
    TabletPortrait,

    /**
     * Mobile [Portrait]: 1024x768.
     */
    TabletLandscape;
}
