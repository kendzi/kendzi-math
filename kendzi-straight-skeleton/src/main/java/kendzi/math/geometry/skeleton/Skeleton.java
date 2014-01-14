/*
 * This software is provided "AS IS" without a warranty of any kind. You use it
 * on your own risk and responsibility!!! This file is shared under BSD v3
 * license. See readme.txt and BSD3 file for details.
 */

package kendzi.math.geometry.skeleton;

import static kendzi.math.geometry.skeleton.utils.LavUtil.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import javax.vecmath.Point2d;
import javax.vecmath.Tuple2d;
import javax.vecmath.Vector2d;

import kendzi.math.geometry.Algebra;
import kendzi.math.geometry.AngleUtil;
import kendzi.math.geometry.line.LineLinear2d;
import kendzi.math.geometry.line.LineParametric2d;
import kendzi.math.geometry.line.LineSegment2d;
import kendzi.math.geometry.point.Vector2dUtil;
import kendzi.math.geometry.polygon.PolygonList2d;
import kendzi.math.geometry.polygon.PolygonUtil;
import kendzi.math.geometry.ray.Ray2d;
import kendzi.math.geometry.ray.RayUtil;
import kendzi.math.geometry.ray.RayUtil.IntersectPoints;
import kendzi.math.geometry.skeleton.circular.CircularList;
import kendzi.math.geometry.skeleton.circular.Edge;
import kendzi.math.geometry.skeleton.circular.Vertex;
import kendzi.math.geometry.skeleton.debug.CanvasDebugger;
import kendzi.math.geometry.skeleton.debug.EmptyDebugger;
import kendzi.math.geometry.skeleton.debug.VisualDebugger;
import kendzi.math.geometry.skeleton.events.EdgeEvent;
import kendzi.math.geometry.skeleton.events.MultiEdgeEvent;
import kendzi.math.geometry.skeleton.events.MultiSplitEvent;
import kendzi.math.geometry.skeleton.events.PickEvent;
import kendzi.math.geometry.skeleton.events.SkeletonEvent;
import kendzi.math.geometry.skeleton.events.SplitEvent;
import kendzi.math.geometry.skeleton.events.VertexSplitEvent;
import kendzi.math.geometry.skeleton.events.chains.Chain;
import kendzi.math.geometry.skeleton.events.chains.Chain.ChainType;
import kendzi.math.geometry.skeleton.events.chains.EdgeChain;
import kendzi.math.geometry.skeleton.events.chains.SingleEdgeChain;
import kendzi.math.geometry.skeleton.events.chains.SplitChain;
import kendzi.math.geometry.skeleton.path.FaceNode;
import kendzi.math.geometry.skeleton.path.FaceQueue;

import org.apache.log4j.Logger;

public class Skeleton {

    /** Log. */
    private static final Logger log = Logger.getLogger(Skeleton.class);

    /**
     * Error epsilon.
     */
    private static final double SPLIT_EPSILON = 1E-10;

    /**
     * Visual debugger.
     */
    private static VisualDebugger vd = new EmptyDebugger();

    /**
     * Additional debug information.
     */
    private static boolean debug = true;

    /**
     * Inits visual debugger.
     */
    public static VisualDebugger initVisualDebugger() {
        debug = true;
        vd = new CanvasDebugger();
        return vd;
    }

    public static SkeletonOutput skeleton(List<Point2d> polygon) {
        if (polygon == null) {
            throw new IllegalArgumentException("polygon can't be null");
        }
        if (polygon.get(0).equals(polygon.get(polygon.size() - 1))) {
            throw new IllegalArgumentException("polygon can't start and end with the same point");
        }
        return skeleton(polygon, null);
    }

    public static SkeletonOutput skeleton(List<Point2d> polygon, List<List<Point2d>> holes) {

        polygon = makeCounterClockwise(polygon);
        holes = makeClockwise(holes);

        if (debug) {
            // add names for points
            polygon = debugNames(polygon, "p");
            holes = debugNames(holes);
        }

        debugPolygons(polygon, holes);

        PriorityQueue<SkeletonEvent> queue = new PriorityQueue<SkeletonEvent>(3, distanseComparator);

        Set<CircularList<Vertex>> sLav = new HashSet<CircularList<Vertex>>();

        List<FaceQueue> faces = new ArrayList<FaceQueue>();

        SkeletonOutput output = new SkeletonOutput();

        // STEP 1

        prepareBisectors(polygon, sLav, faces);

        if (holes != null) {
            for (List<Point2d> inner : holes) {
                prepareBisectors(inner, sLav, faces);
            }
        }

        List<Edge> edges = new ArrayList<Edge>();
        for (CircularList<Vertex> lav : sLav) {
            for (Vertex v_i : lav) {
                edges.add(v_i.previousEdge);
            }
        }

        computeInitEvents(sLav, queue, edges);

        for (CircularList<Vertex> lav : sLav) {
            vd.debug(lav);
        }

        vd.debug(queue);

        int count = 0;

        // STEP 2

        List<SkeletonEvent> processedEvents = new ArrayList<SkeletonEvent>();

        while (!queue.isEmpty()) {
            // start processing skeleton level
            count = assertMaxNumberOfInteraction(count);

            double levelHeight = queue.peek().getDistance();

            List<SkeletonEvent> levelEvents = loadAndGroupLevelEvents(queue);

            debugSteep(queue, sLav, faces);

            for (SkeletonEvent event : levelEvents) {

                vd.debug(event);

                if (event.isObsolete()) {
                    /*
                     * Event is outdated some of parent vertex was processed
                     * before
                     */
                    continue;
                }

                processedEvents.add(event);

                vd.debugProcessedEvents(processedEvents);

                if (event instanceof EdgeEvent) {
                    throw new IllegalStateException("all edge events should be converted to MultiEdgeEvents for given level");

                } else if (event instanceof SplitEvent) {
                    throw new IllegalStateException("all split events should be converted to MultiSplitEvents for given level");

                } else if (event instanceof MultiSplitEvent) {

                    multiSplitEvent((MultiSplitEvent) event, output, sLav, queue, edges);
                    continue;
                } else if (event instanceof PickEvent) {

                    pickEvent((PickEvent) event, output, sLav, queue, edges);
                    continue;
                } else if (event instanceof MultiEdgeEvent) {

                    multiEdgeEvent((MultiEdgeEvent) event, output, sLav, queue, edges);
                    continue;
                } else {
                    throw new RuntimeException("unknown event type: " + event.getClass());
                }
            }
            vd.debug(sLav);
            processTwoNodeLavs(sLav);

            removeEventsUnderHeight(queue, levelHeight);
            removeEmptyLav(sLav);

        }

        for (FaceQueue f : faces) {
            vd.debug(f);
        }

        addFacesToOutput(faces, output);

        // vd.debug(polygon);
        // vd.debug(output);

        return output;
    }

