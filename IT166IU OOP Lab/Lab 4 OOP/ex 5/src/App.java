import javax.swing.JFrame;
public class App {
    public static void main(String[] args) {
        LineSpiral line = new LineSpiral();
        ArcSpiral arc = new ArcSpiral();

        JFrame application1 = new JFrame();
        application1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        application1.add(line); // add the panel to the frame
        application1.setSize(500, 500); // set the size of the frame
        application1.setVisible(true); // make the frame visible

        JFrame application2 = new JFrame();
        application2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        application2.add(arc); // add the panel to the frame
        application2.setSize(500, 500); // set the size of the frame
        application2.setVisible(true); // make the frame visible
    }
}
