package kendzi.math.geometry;

import javax.vecmath.Point3d;

/**
 * The triangle.
 *
 * @author Tomasz Kedziora (Kendzi)
 *
 */
public class Triangle3d {

    private Point3d point0;

    private Point3d point1;

    private Point3d point2;

    /**
     * @return the point0
     */
    public Point3d getPoint0() {
        return point0;
    }

    /**
     * @param point0
     *            the point0 to set
     */
    public void setPoint0(Point3d point0) {
        this.point0 = point0;
    }

    /**
     * @return the point1
     */
    public Point3d getPoint1() {
        return point1;
    }

    /**
     * @param point1
     *            the point1 to set
     */
    public void setPoint1(Point3d point1) {
        this.point1 = point1;
    }

    /**
     * @return the point2
     */
    public Point3d getPoint2() {
        return point2;
    }

    /**
     * @param point2
     *            the point2 to set
     */
    public void setPoint2(Point3d point2) {
        this.point2 = point2;
    }

}
