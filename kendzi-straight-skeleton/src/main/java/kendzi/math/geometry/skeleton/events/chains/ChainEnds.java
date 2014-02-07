package kendzi.math.geometry.skeleton.events.chains;

import kendzi.math.geometry.skeleton.circular.Edge;
import kendzi.math.geometry.skeleton.circular.Vertex;

public interface ChainEnds {

    public Edge getPreviousEdge();

    public Edge getNextEdge();

    public Vertex getPreviousVertex();

    public Vertex getNextVertex();

    public Vertex getCurrentVertex();
}