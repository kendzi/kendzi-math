/*
 * This software is provided "AS IS" without a warranty of any kind. You use it
 * on your own risk and responsibility!!! This file is shared under BSD v3
 * license. See readme.txt and BSD3 file for details.
 */

package kendzi.math.geometry.skeleton;

import static kendzi.math.geometry.skeleton.SkeletonTestUtil.assertExpectedPoints;
import static kendzi.math.geometry.skeleton.SkeletonTestUtil.getFacePoints;
import static kendzi.math.geometry.skeleton.SkeletonTestUtil.p;
import static kendzi.math.geometry.skeleton.SkeletonTestUtil.validate;
import static kendzi.math.geometry.skeleton.SkeletonTestUtil.visualizeResults;
import static kendzi.math.geometry.skeleton.SkeletonTestUtil.writeExpectedOutput;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.joml.Vector2d;
import org.joml.Vector2dc;
import org.junit.Before;
import org.junit.Test;

import kendzi.math.geometry.TestUtil;
import kendzi.math.geometry.skeleton.debug.VisualDebugger;

public class SkeletonExtendedTest {

    private static VisualDebugger vd;

    @Before
    public void init() {
        vd = TestUtil.initVisualDebugger();
    }

    @Test
    public void skeleton_1() {

        vd.clear();

        List<Vector2dc> polygon = new ArrayList<Vector2dc>();
        polygon.add(new Vector2d(0.0, 0.0));
        polygon.add(new Vector2d(-3.449775461918609, -49.91575059159863));
        polygon.add(new Vector2d(-32.00747084659096, -47.84521317948598));
        polygon.add(new Vector2d(-28.477127380211016, 2.359988573601399));
        polygon.add(new Vector2d(-1.8896859218159001, 0.4118847265054484));
        polygon.add(new Vector2d(-1.076681513583651, 8.983546745004219));
        polygon.add(new Vector2d(-38.86307558626359, 11.722028555877124));
        polygon.add(new Vector2d(-39.82989163930171, 1.235654268981051));
        polygon.add(new Vector2d(-39.57353889795221, 1.1688621375393211));
        polygon.add(new Vector2d(-42.78893471068021, -44.80619719787981));
        polygon.add(new Vector2d(-43.29431582931987, -44.739405620383884));
        polygon.add(new Vector2d(-44.107320237571265, -56.071698676456236));
        polygon.add(new Vector2d(-43.59461475489141, -56.160753933575315));
        polygon.add(new Vector2d(-46.810010567638564, -101.75685857404028));
        polygon.add(new Vector2d(-47.35933787048111, -101.76799038995375));
        polygon.add(new Vector2d(-48.27488337525699, -113.34506692472931));
        polygon.add(new Vector2d(-38.445586836087166, -114.17995132343053));
        polygon.add(new Vector2d(-35.947978699068834, -114.29126923500537));
        polygon.add(new Vector2d(-35.88205942274534, -113.94618370458886));
        polygon.add(new Vector2d(49.75440491095583, -120.25790596585914));
        polygon.add(new Vector2d(54.76426991305375, -54.53549526978831));
        polygon.add(new Vector2d(45.0521631984888, -53.48909560539279));
        polygon.add(new Vector2d(7.544094959278954, -50.661589146582685));
        polygon.add(new Vector2d(10.884004960632883, -0.8237693598695728));

        List<Vector2dc> hole1 = new ArrayList<Vector2dc>();
        hole1.add(new Vector2d(-32.70328543019918, -59.789754440995665));
        hole1.add(new Vector2d(-4.277428598269555, -61.80462793570683));
        hole1.add(new Vector2d(-7.6612847838646765, -105.00734788384477));
        hole1.add(new Vector2d(-35.81614014642186, -102.66966741031422));

        List<Vector2dc> hole2 = new ArrayList<Vector2dc>();
        hole2.add(new Vector2d(6.716441822947157, -62.56159679612162));
        hole2.add(new Vector2d(43.931535500663955, -65.1887232176776));
        hole2.add(new Vector2d(40.547679315068834, -108.54726216465428));
        hole2.add(new Vector2d(3.3985049136946692, -105.76431087574309));

        List<Vector2dc> expected = new ArrayList<Vector2dc>(polygon);
        expected.addAll(hole1);
        expected.addAll(hole2);

        expected.add(p(-37.40304820221923, -53.45566777183707));
        expected.add(p(1.2308295151655257, -56.22543247335505));
        expected.add(p(1.2418694405297994, -56.21592633472435));
        expected.add(p(2.0685200860081134, -56.275071768434536));
        expected.add(p(-37.865316156775926, -52.92219295248877));
        expected.add(p(-38.00757692599509, -51.30239327504694));
        expected.add(p(-37.77240159445469, -51.6027340227147));
        expected.add(p(-32.4563297954646, 6.95475731583909));
        expected.add(p(-33.81938207957798, 5.7733665730676));
        expected.add(p(-33.846870681532394, 5.435037665397305));
        expected.add(p(-33.77511306031848, 5.335128671348168));
        expected.add(p(-37.72619997598307, -51.00590465192027));
        expected.add(p(-38.00253986955662, -51.23147902026289));
        expected.add(p(-5.775161578295069, 5.010443864468215));
        expected.add(p(-41.66727917900361, -107.16413874084631));
        expected.add(p(-37.85195162338355, -53.84528728569551));
        expected.add(p(-41.98574589452088, -107.46639896884784));
        expected.add(p(-41.99494725727043, -108.01709310445455));
        expected.add(p(-42.02484223934048, -107.98192433513248));
        expected.add(p(-40.71715681621303, -108.12440680480971));
        expected.add(p(-38.26315516688428, -111.36150863716954));
        expected.add(p(-40.48316655380061, -107.94667257598242));
        expected.add(p(44.68880515744339, -114.37126555818111));
        expected.add(p(-2.536120507280202, -110.86206414368192));
        expected.add(p(-2.5544472989708447, -110.84075467187192));
        expected.add(p(-2.6378939496615468, -110.91241130595492));
        expected.add(p(48.55341422106077, -59.70022823257573));
        expected.add(p(49.29895455451054, -60.563900481485625));
        expected.add(p(45.181132909319146, -113.94812030999066));
        expected.add(p(44.516540596573, -59.340393843150224));
        expected.add(p(2.1034067331554063, -56.24474073663414));
        expected.add(p(1.6754349853647656, -55.7501137680009));
        expected.add(p(5.0709678082420595, -5.862535052040852));
        expected.add(p(1.6444375868952086, -56.70499189899596));
        expected.add(p(-4.277428598269555, -61.80462793570683));
        expected.add(p(-7.6612847838646765, -105.00734788384477));
        expected.add(p(-35.81614014642186, -102.66966741031422));
        expected.add(p(-32.70328543019918, -59.789754440995665));
        expected.add(p(43.931535500663955, -65.1887232176776));
        expected.add(p(40.547679315068834, -108.54726216465428));
        expected.add(p(3.3985049136946692, -105.76431087574309));
        expected.add(p(6.716441822947157, -62.56159679612162));

        vd.debug(polygon);

        SkeletonOutput sk = Skeleton.skeleton(polygon, Arrays.asList(hole1, hole2));

        writeExpectedOutput(polygon, sk);

        visualizeResults(polygon, sk);

        validate(polygon, sk);

        assertExpectedPoints(expected, getFacePoints(sk));
    }

