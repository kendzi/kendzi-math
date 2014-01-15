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
}