import java.awt.*;
import javax.swing.*;

class Main extends JPanel {
    public void paintComponent(Graphics g) {
        int [] x = {45, 55, 75, 55, 63, 43, 17, 31, 12, 35, 45};
        int [] y = {41, 65, 71, 83, 108, 88, 105, 78, 61, 63,41};
        g.drawPolygon(x, y, 10);
        g.setColor(Color.BLUE);
        g.fillPolygon(x, y, 10);
        //Polygon p = new Polygon();
        //g.fillPolygon(p);
    }
    public static void main( String args[] ) {
        JFrame frame = new JFrame("Fill Polygon");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBackground(Color.white);
        frame.setSize(500, 500);

        Main panel = new Main();

        frame.add(panel);
        frame.setVisible(true);      
    }
}   