    @Test
    public void skeleton_2() {

        vd.clear();

        List<Vector2dc> polygon = new ArrayList<Vector2dc>();
        polygon.add(new Vector2d(0.0, 0.0));
        polygon.add(new Vector2d(0.8276566632200648, 11.888928006049232));
        polygon.add(new Vector2d(-27.730160414042565, 13.959474241319969));
        polygon.add(new Vector2d(-24.199801903849206, 64.16488993327617));
        polygon.add(new Vector2d(-35.2962607066056, 62.973758421480234));
        polygon.add(new Vector2d(-38.51167022106408, 16.998503173051198));
        polygon.add(new Vector2d(-39.01705349327869, 17.065295035165335));
        polygon.add(new Vector2d(-39.830061365976746, 5.732953688918397));
        polygon.add(new Vector2d(-39.31735369851071, 5.643898052309142));
        polygon.add(new Vector2d(-42.53276321298834, -39.9524008863303));
        polygon.add(new Vector2d(-31.53884594371366, -40.8652136123464));
        polygon.add(new Vector2d(-28.42597796271775, 2.014882080669223));

        List<Vector2dc> expected = new ArrayList<Vector2dc>(polygon);
        expected.add(p(-33.12576076178471, 8.348995741201023));
        expected.add(p(-5.534565848874263, 6.370910126769063));
        expected.add(p(-33.5880306861987, 8.882472833839651));
        expected.add(p(-33.73029206163186, 10.502279413716543));
        expected.add(p(-33.495115727941425, 10.201937386209625));
        expected.add(p(-30.13850707997531, 58.0039423706306));
        expected.add(p(-33.448913912591436, 10.798769300265931));
        expected.add(p(-33.7252549837291, 10.57319397068576));
        expected.add(p(-36.64164137666454, -34.90132272042725));
        expected.add(p(-33.5746660958561, 7.959374567061363));

        vd.debug(polygon);

        SkeletonOutput sk = Skeleton.skeleton(polygon, null);

        writeExpectedOutput(polygon, sk);

        visualizeResults(polygon, sk);

        validate(polygon, sk);

        assertExpectedPoints(expected, getFacePoints(sk));
    }

