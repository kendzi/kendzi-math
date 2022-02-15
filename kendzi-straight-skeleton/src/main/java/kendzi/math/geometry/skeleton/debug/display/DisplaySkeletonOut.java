package kendzi.math.geometry.skeleton.debug.display;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Stroke;

import org.joml.Vector2dc;

import kendzi.math.geometry.debug.DisplayObject;
import kendzi.math.geometry.debug.DisplayRectBounds;
import kendzi.math.geometry.polygon.PolygonList2d;
import kendzi.math.geometry.skeleton.EdgeOutput;
import kendzi.math.geometry.skeleton.SkeletonOutput;
import kendzi.swing.ui.panel.equation.EquationDisplay;

/**
 * 
 * @author Tomasz Kędziora (kendzi)
 */
public class DisplaySkeletonOut extends DisplayObject {

    private SkeletonOutput skeletonOut;

    /**
     * @param skeletonOut
     */
    public DisplaySkeletonOut(SkeletonOutput skeletonOut) {
        super();
        this.skeletonOut = skeletonOut;
    }

    @Override
    public void draw(Graphics2D g2d, EquationDisplay disp, boolean selected) {

        if (this.skeletonOut == null) {
            return;
        }

        for (EdgeOutput edgeOutput : this.skeletonOut.getEdgeOutputs()) {
            PolygonList2d list = edgeOutput.getPolygon();

            Polygon polygon = new Polygon();
            for (Vector2dc point : list.getPoints()) {
                int x = (int) disp.xPositionToPixel(point.x());
                int y = (int) disp.yPositionToPixel(point.y());

                polygon.addPoint(x, y);
            }

            g2d.setColor(Color.blue.brighter());
            g2d.fillPolygon(polygon);

            if (selected) {
                Stroke stroke = g2d.getStroke();
                g2d.setStroke(new BasicStroke(3));
                g2d.setColor(Color.GREEN.brighter());
                g2d.drawPolygon(polygon);
                g2d.setStroke(stroke);
            }
            g2d.setColor(Color.yellow.darker());
            g2d.drawPolygon(polygon);

        }
    }

    @Override
    public Object drawObject() {
        return skeletonOut;
    }

    @Override
    public DisplayRectBounds getBounds() {
        return null;
    }
}
