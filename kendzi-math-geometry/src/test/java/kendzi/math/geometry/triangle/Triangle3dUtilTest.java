package kendzi.math.geometry.triangle;

import static org.junit.Assert.assertEquals;

import org.joml.Vector3d;
import org.junit.Test;

public class Triangle3dUtilTest {

    private static final double EPSILON = 1e-10;

    @Test
    public void area1() {
        Vector3d a = new Vector3d(0, 0, 0);
        Vector3d b = new Vector3d(1, 0, 0);
        Vector3d c = new Vector3d(0, 1, 0);
        double area = Triangle3dUtil.area(a, b, c);

        assertEquals(0.5, area, EPSILON);
    }

    @Test
    public void area2() {
        Vector3d a = new Vector3d(0, 0, 0);
        Vector3d b = new Vector3d(1, 0, 0);
        Vector3d c = new Vector3d(0, 0, 1);
        double area = Triangle3dUtil.area(a, b, c);

        assertEquals(0.5, area, EPSILON);
    }

}
