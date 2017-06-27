package visual.model;

import java.awt.*;

/**
 * mill
 * Created on 27.05.17.
 */
public class Circle {
    private int x;
    private int y;
    private int rad;

    private final Color color;

    private boolean dragged;
    private boolean inGame;
    private boolean inTriple;
    private boolean inTreat;

    public Circle(final int x, final int y, final int rad, final Color color) {
        this.x = x;
        this.y = y;
        this.rad = rad;
        this.color = color;
    }

    public Circle(final Circle circle) {
        x = circle.x;
        y = circle.y;
        rad = circle.rad;
        color = circle.color;
    }

    public Circle(final int x, final int y, final int rad, final Color color, final boolean dragged, final boolean inGame, final boolean inTriple, final boolean inTreat) {
        this.x = x;
        this.y = y;
        this.rad = rad;
        this.color = color;
        this.dragged = dragged;
        this.inGame = inGame;
        this.inTriple = inTriple;
        this.inTreat = inTreat;
    }

    public int getX() {
        return x;
    }

    public void setX(final int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(final int y) {
        this.y = y;
    }

    public int getRad() {
        return rad;
    }

    public void setRad(final int rad) {
        this.rad = rad;
    }

    public Color getColor() {
        return color;
    }

    public boolean isDragged() {
        return dragged;
    }

    public void setDragged(final boolean dragged) {
        this.dragged = dragged;
    }

    public boolean isInGame() {
        return inGame;
    }

    public void setInGame(final boolean inGame) {
        this.inGame = inGame;
    }

    public boolean isInTriple() {
        return inTriple;
    }

    public void setInTriple(final boolean inTriple) {
        this.inTriple = inTriple;
    }

    public boolean isInTreat() {
        return inTreat;
    }

    public void setInTreat(final boolean inTreat) {
        this.inTreat = inTreat;
    }

    public void release() {
        dragged = false;
    }

    public void drag() {
        dragged = true;
    }
}
