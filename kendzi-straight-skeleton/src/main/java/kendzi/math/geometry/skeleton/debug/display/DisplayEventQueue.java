package kendzi.math.geometry.skeleton.debug.display;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.util.PriorityQueue;

import org.joml.Vector2dc;

import kendzi.math.geometry.debug.DisplayObject;
import kendzi.math.geometry.debug.DisplayRectBounds;
import kendzi.math.geometry.skeleton.events.SkeletonEvent;
import kendzi.math.geometry.skeleton.events.SplitEvent;
import kendzi.swing.ui.panel.equation.EquationDisplay;

/**
 * 
 * @author Tomasz Kędziora (kendzi)
 */
public class DisplayEventQueue extends DisplayObject {

    private PriorityQueue<SkeletonEvent> points;

    public static final Color EDGE_COLOR = Color.PINK;
    public static final Color SPLIT_COLOR = new Color(127, 0, 255);

    /**
     * @param polygon
     */
    public DisplayEventQueue(PriorityQueue<SkeletonEvent> polygon) {
        super();
        this.points = polygon;

    }

    @Override
    public void draw(Graphics2D g2d, EquationDisplay disp, boolean selected) {

        if (this.points == null) {
            return;
        }

        for (SkeletonEvent e : this.points) {

            Vector2dc p = e.v;

            int x = (int) disp.xPositionToPixel(p.x());
            int y = (int) disp.yPositionToPixel(p.y());

            if (selected) {
                g2d.setColor(Color.GREEN.brighter());
                g2d.fillOval(-11 + x, -11 + y, 22, 22);
            }

            if (e instanceof SplitEvent) {

                g2d.setColor(SPLIT_COLOR);
            } else {
                g2d.setColor(EDGE_COLOR);
            }
            g2d.fillOval(-10 + x, -10 + y, 20, 20);
        }
    }

    private void drawLine(Vector2dc current, Vector2dc previous, boolean selected, Graphics2D g2d, EquationDisplay disp) {

        int x1 = (int) disp.xPositionToPixel(previous.x());
        int y1 = (int) disp.yPositionToPixel(previous.y());
        int x2 = (int) disp.xPositionToPixel(current.x());
        int y2 = (int) disp.yPositionToPixel(current.y());

        if (selected) {
            Stroke stroke = g2d.getStroke();
            g2d.setStroke(new BasicStroke(3));
            g2d.setColor(Color.GREEN.brighter());
            g2d.drawLine(x1, y1, x2, y2); // thick
            g2d.setStroke(stroke);
        }
    }

    @Override
    public Object drawObject() {
        return this.points;
    }

    @Override
    public DisplayRectBounds getBounds() {
        DisplayRectBounds b = new DisplayRectBounds();
        for (SkeletonEvent e : this.points) {

            Vector2dc p = e.v;

            b.addPoint(p);
        }
        return b.toBount();
    }
}
