package kendzi.math.geometry.skeleton.path;

import static org.junit.Assert.*;

import javax.vecmath.Point2d;

import kendzi.math.geometry.skeleton.circular.Edge;

import org.junit.Test;

public class FaceQueueUtilTest {

    @Test
    public void test1() {
        FaceNode n1 = debugNewQueue("n1", true);
        FaceNode n2 = debugNewQueue("n2");
        PathQueue<FaceNode> q1 = n1.list();
        PathQueue<FaceNode> q2 = n2.list();

        FaceQueueUtil.connectQueues(n1, n2);

        assertEquals(2, q1.size());
        assertEquals(0, q2.size());

        assertFalse(((FaceQueue) q1).isClosed());
        assertTrue(((FaceQueue) q2).isClosed());
    }

    @Test
    public void test2() {
        FaceNode n1 = debugNewQueue("n1", true);
        FaceNode n2 = debugNode("n2");

        n1.addPush(n2);

        PathQueue<FaceNode> q1 = n1.list();
        PathQueue<FaceNode> q2 = n2.list();

        FaceQueueUtil.connectQueues(n1, n2);

        assertEquals(2, q1.size());
        assertEquals(q1, q2);

        assertTrue(((FaceQueue) q1).isClosed());
        assertTrue(((FaceQueue) q2).isClosed());
    }

    @Test(expected = IllegalStateException.class)
    public void test3() {
        FaceNode n1 = debugNewQueue("n1", true);
        FaceNode n2 = debugNode("n2");
        FaceNode n3 = debugNode("n3");

        n1.addPush(n2);
        n2.addPush(n3);

        FaceQueueUtil.connectQueues(n1, n2);

        fail();
    }

    @Test(expected = IllegalStateException.class)
    public void test4() {
        FaceNode n1 = debugNewQueue("n1", true);
        FaceNode n2 = debugNewQueue("n2", true);
        // connecting two queues with different edges
        FaceQueueUtil.connectQueues(n1, n2);

        fail();
    }

    @Test(expected = IllegalStateException.class)
    public void test5() {
        FaceNode n1 = debugNewQueue("n1", true);
        FaceNode n2 = debugNewQueue("n2", true);

        n1.addPush(n2);

        fail();
    }

    private FaceNode debugNode(final String name) {
        return new FaceNode(null) {
            @Override
            public String toString() {
                return name;
            }
        };
    }

    private FaceNode debugNewQueue(final String name, boolean edge) {
        FaceQueue fq = new FaceQueue();
        FaceNode fn = new FaceNode(null) {
            @Override
            public String toString() {
                return name;
            }
        };
        fq.addFirst(fn);
        if (edge) {
            fq.setEdge(new Edge(new Point2d(), new Point2d()));
        }
        return fn;
    }

    private FaceNode debugNewQueue(String name) {
        return debugNewQueue(name, false);
    }

}
