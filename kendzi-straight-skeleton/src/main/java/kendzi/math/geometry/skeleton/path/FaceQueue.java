package kendzi.math.geometry.skeleton.path;

import kendzi.math.geometry.skeleton.circular.Edge;

public class FaceQueue extends PathQueue<FaceNode> {
    private boolean border;
    private Edge edge;

    /**
     * @return the border
     */
    public boolean isBorder() {
        return border;
    }

    /**
     * @param border
     *            the border to set
     */
    public void setBorder(boolean border) {
        this.border = border;
    }

    /**
     * @return the edge
     */
    public Edge getEdge() {
        return edge;
    }

    /**
     * @param edge
     *            the edge to set
     */
    public void setEdge(Edge edge) {
        this.edge = edge;
    }
}