package visual.model;

import lombok.Builder;
import lombok.Data;

import java.util.Optional;

/**
 * mill
 * Created on 27.05.17.
 */

@Data
@Builder
public class Point {
    private int x;
    private int y;

    private Optional<Circle> circle;
}
