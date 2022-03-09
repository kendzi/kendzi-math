/*
 * This software is provided "AS IS" without a warranty of any kind. You use it
 * on your own risk and responsibility!!! This file is shared under BSD v3
 * license. See readme.txt and BSD3 file for details.
 */

package kendzi.math.geometry.skeleton;

import static kendzi.math.geometry.TestUtil.v;
import static kendzi.math.geometry.skeleton.SkeletonTestUtil.assertExpectedPoints;
import static kendzi.math.geometry.skeleton.SkeletonTestUtil.getFacePoints;
import static kendzi.math.geometry.skeleton.SkeletonTestUtil.p;
import static kendzi.math.geometry.skeleton.SkeletonTestUtil.validate;
import static kendzi.math.geometry.skeleton.SkeletonTestUtil.visualizeResults;
import static kendzi.math.geometry.skeleton.SkeletonTestUtil.writeExpectedOutput;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2dc;
import org.junit.Before;
import org.junit.Test;

import kendzi.math.geometry.TestUtil;
import kendzi.math.geometry.skeleton.debug.VisualDebugger;

public class SkeletonTest {

    private static VisualDebugger vd;

    @Before
    public void init() {
        vd = TestUtil.initVisualDebugger();
    }

    @Test
    public void skeletonTest5() {

        vd.clear();

        List<Vector2dc> polygon = new ArrayList<>();

        polygon.add(v(-2, 0));
        polygon.add(v(-1, -1));
        polygon.add(v(0, 0));
        polygon.add(v(1, -1));
        polygon.add(v(2, 0));
        polygon.add(v(1, 1));
        polygon.add(v(-1, 1));

        List<Vector2dc> expected = new ArrayList<>();
        expected.add(p(-1.000000, 0.000000));
        expected.add(p(-0.707107, 0.292893));
        expected.add(p(0.000000, 0.585786));
        expected.add(p(0.707107, 0.292893));
        expected.add(p(1.000000, 0.000000));
        expected.addAll(polygon);

        vd.debug(polygon);

        SkeletonOutput sk = Skeleton.skeleton(polygon);

        visualizeResults(polygon, sk);
        validate(polygon, sk);

        assertExpectedPoints(expected, getFacePoints(sk));

        // writeExpectedOutput(polygon, sk);

    }

    @Test
    public void skeletonTest_hole_1() {

        vd.clear();

        List<Vector2dc> inner = new ArrayList<>();

        inner.add(v(-1, 1));
        inner.add(v(1, 1));
        inner.add(v(1, -1));
        inner.add(v(-1, -1));

        List<Vector2dc> outer = new ArrayList<>();
        outer.add(v(-2, -2));
        outer.add(v(2, -2));
        outer.add(v(2, 2));
        outer.add(v(-2, 2));

        List<List<Vector2dc>> innerList = new ArrayList<>();
        innerList.add(inner);

        List<Vector2dc> expected = new ArrayList<>();
        expected.add(p(-1.500000, -1.500000));
        expected.add(p(-1.500000, 1.500000));
        expected.add(p(1.500000, -1.500000));
        expected.add(p(1.500000, 1.500000));
        expected.addAll(outer);
        expected.addAll(inner);

        vd.debug(outer);
        vd.debug(inner);

        SkeletonOutput sk = Skeleton.skeleton(outer, innerList);

        writeExpectedOutput(outer, sk);

        visualizeResults(outer, sk);

        validate(outer, sk);

        assertExpectedPoints(expected, getFacePoints(sk));

    }

