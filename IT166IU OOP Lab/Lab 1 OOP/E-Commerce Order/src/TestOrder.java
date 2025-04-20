import java.util.Scanner;

public class TestOrder {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            
            
            System.out.print("Enter the numbers of order: ");
            int orderQuantity = scanner.nextInt();
            
            for (int i = 0; i < orderQuantity; i++) {
                System.out.printf("This is the order %d%n",i + 1);
                System.out.print("Enter a new number ID for order: ");
                int orderID = scanner.nextInt();
                Order order = new Order(orderID);
                System.out.print("How many items in the order: ");
                int numItems = scanner.nextInt();
                for (int j = 0; j < numItems; j++) {
                    System.out.printf("Please enter the ID for item %d: ",j +1);
                    int id = scanner.nextInt();
                    System.out.print("Please enter the name for item: ");
                    String name = scanner.next();
                    System.out.print("Please enter the price for item: ");
                    double price = scanner.nextDouble();
                    Item item = new Item(id, name, price);
                    order.addItem(item);
                }
                System.out.printf("Average cost of order %d: %,.1f %n",i+1,order.calculateAverageCost());
            }
        }
    }
}