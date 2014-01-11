package kendzi.math.geometry.skeleton;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.vecmath.Point2d;

import kendzi.math.geometry.bbox.Bbox2d;
import kendzi.math.geometry.polygon.PolygonList2d;
import kendzi.math.geometry.skeleton.Skeleton.SkeletonOutput;
import kendzi.math.geometry.skeleton.debug.DV;
import kendzi.math.geometry.skeleton.debug.DisplaySkeletonOut;

public class SkeletonTestUtil {


    public static void assertOutlineInSkelet(List<Point2d> polygon, SkeletonOutput sk) {
        Set<Point2d> outline = new HashSet<Point2d>(polygon);

        outPoint: for (Point2d out : outline) {
            List<PolygonList2d> faces2 = sk.getFaces();
            for (PolygonList2d polygonList2d : faces2) {
                List<Point2d> points = polygonList2d.getPoints();
                for (Point2d point2d : points) {
                    if (point2d.equals(out)) {
                        continue outPoint;
                    }
                }
            }
        }
    }

    public static void validate(List<Point2d> polygon, SkeletonOutput sk) {
        writeExpectedOutput(polygon, sk);
        assertInBbox(polygon, sk);
        assertOutlineInSkelet(polygon, sk);
    }

    public static void assertInBbox(List<Point2d> polygon, SkeletonOutput sk) {

        Bbox2d bbox = new Bbox2d();
        for (Point2d point2d : polygon) {
            bbox.addPoint(point2d);
        }

        List<PolygonList2d> faces2 = sk.getFaces();
        for (PolygonList2d polygonList2d : faces2) {
            List<Point2d> points = polygonList2d.getPoints();
            for (Point2d point2d : points) {
                assertTrue("point " + point2d + " not in bbox " + bbox, bbox.isInside(point2d));
            }
        }
    }

    public static void assertExpectedPoints(List<Point2d> expectedList, List<Point2d> givenList) {
        StringBuffer sb = new StringBuffer();
        for (Point2d expected : expectedList) {
            if (!containsEpsilon(givenList, expected)) {
                sb.append(String.format("can't find expected point (%s, %s) in given list\n", expected.x, expected.y));
            }
        }

        for (Point2d given : givenList) {
            if (!containsEpsilon(expectedList, given)) {
                sb.append(String.format("can't find given point (%s, %s) in expected list\n", given.x, given.y));
            }
        }

        if (sb.length() > 0) {
            fail(sb.toString());
        }

        System.out.println("assert ok");
    }

    public static List<Point2d> getFacePoints(SkeletonOutput sk) {

        List<Point2d> ret = new ArrayList<Point2d>();

        for (PolygonList2d polygonList2d : sk.getFaces()) {
            List<Point2d> points = polygonList2d.getPoints();
            for (Point2d point2d : points) {

                if (!containsEpsilon(ret, point2d)) {
                    ret.add(point2d);
                }
            }
        }
        return ret;
    }

    public static void visualizeResults(List<Point2d> polygon, SkeletonOutput sk) {
        DV.debug(polygon);
        DV.debug(new DisplaySkeletonOut(sk));

        DV.block();
    }

    public static void writeExpectedOutput(List<Point2d> polygon, SkeletonOutput sk) {
        // to generate expected output

        List<Point2d> ret = new ArrayList<Point2d>();

        List<PolygonList2d> faces = sk.getFaces();
        for (PolygonList2d polygonList2d : faces) {
            for (Point2d point2d : polygonList2d.getPoints()) {
                if (!containsEpsilon(polygon, point2d)) {

                    if (!containsEpsilon(ret, point2d)) {
                        ret.add(point2d);
                    }
                }
            }
        }

        Comparator c = new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                Point2d p1 = (Point2d) o1;
                Point2d p2 = (Point2d) o2;

                if (p1.x == p2.x) {
                    if (p1.y == p2.y) {
                        return 0;
                    } else {
                        return p1.y < p2.y ? -1 : 1;
                    }
                } else {
                    return p1.x < p2.x ? -1 : 1;
                }
            }

        };

        Collections.sort(ret, c);

        System.out.println("List<Point2d> expected = new ArrayList<Point2d>();");
        for (Point2d point2d : ret) {
            System.out.println(String.format("expected.add(p(%.6f, %.6f));", point2d.x, point2d.y));
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

    public static boolean containsEpsilon(List<Point2d> list, Point2d p) {
        for (Point2d l : list) {
            if (equalEpsilon(l.x, p.x) && equalEpsilon(l.y, p.y)) {
                return true;
            }
        }
        return false;
    }

    public static boolean equalEpsilon(double d1, double d2) {
        return Math.abs(d1 - d2) < 5E-6;
    }

    public static Point2d p(double x, double y) {
        return new Point2d(x, y);
    }
}
