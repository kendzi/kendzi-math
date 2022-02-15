package kendzi.math.geometry.triangle;

import static org.junit.Assert.assertEquals;

import javax.vecmath.Point3d;

import org.junit.Test;

public class Triangle3dUtilTest {

    private static final double EPSILON = 1e-10;

    @Test
    public void area1() {
        Point3d a = new Point3d(0, 0, 0);
        Point3d b = new Point3d(1, 0, 0);
        Point3d c = new Point3d(0, 1, 0);
        double area = Triangle3dUtil.area(a, b, c);

        assertEquals(0.5, area, EPSILON);
    }

    @Test
    public void area2() {
        Point3d a = new Point3d(0, 0, 0);
        Point3d b = new Point3d(1, 0, 0);
        Point3d c = new Point3d(0, 0, 1);
        double area = Triangle3dUtil.area(a, b, c);

        assertEquals(0.5, area, EPSILON);
    }

}
