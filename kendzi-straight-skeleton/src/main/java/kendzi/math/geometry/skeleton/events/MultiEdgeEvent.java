package kendzi.math.geometry.skeleton.events;

import javax.vecmath.Point2d;

import kendzi.math.geometry.skeleton.events.chains.EdgeChain;

public class MultiEdgeEvent extends SkeletonEvent {
    private EdgeChain chain;
    private boolean obsolete;

    public MultiEdgeEvent(Point2d point, double distance, EdgeChain chain) {
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