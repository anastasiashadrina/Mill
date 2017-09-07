package visual.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * mill
 * Created on 27.05.17.
 */

public class Point {
    private final int x;
    private final int y;
    private List<Point> horiz = new ArrayList<>();
    private List<Point> vert = new ArrayList<>();

    private Optional<Circle> circle;

    public Point(final int x, final int y, final Optional<Circle> circle) {
        this.x = x;
        this.y = y;
        this.circle = circle;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Optional<Circle> getCircle() {
        return circle;
    }

    public void setCircle(final Optional<Circle> circle) {
        this.circle = circle;
    }

    public List<Point> getHoriz() { return horiz; }

    public List<Point> getVert() { return vert; }
}