    @Test
    public void skeletonTest_hole_2() {

        vd.clear();

        List<Vector2dc> polygon = new ArrayList<>();
        List<Vector2dc> hole = new ArrayList<>();

        polygon.add(v(0.0, 0.0));
        polygon.add(v(7.087653026630875, -0.0572739636795121));
        polygon.add(v(7.035244566479503, -6.5428208800475005));
        polygon.add(v(-0.052408459722688594, -6.485546915224834));

        hole.add(v(1.4849939588531493, -1.5250224044562133));
        hole.add(v(1.4341762422598874, -5.1814705083480606));
        hole.add(v(5.747532319228888, -5.241418004618678));
        hole.add(v(5.798350035536362, -1.5849699030131408));

        List<List<Vector2dc>> innerList = new ArrayList<>();
        innerList.add(hole);

        List<Vector2dc> expected = new ArrayList<>();
        expected.addAll(polygon);
        expected.addAll(hole);

        expected.add(p(6.3821371859978875, -5.893911100019249));
        expected.add(p(0.7651208111455217, -5.8321836510415475));
        expected.add(p(0.6898242249025952, -5.755213752675646));
        expected.add(p(6.389576876981116, -5.886633146615758));
        expected.add(p(6.443747494495353, -0.9572661447277495));
        expected.add(p(6.310953658294117, -0.8215212379272131));
        expected.add(p(0.7481994722534444, -0.7603900949775717));
        expected.add(p(0.7446762937827887, -0.7638366801629576));

        vd.debug(polygon);
        vd.debug(hole);

        SkeletonOutput sk = Skeleton.skeleton(polygon, innerList);

        writeExpectedOutput(polygon, sk);

        visualizeResults(polygon, sk);

        validate(polygon, sk);

        assertExpectedPoints(expected, getFacePoints(sk));

    }

    // @Test
    public void skeletonTest6_1() {

        vd.clear();

        List<Vector2dc> inner = new ArrayList<>();

        inner.add(v(-1, 1));
        inner.add(v(1, 1));
        inner.add(v(1, -1));
        inner.add(v(0, -1));
        inner.add(v(0, 0));

        List<Vector2dc> outer = new ArrayList<>();
        outer.add(v(-2, -2));
        outer.add(v(2, -2));
        outer.add(v(2, 2));
        outer.add(v(-2, 2));

        List<List<Vector2dc>> innerList = new ArrayList<>();
        innerList.add(inner);

        // polygon.add(v(1, 1));
        // polygon.add(v(-1, 1));

        vd.debug(outer);
        vd.debug(inner);

        SkeletonOutput sk = Skeleton.skeleton(outer, innerList);

        visualizeResults(inner, sk);
        validate(outer, sk);

        // showResult2(polygon, sk);

    }

    @Test
    public void skeletonTest6_9() {

        vd.clear();

        List<Vector2dc> polygon = new ArrayList<>();

        polygon.add(v(119, 158));
        polygon.add(v(259, 159));
        polygon.add(v(248, 63));
        polygon.add(v(126, 60));
        polygon.add(v(90, 106));

        List<Vector2dc> expected = new ArrayList<>();
        expected.add(p(147.156672, 110.447627));
        expected.add(p(149.322770, 109.401806));
        expected.add(p(204.771536, 110.281518));
        expected.addAll(polygon);

        vd.debug(polygon);

        SkeletonOutput sk = Skeleton.skeleton(polygon);

        validate(polygon, sk);

        visualizeResults(polygon, sk);

        assertExpectedPoints(expected, getFacePoints(sk));
    }

    @Test
    public void skeletonTest7() {

        vd.clear();

        List<Vector2dc> polygon = new ArrayList<>();

        polygon.add(v(0, 0));
        polygon.add(v(0, -1));
        polygon.add(v(1, -1));
        polygon.add(v(1, 1));
        polygon.add(v(-1, 1));

        List<Vector2dc> expected = new ArrayList<>();
        expected.add(p(0.414214, 0.414214));
        expected.add(p(0.500000, -0.500000));
        expected.add(p(0.500000, 0.207107));
        expected.addAll(polygon);

        vd.debug(polygon);

        SkeletonOutput sk = Skeleton.skeleton(polygon);

        validate(polygon, sk);

        visualizeResults(polygon, sk);

        assertExpectedPoints(expected, getFacePoints(sk));
    }

