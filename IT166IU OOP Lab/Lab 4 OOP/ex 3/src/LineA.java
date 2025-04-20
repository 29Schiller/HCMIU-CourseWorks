import java.awt.Graphics;
import javax.swing.JPanel;

public class LineA extends JPanel{
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
            count++;
        }
    }
}