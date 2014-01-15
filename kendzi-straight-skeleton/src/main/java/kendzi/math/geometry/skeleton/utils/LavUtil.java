package kendzi.math.geometry.skeleton.utils;

import java.util.ArrayList;
import java.util.List;

import kendzi.math.geometry.skeleton.circular.CircularList;
import kendzi.math.geometry.skeleton.circular.Vertex;

import org.apache.log4j.Logger;

public class LavUtil {

    /** Log. */
    private static final Logger log = Logger.getLogger(LavUtil.class);

    /**
     * Check if two vertex are in the same lav.
     * 
     * @param v1
     *            vertex 1
     * @param v2
     *            vertex 2
     * @return if two vertex are in the same lav
     */
    public static boolean isSameLav(Vertex v1, Vertex v2) {
        if (v1.list() == null || v2.list() == null) {
            return false;
        }
        return v1.list() == v2.list();
    }

    public static void removeFromLav(Vertex vertex) {
        if (vertex == null || vertex.list() == null) {
            // if removed or not in list, skip
            return;
        }

        vertex.remove();
    }

    /**
     * Cuts all vertex after given startVertex and before endVertex. start and
     * and vertex are _included_ in cut result.
     * 
     * @param startVertex
     *            start vertex
     * @param endVertex
     *            end vertex
     * @return list of vertex in the middle between start and end vertex
     */
    public static List<Vertex> cutLavPart(Vertex startVertex, Vertex endVertex) {

        if (log.isDebugEnabled()) {
            log.debug("cutLavPart: startVertex: " + startVertex.getPoint() + ", endVertex: " + endVertex.getPoint() + ", lav: "
                    + lavToString(startVertex));
        }

        List<Vertex> ret = new ArrayList<Vertex>();

        int size = startVertex.list().size();

        Vertex next = startVertex;

        for (int i = 0; i < size - 1; i++) {

            Vertex current = next;

            next = current.next();

            current.remove();

            ret.add(current);

            if (current == endVertex) {
                return ret;
            }
        }

        throw new IllegalStateException("end vertex can't be found in start vertex lav");
    }

    public static String lavToString(Vertex startVertex) {
        StringBuffer sb = new StringBuffer();

        int size = startVertex.list().size();
        Vertex next = startVertex;

        for (int i = 0; i < size - 1; i++) {
            sb.append(next.getPoint());
            sb.append(", ");

            next = next.next();
        }

        return sb.toString();
    }

    /**
     * Split given lav into two new lavs. Given vertex is not included in result
     * lavs. Split index is related from given vertex.
     * 
     * @param vertex
     *            vertex with lav
     * @param splitIndex
     *            split index
     * @return two new lavs created from split
     */
    public static SplitSlavs splitLav(Vertex vertex, int splitIndex) {

        CircularList<Vertex> newLawLeft = new CircularList<Vertex>();
        CircularList<Vertex> newLawRight = new CircularList<Vertex>();

        int sizeLav = vertex.list().size();

        if (splitIndex < 3 || splitIndex > sizeLav - 2) {
            throw new RuntimeException(String.format(
                    "After split each lav need to have at least two nodes! Split index: %s lav size: %s", splitIndex, sizeLav));
        }

        // skip first vertex, it will be skip in result
        Vertex nextVertex = vertex.next();

        for (int i = 1; i < sizeLav; i++) {

            Vertex currentVertex = nextVertex;
            nextVertex = nextVertex.next();

            currentVertex.remove();

            if (i < splitIndex) {
                newLawRight.addLast(currentVertex);
            } else {
                newLawLeft.addLast(currentVertex);
            }
        }

        return new SplitSlavs(newLawLeft, newLawRight);
    }

    public static class SplitSlavs {
        private CircularList<Vertex> newLawLeft = new CircularList<Vertex>();
        private CircularList<Vertex> newLawRight = new CircularList<Vertex>();

        public CircularList<Vertex> getNewLawLeft() {
            return newLawLeft;
        }

        public CircularList<Vertex> getNewLawRight() {
            return newLawRight;
        }

        public SplitSlavs(CircularList<Vertex> newLawLeft, CircularList<Vertex> newLawRight) {
            this.newLawLeft = newLawLeft;
            this.newLawRight = newLawRight;
        }
    }

    /**
     * Moves all nodes from given vertex lav, to new lav. All moved nodes are
     * added at the end of lav. The lav end is determined by first added vertex
     * to lav.
     * 
     * @param vertex
     *            vertex
     * @param newLaw
     *            new lav
     */
    public static void moveAllVertexToLavEnd(Vertex vertex, CircularList<Vertex> newLaw) {
        int size = vertex.list().size();
        for (int i = 0; i < size; i++) {
            Vertex ver = vertex;
            vertex = vertex.next();
            ver.remove();
            newLaw.addLast(ver);
        }
    }

    /**
     * Add all vertex from "merged" lav into "base" lav. Vertex are added before
     * base vertex. Merged vertex order is reversed.
     * 
     * @param base
     *            vertex from lav where vertex will be added
     * @param merged
     *            vertex from lav where vertex will be removed
     */
    public static void mergeBeforeBaseVertex(Vertex base, Vertex merged) {

        if (log.isDebugEnabled()) {
            log.debug("base: " + base.getPoint() + ", merged: " + merged.getPoint() + ", lavs: base" + lavToString(base)
                    + " merged" + lavToString(merged));
        }

        int size = merged.list().size();

        for (int i = 0; i < size; i++) {
            Vertex nextMerged = merged.next();
            nextMerged.remove();

            base.addPrevious(nextMerged);
        }
    }

}