    private static void debugPolygons(List<Point2d> polygon, List<List<Point2d>> holes) {
        vd.clear();
        vd.debug(polygon);
        vd.debugNames(polygon);

        if (holes != null) {
            for (List<Point2d> hole : holes) {
                vd.debug(hole);
                vd.debugNames(hole);
            }
        }
    }

    private static List<List<Point2d>> debugNames(List<List<Point2d>> holes) {
        if (holes != null) {
            List<List<Point2d>> newHoles = new ArrayList<List<Point2d>>();
            int h = 0;
            for (List<Point2d> hole : holes) {
                newHoles.add(debugNames(hole, "h" + h + "_p"));
                h++;
            }
            holes = newHoles;
        }
        return holes;
    }

    private static void processTwoNodeLavs(Set<CircularList<Vertex>> sLav) {
        vd.debug(sLav);
        for (CircularList<Vertex> lav : sLav) {
            if (lav.size() == 2) {
                vd.debug(lav);

                Vertex first = lav.first();
                Vertex last = first.next();

                vd.debug(first.leftFace);
                vd.debug(last.rightFace);
                connectList(first.leftFace, last.rightFace);

                vd.debug(last.leftFace);
                vd.debug(first.rightFace);
                connectList(first.rightFace, last.leftFace);

                first.processed = true;
                last.processed = true;

                removeFromLav(first);
                removeFromLav(last);
            }
        }
        vd.debug(sLav);
    }

    private static void removeEmptyLav(Set<CircularList<Vertex>> sLav) {
        // TODO Auto-generated method stub
    }

    private static void multiEdgeEvent(MultiEdgeEvent event, SkeletonOutput output, Set<CircularList<Vertex>> sLav,
            PriorityQueue<SkeletonEvent> queue, List<Edge> edges) {

        Point2d center = event.getPoint();
        List<EdgeEvent> edgeList = event.getChain().getEdgeList();

        Vertex previousVertex = event.getChain().getPreviousVertex();
        previousVertex.processed = true;

        Vertex nextVertex = event.getChain().getNextVertex();
        nextVertex.processed = true;

        Vertex edgeVertex = new Vertex();
        edgeVertex.point = center;
        edgeVertex.distance = event.getDistance();

        edgeVertex.previousEdge = previousVertex.previousEdge;
        edgeVertex.nextEdge = nextVertex.nextEdge;
        edgeVertex.bisector = calcBisector(edgeVertex.point, edgeVertex.previousEdge, edgeVertex.nextEdge);

        // left face
        addFaceLeft(edgeVertex, previousVertex);

        // right face
        addFaceRight(edgeVertex, nextVertex);

        previousVertex.addPrevious(edgeVertex);

        // back faces
        addMultiBackFaces(edgeList, edgeVertex);

        computeEvents(edgeVertex, queue, edges);
    }

    private static void addMultiBackFaces(List<EdgeEvent> edgeList, Vertex edgeVertex) {
        for (EdgeEvent edgeEvent : edgeList) {

            Vertex leftVertex = edgeEvent.getLeftVertex();
            leftVertex.processed = true;
            removeFromLav(leftVertex);

            Vertex rightVertex = edgeEvent.getRightVertex();
            rightVertex.processed = true;
            removeFromLav(rightVertex);

            addFaceBack(edgeVertex, leftVertex, rightVertex);
        }
    }

    private static void pickEvent(PickEvent event, SkeletonOutput output, Set<CircularList<Vertex>> sLav,
            PriorityQueue<SkeletonEvent> queue, List<Edge> edges) {

        Point2d center = event.getPoint();
        List<EdgeEvent> edgeList = event.getChain().getEdgeList();

        Vertex pickVertex = new Vertex();
        pickVertex.point = center;
        pickVertex.processed = true;

        addMultiBackFaces(edgeList, pickVertex);
    }

    private static void multiSplitEvent(MultiSplitEvent event, SkeletonOutput output, Set<CircularList<Vertex>> sLav,
            PriorityQueue<SkeletonEvent> queue, List<Edge> edges) {

        List<Chain> chains = event.getChains();
        final Point2d center = event.v;

        createOppositeEdgeChains(sLav, chains, center);

        Comparator<ChainEnds> multiSplitSorter = new Comparator<Skeleton.ChainEnds>() {

            @Override
            public int compare(ChainEnds chain1, ChainEnds chain2) {
                /*
                 * Sort it by chain edges begins, chains are sorted
                 * CounterClockwise
                 */
                double angle1 = AngleUtil.angle(center, chain1.getPreviousEdge().getBegin());
                double angle2 = AngleUtil.angle(center, chain2.getPreviousEdge().getBegin());

                return angle1 > angle2 ? 1 : -1;
            }
        };

        // sort list of chains clock wise
        Collections.sort(chains, multiSplitSorter);

        // face node for split event is shared between two chains
        FaceNode lastFaceNode = null;

        // connect all edges into new bisectors and lavs
        int edgeListSize = chains.size();
        for (int i = 0; i < edgeListSize; i++) {
            ChainEnds chainBegin = chains.get(i);
            ChainEnds chainEnd = chains.get((i + 1) % edgeListSize);

            Vertex newVertex = createMultiSplitVertex(chainBegin.getNextEdge(), chainEnd.getPreviousEdge(), center);

            // Split and merge lavs...

            vd.debug(sLav);
            vd.debug(center);

            Vertex beginNextVertex = chainBegin.getNextVertex();
            Vertex endPreviousVertex = chainEnd.getPreviousVertex();

            if (isSameLav(beginNextVertex, endPreviousVertex)) {
                /*
                 * if vertex are in same lav we need to cut part of lav in the
                 * middle of vertex and create new lav from that points
                 */

                List<Vertex> lavPart = cutLavPart(beginNextVertex, endPreviousVertex);

                CircularList<Vertex> lav = new CircularList<Vertex>();
                sLav.add(lav);

                lav.addLast(newVertex);

                for (Vertex vertex : lavPart) {
                    lav.addLast(vertex);
                }

                if (log.isDebugEnabled()) {
                    log.debug("after split: " + lavToString(lav.first()));
                }
                vd.debug(sLav);
            } else {
                /*
                 * if vertex are in different lavs we need to merge them into
                 * one.
                 */
                vd.debug(sLav);
                mergeBeforeBaseVertex(beginNextVertex, endPreviousVertex);

                endPreviousVertex.addNext(newVertex);

                if (log.isDebugEnabled()) {
                    log.debug("after merge: " + lavToString(newVertex));
                }
                vd.debug(sLav);
            }

            computeEvents(newVertex, queue, edges);

            lastFaceNode = addSplitFaces(lastFaceNode, chainBegin, chainEnd, newVertex);

            vd.debug(sLav);
        }

        vd.debug(sLav);

        // remove all centers of events from lav
        edgeListSize = chains.size();
        for (int i = 0; i < edgeListSize; i++) {
            ChainEnds chainBegin = chains.get(i);
            ChainEnds chainEnd = chains.get((i + 1) % edgeListSize);

            removeFromLav(chainBegin.getCurrentVertex());
            removeFromLav(chainEnd.getCurrentVertex());

            if (chainBegin.getCurrentVertex() != null) {
                chainBegin.getCurrentVertex().processed = true;
            }
            if (chainEnd.getCurrentVertex() != null) {
                chainEnd.getCurrentVertex().processed = true;
            }
        }

        if (debug) {
            validateLavsEdges(sLav);
        }

        vd.debug(sLav);
    }

