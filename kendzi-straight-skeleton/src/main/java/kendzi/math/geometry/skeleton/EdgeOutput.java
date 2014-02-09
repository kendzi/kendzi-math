package kendzi.math.geometry.skeleton;

import kendzi.math.geometry.polygon.PolygonList2d;
import kendzi.math.geometry.skeleton.circular.Edge;

public class EdgeOutput {
    // int edgeNumber;
    // int polygonNumber
    private Edge edge;
    private PolygonList2d polygon;

    public Edge getEdge() {
        return edge;
    }

    public void setEdge(Edge edge) {
        this.edge = edge;
    }

    public PolygonList2d getPolygon() {
        return polygon;
    }

    public void setPolygon(PolygonList2d polygon) {
        this.polygon = polygon;
    }

}