    @Test
    public void skeletonTest8() {

        vd.clear();

        List<Vector2dc> polygon = new ArrayList<>();

        polygon.add(v(-1, 0));
        polygon.add(v(-1.2, -2));
        polygon.add(v(1.2, -2));
        polygon.add(v(1, 0.5));
        polygon.add(v(2, -0.2));
        polygon.add(v(2, 1));
        polygon.add(v(-2, 1.2));
        polygon.add(v(-2, -0.2));

        List<Vector2dc> expected = new ArrayList<>();
        expected.add(p(-1.383546, 0.551953));
        expected.add(p(-0.436065, 0.621927));
        expected.add(p(0.011951, -0.903199));
        expected.add(p(0.021802, 0.089862));
        expected.add(p(0.784764, 0.875962));
        expected.add(p(1.582159, 0.602529));
        expected.addAll(polygon);

        vd.debug(polygon);

        SkeletonOutput sk = Skeleton.skeleton(polygon);

        validate(polygon, sk);

        visualizeResults(polygon, sk);

        assertExpectedPoints(expected, getFacePoints(sk));
    }

    @Test
    public void skeletonTestB1() {

        vd.clear();

        List<Vector2dc> polygon = new ArrayList<>();

        polygon.add(v(0.0, 0.0));
        polygon.add(v(0.7904833761575505, 8.520486967634694));
        polygon.add(v(5.978418789681697, 8.712497973454056));
        polygon.add(v(5.95269105167549, -2.6355979260267777));
        polygon.add(v(4.566910029680516, -2.6324561649763485));
        polygon.add(v(4.5603585630377115, -5.522203838861205));
        polygon.add(v(6.043569207647302, -5.525566487736131));
        polygon.add(v(6.038049999411376, -7.960001358506733));
        polygon.add(v(9.886846028372108, -7.968727126586532));
        polygon.add(v(9.902081573281308, -1.248570683335708));
        polygon.add(v(13.742215004880482, -1.2572768087753285));
        polygon.add(v(13.75400717659087, 3.9440624000165103));
        polygon.add(v(9.194585721152315, 3.9543992526769878));
        polygon.add(v(5.823717342947504, 17.30434988614582));
        polygon.add(v(5.808494957384097, 10.589997844496661));
        polygon.add(v(-0.13214359029800526, 10.603466113057067));

        List<Vector2dc> expected = new ArrayList<>();
        expected.add(p(0.359453, 8.976136));
        expected.add(p(0.918471, 9.563508));
        expected.add(p(6.008508, -4.080606));
        expected.add(p(6.729881, 9.664425));
        expected.add(p(6.760642, 9.696273));
        expected.add(p(6.858071, 9.623241));
        expected.add(p(7.394289, -4.083747));
        expected.add(p(7.779411, 2.141718));
        expected.add(p(7.923726, -3.556706));
        expected.add(p(7.933442, 0.729015));
        expected.add(p(7.966811, -6.039966));
        expected.add(p(7.972330, -3.605531));
        expected.add(p(8.562422, 1.355149));
        expected.add(p(11.147441, 1.349289));
        expected.addAll(polygon);

        vd.debug(polygon);

        SkeletonOutput sk = Skeleton.skeleton(polygon);

        validate(polygon, sk);

        visualizeResults(polygon, sk);

        assertExpectedPoints(expected, getFacePoints(sk));
    }

