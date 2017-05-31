import visual.model.Circle;
import visual.model.Point;
import visual.model.Quad;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.awt.Color.BLACK;
import static java.awt.Color.RED;
import static java.lang.Math.abs;
import static model.Constants.N;

/**
 * mill
 * Created on 27.05.17.
 */

public class MainWindow extends JFrame
        implements MouseMotionListener, MouseListener {

    private static final Random random = new Random();
    private static final int DOT_SIZE = 6;

    private static final int WIDTH = 1024;
    private static final int HEIGHT = 600;
    private final List<Circle> whiteCircles;
    private final List<Circle> blackCircles;
    private final List<Circle> circles;
    private final List<Quad> quads;
    private final List<Point> centers;

    private State state;

    private Circle curCircle;

    public MainWindow() {
        state = State.DROP;
        whiteCircles = new ArrayList<>();

        for (int i = 0; i < N * N; ++i) {
            whiteCircles.add(
                    Circle.builder().x(i * 40 + 150).y(10)
                            .rad(10).color(Color.WHITE)
                            .build()
            );
        }

        blackCircles = new ArrayList<>();

        for (int i = 0; i < N * N; ++i) {
            blackCircles.add(
                    Circle.builder().x(i * 40 + 150).y(HEIGHT * 9 / 10 + 10)
                            .rad(10).color(Color.BLACK)
                            .build()
            );
        }

        circles = Stream.concat(whiteCircles.stream(), blackCircles.stream()).collect(Collectors.toList());

        quads = new ArrayList<>();

        for (int i = 0; i < N; ++i) {
            quads.add(
                    Quad.builder().x(300 + i * 50).y(120 + i * 50).size(100 * (N - i)).build()
            );
        }

        centers = getQuadsCenters();

        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);

        addMouseListener(this);
        addMouseMotionListener(this);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        circles.stream()
                .filter(Circle::isDragged)
                .forEach(
                        circle -> {
                            circle.setX(e.getX());
                            circle.setY(e.getY());
                        }
                );

        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (state == State.DROP) {
            final int mouseX = e.getX();
            final int mouseY = e.getY();

            for (Circle circle : notInGameCircles(blackCircles)) {
                final float x = circle.getX();
                final float y = circle.getY();
                final float rad = circle.getRad();

                if ((x - mouseX) * (x - mouseX) + (y - mouseY) * (y - mouseY) < rad * rad) {
                    curCircle = new Circle(circle);
                    circle.drag();
                    break;
                }
            }
        } else {
            final int mouseX = e.getX();
            final int mouseY = e.getY();

            for (Circle circle : inGameCircles(whiteCircles)) {
                final float x = circle.getX();
                final float y = circle.getY();
                final float rad = circle.getRad();

                if ((x - mouseX) * (x - mouseX) + (y - mouseY) * (y - mouseY) < rad * rad) {
                    curCircle = new Circle(circle);
                    circle.drag();
                    break;
                }
            }
        }
    }

    private static List<Circle> inGameCircles(List<Circle> circles) {
        return circles.stream().filter(Circle::isInGame).collect(Collectors.toList());
    }

    private static List<Circle> notInGameCircles(List<Circle> circles) {
        return circles.stream().filter(circle -> !circle.isInGame()).collect(Collectors.toList());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (state == State.DROP) {
            blackCircles.stream()
                    .filter(Circle::isDragged)
                    .forEach(
                            circle -> {
                                if (!isInPosition(circle)) {
                                    circle.setX(curCircle.getX());
                                    circle.setY(curCircle.getY());
                                } else {
                                    circle.setInGame(true);
                                }
                                circle.release();
                            }
                    );

            if (isTriple(whiteCircles)) {
                System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! white");
            }
            if (isTriple(blackCircles)) {
                int n = JOptionPane.showConfirmDialog(
                        this,
                        "Хотите убрать у белых фишку?",
                        "Три в ряд",
                        JOptionPane.YES_NO_OPTION);

                if (n == 0) {
                    state = State.REMOVE_CIRCLE;
                }
            }
        } else if (state == State.REMOVE_CIRCLE) {
            whiteCircles.stream()
                    .filter(Circle::isDragged)
                    .forEach(
                            circle -> {

                                if (isInTreat(circle)){
                                    circle.setInTreat(true);
                                    state = State.DROP;
                                } else {

                                    circle.setX(curCircle.getX());
                                    circle.setY(curCircle.getY());
                                }
                                circle.release();
                            }
                    );
        }

        repaint();
    }

    private boolean isInTreat(Circle circle) {
        return circle.getX() > 20 &&
                circle.getY() > HEIGHT*6/10 &&
                circle.getX() + circle.getRad() < 20 + WIDTH/8 &&
                circle.getY() + circle.getRad() < HEIGHT*6/10 + HEIGHT/5;

    }

    private boolean isInPosition(Circle circle) {
        final Optional<Point> any = centers.stream()
                .filter(
                        center -> abs(circle.getX() - center.getX()) < 20 &&
                                abs(circle.getY() - center.getY()) < 20
                )
                .findAny();

        if (any.isPresent()) {
            final Point point = any.get();
            circle.setX(point.getX());
            circle.setY(point.getY());

            point.setCircle(Optional.of(circle));

            final List<Point> collect = centers.stream()
                    .filter(
                            center -> !center.getCircle().isPresent()
                    )
                    .collect(Collectors.toList());

            final Point center = collect.get(random.nextInt(collect.size()));
            final Optional<Circle> first = whiteCircles.stream()
                    .filter(c -> !c.isInGame())
                    .findFirst();

            if (first.isPresent()) {
                center.setCircle(first);
                final Circle c = first.get();
                c.setInGame(true);
                c.setX(center.getX());
                c.setY(center.getY());
            } else {
                System.out.println("ERROR!!!");
            }

            return true;
        } else {
            return false;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        g.setColor(Color.BLACK);
        quads.forEach(
                quad ->
                        g.drawRect(
                                quad.getX(),
                                quad.getY(),
                                quad.getSize(),
                                quad.getSize()
                        )
        );

        g.setColor(RED);


        centers.forEach(
                point -> drawCircleByCenter(g, point.getX(), point.getY(), DOT_SIZE)
        );

        g.setColor(Color.ORANGE);
        g.fillRect(0, 0, WIDTH, HEIGHT / 10);

        g.setColor(Color.ORANGE);
        g.fillRect(0, HEIGHT * 9 / 10, WIDTH, HEIGHT / 10);

        g.setColor(Color.BLACK);
        g.drawString("ИИ", 10, 10);
        g.drawString("Игрок", 10, 9 * HEIGHT / 10 + 10);

        circles.forEach(
                circle -> drawCircleByCenter(g, circle)
        );

        g.setColor(RED);
        g.drawRect(20,HEIGHT*6/10,WIDTH/8,HEIGHT/5);
        g.drawRect(WIDTH*7/10,HEIGHT/5,WIDTH/8,HEIGHT/5);

        g.setColor(BLACK);
        g.drawString("Поле игрока", 20,HEIGHT*6/10);
        g.drawString("Поле ИИ", WIDTH*7/10,HEIGHT/5);

    }

    private void drawCircleByCenter(Graphics g, Circle circle) {
        final int x = circle.getX();
        final int y = circle.getY();
        final int rad = circle.getRad();
        g.setColor(circle.getColor());
        g.fillOval(
                x - rad, y - rad, 2 * rad, 2 * rad
        );
    }

    private void drawCircleByCenter(Graphics g, int x, int y, int rad) {
        g.fillOval(
                x - rad, y - rad, 2 * rad, 2 * rad
        );
    }

    private List<Point> getQuadsCenters() {
        return quads.stream()
                .flatMap(
                        quad ->
                                Stream.of(
                                        Point.builder().x(quad.getX()).y(quad.getYCenter()).circle(Optional.empty()).build(),
                                        Point.builder().x(quad.getX() + quad.getSize()).y(quad.getYCenter()).circle(Optional.empty()).build(),
                                        Point.builder().x(quad.getXCenter()).y(quad.getY()).circle(Optional.empty()).build(),
                                        Point.builder().x(quad.getXCenter()).y(quad.getY() + quad.getSize()).circle(Optional.empty()).build(),
                                        Point.builder().x(quad.getX()).y(quad.getY()).circle(Optional.empty()).build(),
                                        Point.builder().x(quad.getX()).y(quad.getY() + quad.getSize()).circle(Optional.empty()).build(),
                                        Point.builder().x(quad.getX() + quad.getSize()).y(quad.getY()).circle(Optional.empty()).build(),
                                        Point.builder().x(quad.getX() + quad.getSize()).y(quad.getY() + quad.getSize()).circle(Optional.empty()).build()
                                )
                ).collect(Collectors.toList());
    }

    private static boolean isTriple(List<Circle> circles) {
        List<Circle> tempCircles = circles.stream().filter(Circle::isInGame).collect(Collectors.toList());

        for (int i = 0; i < tempCircles.size(); ++i) {
            for (int j = 0; j < tempCircles.size(); ++j) {
                for (int k = 0; k < tempCircles.size(); ++k) {
                    final Circle circle1 = tempCircles.get(i);
                    final Circle circle2 = tempCircles.get(j);
                    final Circle circle3 = tempCircles.get(k);

                    if (circle1.isInTriple() && circle2.isInTriple() && circle3.isInTriple()) {
                        continue;
                    }

                    if (checkX(circle1,circle2,circle3)) {
                        circle1.setInTriple(true);
                        circle2.setInTriple(true);
                        circle3.setInTriple(true);

                        return true;
                    }

                    if (checkY(circle1,circle2,circle3)) {
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

    private static boolean checkX(Circle circle1, Circle circle2, Circle circle3) {
        final boolean b = circle1.getX() == circle2.getX() && circle1.getX() == circle3.getX() &&
                abs(circle1.getY() - circle2.getY()) == abs(circle2.getY() - circle3.getY()) &&
                circle1.getY() != circle2.getY() && circle1.getY() != circle3.getY();

        if (b) {
            if (circle1.getX() == 450 && abs(circle1.getY() - circle2.getY()) != 50) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    private static boolean checkY(Circle circle1, Circle circle2, Circle circle3) {
        final boolean b = circle1.getY() == circle2.getY() && circle1.getY() == circle3.getY() &&
                abs(circle1.getX() - circle2.getX()) == abs(circle2.getX() - circle3.getX()) &&
                circle1.getX() != circle2.getX() && circle1.getX() != circle3.getX();

        if (b) {
            if (circle1.getY() == 270 && abs(circle1.getX() - circle2.getX()) != 50) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }
}
