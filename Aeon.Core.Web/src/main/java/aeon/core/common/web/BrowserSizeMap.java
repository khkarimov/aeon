package aeon.core.common.web;

import java.awt.Dimension;
import java.util.HashMap;

/**
 * Class to map the browser sizes.
 */
public final class BrowserSizeMap {

    private static final HashMap<BrowserSize, Dimension> browserSizes = new HashMap<>();
    private static final HashMap<Dimension, BrowserSize> sizes = new HashMap<>();

    static {
        browserSizes.put(BrowserSize.Maximized, new Dimension(0, 0));
        browserSizes.put(BrowserSize.MobilePortrait, new Dimension(320, 480));
        browserSizes.put(BrowserSize.MobileLandscape, new Dimension(480, 320));
        browserSizes.put(BrowserSize.SmallTabletPortrait, new Dimension(600, 800));
        browserSizes.put(BrowserSize.SmallTabletLandscape, new Dimension(800, 600));
        browserSizes.put(BrowserSize.TabletPortrait, new Dimension(768, 1024));
        browserSizes.put(BrowserSize.TabletLandscape, new Dimension(1024, 768));
        browserSizes.put(BrowserSize.FullHD, new Dimension(1920, 1080));
    }

    static {
        sizes.put(new Dimension(0, 0), BrowserSize.Maximized);
        sizes.put(new Dimension(320, 480), BrowserSize.MobilePortrait);
        sizes.put(new Dimension(480, 320), BrowserSize.MobileLandscape);
        sizes.put(new Dimension(600, 800), BrowserSize.SmallTabletPortrait);
        sizes.put(new Dimension(800, 600), BrowserSize.SmallTabletLandscape);
        sizes.put(new Dimension(768, 1024), BrowserSize.TabletPortrait);
        sizes.put(new Dimension(1024, 768), BrowserSize.TabletLandscape);
        sizes.put(new Dimension(1920, 1080), BrowserSize.FullHD);
    }

    /**
     *  Boolean function that returns a truth value if the size is in the instance's sizes.
     * @param size size input to test.
     * @return true or false if the size if contained in the current browser size.
     */
    public static boolean contains(Dimension size) {
        return sizes.containsKey(size);
    }

    /**
     *  Boolean function that returns a value based on the BrowserSize.
     * @param browserSize browser size input to test.
     * @return true or false if the size if contained in the current browser size.
     */
    public static boolean contains(BrowserSize browserSize) {
        return browserSizes.containsKey(browserSize);
    }

    /**
     * Given a Size, the function returns the sizes of the browser map.
     * @param size the input size to get.
     * @return the BrowserSize of the map.
     */
    public static BrowserSize map(Dimension size) {
        return sizes.get(size);
    }

    /**
     * Function takes a Browser Size and returns the {@link Dimension} of a browser.
     * @param browserSize the input browserSize.
     * @return the Size of the input.
     */
    public static Dimension map(BrowserSize browserSize) {
        return browserSizes.get(browserSize);
    }
}