    @Test
    public void skeletonTestB2() {

        vd.clear();

        List<Vector2dc> polygon = new ArrayList<>();

        polygon.add(v(0.0, 0.0));
        polygon.add(v(0.7904833761549828, 8.520486967607015));
        polygon.add(v(5.9784187896622765, 8.712497973425755));
        polygon.add(v(5.952691051656153, -2.6355979260182156));
        polygon.add(v(4.56691002966568, -2.632456164967797));
        polygon.add(v(4.560358563022897, -5.522203838843264));
        polygon.add(v(6.0435692076276695, -5.525566487718182));
        polygon.add(v(6.038049999391761, -7.960001358480875));
        polygon.add(v(9.886846028339992, -7.968727126560646));
        polygon.add(v(9.902081573249141, -1.2485706833316517));
        polygon.add(v(13.74221500483584, -1.2572768087712447));
        polygon.add(v(13.754007176546189, 3.944062400003698));
        polygon.add(v(9.194585721122445, 3.9543992526641416));
        polygon.add(v(9.840828592998651, 10.391220834155359));
        polygon.add(v(-0.24573045314637643, 10.433085818392197));

        List<Vector2dc> expected = new ArrayList<>();
        expected.add(p(0.311377, 9.026957));
        expected.add(p(0.732142, 9.474000));
        expected.add(p(6.008508, -4.080606));
        expected.add(p(6.810341, 9.573824));
        expected.add(p(7.394289, -4.083747));
        expected.add(p(7.498680, 2.423725));
        expected.add(p(7.813149, 8.564560));
        expected.add(p(7.923726, -3.556706));
        expected.add(p(7.933442, 0.729015));
        expected.add(p(7.966811, -6.039966));
        expected.add(p(7.972330, -3.605531));
        expected.add(p(8.562422, 1.355149));
        expected.add(p(11.147441, 1.349289));
        expected.addAll(polygon);

        vd.debug(polygon);

        SkeletonOutput sk = Skeleton.skeleton(polygon);

        validate(polygon, sk);

        visualizeResults(polygon, sk);

        assertExpectedPoints(expected, getFacePoints(sk));
    }

    @Test
    public void skeletonTestB3__() {

        vd.clear();

        List<Vector2dc> polygon = new ArrayList<>();

        polygon.add(v(0.0, 0.0));
        polygon.add(v(0.0853589477356087, -5.32440343246266));
        polygon.add(v(3.934154976683839, -5.33312920054243));
        polygon.add(v(3.9493905215929885, 1.387027242686564));
        polygon.add(v(7.789523953179687, 1.378321117246971));
        polygon.add(v(3.2418946694662925, 6.589997178682357));
        polygon.add(v(-0.4480081827933864, 6.565094698194268));

        List<Vector2dc> expected = new ArrayList<>();
        expected.add(p(1.860585, 3.485326));
        expected.add(p(1.972676, 0.083065));
        expected.add(p(1.996554, -3.386722));
        expected.add(p(2.146278, 4.158152));
        expected.add(p(2.251879, 3.903281));
        expected.addAll(polygon);

        vd.debug(polygon);

        SkeletonOutput sk = Skeleton.skeleton(polygon);

        validate(polygon, sk);

        visualizeResults(polygon, sk);

        assertExpectedPoints(expected, getFacePoints(sk));
    }

    @Test
    public void skeletonTestB4__() {

        vd.clear();

        List<Vector2dc> polygon = new ArrayList<>();

        polygon.add(v(0.0, 0.0));
        polygon.add(v(-1.192493260706565, -5.6367673060470285));
        polygon.add(v(2.656302768241665, -5.645493074126799));
        polygon.add(v(6.511671744737513, 1.0659572436626021));
        polygon.add(v(-1.7258603912355601, 6.252730824609899));

        List<Vector2dc> expected = new ArrayList<>();
        expected.add(p(1.427912, -3.517645));
        expected.add(p(2.804480, 0.085324));
        expected.add(p(2.812173, 0.146026));
        expected.addAll(polygon);

        vd.debug(polygon);

        SkeletonOutput sk = Skeleton.skeleton(polygon);

        validate(polygon, sk);

        visualizeResults(polygon, sk);

        assertExpectedPoints(expected, getFacePoints(sk));
    }

    @Test
    public void skeletonTestB5__() {

        vd.clear();

        List<Vector2dc> polygon = new ArrayList<>();

        polygon.add(v(0.0, 0.0));
        polygon.add(v(-1.192493260706565, -5.6367673060470285));
        polygon.add(v(2.656302768241665, -5.645493074126799));
        polygon.add(v(7.051209343876594, 2.9401404828825903));
        polygon.add(v(-1.7258603912355601, 6.252730824609899));

        List<Vector2dc> expected = new ArrayList<>();
        expected.add(p(1.381369, -3.555284));
        expected.add(p(2.671019, 0.081263));
        expected.add(p(2.795365, 1.297294));
        expected.addAll(polygon);

        vd.debug(polygon);

        SkeletonOutput sk = Skeleton.skeleton(polygon);

        validate(polygon, sk);

        visualizeResults(polygon, sk);

        assertExpectedPoints(expected, getFacePoints(sk));
    }