    @Test
    public void skeleton_3() {

        vd.clear();

        List<Vector2dc> polygon = new ArrayList<Vector2dc>();
        polygon.add(new Vector2d(0.0, 0.0));
        polygon.add(new Vector2d(-0.695841208347133, -9.161985802862006));
        polygon.add(new Vector2d(-9.338921480375689, -8.571967728493888));
        polygon.add(new Vector2d(-17.703665269065453, -7.447593490720266));
        polygon.add(new Vector2d(-17.42532878570745, -3.373126538389357));
        polygon.add(new Vector2d(-34.86530686005406, -2.0261026523114936));
        polygon.add(new Vector2d(-37.17989445833143, -1.81458647758825));
        polygon.add(new Vector2d(-55.19119478795656, -0.5677541139705511));
        polygon.add(new Vector2d(-58.11372786299535, -38.75186865681468));
        polygon.add(new Vector2d(-50.07126842338943, -39.34188353361671));
        polygon.add(new Vector2d(-51.126017202341586, -51.888034167850435));
        polygon.add(new Vector2d(-51.536197283087745, -56.652669598441975));
        polygon.add(new Vector2d(-64.58138877837769, -55.528305063057076));
        polygon.add(new Vector2d(-72.68244537234855, -54.81583632791774));
        polygon.add(new Vector2d(-69.18859004203122, -7.625712198986719));
        polygon.add(new Vector2d(-79.09883377766633, -6.7239861902833695));
        polygon.add(new Vector2d(-78.63738118687476, 1.1689057121689643));
        polygon.add(new Vector2d(-88.04222446591587, 2.070633143170312));
        polygon.add(new Vector2d(-87.65401831812835, 7.380808750431726));
        polygon.add(new Vector2d(-92.65675037390812, 7.748179577427749));
        polygon.add(new Vector2d(-107.56972616530382, 8.816894846881922));
        polygon.add(new Vector2d(-112.57245822106444, 9.184265769474024));
        polygon.add(new Vector2d(-112.96066436885195, 3.77389646371617));
        polygon.add(new Vector2d(-114.92366904082473, 3.8629559986240167));
        polygon.add(new Vector2d(-115.04086334961207, 2.538195570451535));
        polygon.add(new Vector2d(-116.69623296100607, 2.638387525545255));
        polygon.add(new Vector2d(-116.62298651802115, 3.952015535370265));
        polygon.add(new Vector2d(-122.2629626277448, 4.308253694611125));
        polygon.add(new Vector2d(-122.84893417162414, -3.573510226744844));
        polygon.add(new Vector2d(-133.24992907525242, -2.7719754218481114));
        polygon.add(new Vector2d(-136.29698110334067, -45.63166089550802));
        polygon.add(new Vector2d(-136.56066829809785, -49.739493961847415));
        polygon.add(new Vector2d(-136.5972915195616, -49.87308197764537));
        polygon.add(new Vector2d(-144.44198556309482, -49.038156822071414));
        polygon.add(new Vector2d(-154.15446390269554, -47.99171711813307));
        polygon.add(new Vector2d(-152.87997579476948, -31.693949992923137));
        polygon.add(new Vector2d(-148.39729348418453, -32.06131821982851));
        polygon.add(new Vector2d(-145.6944997381005, 6.067179931716822));
        polygon.add(new Vector2d(-183.84124724381752, 8.939351818832364));
        polygon.add(new Vector2d(-184.18550552584088, 4.33051857910408));
        polygon.add(new Vector2d(-188.32392955442162, 4.675624318771895));
        polygon.add(new Vector2d(-199.20835098172356, 5.499425199435763));
        polygon.add(new Vector2d(-201.0981092106731, 5.655279433782141));
        polygon.add(new Vector2d(-201.0981092106731, 5.911325686340149));
        polygon.add(new Vector2d(-200.28507369355776, 14.483315691783218));
        polygon.add(new Vector2d(-199.75037465980046, 23.623075402548864));
        polygon.add(new Vector2d(-195.63392456412095, 23.222306088178982));
        polygon.add(new Vector2d(-192.47700287154572, 67.60768288193731));
        polygon.add(new Vector2d(-193.30468767722357, 67.66334572802566));
        polygon.add(new Vector2d(-193.24609052287775, 68.36469764229747));
        polygon.add(new Vector2d(-193.13622085837167, 68.36469764229747));
        polygon.add(new Vector2d(-192.38178249564044, 80.51034556245332));
        polygon.add(new Vector2d(-192.50630144874737, 80.54374334940206));
        polygon.add(new Vector2d(-192.41108107288036, 81.5902073856052));
        polygon.add(new Vector2d(-191.62734413292836, 81.5456769983088));
        polygon.add(new Vector2d(-188.2946309771817, 125.5975377539537));
        polygon.add(new Vector2d(-192.32318534125633, 125.93151831483829));
        polygon.add(new Vector2d(-190.80698397153176, 143.8217730301796));
        polygon.add(new Vector2d(-173.83578313229629, 142.43018301674232));
        polygon.add(new Vector2d(-173.9456527967258, 138.0550262829267));
        polygon.add(new Vector2d(-107.15954608457682, 132.88945125194346));
        polygon.add(new Vector2d(-106.35383521178105, 132.900583951564));
        polygon.add(new Vector2d(-105.02074994949004, 133.14550334995624));
        polygon.add(new Vector2d(-103.43862678102352, 133.8691289086305));
        polygon.add(new Vector2d(-102.2813329819231, 134.82654132846721));
        polygon.add(new Vector2d(-101.31447993453753, 136.14020049587162));
        polygon.add(new Vector2d(-100.20845864548255, 136.05113884865818));
        polygon.add(new Vector2d(-80.4392436842769, 134.5036929371352));
        polygon.add(new Vector2d(-79.47971528123003, 134.42576401589253));
        polygon.add(new Vector2d(-78.85712051582951, 133.11210525048187));
        polygon.add(new Vector2d(-77.91224140138345, 132.0099680511679));
        polygon.add(new Vector2d(-76.60845471625578, 131.07482150365024));
        polygon.add(new Vector2d(-75.64892631317062, 130.64064637716376));
        polygon.add(new Vector2d(-73.99355670177661, 130.28440014311866));
        polygon.add(new Vector2d(-7.85201868781445, 125.31922063111779));
        polygon.add(new Vector2d(-7.49311111722858, 130.0506135632292));
        polygon.add(new Vector2d(9.60993331941421, 128.65902699125058));
        polygon.add(new Vector2d(9.236376460189206, 123.92763524122142));
        polygon.add(new Vector2d(8.181627681237046, 110.5350351927255));
        polygon.add(new Vector2d(4.255618337310636, 110.85788219852725));
        polygon.add(new Vector2d(0.9448791144652091, 67.1512475606807));
        polygon.add(new Vector2d(1.8091871416834238, 67.01765674256961));
        polygon.add(new Vector2d(1.7579146315805474, 66.12705137122322));
        polygon.add(new Vector2d(1.5674738798274461, 66.1604490693166));
        polygon.add(new Vector2d(0.6592179868259791, 53.858976831483176));
        polygon.add(new Vector2d(0.8936066043815458, 53.88124191758578));
        polygon.add(new Vector2d(0.7690876513129297, 53.05743388498746));
        polygon.add(new Vector2d(-0.1171943088256171, 53.09083150464306));
        polygon.add(new Vector2d(-3.2448174241800416, 9.039543887906007));
        polygon.add(new Vector2d(-0.16846681885186854, 8.816894846881922));
        polygon.add(new Vector2d(-0.14649288598894827, 8.995014077862805));
        polygon.add(new Vector2d(0.6811919197272118, 8.89482200817639));
        polygon.add(new Vector2d(0.6299194096243355, 8.015358375794648));
        polygon.add(new Vector2d(0.4980758122744646, 8.02649082539466));

        List<Vector2dc> expected = new ArrayList<Vector2dc>(polygon);

        expected.add(p(0.22498766501530973, 8.482519288941951));
        expected.add(p(0.22804291428188161, 8.516190461338422));
        expected.add(p(0.15399233874051674, 8.42451956393686));
        expected.add(p(0.15402882814217608, 8.424913516260558));
        expected.add(p(-8.54200371397651, 0.8238610560514159));
        expected.add(p(-10.420788871410501, 2.8235982695492625));
        expected.add(p(-10.26903498604995, 2.812246226874768));
        expected.add(p(-61.03194322798886, 67.36487419298273));
        expected.add(p(-42.70885984371319, 46.18933004077511));
        expected.add(p(-43.10454118850721, 40.69777951729232));
        expected.add(p(-34.5562630110808, 30.860444838236432));
        expected.add(p(-32.31508760437524, 28.223224732309113));
        expected.add(p(-33.924443240636386, 90.78233450985698));
        expected.add(p(0.44132245629059363, 53.45330511858461));
        expected.add(p(0.2568264964007361, 53.448041591527556));
        expected.add(p(-6.2775029556709985, 60.75381589825485));
        expected.add(p(-7.024664018198711, 60.8253933087784));
        expected.add(p(-33.75867049403901, 90.77299217258242));
        expected.add(p(1.1670534531361396, 66.67452948203062));
        expected.add(p(1.347945148670707, 66.64469142554623));
        expected.add(p(-1.946448792095577, 118.12378669561315));
        expected.add(p(0.31749588484094743, 119.77603117590006));
        expected.add(p(-1.64604883910226, 118.10016025919867));
        expected.add(p(0.3976890543497383, 120.81337575173194));
        expected.add(p(4.49313037957261, 124.30166138509239));
        expected.add(p(-82.84787179397482, 68.9847390401527));
        expected.add(p(-88.1587841936477, 69.74745652292202));
        expected.add(p(-95.38349396241996, 70.77852777631168));
        expected.add(p(-96.05017083682696, 70.94750272264358));
        expected.add(p(-93.0937410803582, 102.51935396169492));
        expected.add(p(-93.079037281847, 95.258235108459));
        expected.add(p(-85.44762264934579, 123.79275707510793));
        expected.add(p(-93.04921921413377, 102.97870948112161));
        expected.add(p(-81.62541506414908, 131.31555179454497));
        expected.add(p(-80.57146143536926, 132.84569707591066));
        expected.add(p(-100.36640306568131, 134.06191051530428));
        expected.add(p(-99.52440324495109, 132.2216016700931));
        expected.add(p(-96.58802312702588, 122.94381884235717));
        expected.add(p(-100.25538994648302, 70.71565252737079));
        expected.add(p(-109.12205517651584, 70.97696845487435));
        expected.add(p(-103.07315010584149, 70.79700241858225));
        expected.add(p(-130.04846339460948, 72.55459509445708));
        expected.add(p(-180.95914413698014, 131.72465762799146));
        expected.add(p(-154.41532924384578, 100.78324415201128));
        expected.add(p(-154.37302261423963, 100.78018553816237));
        expected.add(p(-182.83155419538986, 133.9549226026316));
        expected.add(p(-180.95226691409226, 131.8612428089489));
        expected.add(p(-182.80884697953078, 134.36866502388702));
        expected.add(p(-185.14914103411456, 74.14850324834707));
        expected.add(p(-191.8833170920917, 80.97823536977444));
        expected.add(p(-191.75885179775784, 80.9582255955295));
        expected.add(p(-185.74646660333013, 74.18555712287022));
        expected.add(p(-192.790802030354, 67.9971570595962));
        expected.add(p(-192.91171294260738, 68.00121817853841));
        expected.add(p(-189.52636703457816, 15.99127614854962));
        expected.add(p(-151.7889624016801, 48.580184914836835));
        expected.add(p(-151.395684217932, 54.085295129682756));
        expected.add(p(-191.86576355588355, 14.406244269410973));
        expected.add(p(-189.93997467912087, 16.026976659688845));
        expected.add(p(-191.90341501448117, 13.840861024550493));
        expected.add(p(-191.94222460277786, 13.383001452589115));
        expected.add(p(-200.83366413488636, 5.898812731214532));
        expected.add(p(-199.05930751289122, 7.3841722081856656));
        expected.add(p(-187.99625509168078, 8.795385136091523));
        expected.add(p(-128.16598548965533, 21.207923480625293));
        expected.add(p(-142.36398338374596, -39.093021096907265));
        expected.add(p(-139.28829638605566, 4.232583370577451));
        expected.add(p(-133.69801956583996, 9.052755916650366));
        expected.add(p(-133.13637875553084, 16.787190970589442));
        expected.add(p(-128.1716717205728, 21.128194314745024));
        expected.add(p(-144.25945998535835, -40.71903018667486));
        expected.add(p(-145.2751618131424, -40.62270950905692));
        expected.add(p(-143.63155810770348, -41.47020488624024));
        expected.add(p(-136.70487119110868, -49.71535770209509));
        expected.add(p(-140.37958427782107, -45.35550673546696));
        expected.add(p(-115.71209195305535, 4.760665687614364));
        expected.add(p(-115.8930581774467, 4.971317216729401));
        expected.add(p(-115.78567551496056, 6.95029279409076));
        expected.add(p(-115.80810094628717, 3.42891897234015));
        expected.add(p(-70.40372469188209, 16.961036189377545));
        expected.add(p(-70.39852981923862, 17.030466134490204));
        expected.add(p(-66.13181959009381, 11.883176833821494));
        expected.add(p(-66.66517916226239, 3.98104312167081));
        expected.add(p(-62.00204163227642, -1.5346672314106609));
        expected.add(p(-63.474199556768475, -46.98678351805689));
        expected.add(p(-65.26752960327565, -44.90808428113797));
        expected.add(p(-63.86680204425184, -47.32114798065856));
        expected.add(p(-59.40280006237284, -47.311571131294606));
        expected.add(p(-55.8858137547639, -51.48307593289708));
        expected.add(p(-8.55721925107371, 0.590431430331766));
        expected.add(p(-8.601175396264287, -0.018974469694361673));
        expected.add(p(-8.487318836690127, -0.15957288669922365));

        vd.debug(polygon);

        SkeletonOutput sk = Skeleton.skeleton(polygon, null);

        writeExpectedOutput(polygon, sk);

        visualizeResults(polygon, sk);

        validate(polygon, sk);

        assertExpectedPoints(expected, getFacePoints(sk));
    }

