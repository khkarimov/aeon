package framework_interaction;

/**
 * Created by DionnyS on 3/31/2016.
 */
public class Point {
    public int x;
    public int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

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

    public void move(int newX, int newY) {
        x = newX;
        y = newY;
    }

    @Override
    public String toString() {
        return String.format("(%d, %d)", x, y);
    }
}
