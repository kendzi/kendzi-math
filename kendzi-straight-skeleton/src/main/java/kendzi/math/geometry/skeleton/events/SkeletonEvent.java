package kendzi.math.geometry.skeleton.events;

import org.joml.Vector2dc;

/**
 * @author kendzi
 * 
 */
public abstract class SkeletonEvent {

    public Vector2dc v;

    protected double distance;

    public abstract boolean isObsolete();

    public SkeletonEvent(Vector2dc point, double distance) {
        super();
        this.v = point;
        this.distance = distance;
    }

    public Vector2dc getPoint() {
        return v;
    }

    public double getDistance() {
        return distance;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "IntersectEntry [v=" + this.v + ", distance=" + this.distance + "]";
    }
}