/*
 * This software is provided "AS IS" without a warranty of any kind.
 * You use it on your own risk and responsibility!!!
 *
 * This file is shared under BSD v3 license.
 * See readme.txt and BSD3 file for details.
 *
 */

package kendzi.math.geometry;

import org.joml.Vector3dc;

/**
 * Plane described by point and normal vector.
 *
 * @see "http://en.wikipedia.org/wiki/Plane_(geometry)"
 *
 * @author Tomasz Kędziora (Kendzi)
 */
public class Plane3d {

    /**
     * Point on plane.
     */
    private Vector3dc point;

    /**
     * Normal vector of plane.
     */
    private Vector3dc normal;

    /** Default constructor.
     * @param point point on plane
     * @param normal normal vector of plane
     */
    public Plane3d(Vector3dc point, Vector3dc normal) {
        this.point = point;
        this.normal = normal;
    }

    /**
     * @return the point
     */
    public Vector3dc getPoint() {
        return this.point;
    }
    /**
     * @param point the point to set
     */
    public void setPoint(Vector3dc point) {
        this.point = point;
    }
    /**
     * @return the normal
     */
    public Vector3dc getNormal() {
        return this.normal;
    }
    /**
     * @param normal the normal to set
     */
    public void setNormal(Vector3dc normal) {
        this.normal = normal;
    }


    /**
     * Calculate Y value on given coordinates X, Z. TODO when point is no exist.
     * 
     * @param x coordinates X
     * @param z coordinates Z
     * @return Y value of plane
     */
    public double calcYOfPlane(double x, double z) {
        double a = this.normal.x();
        double b = this.normal.y();
        double c = this.normal.z();
        double d = -a * this.point.x() - b * this.point.y() - c * this.point.z();

        return (-a * x - c * z - d) / b;
    }

    /**
     * Calculate Z value on given coordinates X, Y. TODO when point is no exist.
     * 
     * @param x coordinates X
     * @param y coordinates Y
     * @return Z value of plane
     */
    public double calcZOfPlane(double x, double y) {
        double a = this.normal.x();
        double b = this.normal.y();
        double c = this.normal.z();
        double d = -a * this.point.x() - b * this.point.y() - c * this.point.z();

        return (-a * x - b * y - d) / c;
    }
}
