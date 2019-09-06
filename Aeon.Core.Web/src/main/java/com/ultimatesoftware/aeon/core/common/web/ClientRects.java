package com.ultimatesoftware.aeon.core.common.web;

import com.ultimatesoftware.aeon.core.framework.abstraction.controls.web.WebControl;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.AeonWebDriver;

/**
 * Class to easily manage the return value from {@link AeonWebDriver#getClientRects(WebControl)}. It also provides the script for that command.
 */
public class ClientRects {

    private int top;
    private int left;
    private int bottom;
    private int right;

    /**
     * Initializes a new instance of the {@link ClientRects} class.
     *
     * @param top    Location of top edge in pixels.
     * @param bottom Location of bottom edge in pixels.
     * @param left   Location of left edge in pixels.
     * @param right  Location of right edge in pixels.
     */
    public ClientRects(int top, int bottom, int left, int right) {
        setTop(top);
        setBottom(bottom);
        setLeft(left);
        setRight(right);
    }

    /**
     * Gets the location of the top edge of the element relative to the document. In pixels.
     *
     * @return The top pixel.
     */
    public final int getTop() {
        return top;
    }

    private void setTop(int value) {
        top = value;
    }

    /**
     * Gets the location of the left edge of the element relative to the document. In pixels.
     *
     * @return The location of the left edge in pixels.
     */
    public final int getLeft() {
        return left;
    }

    private void setLeft(int value) {
        left = value;
    }

    /**
     * Gets the location of the bottom edge of the element relative to the document. In pixels.
     *
     * @return The location of the bottom edge in pixels.
     */
    public final int getBottom() {
        return bottom;
    }

    private void setBottom(int value) {
        bottom = value;
    }

    /**
     * Gets the location of the right edge of the element relative to the document. In pixels.
     *
     * @return The location of the right edge in pixels.
     */
    public final int getRight() {
        return right;
    }

    private void setRight(int value) {
        right = value;
    }

    /**
     * Gets the width of the element. In pixels.
     *
     * @return The width of the element in pixels.
     */
    public final int getWidth() {
        return getRight() - getLeft();
    }

    /**
     * Gets the height of the element. In pixels.
     *
     * @return The height of the element in pixels.
     */
    public final int getHeight() {
        return getBottom() - getTop();
    }

    /**
     * Gets the horizontal center of this element relative to the document. left + (Width / 2).
     *
     * @return The horizontal center of an element in pixels relative to the document.
     */
    public final int getHorizontalCenter() {
        return getLeft() + (getWidth() / 2);
    }

    /**
     * Gets the vertical center of this element relative to the document. top + (Height / 2).
     *
     * @return The vertical center of an element in pixels relative to the document.
     */
    public final int getVerticalCenter() {
        return getTop() + (getHeight() / 2);
    }

    /**
     * Gets the horizontal middle relative to the element (origin at the element's top-left corner). (Width / 2).
     *
     * @return The horizontal middle of an element relative to the element.
     */
    public final int getHorizontalMiddle() {
        return getWidth() / 2;
    }

    /**
     * Gets the vertical middle relative to the element (origin at the element's top-left corner). (Height / 2).
     *
     * @return The vertical middle of an element relative to the element.
     */
    public final int getVerticalMiddle() {
        return getHeight() / 2;
    }


    /**
     * Function that returns top, bottom, left and right as as string.
     *
     * @return the string of top, bottom, left and right.
     */
    public String toString() {
        return "Top: " + this.getTop() + " Bottom: " + this.getBottom() + " Left: " + this.getLeft() + " Right: " + this.getRight();
    }
}
