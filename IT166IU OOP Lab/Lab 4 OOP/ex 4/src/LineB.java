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
            g.drawLine(0, count * heightStep, (count + 1) * widthStep, height);
            g.drawLine( width, count * heightStep, width - (count + 1) * widthStep, height); 
            g.drawLine( width, height - count * heightStep, width - ( count + 1 ) * widthStep, 0 );
            g.drawLine( 0, height - count * heightStep, ( count + 1 ) * widthStep, 0 );

            count++;
        }
    }
}