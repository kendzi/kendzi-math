package kendzi.math.geometry.polygon.split;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.joml.Vector2d;
import org.joml.Vector2dc;
import org.junit.Test;

import kendzi.math.geometry.line.LinePoints2d;

/**
 * Tests for enriching polygonal chain.
 */
public class EnrichPolygonalChainUtilTest {

    @Test
    public void enrichOpenPolygonalChainByLineCrossing1() {

        Vector2dc p0 = debugPoint(0, 0, -1);
        Vector2dc p1 = debugPoint(1, 0, 1);
        Vector2dc s0 = debugPoint("s1", 0, 0);

        List<Vector2dc> openPolygonalChain = Arrays.asList(p0, p1);

        LinePoints2d splittingLine = new LinePoints2d(debugPoint("l1", 0, 0), debugPoint("l2", 1, 0));

        List<Vector2dc> enrichedChain = EnrichPolygonalChainUtil.enrichOpenPolygonalChainByLineCrossing(
                openPolygonalChain, splittingLine);

        assertEquals(3, enrichedChain.size());
        assertEquals(p0, enrichedChain.get(0));
        assertEquals(s0, enrichedChain.get(1));
        assertEquals(p1, enrichedChain.get(2));

    }

    @Test
    public void enrichOpenPolygonalChainByLineCrossing2() {

        Vector2dc p0 = debugPoint(0, 0, -1);
        Vector2dc p1 = debugPoint(1, 0, 0);
        Vector2dc p2 = debugPoint(2, 0, 1);

        List<Vector2dc> openPolygonalChain = Arrays.asList(p0, p1, p2);

        LinePoints2d splittingLine = new LinePoints2d(debugPoint("l1", 0, 0), debugPoint("l2", 1, 0));

        List<Vector2dc> enrichedChain = EnrichPolygonalChainUtil.enrichOpenPolygonalChainByLineCrossing(
                openPolygonalChain, splittingLine);

        assertEquals(3, enrichedChain.size());
        assertEquals(p0, enrichedChain.get(0));
        assertEquals(p1, enrichedChain.get(1));
        assertEquals(p2, enrichedChain.get(2));

    }

    @Test
    public void enrichClosedPolygonalChainByLineCrossing1() {

        Vector2dc p0 = debugPoint(0, 0, -1);
        Vector2dc p1 = debugPoint(1, 0, 1);
        Vector2dc p2 = debugPoint(2, -1, 1);
        Vector2dc p3 = debugPoint(3, -1, 0);
        Vector2dc p4 = debugPoint(4, -1, -1);

        Vector2dc s0 = debugPoint("s1", 0, 0);

        List<Vector2dc> openPolygonalChain = Arrays.asList(p0, p1, p2, p3, p4);

        LinePoints2d splittingLine = new LinePoints2d(debugPoint("l1", 0, 0), debugPoint("l2", 1, 0));

        List<Vector2dc> enrichedChain = EnrichPolygonalChainUtil.enrichClosedPolygonalChainByLineCrossing(
                openPolygonalChain, splittingLine);

        assertEquals(6, enrichedChain.size());
        assertEquals(p0, enrichedChain.get(0));
        assertEquals(s0, enrichedChain.get(1));
        assertEquals(p1, enrichedChain.get(2));
        assertEquals(p2, enrichedChain.get(3));
        assertEquals(p3, enrichedChain.get(4));
        assertEquals(p4, enrichedChain.get(5));

    }

    private Vector2dc debugPoint(int name, double x, double y) {
        return debugPoint("p" + name, x, y);
    }

    private Vector2dc debugPoint(final String name, double x, double y) {
        return new DebugVector2dc(name, x, y);
    }

    private static class DebugVector2dc extends Vector2d {
        private final String name;

        DebugVector2dc(String name, double x, double y) {
            super(x, y);
            this.name = name;
        }
        private static final long serialVersionUID = 1L;

        @Override
        public String toString() {
            return name + ' ' + super.toString();
        }

        @Override
        public boolean equals(Object other) {
            if (other instanceof Vector2dc) {
                return super.equals((Vector2dc) other, 0);
            }
            return false;
        }
    }
}
