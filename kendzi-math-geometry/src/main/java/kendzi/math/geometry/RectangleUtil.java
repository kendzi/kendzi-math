/*
 * This software is provided "AS IS" without a warranty of any kind.
 * You use it on your own risk and responsibility!!!
 *
 * This file is shared under BSD v3 license.
 * See readme.txt and BSD3 file for details.
 *
 */

package kendzi.math.geometry;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joml.Vector2d;
import org.joml.Vector2dc;

import kendzi.math.geometry.rectangle.RectanglePointVector2d;

/**
 * Basic function on rectangles.
 * 
 * @author Tomasz Kędziora (Kendzi)
 */
public class RectangleUtil {
    private RectangleUtil() {
        // Hide constructor
    }

    /** Log. */
    @SuppressWarnings("unused")
    private static final Logger log = LogManager.getLogger(RectangleUtil.class);


    /**
     * For given direction vector, calculate smallest rectangle with all points
     * in side.
     * 
     * @param points set of points
     * @param direction direction vector
     * @return smallest rectangle for given direction
     */
    public static RectanglePointVector2d findRectangleContur(List<Vector2dc> points, Vector2dc direction) {

        Vector2d vector = new Vector2d(direction);
        vector.normalize();

        Vector2d orthogonal = new Vector2d(-vector.y, vector.x);

        double minVector = Double.MAX_VALUE;
        double maxVector = -Double.MAX_VALUE;
        double minOrtagonal = Double.MAX_VALUE;
        double maxOrtagonal = -Double.MAX_VALUE;

        for (Vector2dc point : points) {

            double dot = vector.dot(point);
            if (dot < minVector) {
                minVector = dot;
            }
            if (dot > maxVector) {
                maxVector = dot;
            }

            dot = orthogonal.dot(point);
            if (dot < minOrtagonal) {
                minOrtagonal = dot;
            }
            if (dot > maxOrtagonal) {
                maxOrtagonal = dot;
            }
        }

        double height = maxOrtagonal - minOrtagonal;
        double width = maxVector - minVector;

        Vector2dc point = new Vector2d(
                vector.x * minVector + orthogonal.x * minOrtagonal,
                vector.y * minVector + orthogonal.y * minOrtagonal
                );

        return new RectanglePointVector2d(width, height, point, vector, true);
    }

    /**
     * Finds minimal area rectangle containing set of points.
     * 
     * @param points set of points
     * @return vertex of rectangle or null if less then 3 points
     */
    public static RectanglePointVector2d findRectangleContur(List<Vector2dc> points) {

        List<Vector2dc> graham = Graham.grahamScan(points);

        double smalestArea = Double.MAX_VALUE;
        RectanglePointVector2d smalestRectangle = null;

        Vector2dc begin = graham.get(graham.size() - 1);
        for (Vector2dc end : graham) {

            Vector2d direction = new Vector2d(end);
            direction.sub(begin);

            RectanglePointVector2d rectangleContur = findRectangleContur(graham, direction);

            double area = rectangleContur.getHeight() * rectangleContur.getWidth();
            if (area < smalestArea) {
                smalestArea = area;
                smalestRectangle = rectangleContur;
            }

            begin = end;
        }

        return smalestRectangle;
    }
}