    private static void validateLavsEdges(Set<CircularList<Vertex>> sLav) {
        for (CircularList<Vertex> circularList : sLav) {
            for (Vertex vertex : circularList) {
                Vertex next = vertex.next();
                Vertex previous = vertex.previous();

                if (vertex.previousEdge != previous.nextEdge) {
                    throw new IllegalStateException(String.format(
                            "previous edge don't match next edge in previous node vertex.e_a: %s previous.e_b: %s",
                            vertex.previousEdge, previous.nextEdge));
                }
                if (vertex.nextEdge != next.previousEdge) {
                    throw new IllegalStateException(String.format(
                            "next edge don't match previous edge in next node vertex.e_b: %s next.e_a: %s", vertex.nextEdge,
                            next.previousEdge));
                }
            }
        }
    }

    private static FaceNode addSplitFaces(FaceNode lastFaceNode, ChainEnds chainBegin, ChainEnds chainEnd, Vertex newVertex) {
        if (chainBegin instanceof SingleEdgeChain) {
            /*
             * When chain is generated by opposite edge we need to share face
             * between two chains. Number of that chains shares is always odd.
             */
            Vertex beginVertex = chainBegin.getNextVertex();

            // right face
            if (lastFaceNode == null) {
                /*
                 * Vertex generated by opposite edge share three faces, but
                 * vertex can store only left and right face. So we need to
                 * create vertex clone to store additional face
                 */
                beginVertex = createOppositeEdgeVertex(newVertex);

                vd.debug(beginVertex.rightFace);
                /* same face in two vertex, original and in opposite edge clone */
                newVertex.rightFace = beginVertex.rightFace;

                vd.debug(beginVertex.rightFace);
            } else {
                // face queue exist simply assign it to new node
                if (newVertex.rightFace != null) {
                    throw new RuntimeException();
                }
                newVertex.rightFace = lastFaceNode;
                lastFaceNode = null;
            }

        } else {
            Vertex beginVertex = chainBegin.getCurrentVertex();
            vd.debug(beginVertex.rightFace);
            // right face
            addFaceRight(newVertex, beginVertex);
            vd.debug(beginVertex.rightFace);
        }

        if (chainEnd instanceof SingleEdgeChain) {
            Vertex endVertex = chainEnd.getPreviousVertex();

            // left face
            if (lastFaceNode == null) {
                /*
                 * Vertex generated by opposite edge share three faces, but
                 * vertex can store only left and right face. So we need to
                 * create vertex clone to store additional face
                 */
                endVertex = createOppositeEdgeVertex(newVertex);

                vd.debug(endVertex.leftFace);
                /* same face in two vertex, original and in opposite edge clone */
                newVertex.leftFace = endVertex.leftFace;

                vd.debug(endVertex.leftFace);
            } else {
                // face queue exist simply assign it to new node
                if (newVertex.leftFace != null) {
                    throw new RuntimeException();
                }
                newVertex.leftFace = lastFaceNode;

                lastFaceNode = null;
            }

        } else {
            Vertex endVertex = chainEnd.getCurrentVertex();
            vd.debug(endVertex.leftFace);
            // left face
            addFaceLeft(newVertex, endVertex);
            vd.debug(endVertex.leftFace);
        }
        return lastFaceNode;
    }

    private static Vertex createOppositeEdgeVertex(Vertex newVertex) {
        /*
         * When opposite edge is processed we need to create copy of vertex to
         * use in opposite face. When opposite edge chain occur vertex is shared
         * by additional output face.
         */

        Vertex vertex = new Vertex();
        vertex.bisector = newVertex.bisector;
        vertex.point = newVertex.point;
        vertex.previousEdge = newVertex.previousEdge;
        vertex.nextEdge = newVertex.nextEdge;

        // create new empty node queue
        FaceNode fn = new FaceNode();
        fn.v = vertex;
        vertex.leftFace = fn;
        vertex.rightFace = fn;

        // add one node for queue to present opposite site of edge split event
        FaceQueue rightFace = new FaceQueue();
        rightFace.setBorder(false);
        rightFace.addFirst(fn);

        return vertex;
    }

    private static void createOppositeEdgeChains(Set<CircularList<Vertex>> sLav, List<Chain> chains, Point2d center) {
        /*
         * Add chain created from opposite edge, this chain have to be
         * calculated during processing event because lav could change during
         * processing another events on the same level
         */
        Set<Edge> oppositeEdges = new HashSet<Edge>();

        for (Chain chain : chains) {
            // add opposite edges as chain parts
            if (chain instanceof SplitChain) {
                SplitChain splitChain = (SplitChain) chain;
                Edge oppositeEdge = splitChain.getOppositeEdge();
                if (oppositeEdge != null) {
                    // find lav vertex for opposite edge
                    oppositeEdges.add(oppositeEdge);
                }
            } else if (chain instanceof EdgeChain) {
                EdgeChain edgeChain = (EdgeChain) chain;
                if (ChainType.SPLIT.equals(edgeChain.getType())) {
                    Edge oppositeEdge = ((EdgeChain) chain).getOppositeEdge();
                    if (oppositeEdge != null) {
                        // find lav vertex for opposite edge
                        oppositeEdges.add(oppositeEdge);
                    }
                }
            }
        }

        for (Edge oppositeEdge : oppositeEdges) {
            // find lav vertex for opposite edge
            // FIXME what when we share edge between two lavs?
            Vertex nextVertex = findOppositeEdgeLav(sLav, oppositeEdge, center);

            chains.add(new SingleEdgeChain(oppositeEdge, nextVertex));
        }
    }

