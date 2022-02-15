package kendzi.math.geometry.skeleton.circular;

import org.joml.Vector2dc;

import kendzi.math.geometry.ray.Ray2d;
import kendzi.math.geometry.skeleton.path.FaceNode;

/**
 * @author kendzi
 * 
 */
public class Vertex extends CircularNode {

    private Vector2dc point;

    private double distance;

    private boolean processed;

    private Ray2d bisector;

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

    public Vertex(Vector2dc point, double distance, Ray2d bisector, Edge previousEdge, Edge nextEdge) {
        super();
        this.point = point;
        this.distance = distance;
        this.bisector = bisector;
        this.previousEdge = previousEdge;
        this.nextEdge = nextEdge;

        processed = false;
    }

    public Vertex() {
        // TODO Auto-generated constructor stub
    }

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

    public Vector2dc getPoint() {
        return point;
    }

    public double getDistance() {
        return distance;
    }

    public boolean isProcessed() {
        return processed;
    }

    public Ray2d getBisector() {
        return bisector;
    }

    public Edge getPreviousEdge() {
        return previousEdge;
    }

    public Edge getNextEdge() {
        return nextEdge;
    }

    public FaceNode getLeftFace() {
        return leftFace;
    }

    public FaceNode getRightFace() {
        return rightFace;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }

    @Deprecated
    public void setBisector(Ray2d bisector) {
        this.bisector = bisector;
    }

}