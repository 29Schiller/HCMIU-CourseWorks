import java.util.Scanner;

public class TestDistance {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter the first point x and y: ");
            double xA = scanner.nextDouble();
            double yA = scanner.nextDouble();
            System.out.print("Enter the second point x and y: ");
            double xB = scanner.nextDouble();
            double yB = scanner.nextDouble();

            Point firstPoint = new Point(xA, yA) ;
            Point secondPoint = new Point(xB, yB);

            System.out.println("Distance between the first point and the second point is: " + firstPoint.distance(secondPoint));
        }
    }
}