package kendzi.math.geometry.point;

import org.joml.Vector3d;
import org.joml.Vector3dc;

public class Vector3dUtil {
    private Vector3dUtil() {
        // Hide constructor
    }
    /**
     * Get the vector from a vector to another vector
     * @param from The initial vector
     * @param to the final vector
     * @return The vector between the two
     * @deprecated Use {@link Vector3d#sub(Vector3dc)} ({@code to.sub(from)}) instead.
     */
    @Deprecated
    public static Vector3dc fromTo(Vector3dc from, Vector3dc to) {
        return new Vector3d(to).sub(from);
    }

}
