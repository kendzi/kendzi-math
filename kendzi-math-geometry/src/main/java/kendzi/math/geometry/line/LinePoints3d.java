/*
 * This software is provided "AS IS" without a warranty of any kind.
 * You use it on your own risk and responsibility!!!
 *
 * This file is shared under BSD v3 license.
 * See readme.txt and BSD3 file for details.
 *
 */

package kendzi.math.geometry.line;

import org.joml.Vector3d;
import org.joml.Vector3dc;

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
public class LinePoints3d {
    /**
     * Start point A. XXX rename to A ?
     */
    Vector3dc p1;
    /**
     * End point B. XXX rename to B ?
     */
    Vector3dc p2;

    public LinePoints3d(Vector3dc p1, Vector3dc p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    public Vector3dc getP1() {
        return this.p1;
    }

    public void setP1(Vector3dc p1) {
        this.p1 = p1;
    }

    public Vector3dc getP2() {
        return this.p2;
    }

    public void setP2(Vector3dc p2) {
        this.p2 = p2;
    }

    /**
     * Starting Point A from parametric description.
     *
     * @return starting point A.
     */
    Vector3dc getPointA() {
        return this.p1;
    }

    /**
     * Direction vector U from parametric description.
     *
     * @return direction vector U.
     */
    Vector3d getVectorU() {
        return new Vector3d(this.p2).sub(this.p1);
    }




}
