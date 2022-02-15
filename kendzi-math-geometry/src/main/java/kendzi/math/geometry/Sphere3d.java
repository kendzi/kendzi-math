/*
 * This software is provided "AS IS" without a warranty of any kind.
 * You use it on your own risk and responsibility!!!
 *
 * This file is shared under BSD v3 license.
 * See readme.txt and BSD3 file for details.
 *
 */
package kendzi.math.geometry;

import org.joml.Vector3d;
import org.joml.Vector3dc;

/**
 * Shape geometry.
 *
 * @author Tomasz Kędziora (Kendzi)
 */
public class Sphere3d {
    private Vector3dc center;
    private double radius;


    public Sphere3d() {
        this(new Vector3d(), 1);
    }

    public Sphere3d(Vector3dc center, double radius) {
        super();
        this.center = center;
        this.radius = radius;
    }


    /**
     * @return the center
     */
    public Vector3dc getCenter() {
        return center;
    }
    /**
     * @param center the center to set
     */
    public void setCenter(Vector3dc center) {
        this.center = center;
    }
    /**
     * @return the radius
     */
    public double getRadius() {
        return radius;
    }
    /**
     * @param radius the radius to set
     */
    public void setRadius(double radius) {
        this.radius = radius;
    }



}
