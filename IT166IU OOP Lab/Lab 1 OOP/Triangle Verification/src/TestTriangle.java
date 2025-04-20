import java.util.Scanner;
public class TestTriangle {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter the length of 3 side: ");
            int side1 = scanner.nextInt();
            int side2 = scanner.nextInt();
            int side3 = scanner.nextInt();

            Triangle triangle = new Triangle(side1, side2, side3);
            System.out.println("The triangle is: " + triangle.verify());
        }
    }
}