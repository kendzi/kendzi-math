/*
 * This software is provided "AS IS" without a warranty of any kind. You use it
 * on your own risk and responsibility!!! This file is shared under BSD v3
 * license. See readme.txt and BSD3 file for details.
 */

package kendzi.math.geometry.skeleton;

import static kendzi.math.geometry.skeleton.SkeletonTestUtil.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.vecmath.Point2d;

import kendzi.math.geometry.TestUtil;
import kendzi.math.geometry.polygon.PolygonList2d;
import kendzi.math.geometry.skeleton.debug.VisualDebugger;

import org.junit.Before;
import org.junit.Test;

public class SkeletonLevelEventsTest {

    private static VisualDebugger vd;

    @Before
    public void init() {
        vd = TestUtil.initVisualDebugger();
    }

    @Test
    public void skeleton_pickEvent() {

        vd.clear();

        List<Point2d> outer = new ArrayList<Point2d>();

        outer.add(new Point2d(-1, -1));
        outer.add(new Point2d(1, -1));
        outer.add(new Point2d(1, 1));
        outer.add(new Point2d(-1, 1));

        List<Point2d> expected = new ArrayList<Point2d>();
        expected.add(p(0, 0));
        expected.addAll(outer);

        vd.debug(outer);

        SkeletonOutput sk = Skeleton.skeleton(outer, null);

        writeExpectedOutput(outer, sk);

        visualizeResults(outer, sk);

        validate(outer, sk);

        assertExpectedPoints(expected, getFacePoints(sk));
    }

    @Test
    public void skeleton_multiEdgeEvent() {

        vd.clear();

        List<Point2d> outer = new ArrayList<Point2d>();

        outer.add(new Point2d(0, 1));
        outer.add(new Point2d(-1, 0));
        outer.add(new Point2d(0, -1));

        outer.add(new Point2d(5, -2));
        outer.add(new Point2d(7, 0));
        outer.add(new Point2d(5, 2));

        List<Point2d> expected = new ArrayList<Point2d>();
        expected.add(p(0.53518, 0));
        expected.add(p(4.39444872, 0));
        expected.addAll(outer);

        vd.debug(outer);

        SkeletonOutput sk = Skeleton.skeleton(outer, null);

        writeExpectedOutput(outer, sk);

        visualizeResults(outer, sk);

        validate(outer, sk);

        assertExpectedPoints(expected, getFacePoints(sk));
    }

    @Test
    public void skeletonTest_cross_T1() {

        vd.clear();

        List<Point2d> outer = new ArrayList<Point2d>();

        outer.add(new Point2d(-3, -1));
        outer.add(new Point2d(3, -1));
        outer.add(new Point2d(3, 1));

        outer.add(new Point2d(1, 1));
        outer.add(new Point2d(1, 3));
        outer.add(new Point2d(-1, 3));
        outer.add(new Point2d(-1, 1));
        outer.add(new Point2d(-3, 1));

        List<Point2d> expected = new ArrayList<Point2d>();

        expected.addAll(outer);
        expected.add(p(-2, 0));
        expected.add(p(2, 0));
        expected.add(p(0, 0));
        expected.add(p(0, 2));
        vd.debug(outer);

        SkeletonOutput sk = Skeleton.skeleton(outer, null);

        writeExpectedOutput(outer, sk);

        visualizeResults(outer, sk);

        validate(outer, sk);

        assertExpectedPoints(expected, getFacePoints(sk));
    }

    @Test
    public void skeletonTest_cross_X1() {

        vd.clear();

        List<Point2d> outer = new ArrayList<Point2d>();

        outer.add(new Point2d(-3, -1));
        outer.add(new Point2d(-1, -1));
        outer.add(new Point2d(-1, -3));
        outer.add(new Point2d(1, -3));
        outer.add(new Point2d(1, -1));
        outer.add(new Point2d(3, -1));
        outer.add(new Point2d(3, 1));
        outer.add(new Point2d(1, 1));
        outer.add(new Point2d(1, 3));
        outer.add(new Point2d(-1, 3));
        outer.add(new Point2d(-1, 1));
        outer.add(new Point2d(-3, 1));

        List<Point2d> expected = new ArrayList<Point2d>(outer);

        expected.add(p(0, 0));
        expected.add(p(0, 2));
        expected.add(p(0, -2));
        expected.add(p(2, 0));
        expected.add(p(-2, 0));

        vd.debug(outer);

        SkeletonOutput sk = Skeleton.skeleton(outer, null);

        writeExpectedOutput(outer, sk);

        visualizeResults(outer, sk);

        validate(outer, sk);

        assertExpectedPoints(expected, getFacePoints(sk));
    }

