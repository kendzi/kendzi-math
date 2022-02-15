package kendzi.math.geometry.polygon;

import java.util.ArrayList;
import java.util.List;

import org.ejml.simple.SimpleMatrix;
import org.joml.Vector2dc;

import kendzi.math.geometry.point.TransformationMatrix2d;

public class PolygonWithHolesList2dUtil {
    private PolygonWithHolesList2dUtil() {
        // Hide constructor
    }

    public static PolygonWithHolesList2d transform(PolygonWithHolesList2d polygon, SimpleMatrix transformMatrix) {

        PolygonList2d outer = polygon.getOuter();

        List<Vector2dc> outerList = TransformationMatrix2d.transformList(outer.getPoints(), transformMatrix);

        List<PolygonList2d> inner = polygon.getInner();

        List<PolygonList2d> innerLists = null;

        if (inner != null) {
            innerLists = new ArrayList<>();

            for (PolygonList2d i : inner) {
                innerLists.add(new PolygonList2d(TransformationMatrix2d.transformList(i.getPoints(), transformMatrix)));
            }
        }
        return new PolygonWithHolesList2d(new PolygonList2d(outerList), innerLists);
    }

    public static List<List<Vector2dc>> getListOfHolePoints(PolygonWithHolesList2d polygon) {
        List<List<Vector2dc>> ret = new ArrayList<>();
        List<PolygonList2d> inner = polygon.getInner();
        if (inner != null) {
            for (PolygonList2d p : inner) {
                ret.add(p.getPoints());
            }
        }
        return ret;
    }
}
