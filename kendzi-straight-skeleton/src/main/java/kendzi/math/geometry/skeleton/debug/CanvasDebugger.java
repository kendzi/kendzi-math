package kendzi.math.geometry.skeleton.debug;

import java.awt.Color;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

import org.joml.Vector2dc;

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
import kendzi.math.geometry.skeleton.SkeletonOutput;
import kendzi.math.geometry.skeleton.circular.CircularList;
import kendzi.math.geometry.skeleton.circular.Edge;
import kendzi.math.geometry.skeleton.circular.Vertex;
import kendzi.math.geometry.skeleton.debug.display.DisplayEventNames;
import kendzi.math.geometry.skeleton.debug.display.DisplayEventQueue;
import kendzi.math.geometry.skeleton.debug.display.DisplayFaceNode;
import kendzi.math.geometry.skeleton.debug.display.DisplayIntersectEntry;
import kendzi.math.geometry.skeleton.debug.display.DisplayLav2;
import kendzi.math.geometry.skeleton.debug.display.DisplaySLav;
import kendzi.math.geometry.skeleton.debug.display.DisplaySkeletonOut;
import kendzi.math.geometry.skeleton.events.SkeletonEvent;
import kendzi.math.geometry.skeleton.path.FaceNode;
import kendzi.math.geometry.skeleton.path.FaceQueue;

public class CanvasDebugger implements VisualDebugger {

    private static final DebugLayer dv = DebugDisplay.getDebugDisplay().getDebugLayer();

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
    public void debugSlav(Set<CircularList<Vertex>> slav) {
        dv.addDebug(new DisplaySLav(slav));
    }

    @Override
    public void debug(Set<CircularList<Vertex>> set) {
        for (CircularList<Vertex> circularList : set) {
            if (circularList.size() > 0) {
                debug(circularList);
            }
        }
    }

    @Override
    public void debug(CircularList<Vertex> l) {
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
    public <V extends Vector2dc> void debug(V pVector2dc) {
        dv.addDebug(new DisplayPoints(pVector2dc));

    }

    @Override
    public void debug(LineParametric2d pLineParametric2d) {
        dv.addDebug(new DisplayLineParametric2d(pLineParametric2d));
    }

    @Override
    public <V extends Vector2dc> void debug(List<V> polygon) {
        dv.addDebug(new DisplayPolygon(polygon));
    }

    @Override
    public <V extends Vector2dc> void debugNames(List<V> polygon) {
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
    public void debug(Edge edge) {
        dv.addDebug(new DisplayLineSegment2d(edge.getBegin(), edge.getEnd(), Color.GRAY.darker()));
    }

}
