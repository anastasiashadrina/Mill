package model;

import org.junit.Test;

import java.awt.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * mill
 * Created on 01.06.17.
 */
public class ChipTest {
    @Test
    public void testConstruct() {
        Chip chip = new Chip(Color.WHITE);

        assertThat(chip.getColor())
                .isEqualToComparingFieldByField(Color.WHITE);
    }

    @Test
    public void testSetter() {
        Chip chip = new Chip(Color.WHITE);

        chip.setColor(Color.BLACK);

        assertThat(chip.getColor())
                .isEqualToComparingFieldByField(Color.BLACK);
    }

}