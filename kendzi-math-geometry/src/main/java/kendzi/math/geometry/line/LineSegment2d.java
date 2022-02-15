package kendzi.math.geometry.line;

import org.joml.Vector2d;
import org.joml.Vector2dc;

import kendzi.math.geometry.point.Vector2dUtil;

public class LineSegment2d {

    Vector2dc begin;
    Vector2dc end;

    /**
     * XXX is need ?
     */
    boolean openBegin;
    /**
     * XXX is need ?
     */
    boolean openEnd;

    public LineSegment2d(Vector2dc begin, Vector2dc end) {
        super();
        this.begin = begin;
        this.end = end;
    }

    /** Collision point of line and line segment.
     * @param x1 line segment begin point x
     * @param y1 line segment begin point y
     * @param x2 line segment end point x
     * @param y2 line segment end point y
     *
     * @param A line in linear form parameter A
     * @param B line in linear form parameter B
     * @param C line in linear form parameter C
     *
     * XXX better name is intersects ?!
     * @return collision point
     */
    public static Vector2dc collide(double x1, double y1, double x2, double y2, double A, double B, double C
            ) {

        // XXX TODO FIXME when end of line segment is lies on line

        if (det(x1, y1, A, B, C) * det(x2, y2, A, B, C) < 0) {

            double A2 = y1 - y2;
            double B2 = x2 - x1;
            double C2 = x1 * y2 - x2 * y1;

            return LineLinear2d.collide(A, B, C, A2, B2, C2);
        }
        return null;
    }

    public Vector2dc intersect(LineSegment2d lineSegment) {
        Vector2dc v1 = Vector2dUtil.fromTo(this.begin, this.end);
        Vector2dc v2 = Vector2dUtil.fromTo(lineSegment.begin, lineSegment.end);

        return LineUtil.intersectLineSegments(this.begin, lineSegment.begin, v1, v2);
    }
    /** Test
     * @param lineSegment
     * @return
     */
    // FIXME
    public Vector2dc intersectOpen(LineSegment2d lineSegment) {
        Vector2dc v1 = Vector2dUtil.fromTo(this.begin, this.end);
        Vector2dc v2 = Vector2dUtil.fromTo(lineSegment.begin, lineSegment.end);

        if (this.begin.equals(lineSegment.begin) || this.begin.equals(lineSegment.end) || this.end.equals(lineSegment.begin)
                || this.end.equals(lineSegment.end)) {
            return null;
        }
        return LineUtil.intersectLineSegments(this.begin, lineSegment.begin, v1, v2);
    }

    /** Test
     * @param lineSegment
     * @return
     */
    public Vector2dc intersectEpsilon(LineSegment2d lineSegment, double epslilon) {
        Vector2dc v1 = Vector2dUtil.fromTo(this.begin, this.end);
        Vector2dc v2 = Vector2dUtil.fromTo(lineSegment.begin, lineSegment.end);

        if (this.begin.equals(lineSegment.begin) || this.begin.equals(lineSegment.end) || this.end.equals(lineSegment.begin)
                || this.end.equals(lineSegment.end)) {
            return null;
        }

        double sqlEps = epslilon * epslilon;
        if (this.begin.distanceSquared(lineSegment.begin) < sqlEps || this.begin.distanceSquared(lineSegment.end) < sqlEps
                || this.end.distanceSquared(lineSegment.begin) < sqlEps || this.end.distanceSquared(lineSegment.end) < sqlEps) {
            return null;
        }


        return LineUtil.intersectLineSegments(this.begin, lineSegment.begin, v1, v2);
    }


    private static double det(double x, double y, double A, double B, double C) {
        return A * x + B * y + C;
    }

    /**
     * @return the begin
     */
    public Vector2dc getBegin() {
        return begin;
    }

    /**
     * @param begin the begin to set
     */
    public void setBegin(Vector2dc begin) {
        this.begin = begin;
    }

    /**
     * @return the end
     */
    public Vector2dc getEnd() {
        return end;
    }

    /**
     * @param end the end to set
     */
    public void setEnd(Vector2dc end) {
        this.end = end;
    }

    /**
     * @return the openBegin
     */
    public boolean isOpenBegin() {
        return openBegin;
    }

    /**
     * @param openBegin the openBegin to set
     */
    public void setOpenBegin(boolean openBegin) {
        this.openBegin = openBegin;
    }

    /**
     * @return the openEnd
     */
    public boolean isOpenEnd() {
        return openEnd;
    }

    /**
     * @param openEnd the openEnd to set
     */
    public void setOpenEnd(boolean openEnd) {
        this.openEnd = openEnd;
    }


    // dist_Point_to_Segment(): get the distance of a point to a segment.
    //  Input:  a Point P and a Segment S (in any dimension)
    //  Return: the shortest distance from P to S

    //http://softsurfer.com/Archive/algorithm_0102/algorithm_0102.htm#References
    public static double distancePointToSegment(Vector2dc P, LineSegment2d S) {
        //        Vector v = S.P1 - S.P0;
        //        Vector w = P - S.P0;

        Vector2dc v = new Vector2d(S.getEnd()).sub(S.getBegin());

        Vector2dc w = new Vector2d(P).sub(S.getBegin());


        double c1 = w.dot(v);//dot(w,v);
        if ( c1 <= 0 ) {
            return d(P, S.getBegin());
        }

        double c2 = v.dot(v);//dot(v,v);
        if ( c2 <= c1 ) {
            return d(P, S.getEnd());
        }

        double b = c1 / c2;

        //Pb = S.P0 + b * v;
        Vector2d Pb = new Vector2d(v).mul(b).add(S.getBegin());

        return d(P, Pb);
    }

    private static double d(Vector2dc u, Vector2dc v) {
        double dx = u.x() - v.x();
        double dy = u.y() - v.y();

        return Math.sqrt(dx * dx + dy * dy);
    }

    /**
     * {@inheritDoc}
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "LineSegment2d "
                + (openBegin ? "(" : "<")
                + begin
                + ", "
                + end
                + (openEnd ? ")" : ">")
                ;
    }


}

