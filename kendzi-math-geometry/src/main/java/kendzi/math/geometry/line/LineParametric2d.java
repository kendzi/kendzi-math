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

import kendzi.math.geometry.point.Vector2dUtil;

/**
 * Geometry line in parametric form.
 *
 *  x = x_A + t * u_x;
 *  y = y_A + t * u_y;
 *  where t in R
 *
 *  TODO
 *
 * @see http://pl.wikipedia.org/wiki/Prosta#R.C3.B3wnanie_w_postaci_kierunkowej
 * @see http://en.wikipedia.org/wiki/Linear_equation
 *
 * @author kendzi
 *
 */
public class LineParametric2d {
    public Vector2dc A;
    public Vector2dc U;

    public LineParametric2d(Vector2dc pA, Vector2dc pU) {
        this.A = pA;
        this.U = pU;
    }

    public LineLinear2d getLinearForm() {

        double x = this.A.x();
        double y = this.A.y();

        double B = -this.U.x();
        double A = this.U.y();

        double C = - (A * x + B * y);
        return new LineLinear2d(A, B, C);
    }

    public boolean isOnLeftSite(Vector2dc point, double epsilon) {
        Vector2dc direction = point.sub(A, new Vector2d());

        Vector2dc ortagonalRight = Vector2dUtil.orthogonalRight(U);

        return ortagonalRight.dot(direction) < epsilon;
    }

    public boolean isOnRightSite(Vector2dc point, double epsilon) {
        Vector2dc direction = point.sub(A, new Vector2d());

        Vector2dc ortagonalRight = Vector2dUtil.orthogonalRight(U);

        return ortagonalRight.dot(direction) > -epsilon;
    }

    @Override
    public String toString() {
        return "LineParametric2d [A=" + A + ", U=" + U + "]";
    }



}
