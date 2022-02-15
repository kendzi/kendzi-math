package kendzi.math.geometry.skeleton.circular;

import org.joml.Vector2d;
import org.joml.Vector2dc;

import kendzi.math.geometry.line.LineLinear2d;
import kendzi.math.geometry.line.LineParametric2d;
import kendzi.math.geometry.ray.Ray2d;

/**
 * @author kendzi
 * 
 */
public class Edge extends CircularNode {

    private Vector2dc begin;
    private Vector2dc end;

    private Ray2d bisectorPrevious;
    private Ray2d bisectorNext;

    private LineLinear2d lineLinear2d;

    private Vector2d norm;

    public Edge(Vector2dc begin, Vector2dc end) {
        this.begin = begin;
        this.end = end;

        this.lineLinear2d = new LineLinear2d(begin, end);

        this.norm = new Vector2d(end);
        this.norm.sub(begin);
        this.norm.normalize();

    }

    @Override
    public Edge next() {
        return (Edge) super.next();
    }

    @Override
    public Edge previous() {
        return (Edge) super.previous();
    }

    public LineParametric2d getLineParametric2d() {
        return new LineParametric2d(begin, norm);
    }

    public LineLinear2d getLineLinear() {
        return this.lineLinear2d;
    }

    public Vector2d getNorm() {
        return this.norm;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "EdgeEntry [p1=" + this.begin + ", p2=" + this.end + "]";
    }

    public Vector2dc getBegin() {
        return begin;
    }

    public Vector2dc getEnd() {
        return end;
    }

    public Ray2d getBisectorPrevious() {
        return bisectorPrevious;
    }

    public Ray2d getBisectorNext() {
        return bisectorNext;
    }

    public LineLinear2d getLineLinear2d() {
        return lineLinear2d;
    }

    public void setBisectorPrevious(Ray2d bisectorPrevious) {
        this.bisectorPrevious = bisectorPrevious;
    }

    public void setBisectorNext(Ray2d bisectorNext) {
        this.bisectorNext = bisectorNext;
    }
}