package kendzi.math.geometry.skeleton.events.chains;

public abstract class Chain implements ChainEnds {
    // FIXME
    public abstract ChainType getType();

    public enum ChainType {
        EDGE, CLOSED_EDGE, SPLIT
    }
}