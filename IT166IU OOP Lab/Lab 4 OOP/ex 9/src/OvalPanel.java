import javax.swing.*;
import java.awt.*;

public class OvalPanel extends JPanel {
    public OvalPanel() {
        setPreferredSize(new Dimension(400, 400));
        setBackground(Color.WHITE);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int x = getWidth() / 2;
        int y = getHeight() / 2;
        double radius = Math.min(x, y) * 0.6;
        double angle = 0;
        for (int i = 0; i < 5; i++) {
            int x2 = x + (int) (radius * Math.cos(angle));
            int y2 = y + (int) (radius * Math.sin(angle));
            if (i == 0) {
                g2d.drawLine(x, y, x2, y2);
            } else {
                g2d.drawLine(x, y, x2, y2);
            }
            angle += Math.PI / 2;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Star Panel");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new OvalPanel(), BorderLayout.CENTER);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}