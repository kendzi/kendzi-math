package kendzi.math.geometry.skeleton.debug;

import java.awt.Color;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

import javax.vecmath.Point2d;

import kendzi.math.geometry.debug.DebugDisplay;
import kendzi.math.geometry.debug.DebugLayer;
import kendzi.math.geometry.debug.DisplayLineParametric2d;
import kendzi.math.geometry.debug.DisplayLineSegment2d;
import kendzi.math.geometry.debug.DisplayObject;
import kendzi.math.geometry.debug.DisplayPoints;
import kendzi.math.geometry.debug.DisplayPolygon;
import kendzi.math.geometry.debug.DisplayPolygonNames;
import kendzi.math.geometry.line.LineParametric2d;
import kendzi.math.geometry.line.LineSegment2d;
import kendzi.math.geometry.skeleton.Skeleton.EdgeEntry;
import kendzi.math.geometry.skeleton.Skeleton.FaceNode;
import kendzi.math.geometry.skeleton.Skeleton.FaceQueue;
import kendzi.math.geometry.skeleton.Skeleton.SkeletonOutput;
import kendzi.math.geometry.skeleton.Skeleton.VertexEntry2;
import kendzi.math.geometry.skeleton.circular.CircularList;
import kendzi.math.geometry.skeleton.debug.display.DisplayEventNames;
import kendzi.math.geometry.skeleton.debug.display.DisplayEventQueue;
import kendzi.math.geometry.skeleton.debug.display.DisplayFaceNode;
import kendzi.math.geometry.skeleton.debug.display.DisplayIntersectEntry;
import kendzi.math.geometry.skeleton.debug.display.DisplayLav2;
import kendzi.math.geometry.skeleton.debug.display.DisplaySkeletonOut;
import kendzi.math.geometry.skeleton.events.SkeletonEvent;

public class CanvasDebugger implements VisualDebugger {
    private static DebugLayer dv = DebugDisplay.getDebugDisplay().getDebugLayer();;

    @Override
    public void debug(FaceNode fn) {

        if (fn != null) {
            dv.addDebug(new DisplayFaceNode(fn, Color.red));
        }

    }

    @Override
    public void debug(FaceQueue f) {

        dv.addDebug(new DisplayFaceNode(f, Color.pink.darker()));

    }

    @Override
    public void debug(SkeletonEvent I) {

        dv.addDebug(new DisplayIntersectEntry(I, Color.red));

    }

    @Override
    public void debug(PriorityQueue<SkeletonEvent> queue) {

        dv.addDebug(new DisplayEventQueue(queue));
    }

    @Override
    public void debug(Set<CircularList<VertexEntry2>> set) {

        for (CircularList<VertexEntry2> circularList : set) {
            if (circularList.size() > 0) {
                debug(circularList);
            }
        }
    }

    @Override
    public void debug(CircularList<VertexEntry2> l) {

        dv.addDebug(new DisplayLav2(l, Color.ORANGE.darker().darker()));
    }

    @Override
    public void debug(SkeletonOutput pOutput) {

        dv.addDebug(new DisplaySkeletonOut(pOutput));
    }

    @Override
    public void debug(LineSegment2d pLineSegment2d) {

        dv.addDebug(new DisplayLineSegment2d(pLineSegment2d.getBegin(), pLineSegment2d.getEnd(), Color.GRAY));
    }

    @Override
    public void debug(Point2d pPoint2d) {

        dv.addDebug(new DisplayPoints(pPoint2d));

    }

    @Override
    public void debug(LineParametric2d pLineParametric2d) {

        dv.addDebug(new DisplayLineParametric2d(pLineParametric2d));
    }

    @Override
    public void debug(List<Point2d> polygon) {

        dv.addDebug(new DisplayPolygon(polygon));
    }

    @Override
    public void debugNames(List<Point2d> polygon) {

        dv.addDebug(new DisplayPolygonNames(polygon));
    }

    @Override
    public void debug(DisplayObject displayObject) {

        dv.addDebug(displayObject);
    }

    @Override
    public void debugProcessedEvents(List<SkeletonEvent> processedEvents) {

        dv.addDebug(new DisplayEventNames(processedEvents));
    }

    @Override
    public void clear() {

        dv.clear();
    }

    @Override
    public void block() {

        DebugDisplay.getDebugDisplay().block();
    }

    @Override
    public void debug(EdgeEntry edge) {

        dv.addDebug(new DisplayLineSegment2d(edge.p1, edge.p2, Color.GRAY.darker()));
    }

}
