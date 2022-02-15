/*
 * This software is provided "AS IS" without a warranty of any kind.
 * You use it on your own risk and responsibility!!!
 *
 * This file is shared under BSD v3 license.
 * See readme.txt and BSD3 file for details.
 *
 */

package kendzi.math.geometry.point;

import java.util.ArrayList;
import java.util.List;

import org.ejml.simple.SimpleMatrix;
import org.joml.Vector2d;
import org.joml.Vector2dc;

/**
 *
 * @see http://en.wikipedia.org/wiki/Transformation_matrix
 * @see http://pl.wikipedia.org/wiki/Elementarne_macierze_transformacji
 *
 * @author Tomasz Kedziora (Kendzi)
 *
 */
public class TransformationMatrix2d {

    /**
     * @param alpha
     * @return
     */
    public static SimpleMatrix rotX(double alpha) {
        double sinX = Math.sin(alpha);
        double cosX = Math.cos(alpha);

        return new SimpleMatrix(
                new double [][] {
                {1, 0 },
                {0, cosX}
                });
    }
    /**
     * @param alpha
     * @return
     */
    public static SimpleMatrix rotXA(double alpha) {
        double sinX = Math.sin(alpha);
        double cosX = Math.cos(alpha);

        return new SimpleMatrix(
                new double [][] {
                        {1, 0, 0},
                        {0, cosX, 0},
                        {0, 0, 1}
                });
    }

    /**
     * @param alpha
     * @return
     */
    public static SimpleMatrix rotY(double alpha) {
        double sinX = Math.sin(alpha);
        double cosX = Math.cos(alpha);

        return new SimpleMatrix(
                new double [][] {
                        {cosX, 0 },
                        {0, 1 },
                });
    }
    /**
     * @param alpha
     * @return
     */
    public static SimpleMatrix rotYA(double alpha) {
        double sinX = Math.sin(alpha);
        double cosX = Math.cos(alpha);

        return new SimpleMatrix(
                new double [][] {
                        {cosX, 0, 0},
                        {0, 1, 0},
                        {0, 0, 1}
                });
    }

    /**
     * @param alpha
     * @return
     */
    public static SimpleMatrix rotZ(double alpha) {
        double sinX = Math.sin(alpha);
        double cosX = Math.cos(alpha);

        return new SimpleMatrix(
                new double [][] {
                        {cosX, -sinX },
                        {sinX, cosX }
                });
    }
    /**
     * XXX sign of alpha may change !!!
     * @param alpha
     * @return
     */
    public static SimpleMatrix rotZA(double alpha) {
        double sinX = Math.sin(alpha);
        double cosX = Math.cos(alpha);

        return new SimpleMatrix(
                new double [][] {
                        {cosX, -sinX, 0},
                        {sinX, cosX, 0},
                        {0, 0, 1}
                });
    }


    /**
     * @return
     */
    public static SimpleMatrix tranA(double x, double y) {

        return new SimpleMatrix(
                new double [][] {
                        {1, 0, x},
                        {0, 1, y},
                        {0, 0, 1}
                });
    }

    /**
     *
     * @return
     */
    public static SimpleMatrix scaleA(double scaleX, double scaleY) {


        return new SimpleMatrix(
                new double [][] {
                        {scaleX, 0, 0},
                        {0, scaleY, 0},
                        {0, 0, 1}
                });
    }

    /**
     * Transform a point
     * @param pPoint The point or vector to transform
     * @param pSimpleMatrix the matrix to use
     * @param isPoint {@code true} if it is a point
     * @return The transformed point or vector
     */
    public static Vector2dc transform(Vector2dc pPoint, SimpleMatrix pSimpleMatrix, boolean isPoint) {
        SimpleMatrix sm = new SimpleMatrix(
                new double [][] {
                        {pPoint.x()},
                        {pPoint.y()},
                        {isPoint ? 1 : 0}
                });

        SimpleMatrix mult = pSimpleMatrix.mult(sm);

        return new Vector2d(mult.get(0), mult.get(1));
    }

    /** Transform list of points using transformation matrix.
     * @param pList list of points
     * @param transformLocal transformation matrix
     * @return  transformed list of points
     */
    public static List<Vector2dc> transformList(List<Vector2dc> pList, SimpleMatrix transformLocal) {

        List<Vector2dc> list = new ArrayList<>(pList.size());
        for (Vector2dc p : pList) {
           Vector2dc transformed = TransformationMatrix2d.transform(p, transformLocal, true);
           list.add(transformed);
        }
        return list;
    }

    /** Transform array of points using transformation matrix.
     * @param pList array of points
     * @param transformLocal transformation matrix
     * @return  transformed array of points
     */
    public static Vector2dc[] transformArray(Vector2dc[] pList, SimpleMatrix transformLocal) {

        Vector2dc [] list = new Vector2dc[pList.length];
        int i = 0;
        for (Vector2dc p : pList) {
           Vector2dc transformed = TransformationMatrix2d.transform(p, transformLocal, true);
           list[i] = transformed;
           i++;
        }
        return list;
    }
}
