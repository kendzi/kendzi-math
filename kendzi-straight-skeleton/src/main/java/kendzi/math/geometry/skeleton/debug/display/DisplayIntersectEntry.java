package kendzi.math.geometry.skeleton.debug.display;

import java.awt.Color;
import java.awt.Graphics2D;

import kendzi.math.geometry.debug.DisplayObject;
import kendzi.math.geometry.debug.DisplayRectBounds;
import kendzi.math.geometry.debug.DrawUtil;
import kendzi.math.geometry.skeleton.events.EdgeEvent;
import kendzi.math.geometry.skeleton.events.SkeletonEvent;
import kendzi.math.geometry.skeleton.events.SplitEvent;
import kendzi.swing.ui.panel.equation.EquationDisplay;

/**
 * 
 * @author Tomasz Kędziora (kendzi)
 */
public class DisplayIntersectEntry extends DisplayObject {

    private SkeletonEvent intersect;

    private Color color;

    public DisplayIntersectEntry(SkeletonEvent f, Color pColor) {
        super();
        this.intersect = f;
        this.color = pColor;
    }

    @Override
    public void draw(Graphics2D g2d, EquationDisplay disp, boolean selected) {

        if (this.intersect == null) {
            return;
        }

        // Point2d last = this.points.get(this.points.size() - 1);

        if (intersect instanceof SplitEvent) {
            g2d.setColor(DisplayEventQueue.SPLIT_COLOR);
        } else {
            g2d.setColor(DisplayEventQueue.EDGE_COLOR);
        }

        DrawUtil.drawPoint(intersect.v, selected, g2d, disp);

        if (intersect instanceof SplitEvent) {
            SplitEvent split = (SplitEvent) this.intersect;

            if (split.isObsolete()) {
                g2d.setColor(Color.GRAY.brighter());
            } else {
                g2d.setColor(Color.GRAY.darker());
            }

            DrawUtil.drawPoint(split.getParent().point, selected, g2d, disp);

            DrawUtil.drawLine(intersect.v, split.getParent().point, selected, g2d, disp);

            g2d.setColor(Color.GRAY);
            DrawUtil.drawLine(split.oppositeEdge.getBegin(), split.oppositeEdge.getEnd(), selected, g2d, disp);
        }

        if (intersect instanceof EdgeEvent) {
            EdgeEvent split = (EdgeEvent) this.intersect;

            if (split.Va.processed) {
                g2d.setColor(Color.GRAY.brighter());
            } else {
                g2d.setColor(Color.GRAY.darker());
            }

            DrawUtil.drawPoint(split.Va.point, selected, g2d, disp);

            DrawUtil.drawLine(intersect.v, split.Va.point, selected, g2d, disp);

            if (split.Vb.processed) {
                g2d.setColor(Color.GRAY.brighter());
            } else {
                g2d.setColor(Color.GRAY.darker());
            }

            DrawUtil.drawPoint(split.Vb.point, selected, g2d, disp);

            DrawUtil.drawLine(intersect.v, split.Vb.point, selected, g2d, disp);
        }

    }

    @Override
    public Object drawObject() {
        return this.intersect;
    }

    @Override
    public DisplayRectBounds getBounds() {
        return null;
    }
}
