package kendzi.math.geometry.polygon;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2d;
import org.joml.Vector2dc;

import kendzi.math.geometry.line.LineSegment2d;

public class CircleInsidePolygon {

    /**
     * 
     * @see http
     *      ://stackoverflow.com/questions/4279478/maximum-circle-inside-a-non
     *      -convex-polygon
     * 
     * @param poly
     * @param epsilon
     * @return
     */
    public static Circle iterativeNonConvex(PolygonList2d poly, double epsilon) {

        double divide = 20;
        Vector2dc maxBound = PolygonUtil.maxBound(poly);
        Vector2dc minBound = PolygonUtil.minBound(poly);

        double minX = minBound.x();
        double minY = minBound.y();
        double deltaX = (maxBound.x() - minBound.x()) / divide;
        double deltaY = (maxBound.y() - minBound.y()) / divide;

        Circle best = null;

        do {
            List<Vector2dc> insideList = new ArrayList<>();

            for (double xi = 0; xi <= divide; xi++) {
                for (double yi = 0; yi <= divide; yi++) {

                    Vector2dc p = new Vector2d(minX + xi * deltaX, minY + yi * deltaY);

                    if (PolygonUtil.isPointInsidePolygon(p, poly)) {
                        insideList.add(p);
                    }
                }
            }

            if (insideList.isEmpty()) {
                // ???
            }

            best = furthestFromAnyPointOnEdge(insideList, poly);

            deltaX = deltaX / Math.sqrt(2); // ???
            deltaY = deltaY / Math.sqrt(2); // ???

            minX = best.point.x() - deltaX * divide / 2.0;
            minY = best.point.y() - deltaY * divide / 2.0;

        } while (Math.max(deltaX, deltaY) > epsilon);

        return best;
    }

    private static Circle furthestFromAnyPointOnEdge(List<Vector2dc> insideList, PolygonList2d poly) {

        if (insideList.isEmpty()) {
            return null;
        }

        List<Vector2dc> points = poly.getPoints();

        List<LineSegment2d> segmentList = new ArrayList<>();

        Vector2dc begin = points.get(points.size() - 1);
        for (Vector2dc end : points) {
            LineSegment2d segment = new LineSegment2d(begin, end);
            segmentList.add(segment);
            begin = end;
        }

        double maxDistance = -Double.MAX_VALUE;
        Vector2dc maxDistancePoint = null;

        for (Vector2dc p : insideList) {

            Double distance = distanceFromAnyPointOnEdge(p, segmentList);
            if (distance > maxDistance) {
                maxDistance = distance;
                maxDistancePoint = p;
            }

        }

        return new Circle(maxDistancePoint, maxDistance);
    }

    public static class Circle {
        Vector2dc point;
        double radius;

        public Circle(Vector2dc point, double radius) {
            super();
            this.point = point;
            this.radius = radius;
        }

        /**
         * @return the point
         */
        public Vector2dc getPoint() {
            return this.point;
        }

        /**
         * @param point
         *            the point to set
         */
        public void setPoint(Vector2dc point) {
            this.point = point;
        }

        /**
         * @return the radius
         */
        public double getRadius() {
            return this.radius;
        }

        /**
         * @param radius
         *            the radius to set
         */
        public void setRadius(double radius) {
            this.radius = radius;
        }

    }

    private static Double distanceFromAnyPointOnEdge(Vector2dc P, List<LineSegment2d> segmentList) {

        if (segmentList.isEmpty()) {
            return null;
        }

        double minDistance = Double.MAX_VALUE;

        for (LineSegment2d segment : segmentList) {

            double distance = LineSegment2d.distancePointToSegment(P, segment);
            if (distance < minDistance) {
                minDistance = distance;
            }
        }
        return minDistance;
    }
}