    @Test
    public void skeleton_4() {

        vd.clear();

        List<Vector2dc> polygon = new ArrayList<Vector2dc>();
        polygon.add(new Vector2d(0.0, 0.0));
        polygon.add(new Vector2d(-0.6958226660995663, -9.161741661229058));
        polygon.add(new Vector2d(-9.338672623895327, -8.571739309214053));
        polygon.add(new Vector2d(-17.703193515251265, -7.447395032910407));
        polygon.add(new Vector2d(-17.424864448792285, -3.3730366538775343));
        polygon.add(new Vector2d(-34.864377795860584, -2.0260486622682095));
        polygon.add(new Vector2d(-37.178903716767145, -1.8145381238672087));
        polygon.add(new Vector2d(-55.18972409495281, -0.5677389848905392));
        polygon.add(new Vector2d(-58.11217929255184, -38.75083602647692));
        polygon.add(new Vector2d(-50.06993416229141, -39.34083518101102));
        polygon.add(new Vector2d(-51.12465483509989, -51.88665149495991));
        polygon.add(new Vector2d(-51.53482398567908, -56.65115996116844));
        polygon.add(new Vector2d(-64.57966786262266, -55.52682538699497));
        polygon.add(new Vector2d(-72.68050858579562, -54.814375637180895));
        polygon.add(new Vector2d(-69.18674635707923, -7.625508994804932));
        polygon.add(new Vector2d(-79.09672601207276, -6.7238070146107844));
        polygon.add(new Vector2d(-78.63528571771906, 1.1688745640581146));
        polygon.add(new Vector2d(-88.03987838364577, 2.07057796651236));
        polygon.add(new Vector2d(-87.6516825804806, 7.380612072250827));
        polygon.add(new Vector2d(-92.65428132725948, 7.747973109828322));
        polygon.add(new Vector2d(-107.56685972901278, 8.816659900970421));
        polygon.add(new Vector2d(-112.56945847577252, 9.184021034141438));
        polygon.add(new Vector2d(-112.9576542789377, 3.7737958997920202));
        polygon.add(new Vector2d(-114.9206066422542, 3.8628530615090995));
        polygon.add(new Vector2d(-115.0377978281367, 2.538127934545443));
        polygon.add(new Vector2d(-116.69312332849967, 2.6383172198000864));
        polygon.add(new Vector2d(-116.61987883733028, 3.951910225064495));
        polygon.add(new Vector2d(-122.25970465725791, 4.308138891541702));
        polygon.add(new Vector2d(-122.84566058661298, -3.5734150025608216));
        polygon.add(new Vector2d(-133.24637833243565, -2.7719015563542513));
        polygon.add(new Vector2d(-136.29334916499775, -45.63044493769651));
        polygon.add(new Vector2d(-136.55702933321902, -49.73816854162303));
        polygon.add(new Vector2d(-136.593651578775, -49.87175299766909));
        polygon.add(new Vector2d(-144.43813658286467, -49.03685009054598));
        polygon.add(new Vector2d(-154.15035611172578, -47.99043827133739));
        polygon.add(new Vector2d(-152.87590196539, -31.693105436636657));
        polygon.add(new Vector2d(-148.39333910591563, -32.06046387419279));
        polygon.add(new Vector2d(-145.69061738182475, 6.067018258118125));
        polygon.add(new Vector2d(-183.83634838201243, 8.939113609780577));
        polygon.add(new Vector2d(-184.1805974905028, 4.330403182736944));
        polygon.add(new Vector2d(-188.31891124150596, 4.675499726289345));
        polygon.add(new Vector2d(-199.20304262901976, 5.499278654933562));
        polygon.add(new Vector2d(-201.0927505011286, 5.655128736194783));
        polygon.add(new Vector2d(-201.0927505011286, 5.911168165826879));
        polygon.add(new Vector2d(-200.2797366491656, 14.482929751396512));
        polygon.add(new Vector2d(-199.74505186366167, 23.622445912793196));
        polygon.add(new Vector2d(-195.6287114600151, 23.22168727781937));
        polygon.add(new Vector2d(-192.47187389068938, 67.6058813237975));
        polygon.add(new Vector2d(-193.29953664085167, 67.66154268662468));
        polygon.add(new Vector2d(-193.2409410479583, 68.36287591180381));
        polygon.add(new Vector2d(-193.1310743111755, 68.36287591180381));
        polygon.add(new Vector2d(-192.37665605214426, 80.50820018396671));
        polygon.add(new Vector2d(-192.5011716871648, 80.5415970809566));
        polygon.add(new Vector2d(-192.405953848658, 81.58803323178161));
        polygon.add(new Vector2d(-191.62223779313217, 81.5435040310971));
        polygon.add(new Vector2d(-188.28961344499226, 125.59419092624368));
        polygon.add(new Vector2d(-192.31806045921257, 125.92816258746815));
        polygon.add(new Vector2d(-190.80189949206954, 143.81794057690155));
        polygon.add(new Vector2d(-173.83115088849297, 142.42638764549713));
        polygon.add(new Vector2d(-173.94101762519915, 138.05134749753228));
        polygon.add(new Vector2d(-107.15669057845274, 132.88591011486568));
        polygon.add(new Vector2d(-106.35100117562781, 132.89704251783047));
        polygon.add(new Vector2d(-105.0179514363795, 133.1419553897968));
        polygon.add(new Vector2d(-103.43587042712848, 133.86556166584722));
        polygon.add(new Vector2d(-102.2786074667135, 134.8229485732856));
        polygon.add(new Vector2d(-101.31178018329295, 136.13657273529913));
        polygon.add(new Vector2d(-100.2057883666525, 136.04751346133276));
        polygon.add(new Vector2d(-80.43710020045908, 134.5001087849721));
        polygon.add(new Vector2d(-79.4775973661957, 134.42218194032006));
        polygon.add(new Vector2d(-78.8550191912272, 133.1085581802896));
        polygon.add(new Vector2d(-77.91016525520152, 132.00645034988992));
        polygon.add(new Vector2d(-76.60641331239033, 131.07132872144655));
        polygon.add(new Vector2d(-75.64691047808864, 130.63716516452882));
        polygon.add(new Vector2d(-73.99158497772568, 130.2809284234625));
        polygon.add(new Vector2d(-7.8518094531893965, 125.31588121979091));
        polygon.add(new Vector2d(-7.492911446499633, 130.0471480733401));
        polygon.add(new Vector2d(9.609677241216303, 128.65559858330266));
        polygon.add(new Vector2d(9.23613033625054, 123.92433291180417));
        polygon.add(new Vector2d(8.181409663442054, 110.53208973913361));
        polygon.add(new Vector2d(4.255504936828203, 110.85492814195375));
        polygon.add(new Vector2d(0.9448539360448365, 67.14945816528235));
        polygon.add(new Vector2d(1.8091389318397262, 67.01587090699788));
        polygon.add(new Vector2d(1.7578677880077578, 66.12528926782637));
        polygon.add(new Vector2d(1.5674321109750196, 66.1586860759633));
        polygon.add(new Vector2d(0.6592004204861581, 53.85754163841078));
        polygon.add(new Vector2d(0.8935827922320172, 53.879806131210316));
        polygon.add(new Vector2d(0.7690671572498076, 53.0560200508222));
        polygon.add(new Vector2d(-0.11719118592079525, 53.08941678052345));
        polygon.add(new Vector2d(-3.244730958751987, 9.039303009012116));
        polygon.add(new Vector2d(-0.16846232967617425, 8.816659900970421));
        polygon.add(new Vector2d(-0.14648898235791563, 8.994774385565435));
        polygon.add(new Vector2d(0.681173767842715, 8.89458498572111));
        polygon.add(new Vector2d(0.6299026240107466, 8.015144788617704));
        polygon.add(new Vector2d(0.4980625399288314, 8.026276941568668));

        List<Vector2dc> hole1 = new ArrayList<Vector2dc>();
        hole1.add(new Vector2d(-50.626592295171065, 57.28627419317921));
        hole1.add(new Vector2d(-32.90874988172038, 55.905875118745726));
        hole1.add(new Vector2d(-12.81046150523602, 54.436418417495915));
        hole1.add(new Vector2d(-16.1430858533951, 9.774025324989267));
        hole1.add(new Vector2d(-36.021640756313865, 11.132148650485448));
        hole1.add(new Vector2d(-53.97386554152955, 12.523668798212483));

        List<Vector2dc> hole2 = new ArrayList<Vector2dc>();
        hole2.add(new Vector2d(-124.01757244530397, 116.28752197217447));
        hole2.add(new Vector2d(-119.22005827379552, 115.94241851953866));
        hole2.add(new Vector2d(-118.69269793739127, 120.72933930719502));
        hole2.add(new Vector2d(-99.9787304440255, 119.29326263864381));
        hole2.add(new Vector2d(-98.93865866945472, 119.17080650567986));
        hole2.add(new Vector2d(-99.07782320269378, 118.33587839924526));
        hole2.add(new Vector2d(-84.94163640728999, 117.20037637359833));
        hole2.add(new Vector2d(-84.01143136944458, 117.1558468879716));
        hole2.add(new Vector2d(-83.8283201415307, 118.01303956625422));
        hole2.add(new Vector2d(-64.17682316121977, 116.42111041189692));
        hole2.add(new Vector2d(-64.49177447325387, 111.71211984775611));
        hole2.add(new Vector2d(-59.73820699643939, 111.21116364091701));
        hole2.add(new Vector2d(-62.821800074614906, 72.25917320625628));
        hole2.add(new Vector2d(-63.86187184920483, 72.29257005115998));
        hole2.add(new Vector2d(-65.02645925879611, 57.486654731321906));
        hole2.add(new Vector2d(-63.854547400105126, 57.39759671539089));
        hole2.add(new Vector2d(-66.91616713090494, 18.256735459329477));
        hole2.add(new Vector2d(-71.94806367410179, 18.53503981560867));
        hole2.add(new Vector2d(-72.36555727379984, 13.68141382404771));
        hole2.add(new Vector2d(-91.80464522972144, 15.139727611334237));
        hole2.add(new Vector2d(-91.81196967882116, 15.796525549094895));
        hole2.add(new Vector2d(-106.98090379963855, 16.954271252663702));
        hole2.add(new Vector2d(-107.01752604523281, 16.252944499656536));
        hole2.add(new Vector2d(-126.71296972025685, 17.80031634515585));
        hole2.add(new Vector2d(-126.41266730646046, 22.587152829738997));
        hole2.add(new Vector2d(-131.21018147798807, 22.92111832089155));
        hole2.add(new Vector2d(-128.10461505245598, 61.91729293652652));
        hole2.add(new Vector2d(-127.29892564959277, 61.883896154126276));
        hole2.add(new Vector2d(-126.11968934180209, 76.93473355906319));
        hole2.add(new Vector2d(-127.0132721340341, 77.01265959668525));

        List<Vector2dc> hole3 = new ArrayList<Vector2dc>();
        hole3.add(new Vector2d(-46.20262502860923, 115.1297556295956));
        hole3.add(new Vector2d(-28.675218292229594, 113.62688621736781));
        hole3.add(new Vector2d(-26.111661101320323, 113.45990075074631));
        hole3.add(new Vector2d(-8.642849957853208, 112.13514957270836));
        hole3.add(new Vector2d(-11.997447653311397, 67.48342632631308));
        hole3.add(new Vector2d(-29.349067610915156, 68.67457959412893));
        hole3.add(new Vector2d(-49.63779166441311, 70.3666856096463));

        List<Vector2dc> hole4 = new ArrayList<Vector2dc>();
        hole4.add(new Vector2d(-175.32533850831774, 124.92624766139598));
        hole4.add(new Vector2d(-136.64492272262612, 122.14315157976579));
        hole4.add(new Vector2d(-140.09473825664855, 77.22417313770343));
        hole4.add(new Vector2d(-178.54077167057517, 79.99611448486313));

        List<Vector2dc> hole5 = new ArrayList<Vector2dc>();
        hole5.add(new Vector2d(-179.48562560660088, 67.00473863516775));
        hole5.add(new Vector2d(-161.5334008214043, 65.75792438138386));
        hole5.add(new Vector2d(-141.1055122347056, 64.26620054748251));
        hole5.add(new Vector2d(-144.59927446344116, 19.113912924760257));
        hole5.add(new Vector2d(-182.70838321803458, 21.908089728798558));

        List<Vector2dc> expected = new ArrayList<Vector2dc>(polygon);
        expected.add(p(0.2249816697150181, 8.482293253216136));
        expected.add(p(0.22803683756762555, 8.515963528368783));
        expected.add(p(0.15398823526971633, 8.424295073743616));
        expected.add(p(0.1540247236990356, 8.42468901556959));
        expected.add(p(-8.365791464345522, 0.9776569121545724));
        expected.add(p(-10.15637538630298, 3.0523218440220345));
        expected.add(p(-6.033031633067507, 59.68495250251456));
        expected.add(p(-5.638304516214914, 60.037721608792964));
        expected.add(p(0.44131069626525193, 53.45188073554367));
        expected.add(p(0.2568196526835025, 53.44661734874506));
        expected.add(p(-5.667345875184285, 60.74917781504467));
        expected.add(p(-5.591977737303455, 60.661856333037015));
        expected.add(p(1.1670223543828715, 66.67275278985329));
        expected.add(p(-5.628699037858321, 61.26806273860997));
        expected.add(p(1.3479092296530713, 66.64291552847074));
        expected.add(p(-5.951972691587438, 61.67507659524886));
        expected.add(p(-1.7143106114929947, 117.84874306434608));
        expected.add(p(0.41047938805791095, 119.66356633687613));
        expected.add(p(0.39767845703409316, 120.81015640873413));
        expected.add(p(0.33846546184670323, 120.04420240351597));
        expected.add(p(0.4314162016030956, 119.9356785891813));
        expected.add(p(4.493010650049806, 124.29834908891077));
        expected.add(p(-1.76593328282424, 118.23651916020778));
        expected.add(p(-54.901254068605496, 119.79252408249731));
        expected.add(p(-52.16309483984043, 122.14432957206378));
        expected.add(p(-28.17819279532076, 120.21584243841357));
        expected.add(p(-25.647972685755306, 120.03846926116186));
        expected.add(p(-74.92440906245717, 123.82285688802502));
        expected.add(p(-57.19737372915414, 122.43947740322916));
        expected.add(p(-77.7557275935224, 124.24035614822363));
        expected.add(p(-80.18724872001978, 124.86868067534958));
        expected.add(p(-83.38140586800331, 126.04015124215248));
        expected.add(p(-84.1124522333056, 126.41283338025231));
        expected.add(p(-81.62323997213802, 131.31205259754347));
        expected.add(p(-80.56931442831332, 132.8421571047609));
        expected.add(p(-90.66051715864245, 126.7646272937065));
        expected.add(p(-90.45504281650548, 126.9179676841688));
        expected.add(p(-100.36372857806842, 134.05833813542327));
        expected.add(p(-99.52175119430943, 132.21807832936338));
        expected.add(p(-98.02407683815397, 127.48602836816929));
        expected.add(p(-99.21522090505088, 127.13448991819801));
        expected.add(p(-102.91674941107634, 126.47550753781123));
        expected.add(p(-99.21660207915032, 127.13427285103754));
        expected.add(p(-105.70685097286587, 126.32873954173138));
        expected.add(p(-107.36289244237695, 126.38073293868636));
        expected.add(p(-127.4258827936708, 125.49136379939372));
        expected.add(p(-127.15919075606473, 125.47145820265864));
        expected.add(p(-124.47114610950243, 127.69878073899905));
        expected.add(p(-180.70868264233985, 131.94286137939636));
        expected.add(p(-129.69074470397246, 128.1344995386893));
        expected.add(p(-182.80983937887416, 134.25824255856304));
        expected.add(p(-182.80397562834332, 134.36508446984533));
        expected.add(p(-183.17761678911742, 133.6547033482638));
        expected.add(p(-183.15339642824802, 133.96460782482322));
        expected.add(p(-181.34661171496205, 131.51720354990977));
        expected.add(p(-185.53664975350972, 74.59464006170268));
        expected.add(p(-185.86398844413492, 74.32153862130903));
        expected.add(p(-185.8499574495516, 74.3338782746831));
        expected.add(p(-185.83430688566042, 74.33289021697996));
        expected.add(p(-191.8782039313171, 80.97607752330937));
        expected.add(p(-191.7537419536518, 80.95606828227162));
        expected.add(p(-192.78566468761807, 67.99534512304662));
        expected.add(p(-185.8814103945546, 74.06068487676215));
        expected.add(p(-192.90657237792533, 67.99940613377107));
        expected.add(p(-185.52625215606943, 73.65258966987969));
        expected.add(p(-189.62818520658115, 16.117376793271085));
        expected.add(p(-191.86065086286774, 14.405860382765093));
        expected.add(p(-189.93491330306315, 16.026549584998122));
        expected.add(p(-189.75422758817686, 16.010953711802575));
        expected.add(p(-191.89830131815808, 13.840492203807354));
        expected.add(p(-191.93710987228513, 13.382644832540873));
        expected.add(p(-200.8283124720732, 5.898655544136978));
        expected.add(p(-199.05400313178453, 7.383975440377998));
        expected.add(p(-187.99124551038702, 8.795150763359084));
        expected.add(p(-138.69344915982956, 12.111017659105492));
        expected.add(p(-189.65664341269283, 15.897914460858757));
        expected.add(p(-135.04126561084232, 7.891239374070181));
        expected.add(p(-142.36018977653836, -39.0919793757985));
        expected.add(p(-139.2845847374171, 4.232470583913447));
        expected.add(p(-144.2556158689312, -40.717945136911936));
        expected.add(p(-145.27129063106491, -40.621627025974306));
        expected.add(p(-143.62773072312805, -41.46909981974487));
        expected.add(p(-136.70122838362076, -49.71403292503541));
        expected.add(p(-140.3758435493721, -45.35429813640158));
        expected.add(p(-134.9232620566129, 7.900728804326753));
        expected.add(p(-134.942967864531, 7.883848209090504));
        expected.add(p(-132.5458291188786, 7.715731047434465));
        expected.add(p(-128.35889776852517, 11.307912226352965));
        expected.add(p(-115.70900854519036, 4.760538829014069));
        expected.add(p(-115.8899699473309, 4.971184744846792));
        expected.add(p(-115.7825901462963, 6.950107587971295));
        expected.add(p(-119.01094804129366, 10.645518078082683));
        expected.add(p(-115.80501498004752, 3.428827601113928));
        expected.add(p(-116.04159962195078, 13.199728321318375));
        expected.add(p(-107.29708782359991, 12.535154038777149));
        expected.add(p(-103.13412041941699, 12.579133230165542));
        expected.add(p(-103.50624935104037, 12.250411585767036));
        expected.add(p(-92.38530247180537, 11.455536458118205));
        expected.add(p(-95.48883004911708, 11.683153357865363));
        expected.add(p(-95.82302269642561, 12.038162736732057));
        expected.add(p(-83.68068986446949, 10.809420882677907));
        expected.add(p(-81.15654580389081, 7.881480125239472));
        expected.add(p(-71.73952633585725, 7.076888773189248));
        expected.add(p(-68.0137861878111, 2.818294591972319));
        expected.add(p(-65.50438770654293, 2.6100164797956382));
        expected.add(p(-62.00038944900339, -1.5346263367649169));
        expected.add(p(-63.47250814455016, -46.98553144996788));
        expected.add(p(-65.2657904037615, -44.90688760464605));
        expected.add(p(-63.8651001702609, -47.31988700267952));
        expected.add(p(-59.40121714171334, -47.310310408512166));
        expected.add(p(-55.88432455208203, -51.481704051025744));
        expected.add(p(-63.493143338951924, 9.04113728242509));
        expected.add(p(-63.61463985757753, 8.934667309992056));
        expected.add(p(-64.02540932318064, 3.8806339231729643));
        expected.add(p(-63.27449060208658, 9.027523193013822));
        expected.add(p(-36.658749818664845, 4.663523010577625));
        expected.add(p(-61.04556268813581, 6.452721359012415));
        expected.add(p(-34.316234218691456, 4.475735246171803));
        expected.add(p(-36.49431427030582, 4.649637390688037));
        expected.add(p(-10.361132390053818, 2.732332566616906));
        expected.add(p(-8.556991224800099, 0.5904156969638318));
        expected.add(p(-8.552552018036225, 0.6585199227143426));
        expected.add(p(-8.600946198680736, -0.01897396407711106));
        expected.add(p(-8.487092673069645, -0.15956863452221123));
        expected.add(p(-8.377075924061227, 0.8125185871118122));
        expected.add(p(-5.953157591363079, 60.50281151720168));
        expected.add(p(-29.8415701869625, 62.19603150411389));
        expected.add(p(-32.41854367902101, 62.39769348116456));
        expected.add(p(-32.90874988172038, 55.905875118745726));
        expected.add(p(-12.81046150523602, 54.436418417495915));
        expected.add(p(-16.1430858533951, 9.774025324989267));
        expected.add(p(-36.021640756313865, 11.132148650485448));
        expected.add(p(-53.97386554152955, 12.523668798212483));
        expected.add(p(-50.626592295171065, 57.28627419317921));
        expected.add(p(-56.693038982021505, 64.35555539795092));
        expected.add(p(-57.17892772133689, 63.94089000734394));
        expected.add(p(-56.77125643042684, 63.46674184599888));
        expected.add(p(-60.767450658536184, 11.22837568482691));
        expected.add(p(-119.22005827379552, 115.94241851953866));
        expected.add(p(-118.69269793739127, 120.72933930719502));
        expected.add(p(-99.9787304440255, 119.29326263864381));
        expected.add(p(-98.93865866945472, 119.17080650567986));
        expected.add(p(-90.01972125318964, 125.87001641080542));
        expected.add(p(-99.07782320269378, 118.33587839924526));
        expected.add(p(-90.12331268109257, 125.32493285097323));
        expected.add(p(-84.94163640728999, 117.20037637359833));
        expected.add(p(-84.87281448426252, 118.27435204907455));
        expected.add(p(-84.01143136944458, 117.1558468879716));
        expected.add(p(-83.8283201415307, 118.01303956625422));
        expected.add(p(-64.17682316121977, 116.42111041189692));
        expected.add(p(-64.49177447325387, 111.71211984775611));
        expected.add(p(-54.90205586611933, 119.78135858740818));
        expected.add(p(-59.73820699643939, 111.21116364091701));
        expected.add(p(-52.661711676339074, 117.09221034041578));
        expected.add(p(-56.6907648138197, 65.40614131149937));
        expected.add(p(-62.821800074614906, 72.25917320625628));
        expected.add(p(-57.24939742019113, 64.90515072470694));
        expected.add(p(-63.86187184920483, 72.29257005115998));
        expected.add(p(-57.31247802681735, 64.09329124993343));
        expected.add(p(-65.02645925879611, 57.486654731321906));
        expected.add(p(-63.854547400105126, 57.39759671539089));
        expected.add(p(-57.18190595593219, 63.94112736208829));
        expected.add(p(-66.91616713090494, 18.256735459329477));
        expected.add(p(-71.94806367410179, 18.53503981560867));
        expected.add(p(-72.36555727379984, 13.68141382404771));
        expected.add(p(-91.80464522972144, 15.139727611334237));
        expected.add(p(-91.81196967882116, 15.796525549094895));
        expected.add(p(-106.98090379963855, 16.954271252663702));
        expected.add(p(-107.01752604523281, 16.252944499656536));
        expected.add(p(-126.71296972025685, 17.80031634515585));
        expected.add(p(-136.2843683663649, 9.492038236642747));
        expected.add(p(-126.41266730646046, 22.587152829738997));
        expected.add(p(-135.95234712855262, 14.231648221499476));
        expected.add(p(-131.21018147798807, 22.92111832089155));
        expected.add(p(-138.2327652861215, 16.873847485531044));
        expected.add(p(-134.15988742700256, 68.75274372302722));
        expected.add(p(-128.10461505245598, 61.91729293652652));
        expected.add(p(-133.73010415738727, 69.13433526610211));
        expected.add(p(-127.29892564959277, 61.883896154126276));
        expected.add(p(-133.68096275165215, 69.76545581333923));
        expected.add(p(-126.11968934180209, 76.93473355906319));
        expected.add(p(-133.6110360254397, 70.58879114353032));
        expected.add(p(-133.67447370616813, 69.77103689390044));
        expected.add(p(-127.0132721340341, 77.01265959668525));
        expected.add(p(-134.01704037979877, 71.0673291782393));
        expected.add(p(-124.01757244530397, 116.28752197217447));
        expected.add(p(-130.02277642248922, 123.25398031420269));
        expected.add(p(-28.675218292229594, 113.62688621736781));
        expected.add(p(-26.111661101320323, 113.45990075074631));
        expected.add(p(-8.642849957853208, 112.13514957270836));
        expected.add(p(-11.997447653311397, 67.48342632631308));
        expected.add(p(-29.349067610915156, 68.67457959412893));
        expected.add(p(-49.63779166441311, 70.3666856096463));
        expected.add(p(-46.20262502860923, 115.1297556295956));
        expected.add(p(-136.64492272262612, 122.14315157976579));
        expected.add(p(-140.09473825664855, 77.22417313770343));
        expected.add(p(-178.54077167057517, 79.99611448486313));
        expected.add(p(-185.51081084612642, 73.96031532211765));
        expected.add(p(-161.072298123273, 72.23066626802496));
        expected.add(p(-134.11957974022172, 70.27493124208131));
        expected.add(p(-175.32533850831774, 124.92624766139598));
        expected.add(p(-161.5334008214043, 65.75792438138386));
        expected.add(p(-141.1055122347056, 64.26620054748251));
        expected.add(p(-144.59927446344116, 19.113912924760257));
        expected.add(p(-182.70838321803458, 21.908089728798558));
        expected.add(p(-179.48562560660088, 67.00473863516775));

        vd.debug(polygon);

        SkeletonOutput sk = Skeleton.skeleton(polygon, Arrays.asList(hole1, hole2, hole3, hole4, hole5));

        writeExpectedOutput(polygon, sk);

        visualizeResults(polygon, sk);

        validate(polygon, sk);

        assertExpectedPoints(expected, getFacePoints(sk));
    }

