/*
 * This software is provided "AS IS" without a warranty of any kind. You use it
 * on your own risk and responsibility!!! This file is shared under BSD v3
 * license. See readme.txt and BSD3 file for details.
 */
package kendzi.math.geometry.bbox;

import java.util.List;

import org.joml.Vector2d;
import org.joml.Vector2dc;

/**
 * BBox for 2d.
 *
 * @author Tomasz Kedziora (Kendzi)
 *
 */
public class Bbox2d {

    /**
     * Minimal value of x.
     */
    private double xMin;

    /**
     * Maximal value of x.
     */
    private double xMax;

    /**
     * Minimal value of y.
     */
    private double yMin;

    /**
     * Maximal value of y.
     */
    private double yMax;

    /**
     * Constructor.
     */
    public Bbox2d() {
        xMin = Double.POSITIVE_INFINITY;
        xMax = Double.NEGATIVE_INFINITY;
        yMin = Double.POSITIVE_INFINITY;
        yMax = Double.NEGATIVE_INFINITY;
    }

    /**
     * Constructor from list of points.
     *
     * @param points
     *            points in bbox.
     */
    public Bbox2d(List<Vector2dc> points) {
        this();

        addPoints(points);
    }

    /**
     * Adds point to bbox.
     *
     * @param x
     *            x value of point
     * @param y
     *            y value of point
     */
    public void addPoint(double x, double y) {

        xMin = Math.min(xMin, x);
        xMax = Math.max(xMax, x);

        yMin = Math.min(yMin, y);
        yMax = Math.max(yMax, y);
    }

    /**
     * Adds point to bbox.
     *
     * @param point
     *            point
     */
    public void addPoint(Vector2dc point) {

        xMin = Math.min(xMin, point.x());
        xMax = Math.max(xMax, point.x());

        yMin = Math.min(yMin, point.y());
        yMax = Math.max(yMax, point.y());
    }

    /**
     * Adds points to bbox.
     *
     * @param points
     *            points
     */
    public void addPoints(List<Vector2dc> points) {
        points.forEach(this::addPoint);
    }

    /**
     * Checks if given point is inside bbox.
     *
     * @param point
     *            checked point
     * @return if point is inside bbox
     */
    public boolean isInside(Vector2dc point) {
        return point.x() >= xMin && point.x() <= xMax && point.y() >= yMin && point.y() <= yMax;
    }

    /**
     * Check if at least one point was added to bbox. If it is limiting space.
     *
     * @return if bbox is limiting space
     */
    public boolean isLimited() {
        return xMin != Double.POSITIVE_INFINITY;
    }

    @Override
    public String toString() {
        return "Bbox2d [xMin=" + xMin + ", xMax=" + xMax + ", yMin=" + yMin + ", yMax=" + yMax + "]";
    }

    /**
     * @return the xMin
     */
    public double getxMin() {
        return xMin;
    }

    /**
     * @param xMin
     *            the xMin to set
     */
    public void setxMin(double xMin) {
        this.xMin = xMin;
    }

    /**
     * @return the xMax
     */
    public double getxMax() {
        return xMax;
    }

    /**
     * @param xMax
     *            the xMax to set
     */
    public void setxMax(double xMax) {
        this.xMax = xMax;
    }

    /**
     * @return the yMin
     */
    public double getyMin() {
        return yMin;
    }

    /**
     * @param yMin
     *            the yMin to set
     */
    public void setyMin(double yMin) {
        this.yMin = yMin;
    }

    /**
     * @return the yMax
     */
    public double getyMax() {
        return yMax;
    }

    /**
     * @param yMax
     *            the yMax to set
     */
    public void setyMax(double yMax) {
        this.yMax = yMax;
    }

    /**
     * Gets maximal point.
     *
     * @return maximal point
     */
    public Vector2dc getMinPoint() {
        return new Vector2d(xMin, yMin);
    }

    /**
     * Gets minimal point.
     *
     * @return minimal point
     */
    public Vector2dc getMaxPoint() {
        return new Vector2d(xMax, yMax);
    }

}
