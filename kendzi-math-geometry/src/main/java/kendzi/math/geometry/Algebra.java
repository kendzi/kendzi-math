/*
 * This software is provided "AS IS" without a warranty of any kind.
 * You use it on your own risk and responsibility!!!
 *
 * This file is shared under BSD v3 license.
 * See readme.txt and BSD3 file for details.
 *
 */

package kendzi.math.geometry;

import java.awt.geom.Point2D.Double;

import org.joml.Vector2d;
import org.joml.Vector2dc;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class Algebra {
	private Algebra() {
		// Hide constructor
	}

    @Deprecated
	public static Double projectPointPerpendicularToLine(Double lineA, Double lineB, Double point) {

		double sqr1 = lineA.x - lineB.x;
		double sqr2 = lineA.y - lineB.y;
		double u = ((point.x - lineA.x) * (lineB.x - lineA.x) + (point.y - lineA.y) * (lineB.y - lineA.y)) / (sqr1 * sqr1 + sqr2 * sqr2);

		return new Double(
				(lineA.x + (lineB.x - lineA.x) * u),
				(lineA.y + (lineB.y - lineA.y) * u));
	}

    @Deprecated
	public static Vector2dc projectPointPerpendicularToLine(Vector2dc lineA, Vector2dc lineB, Vector2dc point) {

	    double sqr1 = lineA.x() - lineB.x();
	    double sqr2 = lineA.y() - lineB.y();
	    double u = ((point.x() - lineA.x()) * (lineB.x() - lineA.x()) + (point.y() - lineA.y()) * (lineB.y() - lineA.y())) / (sqr1 * sqr1 + sqr2 * sqr2);

	    return new Vector2d(
	            (lineA.x() + (lineB.x() - lineA.x()) * u),
	            (lineA.y() + (lineB.y() - lineA.y()) * u));
	}

    /**
     * <code>
     * <pre>
     *
     *  unitVector
     *    ^
     *    |
     *    |
     *    + -- ^ vectorToProject
     *    |   /
     *    |  /
     *    | /
     *    |/
     *    0
     * </pre>
     * </code>
     *
     *
     * @see http://en.wikipedia.org/wiki/Vector_projection
     * @param unitVector
     * @param vectorToProject
     * @return
     */
   public static Vector2d orthogonalProjection(Vector2dc unitVector, Vector2dc vectorToProject) {
       Vector2d n = new Vector2d(unitVector).normalize();

       double px = vectorToProject.x();
       double py = vectorToProject.y();

       double ax = n.x();
       double ay = n.y();

       return new Vector2d(
               px * ax * ax   + py * ax * ay,
               px * ax * ay   + py * ay * ay
            );
   }


	/**
	 *
	 * @see http://en.wikipedia.org/wiki/Vector_projection
	 * @param unitVector
	 * @param vectorToProject
	 * @return
	 */
	public static Vector3dc orthogonalProjection(Vector3dc unitVector, Vector3dc vectorToProject) {
	    Vector3d n = new Vector3d(unitVector);
	    n.normalize();

	    double px = vectorToProject.x();
	    double py = vectorToProject.y();
	    double pz = vectorToProject.z();

	    double ax = n.x;
	    double ay = n.y;
	    double az = n.z;

	    return new Vector3d(
	            px * ax * ax   + py * ax * ay  + pz * ax * az,
	            px * ax * ay   + py * ay * ay  + pz * ay * az,
	            px * ax * az   + py * ay * az  + pz * az * az
	            );

	}

}
