package kendzi.math.geometry.polygon.split;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.vecmath.Point2d;

import kendzi.math.geometry.line.LinePoints2d;
import kendzi.math.geometry.polygon.split.PlygonSplitUtil.Close;
import kendzi.math.geometry.polygon.split.PlygonSplitUtil.Node;

import org.junit.Test;

/**
 * Tests for internals of polygon split util.
 */
public class PlygonSplitUtilInternalTest {

    private static final double EPSILON = 0.00001d;

    @Test
    public void closePolygonsSingleSquare() {

        Point2d p1 = debugPoint(0, 1, -0);
        Point2d p2 = debugPoint(1, 1, -1);
        Point2d p3 = debugPoint(2, 1, 1);
        Point2d p4 = debugPoint(4, -1, -0);

        Node n0 = debugNode("n0", p1, 0);
        Node n1 = debugNode("n1", p2, 1);
        Node n2 = debugNode("n2", p3, 1);
        Node n3 = debugNode("n3", p4, 0);

        LinePoints2d line = new LinePoints2d(debugPoint("l1", 0, 0), debugPoint("l2", 1, 0));

        @SuppressWarnings("unchecked")
        List<Close> polygons = PlygonSplitUtil.closePolygons(Arrays.asList(Arrays.<Node> asList(n0, n1, n2, n3)), line);

        assertEquals(1, polygons.size());
        assertEquals(Arrays.asList(n0, n1, n2, n3), polygons.get(0).chain);

    }

    @Test
    public void theClosestInnerClose1() {

        Close close1 = debugClose("c1", -0.5, 0.5, true, null);

        List<Close> innerCloses = Arrays.asList(close1);

        double endDistance = -0.6666666666666666;
        double beginDistance = 0.6666666666666666;

        Close theClosestInnerClose = PlygonSplitUtil.theClosestInnerClose(endDistance, beginDistance, innerCloses);

        assertEquals(close1, theClosestInnerClose);

        Close close2 = debugClose("c2", 0.5, -0.5, true, null);
        List<Close> innerCloses2 = Arrays.asList(close2);

        endDistance = 0.6666666666666666;
        beginDistance = -0.6666666666666666;

        theClosestInnerClose = PlygonSplitUtil.theClosestInnerClose(endDistance, beginDistance, innerCloses2);
        assertEquals(close2, theClosestInnerClose);

    }

    @Test
    public void theClosestInnerClose2() {

        Close close1 = debugClose("c1", 0.1, 0.5, true, null);
        Close close2 = debugClose("c2", -0.1, -0.5, true, null);

        List<Close> innerCloses = Arrays.asList(close1, close2);

        double endDistance = -0.6666666666666666;
        double beginDistance = 0.6666666666666666;

        Close theClosestInnerClose = PlygonSplitUtil.theClosestInnerClose(endDistance, beginDistance, innerCloses);

        assertEquals(close2, theClosestInnerClose);

    }

    @Test
    public void theClosestInnerClose3() {

        Close close1 = debugClose("c1", -0.1, -0.5, true, null);
        Close close2 = debugClose("c2", 0.1, 0.5, true, null);

        List<Close> innerCloses = Arrays.asList(close1, close2);

        double endDistance = 0.6666666666666666;
        double beginDistance = -0.6666666666666666;

        Close theClosestInnerClose = PlygonSplitUtil.theClosestInnerClose(endDistance, beginDistance, innerCloses);

        assertEquals(close2, theClosestInnerClose);

    }

    @Test
    public void close1() {

        Point2d p0 = debugPoint(0, -0.5, 0.0);
        Point2d p1 = debugPoint(1, 0, 1);
        Point2d p2 = debugPoint(2, 0.5, -0.0);

        Point2d p3 = debugPoint(3, 0.6666666666666666, -0.0);
        Point2d p4 = debugPoint(4, 0, 2);
        Point2d p5 = debugPoint(5, -0.6666666666666666, -0.0);

        Node n0 = debugNode("n0", p0, 0);
        Node n1 = debugNode("n1", p1, 1);
        Node n2 = debugNode("n2", p2, 0);

        Node n3 = debugNode("n3", p3, 0);
        Node n4 = debugNode("n4", p4, 2);
        Node n5 = debugNode("n5", p5, 0);

        List<Node> chain2 = Arrays.asList(n0, n1, n2);
        List<Node> chain1 = new ArrayList<Node>(Arrays.asList(n3, n4, n5));

        Close close1 = debugClose("c1", 0.6666666666666666, -0.6666666666666666, false, chain1);
        Close close2 = debugClose("c2", -0.5, 0.5, true, chain2);

        PlygonSplitUtil.close(Arrays.asList(close1), Arrays.asList(close2));

        assertTrue(close2.removed);
        assertNull(close2.chain);

        assertEquals(6, close1.chain.size());
        assertEquals(Arrays.asList(n3, n4, n5, n0, n1, n2), close1.chain);

    }

