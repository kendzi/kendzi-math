package kendzi.math.geometry.skeleton.path;

import kendzi.math.geometry.skeleton.circular.Vertex;

public class FaceNode extends PathQueueNode<FaceNode> {
    public Vertex v;
    boolean border;
    String name;
}