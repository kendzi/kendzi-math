package kendzi.math.geometry.skeleton;

import static kendzi.math.geometry.TestUtil.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.vecmath.Point2d;
import javax.vecmath.Vector2d;

import kendzi.math.geometry.line.LineLinear2d;
import kendzi.math.geometry.ray.Ray2d;
import kendzi.math.geometry.skeleton.circular.CircularList;
import kendzi.math.geometry.skeleton.circular.Edge;
import kendzi.math.geometry.skeleton.circular.Vertex;
import kendzi.math.geometry.skeleton.events.EdgeEvent;
import kendzi.math.geometry.skeleton.events.PickEvent;
import kendzi.math.geometry.skeleton.events.SkeletonEvent;
import kendzi.math.geometry.skeleton.utils.LavUtil;
import kendzi.math.geometry.skeleton.utils.LavUtil.SplitSlavs;

import org.junit.Test;

public class SkeletonInternalTest {

    @Test
    public void testEdgeBehindBisector_1() {

        Ray2d bisector = new Ray2d(p(0, -1), v(0, 1));

        LineLinear2d edge = new LineLinear2d(p(-1, 0), p(1, 0));

        assertFalse(Skeleton.edgeBehindBisector(bisector, edge));

    }

    @Test
    public void testEdgeBehindBisector_2() {

        Ray2d bisector = new Ray2d(p(0, 0), v(1, 0));

        LineLinear2d edge = new LineLinear2d(p(-1, 0), p(1, 0));

        assertTrue(Skeleton.edgeBehindBisector(bisector, edge));

    }

    @Test
    public void testEdgeBehindBisector_3() {

        Ray2d bisector = new Ray2d(p(0, 0), v(0, 1));

        LineLinear2d edge = new LineLinear2d(p(0, 1), p(0, -1));

        assertTrue(Skeleton.edgeBehindBisector(bisector, edge));

    }

    @Test
    public void testEdgeBehindBisector_4() {

        Ray2d bisector = new Ray2d(p(-1, 0.0000001), v(1, 0));

        LineLinear2d edge = new LineLinear2d(p(-1, 0), p(1, 0));

        assertTrue(Skeleton.edgeBehindBisector(bisector, edge));

    }

    @Test
    public void testMergeLav_1() {

        Vertex v1 = debugVertex("v1");
        Vertex v2 = debugVertex("v2");
        Vertex v3 = debugVertex("v3");
        Vertex v4 = debugVertex("v4");

        CircularList<Vertex> lav1 = new CircularList<Vertex>();
        lav1.addLast(v1);
        lav1.addLast(v2);
        lav1.addLast(v3);
        lav1.addLast(v4);

        Vertex e1 = debugVertex("e1");
        Vertex e2 = debugVertex("e2");
        Vertex e3 = debugVertex("e3");
        Vertex e4 = debugVertex("e4");

        CircularList<Vertex> lav2 = new CircularList<Vertex>();
        lav2.addLast(e1);
        lav2.addLast(e2);
        lav2.addLast(e3);
        lav2.addLast(e4);

        CircularList<Vertex> mergeLav = Skeleton.mergeLav(v1, e2);
        assertLavOrder(new Vertex[] { e3, e4, e1, e2, v2, v3, v4, v1 }, mergeLav);

    }

    @Test
    public void testFindSplitIndex_1() {

        Edge anyEdge = debugEdge("anyEdge");
        Edge oppositeEdge = debugEdge("oppositeEdge");

        Vertex v1 = debugVertex("v1", anyEdge, anyEdge);
        Vertex v2 = debugVertex("v2", anyEdge, anyEdge);
        Vertex v3 = debugVertex("v3", oppositeEdge, anyEdge);
        Vertex v4 = debugVertex("v4", anyEdge, anyEdge);
        Vertex v5 = debugVertex("v5", anyEdge, anyEdge);

        CircularList<Vertex> lav1 = new CircularList<Vertex>();
        lav1.addLast(v1);
        lav1.addLast(v2);
        lav1.addLast(v3);
        lav1.addLast(v4);
        lav1.addLast(v5);

        int split = Skeleton.findSplitIndex(v1, oppositeEdge);

        assertEquals(2, split);
    }

