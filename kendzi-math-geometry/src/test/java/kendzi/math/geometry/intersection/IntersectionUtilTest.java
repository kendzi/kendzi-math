package kendzi.math.geometry.intersection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.joml.Vector3d;
import org.junit.Test;

public class IntersectionUtilTest {

    @Test
    public void intersection() {

        Vector3d v0 = new Vector3d(-1, 0, -1);
        Vector3d v1 = new Vector3d(1, 0, -1);
        Vector3d v2 = new Vector3d(0, 0, 1);

        Vector3d p = new Vector3d(0, 1, 0);
        Vector3d d = new Vector3d(0, -1, 0);

        boolean intersects = IntersectionUtil.rayIntersectsTriangle(p, d, v0, v1, v2);

        assertTrue(intersects);

    }

    @Test
    public void edgeIntersection() {

        Vector3d v0 = new Vector3d(-1, 0, -1);
        Vector3d v1 = new Vector3d(1, 0, -1);
        Vector3d v2 = new Vector3d(0, 0, 1);

        Vector3d p = new Vector3d(0, 1, -1);
        Vector3d d = new Vector3d(0, -1, 0);

        boolean intersects = IntersectionUtil.rayIntersectsTriangle(p, d, v0, v1, v2);

        assertTrue(intersects);
    }

    @Test
    public void behind() {

        Vector3d v0 = new Vector3d(-1, 0, -1);
        Vector3d v1 = new Vector3d(1, 0, -1);
        Vector3d v2 = new Vector3d(0, 0, 1);

        Vector3d p = new Vector3d(0, -0.1, 0);
        Vector3d d = new Vector3d(0, -1, 0);

        boolean intersects = IntersectionUtil.rayIntersectsTriangle(p, d, v0, v1, v2);

        assertFalse(intersects);
    }

    @Test
    public void intersectionDistance() {

        Vector3d v0 = new Vector3d(-1, 0, -1);
        Vector3d v1 = new Vector3d(1, 0, -1);
        Vector3d v2 = new Vector3d(0, 0, 1);

        Vector3d p = new Vector3d(0, 1, 0);
        Vector3d d = new Vector3d(0, -1, 0);

        Double intersects = IntersectionUtil.rayIntersectsTriangleDistance(p, d, v0, v1, v2);

        assertEquals(1d, intersects, 0.00001);

        d = new Vector3d(0, -2, 0);

        intersects = IntersectionUtil.rayIntersectsTriangleDistance(p, d, v0, v1, v2);

        assertEquals(0.5d, intersects, 0.00001);

    }

    @Test
    public void behindDistance() {

        Vector3d v0 = new Vector3d(-1, 0, -1);
        Vector3d v1 = new Vector3d(1, 0, -1);
        Vector3d v2 = new Vector3d(0, 0, 1);

        Vector3d p = new Vector3d(0, -0.1, 0);
        Vector3d d = new Vector3d(0, -1, 0);

        Double intersects = IntersectionUtil.rayIntersectsTriangleDistance(p, d, v0, v1, v2);

        assertNull(intersects);
    }
}
