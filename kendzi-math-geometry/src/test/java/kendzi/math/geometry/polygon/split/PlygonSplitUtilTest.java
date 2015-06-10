package kendzi.math.geometry.polygon.split;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.vecmath.Point2d;

import kendzi.math.geometry.line.LinePoints2d;
import kendzi.math.geometry.polygon.split.PlygonSplitUtil.SplitResult;

import org.junit.Test;

/**
 * Tests for polygon split util.
 */
public class PlygonSplitUtilTest {

    private static final double EPSILON = 0.00001d;

    @SuppressWarnings("javadoc")
    @Test
    public void rect() {
        Point2d p0 = debugPoint(0, -1, -1);
        Point2d p1 = debugPoint(1, 1, -1);
        Point2d p2 = debugPoint(2, 1, 1);
        Point2d p3 = debugPoint(3, -1, 1);

        Point2d s0 = debugPoint("s0", -1, 0);
        Point2d s1 = debugPoint("s1", 1, 0);

        List<Point2d> polygon = new ArrayList<Point2d>();
        polygon.add(p0);
        polygon.add(p1);
        polygon.add(p2);
        polygon.add(p3);

        LinePoints2d line = new LinePoints2d(debugPoint("l1", 0, 0), debugPoint("l2", 1, 0));

        SplitResult split = PlygonSplitUtil.split(polygon, line);

        assertEquals(1, split.getLeftPolygons().size());
        assertEquals(1, split.getRightPolygons().size());
        assertEqualsPolygon(polygon(s1, p2, p3, s0), split.getLeftPolygons().get(0));
        assertEqualsPolygon(polygon(s0, p0, p1, s1), split.getRightPolygons().get(0));

    }

    @SuppressWarnings("javadoc")
    @Test
    public void oneSiteOfLine() {
        Point2d p0 = debugPoint(0, -1, -1);
        Point2d p1 = debugPoint(1, 1, -1);
        Point2d p2 = debugPoint(2, 1, 1);
        Point2d p3 = debugPoint(3, -1, 1);

        List<Point2d> polygon = new ArrayList<Point2d>();
        polygon.add(p0);
        polygon.add(p1);
        polygon.add(p2);
        polygon.add(p3);

        LinePoints2d line = new LinePoints2d(debugPoint("l1", 0, -2), debugPoint("l2", 1, -2));

        SplitResult split = PlygonSplitUtil.split(polygon, line);

        assertEquals(1, split.getLeftPolygons().size());
        assertEquals(0, split.getRightPolygons().size());
        assertEqualsPolygon(polygon(p0, p1, p2, p3), split.getLeftPolygons().get(0));

        line = new LinePoints2d(debugPoint("l1", 0, 2), debugPoint("l2", 1, 2));

        split = PlygonSplitUtil.split(polygon, line);

        assertEquals(0, split.getLeftPolygons().size());
        assertEquals(1, split.getRightPolygons().size());
        assertEqualsPolygon(polygon(p0, p1, p2, p3), split.getRightPolygons().get(0));

    }

    @SuppressWarnings("javadoc")
    @Test
    public void polygon1() {
        // polygon points
        Point2d p0 = debugPoint(0, -1, -1);
        Point2d p1 = debugPoint(1, 0, 1);
        Point2d p2 = debugPoint(2, 1, -1);
        Point2d p3 = debugPoint(3, 0, 2);

        // generated splitting points
        Point2d s0 = debugPoint("s0", -2d / 3d, 0);
        Point2d s1 = debugPoint("s1", -0.5, 0);
        Point2d s2 = debugPoint("s2", 0.5, 0);
        Point2d s3 = debugPoint("s3", 2d / 3d, 0);

        List<Point2d> polygon = new ArrayList<Point2d>();
        polygon.add(p0);
        polygon.add(p1);
        polygon.add(p2);
        polygon.add(p3);

        LinePoints2d line = new LinePoints2d(debugPoint("l1", 0, 0), debugPoint("l2", 1, 0));

        SplitResult split = PlygonSplitUtil.split(polygon, line);

        assertEquals(1, split.getLeftPolygons().size());
        assertEquals(2, split.getRightPolygons().size());
        assertEqualsPolygon(polygon(s3, p3, s0, s1, p1, s2), split.getLeftPolygons().get(0));

        assertEqualsPolygon(polygon(s0, p0, s1), split.getRightPolygons().get(1));
        assertEqualsPolygon(polygon(s2, p2, s3), split.getRightPolygons().get(0));

    }