    @Test
    public void testFindSplitIndex_2() {

        Edge anyEdge = debugEdge("anyEdge");
        Edge oppositeEdge = debugEdge("oppositeEdge");

        Vertex v1 = debugVertex("v1", anyEdge, anyEdge);
        Vertex v2 = debugVertex("v2", anyEdge, anyEdge);
        Vertex v3 = debugVertex("v3", anyEdge, oppositeEdge);
        Vertex v4 = debugVertex("v4", anyEdge, anyEdge);
        Vertex v5 = debugVertex("v5", anyEdge, anyEdge);

        CircularList<Vertex> lav1 = new CircularList<Vertex>();
        lav1.addLast(v1);
        lav1.addLast(v2);
        lav1.addLast(v3);
        lav1.addLast(v4);
        lav1.addLast(v5);

        int split = Skeleton.findSplitIndex(v1, oppositeEdge);

        assertEquals(3, split);
    }

    @Test
    public void testFindSplitIndex_3() {

        Edge anyEdge = debugEdge("anyEdge");
        Edge oppositeEdge = debugEdge("oppositeEdge");

        Vertex v1 = debugVertex("v1", oppositeEdge, anyEdge);
        Vertex v2 = debugVertex("v2", anyEdge, anyEdge);
        Vertex v3 = debugVertex("v3", anyEdge, anyEdge);
        Vertex v4 = debugVertex("v4", anyEdge, anyEdge);
        Vertex v5 = debugVertex("v5", anyEdge, anyEdge);

        CircularList<Vertex> lav1 = new CircularList<Vertex>();
        lav1.addLast(v1);
        lav1.addLast(v2);
        lav1.addLast(v3);
        lav1.addLast(v4);
        lav1.addLast(v5);

        int split = Skeleton.findSplitIndex(v1, oppositeEdge);

        assertEquals(0, split);
    }

    @Test
    public void testFindSplitIndex_4() {

        Edge anyEdge = debugEdge("anyEdge");
        Edge oppositeEdge = debugEdge("oppositeEdge");

        Vertex v1 = debugVertex("v1", anyEdge, anyEdge);
        Vertex v2 = debugVertex("v2", anyEdge, anyEdge);
        Vertex v3 = debugVertex("v3", anyEdge, anyEdge);
        Vertex v4 = debugVertex("v4", anyEdge, anyEdge);
        Vertex v5 = debugVertex("v5", anyEdge, oppositeEdge);

        CircularList<Vertex> lav1 = new CircularList<Vertex>();
        lav1.addLast(v1);
        lav1.addLast(v2);
        lav1.addLast(v3);
        lav1.addLast(v4);
        lav1.addLast(v5);

        int split = Skeleton.findSplitIndex(v1, oppositeEdge);

        assertEquals(0, split);
    }

    @Test
    public void testFindSplitLav_1() {

        Vertex v1 = debugVertex("v1");
        Vertex v2 = debugVertex("v2");
        Vertex v3 = debugVertex("v3");
        Vertex v4 = debugVertex("v4");
        Vertex v5 = debugVertex("v5");

        CircularList<Vertex> lav1 = new CircularList<Vertex>();
        lav1.addLast(v1);
        lav1.addLast(v2);
        lav1.addLast(v3);
        lav1.addLast(v4);
        lav1.addLast(v5);

        SplitSlavs splitLav = LavUtil.splitLav(v1, 3);

        assertLavOrder(new Vertex[] { v2, v3 }, splitLav.getNewLawRight());
        assertLavOrder(new Vertex[] { v4, v5 }, splitLav.getNewLawLeft());
    }

