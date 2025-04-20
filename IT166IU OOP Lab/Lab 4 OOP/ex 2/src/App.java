import javax.swing.JFrame;
public class App {
    public static void main(String[] args) {
        DrawPanel panel = new DrawPanel();
        JFrame application = new JFrame();
        application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        application.add(panel); // add the panel to the frame
        application.setSize(500, 500); // set the size of the frame
        application.setVisible(true); // make the frame visible
    }
}
