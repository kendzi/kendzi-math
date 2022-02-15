package kendzi.math.geometry.line;

import org.joml.Vector3d;
import org.joml.Vector3dc;

/**
 *
 * @author Tomasz Kędziora (Kendzi)
 */
public class Line3dDistanceUtil {


    // anything that avoids division overflow
    final static double EPSILON  =  0.00000001;

    /**
     * Get the dot product of two vectors
     * @param v0 A vector
     * @param v1 Another vector
     * @return The dot product
     * @deprecated Use {@link Vector3d#dot(Vector3dc)} instead
     */
    @Deprecated
    static double dot(Vector3d v0, Vector3d v1) {
        return v0.dot(v1);
    }

    /**
     * Get the difference between two vectors
     * @param t0 The first vector
     * @param t1 The second vector
     * @return The difference between the vectors
     * @deprecated Use {@link Vector3d#sub(Vector3dc)} instead
     */
    @Deprecated
    static Vector3d sub(Vector3dc t0, Vector3dc t1) {
        return t0.sub(t1, new Vector3d());
    }

    /**
     * Get the 3D minimum distance between 2 lines.
     * @see <a href="http://geomalgorithms.com/a07-_distance.html">Distance</a>
     *
     * @param L1 3D lines
     * @param L2 3D lines
     * @return the shortest distance between L1 and L2
     */
    public static double distance(LinePoints3d L1, LinePoints3d L2) {

        Vector3d   u = sub(L1.getP2(), L1.getP1());
        Vector3d   v = sub(L2.getP2(), L2.getP1());
        Vector3d   w = sub(L1.getP1(), L2.getP1());

        // always >= 0
        double    a = dot(u,u);
        double    b = dot(u,v);

        // always >= 0
        double    c = dot(v,v);
        double    d = dot(u,w);
        double    e = dot(v,w);

        // always >= 0
        double    D = a*c - b*b;
        double    sc, tc;

        // compute the line parameters of the two closest points
        if (D < EPSILON) {
            // the lines are almost parallel
            sc = 0.0;
            // use the largest denominator
            tc = (b > c ? d / b : e / c);
        } else {
            sc = (b*e - c*d) / D;
            tc = (a*e - b*d) / D;
        }


        // get the difference of the two closest points
        // dP = L1(sc) - L2(tc)
        // dP = w + (sc * u) - (tc * v)

        u.mul(sc);
        v.mul(tc);

        Vector3d dP = w;
        dP.add(u);
        dP.sub(v);

        // return the closest distance
        return dP.length();
    }
}
