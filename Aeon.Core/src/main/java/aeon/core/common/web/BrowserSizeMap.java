package aeon.core.common.web;

import com.sun.glass.ui.Size;

import java.util.HashMap;

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

    public static boolean contains(Size size) {
        return sizes.containsKey(size);
    }

    public static boolean contains(BrowserSize browserSize) {
        return browserSizes.containsKey(browserSize);
    }

    public static BrowserSize map(Size size) {
        return sizes.get(size);
    }

    public static Size map(BrowserSize browserSize) {
        return browserSizes.get(browserSize);
    }
}
