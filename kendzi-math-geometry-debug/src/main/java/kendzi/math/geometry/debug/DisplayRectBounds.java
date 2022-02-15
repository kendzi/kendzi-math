package kendzi.math.geometry.debug;

import java.util.List;

import org.joml.Vector2dc;

public class DisplayRectBounds {
    double minX = Double.MAX_VALUE;
    double minY = Double.MAX_VALUE;
    double maxX = -Double.MAX_VALUE;
    double maxY = -Double.MAX_VALUE;

    boolean isBound = false;


    public void addPoint(Vector2dc p) {
        if (this.minX > p.x()) {
            this.minX = p.x();
        }
        if (this.minY > p.y()) {
            this.minY = p.y();
        }

        if (this.maxX < p.x()) {
            this.maxX = p.x();
        }
        if (this.maxY < p.y()) {
            this.maxY = p.y();
        }

        isBound = true;
    }

    public void addList(List<? extends Vector2dc> list) {
        if (list == null ) {
            return;
        }

        for (Vector2dc p : list) {
            addPoint(p);
        }
    }

    public DisplayRectBounds toBount() {
        if (this.isBound) {
            return this;
        }
        return null;
    }

}

