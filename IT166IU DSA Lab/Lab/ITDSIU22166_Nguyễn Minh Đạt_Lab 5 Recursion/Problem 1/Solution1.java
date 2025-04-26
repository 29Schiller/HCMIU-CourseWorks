import java.util.*;

public class Solution1 {
    public static int puzzle(int base, int limit){
        if(base > limit) return -1;
        else if(base == limit) return 1;
        else return base * puzzle(base + 1, limit);
    }

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.println("Test 1:");
            int base = sc.nextInt();
            int limit = sc.nextInt();
            System.out.println(puzzle(base, limit));

        // The base case
        // If base is greater than limit, return -1
            System.out.println("Test 2:");
            int base2 = sc.nextInt();
            int limit2 = sc.nextInt();
            System.out.println(puzzle(base2, limit2));// base = 5, limit = 2

        // Recursive case
        // If base is equal to limit, return 1
        // Otherwise, return base multiplied by the result of the recursive call with base incremented by 1
            System.out.println("Test 3:");
            int base3 = sc.nextInt();
            int limit3 = sc.nextInt();
            System.out.println(puzzle(base3, limit3));// base = 2, limit = 2
        }

        // Test cases
        System.out.println("Test Cases:");
        System.out.println(puzzle(14,10));
        System.out.println(puzzle(4,7));
        System.out.println(puzzle(0,0));

    }
}