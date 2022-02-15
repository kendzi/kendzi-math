/*
 * This software is provided "AS IS" without a warranty of any kind.
 * You use it on your own risk and responsibility!!!
 *
 * This file is shared under BSD v3 license.
 * See readme.txt and BSD3 file for details.
 *
 */

package kendzi.math.geometry.ray;

import org.joml.Vector2d;
import org.joml.Vector2dc;

/**
 * Operations on rays.
 * 
 * @author Tomasz Kedziora (Kendzi)
 * 
 */
public class RayUtil {

    /**
     * Error epsilon. Anything that avoids division.
     */
    static final double SMALL_NUM = 0.00000001;

    /**
     * Return value if there is no intersection.
     */
    static final IntersectPoints EMPTY = new IntersectPoints();


    private RayUtil() {

    }

    /**
     * Test if point is on ray with epsilon.
     * 
     * @param point
     *            point
     * @param ray
     *            ray
     * @return is point on ray
     */
    public static boolean isPointOnRay(Vector2dc point, Ray2d ray, double epsilon) {

        Vector2d rayDirection = new Vector2d(ray.U);
        rayDirection.normalize();
        // test if point is on ray
        Vector2d pointVector = new Vector2d(point);
        pointVector.sub(ray.A);

        double dot = rayDirection.dot(pointVector);

        if (dot < epsilon) {
            return false;
        }
        double x = rayDirection.x;
        rayDirection.x = rayDirection.y;
        rayDirection.y = -x;

        dot = rayDirection.dot(pointVector);

        return -epsilon < dot && dot < epsilon;
    }

    /**
     * Calculate intersection points for rays. It can return more then one
     * intersection point when rays overlaps.
     * 
     * 
     * @see http://geomalgorithms.com/a05-_intersect-1.html
     * @see http://softsurfer.com/Archive/algorithm_0102/algorithm_0102.htm
     * 
     * @param r1
     *            first ray
     * @param r2
     *            second ray
     * @return class with intersection points. It never return null.
     */
    public static IntersectPoints intersectRays2d(Ray2d r1, Ray2d r2) {

        Vector2dc s1p0 = r1.A;
        Vector2d s1p1 = new Vector2d(r1.A).add(r1.U);

        Vector2dc s2p0 = r2.A;
        Vector2d s2p1 = new Vector2d(r2.A).add(r2.U);

        Vector2dc u = r1.U;
        Vector2dc v = r2.U;

        Vector2d w = s1p0.sub(s2p0, new Vector2d());

        double d = perp(u, v);

        // test if they are parallel (includes either being a point)
        if (Math.abs(d) < SMALL_NUM) { // S1 and S2 are parallel
            if (perp(u, w) != 0 || perp(v, w) != 0) {
                // return 0;
                // they are NOT collinear
                return EMPTY;
            }
            // they are collinear or degenerate
            // check if they are degenerate points
            double du = dot(u, u);
            double dv = dot(v, v);
            if (du == 0 && dv == 0) {
                // both segments are points
                if (s1p0 != s2p0) {

                    // return 0;
                    return EMPTY;
                }
                Vector2dc I0 = s1p0;
                // they are the same point
                // return 1;
                return new IntersectPoints(I0);
            }
            if (du == 0) {
                // S1 is a single point
                if (!inCollinearRay(s1p0, s2p0, v)) {
                    // return 0;
                    return EMPTY;
                }
                Vector2dc I0 = s1p0;
                // return 1;
                return new IntersectPoints(I0);
            }
            if (dv == 0) {
                // S2 a single point
                if (!inCollinearRay(s2p0, s1p0, u)) {
                    // return 0;
                    return EMPTY;
                }
                Vector2dc I0 = s2p0;
                // return 1;
                return new IntersectPoints(I0);
            }
            // they are collinear segments - get overlap (or not)
            double t0, t1;
            // endpoints of S1 in eqn for S2
            Vector2d w2 = s1p1.sub(s2p0, new Vector2d());
            if (v.x() != 0) {
                t0 = w.x / v.x();
                t1 = w2.x / v.x();
            } else {
                t0 = w.y / v.y();
                t1 = w2.y / v.y();
            }
            if (t0 > t1) {
                // must have t0 smaller than t1
                double t = t0;
                t0 = t1;
                t1 = t; // swap if not
            }
            if (/*t0 > 1 ||*/ t1 < 0) {
                // return 0; // NO overlap
                return EMPTY;
            }

            // clip to min 0
            t0 = t0 < 0 ? 0 : t0;
            // clip to max 1
            //t1 = t1 > 1 ? 1 : t1;

            if (t0 == t1) {
                // intersect is a point
                // I0 = S2_P0 + t0 * v;
                Vector2dc I0 = new Vector2d(v).mul(t0).add(s2p0);
                return new IntersectPoints(I0);
            }

            // they overlap in a valid subsegment

            // I0 = S2_P0 + t0 * v;
            Vector2dc I0 = new Vector2d(v).mul(t0).add(s2p0);

            // I1 = S2_P0 + t1 * v;
            Vector2dc I1 = new Vector2d(v).mul(t1).add(s2p0);

            // return 2;
            return new IntersectPoints(I0, I1);
        }

        // the segments are skew and may intersect in a point
        // get the intersect parameter for S1
        double sI = perp(v, w) / d;
        if (sI < 0/* || sI > 1 */) {
            // return 0;
            return EMPTY;
        }

        // get the intersect parameter for S2
        double tI = perp(u, w) / d;
        if (tI < 0 /* || tI > 1 */) {
            // return 0;
            return EMPTY;
        }

        // I0 = S1_P0 + sI * u; // compute S1 intersect point
        Vector2dc I0 = new Vector2d(u).mul(sI).add(s1p0);
        // return 1;
        return new IntersectPoints(I0);
    }