    @Test
    public void skeleton_5() {

        vd.clear();

        List<Vector2dc> polygon = new ArrayList<Vector2dc>();
        polygon.add(new Vector2d(0.0, 0.0));
        polygon.add(new Vector2d(-0.6958026548881477, -9.161478178076948));
        polygon.add(new Vector2d(-9.338404052373633, -8.571492793979743));
        polygon.add(new Vector2d(-17.70268438790386, -7.447180852769691));
        polygon.add(new Vector2d(-17.424363325929452, -3.372939648486792));
        polygon.add(new Vector2d(-16.142621593290144, 9.773744233129612));
        polygon.add(new Vector2d(-12.810093088301556, 54.43485287683303));
        polygon.add(new Vector2d(-32.90780345674236, 55.90426731787754));
        polygon.add(new Vector2d(-50.625136321556965, 57.284626693320064));
        polygon.add(new Vector2d(-49.63636412778366, 70.36466192934482));
        polygon.add(new Vector2d(-29.34822355908187, 68.67260457721952));
        polygon.add(new Vector2d(-11.997102617897646, 67.4814855658616));
        polygon.add(new Vector2d(-8.642601397542933, 112.13192467030846));
        polygon.add(new Vector2d(-7.851583642466842, 125.31227725183234));
        polygon.add(new Vector2d(-7.492695957349284, 130.04340803855604));
        polygon.add(new Vector2d(9.60940087585437, 128.6518985681851));
        polygon.add(new Vector2d(9.235864713749464, 123.92076896347811));
        polygon.add(new Vector2d(8.181174373724609, 110.5289109392406));
        polygon.add(new Vector2d(4.255382552472042, 110.85174005752916));
        polygon.add(new Vector2d(0.9448267629261693, 67.14752700944396));
        polygon.add(new Vector2d(1.8090869026900336, 67.01394359300451));
        polygon.add(new Vector2d(1.7578172333683781, 66.123387566131));
        polygon.add(new Vector2d(1.567387033088238, 66.15678341380686));
        polygon.add(new Vector2d(0.6591814624963845, 53.8559927457046));
        polygon.add(new Vector2d(0.8935570936236559, 53.87825659819807));
        polygon.add(new Vector2d(0.7690450395950705, 53.05449420912991));
        polygon.add(new Vector2d(-0.11718781561150848, 53.08788997837232));
        polygon.add(new Vector2d(-3.2446376433134674, 9.039043047081556));
        polygon.add(new Vector2d(-0.16845748485657452, 8.816406342048168));
        polygon.add(new Vector2d(-0.14648476947130007, 8.994515704236598));
        polygon.add(new Vector2d(0.6811541779199501, 8.894329185746045));
        polygon.add(new Vector2d(0.6298845085982947, 8.014914280523122));
        polygon.add(new Vector2d(0.49804821611433425, 8.026046113323751));

        List<Vector2dc> expected = new ArrayList<Vector2dc>(polygon);
        expected.add(p(0.22497519945185107, 8.482049310367099));
        expected.add(p(0.228030279440679, 8.51571861719398));
        expected.add(p(0.1539838067115609, 8.424052798868134));
        expected.add(p(0.15402029409151105, 8.424446729364753));
        expected.add(p(-8.46470046215179, 0.8909679724959663));
        expected.add(p(-10.232090839434656, 2.9863950680172815));
        expected.add(p(-6.0328581287001075, 59.683236018902456));
        expected.add(p(-9.707017583147685, 9.219988757292096));
        expected.add(p(-5.638142363831541, 60.035994979870125));
        expected.add(p(0.4412980045808891, 53.45034350926698));
        expected.add(p(0.2568122667896132, 53.445080273838485));
        expected.add(p(-5.667182887598463, 60.74743072530452));
        expected.add(p(-5.591816917235063, 60.66011175458134));
        expected.add(p(1.166988791907558, 66.67083534361724));
        expected.add(p(-5.628537161719527, 61.26630072622317));
        expected.add(p(1.3478704650397642, 66.64099894032651));
        expected.add(p(-5.951801518399619, 61.673302877522865));
        expected.add(p(-1.7573361753414014, 117.89581731960725));
        expected.add(p(-2.1782246133383856, 111.69514191374755));
        expected.add(p(0.35384514304874415, 119.72666090736905));
        expected.add(p(0.43073023369972396, 120.83484356072353));
        expected.add(p(4.492881435278868, 124.29477438423137));
        expected.add(p(-3.1800990745474937, 124.99493613135351));
        expected.add(p(-5.952986384098678, 60.50107151272664));
        expected.add(p(-29.840711971214105, 62.194242804209985));
        expected.add(p(-43.61111816270654, 63.298712425414124));
        expected.add(p(-32.41761135191654, 62.395898981650134));
        expected.add(p(-8.547206138620584, -0.08503012741680582));
        expected.add(p(-8.489043344467849, 0.5857274258099088));
        expected.add(p(-13.145027272273616, -3.7276383085200995));
        expected.add(p(-8.486848592193729, -0.15956404547695954));

        vd.debug(polygon);

        SkeletonOutput sk = Skeleton.skeleton(polygon, null);

        writeExpectedOutput(polygon, sk);

        visualizeResults(polygon, sk);

        validate(polygon, sk);

        assertExpectedPoints(expected, getFacePoints(sk));
    }

