package visual.model;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * mill
 * Created on 01.06.17.
 */
public class QuadTest {

    @Test
    public void testCenterX() {
        final Quad quad = createQuad();

        assertThat(quad.getXCenter())
                .isEqualTo(150);
    }

    @Test
    public void testCenterY() {
        final Quad quad = createQuad();

        assertThat(quad.getYCenter())
                .isEqualTo(150);
    }

    public static Quad createQuad() {
        return Quad.builder()
                .x(100)
                .y(100)
                .size(100)
                .build();
    }
}