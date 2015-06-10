package kendzi.math.geometry.polygon.split;

import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Point2d;
import javax.vecmath.Vector2d;

import kendzi.math.geometry.line.LinePoints2d;
import kendzi.math.geometry.line.LineUtil;
import kendzi.math.geometry.point.Vector2dUtil;

/**
 * Polygon split util.
 *
 * @author Tomasz Kędziora (Kendzi)
 *
 */
public class PlygonSplitUtil {

    /** The error to which splitting will be calculated. */
    private static final double EPSILON = 0.000001;

    /**
     * The zero value. Each point which lay on split line will have assigned
     * that value. It could be compared by reference match.
     */
    private final static int ZERO = 0;

    /**
     * Split given polygon by splitting line. Use default value of epsilon for
     * numerical errors.
     *
     * @param polygon
     *            polygon to split
     * @param splittingLine
     *            the splitting line
     * @return the lists of polygons lies on left and right site of splitting
     *         line
     */
    public static SplitResult split(List<Point2d> polygon, LinePoints2d splittingLine) {
        return split(polygon, splittingLine, EPSILON);
    }

    /**
     * Split given polygon by splitting line. Use default value of epsilon for
     * numerical errors.
     *
     * @param polygon
     *            polygon to split
     * @param splittingLine
     *            the splitting line
     * @return the lists of polygons lies on left and right site of splitting
     *         line
     */
    public static SplitResult split(List<Point2d> polygon, LinePoints2d splittingLine, double epsilon) {

        /* Enrich polygon with new points where edges intersects polygon. */
        List<Node> eplygon = enrich(polygon, splittingLine, epsilon);

        List<List<Node>> left = new ArrayList<List<Node>>();
        List<List<Node>> right = new ArrayList<List<Node>>();

        /* Finds polygon parts lies on left and right site of split line. */
        findRightAndLeftParts(eplygon, left, right);

        /* Connect and close polygon parts on split line. */
        List<Close> leftClose = closePolygons(left, splittingLine);
        List<Close> rightClose = closePolygons(right, splittingLine);

        /* Returns results. */
        return new SplitResult(toPolygons(leftClose), toPolygons(rightClose));

    }

    private static List<List<Point2d>> toPolygons(List<Close> closes) {

        List<List<Point2d>> ret = new ArrayList<List<Point2d>>(closes.size());
        for (Close close : closes) {
            ret.add(toPolygon(close.chain));
        }
        return ret;
    }

    private static List<Point2d> toPolygon(List<Node> chain) {
        List<Point2d> ret = new ArrayList<Point2d>(chain.size());
        for (Node node : chain) {
            ret.add(node.point);
        }
        return ret;
    }

    public static class SplitResult {
        private final List<List<Point2d>> leftPolygons;
        private final List<List<Point2d>> rightPolygons;

        public SplitResult(List<List<Point2d>> leftPolygons, List<List<Point2d>> rightPolygons) {
            super();
            this.leftPolygons = leftPolygons;
            this.rightPolygons = rightPolygons;
        }

        /**
         * @return the leftPolygons
         */
        public List<List<Point2d>> getLeftPolygons() {
            return leftPolygons;
        }

        /**
         * @return the rightPolygons
         */
        public List<List<Point2d>> getRightPolygons() {
            return rightPolygons;
        }

    }

    protected static List<Close> closePolygons(List<List<Node>> chains, LinePoints2d line) {

        Point2d start = line.getP1();
        Vector2d normal = Vector2dUtil.fromTo(line.getP1(), line.getP2());
        normal.normalize();

        List<Close> forwardCloses = new ArrayList<Close>();// XXX
        List<Close> backwardCloses = new ArrayList<Close>();// XXX

        for (List<Node> chain : chains) {

            Close chainClose = new Close();

            chainClose.chain = chain;
            chainClose.beginDistance = distanceAlongVector(start, normal, chain.get(0).point);
            chainClose.endDistance = distanceAlongVector(start, normal, chain.get(chain.size() - 1).point);
            chainClose.direction = chainClose.beginDistance < chainClose.endDistance;

            if (chainClose.direction) {
                forwardCloses.add(chainClose);

            } else {
                backwardCloses.add(chainClose);
            }
        }

        if (backwardCloses.isEmpty()) {
            return forwardCloses;
        } else if (forwardCloses.isEmpty()) {
            return backwardCloses;
        }

        /*
         * Polygon can be clockwise or anti clockwise, we need to decide which
         * of both set of chains is outer and which is represents holes (inner)
         * after split. In correct polygon we could check only begins or ends
         * for distance of projections of split points into split line.
         */
        double theBigestForwardBegin = findTheBiggestEnd(forwardCloses);
        double theBigestBackwardEnd = findTheBiggestBegin(backwardCloses);

        if (theBigestForwardBegin > theBigestBackwardEnd) {
            /*
             * The ending is bigger then begins this mean forward list is outer
             * and backward list is inner.
             */
            close(forwardCloses, backwardCloses);
            return forwardCloses;
        }

        /*
         * The begins is bigger then ends this mean backward list is outer and
         * forward list is inner.
         */
        close(backwardCloses, forwardCloses);
        return backwardCloses;

    }

