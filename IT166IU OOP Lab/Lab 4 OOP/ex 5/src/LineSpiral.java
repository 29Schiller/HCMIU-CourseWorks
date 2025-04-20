import java.awt.Graphics;
import javax.swing.JPanel;
import java.awt.Color;

public class LineSpiral extends JPanel{
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(Color.red);
        int oldX = getWidth() / 2; // starting X
        int oldY = getHeight() / 2; // starting Y
        int distance = 0; // distance to move        
        // draws individual lines in to form a spiral
        for ( int i = 0; i < 20; i++ ){
            int newX = oldX; // new X position
            int newY = oldY; // new Y position           
            if ( i % 2 == 0 ) // increment the distance every other leg
                distance += 40;
            // set the endpoint depending on the desired direction
            switch ( i % 4 ){
                case 0:
                    newY += distance;
                    break;
                case 1:
                    newX -= distance;
                    break;
                case 2:
                    newY -= distance;
                    break;
                case 3:
                    newX += distance;
                    break;
            }
            g.drawLine(oldX, oldY, newX, newY );
            oldX = newX;
            oldY = newY;
        }
    }
}