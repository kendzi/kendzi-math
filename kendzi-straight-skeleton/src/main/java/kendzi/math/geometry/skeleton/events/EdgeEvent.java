package kendzi.math.geometry.skeleton.events;

import javax.vecmath.Point2d;

import kendzi.math.geometry.skeleton.Skeleton.VertexEntry2;

public class EdgeEvent extends SkeletonEvent {

    public VertexEntry2 Va;
    public VertexEntry2 Vb;

    public EdgeEvent(Point2d point, double distance, VertexEntry2 previousVertex, VertexEntry2 nextVertex) {
        super(point, distance);
        Va = previousVertex;
        Vb = nextVertex;
    }

    public VertexEntry2 getLeftVertex() {
        return Va;
    }

    public VertexEntry2 getRightVertex() {
        return Vb;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "EdgeEvent [v=" + this.v + ", Va=" + (this.Va != null ? this.Va.v : "null") + ", Vb="
                + (this.Vb != null ? this.Vb.v : "null") + ", distance=" + this.distance + "]";
    }

    @Override
    public boolean isObsolete() {
        return Va.processed || Vb.processed;
    }
}