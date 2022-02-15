/*
 * This software is provided "AS IS" without a warranty of any kind.
 * You use it on your own risk and responsibility!!!
 *
 * This file is shared under BSD v3 license.
 * See readme.txt and BSD3 file for details.
 *
 */

package kendzi.math.geometry.rectangle;

import org.joml.Vector2d;
import org.joml.Vector2dc;

/**
 * Rectangle made from point, vector, width, height.
 * 
 * @author Tomasz Kędziora (Kendzi)
 */
public class RectanglePointVector2d {
    private double width;
    private double height;
    private Vector2dc point;
    private Vector2d vector;

    public RectanglePointVector2d(double width, double height, Vector2dc point, Vector2d vector) {
        this(width, height, point, vector, false);
    }

    public RectanglePointVector2d(double width, double height, Vector2dc point, Vector2dc vector, boolean normalized) {
        super();
        this.width = width;
        this.height = height;
        this.point = new Vector2d(point);
        this.vector = new Vector2d(vector);

        if (!normalized) {
            this.vector.normalize();
        }
    }

    /**
     * @return the width
     */
    public double getWidth() {
        return width;
    }

    /**
     * @param width the width to set
     */
    public void setWidth(double width) {
        this.width = width;
    }

    /**
     * @return the height
     */
    public double getHeight() {
        return height;
    }

    /**
     * @param height the height to set
     */
    public void setHeight(double height) {
        this.height = height;
    }

    /**
     * @return the point
     */
    public Vector2dc getPoint() {
        return point;
    }

    /**
     * @param point the point to set
     */
    public void setPoint(Vector2dc point) {
        this.point = point;
    }

    /**
     * @return the vector
     */
    public Vector2d getVector() {
        return vector;
    }

    /**
     * @param vector the vector to set
     */
    public void setVector(Vector2d vector) {
        this.vector = vector;
    }
}
