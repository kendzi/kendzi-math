package kendzi.math.geometry.intersection;

import static org.junit.Assert.*;

import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import org.junit.Test;

public class IntersectionUtilTest {

    @Test
    public void intersection() {

        Point3d v0 = new Point3d(-1, 0, -1);
        Point3d v1 = new Point3d(1, 0, -1);
        Point3d v2 = new Point3d(0, 0, 1);

        Point3d p = new Point3d(0, 1, 0);
        Vector3d d = new Vector3d(0, -1, 0);

        boolean intersects = IntersectionUtil.rayIntersectsTriangle(p, d, v0, v1, v2);

        assertTrue(intersects);

    }

    @Test
    public void edgeIntersection() {

        Point3d v0 = new Point3d(-1, 0, -1);
        Point3d v1 = new Point3d(1, 0, -1);
        Point3d v2 = new Point3d(0, 0, 1);

        Point3d p = new Point3d(0, 1, -1);
        Vector3d d = new Vector3d(0, -1, 0);

        boolean intersects = IntersectionUtil.rayIntersectsTriangle(p, d, v0, v1, v2);

        assertTrue(intersects);
    }

    @Test
    public void behind() {

        Point3d v0 = new Point3d(-1, 0, -1);
        Point3d v1 = new Point3d(1, 0, -1);
        Point3d v2 = new Point3d(0, 0, 1);

        Point3d p = new Point3d(0, -0.1, 0);
        Vector3d d = new Vector3d(0, -1, 0);

        boolean intersects = IntersectionUtil.rayIntersectsTriangle(p, d, v0, v1, v2);

        assertFalse(intersects);
    }

    @Test
    public void intersectionDistance() {

        Point3d v0 = new Point3d(-1, 0, -1);
        Point3d v1 = new Point3d(1, 0, -1);
        Point3d v2 = new Point3d(0, 0, 1);

        Point3d p = new Point3d(0, 1, 0);
        Vector3d d = new Vector3d(0, -1, 0);

        Double intersects = IntersectionUtil.rayIntersectsTriangleDistance(p, d, v0, v1, v2);

        assertEquals(1d, intersects, 0.00001);

        d = new Vector3d(0, -2, 0);

        intersects = IntersectionUtil.rayIntersectsTriangleDistance(p, d, v0, v1, v2);

        assertEquals(0.5d, intersects, 0.00001);

    }

    @Test
    public void behindDistance() {

        Point3d v0 = new Point3d(-1, 0, -1);
        Point3d v1 = new Point3d(1, 0, -1);
        Point3d v2 = new Point3d(0, 0, 1);

        Point3d p = new Point3d(0, -0.1, 0);
        Vector3d d = new Vector3d(0, -1, 0);

        Double intersects = IntersectionUtil.rayIntersectsTriangleDistance(p, d, v0, v1, v2);

        assertNull(intersects);
    }
}