    private Close debugClose(final String name, double beginDistance, double endDistance, boolean direction,
            List<Node> chain) {
        Close close = new Close() {
            @Override
            public String toString() {
                return name;
            }
        };
        close.beginDistance = beginDistance;
        close.endDistance = endDistance;
        close.direction = direction;
        close.chain = chain;
        return close;
    }

    public void closePolygons1() {

        Point2d p0 = debugPoint(0, -0.5, 0.0);
        Point2d p1 = debugPoint(1, 0, 1);
        Point2d p2 = debugPoint(2, 0.5, -0.0);

        Point2d p3 = debugPoint(3, 0.6666666666666666, -0.0);
        Point2d p4 = debugPoint(4, 0, 2);
        Point2d p5 = debugPoint(5, -0.6666666666666666, -0.0);

        Node n0 = debugNode("n0", p0, 0);
        Node n1 = debugNode("n1", p1, 1);
        Node n2 = debugNode("n2", p2, 0);

        Node n3 = debugNode("n3", p3, 0);
        Node n4 = debugNode("n4", p4, 2);
        Node n5 = debugNode("n5", p5, 0);

        LinePoints2d line = new LinePoints2d(debugPoint("l1", 0, 0), debugPoint("l2", 1, 0));

        List<Node> chain1 = Arrays.asList(n0, n1, n2);
        List<Node> chain2 = Arrays.asList(n3, n4, n5);

        @SuppressWarnings("unchecked")
        List<Close> polygons = PlygonSplitUtil.closePolygons(Arrays.asList(chain1, chain2), line);

        assertEquals(1, polygons.size());
        assertEquals(Arrays.asList(n0, n1, n2, n3, n4, n5), polygons.get(0).chain);
    }

    private Node debugNode(final String name, Point2d point, double det) {
        Node n = new Node(point, det) {
            @Override
            public String toString() {
                return name;
            };
        };
        return n;
    }

    @Test
    public void findRightAndLeftParts() {

        Node n0 = detNode("n0", -1.0);
        Node n1 = detNode("n1", -1.0);
        Node n2 = detNode("n2", 0.0);
        Node n3 = detNode("n3", 1.0);
        Node n4 = detNode("n4", 1.0);
        Node n5 = detNode("n5", 0.0);

        List<Node> list = Arrays.asList(n0, n1, n2, n3, n4, n5);

        List<List<Node>> left = new ArrayList<List<Node>>();
        List<List<Node>> right = new ArrayList<List<Node>>();

        PlygonSplitUtil.findRightAndLeftParts(list, left, right);

        List<Node> leftPolygon = left.get(0);
        List<Node> rightPolygon = right.get(0);

        assertEquals(4, leftPolygon.size());
        assertEquals(4, rightPolygon.size());

        assertEquals(Arrays.asList(n5, n0, n1, n2), rightPolygon);
        assertEquals(Arrays.asList(n2, n3, n4, n5), leftPolygon);
    }

    private Node detNode(final String name, double d) {
        Node n = new Node(null, d) {
            @Override
            public String toString() {
                return name;
            }
        };
        return n;
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

    @Test
    public void findTheBiggestBegin() {

        Close close1 = debugClose("c1", 0.6, -0.6, false, null);
        Close close2 = debugClose("c2", -0.5, 0.5, false, null);
        List<Close> closes = Arrays.asList(close1, close2);
        double bigestBegin = PlygonSplitUtil.findTheBiggestBegin(closes);

        assertEquals(0.6, bigestBegin, EPSILON);
    }

    @Test
    public void findTheBiggestEnd() {

        Close close1 = debugClose("c1", 0.6, -0.6, false, null);
        Close close2 = debugClose("c2", -0.5, 0.5, false, null);
        List<Close> closes = Arrays.asList(close1, close2);
        double bigestBegin = PlygonSplitUtil.findTheBiggestEnd(closes);

        assertEquals(0.5, bigestBegin, EPSILON);
    }

    @Test
    public void findTheSmallestBegin() {

        Close close1 = debugClose("c1", 0.6, -0.6, false, null);
        Close close2 = debugClose("c2", -0.5, 0.5, false, null);
        List<Close> closes = Arrays.asList(close1, close2);
        double bigestBegin = PlygonSplitUtil.findTheSmallestBegin(closes);

        assertEquals(-0.5, bigestBegin, EPSILON);
    }

}
