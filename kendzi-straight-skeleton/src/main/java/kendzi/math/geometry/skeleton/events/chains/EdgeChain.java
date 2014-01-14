package kendzi.math.geometry.skeleton.events.chains;

import java.util.List;

import kendzi.math.geometry.skeleton.circular.Edge;
import kendzi.math.geometry.skeleton.circular.Vertex;
import kendzi.math.geometry.skeleton.events.EdgeEvent;

public class EdgeChain extends Chain {
    private boolean closed;
    @Deprecated
    private boolean split;
    private List<EdgeEvent> edgeList;
    private Edge oppositeEdge;

    public EdgeChain(List<EdgeEvent> edgeList) {
        this.edgeList = edgeList;
        closed = getPreviousVertex() == getNextVertex();
    }

    public boolean isClosed() {
        return closed;
    }

    public boolean isSplit() {
        return split;
    }

    public void setSplit(boolean split) {
        this.split = split;
    }

    public List<EdgeEvent> getEdgeList() {
        return edgeList;
    }

    public void setEdgeList(List<EdgeEvent> edgeList) {
        this.edgeList = edgeList;
    }

    public Edge getOppositeEdge() {
        return oppositeEdge;
    }

    public void setOppositeEdge(Edge oppositeEdge) {
        this.oppositeEdge = oppositeEdge;
    }

    @Override
    public Edge getPreviousEdge() {
        return edgeList.get(0).Va.previousEdge;
    }

    @Override
    public Edge getNextEdge() {
        return edgeList.get(edgeList.size() - 1).Vb.nextEdge;
    }

    @Override
    public Vertex getPreviousVertex() {
        return edgeList.get(0).Va;
    }

    @Override
    public Vertex getNextVertex() {
        return edgeList.get(edgeList.size() - 1).Vb;
    }

    @Override
    public Vertex getCurrentVertex() {
        return null;
    }

    @Override
    public ChainType getType() {
        if (closed && split) {
            throw new RuntimeException("chain can't be closed and split");
        }
        if (closed) {
            return ChainType.CLOSED_EDGE;
        }
        if (split) {
            return ChainType.SPLIT;
        }
        return ChainType.EDGE;
    }
}