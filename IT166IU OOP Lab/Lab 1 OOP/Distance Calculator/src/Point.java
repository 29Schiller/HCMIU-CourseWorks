public class Point {
    private double x;
    private double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double distance(Point target) {
        double deltaX = x - target.getX();
        double deltaY = y - target.getY();
        return Math.sqrt(Math.pow(deltaX,2) + Math.pow(deltaY,2));
    }
}