package kendzi.math.geometry.skeleton.events;

import org.joml.Vector2dc;

import kendzi.math.geometry.skeleton.events.chains.EdgeChain;

public class MultiEdgeEvent extends SkeletonEvent {
    private EdgeChain chain;
    private boolean obsolete;

    public MultiEdgeEvent(Vector2dc point, double distance, EdgeChain chain) {
        super(point, distance);
        this.chain = chain;
    }

    @Override
    public boolean isObsolete() {
        return obsolete;
    }

    public EdgeChain getChain() {
        return chain;
    }
}