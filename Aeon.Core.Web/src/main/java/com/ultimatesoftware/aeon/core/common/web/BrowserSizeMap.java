package com.ultimatesoftware.aeon.core.common.web;

import java.awt.*;
import java.util.EnumMap;
import java.util.HashMap;

/**
 * Class to map the browser sizes.
 */
public final class BrowserSizeMap {

    private static final EnumMap<BrowserSize, Dimension> browserSizes = new EnumMap<>(BrowserSize.class);
    private static final HashMap<Dimension, BrowserSize> sizes = new HashMap<>();

    static {
        browserSizes.put(BrowserSize.MAXIMIZED, new Dimension(0, 0));
        browserSizes.put(BrowserSize.MOBILE_PORTRAIT, new Dimension(320, 480));
        browserSizes.put(BrowserSize.MOBILE_LANDSCAPE, new Dimension(480, 320));
        browserSizes.put(BrowserSize.SMALL_TABLET_PORTRAIT, new Dimension(600, 800));
        browserSizes.put(BrowserSize.SMALL_TABLET_LANDSCAPE, new Dimension(800, 600));
        browserSizes.put(BrowserSize.TABLET_PORTRAIT, new Dimension(768, 1024));
        browserSizes.put(BrowserSize.TABLET_LANDSCAPE, new Dimension(1024, 768));
        browserSizes.put(BrowserSize.FULL_HD, new Dimension(1920, 1080));
    }

    static {
        sizes.put(new Dimension(0, 0), BrowserSize.MAXIMIZED);
        sizes.put(new Dimension(320, 480), BrowserSize.MOBILE_PORTRAIT);
        sizes.put(new Dimension(480, 320), BrowserSize.MOBILE_LANDSCAPE);
        sizes.put(new Dimension(600, 800), BrowserSize.SMALL_TABLET_PORTRAIT);
        sizes.put(new Dimension(800, 600), BrowserSize.SMALL_TABLET_LANDSCAPE);
        sizes.put(new Dimension(768, 1024), BrowserSize.TABLET_PORTRAIT);
        sizes.put(new Dimension(1024, 768), BrowserSize.TABLET_LANDSCAPE);
        sizes.put(new Dimension(1920, 1080), BrowserSize.FULL_HD);
    }

    /**
     * A private constructor to hide the implicit public constructor.
     */
    private BrowserSizeMap() {
        throw new IllegalStateException("Incorrect initialization of the BrowserSizeMap object!");
        //It is not possible to call this constructor, anyway.
    }

    /**
     * Boolean function that returns a truth value if the size is in the instance's sizes.
     *
     * @param size size input to test.
     * @return true or false if the size if contained in the current browser size.
     */
    public static boolean contains(Dimension size) {
        return sizes.containsKey(size);
    }

    /**
     * Boolean function that returns a value based on the BrowserSize.
     *
     * @param browserSize browser size input to test.
     * @return true or false if the size if contained in the current browser size.
     */
    public static boolean contains(BrowserSize browserSize) {
        return browserSizes.containsKey(browserSize);
    }

    /**
     * Given a Size, the function returns the sizes of the browser map.
     *
     * @param size the input size to get.
     * @return the BrowserSize of the map.
     */
    public static BrowserSize map(Dimension size) {
        return sizes.get(size);
    }

    /**
     * Function takes a Browser Size and returns the {@link Dimension} of a browser.
     *
     * @param browserSize the input browserSize.
     * @return the Size of the input.
     */
    public static Dimension map(BrowserSize browserSize) {
        return browserSizes.get(browserSize);
    }

    /**
     * Function takes a Browser Size and returns the {@link Dimension} of a browser.
     *
     * @param browserSize the input browserSize.
     * @return the Size of the input.
     */
    public static Dimension map(String browserSize) {
        return browserSizes.get(Enum.valueOf(BrowserSize.class, browserSize));
    }
}