    // ===================================================================

    private static boolean inCollinearRay(Vector2dc p, Vector2dc rayStart, Vector2dc rayDirection) {
        //        test if point is on ray
        Vector2d collideVector = new Vector2d(p);
        collideVector.sub(rayStart);

        double dot = rayDirection.dot(collideVector);

        if (dot < 0) {
            return false;
        }

        return true;
    }

    // inSegment(): determine if a point is inside a segment
    // Input: a point P, and a collinear segment S
    // Return: 1 = P is inside S
    // 0 = P is not inside S
    static boolean inSegment(Vector2dc P, Vector2dc segmentP0, Vector2dc segmentP1) {

        if (segmentP0.x() != segmentP1.x()) { // S is not vertical
            if (segmentP0.x() <= P.x() && P.x() <= segmentP1.x()) {
                return true;
            }
            if (segmentP0.x() >= P.x() && P.x() >= segmentP1.x()) {
                return true;
            }
        } else { // S is vertical, so test y coordinate
            if (segmentP0.y() <= P.y() && P.y() <= segmentP1.y()) {
                return true;
            }
            if (segmentP0.y() >= P.y() && P.y() >= segmentP1.y()) {
                return true;
            }
        }
        return false;
    }

    private static double dot(Vector2dc u, Vector2dc v) {
        return u.dot(v);
    }

    /**
     * Perp Dot Product.
     * 
     * @param u
     * @param v
     * @return
     */
    private static double perp(Vector2dc u, Vector2dc v) {
        return u.x() * v.y() - u.y() * v.x();
    }

    /**
     * Result of intersection calculation. If rays intersect in one point
     * variable "intersect is setup. if rays overlaps intersect is setup and end
     * of intersection is set in intersectEnd.
     * 
     * @author Tomasz Kedziora (Kendzi)
     * 
     */
    public static class IntersectPoints {

        /**
         * Intersection point or begin of intersection segment.
         */
        private Vector2dc intersect;

        /**
         * Intersection end.
         */
        private Vector2dc intersectEnd;

        public IntersectPoints(Vector2dc intersect, Vector2dc intersectEnd) {
            super();
            this.intersect = intersect;
            this.intersectEnd = intersectEnd;
        }
        public IntersectPoints(Vector2dc intersect) {
            this(intersect, null);
        }
        public IntersectPoints() {
            this(null, null);
        }
        public Vector2dc getIntersect() {
            return intersect;
        }
        public Vector2dc getIntersectEnd() {
            return intersectEnd;
        }
    }

}