    @Test
    public void skeletonTestB6__() {

        vd.clear();

        List<Vector2dc> polygon = new ArrayList<>();

        polygon.add(v(0.0, 0.0));
        polygon.add(v(-1.192493260706565, -5.636767306047028));
        polygon.add(v(2.656302768241665, -5.645493074126798));
        polygon.add(v(5.716563703938576, 6.120572646649897));
        polygon.add(v(-5.985367752852362, 6.423111118668768));
        polygon.add(v(-6.297731626436729, -3.6293262553813097));
        polygon.add(v(-3.4580600517873807, 1.3968924313579514));

        List<Vector2dc> expected = new ArrayList<>();
        expected.add(p(-4.254893, 3.676216));
        expected.add(p(-3.720036, 4.025044));
        expected.add(p(1.173593, -3.723313));
        expected.add(p(1.493460, 2.941709));
        expected.add(p(2.345444, 1.248630));
        expected.addAll(polygon);

        vd.debug(polygon);

        SkeletonOutput sk = Skeleton.skeleton(polygon);

        validate(polygon, sk);

        visualizeResults(polygon, sk);

        assertExpectedPoints(expected, getFacePoints(sk));
    }

    @Test
    public void skeletonTestB7__() {

        vd.clear();

        List<Vector2dc> polygon = new ArrayList<>();

        polygon.add(v(0.0, 0.0));
        polygon.add(v(-1.1889778921584675, -7.356451670462243));
        polygon.add(v(5.7257149714503175, -12.035132476438635));
        polygon.add(v(11.739705976732338, -17.194940549920428));
        polygon.add(v(0.8357970425329011, -1.0288592710693223));
        polygon.add(v(7.360455718922119, -6.229013606285628));

        List<Vector2dc> expected = new ArrayList<>();
        expected.add(p(0.159929, -0.432595));
        expected.add(p(0.228431, -0.371176));
        expected.add(p(1.434035, -6.223122));
        expected.add(p(6.380715, -11.177062));
        expected.addAll(polygon);

        vd.debug(polygon);

        SkeletonOutput sk = Skeleton.skeleton(polygon);

        validate(polygon, sk);

        visualizeResults(polygon, sk);

        assertExpectedPoints(expected, getFacePoints(sk));
    }

    @Test
    public void skeletonTestB8__() {

        vd.clear();

        List<Vector2dc> polygon = new ArrayList<>();

        polygon.add(v(0.0, 0.0));
        polygon.add(v(-1.1889778921584675, -7.356451670462243));
        polygon.add(v(5.7257149714503175, -12.035132476438635));
        polygon.add(v(11.739705976732338, -17.194940549920428));
        polygon.add(v(0.8357970425329011, -1.0288592710693223));

        List<Vector2dc> expected = new ArrayList<>();
        expected.add(p(0.367496, -1.375942));
        expected.add(p(1.434035, -6.223122));
        expected.add(p(6.380715, -11.177062));
        expected.addAll(polygon);

        vd.debug(polygon);

        SkeletonOutput sk = Skeleton.skeleton(polygon);

        validate(polygon, sk);

        visualizeResults(polygon, sk);

        assertExpectedPoints(expected, getFacePoints(sk));
    }

    @Test
    public void skeletonTest9() {

        vd.clear();

        List<Vector2dc> polygon = new ArrayList<>();

        polygon.add(v(77, 85));
        polygon.add(v(198, 85));
        polygon.add(v(196, 139));
        polygon.add(v(150, 119));
        polygon.add(v(157, 177));
        polygon.add(v(112, 179));
        polygon.add(v(125, 130));
        polygon.add(v(68, 118));

        List<Vector2dc> expected = new ArrayList<>();
        expected.add(p(91.320644, 103.749308));
        expected.add(p(126.066597, 107.367125));
        expected.add(p(134.360696, 98.011826));
        expected.add(p(136.287191, 159.442502));
        expected.add(p(138.938550, 121.416104));
        expected.add(p(175.597143, 106.588481));
        expected.addAll(polygon);

        vd.debug(polygon);

        SkeletonOutput sk = Skeleton.skeleton(polygon);

        validate(polygon, sk);

        visualizeResults(polygon, sk);

        assertExpectedPoints(expected, getFacePoints(sk));
    }

