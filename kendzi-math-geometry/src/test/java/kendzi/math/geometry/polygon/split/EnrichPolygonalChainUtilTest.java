package kendzi.math.geometry.polygon.split;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import javax.vecmath.Point2d;

import kendzi.math.geometry.line.LinePoints2d;

import org.junit.Test;

/**
 * Tests for enriching polygonal chain.
 */
public class EnrichPolygonalChainUtilTest {

    @Test
    public void enrichOpenPolygonalChainByLineCrossing1() {

        Point2d p0 = debugPoint(0, 0, -1);
        Point2d p1 = debugPoint(1, 0, 1);
        Point2d s0 = debugPoint("s1", 0, 0);

        List<Point2d> openPolygonalChain = Arrays.asList(p0, p1);

        LinePoints2d splittingLine = new LinePoints2d(debugPoint("l1", 0, 0), debugPoint("l2", 1, 0));

        List<Point2d> enrichedChain = EnrichPolygonalChainUtil.enrichOpenPolygonalChainByLineCrossing(
                openPolygonalChain, splittingLine);

        assertEquals(3, enrichedChain.size());
        assertEquals(p0, enrichedChain.get(0));
        assertEquals(s0, enrichedChain.get(1));
        assertEquals(p1, enrichedChain.get(2));

    }

    @Test
    public void enrichOpenPolygonalChainByLineCrossing2() {

        Point2d p0 = debugPoint(0, 0, -1);
        Point2d p1 = debugPoint(1, 0, 0);
        Point2d p2 = debugPoint(2, 0, 1);

        List<Point2d> openPolygonalChain = Arrays.asList(p0, p1, p2);

        LinePoints2d splittingLine = new LinePoints2d(debugPoint("l1", 0, 0), debugPoint("l2", 1, 0));

        List<Point2d> enrichedChain = EnrichPolygonalChainUtil.enrichOpenPolygonalChainByLineCrossing(
                openPolygonalChain, splittingLine);

        assertEquals(3, enrichedChain.size());
        assertEquals(p0, enrichedChain.get(0));
        assertEquals(p1, enrichedChain.get(1));
        assertEquals(p2, enrichedChain.get(2));

    }

    @Test
    public void enrichClosedPolygonalChainByLineCrossing1() {

        Point2d p0 = debugPoint(0, 0, -1);
        Point2d p1 = debugPoint(1, 0, 1);
        Point2d p2 = debugPoint(2, -1, 1);
        Point2d p3 = debugPoint(3, -1, 0);
        Point2d p4 = debugPoint(4, -1, -1);

        Point2d s0 = debugPoint("s1", 0, 0);

        List<Point2d> openPolygonalChain = Arrays.asList(p0, p1, p2, p3, p4);

        LinePoints2d splittingLine = new LinePoints2d(debugPoint("l1", 0, 0), debugPoint("l2", 1, 0));

        List<Point2d> enrichedChain = EnrichPolygonalChainUtil.enrichClosedPolygonalChainByLineCrossing(
                openPolygonalChain, splittingLine);

        assertEquals(6, enrichedChain.size());
        assertEquals(p0, enrichedChain.get(0));
        assertEquals(s0, enrichedChain.get(1));
        assertEquals(p1, enrichedChain.get(2));
        assertEquals(p2, enrichedChain.get(3));
        assertEquals(p3, enrichedChain.get(4));
        assertEquals(p4, enrichedChain.get(5));

    }

    private Point2d debugPoint(int name, double x, double y) {
        return debugPoint("p" + name, x, y);
    }

    private Point2d debugPoint(final String name, double x, double y) {
        return new Point2d(x, y) {

            private static final long serialVersionUID = 1L;

            @Override
            public String toString() {
                return name;
            }
        };
    }

}
