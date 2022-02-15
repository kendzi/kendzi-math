package kendzi.math.geometry.skeleton.events;

import org.joml.Vector2dc;

import kendzi.math.geometry.skeleton.circular.Edge;
import kendzi.math.geometry.skeleton.circular.Vertex;

/**
 * @author kendzi
 * 
 */
public class VertexSplitEvent extends SplitEvent {

    public VertexSplitEvent(Vector2dc point, double distance, Vertex parent) {
        super(point, distance, parent, null);
    }

    public Edge getOppositeEdgePrevious() {
        return oppositeEdge;
    }

    public Edge getOppositeEdgeNext() {
        return oppositeEdge.next();
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "VertexSplitEvent [v=" + this.v + ", parent=" + (this.getParent() != null ? this.getParent().getPoint() : "null")
                + ", distance=" + this.getDistance() + "]";
    }

    @Override
    public Edge getOppositeEdge() {
        throw new RuntimeException("XXX");
    }

}