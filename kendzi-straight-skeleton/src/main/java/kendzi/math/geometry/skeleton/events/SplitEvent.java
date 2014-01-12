package kendzi.math.geometry.skeleton.events;

import javax.vecmath.Point2d;

import kendzi.math.geometry.skeleton.Skeleton.EdgeEntry;
import kendzi.math.geometry.skeleton.Skeleton.VertexEntry2;

/**
 * @author kendzi
 * 
 */
public class SplitEvent extends SkeletonEvent {

    public SplitEvent(Point2d point, double distance, VertexEntry2 parent, EdgeEntry oppositeEdge) {
        super(point, distance);
        this.parent = parent;
        this.oppositeEdge = oppositeEdge;
    }

    public EdgeEntry oppositeEdge;

    private VertexEntry2 parent;

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "SplitEvent [v=" + this.v + ", parent=" + (this.parent != null ? this.parent.v : "null") + ", distance="
                + this.distance + "]";
    }

    @Override
    public boolean isObsolete() {
        return parent.processed;
    }

    public EdgeEntry getOppositeEdge() {
        return oppositeEdge;
    }

    public VertexEntry2 getParent() {
        return parent;
    }

    @Override
    public Point2d getPoint() {
        return v;
    }
}