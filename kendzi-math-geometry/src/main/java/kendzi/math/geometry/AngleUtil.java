package kendzi.math.geometry;

import org.joml.Vector2dc;

public class AngleUtil {
    private AngleUtil() {
        // Hide constructor
    }
    private static final double PI_2 = Math.PI * 2;

    public static double angleBetweenOriented(Vector2dc tip1, Vector2dc tail, Vector2dc tip2) {
        double a1 = angle(tail, tip1);
        double a2 = angle(tail, tip2);
        double angDel = a2 - a1;

        if (angDel <= -Math.PI) {
            return angDel + PI_2;
        }
        if (angDel > Math.PI) {
            return angDel - PI_2;
        }
        return angDel;
    }

    public static double angle(Vector2dc p0, Vector2dc p1) {
        // p0.angle(p1) should work. Test with production code.
        double dx = p1.x() - p0.x();
        double dy = p1.y() - p0.y();
        return Math.atan2(dy, dx);
    }

    public static double angle(Vector2dc p) {
        return Math.atan2(p.y(), p.x());
    }
}
