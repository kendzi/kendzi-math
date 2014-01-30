package kendzi.math.geometry.skeleton.path;

import kendzi.math.geometry.skeleton.circular.Vertex;

public class FaceNode extends PathQueueNode<FaceNode> {

    private Vertex vertex;

    public FaceNode(Vertex vertex) {
        super();
        this.vertex = vertex;
    }

    public Vertex getVertex() {
        return vertex;
    }

    public boolean isQueueClosed() {
        FaceQueue fq = getFaceQueue();
        return fq.isClosed();
    }

    public FaceQueue getFaceQueue() {
        return (FaceQueue) list();
    }

    public boolean isQueueUnconnected() {
        FaceQueue fq = getFaceQueue();
        return fq.isUnconnected();
    }

    public void queueClose() {
        getFaceQueue().close();
        // TODO Auto-generated method stub

    }

}