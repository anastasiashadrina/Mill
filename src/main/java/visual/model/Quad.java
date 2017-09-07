package visual.model;

/**
 * mill
 * Created on 27.05.17.
 */

public class Quad {
    private final int id;
    private final int x;
    private final int y;
    private final int size;

    public int getYCenter() {
        return y + size/2;
    }

    public int getXCenter() {
        return x + size/2;
    }

    public Quad(final int id, final int x, final int y, final int size) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.size = size;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSize() {
        return size;
    }

    public int getId() { return id; }

}
