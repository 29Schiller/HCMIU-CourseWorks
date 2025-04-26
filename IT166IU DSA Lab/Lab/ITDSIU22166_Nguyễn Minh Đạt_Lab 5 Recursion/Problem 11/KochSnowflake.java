import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;

public class KochSnowflake extends JPanel {
    private final int level;

    public KochSnowflake(int level) {
        this.level = level;
        setBackground(Color.WHITE);
    }

    public void drawKochCurve(Graphics2D g, int level, Point2D.Double p1, Point2D.Double p2) {
        if (level == 0) {
            g.drawLine((int)p1.x, (int)p1.y, (int)p2.x, (int)p2.y);
        } else {
            double dx = (p2.x - p1.x) / 3;
            double dy = (p2.y - p1.y) / 3;

            Point2D.Double pA = new Point2D.Double(p1.x + dx, p1.y + dy);
            Point2D.Double pB = new Point2D.Double(p1.x + 2*dx, p1.y + 2*dy);

            double angle = Math.toRadians(60);
            double px = pA.x + (Math.cos(angle) * (pB.x - pA.x) - Math.sin(angle) * (pB.y - pA.y));
            double py = pA.y + (Math.sin(angle) * (pB.x - pA.x) + Math.cos(angle) * (pB.y - pA.y));
            Point2D.Double pC = new Point2D.Double(px, py);

            drawKochCurve(g, level - 1, p1, pA);
            drawKochCurve(g, level - 1, pA, pC);
            drawKochCurve(g, level - 1, pC, pB);
            drawKochCurve(g, level - 1, pB, p2);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        int width = 600; // getWidth();
        int height = 600; // getHeight();

        // Equilateral triangle
        Point2D.Double p1 = new Point2D.Double(width / 2.0, height / 6.0);
        Point2D.Double p2 = new Point2D.Double(width / 6.0, height * 5.0 / 6.0);
        Point2D.Double p3 = new Point2D.Double(width * 5.0 / 6.0, height * 5.0 / 6.0);

        drawKochCurve(g2, level, p1, p2);
        drawKochCurve(g2, level, p2, p3);
        drawKochCurve(g2, level, p3, p1);
    }

    public static void main(String[] args) {
        int level = 4;  // Set recursion depth here

        JFrame frame = new JFrame("Koch Snowflake");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.add(new KochSnowflake(level));
        frame.setVisible(true);
    }
}
