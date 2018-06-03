package aeon.core.common;

/**
 * Represents a point on the screen.
 */
public class Point {

    public int x;
    public int y;

    /**
     * Constructor for a new point.
     * @param x the x coordinate.
     * @param y the y coordinate.
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Gets the x coordinate of the point.
     * @return the x coordinate.
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the y coordinate of the point.
     * @return the y coordinate.
     */
    public int getY() {
        return y;
    }

    /**
     * Functions returns a point moved by a specified amount.
     * @param xOffset The x amount to offset.
     * @param yOffset The y ammount to offset.
     * @return the new point.
     */
    public Point moveBy(int xOffset, int yOffset) {
        return new Point(x + xOffset, y + yOffset);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Point)) {
            return false;
        }

        Point other = (Point) o;
        return other.x == x && other.y == y;
    }

    @Override
    public int hashCode() {
        // Assuming x,y rarely exceed 4096 pixels, shifting
        // by 12 should provide a good hash value.
        return x << 12 + y;
    }

    /**
     * Function sets a new x and y to move to.
     * @param newX The new x value.
     * @param newY The new y value.
     */
    public void move(int newX, int newY) {
        x = newX;
        y = newY;
    }

    @Override
    public String toString() {
        return String.format("(%d, %d)", x, y);
    }
}
