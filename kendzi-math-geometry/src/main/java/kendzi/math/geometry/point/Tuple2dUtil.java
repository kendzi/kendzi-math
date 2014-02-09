package kendzi.math.geometry.point;

import javax.vecmath.Tuple2d;

public final class Tuple2dUtil {

    private Tuple2dUtil() {
        //
    }

    /**
     * Computes the distance between this point and point p1.
     * 
     * @param p0
     * 
     * @param p1
     *            the other point
     * @return
     */
    public static double distance(Tuple2d p0, Tuple2d p1) {
        double dx, dy;

        dx = p0.x - p1.x;
        dy = p0.y - p1.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

}