    @Test
    public void skeletonTestB10() {

        vd.clear();

        List<Vector2dc> polygon = new ArrayList<>();

        polygon.add(v(0.0, 0.0));
        polygon.add(v(23.542862199718826, -1.0957017437087124));
        polygon.add(v(12.89581137652037, 1.5573908447103584));
        polygon.add(v(13.68678342709616, 5.195862274901293));
        polygon.add(v(30.92997412599037, 6.619611963708646));
        polygon.add(v(16.53428280871175, 7.568778425199767));
        polygon.add(v(13.05400578686415, 8.676139297892002));
        polygon.add(v(-4.188927083681472, 7.336703572978552));
        polygon.add(v(10.196014852102863, 4.475707108744242));
        polygon.add(v(8.782756714583655, 1.5573908412810287));

        List<Vector2dc> expected = new ArrayList<>();
        expected.add(p(9.496922, 0.613365));
        expected.add(p(10.882442, 1.437594));
        expected.add(p(11.471020, 0.671521));
        expected.add(p(11.720280, 6.390569));
        expected.add(p(12.241556, 6.845124));
        expected.add(p(12.291810, 5.518617));
        expected.add(p(12.847638, 6.893686));
        expected.add(p(16.331903, 6.498860));
        expected.addAll(polygon);

        vd.debug(polygon);

        SkeletonOutput sk = Skeleton.skeleton(polygon);

        validate(polygon, sk);

        visualizeResults(polygon, sk);

        assertExpectedPoints(expected, getFacePoints(sk));
    }

    @Test
    public void skeletonTestB11() {

        vd.clear();

        List<Vector2dc> polygon = new ArrayList<>();

        polygon.add(v(0.0, 0.0));
        polygon.add(v(-0.2885918221241157, 14.001053106358517));
        polygon.add(v(4.899343591400031, 14.19306411217788));
        polygon.add(v(4.873615853393824, 2.8449682126970464));
        polygon.add(v(3.4878348313988496, 2.8481099737474747));
        polygon.add(v(3.4812833647560453, -0.04163770013738066));
        polygon.add(v(4.964494009365636, -0.04500034901230876));
        polygon.add(v(4.95897480112971, -2.4794352197829106));
        polygon.add(v(8.807770830090442, -2.4881609878627096));
        polygon.add(v(8.823006374999641, 4.231995455388115));
        polygon.add(v(12.663139806598815, 4.223289329948495));
        polygon.add(v(12.674931978309203, 9.424628538740333));
        polygon.add(v(8.115510522870647, 9.43496539140081));
        polygon.add(v(4.744642144665839, 22.784916024869645));
        polygon.add(v(4.729419759102431, 16.070563983220485));
        polygon.add(v(-1.2112187885796715, 16.08403225178089));

        List<Vector2dc> expected = new ArrayList<>();
        expected.add(p(-0.689093, 14.379124));
        expected.add(p(-0.093795, 15.045234));
        expected.add(p(4.929433, 1.399960));
        expected.add(p(5.650806, 15.144991));
        expected.add(p(5.681567, 15.176839));
        expected.add(p(5.778996, 15.103807));
        expected.add(p(6.315214, 1.396819));
        expected.add(p(6.700336, 7.622285));
        expected.add(p(6.844651, 1.923860));
        expected.add(p(6.854367, 6.209582));
        expected.add(p(6.887736, -0.559400));
        expected.add(p(6.893255, 1.875035));
        expected.add(p(7.483346, 6.835716));
        expected.add(p(10.068366, 6.829855));
        expected.addAll(polygon);

        vd.debug(polygon);

        SkeletonOutput sk = Skeleton.skeleton(polygon);

        validate(polygon, sk);

        visualizeResults(polygon, sk);

        assertExpectedPoints(expected, getFacePoints(sk));
    }

