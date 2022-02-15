package kendzi.math.geometry.skeleton.debug;

import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

import org.joml.Vector2dc;

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

public interface VisualDebugger {

    void debug(FaceNode fn);

    void debug(FaceQueue f);

    void debug(SkeletonEvent I);

    void debug(PriorityQueue<SkeletonEvent> queue);

    void debug(Set<CircularList<Vertex>> set);

    void debugSlav(Set<CircularList<Vertex>> slav);

    void debug(CircularList<Vertex> l);

    void debug(SkeletonOutput pOutput);

    void debug(LineSegment2d pLineSegment2d);

    <V extends Vector2dc> void debug(V pVector2dc);

    void debug(LineParametric2d pLineParametric2d);

    <V extends Vector2dc> void debug(List<V> polygon);

    <V extends Vector2dc> void debugNames(List<V> polygon);

    void debug(DisplayObject displayObject);

    void debugProcessedEvents(List<SkeletonEvent> processedEvents);

    void clear();

    void block();

    void debug(Edge edge);

}
