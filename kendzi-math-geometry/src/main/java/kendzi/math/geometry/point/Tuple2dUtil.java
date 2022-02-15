package kendzi.math.geometry.point;

import org.joml.Vector2dc;

public final class Tuple2dUtil {

    private Tuple2dUtil() {
        // Hide constructor
    }

    /**
     * Computes the distance between this point and point p1.
     * 
     * @param p0
     * 
     * @param p1
     *            the other point
     * @return
     * @deprecated Use {@link Vector2dc#distance(Vector2dc)} instead
     */
    @Deprecated
    public static double distance(Vector2dc p0, Vector2dc p1) {
        return p0.distance(p1);
    }

}