    @Test
    public void skeletonTestB11_b() {

        vd.clear();

        List<Vector2dc> polygon = new ArrayList<>();

        polygon.add(v(4.899343591400031, 14.19306411217788));
        polygon.add(v(4.873615853393824, 2.8449682126970464));
        polygon.add(v(3.4878348313988496, 2.8481099737474747));
        polygon.add(v(3.4812833647560453, -0.04163770013738066));
        polygon.add(v(4.964494009365636, -0.04500034901230876));
        polygon.add(v(4.95897480112971, -2.4794352197829106));
        polygon.add(v(8.807770830090442, -2.4881609878627096));
        polygon.add(v(8.823006374999641, 4.231995455388115));

        List<Vector2dc> expected = new ArrayList<>(polygon);

        expected.add(p(6.8490390285892975, 3.8595532917064257));
        expected.add(p(6.315213958119228, 1.396818641879405));
        expected.add(p(6.844650538271922, 1.9238600368574004));
        expected.add(p(4.929432935568722, 1.3999604034575501));
        expected.add(p(6.893254906247293, 1.875034782130368));
        expected.add(p(6.8877356980830235, -0.5594000893968922));

        vd.debug(polygon);

        SkeletonOutput sk = Skeleton.skeleton(polygon);

        validate(polygon, sk);

        visualizeResults(polygon, sk);

        assertExpectedPoints(expected, getFacePoints(sk));
    }

    @Test
    public void skeletonTestB12() {

        vd.clear();

        List<Vector2dc> polygon = new ArrayList<>();

        polygon.add(v(0.0, 0.0));
        polygon.add(v(1.6082838074612242, 15.395815413439262));
        polygon.add(v(6.796219518140479, 15.587826427398873));
        polygon.add(v(6.7704917786606345, 4.239729879063727));
        polygon.add(v(5.384710677004972, 4.2428716408656655));
        polygon.add(v(5.37815921027269, 1.3531237986037645));
        polygon.add(v(6.861369940123552, 1.3497611512508971));
        polygon.add(v(6.855850731428608, -1.084673859531076));
        polygon.add(v(10.704646980698193, -1.093399628682226));
        polygon.add(v(10.719882526622944, 5.626757200629533));
        polygon.add(v(14.560016178034793, 5.6180510758343525));
        polygon.add(v(14.571808350563504, 10.819390581977487));
        polygon.add(v(10.01238663382704, 10.829727434086928));
        polygon.add(v(6.64151806240239, 24.179678832787182));
        polygon.add(v(6.626295676252851, 17.465326408838887));
        polygon.add(v(0.6856567883022331, 17.478794675312955));

        List<Vector2dc> expected = new ArrayList<>();
        expected.add(p(1.140824, 15.895738));
        expected.add(p(1.684220, 16.437933));
        expected.add(p(6.826309, 2.794722));
        expected.add(p(7.547682, 16.539753));
        expected.add(p(7.578443, 16.571601));
        expected.add(p(7.675872, 16.498570));
        expected.add(p(8.212090, 2.791580));
        expected.add(p(8.597212, 9.017047));
        expected.add(p(8.741527, 3.318622));
        expected.add(p(8.751243, 7.604343));
        expected.add(p(8.784612, 0.835361));
        expected.add(p(8.790131, 3.269796));
        expected.add(p(9.380222, 8.230478));
        expected.add(p(11.965243, 8.224617));
        expected.addAll(polygon);

        vd.debug(polygon);

        SkeletonOutput sk = Skeleton.skeleton(polygon);

        validate(polygon, sk);

        visualizeResults(polygon, sk);

        assertExpectedPoints(expected, getFacePoints(sk));
    }

