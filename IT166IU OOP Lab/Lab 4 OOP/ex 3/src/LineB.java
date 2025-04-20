import java.awt.Graphics;
import javax.swing.JPanel;

public class LineB extends JPanel{
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        int increment = 15;

        int width = getWidth(); // total width
        int height = getHeight(); // total height

        int widthStep = width / increment;
        int heightStep = height / increment;

        int count = 0;
        while(count < increment){
            g.drawLine(0, 0, count * widthStep, height - count * heightStep);
            g.drawLine( width, height, count * widthStep, height - count * heightStep );
            g.drawLine( 0, height, count * widthStep, count * heightStep );
            g.drawLine( width, 0, count * widthStep, count * heightStep );

            count++;
        }
    }
}