    @Test
    public void skeleton_6() {

        vd.clear();

        List<Vector2dc> polygon = new ArrayList<Vector2dc>();
        polygon.add(new Vector2d(0.0, 0.0));
        polygon.add(new Vector2d(12.897989139805986, -1.2801851278900926));
        polygon.add(new Vector2d(9.587432018184284, -44.984415761338475));
        polygon.add(new Vector2d(10.45169250570246, -45.11799923152818));
        polygon.add(new Vector2d(10.40042281575131, -46.008555616736736));
        polygon.add(new Vector2d(10.209992538847342, -45.97515975562331));
        polygon.add(new Vector2d(9.301786602818757, -58.27595537322258));
        polygon.add(new Vector2d(9.536162328252283, -58.25369151177075));
        polygon.add(new Vector2d(9.4116502241235, -59.077454232298045));
        polygon.add(new Vector2d(8.525417012321396, -59.044058449618106));
        polygon.add(new Vector2d(5.397965926220366, -103.09292310494317));
        polygon.add(new Vector2d(-7.500023213547323, -102.35822162327173));
        polygon.add(new Vector2d(-4.167493367641692, -57.69709500917689));
        polygon.add(new Vector2d(-41.982551816621694, -54.84732004601991));
        polygon.add(new Vector2d(-40.99377922499388, -41.76727954695175));
        polygon.add(new Vector2d(-3.3545025701129614, -44.650457070545166));

        List<Vector2dc> expected = new ArrayList<Vector2dc>(polygon);
        expected.add(p(-34.95236405380182, -48.803894914360306));
        expected.add(p(2.974315495160843, -51.39934012787623));
        expected.add(p(2.668507270977346, -51.66234808659685));
        expected.add(p(3.0496714771194053, -51.486845032739296));
        expected.add(p(3.004460242622362, -52.09595065239559));
        expected.add(p(-0.5826270913313756, -96.29477300440011));
        expected.add(p(2.609744318931162, -52.44870975530364));
        expected.add(p(9.083903057233032, -58.68160477288223));
        expected.add(p(8.899417245209744, -58.68686801042851));
        expected.add(p(9.809594136557513, -45.461107618972676));
        expected.add(p(3.0140654485992524, -50.86564441100163));
        expected.add(p(9.990475882471465, -45.490944034268786));
        expected.add(p(2.6908009618465503, -50.458642095935566));
        expected.add(p(5.961683861298499, -7.100422025402652));

        vd.debug(polygon);

        SkeletonOutput sk = Skeleton.skeleton(polygon, null);

        writeExpectedOutput(polygon, sk);

        visualizeResults(polygon, sk);

        validate(polygon, sk);

        assertExpectedPoints(expected, getFacePoints(sk));
    }

