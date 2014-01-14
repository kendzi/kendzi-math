package kendzi.math.geometry.skeleton.events.chains;

import kendzi.math.geometry.skeleton.circular.Edge;
import kendzi.math.geometry.skeleton.circular.Vertex;
import kendzi.math.geometry.skeleton.events.SplitEvent;
import kendzi.math.geometry.skeleton.events.VertexSplitEvent;

public class SplitChain extends Chain {

    private SplitEvent splitEvent;

    public SplitChain(SplitEvent event) {
        splitEvent = event;
    }

    @Override
    public Edge getPreviousEdge() {
        return splitEvent.getParent().previousEdge;
    }

    @Override
    public Edge getNextEdge() {
        return splitEvent.getParent().nextEdge;
    }

    @Override
    public Vertex getPreviousVertex() {
        return splitEvent.getParent().previous();
    }

    @Override
    public Vertex getNextVertex() {
        return splitEvent.getParent().next();
    }

    @Override
    public Vertex getCurrentVertex() {
        return splitEvent.getParent();
    }

    public SplitEvent getSplitEvent() {
        return splitEvent;
    }

    public Edge getOppositeEdge() {
        if (!(splitEvent instanceof VertexSplitEvent)) {
            return splitEvent.getOppositeEdge();
        }
        return null;
    }

    @Override
    public ChainType getType() {
        return ChainType.SPLIT;
    }
}