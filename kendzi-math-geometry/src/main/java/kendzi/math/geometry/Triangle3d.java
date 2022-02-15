package kendzi.math.geometry;

import org.joml.Vector3dc;

/**
 * The triangle.
 *
 * @author Tomasz Kedziora (Kendzi)
 *
 */
public class Triangle3d {

    private Vector3dc point0;

    private Vector3dc point1;

    private Vector3dc point2;

    /**
     * Triangle constructor.
     * 
     * @param point0
     *            point 0
     * @param point1
     *            point 1
     * @param point2
     *            point 2
     */
    public Triangle3d(Vector3dc point0, Vector3dc point1, Vector3dc point2) {
        this.point0 = point0;
        this.point1 = point1;
        this.point2 = point2;
    }

    /**
     * @return the point0
     */
    public Vector3dc getPoint0() {
        return point0;
    }

    /**
     * @param point0
     *            the point0 to set
     */
    public void setPoint0(Vector3dc point0) {
        this.point0 = point0;
    }

    /**
     * @return the point1
     */
    public Vector3dc getPoint1() {
        return point1;
    }

    /**
     * @param point1
     *            the point1 to set
     */
    public void setPoint1(Vector3dc point1) {
        this.point1 = point1;
    }

    /**
     * @return the point2
     */
    public Vector3dc getPoint2() {
        return point2;
    }

    /**
     * @param point2
     *            the point2 to set
     */
    public void setPoint2(Vector3dc point2) {
        this.point2 = point2;
    }

}