    private static Vertex createMultiSplitVertex(Edge previousEdge, Edge nextEdge, Point2d center) {

        Point2d edgeEnd = previousEdge.getEnd();
        Vector2d bisectorPrediction = new Vector2d(edgeEnd.x - center.x, edgeEnd.y - center.y);

        Ray2d bisector = calcBisector(center, previousEdge, nextEdge);

        if (bisector.U.dot(bisectorPrediction) < 0) {
            // bisector is calculated in opposite direction to edges and center
            bisector.U.negate();
        }

        Vertex vertex = new Vertex();
        vertex.bisector = bisector;
        vertex.point = center;
        // edges are mirrored for event
        vertex.previousEdge = nextEdge;
        vertex.nextEdge = previousEdge;

        return vertex;
    }

    /**
     * Create chains of events from cluster. Cluster is set of events which meet
     * in the same result point. Try to connect all event which share the same
     * vertex into chain. Events in chain are sorted. If events don't share
     * vertex, returned chains contains only one event.
     * 
     * @param cluster
     *            set of event which meet in the same result point
     * @return chains of events
     */
    private static List<Chain> createChains(List<SkeletonEvent> cluster) {
        List<EdgeEvent> edgeCluster = new ArrayList<EdgeEvent>();
        List<SplitEvent> splitCluster = new ArrayList<SplitEvent>();
        Set<Vertex> vertexEventsParents = new HashSet<Vertex>();

        for (SkeletonEvent skeletonEvent : cluster) {
            if (skeletonEvent instanceof EdgeEvent) {
                edgeCluster.add((EdgeEvent) skeletonEvent);
            } else {
                if (skeletonEvent instanceof VertexSplitEvent) {
                    /*
                     * It will be processed in next loop to find unique split
                     * events for one parent.
                     */
                    continue;

                } else if (skeletonEvent instanceof SplitEvent) {
                    SplitEvent splitEvent = (SplitEvent) skeletonEvent;
                    /*
                     * If vertex and split event exist for the same parent
                     * vertex and at the same level always prefer split.
                     */
                    vertexEventsParents.add(splitEvent.getParent());
                    splitCluster.add(splitEvent);
                }
            }
        }

        for (SkeletonEvent skeletonEvent : cluster) {

            if (skeletonEvent instanceof VertexSplitEvent) {
                VertexSplitEvent vertexEvent = (VertexSplitEvent) skeletonEvent;
                if (!vertexEventsParents.contains(vertexEvent.getParent())) {

                    /*
                     * It can be created multiple vertex events for one parent.
                     * Its is caused because two edges share one vertex and new
                     * event will be added to both of them. When processing we
                     * need always group them into one per vertex. Always prefer
                     * split events over vertex events.
                     */

                    vertexEventsParents.add(vertexEvent.getParent());

                    splitCluster.add(vertexEvent);
                }
            }
        }

        List<EdgeChain> edgeChains = new ArrayList<EdgeChain>();

        while (edgeCluster.size() > 0) {
            /*
             * We need to find all connected edge events, and create chains from
             * them. Two event are assumed as connected if next parent of one
             * event is equal to previous parent of second event.
             */

            edgeChains.add(new EdgeChain(createEdgeChain(edgeCluster)));
        }

        List<Chain> chains = new ArrayList<Chain>(edgeChains);

        splitEventLoop: while (splitCluster.size() > 0) {
            SplitEvent split = splitCluster.remove(0);

            for (EdgeChain chain : edgeChains) {

                // check if chain is split type
                if (isInEdgeChain(split, chain)) {
                    // if we have edge chain it can't share split event

                    continue splitEventLoop;
                }
            }

            /*
             * split event is not part of any edge chain, it should be added as
             * new single element chain;
             */
            chains.add(new SplitChain(split));
        }

        /*
         * Return list of chains with type. Possible types are edge chain,
         * closed edge chain, split chain. Closed edge chain will produce pick
         * event. Always it can exist only one closed edge chain for point
         * cluster.
         */
        return chains;
    }

    private static boolean isInEdgeChain(SplitEvent split, EdgeChain chain) {

        Vertex splitParent = split.getParent();

        List<EdgeEvent> edgeList = chain.getEdgeList();
        for (EdgeEvent edgeEvent : edgeList) {
            if (edgeEvent.Va == splitParent || edgeEvent.Vb == splitParent) {
                return true;
            }
        }

        return false;
    }

    protected static ArrayList<EdgeEvent> createEdgeChain(List<EdgeEvent> edgeCluster) {

        ArrayList<EdgeEvent> edgeList = new ArrayList<EdgeEvent>();

        edgeList.add(edgeCluster.remove(0));

        // find all successors of edge event
        // find all predecessors of edge event

        // XXX check in future
        loop: do {

            Vertex beginVertex = edgeList.get(0).Va;
            Vertex endVertex = edgeList.get(edgeList.size() - 1).Vb;

            for (int i = 0; i < edgeCluster.size(); i++) {
                EdgeEvent edge = edgeCluster.get(i);
                if (edge.Va == endVertex) {
                    // edge should be added as last in chain
                    edgeCluster.remove(i);
                    edgeList.add(edge);
                    continue loop;
                } else if (edge.Vb == beginVertex) {
                    // edge should be added as first in chain
                    edgeCluster.remove(i);
                    edgeList.add(0, edge);
                    continue loop;
                }
            }
            break;

        } while (true);

        return edgeList;
    }

    public static interface ChainEnds {
        public Edge getPreviousEdge();

        public Edge getNextEdge();

        public Vertex getPreviousVertex();

        public Vertex getNextVertex();

        public Vertex getCurrentVertex();
    }

    private static void removeEventsUnderHeight(PriorityQueue<SkeletonEvent> queue, double levelHeight) {

        while (!queue.isEmpty()) {
            if (queue.peek().getDistance() > levelHeight + SPLIT_EPSILON) {
                break;
            }
            queue.poll();
        }
    }

    private static List<SkeletonEvent> loadAndGroupLevelEvents(PriorityQueue<SkeletonEvent> queue) {

        List<SkeletonEvent> levelEvents = loadLevelEvents(queue);

        return groupLevelEvents(levelEvents);
    }