    @Test
    public void skeletonTestB13() {

        vd.clear();

        List<Vector2dc> polygon = new ArrayList<>();

        polygon.add(v(0.0, 0.0));
        polygon.add(v(-0.03697835689094475, 17.903291653889664));
        polygon.add(v(9.36122931562474, 17.922703185404146));
        polygon.add(v(9.399539490923859, -0.6253964219022965));
        polygon.add(v(6.897780217346079, -0.6305636811510293));
        polygon.add(v(6.907305814387495, -5.242438102429183));
        polygon.add(v(9.496043768204736, -5.2367356072030695));
        polygon.add(v(9.673537498409361, -7.819464124646299));
        polygon.add(v(19.728934851080233, -7.7986952031890375));
        polygon.add(v(19.715280237589244, -1.1877328304801722));
        polygon.add(v(23.581205989632387, -1.1797479507986637));
        polygon.add(v(23.570459756724986, 4.023104657038741));
        polygon.add(v(19.065027189523686, 4.01379891209519));
        polygon.add(v(19.009685241927738, 30.807932065847332));
        polygon.add(v(9.439383865135643, 30.78816508512935));
        polygon.add(v(9.453189359125524, 24.10415305431124));
        polygon.add(v(-0.01730198014624129, 24.08459222736407));
        polygon.add(v(-0.030597953439544412, 30.521916694234474));
        polygon.add(v(-10.417861267451112, 30.500462317733504));
        polygon.add(v(-10.354819907553885, -0.021387367337700525));

        vd.debug(polygon);

        List<Vector2dc> expected = new ArrayList<>();
        expected.add(p(-5.225081993006608, 23.070007924404237));
        expected.add(p(-5.213502422879821, 25.317557848847482));
        expected.add(p(-5.208893794753686, 23.086263132900537));
        expected.add(p(-5.188103636084189, 5.166716270119771));
        expected.add(p(-3.1015352470932616, 20.98759193064646));
        expected.add(p(9.208321529781248, -2.9315800507494063));
        expected.add(p(11.648322280432005, -2.9263727729378277));
        expected.add(p(12.445462580412869, 21.019703480686516));
        expected.add(p(12.606101682729628, -3.818739927261688));
        expected.add(p(13.428106603203808, -3.789677802721639));
        expected.add(p(14.19596815545603, 19.27641416469292));
        expected.add(p(14.234418043971877, 26.012897887101527));
        expected.add(p(14.237504608711998, -0.83370695637133));
        expected.add(p(14.248223537950237, 19.328885855734843));
        expected.add(p(14.557918451002058, -1.1527999293121498));
        expected.add(p(14.561015138561665, -2.652079649029039));
        expected.add(p(17.108480813881517, 1.4083203585579516));
        expected.add(p(20.974406567920894, 1.4163052362523167));

        expected.addAll(polygon);

        SkeletonOutput sk = Skeleton.skeleton(polygon);

        visualizeResults(polygon, sk);

        validate(polygon, sk);

        assertExpectedPoints(expected, getFacePoints(sk));
    }

    @Test
    public void circularAddTest() {

        vd.clear();

        List<Vector2dc> polygon = new ArrayList<>();

        polygon.add(v(50, 50));
        polygon.add(v(100, 50));
        polygon.add(v(100, 100));
        polygon.add(v(50, 100));

        List<Vector2dc> expected = new ArrayList<>();
        expected.add(p(75.000000, 75.000000));
        expected.addAll(polygon);

        vd.debug(polygon);

        SkeletonOutput sk = Skeleton.skeleton(polygon);

        visualizeResults(polygon, sk);

        validate(polygon, sk);

        assertExpectedPoints(expected, getFacePoints(sk));
    }

    @Test
    public void circularAddTest2() {

        vd.clear();

        List<Vector2dc> polygon = new ArrayList<>();

        polygon.add(v(50, 50));
        polygon.add(v(150, 50));
        polygon.add(v(150, 100));
        polygon.add(v(50, 100));

        List<Vector2dc> expected = new ArrayList<>();
        expected.add(p(75.000000, 75.000000));
        expected.add(p(125.000000, 75.000000));
        expected.addAll(polygon);

        SkeletonOutput sk = Skeleton.skeleton(polygon);

        visualizeResults(polygon, sk);

        validate(polygon, sk);

        assertExpectedPoints(expected, getFacePoints(sk));
    }
}
