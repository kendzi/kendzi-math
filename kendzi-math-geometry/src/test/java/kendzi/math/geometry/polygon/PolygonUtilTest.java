package kendzi.math.geometry.polygon;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.joml.Vector2d;
import org.joml.Vector2dc;
import org.junit.Test;

public class PolygonUtilTest {

    @Test
    public void makeCounterClockwise() {

        Vector2dc p1 = point("p1", 0, 0);
        Vector2dc p2 = point("p2", 1, 0);
        Vector2dc p3 = point("p3", 1, 1);

        List<Vector2dc> counterClockwiseInput = Arrays.asList(p1, p2, p3);

        List<Vector2dc> counterClockwise = PolygonUtil.makeCounterClockwise(counterClockwiseInput);

        assertEquals(counterClockwiseInput, counterClockwise);

        List<Vector2dc> clockwiseInput = Arrays.asList(p3, p2, p1);

        counterClockwise = PolygonUtil.makeCounterClockwise(clockwiseInput);

        assertEquals(counterClockwiseInput, counterClockwise);

    }

    @Test
    public void isClockwisePolygon() {

        Vector2dc p1 = point("p1", 0, 0);
        Vector2dc p2 = point("p2", 1, 0);
        Vector2dc p3 = point("p3", 1, 1);

        List<Vector2dc> counterClockwiseInput = Arrays.asList(p1, p2, p3);

        boolean isClockwise = PolygonUtil.isClockwisePolygon(counterClockwiseInput);

        assertFalse(isClockwise);

        List<Vector2dc> clockwiseInput = Arrays.asList(p3, p2, p1);

        isClockwise = PolygonUtil.isClockwisePolygon(clockwiseInput);

        assertTrue(isClockwise);

    }

    private Vector2dc point(final String string, int i, int j) {
        return new Vector2d(i, j) {
            @Override
            public String toString() {
                return string;
            };
        };
    }
}
