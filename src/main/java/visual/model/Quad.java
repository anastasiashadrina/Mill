package visual.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * mill
 * Created on 27.05.17.
 */

@Data
@Builder
@AllArgsConstructor
public class Quad {
    private int x;
    private int y;
    private int size;

    public int getYCenter() {
        return y + size/2;
    }

    public int getXCenter() {
        return x + size/2;
    }
}
