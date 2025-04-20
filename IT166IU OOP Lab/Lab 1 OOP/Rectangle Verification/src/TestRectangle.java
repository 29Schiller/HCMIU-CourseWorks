import java.util.Scanner;
public class TestRectangle {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter the width of the rectangle: ");
            int width = scanner.nextInt();
            System.out.print("Enter the height of the rectangle: ");
            int height = scanner.nextInt();
            
            Rectangle Rectangle = new Rectangle(width, height);
            Rectangle.visualize();
        }
    }
}