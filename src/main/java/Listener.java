import visual.model.Circle;
import visual.model.Point;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.lang.Math.abs;
import static model.Constants.*;

/**
 * mill
 * Created on 27.06.17.
 */
class Listener
        implements MouseMotionListener, MouseListener {

    private static final int WIDTH = 1024;
    private static final int HEIGHT = 600;
    private final MainWindow mainWindow;
    private final List<Circle> whiteCircles;
    private final List<Circle> blackCircles;
    private final List<Circle> circles;
    private final List<Point> centers;

    private State state;

    private Circle curCircle;

    Listener(
            final MainWindow mainWindow,
            final List<Circle> whiteCircles,
            final List<Circle> blackCircles,
            final List<Circle> circles,
            final List<Point> centers
    ) {
        this.mainWindow = mainWindow;
        this.whiteCircles = whiteCircles;
        this.blackCircles = blackCircles;
        this.circles = circles;
        this.centers = centers;

        state = State.DROP;
    }

    @Override
    public void mouseDragged(final MouseEvent e) {
        circles.stream()
                .filter(Circle::isDragged)
                .forEach(
                        circle -> {
                            circle.setX(e.getX());
                            circle.setY(e.getY());
                        }
                );

        mainWindow.repaint();
    }

    @Override
    public void mouseMoved(final MouseEvent e) {
    }

    @Override
    public void mouseClicked(final MouseEvent e) {
    }

    @Override
    public void mousePressed(final MouseEvent e) {
        if (state == State.DROP) {
            final int mouseX = e.getX();
            final int mouseY = e.getY();

            for (final Circle circle : MainWindowUtils.notInGameCircles(blackCircles)) {
                if (MainWindowUtils.positionInCircle(mouseX, mouseY, circle)) {
                    curCircle = new Circle(circle);
                    circle.drag();
                    break;
                }
            }
        } else if (state == State.GAME) {
            final int mouseX = e.getX();
            final int mouseY = e.getY();

            for (final Circle circle : blackCircles) {
                final float x = circle.getX();
                final float y = circle.getY();
                final float rad = circle.getRad();

                if (MainWindowUtils.positionInCircle(mouseX, mouseY, circle)) {
                    curCircle = new Circle(circle);
                    circle.drag();
                    break;
                }
            }
        } else {
            final int mouseX = e.getX();
            final int mouseY = e.getY();

            for (final Circle circle : MainWindowUtils.inGameCircles(whiteCircles)) {
                final float x = circle.getX();
                final float y = circle.getY();
                final float rad = circle.getRad();

                if (MainWindowUtils.positionInCircle(mouseX, mouseY, circle)) {
                    curCircle = new Circle(circle);
                    circle.drag();
                    break;
                }
            }
        }
    }

    @Override
    public void mouseReleased(final MouseEvent e) {
        if (state == State.DROP) {
            final AtomicBoolean isWhiteMakeStep = new AtomicBoolean(false);
            blackCircles.stream()
                    .filter(Circle::isDragged)
                    .forEach(
                            circle -> {
                                if (!isInPosition(circle)) {
                                    circle.setX(curCircle.getX());
                                    circle.setY(curCircle.getY());
                                } else {
                                    isWhiteMakeStep.set(true);
                                    circle.setInGame(true);
                                }
                                circle.release();
                            }
                    );
            if (MainWindowUtils.isTriple(blackCircles)) {
                final int n = JOptionPane.showConfirmDialog(
                        mainWindow,
                        "Хотите убрать у белых фишку?",
                        "Три в ряд",
                        JOptionPane.YES_NO_OPTION);

                if (n == 0) {
                    state = State.REMOVE_CIRCLE;
                }
            }

            if (state != State.REMOVE_CIRCLE && isWhiteMakeStep.get()) {
                mainWindow.whiteMakeStep();
            }
            if (MainWindowUtils.isTriple(whiteCircles)) {
                mainWindow.whiteRemoveBlackCircle();
            }

            if (mainWindow.isAll()) {
                state = State.GAME;
            }

        } else if (state == State.REMOVE_CIRCLE) {
            whiteCircles.stream()
                    .filter(Circle::isDragged)
                    .forEach(
                            circle -> {
                                if (isInTreat(circle)) {
                                    circle.setInTreat(true);

                                    centers.stream()
                                            .filter(
                                                    center -> center.getCircle().isPresent() && circle == center.getCircle().get()
                                            )
                                            .forEach(
                                                    center -> center.setCircle(Optional.empty())
                                            );

                                    state = State.DROP;
                                    mainWindow.whiteMakeStep();
                                } else {
                                    circle.setX(curCircle.getX());
                                    circle.setY(curCircle.getY());
                                }
                                circle.release();
                            }
                    );
        } else if (state == State.GAME) {
            final AtomicBoolean isWhiteMakeStep = new AtomicBoolean(false);
            blackCircles.stream()
                    .filter(Circle::isDragged)
                    .forEach(
                            c -> {
                                final Optional<Point> old = centers.stream()
                                        .filter(
                                                circle -> circle.getCircle().isPresent() && circle.getCircle().get()
                                                        .equals(c)
                                        )
                                        .findFirst();

                                old.ifPresent(point -> point.setCircle(Optional.empty()));

                                if (!isInPosition(c)) {
                                    c.setX(curCircle.getX());
                                    c.setY(curCircle.getY());
                                } else {
                                    isWhiteMakeStep.set(true);
                                    c.setInGame(true);
                                }
                                c.release();
                            }
                    );

            if (MainWindowUtils.isTriple(blackCircles)) {
                final int n = JOptionPane.showConfirmDialog(
                        mainWindow,
                        "Хотите убрать у белых фишку?",
                        "Три в ряд",
                        JOptionPane.YES_NO_OPTION);

                if (n == 0) {
                    state = State.REMOVE_CIRCLE;
                }
            }

            if (state != State.REMOVE_CIRCLE && isWhiteMakeStep.get()) {
                mainWindow.whiteMakeStep();
            }
            if (MainWindowUtils.isTriple(whiteCircles)) {
                mainWindow.whiteRemoveBlackCircle();
            }
        }

        mainWindow.repaint();
    }

    private boolean isInTreat(final Circle circle) {
        return circle.getX() > MAXIMUM_DISTANCE &&
                circle.getY() > HEIGHT * 6 / AMOUNT_OF_PARTS &&
                circle.getX() + circle.getRad() < MAXIMUM_DISTANCE + WIDTH / QUAD_SIZE &&
                circle.getY() + circle.getRad() < HEIGHT * 6 / AMOUNT_OF_PARTS + HEIGHT / 5;

    }

    private boolean isInPosition(final Circle circle) {
        final Optional<Point> any = centers.stream()
                .filter(
                        center -> !center.getCircle().isPresent()
                )
                .filter(
                        center -> abs(circle.getX() - center.getX()) < MAXIMUM_DISTANCE &&
                                abs(circle.getY() - center.getY()) < MAXIMUM_DISTANCE
                )
                .findAny();

        if (any.isPresent()) {
            final Point point = any.get();
            circle.setX(point.getX());
            circle.setY(point.getY());

            point.setCircle(Optional.of(circle));

            return true;
        } else {
            return false;
        }
    }

    @Override
    public void mouseEntered(final MouseEvent e) {
    }

    @Override
    public void mouseExited(final MouseEvent e) {
    }

    private boolean isCanGameStep(final Circle circle) {
        return abs(curCircle.getX() - circle.getX()) == DEFAULT_DISTANCE &&
                abs(curCircle.getY() - circle.getY()) == DEFAULT_DISTANCE;
    }
}
