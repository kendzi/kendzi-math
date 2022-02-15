package kendzi.math.geometry.skeleton.debug.display;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.List;

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
public class DisplayEventNames extends DisplayObject {

    private List<SkeletonEvent> events;

    public final static Color EDGE_COLOR = Color.PINK;
    public final static Color SPLIT_COLOR = new Color(127, 0, 255);

    public DisplayEventNames(List<SkeletonEvent> events) {
        super();
        this.events = events;
    }

    @Override
    public void draw(Graphics2D g2d, EquationDisplay disp, boolean selected) {

        if (this.events == null) {
            return;
        }

        int count = 0;
        for (SkeletonEvent e : this.events) {
            count++;
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

            g2d.setColor(Color.BLACK.brighter().brighter());

            g2d.drawString("" + count, x + 12, y);
        }

    }

    @Override
    public Object drawObject() {
        return this.events;
    }

    @Override
    public DisplayRectBounds getBounds() {
        DisplayRectBounds b = new DisplayRectBounds();
        for (SkeletonEvent e : this.events) {

            Vector2dc p = e.v;

            b.addPoint(p);
        }
        return b.toBount();
    }
}
