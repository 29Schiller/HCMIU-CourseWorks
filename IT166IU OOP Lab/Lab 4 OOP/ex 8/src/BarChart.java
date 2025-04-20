import java.awt.Graphics;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.Color;
import java .util.Random;

public class BarChart extends JPanel {
    private Random randomNumbers = new Random();
    public void paintComponent( Graphics g ){
        int maxHeight = getHeight();
        for ( int i = 0; i < 5; i++ ){
            String input = JOptionPane.showInputDialog("Enter the length of the bar " + (i+1));
            int length = Integer.parseInt(input);           
            Color color = new Color( randomNumbers.nextInt( 256 ),randomNumbers.nextInt( 256 ),randomNumbers.nextInt( 256 ));
            g.setColor(color);
            g.fillRect(0,(maxHeight/10)*(i+1),length,maxHeight/10);    
        }
    }
    
}
