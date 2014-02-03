package kendzi.math.geometry.skeleton.debug.display;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.List;

import kendzi.math.geometry.debug.DisplayObject;
import kendzi.math.geometry.debug.DisplayRectBounds;
import kendzi.math.geometry.debug.DrawUtil;
import kendzi.math.geometry.skeleton.circular.Edge;
import kendzi.math.geometry.skeleton.events.EdgeEvent;
import kendzi.math.geometry.skeleton.events.MultiEdgeEvent;
import kendzi.math.geometry.skeleton.events.MultiSplitEvent;
import kendzi.math.geometry.skeleton.events.PickEvent;
import kendzi.math.geometry.skeleton.events.SkeletonEvent;
import kendzi.math.geometry.skeleton.events.SplitEvent;
import kendzi.math.geometry.skeleton.events.chains.Chain;
import kendzi.math.geometry.skeleton.events.chains.EdgeChain;
import kendzi.math.geometry.skeleton.events.chains.SingleEdgeChain;
import kendzi.math.geometry.skeleton.events.chains.SplitChain;
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

        DrawUtil.drawPoint(intersect.v, selected, g2d, disp);

        if (intersect instanceof SplitEvent) {
            g2d.setColor(DisplayEventQueue.SPLIT_COLOR);
        } else {
            g2d.setColor(DisplayEventQueue.EDGE_COLOR);
        }

        if (intersect instanceof MultiSplitEvent) {

            MultiSplitEvent event = (MultiSplitEvent) this.intersect;

            drawChains(g2d, disp, selected, event.getChains());

        } else if (intersect instanceof MultiEdgeEvent) {

            MultiEdgeEvent event = (MultiEdgeEvent) this.intersect;

            drawChain(g2d, disp, selected, event.getChain());

        } else if (intersect instanceof PickEvent) {

            PickEvent event = (PickEvent) this.intersect;

            drawChain(g2d, disp, selected, event.getChain());

        } else if (intersect instanceof SplitEvent) {
            SplitEvent split = (SplitEvent) this.intersect;

            drawSplitEvent(g2d, disp, selected, split);

        } else if (intersect instanceof EdgeEvent) {
            EdgeEvent split = (EdgeEvent) this.intersect;

            drawEdgeEvent(g2d, disp, selected, split);
        }

    }

    private void drawEdgeEvent(Graphics2D g2d, EquationDisplay disp, boolean selected, EdgeEvent split) {
        if (split.getPreviousVertex().isProcessed()) {
            g2d.setColor(Color.GRAY.brighter());
        } else {
            g2d.setColor(Color.GRAY.darker());
        }

        DrawUtil.drawPoint(split.getPreviousVertex().getPoint(), selected, g2d, disp);

        DrawUtil.drawLine(intersect.v, split.getPreviousVertex().getPoint(), selected, g2d, disp);

        if (split.getNextVertex().isProcessed()) {
            g2d.setColor(Color.GRAY.brighter());
        } else {
            g2d.setColor(Color.GRAY.darker());
        }

        DrawUtil.drawPoint(split.getNextVertex().getPoint(), selected, g2d, disp);

        DrawUtil.drawLine(intersect.v, split.getNextVertex().getPoint(), selected, g2d, disp);
    }

    private void drawChains(Graphics2D g2d, EquationDisplay disp, boolean selected, List<Chain> chains) {
        for (Chain chain : chains) {
            drawChain(g2d, disp, selected, chain);
        }
    }

    private void drawChain(Graphics2D g2d, EquationDisplay disp, boolean selected, Chain chain) {
        if (chain instanceof SplitChain) {
            SplitChain splitChain = (SplitChain) chain;

            drawSplitEvent(g2d, disp, selected, splitChain.getSplitEvent());

        } else if (chain instanceof EdgeChain) {
            EdgeChain edgeChain = (EdgeChain) chain;

            for (EdgeEvent edgeEvent : edgeChain.getEdgeList()) {
                drawEdgeEvent(g2d, disp, selected, edgeEvent);
            }
        } else if (chain instanceof SingleEdgeChain) {
            SingleEdgeChain singleEdgeChain = (SingleEdgeChain) chain;

            drawEdge(g2d, disp, selected, singleEdgeChain.getPreviousEdge());

        }
    }

    private void drawEdge(Graphics2D g2d, EquationDisplay disp, boolean selected, Edge previousEdge) {
        // if (split.isObsolete()) {
        // g2d.setColor(Color.GRAY.brighter());
        // } else {
        // g2d.setColor(Color.GRAY.darker());
        // }

        DrawUtil.drawLine(previousEdge.getBegin(), previousEdge.getEnd(), selected, g2d, disp);

    }

    private void drawSplitEvent(Graphics2D g2d, EquationDisplay disp, boolean selected, SplitEvent split) {
        if (split.isObsolete()) {
            g2d.setColor(Color.GRAY.brighter());
        } else {
            g2d.setColor(Color.GRAY.darker());
        }

        DrawUtil.drawPoint(split.getParent().getPoint(), selected, g2d, disp);

        DrawUtil.drawLine(intersect.v, split.getParent().getPoint(), selected, g2d, disp);

        g2d.setColor(Color.GRAY);
        if (split.oppositeEdge != null) {
            DrawUtil.drawLine(split.getOppositeEdge().getBegin(), split.getOppositeEdge().getEnd(), selected, g2d, disp);
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
