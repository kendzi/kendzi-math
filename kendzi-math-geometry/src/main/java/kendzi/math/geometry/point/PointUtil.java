/*
 * This software is provided "AS IS" without a warranty of any kind.
 * You use it on your own risk and responsibility!!!
 *
 * This file is shared under BSD v3 license.
 * See readme.txt and BSD3 file for details.
 *
 */

package kendzi.math.geometry.point;

import org.joml.Vector2d;
import org.joml.Vector2dc;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class PointUtil {
    private PointUtil() {
        // Hide constructor
    }

    /**
     * @see http://en.wikipedia.org/wiki/Transformation_matrix
     * @param pTuple
     * @param pAngle
     * @return
     */
    public static Vector2d rotateCounterClockwise2d(Vector2dc pTuple, double pAngle) {
        double cosA = Math.cos(pAngle);
        double sinA = Math.sin(pAngle);
        return new Vector2d(
                pTuple.x() * cosA - pTuple.y() * sinA,
                pTuple.x() * sinA + pTuple.y() * cosA);
    }

    /**
     * @see http://en.wikipedia.org/wiki/Transformation_matrix
     * @param pTuple
     * @param pAngle
     * @return
     */
    public static Vector2d rotateClockwise2d(Vector2dc pTuple, double pAngle) {
        double cosA = Math.cos(pAngle);
        double sinA = Math.sin(pAngle);
        return new Vector2d(
                pTuple.x() * cosA + pTuple.y() * sinA,
                - pTuple.x() * sinA + pTuple.y() * cosA);
    }

    /**
     * @see http://pl.wikipedia.org/wiki/Elementarne_macierze_transformacji
     * @param pTuple
     * @param pAngle
     * @return
     * @deprecated Use {@link Vector3d#rotateX(double)} instead.
     */
    @Deprecated
    public static Vector3d rotateX3d(Vector3dc pTuple, double pAngle) {
        return pTuple.rotateX(pAngle, new Vector3d());
    }

    /**
     * @see <a href="http://pl.wikipedia.org/wiki/Elementarne_macierze_transformacji>Elementarne_macierze_transformacji</a>
     * @param pVector3dc
     * @param pAngle
     * @return
     * @deprecated Use {@link Vector3d#rotateY(double)} instead.
     */
    @Deprecated
    public static Vector3d rotateY3d(Vector3dc pVector3dc, double pAngle) {
        return pVector3dc.rotateY(pAngle, new Vector3d());
    }

    /**
     * @see <a href="http://pl.wikipedia.org/wiki/Elementarne_macierze_transformacji">Transforms</a>
     * @param pVector3dc
     * @param pAngle
     * @return A <i>new</i> {@link Vector3d} object.
     * @deprecated Use {@link Vector3d#rotateZ(double)} instead.
     */
    @Deprecated
    public static Vector3d rotateZ3d(Vector3dc pVector3dc, double pAngle) {
        return pVector3dc.rotateZ(pAngle, new Vector3d());
    }
}
