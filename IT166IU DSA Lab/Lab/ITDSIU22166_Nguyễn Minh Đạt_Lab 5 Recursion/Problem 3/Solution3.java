import java.util.*;

public class Solution3{
    public static int sum(int n){
        if (n <= 0) {
            return 0; // Base case: sum of numbers up to 0 is 0
        } else {
            return n + sum(n - 1); // Recursive case: add n to the sum of numbers up to n-1
        }
    }

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.println("Test Case 1:");
            int n = sc.nextInt();
            System.out.println(sum(n));

            // The base case
            // If number is  <= 0. Sum up to 0 is 0
            System.out.println("Base Case: num <= 0");
            int base = sc.nextInt();
            System.out.println(sum(base));

            // Recursive case
            // If number is > 0, Sum up to 0 is recursive case.
            // Otherwise, return base multiplied by the result of the recursive call with base incremented by 1
            System.out.println("Recursive Case: num > 0");
            int Recursive = sc.nextInt();
            System.out.println(sum(Recursive));
        }

        // Test cases
        System.out.println("Test Case:");
        System.out.println("The sum of all numbers from 1 to 10:");
        System.out.println(sum(10));
    }
}