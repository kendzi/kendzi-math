package kendzi.math.geometry.skeleton.events.chains;

import kendzi.math.geometry.skeleton.circular.Edge;
import kendzi.math.geometry.skeleton.circular.Vertex;

public class SingleEdgeChain extends Chain {

    private Edge oppositeEdge;

    private Vertex nextVertex;

    private Vertex previousVertex;

    public SingleEdgeChain(Edge oppositeEdge, Vertex nextVertex) {
        this.oppositeEdge = oppositeEdge;
        this.nextVertex = nextVertex;
        /*
         * previous vertex for opposite edge event is valid only before
         * processing of multi split event start. We need to store vertex before
         * processing starts.
         */
        this.previousVertex = nextVertex.previous();
    }

    @Override
    public Edge getPreviousEdge() {
        return oppositeEdge;
    }

    @Override
    public Edge getNextEdge() {
        return oppositeEdge;
    }

    @Override
    public Vertex getPreviousVertex() {
        return previousVertex;
    }

    @Override
    public Vertex getNextVertex() {
        return nextVertex;
    }

    @Override
    public Vertex getCurrentVertex() {
        return null;
    }

    @Override
    public ChainType getType() {
        return ChainType.SPLIT;
    }
}
