import java.awt.Graphics;
import javax.swing.JPanel;

public class Circle extends JPanel{
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        for (int i = 0; i < 12; i++) {
            int radius = 10 + i * 10;
            g.drawOval(centerX - radius, centerY - radius, radius * 2, radius * 2);
        }
    }
}