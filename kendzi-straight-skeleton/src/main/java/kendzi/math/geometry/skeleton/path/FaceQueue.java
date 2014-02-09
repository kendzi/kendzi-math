package kendzi.math.geometry.skeleton.path;

import kendzi.math.geometry.skeleton.circular.Edge;

public class FaceQueue extends PathQueue<FaceNode> {

    /**
     * Edge for given queue.
     */
    private Edge edge;

    /**
     * Flag if queue is closed. After closing can't be modify.
     */
    private boolean closed;

    @Override
    public void addPush(PathQueueNode<FaceNode> node, PathQueueNode<FaceNode> newNode) {
        if (closed) {
            throw new IllegalStateException("can't add node to closed FaceQueue");
        }
        super.addPush(node, newNode);
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

    /**
     * Flag if queue is connected to edges.
     */
    public boolean isUnconnected() {
        return edge == null;
    }

    /**
     * Flag if queue is closed. After closing can't be modify.
     */
    public boolean isClosed() {
        return closed;
    }

    /**
     * Mark queue as closed. After closing can't be modify.
     */
    public void close() {
        this.closed = true;
    }
}