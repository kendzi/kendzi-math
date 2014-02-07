/*
 * This software is provided "AS IS" without a warranty of any kind. You use it
 * on your own risk and responsibility!!! This file is shared under BSD v3
 * license. See readme.txt and BSD3 file for details.
 */

package kendzi.math.geometry.skeleton;

import java.awt.Graphics2D;

import kendzi.swing.ui.panel.equation.EquationDisplay;
import kendzi.swing.ui.panel.equation.EquationLayer;

public class SkeletonLayer extends EquationLayer {

    private SkeletonOutput output;

    public SkeletonLayer(SkeletonOutput output) {
        this.output = output;
    }

    @Override
    public void draw(Graphics2D g2d, EquationDisplay disp) {

        if (this.output == null) {
            return;
        }
    }
}
