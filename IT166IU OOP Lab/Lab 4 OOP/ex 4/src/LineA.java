import java.awt.Graphics;
import javax.swing.JPanel;

public class LineA extends JPanel{
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        int increment = 15; // đường

        int width = getWidth(); // total width
        int height = getHeight(); // total height

        int widthStep = width / increment; // Ox


        int heightStep = height / increment;// Oy

        int count = 0;
        while(count < increment){
            g.drawLine(0, count * heightStep,// điểm đầu
             (count + 1) * widthStep, height // điểm cuối
             );
            
            count++;
        }
    }
}