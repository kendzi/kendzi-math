package kendzi.math.geometry.skeleton.debug.display;

import java.awt.Color;
import java.awt.Graphics2D;

import javax.vecmath.Point2d;

import kendzi.math.geometry.debug.DisplayObject;
import kendzi.math.geometry.debug.DisplayRectBounds;
import kendzi.math.geometry.debug.DrawUtil;
import kendzi.math.geometry.skeleton.path.FaceNode;
import kendzi.math.geometry.skeleton.path.FaceQueue;
import kendzi.swing.ui.panel.equation.EquationDisplay;

/**
 * 
 * @author Tomasz Kędziora (kendzi)
 */
public class DisplayFaceNode extends DisplayObject {

    private FaceQueue lav;

    private Color color;

    /**
     * @param LAV
     * @param pColor
     */
    public DisplayFaceNode(FaceQueue f, Color pColor) {
        super();
        this.lav = f;
        this.color = pColor;
    }

    /**
     * @param LAV
     * @param pColor
     */
    public DisplayFaceNode(FaceNode node, Color pColor) {
        super();
        this.lav = (FaceQueue) node.list();
        this.color = pColor;
    }

    @Override
    public void draw(Graphics2D g2d, EquationDisplay disp, boolean selected) {

        if (this.lav == null) {
            return;
        }

        g2d.setColor(color.darker());

        FaceNode v1 = null;
        for (FaceNode v2 : lav) {
            if (v1 == null) {
                v1 = v2;
                continue;
            }

            Point2d p1 = v1.getVertex().getPoint();
            Point2d p2 = v2.getVertex().getPoint();

            g2d.setColor(color);
            DrawUtil.drawLine(p1, p2, selected, g2d, disp);

            v1 = v2;
        }

        for (FaceNode v2 : lav) {

            g2d.setColor(color);

            DrawUtil.drawPoint(v2.getVertex().getPoint(), selected, g2d, disp);
        }
    }

    @Override
    public Object drawObject() {
        return this.lav;
    }

    @Override
    public DisplayRectBounds getBounds() {
        return null;
    }
}
