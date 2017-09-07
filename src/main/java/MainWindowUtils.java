import visual.model.Circle;
import visual.model.Point;
import visual.model.Quad;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.Math.abs;
import static model.Constants.DEFAULT_DISTANCE;
import static model.Constants.POSITION_X;
import static model.Constants.POSITION_Y;

/**
 * mill
 * Created on 27.06.17.
 */
class MainWindowUtils {
    static void addQuadCenters(final Quad quad, List<Point> centers) {

        Point p0 = new Point(quad.getX(), (quad.getY()), Optional.empty());
        Point p1 = new Point(quad.getXCenter(), (quad.getY()), Optional.empty());
        Point p2 = new Point(quad.getX() + quad.getSize(), (quad.getY()), Optional.empty());
        Point p3 = new Point(quad.getX() + quad.getSize(), quad.getYCenter(), Optional.empty());
        Point p4 = new Point(quad.getX() + quad.getSize(), (quad.getY() + quad.getSize()), Optional.empty());
        Point p5 = new Point(quad.getXCenter(), (quad.getY() + quad.getSize()), Optional.empty());
        Point p6 = new Point(quad.getX(), (quad.getY() + quad.getSize()), Optional.empty());
        Point p7 = new Point(quad.getX(), quad.getYCenter(), Optional.empty());

        p0.getHoriz().add(p1);
        p0.getHoriz().add(p2);
        p0.getVert().add(p6);
        p0.getVert().add(p7);

        p1.getHoriz().add(p0);
        p1.getHoriz().add(p2);

        p2.getHoriz().add(p1);
        p2.getHoriz().add(p0);
        p2.getVert().add(p3);
        p2.getVert().add(p4);

        p3.getVert().add(p2);
        p3.getVert().add(p4);

        p4.getHoriz().add(p5);
        p4.getHoriz().add(p6);
        p4.getVert().add(p3);
        p4.getVert().add(p2);

        p5.getHoriz().add(p4);
        p5.getHoriz().add(p6);

        p6.getHoriz().add(p5);
        p6.getHoriz().add(p4);
        p6.getVert().add(p7);
        p6.getVert().add(p0);

        p7.getVert().add(p0);
        p7.getVert().add(p6);

        centers.add(p0);
        centers.add(p1);
        centers.add(p2);
        centers.add(p3);
        centers.add(p4);
        centers.add(p5);
        centers.add(p6);
        centers.add(p7);
    }

    static boolean isTriple(final List<Circle> circles) {
        final List<Circle> tempCircles = circles.stream().filter(Circle::isInGame).collect(Collectors.toList());

        for (int i = 0; i < tempCircles.size(); ++i) {
            for (int j = 0; j < tempCircles.size(); ++j) {
                for (int k = 0; k < tempCircles.size(); ++k) {
                    final Circle circle1 = tempCircles.get(i);
                    final Circle circle2 = tempCircles.get(j);
                    final Circle circle3 = tempCircles.get(k);

                    if (circle1.isInTriple() && circle2.isInTriple() && circle3.isInTriple()) {
                        continue;
                    }

                    if (checkX(circle1, circle2, circle3)) {
                        circle1.setInTriple(true);
                        circle2.setInTriple(true);
                        circle3.setInTriple(true);

                        return true;
                    }

                    if (checkY(circle1, circle2, circle3)) {
                        circle1.setInTriple(true);
                        circle2.setInTriple(true);
                        circle3.setInTriple(true);

                        return true;
                    }
                }
            }
        }
        return false;
    }

    static boolean checkX(final Circle circle1, final Circle circle2, final Circle circle3) {
        final boolean b = circle1.getX() == circle2.getX() && circle1.getX() == circle3.getX() &&
                abs(circle1.getY() - circle2.getY()) == abs(circle2.getY() - circle3.getY()) &&
                circle1.getY() != circle2.getY() && circle1.getY() != circle3.getY();

        return b && !(circle1.getX() == POSITION_X && abs(circle1.getY() - circle2.getY()) != DEFAULT_DISTANCE);
    }

    static boolean checkY(final Circle circle1, final Circle circle2, final Circle circle3) {
        final boolean b = circle1.getY() == circle2.getY() && circle1.getY() == circle3.getY() &&
                abs(circle1.getX() - circle2.getX()) == abs(circle2.getX() - circle3.getX()) &&
                circle1.getX() != circle2.getX() && circle1.getX() != circle3.getX();

        return b && !(circle1.getY() == POSITION_Y && abs(circle1.getX() - circle2.getX()) != DEFAULT_DISTANCE);
    }

    static List<Circle> inGameCircles(final List<Circle> circles) {
        return circles.stream().filter(Circle::isInGame).collect(Collectors.toList());
    }

    static List<Circle> notInGameCircles(final List<Circle> circles) {
        return circles.stream().filter(circle -> !circle.isInGame()).collect(Collectors.toList());
    }

    static boolean positionInCircle(final int mouseX, final int mouseY, final Circle circle) {
        final int x = circle.getX();
        final int y = circle.getY();
        final int rad = circle.getRad();

        return (x - mouseX) * (x - mouseX) + (y - mouseY) * (y - mouseY) < rad * rad;
    }
}
