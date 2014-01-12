package kendzi.math.geometry.skeleton.events;

import javax.vecmath.Point2d;

import kendzi.math.geometry.skeleton.Skeleton.EdgeEntry;
import kendzi.math.geometry.skeleton.Skeleton.VertexEntry2;

/**
 * @author kendzi
 * 
 */
public class VertexSplitEvent extends SplitEvent {

    public VertexSplitEvent(Point2d point, double distance, VertexEntry2 parent) {
        super(point, distance, parent, null);
    }

    public EdgeEntry getOppositeEdgePrevious() {
        return oppositeEdge;
    }

    public EdgeEntry getOppositeEdgeNext() {
        return oppositeEdge.next();
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "VertexSplitEvent [v=" + this.v + ", parent=" + (this.getParent() != null ? this.getParent().v : "null")
                + ", distance=" + this.getDistance() + "]";
    }

    @Override
    public EdgeEntry getOppositeEdge() {
        throw new RuntimeException("XXX");
    }

}