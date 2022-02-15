/*
 * This software is provided "AS IS" without a warranty of any kind. You use it
 * on your own risk and responsibility!!! This file is shared under BSD v3
 * license. See readme.txt and BSD3 file for details.
 */

package kendzi.math.geometry.skeleton;

import static kendzi.math.geometry.skeleton.utils.LavUtil.cutLavPart;
import static kendzi.math.geometry.skeleton.utils.LavUtil.isSameLav;
import static kendzi.math.geometry.skeleton.utils.LavUtil.lavToString;
import static kendzi.math.geometry.skeleton.utils.LavUtil.mergeBeforeBaseVertex;
import static kendzi.math.geometry.skeleton.utils.LavUtil.moveAllVertexToLavEnd;
import static kendzi.math.geometry.skeleton.utils.LavUtil.removeFromLav;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joml.Vector2d;
import org.joml.Vector2dc;

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
import kendzi.math.geometry.skeleton.events.chains.ChainEnds;
import kendzi.math.geometry.skeleton.events.chains.EdgeChain;
import kendzi.math.geometry.skeleton.events.chains.SingleEdgeChain;
import kendzi.math.geometry.skeleton.events.chains.SplitChain;
import kendzi.math.geometry.skeleton.path.FaceNode;
import kendzi.math.geometry.skeleton.path.FaceQueue;
import kendzi.math.geometry.skeleton.path.FaceQueueUtil;
import kendzi.math.geometry.skeleton.utils.ValidateUtil;

/**
 * Straight skeleton algorithm implementation. Base on highly modified Petr
 * Felkel and Stepan Obdrzalek algorithm.
 *
 * @author Tomasz Kedziora (Kendzi)
 *
 */
public class Skeleton {

    /** Log. */
    private static final Logger log = LogManager.getLogger(Skeleton.class);

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

    public static SkeletonOutput skeleton(List<Vector2dc> polygon) {
        return skeleton(polygon, null);
    }

    public static SkeletonOutput skeleton(List<Vector2dc> polygon, List<List<Vector2dc>> holes) {
        return skeleton(polygon, holes, new SkeletonConfiguration());
    }