    protected static double findTheBiggestBegin(List<Close> backwardCloses) {

        double max = -Double.MAX_VALUE;
        for (Close close : backwardCloses) {
            if (close.beginDistance > max) {
                max = close.beginDistance;
            }
        }

        return max;
    }

    protected static double findTheSmallestBegin(List<Close> forwardCloses) {
        double min = Double.MAX_VALUE;
        for (Close close : forwardCloses) {
            if (close.beginDistance < min) {
                min = close.beginDistance;
            }
        }

        return min;
    }

    protected static double findTheBiggestEnd(List<Close> forwardCloses) {
        double max = -Double.MAX_VALUE;
        for (Close close : forwardCloses) {
            if (close.endDistance > max) {
                max = close.endDistance;
            }
        }

        return max;
    }

    protected static void close(List<Close> outerCloses, List<Close> innerCloses) {

        for (Close close : outerCloses) {

            Close innerClose = null;

            while ((innerClose = theClosestInnerClose(close.endDistance, close.beginDistance, innerCloses)) != null) {

                merge(close, innerClose);
                innerClose.removed = true;
            }
        }
    }

    private static void merge(Close outerClose, Close innerClose) {

        // the range is decreased by find chain
        outerClose.endDistance = innerClose.endDistance;

        Node lastNodeInOuterChain = outerClose.chain.get(outerClose.chain.size() - 1);
        Node firstNodeInInnerChain = innerClose.chain.get(0);

        if (lastNodeInOuterChain.equals(firstNodeInInnerChain)) {
            outerClose.chain.remove(outerClose.chain.size() - 1);
        }

        outerClose.chain.addAll(innerClose.chain);
        innerClose.chain = null;
        innerClose.removed = true;
    }

    protected static Close theClosestInnerClose(double endDistance, double beginDistance, List<Close> backwardCloses) {

        double closeSize = endDistance - beginDistance;
        double normal = -1;
        if (closeSize < 0) {
            closeSize = -closeSize;
            normal = 1;
        }

        double minDistance = Double.MAX_VALUE;
        Close closestClose = null;
        for (Close close : backwardCloses) {
            if (close.removed) {
                continue;
            }

            double distance = (close.beginDistance - endDistance) * normal;

            if (distance < 0 || distance > closeSize) {
                // out of close size, reject
                continue;
            }

            if (distance < minDistance) {
                minDistance = distance;
                closestClose = close;
            }

        }

        return closestClose;
    }

    /**
     * Calculate distance from start point to projection of point along normal
     * vector. Starting point and normal vector represent line.
     *
     * @param vectorStartPoint
     *            the normal vector starting point
     * @param vectorNormal
     *            the normal vector
     * @param point
     *            the point which we would like to know distance
     * @return distance between starting point and projection of point on line
     */
    private static double distanceAlongVector(Point2d vectorStartPoint, Vector2d vectorNormal, Point2d point) {

        // Returns: normal dot (point - startPoint)
        return vectorNormal.x * (point.x - vectorStartPoint.x) + vectorNormal.y * (point.y - vectorStartPoint.y);

    }

    /**
     * Represents part of polygon lies on one site of splitting line. The close
     * is not closed and several of them should be merged to receive output
     * polygon.
     */
    protected static class Close {
        public List<Node> chain;
        public double beginDistance;
        public double endDistance;
        public boolean direction;
        public boolean removed;

        /*
         * (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "Close [beginDistance=" + beginDistance + ", endDistance=" + endDistance + ", direction=" + direction
                    + ", removed=" + removed + ", chain=" + chain + "]";
        }

    }

    protected static void findRightAndLeftParts(List<Node> polygon, List<List<Node>> left, List<List<Node>> right) {
        int firstChange = findFirstChangePointIndex(polygon, 0);

        int size = polygon.size();

        if (firstChange == -1) {
            /*
             * All points lying on one site of split line. Chose which site it
             * is and copy to correct result.
             */
            singleSite(polygon, left, right);
            return;
        }

        List<Node> chain = new ArrayList<Node>();
        int currentIndex = firstChange;

