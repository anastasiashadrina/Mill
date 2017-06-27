import visual.model.Circle;

import java.awt.*;

/**
 * mill
 * Created on 27.06.17.
 */
class MainWindowDrawer {

    static void drawCircleByCenter(final Graphics g, final Circle circle) {
        final int x = circle.getX();
        final int y = circle.getY();
        final int rad = circle.getRad();
        g.setColor(circle.getColor());
        g.fillOval(
                x - rad, y - rad, 2 * rad, 2 * rad
        );
    }

    static void drawCircleByCenter(final Graphics g, final int x, final int y, final int rad) {
        g.fillOval(
                x - rad, y - rad, 2 * rad, 2 * rad
        );
    }
}
