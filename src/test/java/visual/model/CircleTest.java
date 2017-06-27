package visual.model;

import org.junit.Test;

import java.awt.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * mill
 * Created on 01.06.17.
 */
public class CircleTest {
    @Test
    public void testBuilder() {
        final Circle circle = createCircle();

        assertThat(circle)
                .hasFieldOrPropertyWithValue("x", 100)
                .hasFieldOrPropertyWithValue("y", 200)
                .hasFieldOrPropertyWithValue("rad", 10)
                .hasFieldOrPropertyWithValue("color", Color.YELLOW);
    }

    @Test
    public void testSetters() {
        final Circle circle = createCircle();

        circle.setInTreat(true);
        circle.setInTriple(true);
        circle.setDragged(true);

        circle.setX(20);
        circle.setY(50);
        circle.setRad(5);

        assertThat(circle.getX()).isEqualTo(20);
        assertThat(circle.getY()).isEqualTo(50);
        assertThat(circle.getRad()).isEqualTo(5);
        assertThat(circle.isDragged()).isTrue();
        assertThat(circle.isInTriple()).isTrue();
        assertThat(circle.isInTreat()).isTrue();
    }

    private static Circle createCircle() {
        return new Circle (100,200,10,Color.YELLOW);
    }
}