package kendzi.math.geometry.triangle;

import javax.vecmath.Point3d;

/**
 * Utils for 3d triangles.
 */
public class Triangle3dUtil {

    /**
     * Calculates area of triangle described by vertex point.
     *
     * @see "http://math.stackexchange.com/a/128999"
     * @param a
     *            the point a
     * @param b
     *            the point b
     * @param c
     *            the point c
     * @return area of triangle
     *
     *
     */
    public static double area(Point3d a, Point3d b, Point3d c) {

        double abx = b.x - a.x;
        double aby = b.y - a.y;
        double abz = b.z - a.z;

        double acx = c.x - a.x;
        double acy = c.y - a.y;
        double acz = c.z - a.z;

        double x1 = abx;
        double x2 = aby;
        double x3 = abz;

        double y1 = acx;
        double y2 = acy;
        double y3 = acz;

        return 0.5 * Math.sqrt( //
                pow(x2 * y3 - x3 * y2) + pow(x3 * y1 - x1 * y3) + pow(x1 * y2 - x2 * y1));

    }

    private static double pow(double number) {
        return number * number;
    }
}
