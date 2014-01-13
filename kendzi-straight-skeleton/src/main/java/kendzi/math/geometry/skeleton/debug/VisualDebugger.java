package kendzi.math.geometry.skeleton.debug;

import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

import javax.vecmath.Point2d;

import kendzi.math.geometry.debug.DisplayObject;
import kendzi.math.geometry.line.LineParametric2d;
import kendzi.math.geometry.line.LineSegment2d;
import kendzi.math.geometry.skeleton.Skeleton.EdgeEntry;
import kendzi.math.geometry.skeleton.Skeleton.FaceNode;
import kendzi.math.geometry.skeleton.Skeleton.FaceQueue;
import kendzi.math.geometry.skeleton.Skeleton.SkeletonOutput;
import kendzi.math.geometry.skeleton.Skeleton.VertexEntry2;
import kendzi.math.geometry.skeleton.circular.CircularList;
import kendzi.math.geometry.skeleton.events.SkeletonEvent;

public interface VisualDebugger {

    void debug(FaceNode fn);

    void debug(FaceQueue f);

    void debug(SkeletonEvent I);

    void debug(PriorityQueue<SkeletonEvent> queue);

    void debug(Set<CircularList<VertexEntry2>> set);

    void debug(CircularList<VertexEntry2> l);

    void debug(SkeletonOutput pOutput);

    void debug(LineSegment2d pLineSegment2d);

    void debug(Point2d pPoint2d);

    void debug(LineParametric2d pLineParametric2d);

    void debug(List<Point2d> polygon);

    void debugNames(List<Point2d> polygon);

    void debug(DisplayObject displayObject);

    void debugProcessedEvents(List<SkeletonEvent> processedEvents);

    void clear();

    void block();

    void debug(EdgeEntry edge);

}