    @Test(expected = RuntimeException.class)
    public void testFindSplitLav_2() {

        Vertex v1 = debugVertex("v1");
        Vertex v2 = debugVertex("v2");
        Vertex v3 = debugVertex("v3");
        Vertex v4 = debugVertex("v4");
        Vertex v5 = debugVertex("v5");

        CircularList<Vertex> lav1 = new CircularList<Vertex>();
        lav1.addLast(v1);
        lav1.addLast(v2);
        lav1.addLast(v3);
        lav1.addLast(v4);
        lav1.addLast(v5);

        LavUtil.splitLav(v1, 1);

        fail();
    }

    @Test()
    public void testVertexOpositeEdge_1() {

        Point2d point = new Point2d(0, 2);

        Edge edge = debugEdge("edge");
        Edge edgePrevious = debugEdge("edgePrevious");
        Edge edgeNext = debugEdge("edgeNext");

        edgesCircularList(edge, edgeNext, edgePrevious);

        edge.setBisectorPrevious(new Ray2d(new Point2d(-2, 0), new Vector2d(1, 1)));
        edge.setBisectorNext(new Ray2d(new Point2d(2, 0), new Vector2d(1, 1)));

        Edge vertexOppositeEdge = Skeleton.vertexOpositeEdge(point, edge);

        assertEquals(edgePrevious, vertexOppositeEdge);
    }

    @Test()
    public void testVertexOpositeEdge_2() {

        Point2d point = new Point2d(0, 2);

        Edge edge = debugEdge("edge");
        Edge edgePrevious = debugEdge("edgePrevious");
        Edge edgeNext = debugEdge("edgeNext");

        edgesCircularList(edge, edgeNext, edgePrevious);

        edge.setBisectorPrevious(new Ray2d(new Point2d(-3, 0), new Vector2d(1, 1)));
        edge.setBisectorNext(new Ray2d(new Point2d(2, 0), new Vector2d(-1, 1)));

        Edge vertexOppositeEdge = Skeleton.vertexOpositeEdge(point, edge);

        assertEquals(edge, vertexOppositeEdge);
    }

    @Test()
    public void testVertexOpositeEdge_3() {

        Point2d point = new Point2d(0, 2);

        Edge edge = debugEdge("edge");
        Edge edgePrevious = debugEdge("edgePrevious");
        Edge edgeNext = debugEdge("edgeNext");

        edgesCircularList(edge, edgeNext, edgePrevious);

        edge.setBisectorPrevious(new Ray2d(new Point2d(-2, 0), new Vector2d(1, 1)));
        edge.setBisectorNext(new Ray2d(new Point2d(2, 0), new Vector2d(-1, 1)));

        Edge vertexOppositeEdge = Skeleton.vertexOpositeEdge(point, edge);

        assertEquals(edge, vertexOppositeEdge);
    }

    @Test
    public void createEdgeChain_1() {

        Vertex v1 = debugVertex("v1");
        Vertex v2 = debugVertex("v2");
        Vertex v3 = debugVertex("v3");
        Vertex v4 = debugVertex("v4");
        Vertex v5 = debugVertex("v5");

        EdgeEvent e1 = debugEdgeEvent("e1", v1, v2);
        EdgeEvent e2 = debugEdgeEvent("e2", v3, v4);
        EdgeEvent e3 = debugEdgeEvent("e3", v2, v5);

        List<EdgeEvent> edgeCluster = new ArrayList<EdgeEvent>();
        edgeCluster.add(e1);
        edgeCluster.add(e2);
        edgeCluster.add(e3);

        ArrayList<EdgeEvent> chain = Skeleton.createEdgeChain(edgeCluster);

        assertListEquals(new EdgeEvent[] { e1, e3 }, chain);
    }