    @Test
    public void skeletonTest_double_split() {

        vd.clear();

        List<Point2d> outer = new ArrayList<Point2d>();

        outer.add(new Point2d(-6, 0));
        outer.add(new Point2d(-3, -6));
        outer.add(new Point2d(-1, -2));
        outer.add(new Point2d(1, -2));
        outer.add(new Point2d(3, -6));
        outer.add(new Point2d(6, 0));

        List<Point2d> expected = new ArrayList<Point2d>(outer);

        expected.add(p(-3.0000000000000004, -1.854101966249685));
        expected.add(p(-1.6180339887498951, -1.0000000000000002));
        expected.add(p(1.6180339887498951, -1.0000000000000002));
        expected.add(p(3.0000000000000004, -1.854101966249685));

        vd.debug(outer);

        SkeletonOutput sk = Skeleton.skeleton(outer, null);

        writeExpectedOutput(outer, sk);

        visualizeResults(outer, sk);

        validate(outer, sk);

        assertExpectedPoints(expected, getFacePoints(sk));
    }

    @Test
    public void skeletonTest_double_split2() {

        vd.clear();

        List<Point2d> outer = new ArrayList<Point2d>();

        outer.add(new Point2d(-6, 0));
        outer.add(new Point2d(-3, -6));
        outer.add(new Point2d(-1, -2));
        outer.add(new Point2d(0, -3));
        outer.add(new Point2d(1, -2));
        outer.add(new Point2d(3, -6));
        outer.add(new Point2d(6, 0));

        List<Point2d> expected = new ArrayList<Point2d>(outer);

        expected.add(p(-3.0000000000000004, -1.854101966249685));
        expected.add(p(-1.2038204263767998, -0.7440019398522527));
        expected.add(p(-0.0, -1.242640687119285));
        expected.add(p(1.2038204263767998, -0.7440019398522527));
        expected.add(p(3.0000000000000004, -1.854101966249685));

        vd.debug(outer);

        SkeletonOutput sk = Skeleton.skeleton(outer, null);

        writeExpectedOutput(outer, sk);

        visualizeResults(outer, sk);

        validate(outer, sk);

        assertExpectedPoints(expected, getFacePoints(sk));

        assertPolygonWithEdges(7, sk);
    }

    @Test
    public void skeletonTest_multiple() {

        vd.clear();

        List<Point2d> outer = new ArrayList<Point2d>();

        outer.add(new Point2d(0, 0));
        outer.add(new Point2d(5, 0));
        outer.add(new Point2d(5, 5));
        outer.add(new Point2d(0, 5));

        List<Point2d> h1 = new ArrayList<Point2d>();
        h1.add(new Point2d(1, 1));
        h1.add(new Point2d(2, 1));
        h1.add(new Point2d(2, 2));
        h1.add(new Point2d(1, 2));

        List<Point2d> h2 = new ArrayList<Point2d>();
        h2.add(new Point2d(3, 3));
        h2.add(new Point2d(4, 3));
        h2.add(new Point2d(4, 4));
        h2.add(new Point2d(3, 4));

        List<Point2d> h3 = new ArrayList<Point2d>();
        h3.add(new Point2d(1, 3));
        h3.add(new Point2d(2, 3));
        h3.add(new Point2d(2, 4));
        h3.add(new Point2d(1, 4));

        List<Point2d> h4 = new ArrayList<Point2d>();
        h4.add(new Point2d(3, 1));
        h4.add(new Point2d(4, 1));
        h4.add(new Point2d(4, 2));
        h4.add(new Point2d(3, 2));

        List<Point2d> expected = new ArrayList<Point2d>(outer);

        expected.add(p(4.5, 2.5));
        expected.add(p(4.5, 0.5));
        expected.add(p(4.5, 4.5));
        expected.add(p(0.5, 4.5));
        expected.add(p(2.5, 4.5));
        expected.add(p(0.5, 0.5));
        expected.add(p(0.5, 2.5));
        expected.add(p(2.5, 0.5));
        expected.add(p(2.5, 2.5));
        expected.add(p(2.0, 2.0));
        expected.add(p(2.0, 1.0));
        expected.add(p(1.0, 1.0));
        expected.add(p(1.0, 2.0));
        expected.add(p(4.0, 4.0));
        expected.add(p(4.0, 3.0));
        expected.add(p(3.0, 3.0));
        expected.add(p(3.0, 4.0));
        expected.add(p(2.0, 4.0));
        expected.add(p(2.0, 3.0));
        expected.add(p(1.0, 3.0));
        expected.add(p(1.0, 4.0));
        expected.add(p(4.0, 2.0));
        expected.add(p(4.0, 1.0));
        expected.add(p(3.0, 1.0));
        expected.add(p(3.0, 2.0));

        vd.debug(outer);

        @SuppressWarnings("unchecked")
        SkeletonOutput sk = Skeleton.skeleton(outer, Arrays.asList(h1, h2, h3, h4));

        writeExpectedOutput(outer, sk);

        visualizeResults(outer, sk);

        validate(outer, sk);

        assertExpectedPoints(expected, getFacePoints(sk));

    }

    private void assertPolygonWithEdges(int numOfEdges, SkeletonOutput sk) {

        for (EdgeOutput edgeOutput : sk.getEdgeOutputs()) {
            PolygonList2d polygonList2d = edgeOutput.getPolygon();
            List<Point2d> points = polygonList2d.getPoints();
            if (points.size() == numOfEdges) {
                return;
            }

        }
        fail("expected polygon with number of edges: " + numOfEdges);
    }
}
