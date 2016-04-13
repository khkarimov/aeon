package echo.core.common;// <summary>

/**
 * Class to easily manage the return value from <see cref="GetClientRectsCommand"/>. It also provides the script for that command.
 */
public class ClientRects {
    private int top;
    private int left;
    private int bottom;
    private int right;

    /**
     * Initializes a new instance of the <see cref="ClientRects"/> class.
     *
     * @param clientRects ClientRects object.
     */
    public ClientRects(ClientRects clientRects) {
        this.top = clientRects.getTop();
    }

    /**
     * Initializes a new instance of the <see cref="ClientRects"/> class.
     *
     * @param top    TODO: add comment.
     * @param bottom TODO: add comment.
     * @param left   TODO: add comment.
     * @param right  TODO: add comment.
     */
    public ClientRects(int top, int bottom, int left, int right) {
        setTop(top);
        setBottom(bottom);
        setLeft(left);
        setRight(right);
    }

    /**
     * Gets the location of the top edge of the element relative to the document. In pixels.
     */
    public final int getTop() {
        return top;
    }

    private void setTop(int value) {
        top = value;
    }

    /**
     * Gets the location of the left edge of the element relative to the document. In pixels.
     */
    public final int getLeft() {
        return left;
    }

    private void setLeft(int value) {
        left = value;
    }

    /**
     * Gets the location of the bottom edge of the element relative to the document. In pixels.
     */
    public final int getBottom() {
        return bottom;
    }

    private void setBottom(int value) {
        bottom = value;
    }

    /**
     * Gets the location of the right edge of the element relative to the document. In pixels.
     */
    public final int getRight() {
        return right;
    }

    private void setRight(int value) {
        right = value;
    }

    /**
     * Gets the width of the element. In pixels.
     */
    public final int getWidth() {
        return getRight() - getLeft();
    }

    /**
     * Gets the height of the element. In pixels.
     */
    public final int getHeight() {
        return getBottom() - getTop();
    }

    /**
     * Gets the horizontal center of this element relative to the document. left + (Width / 2).
     */
    public final int getHorizontalCenter() {
        return getLeft() + (getWidth() / 2);
    }

    /**
     * Gets the vertical center of this element relative to the document. top + (Height / 2).
     */
    public final int getVerticalCenter() {
        return getTop() + (getHeight() / 2);
    }

    /**
     * Gets the horizontal middle relative to the element (origin at the element's top-left corner). (Width / 2).
     */
    public final int getHorizontalMiddle() {
        return getWidth() / 2;
    }

    /**
     * Gets the vertical middle relative to the element (origin at the element's top-left corner). (Height / 2).
     */
    public final int getVerticalMiddle() {
        return getHeight() / 2;
    }
}
