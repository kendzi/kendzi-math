package kendzi.math.geometry.skeleton.events;

import javax.vecmath.Point2d;

import kendzi.math.geometry.skeleton.circular.Vertex;

public class EdgeEvent extends SkeletonEvent {

    private Vertex previousVertex;
    private Vertex nextVertex;

    public EdgeEvent(Point2d point, double distance, Vertex previousVertex, Vertex nextVertex) {
        super(point, distance);
        this.previousVertex = previousVertex;
        this.nextVertex = nextVertex;
    }

    public Vertex getPreviousVertex() {
        return previousVertex;
    }

    public Vertex getNextVertex() {
        return nextVertex;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "EdgeEvent [v=" + this.v + ", previousVertex="
                + (this.previousVertex != null ? this.previousVertex.getPoint() : "null") + ", nextVertex="
                + (this.nextVertex != null ? this.nextVertex.getPoint() : "null") + ", distance=" + this.distance + "]";
    }

    @Override
    public boolean isObsolete() {
        return previousVertex.isProcessed() || nextVertex.isProcessed();
    }
}