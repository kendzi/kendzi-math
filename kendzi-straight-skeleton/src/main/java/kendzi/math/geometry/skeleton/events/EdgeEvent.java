package kendzi.math.geometry.skeleton.events;

import javax.vecmath.Point2d;

import kendzi.math.geometry.skeleton.circular.Vertex;

public class EdgeEvent extends SkeletonEvent {

    public Vertex Va;
    public Vertex Vb;

    public EdgeEvent(Point2d point, double distance, Vertex previousVertex, Vertex nextVertex) {
        super(point, distance);
        Va = previousVertex;
        Vb = nextVertex;
    }

    public Vertex getLeftVertex() {
        return Va;
    }

    public Vertex getRightVertex() {
        return Vb;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "EdgeEvent [v=" + this.v + ", Va=" + (this.Va != null ? this.Va.point : "null") + ", Vb="
                + (this.Vb != null ? this.Vb.point : "null") + ", distance=" + this.distance + "]";
    }

    @Override
    public boolean isObsolete() {
        return Va.processed || Vb.processed;
    }
}