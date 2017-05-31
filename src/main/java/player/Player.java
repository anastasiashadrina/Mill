package player;

import model.Chip;
import model.MillField;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * mill
 * Created on 26.05.17.
 */

public class Player {
    private final Color color;
    private List<Chip> chips = new ArrayList<>();
    private final MillField millField;

    public Player(Color color, MillField millField) {
        this.color = color;
        this.millField = millField;
    }

    public Chip removeChip() {
        return chips.remove(chips.size()-1);
    }

    public void addChip() {
        chips.add(new Chip(color));
    }
}