    @Test
    public void createEdgeChain_2() {

        Vertex v1 = debugVertex("v1");
        Vertex v2 = debugVertex("v2");
        Vertex v3 = debugVertex("v3");
        Vertex v4 = debugVertex("v4");

        EdgeEvent e1 = debugEdgeEvent("e1", v1, v2);
        EdgeEvent e2 = debugEdgeEvent("e2", v3, v1);
        EdgeEvent e3 = debugEdgeEvent("e3", v2, v4);

        List<EdgeEvent> edgeCluster = new ArrayList<EdgeEvent>();
        edgeCluster.add(e1);
        edgeCluster.add(e2);
        edgeCluster.add(e3);

        ArrayList<EdgeEvent> chain = Skeleton.createEdgeChain(edgeCluster);

        assertListEquals(new EdgeEvent[] { e2, e1, e3 }, chain);
    }

    @Test
    public void groupLevelEvents_1() {
        //
        Vertex v1 = debugVertex("v1");
        Vertex v2 = debugVertex("v2");
        Vertex v3 = debugVertex("v3");
        Vertex v4 = debugVertex("v4");
        Point2d p1 = debugPoint2d("p1", 0, 0);

        EdgeEvent e1 = debugEdgeEvent("e1", v1, v2, p1);
        EdgeEvent e2 = debugEdgeEvent("e2", v2, v3, p1);
        EdgeEvent e3 = debugEdgeEvent("e3", v3, v4, p1);
        EdgeEvent e4 = debugEdgeEvent("e4", v4, v1, p1);

        List<SkeletonEvent> edgeCluster = new ArrayList<SkeletonEvent>();
        edgeCluster.add(e1);
        edgeCluster.add(e2);
        edgeCluster.add(e3);
        edgeCluster.add(e4);

        List<SkeletonEvent> chain = Skeleton.groupLevelEvents(edgeCluster);

        assertEquals(PickEvent.class, chain.get(0).getClass());
    }

    @Test()
    public void cutLavPart() {

        Vertex v1 = debugVertex("v1");
        Vertex v2 = debugVertex("v2");
        Vertex v3 = debugVertex("v3");
        Vertex v4 = debugVertex("v4");
        Vertex v5 = debugVertex("v5");

        CircularList<Vertex> lav1 = new CircularList<Vertex>();
        lav1.addLast(v1);
        lav1.addLast(v2);
        lav1.addLast(v3);
        lav1.addLast(v4);
        lav1.addLast(v5);

        List<Vertex> cutLavPart = LavUtil.cutLavPart(v2, v3);

        assertListEquals(Arrays.asList(v2, v3), cutLavPart);
    }

    @Test()
    public void cutLavPart_2() {

        Vertex v1 = debugVertex("v1");
        Vertex v2 = debugVertex("v2");
        Vertex v3 = debugVertex("v3");
        Vertex v4 = debugVertex("v4");
        Vertex v5 = debugVertex("v5");

        CircularList<Vertex> lav1 = new CircularList<Vertex>();
        lav1.addLast(v1);
        lav1.addLast(v2);
        lav1.addLast(v3);
        lav1.addLast(v4);
        lav1.addLast(v5);

        List<Vertex> cutLavPart = LavUtil.cutLavPart(v1, v1);

        assertListEquals(Arrays.asList(v1), cutLavPart);
    }

    @Test()
    public void choseOppositeEdgeLav() {

        Point2d p1 = debugPoint2d("p0", 0, 0);
        Point2d p2 = debugPoint2d("p1", 1, 0);
        Edge oppositeEdge = new Edge(p1, p2);

        Vertex v1 = debugVertex("v1", p1);
        Vertex v2 = debugVertex("v2", p2);

        CircularList<Vertex> lav1 = new CircularList<Vertex>();
        lav1.addLast(v1);
        lav1.addLast(v2);

        List<Vertex> edgeLavs = Arrays.asList(v1, v1);

        Point2d center = debugPoint2d("c", 0.5, 1);

        Vertex vertex = Skeleton.choseOppositeEdgeLav(edgeLavs, oppositeEdge, center);

        assertEquals(vertex, v1);
    }

