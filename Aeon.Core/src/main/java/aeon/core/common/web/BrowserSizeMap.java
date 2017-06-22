package aeon.core.common.web;

import com.sun.glass.ui.Size;

import java.util.HashMap;

/**
 * Class to map the browser sizes.
 */
public final class BrowserSizeMap {

    private static final HashMap<BrowserSize, Size> browserSizes = new HashMap<BrowserSize, Size>();
    private static final HashMap<Size, BrowserSize> sizes = new HashMap<Size, BrowserSize>();

    static {
        browserSizes.put(BrowserSize.Maximized, new Size(0, 0));
        browserSizes.put(BrowserSize.MobilePortrait, new Size(320, 480));
        browserSizes.put(BrowserSize.MobileLandscape, new Size(480, 320));
        browserSizes.put(BrowserSize.SmallTabletPortrait, new Size(600, 800));
        browserSizes.put(BrowserSize.SmallTabletLandscape, new Size(800, 600));
        browserSizes.put(BrowserSize.TabletPortrait, new Size(768, 1024));
        browserSizes.put(BrowserSize.TabletLandscape, new Size(1024, 768));
    }

    static {
        sizes.put(new Size(0, 0), BrowserSize.Maximized);
        sizes.put(new Size(320, 480), BrowserSize.MobilePortrait);
        sizes.put(new Size(480, 320), BrowserSize.MobileLandscape);
        sizes.put(new Size(600, 800), BrowserSize.SmallTabletPortrait);
        sizes.put(new Size(800, 600), BrowserSize.SmallTabletLandscape);
        sizes.put(new Size(768, 1024), BrowserSize.TabletPortrait);
        sizes.put(new Size(1024, 768), BrowserSize.TabletLandscape);
    }

    /**
     *  Boolean function that returns a truth value if the size is in the instance's sizes.
     * @param size size input to test.
     * @return true or false if the size if contained in the current browser size.
     */
    public static boolean contains(Size size) {
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
    public static BrowserSize map(Size size) {
        return sizes.get(size);
    }

    /**
     * Function takes a Browser Size and returns the {@link Size} of a browser.
     * @param browserSize the input browserSize.
     * @return the Size of the input.
     */
    public static Size map(BrowserSize browserSize) {
        return browserSizes.get(browserSize);
    }
}
