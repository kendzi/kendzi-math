/*
 * This software is provided "AS IS" without a warranty of any kind.
 * You use it on your own risk and responsibility!!!
 *
 * This file is shared under BSD v3 license.
 * See readme.txt and BSD3 file for details.
 *
 */

package kendzi.math.geometry;

import org.joml.Vector2dc;

public class Quadrilateral2d {
    private Vector2dc p1;
    private Vector2dc p2;
    private Vector2dc p3;
    private Vector2dc p4;

    /**
     * @return the p1
     */
    public Vector2dc getP1() {
        return this.p1;
    }

    /**
     * @param p1 the p1 to set
     */
    public void setP1(Vector2dc p1) {
        this.p1 = p1;
    }

    /**
     * @return the p2
     */
    public Vector2dc getP2() {
        return this.p2;
    }

    /**
     * @param p2 the p2 to set
     */
    public void setP2(Vector2dc p2) {
        this.p2 = p2;
    }

    /**
     * @return the p3
     */
    public Vector2dc getP3() {
        return this.p3;
    }

    /**
     * @param p3 the p3 to set
     */
    public void setP3(Vector2dc p3) {
        this.p3 = p3;
    }
    /**
     * @return the p4
     */
    public Vector2dc getP4() {
        return this.p4;
    }

    /**
     * @param p4 the p4 to set
     */
    public void setP4(Vector2dc p4) {
        this.p4 = p4;
    }
}
