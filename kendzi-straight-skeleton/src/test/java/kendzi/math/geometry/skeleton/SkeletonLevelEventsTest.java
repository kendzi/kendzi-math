/*
 * This software is provided "AS IS" without a warranty of any kind. You use it
 * on your own risk and responsibility!!! This file is shared under BSD v3
 * license. See readme.txt and BSD3 file for details.
 */

package kendzi.math.geometry.skeleton;

import static kendzi.math.geometry.skeleton.SkeletonTestUtil.*;

import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Point2d;

import kendzi.math.geometry.TestUtil;
import kendzi.math.geometry.skeleton.Skeleton.SkeletonOutput;
import kendzi.math.geometry.skeleton.debug.DV;

import org.junit.Before;
import org.junit.Test;

public class SkeletonLevelEventsTest {

    @Before
    public void init() {
        TestUtil.initVisualDebugger();
    }

    @Test
    public void skeleton_pickEvent() {

        DV.clear();

        List<Point2d> outer = new ArrayList<Point2d>();

        outer.add(new Point2d(-1, -1));
        outer.add(new Point2d(1, -1));
        outer.add(new Point2d(1, 1));
        outer.add(new Point2d(-1, 1));

        List<Point2d> expected = new ArrayList<Point2d>();
        expected.add(p(0, 0));
        expected.addAll(outer);

        DV.debug(outer);

        SkeletonOutput sk = Skeleton.skeleton(outer, null);

        writeExpectedOutput(outer, sk);

        visualizeResults(outer, sk);

        validate(outer, sk);

        assertExpectedPoints(expected, getFacePoints(sk));
    }

    @Test
    public void skeleton_multiEdgeEvent() {

        DV.clear();

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

        DV.debug(outer);

        SkeletonOutput sk = Skeleton.skeleton(outer, null);

        writeExpectedOutput(outer, sk);

        visualizeResults(outer, sk);

        validate(outer, sk);

        assertExpectedPoints(expected, getFacePoints(sk));
    }

    @Test
    public void skeletonTest_cross_T1() {

        DV.clear();

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
        DV.debug(outer);

        SkeletonOutput sk = Skeleton.skeleton(outer, null);

        writeExpectedOutput(outer, sk);

        visualizeResults(outer, sk);

        validate(outer, sk);

        assertExpectedPoints(expected, getFacePoints(sk));
    }

    @Test
    public void skeletonTest_cross_X1() {

        DV.clear();

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

        DV.debug(outer);

        SkeletonOutput sk = Skeleton.skeleton(outer, null);

        writeExpectedOutput(outer, sk);

        visualizeResults(outer, sk);

        validate(outer, sk);

        assertExpectedPoints(expected, getFacePoints(sk));
    }

    @Test
    public void skeletonTest_double_split() {

        DV.clear();

        List<Point2d> outer = new ArrayList<Point2d>();

        outer.add(new Point2d(-6, 0));
        outer.add(new Point2d(-3, -6));
        outer.add(new Point2d(-1, -2));
        outer.add(new Point2d(1, -2));
        outer.add(new Point2d(3, -6));
        outer.add(new Point2d(6, 0));

        List<Point2d> expected = new ArrayList<Point2d>(outer);

        expected.add(p(0, 0));
        expected.add(p(0, 2));
        expected.add(p(0, -2));
        expected.add(p(2, 0));
        expected.add(p(-2, 0));

        DV.debug(outer);

        SkeletonOutput sk = Skeleton.skeleton(outer, null);

        writeExpectedOutput(outer, sk);

        visualizeResults(outer, sk);

        validate(outer, sk);

        assertExpectedPoints(expected, getFacePoints(sk));
    }

}