    protected static List<SkeletonEvent> groupLevelEvents(List<SkeletonEvent> levelEvents) {

        List<SkeletonEvent> ret = new ArrayList<SkeletonEvent>();

        while (levelEvents.size() > 0) {

            SkeletonEvent event = levelEvents.remove(0);
            Point2d eventCenter = event.getPoint();
            double distance = event.getDistance();

            List<SkeletonEvent> cluster = new ArrayList<SkeletonEvent>();
            cluster.add(event);
            for (int j = 0; j < levelEvents.size(); j++) {
                SkeletonEvent test = levelEvents.get(j);
                if (near(eventCenter, test.v, SPLIT_EPSILON)) {
                    // group all event when the result point are near each other
                    cluster.add(levelEvents.remove(j));
                    j--;
                }
            }

            /*
             * More then one event share the same result point, we need to
             * create new level event
             */
            ret.add(createLevelEvent(eventCenter, distance, cluster));
        }
        return ret;
    }

    /**
     * @param eventCluster
     *            list of events which meet in single point.
     * @param eventCenter
     * @return
     */
    private static SkeletonEvent createLevelEvent(Point2d eventCenter, double distance, List<SkeletonEvent> eventCluster) {

        List<Chain> chains = createChains(eventCluster);

        if (chains.size() == 1) {
            Chain chain = chains.get(0);
            if (ChainType.CLOSED_EDGE.equals(chain.getType())) {
                return new PickEvent(eventCenter, distance, (EdgeChain) chain);
            } else if (ChainType.EDGE.equals(chain.getType())) {
                return new MultiEdgeEvent(eventCenter, distance, (EdgeChain) chain);
            } else if (ChainType.SPLIT.equals(chain.getType())) {
                return new MultiSplitEvent(eventCenter, distance, chains);
            }
        }

        for (Chain chain : chains) {
            if (ChainType.CLOSED_EDGE.equals(chain.getType())) {
                throw new RuntimeException("found closed chain of events for single point, but found more then one chain");
            }
        }

        return new MultiSplitEvent(eventCenter, distance, chains);
    }

    static boolean near(Point2d p1, Point2d p2, double epsilon) {
        // XXX
        return distance(p1, p2) < epsilon;
    }

    private static void debugSteep(PriorityQueue<SkeletonEvent> queue, Set<CircularList<Vertex>> sLav, List<FaceQueue> faces) {
        vd.debug(queue);

        for (CircularList<Vertex> l : sLav) {
            vd.debug(l);
        }

        for (FaceQueue f : faces) {
            vd.debug(f);
        }
    }

    /**
     * Loads all not obsolete event which are on one level. As level heigh is
     * taken epsilon.
     * 
     * @param queue
     * @return
     */
    private static List<SkeletonEvent> loadLevelEvents(PriorityQueue<SkeletonEvent> queue) {

        List<SkeletonEvent> level = new ArrayList<SkeletonEvent>();

        SkeletonEvent levelStart = null;
        do {
            levelStart = queue.poll();
            // skip all obsolete events in level
        } while (levelStart != null && levelStart.isObsolete());

        if (levelStart == null || levelStart.isObsolete()) {
            // all events obsolete
            return level;
        }

        double levelStartHeight = levelStart.getDistance();

        level.add(levelStart);

        SkeletonEvent event = null;
        while ((event = queue.peek()) != null && event.getDistance() - levelStartHeight < SPLIT_EPSILON) {

            SkeletonEvent nextLevelEvent = queue.poll();
            if (!nextLevelEvent.isObsolete()) {
                level.add(nextLevelEvent);
            }
        }
        return level;
    }

    private static int assertMaxNumberOfInteraction(int count) {
        count++;
        if (count > 10000) {
            throw new RuntimeException("to many interaction: bug?");
        }
        return count;
    }

    private static class DebugPoint2d extends Point2d {
        String name;

        public DebugPoint2d(double x, double y, String name) {
            super(x, y);
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }

    }

    private static List<Point2d> debugNames(List<Point2d> polygon, String name) {
        List<Point2d> ret = new ArrayList<Point2d>();
        int i = 0;
        for (Point2d point : polygon) {
            ret.add(new DebugPoint2d(point.x, point.y, name + i));
            i++;
        }
        return ret;
    }

    private static List<List<Point2d>> makeClockwise(List<List<Point2d>> holes) {
        if (holes == null) {
            return null;
        }

        List<List<Point2d>> ret = new ArrayList<List<Point2d>>(holes.size());
        for (List<Point2d> hole : holes) {
            if (PolygonUtil.isClockwisePolygon(hole)) {
                ret.add(hole);
            } else {
                ret.add(PolygonUtil.reverse(hole));
            }
        }
        return ret;
    }

    private static List<Point2d> makeCounterClockwise(List<Point2d> polygon) {
        if (PolygonUtil.isClockwisePolygon(polygon)) {
            return PolygonUtil.reverse(polygon);
        }
        return polygon;
    }

    /**
     * @param pBorder
     * @param sLav
     * @param faces
     */
    private static void prepareBisectors(List<Point2d> pBorder, Set<CircularList<Vertex>> sLav, List<FaceQueue> faces) {
        CircularList<Vertex> lav = new CircularList<Vertex>();
        sLav.add(lav);

        for (Point2d p : pBorder) {
            Vertex v2 = new Vertex();
            v2.point = p;
            lav.addLast(v2);
        }

        CircularList<Edge> edgesList = new CircularList<Edge>();

        for (Vertex v_i : lav) {

            Vertex v_ip1 = v_i.next();

            Edge e_i = new Edge(v_i.point, v_ip1.point);

            edgesList.addLast(e_i);

            v_i.nextEdge = e_i;
        }

        for (Vertex v_i : lav) {
            v_i.previousEdge = v_i.nextEdge.previous();
        }

        vd.debug(lav);

        for (Vertex v : lav) {

            v.bisector = calcBisector(v.point, v.previousEdge, v.nextEdge);

            v.previousEdge.setBisectorNext(v.bisector);
            v.nextEdge.setBisectorPrevious(v.bisector);

            vd.debug(v.bisector);

            FaceQueue rightFace = new FaceQueue();
            rightFace.setBorder(true);
            rightFace.setEdge(v.nextEdge);

            FaceNode fn = new FaceNode();
            fn.v = v;
            rightFace.addFirst(fn);
            v.rightFace = fn;

            faces.add(rightFace);
        }

        for (Vertex v : lav) {

            Vertex next = v.previous();

            FaceNode rightFace = next.rightFace;

            FaceNode fn = new FaceNode();
            fn.v = v;
            rightFace.addPush(fn);
            v.leftFace = fn;
        }
    }

