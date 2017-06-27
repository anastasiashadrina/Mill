package model;

import java.awt.*;

/**
 * mill
 * Created on 26.05.17.
 */

class Chip {
    private Color color;

    public Chip(final Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(final Color color) {
        this.color = color;
    }
}
