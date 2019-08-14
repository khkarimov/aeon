package com.ultimatesoftware.aeon.extensions.selenium;

/**
 * Utility to retrieve screen information.
 */
public class ScreenHelper {

    static int screenWidth = -1;
    static int screenHeight = -1;

    /**
     * Private constructor to hide the implicit public one.
     */
    private ScreenHelper() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Gets the screen width.
     *
     * @return Screen width.
     */
    static int getScreenWidth() {
        if (screenWidth == -1) {
            screenWidth = (int) java.awt.Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        }
        return screenWidth;
    }

    /**
     * Gets the screen height.
     *
     * @return Screen height.
     */
    static int getScreenHeight() {
        if (screenHeight == -1) {
            screenHeight = (int) java.awt.Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        }
        return screenHeight;
    }
}
