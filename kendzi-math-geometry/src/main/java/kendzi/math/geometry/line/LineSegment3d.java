package kendzi.math.geometry.line;

import org.joml.Vector3dc;

public class LineSegment3d {

    Vector3dc begin;
    Vector3dc end;

    public LineSegment3d(Vector3dc begin, Vector3dc end) {
        super();
        this.begin = begin;
        this.end = end;
    }

    /**
     * @return the begin
     */
    public Vector3dc getBegin() {
        return begin;
    }

    /**
     * @param begin the begin to set
     */
    public void setBegin(Vector3dc begin) {
        this.begin = begin;
    }

    /**
     * @return the end
     */
    public Vector3dc getEnd() {
        return end;
    }

    /**
     * @param end the end to set
     */
    public void setEnd(Vector3dc end) {
        this.end = end;
    }

    /**
     * {@inheritDoc}
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "LineSegment2d " + "(" + begin + ", " + end + ")";
    }
}

