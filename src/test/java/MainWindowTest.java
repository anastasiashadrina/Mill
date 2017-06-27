import org.junit.Test;
import visual.model.Circle;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * mill
 * Created on 01.06.17.
 */
public class MainWindowTest {
    private static List<Circle> createCircleList() {
        return Arrays.asList(
                new Circle(100, 100, 50, Color.WHITE, false, false, false, false),
                new Circle(100, 100, 50, Color.WHITE, false, false, false, false),
                new Circle(100, 100, 50, Color.WHITE, false, false, false, false),
                new Circle(100, 100, 50, Color.WHITE, false, false, false, false),
                new Circle(100, 100, 50, Color.WHITE, false, false, false, false),
                new Circle(100, 100, 50, Color.WHITE, false, false, false, false),
                new Circle(100, 100, 50, Color.WHITE, false, true, false, false),
                new Circle(100, 100, 50, Color.WHITE, false, true, false, false)
        );
    }

    private static List<Circle> noTripleCircleList() {
        return Arrays.asList(
                new Circle(100, 100, 50, Color.WHITE, false, false, false, false),
                new Circle(100, 100, 50, Color.WHITE, false, false, false, false),
                new Circle(100, 100, 50, Color.WHITE, false, false, false, false),
                new Circle(100, 100, 50, Color.WHITE, false, false, false, false),
                new Circle(100, 100, 50, Color.WHITE, false, false, false, false),
                new Circle(100, 100, 50, Color.WHITE, false, true, false, false)
        );
    }

    private static List<Circle> tripleCircleList() {
        return Arrays.asList(
                new Circle(100, 100, 50, Color.WHITE, false, true, false, false),
                new Circle(100, 150, 50, Color.WHITE, false, true, false, false),
                new Circle(100, 200, 50, Color.WHITE, false, true, false, false)
        );
    }

    private static List<Circle> checkXTrueCircleList() {
        return Arrays.asList(
                new Circle(100, 100, 50, Color.WHITE, false, true, false, false),
                new Circle(100, 150, 50, Color.WHITE, false, true, false, false),
                new Circle(100, 200, 50, Color.WHITE, false, true, false, false)
        );
    }

    private static List<Circle> checkXFalseCircleList() {
        return Arrays.asList(
                new Circle(100, 100, 50, Color.WHITE, false, true, false, false),
                new Circle(120, 200, 50, Color.WHITE, false, true, false, false),
                new Circle(100, 250, 50, Color.WHITE, false, true, false, false)
        );
    }

    private static List<Circle> checkYTrueCircleList() {
        return Arrays.asList(
                new Circle(100, 100, 50, Color.WHITE, false, true, false, false),
                new Circle(150, 100, 50, Color.WHITE, false, true, false, false),
                new Circle(200, 100, 50, Color.WHITE, false, true, false, false)
        );
    }

    private static List<Circle> checkYFalseCircleList() {
        return Arrays.asList(
                new Circle(100, 100, 50, Color.WHITE, false, true, false, false),
                new Circle(100, 150, 50, Color.WHITE, false, true, false, false),
                new Circle(100, 200, 50, Color.WHITE, false, true, false, false)
        );
    }

    @Test
    public void inGameCircles() throws Exception {
        final List<Circle> circleList = createCircleList();

        assertThat(
                MainWindowUtils.inGameCircles(circleList)
        )
                .containsOnly(circleList.get(6), circleList.get(7));
    }

    @Test
    public void notInGameCircles() throws Exception {
        final List<Circle> circleList = createCircleList();

        assertThat(
                MainWindowUtils.notInGameCircles(circleList)
        )
                .containsOnly(
                        circleList.get(0),
                        circleList.get(1),
                        circleList.get(2),
                        circleList.get(3),
                        circleList.get(4),
                        circleList.get(5)
                );
    }

    @Test
    public void isTripleFalse() throws Exception {
        final List<Circle> circleList = noTripleCircleList();

        assertThat(
                MainWindowUtils.isTriple(circleList)
        )
                .isFalse();

    }

    @Test
    public void isTripleTrue() throws Exception {
        final List<Circle> circleList = tripleCircleList();

        assertThat(
                MainWindowUtils.isTriple(circleList)
        )
                .isTrue();

    }

    @Test
    public void checkXTrue() throws Exception {
        final List<Circle> circles = checkXTrueCircleList();

        assertThat(
                MainWindowUtils.checkX(circles.get(0), circles.get(1), circles.get(2))
        )
                .isTrue();
    }

    @Test
    public void checkYTrue() throws Exception {
        final List<Circle> circles = checkYTrueCircleList();

        assertThat(
                MainWindowUtils.checkY(circles.get(0), circles.get(1), circles.get(2))
        )
                .isTrue();
    }

    @Test
    public void checkXFalse() throws Exception {
        final List<Circle> circles = checkXFalseCircleList();

        assertThat(
                MainWindowUtils.checkX(circles.get(0), circles.get(1), circles.get(2))
        )
                .isFalse();
    }

    @Test
    public void checkYFalse() throws Exception {
        final List<Circle> circles = checkYFalseCircleList();

        assertThat(
                MainWindowUtils.checkY(circles.get(0), circles.get(1), circles.get(2))
        )
                .isFalse();
    }

}