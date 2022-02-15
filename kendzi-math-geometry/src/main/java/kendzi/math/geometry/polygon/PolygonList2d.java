/*
 * This software is provided "AS IS" without a warranty of any kind.
 * You use it on your own risk and responsibility!!!
 *
 * This file is shared under BSD v3 license.
 * See readme.txt and BSD3 file for details.
 *
 */

package kendzi.math.geometry.polygon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.joml.Vector2dc;

/**
 * Polygon described by list of points.
 *
 * Polygon is [XXX anti cloak wise]!
 *
 *
 * @author Tomasz Kędziora (kendzi)
 *
 */
public class PolygonList2d {

    /**
     * Points of polygon.
     */
    private List<Vector2dc> points;

    /**
     * Create polygon from list of points.
     *
     * @param pPoints list of points
     */
    public PolygonList2d(List<Vector2dc> pPoints) {
        this.points = pPoints;
    }

    /**
     * Create polygon from  points.
     *
     * @param pPoints points
     */
    public PolygonList2d(Vector2dc ... pPoints) {
        List<Vector2dc> ret = new ArrayList<>(pPoints.length);
        ret.addAll(Arrays.asList(pPoints));

        this.points = ret;
    }

    /**
     * Create empty polygon.
     */
    public PolygonList2d() {
        this(new ArrayList<>());
    }

    /**
     * @return the points
     */
    public List<Vector2dc> getPoints() {
        return this.points;
    }

    /**
     * @param pPoints the points to set
     */
    public void setPoints(List<Vector2dc> pPoints) {
        this.points = pPoints;
    }




    public void union(PolygonList2d pPolygon) {
        // TODO !!!
        //        suma
        throw new RuntimeException("TODO");
    }
    public void difference(PolygonList2d pPolygon) {
        // TODO !!!
        //        roznica
        throw new RuntimeException("TODO");
    }

    public boolean inside(Vector2dc pPoint) {
        // TODO !!!
        throw new RuntimeException("TODO");

    }

    public boolean inside(Vector2dc pPoint, double epsilon) {
        // TODO !!!
        throw new RuntimeException("TODO");
    }

    /**
     * Reverse point order in list
     * 
     * @param polygon
     * @return
     */
    public static List<Vector2dc> reverse(List<Vector2dc> polygon) {
        return PolygonUtil.reverse(polygon);
    }
}
