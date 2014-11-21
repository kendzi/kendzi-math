package kendzi.math.geometry.polygon;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import javax.vecmath.Point2d;

import org.junit.Test;

public class PolygonUtilTest {

    @Test
    public void makeCounterClockwise() {

        Point2d p1 = point("p1", 0, 0);
        Point2d p2 = point("p2", 1, 0);
        Point2d p3 = point("p3", 1, 1);

        List<Point2d> counterClockwiseInput = Arrays.asList(p1, p2, p3);

        List<Point2d> counterClockwise = PolygonUtil.makeCounterClockwise(counterClockwiseInput);

        assertEquals(counterClockwiseInput, counterClockwise);

        List<Point2d> clockwiseInput = Arrays.asList(p3, p2, p1);

        counterClockwise = PolygonUtil.makeCounterClockwise(clockwiseInput);

        assertEquals(counterClockwiseInput, counterClockwise);

    }

    @Test
    public void isClockwisePolygon() {

        Point2d p1 = point("p1", 0, 0);
        Point2d p2 = point("p2", 1, 0);
        Point2d p3 = point("p3", 1, 1);

        List<Point2d> counterClockwiseInput = Arrays.asList(p1, p2, p3);

        boolean isClockwise = PolygonUtil.isClockwisePolygon(counterClockwiseInput);

        assertFalse(isClockwise);

        List<Point2d> clockwiseInput = Arrays.asList(p3, p2, p1);

        isClockwise = PolygonUtil.isClockwisePolygon(clockwiseInput);

        assertTrue(isClockwise);

    }

    private Point2d point(final String string, int i, int j) {
        return new Point2d(i, j) {
            @Override
            public String toString() {
                return string;
            };
        };
    }
}
