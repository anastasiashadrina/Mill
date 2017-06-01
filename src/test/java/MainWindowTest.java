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


    @Test
    public void inGameCircles() throws Exception {
        final List<Circle> circleList = createCircleList();

        assertThat(
                MainWindow.inGameCircles(circleList)
        )
                .containsOnly(circleList.get(6),circleList.get(7));
    }

    @Test
    public void notInGameCircles() throws Exception {
        final List<Circle> circleList = createCircleList();

        assertThat(
                MainWindow.notInGameCircles(circleList)
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
                MainWindow.isTriple(circleList)
        )
                .isFalse();

    }

    @Test
    public void isTripleTrue() throws Exception {
        final List<Circle> circleList = tripleCircleList();

        assertThat(
                MainWindow.isTriple(circleList)
        )
                .isTrue();

    }

    @Test
    public void checkXTrue() throws Exception {
        final List<Circle> circles = checkXTrueCircleList();

        assertThat(
                MainWindow.checkX(circles.get(0), circles.get(1),circles.get(2))
        )
                .isTrue();
    }

    @Test
    public void checkYTrue() throws Exception {
        final List<Circle> circles = checkYTrueCircleList();

        assertThat(
                MainWindow.checkY(circles.get(0), circles.get(1),circles.get(2))
        )
                .isTrue();
    }

    @Test
    public void checkXFalse() throws Exception {
        final List<Circle> circles = checkXFalseCircleList();

        assertThat(
                MainWindow.checkX(circles.get(0), circles.get(1),circles.get(2))
        )
                .isFalse();
    }

    @Test
    public void checkYFalse() throws Exception {
        final List<Circle> circles = checkYFalseCircleList();

        assertThat(
                MainWindow.checkY(circles.get(0), circles.get(1),circles.get(2))
        )
                .isFalse();
    }

    private static List<Circle> createCircleList() {
        return Arrays.asList(
                Circle.builder().x(100).y(100).rad(50).color(Color.WHITE).dragged(false).inGame(false).inTreat(false).inTriple(false).build(),
                Circle.builder().x(100).y(100).rad(50).color(Color.WHITE).dragged(false).inGame(false).inTreat(false).inTriple(false).build(),
                Circle.builder().x(100).y(100).rad(50).color(Color.WHITE).dragged(false).inGame(false).inTreat(false).inTriple(false).build(),
                Circle.builder().x(100).y(100).rad(50).color(Color.WHITE).dragged(false).inGame(false).inTreat(false).inTriple(false).build(),
                Circle.builder().x(100).y(100).rad(50).color(Color.WHITE).dragged(false).inGame(false).inTreat(false).inTriple(false).build(),
                Circle.builder().x(100).y(100).rad(50).color(Color.WHITE).dragged(false).inGame(false).inTreat(false).inTriple(false).build(),
                Circle.builder().x(100).y(100).rad(50).color(Color.WHITE).dragged(false).inGame(true).inTreat(false).inTriple(false).build(),
                Circle.builder().x(100).y(100).rad(50).color(Color.WHITE).dragged(false).inGame(true).inTreat(false).inTriple(false).build()
        );
    }

    private static List<Circle> noTripleCircleList() {
        return Arrays.asList(
                Circle.builder().x(100).y(100).rad(50).color(Color.WHITE).dragged(false).inGame(false).inTreat(false).inTriple(false).build(),
                Circle.builder().x(100).y(100).rad(50).color(Color.WHITE).dragged(false).inGame(false).inTreat(false).inTriple(false).build(),
                Circle.builder().x(100).y(100).rad(50).color(Color.WHITE).dragged(false).inGame(false).inTreat(false).inTriple(false).build(),
                Circle.builder().x(100).y(100).rad(50).color(Color.WHITE).dragged(false).inGame(false).inTreat(false).inTriple(false).build(),
                Circle.builder().x(100).y(100).rad(50).color(Color.WHITE).dragged(false).inGame(false).inTreat(false).inTriple(false).build(),
                Circle.builder().x(100).y(100).rad(50).color(Color.WHITE).dragged(false).inGame(true).inTreat(false).inTriple(false).build()
                );
    }

    private static List<Circle> tripleCircleList() {
        return Arrays.asList(
                Circle.builder().x(100).y(100).rad(50).color(Color.WHITE).dragged(false).inGame(true).inTreat(false).inTriple(false).build(),
                Circle.builder().x(100).y(150).rad(50).color(Color.WHITE).dragged(false).inGame(true).inTreat(false).inTriple(false).build(),
                Circle.builder().x(100).y(200).rad(50).color(Color.WHITE).dragged(false).inGame(true).inTreat(false).inTriple(false).build()
                );
    }

    private static List<Circle> checkXTrueCircleList() {
        return Arrays.asList(
                Circle.builder().x(100).y(100).rad(50).color(Color.WHITE).dragged(false).inGame(true).inTreat(false).inTriple(false).build(),
                Circle.builder().x(100).y(150).rad(50).color(Color.WHITE).dragged(false).inGame(true).inTreat(false).inTriple(false).build(),
                Circle.builder().x(100).y(200).rad(50).color(Color.WHITE).dragged(false).inGame(true).inTreat(false).inTriple(false).build()
        );
    }

    private static List<Circle> checkXFalseCircleList() {
        return Arrays.asList(
                Circle.builder().x(100).y(100).rad(50).color(Color.WHITE).dragged(false).inGame(true).inTreat(false).inTriple(false).build(),
                Circle.builder().x(120).y(200).rad(50).color(Color.WHITE).dragged(false).inGame(true).inTreat(false).inTriple(false).build(),
                Circle.builder().x(100).y(250).rad(50).color(Color.WHITE).dragged(false).inGame(true).inTreat(false).inTriple(false).build()
        );
    }

    private static List<Circle> checkYTrueCircleList() {
        return Arrays.asList(
                Circle.builder().x(100).y(100).rad(50).color(Color.WHITE).dragged(false).inGame(true).inTreat(false).inTriple(false).build(),
                Circle.builder().x(150).y(100).rad(50).color(Color.WHITE).dragged(false).inGame(true).inTreat(false).inTriple(false).build(),
                Circle.builder().x(200).y(100).rad(50).color(Color.WHITE).dragged(false).inGame(true).inTreat(false).inTriple(false).build()
        );
    }

    private static List<Circle> checkYFalseCircleList() {
        return Arrays.asList(
                Circle.builder().x(100).y(100).rad(50).color(Color.WHITE).dragged(false).inGame(true).inTreat(false).inTriple(false).build(),
                Circle.builder().x(100).y(150).rad(50).color(Color.WHITE).dragged(false).inGame(true).inTreat(false).inTriple(false).build(),
                Circle.builder().x(100).y(200).rad(50).color(Color.WHITE).dragged(false).inGame(true).inTreat(false).inTriple(false).build()
        );
    }

}