        for (int i = 0; i < size; i++) {
            currentIndex = (firstChange + i) % size;
            int nextIndex = (currentIndex + 1) % size;

            Node currentNode = polygon.get(currentIndex);
            Node nextNode = polygon.get(nextIndex);
            if (chain.size() == 0) {
                /*
                 * First node in the chain is always lies on the splitting line.
                 */
                chain.add(currentNode);
            } else if (chain.size() == 1) {
                /*
                 * The second node in the chain is _never_ lies on the splitting
                 * line. So we can chose if that chain is on left or right site
                 * of splitting line.
                 */
                Node secondChainNode = currentNode;
                chain.add(secondChainNode);

                if (secondChainNode.det > 0) {

                    left.add(chain);
                } else {

                    right.add(chain);
                }
            } else if (currentNode.isOnSplittingLine() && !nextNode.isOnSplittingLine()) {
                /*
                 * It is last point in current chain and first in the next
                 * chain.
                 */
                chain.add(currentNode);
                chain = new ArrayList<PlygonSplitUtil.Node>();
                chain.add(currentNode);
                /*
                 * Last added chain is not completed and will be reject
                 * intentionally!
                 */
            } else {
                // adds some point in the middle
                chain.add(currentNode);

            }
        }

        int lastIndex = firstChange;
        Node lastNode = polygon.get(lastIndex);
        /*
         * Last node have to be repeated as first element of first chain and
         * last element of last chain
         */
        chain.add(lastNode);
    }

    private static void singleSite(List<Node> polygon, List<List<Node>> left, List<List<Node>> right) {
        List<Node> ret = new ArrayList<PlygonSplitUtil.Node>(polygon);

        for (Node node : polygon) {
            if (node.det > 0) {
                left.add(ret);
                return;
            } else if (node.det < 0) {
                right.add(ret);
                return;
            }
        }
        throw new IllegalStateException("all polygon points lying on spliting line");
    }

    private static int findFirstChangePointIndex(List<Node> polygon, int start) {
        /*
         * We are looking for polygons lies on "left" and "right" site of split
         * line.
         */

        int size = polygon.size();

        start = start % size;

        int i = 0;
        while (i < size) {

            int beginIndex = (i + start) % size;

            Node begin = polygon.get(beginIndex);

            if (begin.isOnSplittingLine()) {
                // find first point not at splitting line
                i++;
                continue;
            }

            // check if next point is on splitting line
            int middleIndex = (beginIndex + 1) % size;

            Node middle = polygon.get(middleIndex);
            if (!middle.isOnSplittingLine()) {
                i++;
                continue;
            }

            int j = 0;
            // skip all following points lies on split line
            while (polygon.get((middleIndex + 1) % size).isOnSplittingLine()) {
                // next point is on splitting line consume it
                middleIndex++;

                j++;
                if (j > size) {
                    /*
                     * Should not come here because of check it at the begin of
                     * algorithm. But it should not loop forever in case of some
                     * error.
                     */
                    throw new IllegalStateException("all point lies on spliting line");
                }
            }

            // check end point
            int endIndex = (middleIndex + 1) % size;

            Node end = polygon.get(endIndex);

            if (begin.det * end.det < 0) {
                // both points are on opposite sites of splitting line
                return middleIndex;
            }

            i++;
        }
        // no splitting points found
        return -1;
    }

    private static List<Node> enrich(List<Point2d> polygon, LinePoints2d line, double epsilon) {
        int size = polygon.size();

        List<Node> enreach = new ArrayList<Node>(size);

        for (int i = 0; i < size; i++) {
            Point2d point2d = polygon.get(i);

            double det = LineUtil.matrixDet(line.getP1(), line.getP2(), point2d);
            if (det > -epsilon && det < epsilon) {
                /*
                 * To close to splitting line, to avoid numerical error assume
                 * that point is lies on the splitting line.
                 */
                det = ZERO;
            }

            enreach.add(new Node(point2d, det));
        }

        size = enreach.size();
        List<Node> enreachRet = new ArrayList<Node>(size + 10);

        for (int i = 0; i < size; i++) {
            Node begin = enreach.get(i);
            Node end = enreach.get((i + 1) % size);

            enreachRet.add(begin);

            if (begin.isOnSplittingLine() || end.isOnSplittingLine()) {
                /*
                 * One of points is on spliting line. Line can't be spleat more
                 * than once. Continue with next segment.
                 */
                continue;
            }

            if (begin.det * end.det < 0) {
                /*
                 * Points of line segment are on opposite sites of splitting
                 * line. We need to split line segment.
                 */

                Point2d splitPoint = LineUtil.crossLineWithLineSegment(line.getP1(), line.getP2(), begin.point, end.point);

                enreachRet.add(new Node(splitPoint, ZERO));
            }

        }
        return enreachRet;
    }

    /**
     * Polygon point with calculated determinant from splitting line.
     */
    protected static class Node {
        private final Point2d point;
        private final double det;

        /**
         * The node.
         *
         * @param point
         *            polygon point
         * @param det
         *            determinant from splitting line
         */
        public Node(Point2d point, double det) {
            this.point = point;
            this.det = det;
        }

        /**
         * Checks if point is lies on splitting line.
         *
         * @return is point on splitting line
         */
        public boolean isOnSplittingLine() {
            return det == ZERO;
        }

        @Override
        public String toString() {
            return "Node [point=" + point + ", det=" + det + "]";
        }
    }
}