    private static void addFacesToOutput(List<FaceQueue> faces, SkeletonOutput output) {

        for (FaceQueue face : faces) {
            if (face.getSize() > 0) {
                List<Point2d> faceList = new ArrayList<Point2d>();

                for (FaceNode fn : face) {
                    faceList.add(fn.v.point);
                    output.distance.put(fn.v.point, fn.v.distance);
                }

                PolygonList2d polygon = new PolygonList2d(faceList);

                output.faces.add(polygon);
                output.edges.put(polygon, new LineSegment2d(face.getEdge().getBegin(), face.getEdge().getEnd()));

            }
        }
    }

    private static void computeInitEvents(Set<CircularList<Vertex>> sLav, PriorityQueue<SkeletonEvent> queue,
            List<Edge> edges) {

        // Map<VertexEntry2, List<SplitCandidate>> splitEventsSet = new
        // HashMap<VertexEntry2, List<SplitCandidate>>();
        // splitEventsSet.put(vertex, calcOppositeEdges);

        for (CircularList<Vertex> lav : sLav) {

            for (Vertex vertex : lav) {

                computeSplitEvents(vertex, edges, queue, null);
            }
        }

        for (CircularList<Vertex> lav : sLav) {

            for (Vertex vertex : lav) {

                Vertex nextVertex = vertex.next();
                computeEdgeEvents(vertex, nextVertex, queue);
            }
        }
    }

    private static void computeSplitEvents(Vertex vertex, List<Edge> edges, PriorityQueue<SkeletonEvent> queue,
            Double distanceSquared) {

        Point2d source = vertex.point;

        List<SplitCandidate> calcOppositeEdges = calcOppositeEdges(vertex, edges);

        // splitEventsSet.put(vertex, calcOppositeEdges);

        // check if it is vertex split event
        for (SplitCandidate oppositeEdge : calcOppositeEdges) {

            Point2d point = oppositeEdge.getPoint();

            if (distanceSquared != null) {
                if (source.distanceSquared(point) > distanceSquared + SPLIT_EPSILON) {
                    /*
                     * Current split event distance from source of event is
                     * greater then for edge event. Split event can be reject.
                     * Distance from source is not the same as distance for
                     * edge. Two events can have the same distance to edge but
                     * they will be in different distance form its source.
                     * Unnecessary events should be reject otherwise they cause
                     * problems for degenerate cases.
                     */
                    continue;
                }
            }

            // check if it is vertex split event
            if (oppositeEdge.getOppositePoint() != null) {
                // some of vertex event can share the same opposite
                // point
                queue.add(new VertexSplitEvent(point, oppositeEdge.getDistance(), vertex));
                continue;
            }

            queue.add(new SplitEvent(point, oppositeEdge.getDistance(), vertex, oppositeEdge.getOppositeEdge()));
            continue;
        }
    }

    private static void computeEvents(Vertex vertex, PriorityQueue<SkeletonEvent> queue, List<Edge> edges) {

        vd.debug(vertex.list());

        Double distanceSquared = computeCloserEdgeEvent(vertex, queue);

        computeSplitEvents(vertex, edges, queue, distanceSquared);
    }

    /**
     * Calculate two new edge events for given vertex. Events are generated
     * using current, previous and next vertex in current lav. When two edge
     * events are generated distance from source is check. To queue is added
     * only closer event or both if they have the same distance.
     * 
     * @param vertex
     *            source of edge events
     * @param queue
     *            queue of events
     * @return squared distance to closer edge event
     */
    private static Double computeCloserEdgeEvent(Vertex vertex, PriorityQueue<SkeletonEvent> queue) {

        Vertex nextVertex = vertex.next();
        Vertex previousVertex = vertex.previous();

        Point2d point = vertex.point;
        /*
         * We need to chose closer edge event. When two evens appear in epsilon
         * we take both. They will create single MultiEdgeEvent.
         */

        Point2d point1 = computeIntersectionBisectors(vertex, nextVertex);
        Point2d point2 = computeIntersectionBisectors(previousVertex, vertex);

        if (point1 == null && point2 == null) {
            return null;
        }

        double distance1 = Double.MAX_VALUE;
        double distance2 = Double.MAX_VALUE;

        if (point1 != null) {
            distance1 = point.distanceSquared(point1);
        }
        if (point2 != null) {
            distance2 = point.distanceSquared(point2);
        }

        if (distance1 - SPLIT_EPSILON < distance2) {
            queue.add(createEdgeEvent(point1, vertex, nextVertex));
        }
        if (distance2 - SPLIT_EPSILON < distance1) {
            queue.add(createEdgeEvent(point2, previousVertex, vertex));
        }

        return distance1 < distance2 ? distance1 : distance2;
    }

    private static SkeletonEvent createEdgeEvent(Point2d point, Vertex previousVertex, Vertex nextVertex) {
        return new EdgeEvent(point, calcDistance(point, previousVertex.nextEdge), previousVertex, nextVertex);
    }

    private static void computeEdgeEvents(Vertex previousVertex, Vertex nextVertex, PriorityQueue<SkeletonEvent> queue) {
        Point2d point = computeIntersectionBisectors(previousVertex, nextVertex);
        if (point != null) {
            queue.add(createEdgeEvent(point, previousVertex, nextVertex));
        }
    }

    /**
     * Check if given point is on one of edge bisectors. If so this is vertex
     * split event. This event need two opposite edges to process but second
     * (next) edge can be take from edges list and it is next edge on list.
     * 
     * @param point
     *            point of event
     * @param edge
     *            candidate for opposite edge
     * @return previous opposite edge if it is vertex split event
     */
    protected static Edge vertexOpositeEdge(Point2d point, Edge edge) {

        if (RayUtil.isPointOnRay(point, edge.getBisectorNext(), SPLIT_EPSILON)) {
            return edge;
        }

        if (RayUtil.isPointOnRay(point, edge.getBisectorPrevious(), SPLIT_EPSILON)) {
            return edge.previous();
        }
        return null;
    }

    private static List<SplitCandidate> calcOppositeEdges(Vertex vertex, List<Edge> edges) {

        List<SplitCandidate> ret = new ArrayList<SplitCandidate>();

        for (Edge edgeEntry : edges) {

            LineLinear2d edge = edgeEntry.getLineLinear();

            // check if edge is behind bisector
            if (edgeBehindBisector(vertex.bisector, edge)) {
                continue;
            }

            // compute the coordinates of the candidate point Bi
            SplitCandidate candidatePoint = calcCandidatePointForSplit(vertex, edgeEntry);

            if (candidatePoint != null) {
                ret.add(candidatePoint);
            }
        }

        Collections.sort(ret, new Comparator<SplitCandidate>() {

            @Override
            public int compare(SplitCandidate o1, SplitCandidate o2) {
                return Double.compare(o1.distance, o2.distance);
            }
        });

        return ret;
    }

