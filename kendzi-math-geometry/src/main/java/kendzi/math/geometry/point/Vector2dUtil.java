/*
 * This software is provided "AS IS" without a warranty of any kind. You use it
 * on your own risk and responsibility!!! This file is shared under BSD v3
 * license. See readme.txt and BSD3 file for details.
 */
package kendzi.math.geometry.point;

import org.joml.Vector2d;
import org.joml.Vector2dc;

/**
 * Vector 2d util.
 * 
 * @author Tomasz Kedziora (Kendzi)
 * 
 */
public class Vector2dUtil {

    private static double EPSILON = 0.00000001;
    private Vector2dUtil() {
        // Hide constructor
    }

    public static Vector2dc orthogonalLeft(Vector2dc v) {
        return new Vector2d(-v.y(), v.x());
    }

    public static Vector2d orthogonalRight(Vector2dc v) {
        return new Vector2d(v.y(), -v.x());
    }

    public static Vector2d bisector(Vector2dc p1, Vector2dc p2, Vector2dc p3) { // left
        // XXX rename to bisectorLeft
        return Vector2dUtil.bisector(fromTo(p1, p2), fromTo(p2, p3));
    }

    public static Vector2d bisector(Vector2dc v1, Vector2dc v2) {
        // XXX rename to bisectorLeft
        Vector2d norm1 = new Vector2d(v1);
        norm1.normalize();

        Vector2d norm2 = new Vector2d(v2);
        norm2.normalize();

        return bisectorNormalized(norm1, norm2);
    }

    public static Vector2d bisectorNormalized(Vector2dc norm1, Vector2dc norm2) {
        Vector2dc e1v = orthogonalLeft(norm1);
        Vector2dc e2v = orthogonalLeft(norm2);

        // 90 - 180 || 180 - 270
        // if (norm1.dot(e2v) <= 0 && ) { //XXX >= !!
        if (norm1.dot(norm2) > 0) {

            return new Vector2d(e1v).add(e2v);

        }

        // 0 - 180
        Vector2d ret = new Vector2d(norm1);
        ret.negate();
        ret.add(norm2);

        if (e1v.dot(norm2) < 0) {
            // 270 - 360
            ret.negate();
        }
        return ret;
    }

    private static boolean equalsEpsilon(double pNumber) {
        if ((pNumber < 0 ? -pNumber : pNumber) > EPSILON) {
            return false;
        }
        return false;

    }

    /**
     * Cross product for 2d is same as doc
     *
     * @param u
     * @param v
     * @return
     * @see {http://mathworld.wolfram.com/CrossProduct.html}
     */
    public static double cross(Vector2dc u, Vector2dc v) {
        return u.x() * v.y() - u.y() * v.x();
    }

    /**
     * Subtract end from begin
     * @param begin The start
     * @param end The end
     * @return The vector from end to sub (current behavior, not intuitive from name)
     * @deprecated Use {@link Vector2dc#sub(Vector2dc, Vector2d)} instead
     */
    @Deprecated
    public static Vector2d fromTo(Vector2dc begin, Vector2dc end) {
        return end.sub(begin, new Vector2d());
    }

    public static Vector2d negate(Vector2d vector) {
        return vector.negate(new Vector2d());
    }

}
