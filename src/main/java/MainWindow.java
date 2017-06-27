import visual.model.Circle;
import visual.model.Point;
import visual.model.Quad;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.awt.Color.BLACK;
import static java.awt.Color.RED;
import static model.Constants.*;

/**
 * mill
 * Created on 27.05.17.
 */

class MainWindow extends JFrame {
    private static final Random random = new Random();

    private static final int WIDTH = 1024;
    private static final int HEIGHT = 600;
    private final List<Circle> whiteCircles;
    private final List<Circle> blackCircles;
    private final List<Circle> circles;
    private final List<Quad> quads;
    private final List<Point> centers;

    MainWindow() {
        whiteCircles = new ArrayList<>();

        for (int i = 0; i < N * N; ++i) {
            whiteCircles.add(
                    new Circle(i * 40 + 150, HEIGHT / 12, AMOUNT_OF_PARTS, Color.WHITE)
            );
        }

        blackCircles = new ArrayList<>();

        for (int i = 0; i < N * N; ++i) {
            blackCircles.add(
                    new Circle(i * 40 + 150, HEIGHT * 9 / AMOUNT_OF_PARTS + AMOUNT_OF_PARTS, AMOUNT_OF_PARTS, Color.BLACK)
            );
        }

        circles = Stream.concat(whiteCircles.stream(), blackCircles.stream()).collect(Collectors.toList());

        quads = new ArrayList<>();

        for (int i = 0; i < N; ++i) {
            quads.add(
                    new Quad(300 + i * DEFAULT_DISTANCE, 120 + i * DEFAULT_DISTANCE, 100 * (N - i))
            );
        }

        centers = MainWindowUtils.getQuadsCenters(quads);

        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);

        final Listener listener = new Listener(this, whiteCircles, blackCircles, circles, centers);

        addMouseListener(listener);
        addMouseMotionListener(listener);
    }

    void whiteRemoveBlackCircle() {
        final Optional<Circle> any = blackCircles
                .stream()
                .filter(
                        circle -> circle.isInGame() && !circle.isInTreat()
                )
                .findAny();

        if (any.isPresent()) {
            final Circle circle = any.get();

            circle.setInTreat(true);

            circle.setX((int) (WIDTH * 7 / AMOUNT_OF_PARTS + random.nextDouble() * WIDTH / 8));
            circle.setY((int) (HEIGHT / 5 + random.nextDouble() * HEIGHT / 5));
        }
    }

    boolean isAll() {
        return
                whiteCircles.stream().allMatch(Circle::isInGame) &&
                        blackCircles.stream().allMatch(Circle::isInGame);
    }

    void whiteMakeStep() {
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
    }

    @Override
    public void paint(final Graphics g) {
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
                point -> MainWindowDrawer.drawCircleByCenter(g, point.getX(), point.getY(), DOT_SIZE)
        );

        g.setColor(Color.ORANGE);
        g.fillRect(0, 0, WIDTH, HEIGHT / AMOUNT_OF_PARTS);

        g.setColor(Color.ORANGE);
        g.fillRect(0, HEIGHT * 9 / AMOUNT_OF_PARTS, WIDTH, HEIGHT / AMOUNT_OF_PARTS);

        g.setColor(RED);
        g.drawRect(20, HEIGHT * 6 / AMOUNT_OF_PARTS, WIDTH / QUAD_SIZE, HEIGHT / 5);
        g.drawRect(WIDTH * 7 / AMOUNT_OF_PARTS, HEIGHT / 5, WIDTH / QUAD_SIZE, HEIGHT / 5);

        g.setColor(BLACK);
        g.drawString("Поле игрока", MAXIMUM_DISTANCE, HEIGHT * 6 / AMOUNT_OF_PARTS);
        g.drawString("Поле ИИ", WIDTH * 7 / AMOUNT_OF_PARTS, HEIGHT / 5);

        g.setColor(Color.BLACK);
        g.drawString("ИИ", AMOUNT_OF_PARTS, HEIGHT / AMOUNT_OF_PARTS);
        g.drawString("Игрок", AMOUNT_OF_PARTS, 9 * HEIGHT / AMOUNT_OF_PARTS + AMOUNT_OF_PARTS);

        circles.forEach(
                circle -> MainWindowDrawer.drawCircleByCenter(g, circle)
        );
    }
}
