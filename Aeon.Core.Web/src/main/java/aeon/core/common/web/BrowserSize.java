package aeon.core.common.web;

/**
 * The browser size on which to automate tests.
 */
public enum BrowserSize {
    /**
     * Default value.
     */
    Maximized(new Dimension(0, 0)),

    /**
     * Mobile [Portrait]: 320x480.
     */
    MobilePortrait(new Dimension(320, 480)),

    /**
     * Mobile [Landscape]: 480x320.
     */
    MobileLandscape(new Dimension(480, 320)),

    /**
     * Mobile [Portrait]: 600x800.
     */
    SmallTabletPortrait(new Dimension(600, 800)),

    /**
     * Mobile [Portrait]: 800x600.
     */
    SmallTabletLandscape(new Dimension(800, 600)),

    /**
     * Mobile [Portrait]: 768x1024.
     */
    TabletPortrait(new Dimension(768, 1024)),

    /**
     * Mobile [Portrait]: 1024x768.
     */
    TabletLandscape(new Dimension(1024, 768)),

    /**
     * Desktop [Full HD]: 1920x1080.
     */
    FullHD(new Dimension(1920, 1080));

    public Dimension dimension;

    BrowserSize(Dimension dimension) {
        this.dimension = dimension;
    }

    /**
     * Dimensions class to be used for the browser size.
     * <p>
     */
    public static class Dimension {
        public int width;
        public int height;

        /**
         * Constructor for Dimension class.
         * @param width width of window resolution
         * @param height heigh of window resolution
         */
        public Dimension(int width, int height) {
            this.width = width;
            this.height = height;
        }
    }
}
