package kendzi.math.geometry.point;

import org.joml.Vector2d;
import org.joml.Vector2dc;

public class Point2dUtil {
	private Point2dUtil() {
		// Hide constructor
	}

	/**
	 * Subtract a vector from another vector
	 * @param p1 The first vector
	 * @param p2 The vector to subtract from the first vector
	 * @return A new vector
	 * @deprecated Use {@link Vector2dc#sub(Vector2dc, Vector2d)} or {@link Vector2d#sub(Vector2dc)} instead
	 */
	@Deprecated
	public static Vector2dc sub(Vector2dc p1, Vector2dc p2) {
		return p1.sub(p2, new Vector2d());
	}
}
