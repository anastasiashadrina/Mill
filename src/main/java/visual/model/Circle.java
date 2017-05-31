package visual.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.awt.*;

/**
 * mill
 * Created on 27.05.17.
 */

@Data
@Builder
@AllArgsConstructor
public class Circle {
    private int x;
    private int y;
    private int rad;

    private Color color;

    private boolean dragged;
    private boolean inGame;
    private boolean inTriple;
    private boolean inTreat;

    public Circle(Circle circle) {
        x = circle.x;
        y = circle.y;
        rad = circle.rad;
        color = circle.color;
    }

    public void release() {
        dragged = false;
    }

    public void drag() {
        dragged = true;
    }
}
