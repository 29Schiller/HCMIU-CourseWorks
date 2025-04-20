import java.util.Scanner;

public class Point2D {
    private int x;
    private int y;
    Scanner sc = new Scanner(System.in);
    public Point2D() {
        // write your code here
        this.x = 0;
        this.y = 0;
    }

    public Point2D(int x, int y) {
        // write your code here
        this.x = x;
        this.y = y;
    }

    public Point2D(Point2D p) {
        // write your code here
        this.x = p.x;
        this.y = p.y;
    }

    public void input() {
        try (// write your code here
        Scanner sc = new Scanner(System.in)) {
            x = sc.nextInt();
            y = sc.nextInt();
        }
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public void move(int x, int y) {
        this.x = x;
        this.y = y;
    }


    public boolean isOrigin() {
        // write your code here
        return x == 0 && y == 0;
    }

    public double distance(Point2D p) {
        // write your code here
        double dx = x - p.x;
        double dy = y - p.y;
        return Math.sqrt(dx*dx + dy*dy);
    }

    public static double distance( Point2D p1, Point2D p2) {
        // write your code here
        double dx = p1.x - p2.x;
        double dy = p1.y - p2.y;
        return Math.sqrt(dx*dx + dy*dy);
    }

    public int getX() { return x; }
    
    public int getY() { return y; }
}