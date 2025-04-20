import java.util.Random;
import java.util.Scanner;

public class Particle2D {
    public int x;
    public int y;
    public static Random random = new Random();
    public enum Direction {
        NORTH,
        NORTH_EAST,
        EAST,
        SOUTH_EAST,
        SOUTH,
        SOUTH_WEST,
        WEST,
        NORTH_WEST
    }
    Scanner sc = new Scanner(System.in);

    
    
    // Particle(x,y) with locate at point(x,y)
    public Particle2D(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ')';
    }
    // Move the particle to a new point(x,y)
    public void move(int dx, int dy) {
        int newX = this.x + dx;
        int newY = this.y + dy;
        this.x = newX;
        this.y = newY;
    }

    public void moveRandom() {
        Direction[] directions = Direction.values();
        int randomIndex = random.nextInt(directions.length);
        Direction direction = directions[randomIndex];
        switch (direction) {
            case NORTH:
                move(0, -1);
                break;
            case NORTH_EAST:
                move(1, -1);
                break;
            case EAST:
                move(1, 0);
                break;
            case SOUTH_EAST:
                move(1, 1);
                break;
            case SOUTH:
                move(0, 1);
                break;
            case SOUTH_WEST:
                move(-1, 1);
                break;
            case WEST:
                move(-1, 0);
                break;
            case NORTH_WEST:
                move(-1, -1);
                break;
        }
    }


    // calculate distance in first way
    public double distance(Particle2D p) {
        double dx = x - p.x;
        double dy = y - p.y;
        return Math.sqrt(dx*dx + dy*dy);
    }

    // calculate distance in second way
    public static double distance( Particle2D p1, Particle2D p2) {
        double dx = p1.x - p2.x;
        double dy = p1.y - p2.y;
        return Math.sqrt(dx*dx + dy*dy);
    }

    public int getX() { return x; }
    
    public int getY() { return y; }

    
}

