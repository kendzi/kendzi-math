package kendzi.math.geometry;

import org.joml.Vector2d;
import org.joml.Vector2dc;

public class Triangle2d {

    private final Vector2dc p1;

    private final Vector2dc p2;

    private final Vector2dc p3;

    public Triangle2d(Vector2dc p1, Vector2dc p2, Vector2dc p3) {
        super();
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
    }

    public Vector2dc center() {
        return new Vector2d(this.p1).add(this.p2).add(this.p3).div(3);
    }

    /**
     * @return the p1
     */
    public Vector2dc getP1() {
        return this.p1;
    }

    /**
     * @return the p2
     */
    public Vector2dc getP2() {
        return this.p2;
    }

    /**
     * @return the p3
     */
    public Vector2dc getP3() {
        return this.p3;
    }


}
