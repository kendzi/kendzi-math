package kendzi.math.geometry.skeleton.circular;

import javax.vecmath.Point2d;

import kendzi.math.geometry.ray.Ray2d;
import kendzi.math.geometry.skeleton.path.FaceNode;

/**
 * @author kendzi
 * 
 */
public class Vertex extends CircularNode {

    public Point2d point;

    public double distance;

    public boolean processed;

    public Ray2d bisector;

    /**
     * Previous edge.
     */
    public Edge previousEdge;

    /**
     * Next edge.
     */
    public Edge nextEdge;

    public FaceNode leftFace;

    public FaceNode rightFace;

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "VertexEntry [v=" + this.point + ", processed=" + this.processed + ", bisector=" + this.bisector
                + ", previousEdge=" + this.previousEdge + ", nextEdge=" + this.nextEdge;
    }

    @Override
    public Vertex next() {
        return (Vertex) super.next();
    }

    @Override
    public Vertex previous() {
        return (Vertex) super.previous();
    }
}