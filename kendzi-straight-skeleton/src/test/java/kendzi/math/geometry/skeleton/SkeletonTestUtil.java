package kendzi.math.geometry.skeleton;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.joml.Vector2d;
import org.joml.Vector2dc;

import kendzi.math.geometry.TestUtil;
import kendzi.math.geometry.bbox.Bbox2d;
import kendzi.math.geometry.polygon.PolygonList2d;
import kendzi.math.geometry.skeleton.debug.VisualDebugger;
import kendzi.math.geometry.skeleton.debug.display.DisplaySkeletonOut;

public class SkeletonTestUtil {

    private static VisualDebugger vd = TestUtil.initVisualDebugger();

    public static void assertOutlineInSkelet(List<Vector2dc> polygon, SkeletonOutput sk) {
        Set<Vector2dc> outline = new HashSet<>(polygon);

        outPoint: for (Vector2dc out : outline) {

            for (EdgeOutput edgeOutput : sk.getEdgeOutputs()) {
                PolygonList2d polygonList2d = edgeOutput.getPolygon();
                List<Vector2dc> points = polygonList2d.getPoints();
                for (Vector2dc point2d : points) {
                    if (point2d.equals(out)) {
                        continue outPoint;
                    }
                }
            }
        }
    }

    public static void validate(List<Vector2dc> polygon, SkeletonOutput sk) {
        writeExpectedOutput(polygon, sk);
        assertInBbox(polygon, sk);
        assertOutlineInSkelet(polygon, sk);
    }

    public static void assertInBbox(List<Vector2dc> polygon, SkeletonOutput sk) {

        Bbox2d bbox = new Bbox2d();
        for (Vector2dc point2d : polygon) {
            bbox.addPoint(point2d);
        }

        for (EdgeOutput edgeOutput : sk.getEdgeOutputs()) {

            List<Vector2dc> points = edgeOutput.getPolygon().getPoints();
            for (Vector2dc point2d : points) {
                assertTrue("point " + point2d + " not in bbox " + bbox, bbox.isInside(point2d));
            }
        }
    }

    public static <V extends Vector2dc> void assertExpectedPoints(List<V> expectedList, List<V> givenList) {
        StringBuffer sb = new StringBuffer();
        for (V expected : expectedList) {
            if (!containsEpsilon(givenList, expected)) {
                sb.append(String.format("can't find expected point (%s, %s) in given list\n", expected.x(), expected.y()));
            }
        }

        for (V given : givenList) {
            if (!containsEpsilon(expectedList, given)) {
                sb.append(String.format("can't find given point (%s, %s) in expected list\n", given.x(), given.y()));
            }
        }

        if (sb.length() > 0) {
            fail(sb.toString());
        }

        System.out.println("assert ok");
    }

    public static List<Vector2dc> getFacePoints(SkeletonOutput sk) {

        List<Vector2dc> ret = new ArrayList<>();

        for (EdgeOutput edgeOutput : sk.getEdgeOutputs()) {

            List<Vector2dc> points = edgeOutput.getPolygon().getPoints();
            for (Vector2dc point2d : points) {

                if (!containsEpsilon(ret, point2d)) {
                    ret.add(point2d);
                }
            }
        }
        return ret;
    }

    public static void visualizeResults(List<Vector2dc> polygon, SkeletonOutput sk) {
        vd.debug(polygon);
        vd.debug(new DisplaySkeletonOut(sk));

        vd.block();
    }

    public static void writeExpectedOutput(List<Vector2dc> polygon, SkeletonOutput sk) {
        // to generate expected output

        List<Vector2dc> ret = new ArrayList<>();

        for (EdgeOutput edgeOutput : sk.getEdgeOutputs()) {
            PolygonList2d polygonList2d = edgeOutput.getPolygon();
            for (Vector2dc point2d : polygonList2d.getPoints()) {
                if (!containsEpsilon(polygon, point2d)) {

                    if (!containsEpsilon(ret, point2d)) {
                        ret.add(point2d);
                    }
                }
            }
        }

        Comparator<Vector2dc> c = (p1, p2) -> {

            if (p1.x() == p2.x()) {
                if (p1.y() == p2.y()) {
                    return 0;
                } else {
                    return Double.compare(p1.y(), p2.y());
                }
            } else {
                return Double.compare(p1.x(), p2.x());
            }
        };

        ret.sort(c);

        System.out.println("List<Vector2dc> expected = new ArrayList<Vector2dc>();");
        for (Vector2dc point2d : ret) {
            System.out.printf("expected.add(p(%.6f, %.6f));%n", point2d.x(), point2d.y());
        }
        System.out.println("expected.addAll(polygon);");
    }

    public static String fmt(double d) {
        if (d == (int) d) {
            return String.format("%d", (int) d);
        } else {
            return String.format("%s", d);
        }
    }

    public static <V extends Vector2dc> boolean containsEpsilon(List<V> list, V p) {
        for (Vector2dc l : list) {
            if (p.equals(l, 5e-6)) {
                return true;
            }
        }
        return false;
    }

    public static boolean equalEpsilon(double d1, double d2) {
        return Math.abs(d1 - d2) < 5E-6;
    }

    public static Vector2d p(double x, double y) {
        return new Vector2d(x, y);
    }
}