    public static SkeletonOutput skeleton(List<Vector2dc> polygon, List<List<Vector2dc>> holes,
            SkeletonConfiguration skeletonConfiguration) {

        if (holes == null) {
            holes = Collections.emptyList();
        }

        polygon = initPolygon(polygon, skeletonConfiguration);
        holes = initHoles(holes, skeletonConfiguration);

        PriorityQueue<SkeletonEvent> queue = new PriorityQueue<>(3, new SkeletonEventDistanseComparator());
        Set<CircularList<Vertex>> sLav = new HashSet<>();
        List<SkeletonEvent> processedEvents = new ArrayList<>();
        List<FaceQueue> faces = new ArrayList<>();
        List<Edge> edges = new ArrayList<>();

        initSlav(polygon, sLav, edges, faces);

        for (List<Vector2dc> inner : holes) {
            initSlav(inner, sLav, edges, faces);
        }

        initEvents(sLav, queue, edges);

        debugInit(polygon, holes, sLav, queue);

        int count = 0;

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
                vd.debug(event);
                processedEvents.add(event);
                vd.debug(sLav);
                vd.debugSlav(sLav);
                vd.debugProcessedEvents(processedEvents);

                if (event instanceof EdgeEvent) {
                    throw new IllegalStateException("all edge events should be converted to MultiEdgeEvents for given level");

                } else if (event instanceof SplitEvent) {
                    throw new IllegalStateException("all split events should be converted to MultiSplitEvents for given level");

                } else if (event instanceof MultiSplitEvent) {

                    multiSplitEvent((MultiSplitEvent) event, sLav, queue, edges);
                    continue;
                } else if (event instanceof PickEvent) {

                    pickEvent((PickEvent) event, sLav, queue, edges);
                    continue;
                } else if (event instanceof MultiEdgeEvent) {

                    multiEdgeEvent((MultiEdgeEvent) event, sLav, queue, edges);
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

        return addFacesToOutput(faces);
    }

    private static List<List<Vector2dc>> initHoles(List<List<Vector2dc>> holes, SkeletonConfiguration skeletonConfiguration) {

        if (holes == null) {
            return null;
        }

        holes = makeClockwise(holes);

        if (skeletonConfiguration.isDebug()) {
            // add names for points

            List<List<Vector2dc>> newHoles = new ArrayList<>();
            int h = 0;
            for (List<Vector2dc> hole : holes) {
                newHoles.add(debugNames(hole, "h" + h + "_p"));
                h++;
            }
            return newHoles;
        }
        return holes;
    }

    private static List<Vector2dc> initPolygon(List<Vector2dc> polygon, SkeletonConfiguration skeletonConfiguration) {

        if (polygon == null) {
            throw new IllegalArgumentException("polygon can't be null");
        }

        if (polygon.get(0).equals(polygon.get(polygon.size() - 1))) {
            throw new IllegalArgumentException("polygon can't start and end with the same point");
        }

        polygon = makeCounterClockwise(polygon);

        if (skeletonConfiguration.isDebug()) {
            // add names for points
            polygon = debugNames(polygon, "p");
        }

        return polygon;
    }

    private static void debugInit(List<Vector2dc> polygon, List<List<Vector2dc>> holes, Set<CircularList<Vertex>> sLav,
            PriorityQueue<SkeletonEvent> queue) {

        vd.clear();
        vd.debug(polygon);
        vd.debugNames(polygon);

        for (List<Vector2dc> hole : holes) {
            vd.debug(hole);
            vd.debugNames(hole);
        }

        for (CircularList<Vertex> lav : sLav) {
            vd.debug(lav);
        }

        vd.debug(queue);
    }

    private static void processTwoNodeLavs(Set<CircularList<Vertex>> sLav) {
        vd.debug(sLav);
        for (CircularList<Vertex> lav : sLav) {
            if (lav.size() == 2) {

                Vertex first = lav.first();
                Vertex last = first.next();

                FaceQueueUtil.connectQueues(first.leftFace, last.rightFace);
                FaceQueueUtil.connectQueues(first.rightFace, last.leftFace);

                first.setProcessed(true);
                last.setProcessed(true);

                removeFromLav(first);
                removeFromLav(last);
            }
        }
        vd.debug(sLav);
    }

    private static void removeEmptyLav(Set<CircularList<Vertex>> sLav) {
        for (Iterator<CircularList<Vertex>> it = sLav.iterator(); it.hasNext();) {
            if (it.next().size() == 0) {
                it.remove();
            }
        }
    }

    private static void multiEdgeEvent(MultiEdgeEvent event, Set<CircularList<Vertex>> sLav, PriorityQueue<SkeletonEvent> queue,
            List<Edge> edges) {

        Vector2dc center = event.getPoint();
        List<EdgeEvent> edgeList = event.getChain().getEdgeList();

        Vertex previousVertex = event.getChain().getPreviousVertex();
        previousVertex.setProcessed(true);

        Vertex nextVertex = event.getChain().getNextVertex();
        nextVertex.setProcessed(true);

        Ray2d bisector = calcBisector(center, previousVertex.previousEdge, nextVertex.nextEdge);
        Vertex edgeVertex = new Vertex(center, event.getDistance(), bisector, previousVertex.previousEdge, nextVertex.nextEdge);

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

            Vertex leftVertex = edgeEvent.getPreviousVertex();
            leftVertex.setProcessed(true);
            removeFromLav(leftVertex);

            Vertex rightVertex = edgeEvent.getNextVertex();
            rightVertex.setProcessed(true);
            removeFromLav(rightVertex);

            addFaceBack(edgeVertex, leftVertex, rightVertex);
        }
    }

    private static void pickEvent(PickEvent event, Set<CircularList<Vertex>> sLav, PriorityQueue<SkeletonEvent> queue,
            List<Edge> edges) {

        Vector2dc center = event.getPoint();
        List<EdgeEvent> edgeList = event.getChain().getEdgeList();

        // lav will be removed so it is final vertex.
        Vertex pickVertex = new Vertex(center, event.getDistance(), null, null, null);
        pickVertex.setProcessed(true);

        addMultiBackFaces(edgeList, pickVertex);
    }

