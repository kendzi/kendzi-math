package kendzi.math.geometry.ray;

import org.joml.Vector3d;
import org.joml.Vector3dc;

public class Ray3d {

    private Vector3dc point;

    private Vector3dc vector;

    public Ray3d() {
        this(new Vector3d(), new Vector3d());
    }

    public Ray3d(Vector3dc point, Vector3dc vector) {
        super();
        this.point = point;
        this.vector = vector;
    }

    /**
     * @return the point
     */
    public Vector3dc getPoint() {
        return point;
    }
    /**
     * @param point the point to set
     */
    public void setPoint(Vector3dc point) {
        this.point = point;
    }
    /**
     * @return the vector
     */
    public Vector3dc getVector() {
        return vector;
    }
    /**
     * @param vector the vector to set
     */
    public void setVector(Vector3dc vector) {
        this.vector = vector;
    }


}
