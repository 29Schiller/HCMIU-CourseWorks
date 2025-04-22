import java.util.Scanner;

public class MinGapFinder {

    public static int minGap(int[] array, int n) {
        if (array == null || n < 2) {
            return 0; // Handle cases with fewer than 2 elements or null array
        }

        int minGap = Integer.MAX_VALUE; // Initialize with the largest possible integer value

        for (int i = 0; i < n - 1; i++) {
            int gap = array[i + 1] - array[i];
            minGap = Math.min(minGap, gap); // Update minGap if a smaller gap is found
        }

        return minGap;
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter the size of the array: ");
            int size = scanner.nextInt();

            if (size <= 0) {
                throw new IllegalArgumentException("Array size must be positive.");
            }

            int[] array = new int[size];

            System.out.println("Enter the array elements:");
            for (int i = 0; i < size; i++) {
                array[i] = scanner.nextInt();
            }
            int minGap = minGap(array, size);
            System.out.println("Min gap in the array: " + minGap);
        }
    }
}