    private static void multiSplitEvent(MultiSplitEvent event, Set<CircularList<Vertex>> sLav,
            PriorityQueue<SkeletonEvent> queue, List<Edge> edges) {

        List<Chain> chains = event.getChains();
        final Vector2dc center = event.v;

        createOppositeEdgeChains(sLav, chains, center);

        Comparator<ChainEnds> multiSplitSorter = new Comparator<ChainEnds>() {

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

            Vertex newVertex = createMultiSplitVertex(chainBegin.getNextEdge(), chainEnd.getPreviousEdge(), center,
                    event.getDistance());

            // Split and merge lavs...
            vd.debugSlav(sLav);
            vd.debug(sLav);
            vd.debug(center);

            Vertex beginNextVertex = chainBegin.getNextVertex();
            Vertex endPreviousVertex = chainEnd.getPreviousVertex();

            vd.debug(endPreviousVertex.getPoint());
            correctBisectorDirection(newVertex.getBisector(), beginNextVertex, endPreviousVertex, chainBegin.getNextEdge(),
                    chainEnd.getPreviousEdge());

            if (isSameLav(beginNextVertex, endPreviousVertex)) {
                /*
                 * if vertex are in same lav we need to cut part of lav in the
                 * middle of vertex and create new lav from that points
                 */

                List<Vertex> lavPart = cutLavPart(beginNextVertex, endPreviousVertex);

                CircularList<Vertex> lav = new CircularList<>();
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
                chainBegin.getCurrentVertex().setProcessed(true);
            }
            if (chainEnd.getCurrentVertex() != null) {
                chainEnd.getCurrentVertex().setProcessed(true);
            }
        }

        if (debug) {
            validateLavsEdges(sLav);
        }

        vd.debug(sLav);
    }

    private static void correctBisectorDirection(Ray2d bisector, Vertex beginNextVertex, Vertex endPreviousVertex,
            Edge beginEdge, Edge endEdge) {

        /*
         * New bisector for vertex is created using connected edges. For
         * parallel edges numerical error may appear and direction of created
         * bisector is wrong. It for parallel edges direction of edge need to be
         * corrected using location of vertex.
         */
        Edge beginEdge2 = beginNextVertex.getPreviousEdge();
        Edge endEdge2 = endPreviousVertex.getNextEdge();

        if (beginEdge != beginEdge2 || endEdge != endEdge2) {
            throw new RuntimeException();
        }

        /*
         * Check if edges are parallel and in opposite direction to each other.
         */

        if (beginEdge.getNorm().dot(endEdge.getNorm()) < -0.97) {

            Vector2d n1 = Vector2dUtil.fromTo(endPreviousVertex.getPoint(), bisector.A).normalize();
            Vector2d n2 = Vector2dUtil.fromTo(bisector.A, beginNextVertex.getPoint()).normalize();
            Vector2d bisectorPrediction = calcVectorBisector(n1, n2);
            vd.debug(bisector);

            if (bisector.U.dot(bisectorPrediction) < 0) {
                /*
                 * Bisector is calculated in opposite direction to edges and
                 * center.
                 */
                bisector.U = bisector.U.negate(new Vector2d());
            }
        }
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
                 * create vertex clone to store additional back face.
                 */
                beginVertex = createOppositeEdgeVertex(newVertex);

                vd.debug(beginVertex.rightFace);
                /* same face in two vertex, original and in opposite edge clone */
                newVertex.rightFace = beginVertex.rightFace;
                lastFaceNode = beginVertex.leftFace;
                vd.debug(beginVertex.rightFace);
            } else {
                // face queue exist simply assign it to new node

                ValidateUtil.validateNull(newVertex.rightFace, "newVertex.rightFace");
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
                 * create vertex clone to store additional back face.
                 */
                endVertex = createOppositeEdgeVertex(newVertex);

                vd.debug(endVertex.leftFace);
                /* same face in two vertex, original and in opposite edge clone */
                newVertex.leftFace = endVertex.leftFace;
                lastFaceNode = endVertex.leftFace;
                vd.debug(endVertex.leftFace);
            } else {
                // face queue exist simply assign it to new node

                ValidateUtil.validateNull(newVertex.leftFace, "newVertex.leftFace");
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

        Vertex vertex = new Vertex(newVertex.getPoint(), newVertex.getDistance(), newVertex.getBisector(),
                newVertex.previousEdge, newVertex.nextEdge);

        // create new empty node queue
        FaceNode fn = new FaceNode(vertex);
        vertex.leftFace = fn;
        vertex.rightFace = fn;

        // add one node for queue to present opposite site of edge split event
        FaceQueue rightFace = new FaceQueue();
        rightFace.addFirst(fn);

        return vertex;
    }

