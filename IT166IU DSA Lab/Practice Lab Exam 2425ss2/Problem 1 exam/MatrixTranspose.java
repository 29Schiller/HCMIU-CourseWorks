import java.util.Scanner;

public class MatrixTranspose {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Read n and m
        int n = scanner.nextInt();
        int m = scanner.nextInt();

        // Create original matrix
        int[][] matrix = new int[n][m];

        // Read matrix elements
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                matrix[i][j] = scanner.nextInt();
            }
        }

        // Create transpose matrix (m x n)
        int[][] transpose = new int[m][n];

        // Compute transpose
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                transpose[j][i] = matrix[i][j];
            }
        }

        // Print transpose matrix
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(transpose[i][j] + " ");
            }
            System.out.println();
        }

        scanner.close();
    }
}