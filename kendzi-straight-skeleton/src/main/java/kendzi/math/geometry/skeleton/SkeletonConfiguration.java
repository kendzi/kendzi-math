package kendzi.math.geometry.skeleton;

public class SkeletonConfiguration {
    private boolean makeOriented = true;

    private boolean useDebugNames = false;

    private boolean debug = false;

    public boolean isMakeOriented() {
        return makeOriented;
    }

    public void setMakeOriented(boolean makeOriented) {
        this.makeOriented = makeOriented;
    }

    public boolean isUseDebugNames() {
        return useDebugNames;
    }

    public void setUseDebugNames(boolean useDebugNames) {
        this.useDebugNames = useDebugNames;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

}