    private static void createOppositeEdgeChains(Set<CircularList<Vertex>> sLav, List<Chain> chains, Vector2dc center) {
        /*
         * Add chain created from opposite edge, this chain have to be
         * calculated during processing event because lav could change during
         * processing another events on the same level
         */
        Set<Edge> oppositeEdges = new HashSet<>();

        List<Chain> oppositeEdgeChains = new ArrayList<>();
        List<Chain> chainsForRemoval = new ArrayList<>();

        for (Chain chain : chains) {
            // add opposite edges as chain parts
            if (chain instanceof SplitChain) {
                SplitChain splitChain = (SplitChain) chain;
                Edge oppositeEdge = splitChain.getOppositeEdge();
                if (oppositeEdge != null && !oppositeEdges.contains(oppositeEdge)) {
                    // find lav vertex for opposite edge

                    Vertex nextVertex = findOppositeEdgeLav(sLav, oppositeEdge, center);
                    if (nextVertex != null) {

                        oppositeEdgeChains.add(new SingleEdgeChain(oppositeEdge, nextVertex));
                    } else {
                        // XXX
                        findOppositeEdgeLav(sLav, oppositeEdge, center);
                        chainsForRemoval.add(chain);
                    }

                    oppositeEdges.add(oppositeEdge);
                }
            } else if (chain instanceof EdgeChain) {
                EdgeChain edgeChain = (EdgeChain) chain;
                if (ChainType.SPLIT.equals(edgeChain.getType())) {
                    Edge oppositeEdge = ((EdgeChain) chain).getOppositeEdge();
                    if (oppositeEdge != null) {
                        // XXX never happen?
                        // find lav vertex for opposite edge
                        oppositeEdges.add(oppositeEdge);
                    }
                }
            }
        }

        /*
         * if opposite edge can't be found in active lavs then split chain with
         * that edge should be removed
         */
        chains.removeAll(chainsForRemoval);
        chains.addAll(oppositeEdgeChains);

    }

    private static Vertex createMultiSplitVertex(Edge nextEdge, Edge previousEdge, Vector2dc center, double distance) {

        Ray2d bisector = calcBisector(center, previousEdge, nextEdge);

        // edges are mirrored for event
        return new Vertex(center, distance, bisector, previousEdge, nextEdge);
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
        List<EdgeEvent> edgeCluster = new ArrayList<>();
        List<SplitEvent> splitCluster = new ArrayList<>();
        Set<Vertex> vertexEventsParents = new HashSet<>();

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

        List<EdgeChain> edgeChains = new ArrayList<>();

        while (edgeCluster.size() > 0) {
            /*
             * We need to find all connected edge events, and create chains from
             * them. Two event are assumed as connected if next parent of one
             * event is equal to previous parent of second event.
             */

            edgeChains.add(new EdgeChain(createEdgeChain(edgeCluster)));
        }

        List<Chain> chains = new ArrayList<>(edgeChains);

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
            if (edgeEvent.getPreviousVertex() == splitParent || edgeEvent.getNextVertex() == splitParent) {
                return true;
            }
        }