    @SuppressWarnings("javadoc")
    @Test
    public void saw1() {
        List<Point2d> polygon = saw();

        LinePoints2d line = new LinePoints2d(debugPoint("l1", 0, 0), debugPoint("l2", 1, 0));

        SplitResult split = PlygonSplitUtil.split(polygon, line);

        assertEquals(1, split.getLeftPolygons().size());
        assertEquals(4, split.getRightPolygons().size());

        Point2d p0 = polygon.get(0);
        Point2d p8 = polygon.get(8);
        Point2d p9 = polygon.get(9);
        Point2d p10 = polygon.get(10);

        assertEqualsPolygon(polygon(p8, p9, p10, p0), split.getLeftPolygons().get(0));

        assertEquals(3, split.getRightPolygons().get(0).size());
        assertEquals(3, split.getRightPolygons().get(1).size());
        assertEquals(3, split.getRightPolygons().get(2).size());
        assertEquals(3, split.getRightPolygons().get(3).size());
    }

    @SuppressWarnings("javadoc")
    @Test
    public void sawLess1() {
        // the points on split lines
        List<Point2d> polygon = sawLess();

        LinePoints2d line = new LinePoints2d(debugPoint("l1", 0, 0), debugPoint("l2", 1, 0));

        SplitResult split = PlygonSplitUtil.split(polygon, line);

        assertEquals(1, split.getLeftPolygons().size());
        assertEquals(2, split.getRightPolygons().size());

        Point2d p0 = polygon.get(0);
        Point2d p1 = polygon.get(1);
        Point2d p2 = polygon.get(2);
        Point2d p3 = polygon.get(3);
        Point2d p4 = polygon.get(4);
        Point2d p8 = polygon.get(8);
        Point2d p9 = polygon.get(9);
        Point2d p10 = polygon.get(10);

        assertEqualsPolygon(polygon(p8, p9, p10, p0, p1, p2, p3, p4), split.getLeftPolygons().get(0));

        assertEquals(3, split.getRightPolygons().get(0).size());
        assertEquals(3, split.getRightPolygons().get(1).size());
    }

    @SuppressWarnings("javadoc")
    @Test
    public void spiral1() {
        // The points on split lines.
        List<Point2d> polygon = spiral();

        LinePoints2d line = new LinePoints2d(debugPoint("l1", 0, 3.5), debugPoint("l2", 1, 3.5));

        SplitResult split = PlygonSplitUtil.split(polygon, line);

        List<List<Point2d>> leftPolygons = split.getLeftPolygons();
        assertEquals(3, leftPolygons.size());

        List<List<Point2d>> rightPolygons = split.getRightPolygons();
        assertEquals(3, rightPolygons.size());

        Point2d p0 = polygon.get(0);
        Point2d p1 = polygon.get(1);
        Point2d p2 = polygon.get(2);
        Point2d p3 = polygon.get(3);
        Point2d p4 = polygon.get(4);
        Point2d p5 = polygon.get(5);
        Point2d p6 = polygon.get(6);
        Point2d p7 = polygon.get(7);
        Point2d p8 = polygon.get(8);
        Point2d p9 = polygon.get(9);
        Point2d p10 = polygon.get(10);
        Point2d p11 = polygon.get(11);
        Point2d p12 = polygon.get(12);
        Point2d p13 = polygon.get(13);
        Point2d p14 = polygon.get(14);
        Point2d p15 = polygon.get(15);
        Point2d p16 = polygon.get(16);
        Point2d p17 = polygon.get(17);
        Point2d p18 = polygon.get(18);
        Point2d p19 = polygon.get(19);

        Point2d s0 = debugPoint("s0", 0.0, 3.5);
        Point2d s1 = debugPoint("s1", 1.0, 3.5);
        Point2d s2 = debugPoint("s2", 2.0, 3.5);
        Point2d s3 = debugPoint("s3", 3.0, 3.5);
        Point2d s4 = debugPoint("s4", 4.0, 3.5);
        Point2d s5 = debugPoint("s5", 5.0, 3.5);
        Point2d s6 = debugPoint("s6", 6.0, 3.5);
        Point2d s7 = debugPoint("s7", 7.0, 3.5);
        Point2d s8 = debugPoint("s8", 8.0, 3.5);
        Point2d s9 = debugPoint("s9", 9.0, 3.5);

        assertEqualsPolygon(polygon(p2, p3, s8, s9, p18, p19, s0, s1), findPolygonWithPoint(p19, leftPolygons));
        assertEqualsPolygon(polygon(p6, p7, s6, s7, p14, p15, s2, s3), findPolygonWithPoint(p15, leftPolygons));
        assertEqualsPolygon(polygon(p10, p11, s4, s5), findPolygonWithPoint(p11, leftPolygons));

        assertEqualsPolygon(polygon(p0, p1, s1, s0), findPolygonWithPoint(p0, rightPolygons));
        assertEqualsPolygon(polygon(p16, p17, s9, s8, p4, p5, s3, s2), findPolygonWithPoint(p16, rightPolygons));
        assertEqualsPolygon(polygon(p12, p13, s7, s6, p8, p9, s5, s4), findPolygonWithPoint(p12, rightPolygons));
    }

