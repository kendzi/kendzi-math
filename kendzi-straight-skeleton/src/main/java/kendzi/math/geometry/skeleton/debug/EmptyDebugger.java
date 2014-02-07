package kendzi.math.geometry.skeleton.debug;

import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

import javax.vecmath.Point2d;

import kendzi.math.geometry.debug.DisplayObject;
import kendzi.math.geometry.line.LineParametric2d;
import kendzi.math.geometry.line.LineSegment2d;
import kendzi.math.geometry.skeleton.SkeletonOutput;
import kendzi.math.geometry.skeleton.circular.CircularList;
import kendzi.math.geometry.skeleton.circular.Edge;
import kendzi.math.geometry.skeleton.circular.Vertex;
import kendzi.math.geometry.skeleton.events.SkeletonEvent;
import kendzi.math.geometry.skeleton.path.FaceNode;
import kendzi.math.geometry.skeleton.path.FaceQueue;

public class EmptyDebugger implements VisualDebugger {

    @Override
    public void debug(FaceNode fn) {
        //

    }

    @Override
    public void debug(FaceQueue f) {
        //

    }

    @Override
    public void debug(SkeletonEvent I) {
        //

    }

    @Override
    public void debug(PriorityQueue<SkeletonEvent> queue) {
        //

    }

    @Override
    public void debug(Set<CircularList<Vertex>> set) {
        //

    }

    @Override
    public void debug(CircularList<Vertex> l) {
        //

    }

    @Override
    public void debug(SkeletonOutput pOutput) {
        //

    }

    @Override
    public void debug(LineSegment2d pLineSegment2d) {
        //

    }

    @Override
    public void debug(Point2d pPoint2d) {
        //

    }

    @Override
    public void debug(LineParametric2d pLineParametric2d) {
        //

    }

    @Override
    public void debug(List<Point2d> polygon) {
        //

    }

    @Override
    public void debugNames(List<Point2d> polygon) {
        //

    }

    @Override
    public void debug(DisplayObject displayObject) {
        //

    }

    @Override
    public void debugProcessedEvents(List<SkeletonEvent> processedEvents) {
        //

    }

    @Override
    public void clear() {
        //

    }

    @Override
    public void block() {
        //

    }

    @Override
    public void debug(Edge edge) {
        //
    }

    @Override
    public void debugSlav(Set<CircularList<Vertex>> slav) {
        //
    }

}