        return false;
    }

    protected static ArrayList<EdgeEvent> createEdgeChain(List<EdgeEvent> edgeCluster) {

        ArrayList<EdgeEvent> edgeList = new ArrayList<>();

        edgeList.add(edgeCluster.remove(0));

        // find all successors of edge event
        // find all predecessors of edge event

        // XXX check in future
        loop: do {

            Vertex beginVertex = edgeList.get(0).getPreviousVertex();
            Vertex endVertex = edgeList.get(edgeList.size() - 1).getNextVertex();

            for (int i = 0; i < edgeCluster.size(); i++) {
                EdgeEvent edge = edgeCluster.get(i);
                if (edge.getPreviousVertex() == endVertex) {
                    // edge should be added as last in chain
                    edgeCluster.remove(i);
                    edgeList.add(edge);
                    continue loop;
                } else if (edge.getNextVertex() == beginVertex) {
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

        List<SkeletonEvent> ret = new ArrayList<>();

        Set<Vertex> parentGroup = new HashSet<>();

        while (levelEvents.size() > 0) {
            parentGroup.clear();

            SkeletonEvent event = levelEvents.remove(0);
            Vector2dc eventCenter = event.getPoint();
            double distance = event.getDistance();

            addEventToGroup(parentGroup, event);

            List<SkeletonEvent> cluster = new ArrayList<>();
            cluster.add(event);

            // XXX use interator?
            for (int j = 0; j < levelEvents.size(); j++) {
                SkeletonEvent test = levelEvents.get(j);

                if (isEventInGroup(parentGroup, test)) {
                    /*
                     * Because of numerical errors split event and edge event
                     * can appear in slight different point. Epsilon can be
                     * apply to level but event point can move rapidly even for
                     * little changes in level. If two events for the same level
                     * share the same parent, they should be merge together.
                     */
                    vd.debug(test.v);
                    vd.debug(eventCenter);

                    cluster.add(levelEvents.remove(j));
                    addEventToGroup(parentGroup, test);
                    j--;
                } else if (near(eventCenter, test.v, SPLIT_EPSILON)) {
                    // group all event when the result point are near each other
                    cluster.add(levelEvents.remove(j));
                    addEventToGroup(parentGroup, test);
                    j--;
                }
            }

            /*
             * More then one event share the same result point, we need to
             * create new level event.
             */
            ret.add(createLevelEvent(eventCenter, distance, cluster));
        }
        return ret;
    }

    private static boolean isEventInGroup(Set<Vertex> parentGroup, SkeletonEvent event) {
        if (event instanceof SplitEvent) {
            return parentGroup.contains(((SplitEvent) event).getParent());
        } else if (event instanceof EdgeEvent) {
            return parentGroup.contains(((EdgeEvent) event).getPreviousVertex())
                    || parentGroup.contains(((EdgeEvent) event).getNextVertex());
        }
        return false;
    }

    private static void addEventToGroup(Set<Vertex> parentGroup, SkeletonEvent event) {
        if (event instanceof SplitEvent) {
            parentGroup.add(((SplitEvent) event).getParent());
        } else if (event instanceof EdgeEvent) {
            parentGroup.add(((EdgeEvent) event).getPreviousVertex());
            parentGroup.add(((EdgeEvent) event).getNextVertex());
        }
    }

    /**
     * @param eventCluster
     *            list of events which meet in single point.
     * @param eventCenter
     * @return
     */
    private static SkeletonEvent createLevelEvent(Vector2dc eventCenter, double distance, List<SkeletonEvent> eventCluster) {

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

    static boolean near(Vector2dc p1, Vector2dc p2, double epsilon) {
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

        List<SkeletonEvent> level = new ArrayList<>();

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

    private static class DebugVector2dc extends Vector2d {
        String name;

        public DebugVector2dc(double x, double y, String name) {
            super(x, y);
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }

    }

    private static List<Vector2dc> debugNames(List<Vector2dc> polygon, String name) {
        List<Vector2dc> ret = new ArrayList<>();
        int i = 0;
        for (Vector2dc point : polygon) {
            ret.add(new DebugVector2dc(point.x(), point.y(), name + i));
            i++;
        }
        return ret;
    }

    private static List<List<Vector2dc>> makeClockwise(List<List<Vector2dc>> holes) {
        if (holes == null) {
            return null;
        }

        List<List<Vector2dc>> ret = new ArrayList<>(holes.size());
        for (List<Vector2dc> hole : holes) {
            if (PolygonUtil.isClockwisePolygon(hole)) {
                ret.add(hole);
            } else {
                ret.add(PolygonUtil.reverse(hole));
            }
        }
        return ret;
    }

    private static List<Vector2dc> makeCounterClockwise(List<Vector2dc> polygon) {
        return PolygonUtil.makeCounterClockwise(polygon);
    }

    /**
     * @param polygon
     * @param sLav
     * @param edges
     * @param faces
     */
    private static void initSlav(List<Vector2dc> polygon, Set<CircularList<Vertex>> sLav, List<Edge> edges, List<FaceQueue> faces) {

        CircularList<Edge> edgesList = new CircularList<>();

        int size = polygon.size();
        for (int i = 0; i < size; i++) {
            int j = (i + 1) % size;
            edgesList.addLast(new Edge(polygon.get(i), polygon.get(j)));
        }

        for (Edge edge : edgesList) {

            Edge nextEdge = edge.next();

            Ray2d bisector = calcBisector(edge.getEnd(), edge, nextEdge);

            edge.setBisectorNext(bisector);
            nextEdge.setBisectorPrevious(bisector);

            edges.add(edge);
        }

        CircularList<Vertex> lav = new CircularList<>();
        sLav.add(lav);

        for (Edge edge : edgesList) {

            Edge nextEdge = edge.next();

            Vertex vertex = new Vertex(edge.getEnd(), 0, edge.getBisectorNext(), edge, nextEdge);

            lav.addLast(vertex);
        }

        vd.debug(lav);

        for (Vertex vertex : lav) {
            Vertex next = vertex.next();

            // create face on right site of vertex
            FaceNode rightFace = new FaceNode(vertex);

            FaceQueue faceQueue = new FaceQueue();
            faceQueue.setEdge(vertex.getNextEdge());

            faceQueue.addFirst(rightFace);

            faces.add(faceQueue);

            vertex.rightFace = rightFace;

            // create face on left site of next vertex
            FaceNode leftFace = new FaceNode(next);
            rightFace.addPush(leftFace);
            next.leftFace = leftFace;
        }
    }

    private static SkeletonOutput addFacesToOutput(List<FaceQueue> faces) {

        for (FaceQueue f : faces) {
            vd.debug(f);
        }

        SkeletonOutput output = new SkeletonOutput();

        List<EdgeOutput> edgeOutputs = new ArrayList<>();

        for (FaceQueue face : faces) {
            if (face.getSize() > 0) {

                List<Vector2dc> faceList = new ArrayList<>();

                for (FaceNode fn : face) {
                    faceList.add(fn.getVertex().getPoint());
                    output.getDistance().put(fn.getVertex().getPoint(), fn.getVertex().getDistance());
                }

                PolygonList2d polygon = new PolygonList2d(faceList);

                output.getFaces().add(polygon);
                output.getEdges().put(polygon, new LineSegment2d(face.getEdge().getBegin(), face.getEdge().getEnd()));

                EdgeOutput edgeOutput = new EdgeOutput();
                edgeOutput.setEdge(face.getEdge());
                edgeOutput.setPolygon(polygon);

                edgeOutputs.add(edgeOutput);
            }
        }

        output.setEdgeOutputs(edgeOutputs);

        return output;
    }

    private static void initEvents(Set<CircularList<Vertex>> sLav, PriorityQueue<SkeletonEvent> queue, List<Edge> edges) {

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

        Vector2dc source = vertex.getPoint();

        List<SplitCandidate> calcOppositeEdges = calcOppositeEdges(vertex, edges);

        // check if it is vertex split event
        for (SplitCandidate oppositeEdge : calcOppositeEdges) {

            Vector2dc point = oppositeEdge.getPoint();

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

        Vector2dc point = vertex.getPoint();
        /*
         * We need to chose closer edge event. When two evens appear in epsilon
         * we take both. They will create single MultiEdgeEvent.
         */

        Vector2dc point1 = computeIntersectionBisectors(vertex, nextVertex);
        Vector2dc point2 = computeIntersectionBisectors(previousVertex, vertex);

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

    private static SkeletonEvent createEdgeEvent(Vector2dc point, Vertex previousVertex, Vertex nextVertex) {
        return new EdgeEvent(point, calcDistance(point, previousVertex.nextEdge), previousVertex, nextVertex);
    }

    private static void computeEdgeEvents(Vertex previousVertex, Vertex nextVertex, PriorityQueue<SkeletonEvent> queue) {
        Vector2dc point = computeIntersectionBisectors(previousVertex, nextVertex);
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
    protected static Edge vertexOpositeEdge(Vector2dc point, Edge edge) {

        if (RayUtil.isPointOnRay(point, edge.getBisectorNext(), SPLIT_EPSILON)) {
            return edge;
        }

        if (RayUtil.isPointOnRay(point, edge.getBisectorPrevious(), SPLIT_EPSILON)) {
            return edge.previous();
        }
        return null;
    }

    private static List<SplitCandidate> calcOppositeEdges(Vertex vertex, List<Edge> edges) {

        List<SplitCandidate> ret = new ArrayList<>();

        for (Edge edgeEntry : edges) {

            LineLinear2d edge = edgeEntry.getLineLinear();

            // check if edge is behind bisector
            if (edgeBehindBisector(vertex.getBisector(), edge)) {
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
        /*
         * Simple intersection test between the bisector starting at V and the
         * whole line containing the currently tested line segment ei rejects
         * the line segments laying "behind" the vertex V
         */
        return Ray2d.collide(bisector, edge, SPLIT_EPSILON) == null;
    }

    private static class SplitCandidate {

        private final double distance;
        private final Vector2dc point;
        private final Edge oppositeEdge;
        private final Vector2dc oppositePoint;

        public SplitCandidate(Vector2dc point, double distance, Edge oppositeEdge, Vector2dc oppositePoint) {
            super();
            this.point = point;
            this.distance = distance;
            this.oppositeEdge = oppositeEdge;
            this.oppositePoint = oppositePoint;
        }

        public double getDistance() {
            return distance;
        }

        public Vector2dc getPoint() {
            return point;
        }

        public Edge getOppositeEdge() {
            return oppositeEdge;
        }

        public Vector2dc getOppositePoint() {
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

        Vector2dc edgesCollide = vertexEdge.getLineLinear().collide(edge.getLineLinear());

        if (edgesCollide == null) {
            /*
             * Check should be performed to exclude the case when one of the
             * line segments starting at V is parallel to ei.
             */
            throw new RuntimeException("ups this should not happen");
        }

        LineLinear2d edgesBisectorLine = new LineParametric2d(edgesCollide, edgesBisector).getLinearForm();

        /*
         * Compute the coordinates of the candidate point Bi as the intersection
         * between the bisector at V and the axis of the angle between one of
         * the edges starting at V and the tested line segment ei
         */
        Vector2dc candidatePoint = Ray2d.collide(vertex.getBisector(), edgesBisectorLine, SPLIT_EPSILON);

        if (candidatePoint == null) {
            return null;
        }

        if (edge.getBisectorPrevious().isOnRightSite(candidatePoint, SPLIT_EPSILON)
                && edge.getBisectorNext().isOnLeftSite(candidatePoint, SPLIT_EPSILON)) {

            double distance = calcDistance(candidatePoint, edge);

            if (edge.getBisectorPrevious().isOnLeftSite(candidatePoint, SPLIT_EPSILON)) {

                Vector2dc oppositePoint = edge.getBegin();
                return new SplitCandidate(candidatePoint, distance, null, oppositePoint);
            } else if (edge.getBisectorNext().isOnRightSite(candidatePoint, SPLIT_EPSILON)) {

                Vector2dc oppositePoint = edge.getBegin();
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

    private static Vector2dc computeIntersectionBisectors(Vertex vertexPrevious, Vertex vertexNext) {

        Ray2d bisectorPrevious = vertexPrevious.getBisector();
        Ray2d bisectorNext = vertexNext.getBisector();

        IntersectPoints intersectRays2d = RayUtil.intersectRays2d(bisectorPrevious, bisectorNext);

        Vector2dc intersect = intersectRays2d.getIntersect();

        if (vertexPrevious.getPoint().equals(intersect) || vertexNext.getPoint().equals(intersect)) {
            // skip the same points
            return null;
        }

        return intersect;
    }

    protected static CircularList<Vertex> mergeLav(Vertex firstLav, Vertex secondLav) {

        Vertex firstNext = firstLav.next();
        Vertex secondNext = secondLav.next();

        vd.debug(firstNext.list());
        vd.debug(secondNext.list());

        CircularList<Vertex> newLaw = new CircularList<>();

        moveAllVertexToLavEnd(secondNext, newLaw);
        moveAllVertexToLavEnd(firstNext, newLaw);

        vd.debug(newLaw);
        return newLaw;
    }

    protected static Vertex findOppositeEdgeLav(Set<CircularList<Vertex>> sLav, Edge oppositeEdge, Vector2dc center) {

        List<Vertex> edgeLavs = findEdgeLavs(sLav, oppositeEdge, null);

        for (Vertex vertex : edgeLavs) {
            vd.debug(vertex.list());
            vd.debug(vertex.getPoint());
        }
        vd.debug(oppositeEdge);
        vd.debug(center);
        return choseOppositeEdgeLav(edgeLavs, oppositeEdge, center);
    }

    protected static Vertex choseOppositeEdgeLav(List<Vertex> edgeLavs, Edge oppositeEdge, Vector2dc center) {
        // XXX
        if (edgeLavs.size() == 0) {
            return null;
        } else if (edgeLavs.size() == 1) {
            return edgeLavs.get(0);
        }

        Vector2dc edgeStart = oppositeEdge.getBegin();
        Vector2d edgeNorm = oppositeEdge.getNorm();
        Vector2d centerVector = new Vector2d(center);
        centerVector.sub(edgeStart);
        double centerDot = edgeNorm.dot(centerVector);
        vd.debug(center);
        for (Vertex end : edgeLavs) {

            Vertex begin = end.previous();
            vd.debug(end.getPoint());
            vd.debug(begin.getPoint());
            vd.debug(new LineSegment2d(begin.getPoint(), end.getPoint()));
            Vector2d beginVector = new Vector2d(begin.getPoint());
            Vector2d endVector = new Vector2d(end.getPoint());

            beginVector.sub(edgeStart);
            endVector.sub(edgeStart);

            double beginDot = edgeNorm.dot(beginVector);
            double endDot = edgeNorm.dot(endVector);

            /*
             * Make projection of center, begin and end into edge. Begin and end
             * are vertex chosen by opposite edge (then point to opposite edge).
             * Chose lav only when center is between begin and end. Only one lav
             * should meet criteria.
             */

            if (beginDot < centerDot && centerDot < endDot || beginDot > centerDot && centerDot > endDot) {
                return end;
            }

        }

        // Additional check if center is inside lav
        for (Vertex end : edgeLavs) {
            int size = end.list().size();
            List<Vector2dc> points = new ArrayList<>(size);
            Vertex next = end;
            for (int i = 0; i < size; i++) {
                points.add(next.getPoint());
                next = next.next();
            }
            if (PolygonUtil.isPointInsidePolygon(center, points)) {
                return end;
            }
        }

        throw new IllegalStateException(
                "could not find lav for opposite edge, it could be correct but need some test data to check.");

    }

    private static List<Vertex> findEdgeLavs(Set<CircularList<Vertex>> sLav, Edge oppositeEdge, CircularList<Vertex> skippedLav) {

        List<Vertex> edgeLavs = new ArrayList<>();

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
        FaceNode fn = new FaceNode(newVertex);
        va.rightFace.addPush(fn);
        vd.debug(fn);
        vd.debug(vb.leftFace);
        FaceQueueUtil.connectQueues(fn, vb.leftFace);

    }

    private static FaceNode addFaceRight(Vertex newVertex, Vertex vb) {
        FaceNode fn = new FaceNode(newVertex);
        vb.rightFace.addPush(fn);
        newVertex.rightFace = fn;

        return fn;
    }

    private static FaceNode addFaceLeft(Vertex newVertex, Vertex va) {
        FaceNode fn = new FaceNode(newVertex);
        va.leftFace.addPush(fn);
        newVertex.leftFace = fn;

        return fn;
    }

    /**
     *
     */
    public static class SkeletonEventDistanseComparator implements Comparator<SkeletonEvent> {
        @Override
        public int compare(SkeletonEvent event1, SkeletonEvent event2) {
            return Double.compare(event1.getDistance(), event2.getDistance());
        }
    }

    private static double calcDistance(Vector2dc intersect, Edge currentEdge) {
        Vector2d edge = new Vector2d(currentEdge.getEnd()).sub(currentEdge.getBegin());

        Vector2d vector = new Vector2d(intersect).sub(currentEdge.getBegin());

        Vector2d pointOnVector = Algebra.orthogonalProjection(edge, vector);

        return distance(vector, pointOnVector);
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
    private static double distance(Vector2dc p0, Vector2dc p1) {
        double dx;
        double dy;

        dx = p0.x() - p1.x();
        dy = p0.y() - p1.y();
        return Math.sqrt(dx * dx + dy * dy);
    }

    /**
     * @param p
     * @param e1
     * @param e2
     * @return Bisector
     */
    public static Ray2d calcBisector(Vector2dc p, Edge e1, Edge e2) {

        Vector2d norm1 = e1.getNorm();
        Vector2d norm2 = e2.getNorm();

        Vector2d bisector = calcVectorBisector(norm1, norm2);

        return new Ray2d(p, bisector);
    }

    private static Vector2d calcVectorBisector(Vector2dc norm1, Vector2dc norm2) {

        return Vector2dUtil.bisectorNormalized(norm1, norm2);
    }

}
