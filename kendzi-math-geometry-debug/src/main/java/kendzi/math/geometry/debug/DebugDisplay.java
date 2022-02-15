package kendzi.math.geometry.debug;

import javax.swing.JDialog;

import kendzi.swing.ui.panel.equation.MapComponent;

public class DebugDisplay {

    private static DebugDisplay debugDisplay = null;

    public static DebugDisplay getDebugDisplay() {
        if (debugDisplay == null) {
            debugDisplay = new DebugDisplay();
        }
        return debugDisplay;
    }

    private JDialog frame;
    private DebugLayer debugLayer;
    private MapComponent mc;

    public DebugDisplay() {

        this.debugLayer = new DebugLayer();
        createDebugFrame(this.debugLayer);
    }

    public JDialog getFrame() {
        return this.frame;
    }

    public DebugLayer getDebugLayer() {
        return this.debugLayer;
    }

    public MapComponent getMapComponent() {
        return mc;
    }

    private void createDebugFrame(final DebugLayer debugLayer) {

        mc = new MapComponent();
        mc.addLayer(debugLayer);

        DebugDisplay.this.frame = new JDialog();
        DebugDisplay.this.frame.add(mc);
        DebugDisplay.this.frame.pack();
        DebugDisplay.this.frame.setSize(600, 600);

        DebugDisplay.this.frame.setModal(false);
        DebugDisplay.this.frame.setVisible(true);
    }

    public void block() {
        this.getFrame().setVisible(false);
        this.getFrame().setModal(true);
        this.getFrame().setVisible(true);
    }

}
