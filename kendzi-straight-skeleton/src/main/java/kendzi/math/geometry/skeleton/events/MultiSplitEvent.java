package kendzi.math.geometry.skeleton.events;

import java.util.List;

import org.joml.Vector2dc;

import kendzi.math.geometry.skeleton.events.chains.Chain;

public class MultiSplitEvent extends SkeletonEvent {

    private List<Chain> chains;

    private boolean obsolete;

    public MultiSplitEvent(Vector2dc point, double distance, List<Chain> chains) {
        super(point, distance);
        this.chains = chains;
    }

    @Override
    public boolean isObsolete() {
        return obsolete;
    }

    public List<Chain> getChains() {
        return chains;
    }
}