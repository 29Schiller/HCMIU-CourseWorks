import java.awt.Graphics;
import javax.swing.JPanel;
import java.awt.Color;
import java .util.Random;

public class RandomShapes extends JPanel {
    private Random randomNumbers = new Random();
    public void paintComponent( Graphics g ){
        int maxWidth = getWidth(); 
        int maxHeight = getHeight();
        for ( int i = 0; i < 10; i++ ){
            Color color = new Color( randomNumbers.nextInt( 256 ),randomNumbers.nextInt( 256 ),randomNumbers.nextInt( 256 ));
            g.setColor(color);

            switch ( randomNumbers.nextInt( 2 ) ){
                case 0: // draw a random rectangle
                    g.fillRect( randomNumbers.nextInt( maxWidth ) + 1,
                    randomNumbers.nextInt( maxHeight ) + 1,
                    randomNumbers.nextInt( maxWidth / 2 ) + 1,
                    randomNumbers.nextInt( maxHeight / 2 ) + 1 );
                break;
                case 1: // draw a random oval
                    g.fillOval( randomNumbers.nextInt( maxWidth ) + 1,
                    randomNumbers.nextInt( maxHeight ) + 1,
                    randomNumbers.nextInt( maxWidth / 2 ) + 1,
                    randomNumbers.nextInt( maxHeight / 2 ) + 1 );
                break;
            }
        }
    }
    
}
