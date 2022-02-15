package kendzi.math.geometry.intersection;

import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import kendzi.math.geometry.point.Vector3dUtil;

/**
 * Intersection util.
 * 
 * @author Tomasz Kedziora (Kendzi)
 *
 */
public class IntersectionUtil {

    /**
     * Check whatever ray intersect triangle.
     *
     * @see <a
     *      href="http://www.lighthouse3d.com/tutorials/maths/ray-triangle-intersection/">lighthouse3d
     *      example</a>
     * @param p
     *            ray start point
     * @param d
     *            ray direction vector
     * @param v0
     *            triangle first point
     * @param v1
     *            triangle second point
     * @param v2
     *            triangle third point
     * @return if ray intersect triangle.
     */
    public static boolean rayIntersectsTriangle(Point3d p, Vector3d d, Point3d v0, Point3d v1, Point3d v2) {

        double a, f, u, v;

        Vector3d e1 = Vector3dUtil.fromTo(v0, v1);
        Vector3d e2 = Vector3dUtil.fromTo(v0, v2);

        Vector3d h = new Vector3d();

        h.cross(d, e2);

        a = e1.dot(h);

        if (a > -0.00001 && a < 0.00001) {
            return false;
        }

        f = 1 / a;

        Vector3d s = Vector3dUtil.fromTo(v0, p);

        u = f * s.dot(h);

        if (u < 0.0 || u > 1.0) {
            return false;
        }

        Vector3d q = new Vector3d();
        q.cross(s, e1);

        v = f * d.dot(q);

        if (v < 0.0 || u + v > 1.0) {
            return false;
        }

        // at this stage we can compute t to find out where
        // the intersection point is on the line
        double t = f * e2.dot(q);
        // t = f * innerProduct(e2, q);

        if (t > 0.00001) {// ray intersection
            return true;

        } else {
            // this means that there is a line intersection
            // but not a ray intersection
            return false;
        }
    }

    /**
     * Check whatever ray intersect triangle. If so return time when it happen.
     * When ray direction vector "d" is normalized, time is equal to distance
     * where ray collide with triangle.
     *
     * @see <a
     *      href="http://www.lighthouse3d.com/tutorials/maths/ray-triangle-intersection/">lighthouse3d
     *      example</a>
     * @param p
     *            ray start point
     * @param d
     *            ray direction vector
     * @param v0
     *            triangle first point
     * @param v1
     *            triangle second point
     * @param v2
     *            triangle third point
     * @return if ray intersect triangle.
     */
    public static Double rayIntersectsTriangleDistance(Point3d p, Vector3d d, Point3d v0, Point3d v1, Point3d v2) {

        double a, f, u, v;
        Vector3d h = new Vector3d();

        Vector3d e1 = Vector3dUtil.fromTo(v0, v1);
        Vector3d e2 = Vector3dUtil.fromTo(v0, v2);

        h.cross(d, e2);

        a = e1.dot(h);

        if (a > -0.00001 && a < 0.00001) {
            return null;
        }

        f = 1 / a;

        Vector3d s = Vector3dUtil.fromTo(v0, p);

        u = f * s.dot(h);

        if (u < 0.0 || u > 1.0) {
            return null;
        }

        Vector3d q = new Vector3d();
        q.cross(s, e1);

        v = f * d.dot(q);

        if (v < 0.0 || u + v > 1.0) {
            return null;
        }

        // at this stage we can compute t to find out where
        // the intersection point is on the line
        double t = f * e2.dot(q);
        // t = f * innerProduct(e2, q);

        if (t > 0.00001) {// ray intersection
            return t;

        } else {
            // this means that there is a line intersection
            // but not a ray intersection
            return null;
        }
    }
}