    protected static boolean edgeBehindBisector(Ray2d bisector, LineLinear2d edge) {
        // Simple intersection test between the bisector starting at V and the
        // whole line containing the currently tested line segment ei
        // rejects the line segments laying "behind" the vertex V

        return Ray2d.collide(bisector, edge, SPLIT_EPSILON) == null;
    }

    private static class SplitCandidate {

        private double distance;
        private Point2d point;
        private Edge oppositeEdge;
        private Point2d oppositePoint;

        public SplitCandidate(Point2d point, double distance, Edge oppositeEdge, Point2d oppositePoint) {
            super();
            this.point = point;
            this.distance = distance;
            this.oppositeEdge = oppositeEdge;
            this.oppositePoint = oppositePoint;
        }

        public double getDistance() {
            return distance;
        }

        public Point2d getPoint() {
            return point;
        }

        public Edge getOppositeEdge() {
            return oppositeEdge;
        }

        public Point2d getOppositePoint() {
            return oppositePoint;
        }
    }

    protected static SplitCandidate calcCandidatePointForSplit(Vertex vertex, Edge edge) {

        Edge vertexEdge = choseLessParallelVertexEdge(vertex, edge);
        if (vertexEdge == null) {
            return null;
        }

        Vector2d vertexEdteNormNegate = vertexEdge.getNorm();

        Vector2d edgesBisector = calcVectorBisector(vertexEdteNormNegate, edge.getNorm());

        Point2d edgesCollide = vertexEdge.getLineLinear().collide(edge.getLineLinear());

        if (edgesCollide == null) {
            // check should be performed to exclude the case when one of the
            // line segments starting at V is
            // parallel to ei.

            // return null;
            throw new RuntimeException("ups this should not happen");
        }

        LineLinear2d edgesBisectorLine = new LineParametric2d(edgesCollide, edgesBisector).getLinearForm();

        // compute the coordinates of the candidate point Bi as the intersection
        // between the bisector at V and the axis of the angle between one of
        // the edges starting at V and the tested line segment ei

        Point2d candidatePoint = Ray2d.collide(vertex.bisector, edgesBisectorLine, SPLIT_EPSILON);

        if (candidatePoint == null) {
            return null;
        }

        if (edge.getBisectorPrevious().isOnRightSite(candidatePoint, SPLIT_EPSILON)
                && edge.getBisectorNext().isOnLeftSite(candidatePoint, SPLIT_EPSILON)) {

            double distance = calcDistance(candidatePoint, edge);

            if (edge.getBisectorPrevious().isOnLeftSite(candidatePoint, SPLIT_EPSILON)) {

                Point2d oppositePoint = edge.getBegin();
                return new SplitCandidate(candidatePoint, distance, null, oppositePoint);
            } else if (edge.getBisectorNext().isOnRightSite(candidatePoint, SPLIT_EPSILON)) {

                Point2d oppositePoint = edge.getBegin();
                return new SplitCandidate(candidatePoint, distance, null, oppositePoint);
            }

            return new SplitCandidate(candidatePoint, distance, edge, null);
        }

        return null;
    }

    private static Edge choseLessParallelVertexEdge(Vertex vertex, Edge edge) {
        Edge edgeA = vertex.previousEdge;
        Edge edgeB = vertex.nextEdge;

        Edge vertexEdge = edgeA;

        double edgeADot = Math.abs(edge.getNorm().dot(edgeA.getNorm()));
        double edgeBDot = Math.abs(edge.getNorm().dot(edgeB.getNorm()));

        if (edgeADot + edgeBDot >= 2 - SPLIT_EPSILON) {
            // both lines are parallel to given edge
            return null;
        }

        if (edgeADot > edgeBDot) {
            /*
             * Simple check should be performed to exclude the case when one of
             * the line segments starting at V (vertex) is parallel to e_i
             * (edge) we always chose edge which is less parallel.
             */
            vertexEdge = edgeB;
        }
        return vertexEdge;
    }

    private static Point2d computeIntersectionBisectors(Vertex vertexPrevious, Vertex vertexNext) {

        Ray2d bisectorPrevious = vertexPrevious.bisector;
        Ray2d bisectorNext = vertexNext.bisector;

        IntersectPoints intersectRays2d = RayUtil.intersectRays2d(bisectorPrevious, bisectorNext);

        Point2d intersect = intersectRays2d.getIntersect();

        if (vertexPrevious.point.equals(intersect) || vertexNext.point.equals(intersect)) {
            // skip the same points
            return null;
        }

        if (intersect != null) {
            return intersect;
        }
        return null;
    }

    protected static CircularList<Vertex> mergeLav(Vertex firstLav, Vertex secondLav) {

        Vertex firstNext = firstLav.next();
        Vertex secondNext = secondLav.next();

        vd.debug(firstNext.list());
        vd.debug(secondNext.list());

        CircularList<Vertex> newLaw = new CircularList<Vertex>();

        moveAllVertexToLavEnd(secondNext, newLaw);
        moveAllVertexToLavEnd(firstNext, newLaw);

        vd.debug(newLaw);
        return newLaw;
    }

    protected static Vertex findOppositeEdgeLav(Set<CircularList<Vertex>> sLav, Edge oppositeEdge, Point2d center) {

        List<Vertex> edgeLavs = findEdgeLavs(sLav, oppositeEdge, null);
        vd.debug(edgeLavs.get(0).point);
        vd.debug(edgeLavs.get(0).list());
        return choseOppositeEdgeLav(edgeLavs, oppositeEdge, center);
    }

    protected static Vertex choseOppositeEdgeLav(List<Vertex> edgeLavs, Edge oppositeEdge, Point2d center) {
        if (edgeLavs.size() == 0) {
            return null;
        } else if (edgeLavs.size() == 1) {
            return edgeLavs.get(0);
        }

        Point2d edgeStart = oppositeEdge.getBegin();
        Vector2d edgeNorm = oppositeEdge.getNorm();
        Vector2d centerVector = new Vector2d(center);
        centerVector.sub(edgeStart);
        double centerDot = edgeNorm.dot(centerVector);

        for (Vertex end : edgeLavs) {
            Vertex begin = end.previous();

            Vector2d beginVector = new Vector2d(begin.point);
            Vector2d endVector = new Vector2d(end.point);

            beginVector.sub(edgeStart);
            endVector.sub(edgeStart);

            double beginDot = edgeNorm.dot(beginVector);
            double endDot = edgeNorm.dot(endVector);

            if (beginDot < centerDot && centerDot < endDot || beginDot > centerDot && centerDot > endDot) {
                // return begin;
                return end;
            }

        }
        return null;
    }