    private List<Point2d> findPolygonWithPoint(Point2d find, List<List<Point2d>> polygons) {
        for (List<Point2d> polygon : polygons) {

            for (Point2d point : polygon) {
                if (find.equals(point)) {
                    return polygon;
                }
            }
        }

        return null;
    }

    private List<Point2d> saw() {
        List<Point2d> p = new ArrayList<Point2d>();

        p.add(debugPoint(0, 0, 0));
        p.add(debugPoint(1, 1, -1));
        p.add(debugPoint(2, 2, 0));
        p.add(debugPoint(3, 3, -1));
        p.add(debugPoint(4, 4, 0));
        p.add(debugPoint(5, 5, -1));
        p.add(debugPoint(6, 6, 0));
        p.add(debugPoint(7, 7, -1));
        p.add(debugPoint(8, 8, 0));

        p.add(debugPoint(9, 8, 1));
        p.add(debugPoint(10, 0, 1));

        return p;
    }

    private List<Point2d> sawLess() {

        List<Point2d> p = new ArrayList<Point2d>();

        p.add(debugPoint(0, 0, 0));
        p.add(debugPoint(1, 1, 0));
        p.add(debugPoint(2, 2, 0));
        p.add(debugPoint(3, 3, 0));
        p.add(debugPoint(4, 4, 0));
        p.add(debugPoint(5, 5, -1));
        p.add(debugPoint(6, 6, 0));
        p.add(debugPoint(7, 7, -1));
        p.add(debugPoint(8, 8, 0));

        p.add(debugPoint(9, 8, 1));
        p.add(debugPoint(10, 0, 1));

        return p;
    }

    private List<Point2d> spiral() {

        List<Point2d> p = new ArrayList<Point2d>();

        p.add(debugPoint(0, 0, 0));
        p.add(debugPoint(1, 1, 0));
        p.add(debugPoint(2, 1, 7));
        p.add(debugPoint(3, 8, 7));
        p.add(debugPoint(4, 8, 1));
        p.add(debugPoint(5, 3, 1));
        p.add(debugPoint(6, 3, 5));
        p.add(debugPoint(7, 6, 5));
        p.add(debugPoint(8, 6, 3));

        p.add(debugPoint(9, 5, 3));
        p.add(debugPoint(10, 5, 4));
        p.add(debugPoint(11, 4, 4));
        p.add(debugPoint(12, 4, 2));

        p.add(debugPoint(13, 7, 2));
        p.add(debugPoint(14, 7, 6));
        p.add(debugPoint(15, 2, 6));

        p.add(debugPoint(16, 2, 0));
        p.add(debugPoint(17, 9, 0));
        p.add(debugPoint(18, 9, 8));
        p.add(debugPoint(19, 0, 8));
        return p;
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

    private void assertEqualsPolygon(List<Point2d> expected, List<Point2d> actual) {

        int expectedListSize = expected.size();

        assertEquals("polygons sizes don't equals", expectedListSize, actual.size());

        permute: for (int p = 0; p < expectedListSize; p++) {
            for (int i = 0; i < expectedListSize; i++) {
                if (!expected.get((i + p) % expectedListSize).epsilonEquals(actual.get(i), EPSILON)) {
                    continue permute;
                }
            }
            return;
        }
        fail("polygons don't equals" + "expected:<" + expected + "> but was:<" + actual + ">");
    }

    private List<Point2d> polygon(Point2d... points) {
        return Arrays.asList(points);
    }
}
