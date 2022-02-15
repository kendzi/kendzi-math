package kendzi.math.geometry.skeleton.debug.display;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.util.Set;

import javax.vecmath.Point2d;
import javax.vecmath.Vector2d;

import kendzi.math.geometry.debug.DisplayObject;
import kendzi.math.geometry.debug.DisplayRectBounds;
import kendzi.math.geometry.skeleton.circular.CircularList;
import kendzi.math.geometry.skeleton.circular.Vertex;
import kendzi.swing.ui.panel.equation.EquationDisplay;

/**
 * 
 * @author Tomasz Kędziora (kendzi)
 */
public class DisplaySLav extends DisplayObject {

    private Set<CircularList<Vertex>> slav;

    private Color color;

    /**
     * @param LAV
     * @param pColor
     */
    public DisplaySLav(Set<CircularList<Vertex>> slav) {
        super();
        this.slav = slav;
        this.color = Color.ORANGE.darker().darker().darker();
    }

    @Override
    public void draw(Graphics2D g2d, EquationDisplay disp, boolean selected) {

        if (this.slav == null) {
            return;
        }

        g2d.setColor(color.darker());

        for (CircularList<Vertex> lav : slav) {

            drawLav(g2d, disp, selected, lav);
        }

    }

    private void drawLav(Graphics2D g2d, EquationDisplay disp, boolean selected, CircularList<Vertex> lav) {
        int size = lav.size();
        double[] xx = new double[size];
        double[] yy = new double[size];
        int i = 0;

        for (Vertex vertex : lav) {
            xx[i] = disp.xPositionToPixel(vertex.getPoint().x);
            yy[i] = disp.yPositionToPixel(vertex.getPoint().y);
            i++;
        }

        g2d.setColor(Color.BLUE.brighter());
        i = 0;
        for (Vertex vertex : lav) {
            Vector2d vector = vertex.getBisector().U;

            double x = xx[i];
            double y = yy[i];
            double vx = disp.xPositionToPixel(vertex.getPoint().x + vector.x);
            double vy = disp.yPositionToPixel(vertex.getPoint().y + vector.y);

            double dx = vx - x;
            double dy = vy - y;
            double norm = 1.0 / Math.sqrt(dx * dx + dy * dy);

            vx = dx * norm * 80 + x;
            vy = dy * norm * 80 + y;

            g2d.drawLine((int) x, (int) y, (int) vx, (int) vy);
            i++;
        }

        Stroke stroke = g2d.getStroke();
        g2d.setStroke(new BasicStroke(3));
        if (selected) {
            g2d.setColor(Color.GREEN.brighter());
        } else {
            g2d.setColor(color);
        }

        drawPolygonWithOffset(g2d, xx, yy, 12);

        g2d.setStroke(stroke);

        g2d.setColor(color);
        for (Vertex v2 : lav) {

            drawPoint(v2.getPoint(), selected, g2d, disp);
        }
    }

    private void drawPolygonWithOffset(Graphics2D g2d, double[] xx, double[] yy, double o) {
        int s = xx.length;

        double[] oxx = new double[s];
        double[] oyy = new double[s];

        double[] oxx2 = new double[s];
        double[] oyy2 = new double[s];

        for (int i = 0; i < s; i++) {
            double x1 = xx[i];
            double x2 = xx[(i + 1) % s];
            double x3 = xx[(i + 2) % s];

            double y1 = yy[i];
            double y2 = yy[(i + 1) % s];
            double y3 = yy[(i + 2) % s];

            double xo1 = -(y2 - y1);
            double xo2 = -(y3 - y2);
            double yo1 = x2 - x1;
            double yo2 = x3 - x2;

            double o1norm = 1.0 / Math.sqrt(xo1 * xo1 + yo1 * yo1);
            double o2norm = 1.0 / Math.sqrt(xo2 * xo2 + yo2 * yo2);

            double ox = -(xo1 * o1norm + xo2 * o2norm) / 2;
            double oy = -(yo1 * o1norm + yo2 * o2norm) / 2;

            double norm = 1.0 / Math.sqrt(ox * ox + oy * oy);
            ox = ox * norm * o;
            oy = oy * norm * o;

            oxx[(i + 1) % s] = ox + x2;
            oyy[(i + 1) % s] = oy + y2;

            oxx2[(i + 1) % s] = ox + ox + x2;
            oyy2[(i + 1) % s] = oy + oy + y2;

        }

        for (int i = 0; i < s; i++) {
            int x1 = (int) oxx[i];
            int x2 = (int) oxx[(i + 1) % s];

            int y1 = (int) oyy[i];
            int y2 = (int) oyy[(i + 1) % s];

            g2d.drawLine(x1, y1, x2, y2);
        }

        for (int i = 0; i < s; i++) {
            int x1 = (int) oxx[i];
            int x2 = (int) oxx2[i];

            int y1 = (int) oyy[i];
            int y2 = (int) oyy2[i];

            g2d.drawLine(x1, y1, x2, y2);
        }

    }

    /**
     * @param p
     * @param selected
     * @param g2d
     * @param disp
     */
    public void drawPoint(Point2d p, boolean selected, Graphics2D g2d, EquationDisplay disp) {
        int x = (int) disp.xPositionToPixel(p.x);
        int y = (int) disp.yPositionToPixel(p.y);

        if (selected) {
            g2d.setColor(Color.GREEN.brighter());
            g2d.fillOval(-11 + x, -11 + y, 22, 22);
        }
        g2d.setColor(color);
        g2d.fillOval(-10 + x, -10 + y, 20, 20);
    }

    @Override
    public Object drawObject() {
        return this.slav;
    }

    @Override
    public DisplayRectBounds getBounds() {
        return null;
    }
}
