/*
 * This software is provided "AS IS" without a warranty of any kind.
 * You use it on your own risk and responsibility!!!
 *
 * This file is shared under BSD v3 license.
 * See readme.txt and BSD3 file for details.
 *
 */

package kendzi.math.geometry.line;

import org.joml.Vector2d;
import org.joml.Vector2dc;

/**
 *
 * Geometry line in two point form. Form:
 *
 * x = x_A + t(x_B - x_A) y = y_A + t(y_B - y_A)
 *
 * Similar to Parametric form.
 *
 * TODO
 *
 * @see "http://pl.wikipedia.org/wiki/Prosta"
 * @see "http://en.wikipedia.org/wiki/Linear_equation"
 * @author Tomasz Kedziora (Kendzi)
 *
 */
public class LinePoints2d {
    /**
     * Start point A. XXX rename to A ?
     */
    Vector2dc p1;
    /**
     * End point B. XXX rename to B ?
     */
    Vector2dc p2;

    public LinePoints2d(Vector2dc p1, Vector2dc p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    public Vector2dc getP1() {
        return this.p1;
    }

    public void setP1(Vector2dc p1) {
        this.p1 = p1;
    }

    public Vector2dc getP2() {
        return this.p2;
    }

    public void setP2(Vector2dc p2) {
        this.p2 = p2;
    }

    /**
     * Starting Point A from parametric description.
     *
     * @return starting point A.
     */
    Vector2dc getPointA() {
        return this.p1;
    }

    /**
     * Direction vector U from parametric description.
     *
     * @return direction vector U.
     */
    Vector2dc getVectorU() {
        return this.p2.sub(this.p1, new Vector2d());
    }

    public LineParametric2d getLineParametric2d() {
        return new LineParametric2d(getPointA(), getVectorU());
    }


    /** Determinate if point is over line or on line.
     * @param pPoint point
     * @return point is over line or on line
     * TODO RENAME TO POINT_IN_FRONT
     */
    public boolean inFront(Vector2dc pPoint) {
        return LineUtil.matrixDet(this.p1, this.p2, pPoint) >= 0;
    }

    /**
     * {@inheritDoc}
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "L (" + this.p1 + ") -> (" + this.p2 + ")";
    }




}
