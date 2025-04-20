import java.awt.Graphics;
import javax.swing.JPanel;
import java.awt.Color;

public class CaptainShield extends JPanel {
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        
        // Set background's color to Black
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        //Draw The Shield with 4 ConcenTric Circles
        // The first Red Circle
        g.setColor(Color.RED);
        g.fillOval(0,0,getWidth(),getHeight());

        // The second white circle
        g.setColor(Color.WHITE);
        g.fillOval(50,50, (centerX - 50)* 2, (centerY-50) * 2);

        // The third red circle
        g.setColor(Color.RED);
        g.fillOval(100,100, (centerX - 100)* 2, (centerY-100) * 2);

        // The last blue circle
        g.setColor(Color.BLUE);
        g.fillOval(150,150, (centerX - 150)* 2, (centerY-150) * 2);

        // Draw the five-pointed star in the center of the panel
        int [] x = {centerX - 50, centerX, centerX + 50, centerX + 25, centerX - 25};
        int [] y = {centerY - 75, centerY + 25, centerY - 75, centerY + 50, centerY + 50};
        g.setColor(Color.WHITE);
        g.fillPolygon(x, y, 5);
    }
}