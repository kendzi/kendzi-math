package kendzi.math.geometry.skeleton.events;

import javax.vecmath.Point2d;

/**
 * @author kendzi
 * 
 */
public abstract class SkeletonEvent {

    public Point2d v;

    protected double distance;

    public abstract boolean isObsolete();

    public SkeletonEvent(Point2d point, double distance) {
        super();
        this.v = point;
        this.distance = distance;
    }

    public Point2d getPoint() {
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