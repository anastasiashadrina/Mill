package visual.model;

import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * mill
 * Created on 01.06.17.
 */
public class PointTest {
    @Test
    public void test() {
        final Point point = createPoint();

        assertThat(point)
                .hasFieldOrPropertyWithValue("x",100)
                .hasFieldOrPropertyWithValue("y",50);
    }

    @Test
    public void testSetter() {
        final Point point = createPoint();

        point.setCircle(Optional.empty());

        assertThat(point.getCircle())
                .isEmpty();
    }

    @Test
    public void testNotNullCircle() {
        final Point point = createPoint();

        point.setCircle(Optional.of(
                Circle.builder().build()
        ));

        assertThat(point.getCircle())
                .isNotNull();
    }

    public static Point createPoint() {
        return Point.builder()
                .x(100)
                .y(50)
                .build();
    }
}