    @Test
    public void skeleton_7() {

        vd.clear();

        List<Vector2dc> polygon = new ArrayList<Vector2dc>();
        polygon.add(new Vector2d(0.0, 0.0));
        polygon.add(new Vector2d(3.3325122039160604, 44.660890183765225));
        polygon.add(new Vector2d(-16.76509985651811, 46.13029743716488));
        polygon.add(new Vector2d(-34.48234605694931, 47.51065006057564));
        polygon.add(new Vector2d(-33.493578699757975, 60.59062131557136));
        polygon.add(new Vector2d(-13.205537370552673, 58.89857224014935));
        polygon.add(new Vector2d(4.145498697574807, 57.70745905515341));
        polygon.add(new Vector2d(7.499983509377387, 102.35767975185216));
        polygon.add(new Vector2d(8.29099739518791, 115.53796786164443));
        polygon.add(new Vector2d(8.649883324805351, 120.26907550602861));
        polygon.add(new Vector2d(25.751896503054375, 118.87757284223014));
        polygon.add(new Vector2d(25.378362168102658, 114.14646637985689));
        polygon.add(new Vector2d(24.32367698709856, 100.75467386193046));
        polygon.add(new Vector2d(20.397904368867692, 101.07750140109947));
        polygon.add(new Vector2d(17.08736477291475, 57.37350213229498));
        polygon.add(new Vector2d(17.95162068514772, 57.23991936927919));
        polygon.add(new Vector2d(17.900351266611796, 56.349367698562986));
        polygon.add(new Vector2d(17.709921997821514, 56.38276338288297));
        polygon.add(new Vector2d(16.80172086971976, 44.08203288413275));
        polygon.add(new Vector2d(17.036095354397972, 44.104296627722526));
        polygon.add(new Vector2d(16.91158390942045, 43.2805382680904));
        polygon.add(new Vector2d(16.025355389224362, 43.31393387397731));
        polygon.add(new Vector2d(12.89792085945201, -0.7346975922551309));

        List<Vector2dc> expected = new ArrayList<Vector2dc>(polygon);
        expected.add(p(10.504427846768982, 50.262004888800014));
        expected.add(p(10.109714012653706, 49.90924765335381));
        expected.add(p(16.583838477581835, 43.6763856319295));
        expected.add(p(16.399353642203014, 43.67112242224613));
        expected.add(p(10.475387465053853, 50.973437154244564));
        expected.add(p(10.550753066764416, 50.88611861064228));
        expected.add(p(17.30952571518998, 56.89681279820688));
        expected.add(p(10.51403300189711, 51.49230461710896));
        expected.add(p(17.490406503539052, 56.866976540860996));
        expected.add(p(10.1907702264655, 51.89930477755651));
        expected.add(p(14.385215052284758, 108.12154420705387));
        expected.add(p(13.96432867306472, 101.92089913181533));
        expected.add(p(16.496386043825577, 109.95237883923916));
        expected.add(p(16.573270758394443, 111.06055607193703));
        expected.add(p(20.635402089919857, 114.52046997115824));
        expected.add(p(12.96245911252736, 115.2206282934406));
        expected.add(p(10.189585366562266, 50.72707914673348));
        expected.add(p(-13.698023373676454, 52.42024215606471));
        expected.add(p(-27.468362207188378, 53.52470637475261));
        expected.add(p(-16.274910149468344, 52.621897347103115));
        expected.add(p(6.917359502399792, 6.063416519745084));

        vd.debug(polygon);

        SkeletonOutput sk = Skeleton.skeleton(polygon, null);

        writeExpectedOutput(polygon, sk);

        visualizeResults(polygon, sk);

        validate(polygon, sk);

        assertExpectedPoints(expected, getFacePoints(sk));
    }

