import java.awt.Graphics;
import javax.swing.JPanel;
import java.awt.Color;

public class ArcSpiral extends JPanel{
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(Color.green);
        int x = getWidth() / 2; // starting X
        int y = getHeight() / 2; // starting Y
        
        int radiusStep = 20; // distance the radius changes
        int diameter = 0; // diameter of the arc
        int arc = 180; // amount and direction of arc to sweep
        
        // draws individual lines in to form a spiral
        for ( int i = 0; i < 20; i++ ){
            if (i % 2 ==1) {
                x -= 2 * radiusStep;
            }
            y -= radiusStep;
            diameter += 2 * radiusStep;
            g.drawArc( x, y, diameter, diameter, 0, arc );
            arc = -arc;
        }
    }
}