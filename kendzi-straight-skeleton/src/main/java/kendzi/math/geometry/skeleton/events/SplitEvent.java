package kendzi.math.geometry.skeleton.events;

import org.joml.Vector2dc;

import kendzi.math.geometry.skeleton.circular.Edge;
import kendzi.math.geometry.skeleton.circular.Vertex;

/**
 * @author kendzi
 * 
 */
public class SplitEvent extends SkeletonEvent {

    public SplitEvent(Vector2dc point, double distance, Vertex parent, Edge oppositeEdge) {
        super(point, distance);
        this.parent = parent;
        this.oppositeEdge = oppositeEdge;
    }

    public Edge oppositeEdge;

    private Vertex parent;

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "SplitEvent [v=" + this.v + ", parent=" + (this.parent != null ? this.parent.getPoint() : "null") + ", distance="
                + this.distance + "]";
    }

    @Override
    public boolean isObsolete() {
        return parent.isProcessed();
    }

    public Edge getOppositeEdge() {
        return oppositeEdge;
    }

    public Vertex getParent() {
        return parent;
    }

    @Override
    public Vector2dc getPoint() {
        return v;
    }
}