    private static List<Vertex> findEdgeLavs(Set<CircularList<Vertex>> sLav, Edge oppositeEdge,
            CircularList<Vertex> skippedLav) {

        List<Vertex> edgeLavs = new ArrayList<Vertex>();

        for (CircularList<Vertex> lav : sLav) {
            if (lav.equals(skippedLav)) {
                continue;
            }

            Vertex vertexInLav = getEdgeInLav(lav, oppositeEdge);
            if (vertexInLav != null) {
                edgeLavs.add(vertexInLav);
            }
        }
        return edgeLavs;
    }

    /**
     * Try to find index of last vertex after opposite edge is found. Index is
     * calculated relatively from given starting vertex.
     * 
     * @param vertex
     * @param oppositeEdge
     * @return
     */
    protected static int findSplitIndex(Vertex vertex, Edge oppositeEdge) {

        int sizeLav = vertex.list().size();

        Vertex nextVertex = vertex;

        for (int i = 0; i < sizeLav; i++) {

            Vertex currentVertex = nextVertex;

            if (oppositeEdge.equals(currentVertex.previousEdge) || oppositeEdge.equals(currentVertex.previous().nextEdge)) {
                return i;
            }

            nextVertex = nextVertex.next();
        }
        return -1;
    }

    /**
     * Take next lav vertex _AFTER_ given edge, Find vertex is always on RIGHT
     * site of edge.
     * 
     * @param lav
     * @param oppositeEdge
     * @return
     */
    private static Vertex getEdgeInLav(CircularList<Vertex> lav, Edge oppositeEdge) {
        // FIXME jUNIT
        for (Vertex node : lav) {
            // XXX is it correct? not previous.e_b
            if (oppositeEdge.equals(node.previousEdge) || oppositeEdge.equals(node.previous().nextEdge)) {
                return node;
            }
        }
        return null;
    }

    private static void addFaceBack(Vertex newVertex, Vertex va, Vertex vb) {
        vd.debug(va.rightFace);
        // back face
        FaceNode fn = new FaceNode();
        fn.v = newVertex;
        va.rightFace.addPush(fn);
        vd.debug(fn);
        vd.debug(vb.leftFace);
        connectList(fn, vb.leftFace);

    }

    private static FaceNode addFaceRight(Vertex newVertex, Vertex vb) {
        FaceNode fn = new FaceNode();
        fn.v = newVertex;
        vb.rightFace.addPush(fn);
        newVertex.rightFace = fn;

        return fn;
    }

    private static FaceNode addFaceLeft(Vertex newVertex, Vertex va) {
        FaceNode fn = new FaceNode();
        fn.v = newVertex;
        va.leftFace.addPush(fn);
        newVertex.leftFace = fn;

        return fn;
    }

    private static void connectList(FaceNode rightFace, FaceNode leftFace) {

        vd.debug((FaceQueue) rightFace.list());

        if (((FaceQueue) leftFace.list()).isBorder()) {
            leftFace.addQueue(rightFace);
        }
        rightFace.addQueue(leftFace);
    }

    /**
     *
     */
    public static Comparator<SkeletonEvent> distanseComparator = new Comparator<SkeletonEvent>() {
        @Override
        public int compare(SkeletonEvent pV1, SkeletonEvent pV2) {
            return Double.compare(pV1.getDistance(), pV2.getDistance());
        }
    };

    private static double calcDistance(Point2d pIntersect, Edge e_i) {
        Vector2d edge = new Vector2d(e_i.getEnd());
        edge.sub(e_i.getBegin());

        Point2d intersect = new Point2d(pIntersect);
        intersect.sub(e_i.getBegin());

        Vector2d pointOnVector = Algebra.orthogonalProjection(edge, intersect);

        return distance(intersect, pointOnVector);
    }

    /**
     * Computes the distance between this point and point p1.
     * 
     * @param p0
     * 
     * @param p1
     *            the other point
     * @return
     */
    private static double distance(Tuple2d p0, Tuple2d p1) {
        double dx, dy;

        dx = p0.x - p1.x;
        dy = p0.y - p1.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    /**
     * @param p
     * @param e1
     * @param e2
     * @return Bisector
     */
    public static Ray2d calcBisector(Point2d p, Edge e1, Edge e2) {

        Vector2d norm1 = e1.getNorm();
        Vector2d norm2 = e2.getNorm();

        Vector2d bisector = calcVectorBisector(norm1, norm2);

        return new Ray2d(p, bisector);
    }

    private static Vector2d calcVectorBisector(Vector2d norm1, Vector2d norm2) {

        return Vector2dUtil.bisectorNormalized(norm1, norm2);
    }

    public static void main(String[] args) {
        List<Point2d> polygon = new ArrayList<Point2d>();
        polygon.add(new Point2d(0, 0));
        polygon.add(new Point2d(1, 0));
        polygon.add(new Point2d(1, 1));
        polygon.add(new Point2d(0, 1));

        skeleton(polygon);
    }

    /**
     * Output of Skeleton algorithm.
     * 
     * @author Tomasz Kędziora (Kendzi)
     */
    public static class SkeletonOutput {

        /**
         * Edges of polygon.
         */
        private Map<PolygonList2d, LineSegment2d> edges = new HashMap<PolygonList2d, LineSegment2d>();

        /**
         * Faces generated by Skeleton algorithm.
         */
        private List<PolygonList2d> faces = new ArrayList<PolygonList2d>();

        /**
         * Distance points from edges.
         */
        private Map<Point2d, Double> distance = new HashMap<Point2d, Double>();

        /**
         * @return the edges
         */
        public Map<PolygonList2d, LineSegment2d> getEdges() {
            return edges;
        }

        /**
         * @param edges
         *            the edges to set
         */
        public void setEdges(Map<PolygonList2d, LineSegment2d> edges) {
            this.edges = edges;
        }

        /**
         * @return the faces
         */
        public List<PolygonList2d> getFaces() {
            return faces;
        }

        /**
         * @param faces
         *            the faces to set
         */
        public void setFaces(List<PolygonList2d> faces) {
            this.faces = faces;
        }

        /**
         * @return the distance
         */
        public Map<Point2d, Double> getDistance() {
            return distance;
        }

        /**
         * @param distance
         *            the distance to set
         */
        public void setDistance(Map<Point2d, Double> distance) {
            this.distance = distance;
        }

    }

}
