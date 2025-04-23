import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Mr_Pips {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Read number of test cases
        int T = scanner.nextInt();

        // Process each test case
        for (int t = 0; t < T; t++) {
            // Read number of roads
            int E = scanner.nextInt();

            // Use a Set to store unique cities
            Set<Integer> cities = new HashSet<>();

            // Read each road and add cities to the set
            for (int i = 0; i < E; i++) {
                int X = scanner.nextInt();
                int Y = scanner.nextInt();
                cities.add(X);
                cities.add(Y);
            }

            // Output the number of unique cities
            System.out.println(cities.size());
        }

        scanner.close();
    }
}