    @Test()
    public void choseOppositeEdgeLav_2() {

        Point2d p1 = debugPoint2d("p1", 0, 0);
        Point2d p2 = debugPoint2d("p2", 0.2, 0);
        Point2d p3 = debugPoint2d("p3", 0.2, 0);
        Point2d p4 = debugPoint2d("p4", 1, 0);

        Edge oppositeEdge = new Edge(p1, p3);

        Vertex v1 = debugVertex("v1", p1);
        Vertex v2 = debugVertex("v2", p2);

        CircularList<Vertex> lav1 = new CircularList<Vertex>();
        lav1.addLast(v1);
        lav1.addLast(v2);

        Vertex v3 = debugVertex("v3", p3);
        Vertex v4 = debugVertex("v4", p4);

        CircularList<Vertex> lav2 = new CircularList<Vertex>();
        lav2.addLast(v3);
        lav2.addLast(v4);

        List<Vertex> edgeLavs = Arrays.asList(v1, v3);

        Point2d center = debugPoint2d("c", 0.5, 1);

        Vertex vertex = Skeleton.choseOppositeEdgeLav(edgeLavs, oppositeEdge, center);

        assertEquals(v3, vertex);
    }

    private EdgeEvent debugEdgeEvent(final String name, Vertex v1, Vertex v2) {
        EdgeEvent event = new EdgeEvent(null, 0, v1, v2) {
            @Override
            public String toString() {
                return name;
            };
        };

        return event;
    }

    private EdgeEvent debugEdgeEvent(final String name, Vertex v1, Vertex v2, Point2d point) {
        EdgeEvent event = new EdgeEvent(point, 0, v1, v2) {
            @Override
            public String toString() {
                return name;
            };
        };

        return event;
    }

    private void edgesCircularList(Edge... edges) {
        CircularList<Edge> edgesList = new CircularList<Edge>();

        for (Edge edgeEntry : edges) {
            edgesList.addLast(edgeEntry);
        }
    }

    private Edge debugEdge(final String string) {
        return new Edge(new Point2d(), new Point2d()) {
            @Override
            public String toString() {
                return string;
            }
        };
    }

    private Vertex debugVertex(final String name1) {

        return new Vertex() {
            @Override
            public String toString() {
                return name1;
            }
        };
    }

    private Vertex debugVertex(final String name1, Edge edgeLeft, Edge edgeRight) {

        Vertex v = new Vertex() {
            @Override
            public String toString() {
                return name1;
            }
        };
        v.previousEdge = edgeLeft;
        v.nextEdge = edgeRight;

        return v;
    }

    private Vertex debugVertex(final String name1, Point2d p) {

        Vertex v = new Vertex(p, 0, null, null, null) {
            @Override
            public String toString() {
                return name1;
            }

        };
        return v;
    }

    private <T> void assertListEquals(List<T> expecteds, List<T> actuals) {
        assertArrayEquals(expecteds.toArray(), actuals.toArray());
    }

    private <T> void assertListEquals(T[] expecteds, List<T> actuals) {
        assertArrayEquals(expecteds, actuals.toArray());
    }

    private void assertLavOrder(Vertex[] vertexEntry2s, CircularList<Vertex> mergeLav) {
        int i = 0;
        for (Vertex vertexEntry2 : mergeLav) {
            if (!vertexEntry2s[i].equals(vertexEntry2)) {
                fail("as lav element [" + i + "] expected " + vertexEntry2s[i] + " but get " + vertexEntry2);
            }

            i++;
        }

    }

    public static Point2d debugPoint2d(final String name, double x, double y) {
        return new Point2d(x, y) {
            @Override
            public String toString() {
                return name;
            };
        };
    }
}
