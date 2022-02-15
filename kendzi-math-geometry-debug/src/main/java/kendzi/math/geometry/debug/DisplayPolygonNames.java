package kendzi.math.geometry.debug;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.List;

import org.joml.Vector2dc;

import kendzi.swing.ui.panel.equation.EquationDisplay;

/**
 * 
 * @author Tomasz Kędziora (kendzi)
 */
public class DisplayPolygonNames extends DisplayObject {

    private List<? extends Vector2dc> polygon;

    /**
     * @param polygon
     */
    public DisplayPolygonNames(List<? extends Vector2dc> polygon) {
        super();
        this.polygon = polygon;
    }

    @Override
    public void draw(Graphics2D g2d, EquationDisplay disp, boolean selected) {

        if (this.polygon == null || this.polygon.isEmpty()) {
            return;
        }

        for (Vector2dc p : this.polygon) {

            int x2 = (int) disp.xPositionToPixel(p.x());
            int y2 = (int) disp.yPositionToPixel(p.y());

            if (selected) {
                g2d.setColor(Color.BLACK);

            }
            g2d.setColor(Color.BLACK.brighter().brighter());

            g2d.drawString(p.toString(), x2, y2);
        }
    }

    @Override
    public Object drawObject() {
        return this;
    }

    @Override
    public DisplayRectBounds getBounds() {
        DisplayRectBounds b = new DisplayRectBounds();
        b.addList(polygon);
        return b.toBount();
    }
}