    @Test
    public void skeleton_8() {

        vd.clear();

        List<Vector2dc> polygon = new ArrayList<Vector2dc>();

        polygon.add(new Vector2d(-73.99355670177661, 130.28440014311866));
        polygon.add(new Vector2d(-7.85201868781445, 125.31922063111779));

        polygon.add(new Vector2d(-7.49311111722858, 130.0506135632292));
        polygon.add(new Vector2d(9.60993331941421, 128.65902699125058));
        polygon.add(new Vector2d(9.236376460189206, 123.92763524122142));
        polygon.add(new Vector2d(8.181627681237046, 110.5350351927255));
        polygon.add(new Vector2d(4.255618337310636, 110.85788219852725));

        polygon.add(new Vector2d(0.9448791144652091, 67.1512475606807));

        List<Vector2dc> expected = new ArrayList<Vector2dc>(polygon);

        expected.add(p(0.31749588484094743, 119.77603117590006));
        expected.add(p(-1.64604883910226, 118.10016025919867));
        expected.add(p(-1.946448792095577, 118.12378669561315));
        expected.add(p(0.3976890543497383, 120.81337575173194));
        expected.add(p(4.49313037957261, 124.30166138509239));
        expected.add(p(-15.548141335420517, 106.42963023576449));

        vd.debug(polygon);

        SkeletonOutput sk = Skeleton.skeleton(polygon, null);

        writeExpectedOutput(polygon, sk);

        visualizeResults(polygon, sk);

        validate(polygon, sk);

        assertExpectedPoints(expected, getFacePoints(sk));
    }

}
