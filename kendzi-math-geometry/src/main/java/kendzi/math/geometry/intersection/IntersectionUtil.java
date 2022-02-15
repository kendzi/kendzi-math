package kendzi.math.geometry.intersection;

import org.joml.Vector3d;
import org.joml.Vector3dc;

import kendzi.math.geometry.point.Vector3dUtil;

/**
 * Intersection util.
 * 
 * @author Tomasz Kedziora (Kendzi)
 *
 */
public class IntersectionUtil {
    private IntersectionUtil() {
        // Hide constructor
    }

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
    public static boolean rayIntersectsTriangle(Vector3dc p, Vector3dc d, Vector3dc v0, Vector3dc v1, Vector3dc v2) {
        double a;
        double f;
        double u;
        double v;

        Vector3dc e1 = Vector3dUtil.fromTo(v0, v1);
        Vector3dc e2 = Vector3dUtil.fromTo(v0, v2);

        Vector3d h = d.cross(e2, new Vector3d());

        a = e1.dot(h);

        if (a > -0.00001 && a < 0.00001) {
            return false;
        }

        f = 1 / a;

        Vector3dc s = Vector3dUtil.fromTo(v0, p);

        u = f * s.dot(h);

        if (u < 0.0 || u > 1.0) {
            return false;
        }

        Vector3d q = s.cross(e1, new Vector3d());

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
    public static Double rayIntersectsTriangleDistance(Vector3dc p, Vector3dc d, Vector3dc v0, Vector3dc v1, Vector3dc v2) {

        double a;
        double f;
        double u;
        double v;

        Vector3dc e1 = Vector3dUtil.fromTo(v0, v1);
        Vector3dc e2 = Vector3dUtil.fromTo(v0, v2);

        Vector3d h = d.cross(e2, new Vector3d());

        a = e1.dot(h);

        if (a > -0.00001 && a < 0.00001) {
            return null;
        }

        f = 1 / a;

        Vector3dc s = Vector3dUtil.fromTo(v0, p);

        u = f * s.dot(h);

        if (u < 0.0 || u > 1.0) {
            return null;
        }

        Vector3d q = s.cross(e1, new Vector3d());

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
