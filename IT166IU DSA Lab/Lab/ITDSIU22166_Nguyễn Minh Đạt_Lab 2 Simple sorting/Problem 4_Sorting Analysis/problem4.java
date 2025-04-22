import java.util.Random;

class Sorting_Analysis {
    private int comparisons = 0;
    private int copies = 0;
    private int swaps = 0;

    // Generate a random array of given size
    public int[] generateRandomArray(int size) {
        Random rand = new Random();
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = rand.nextInt(100000); // Random numbers between 0 and 99999
        }
        return arr;
    }

    // Reset counters
    public void resetCounters() {
        comparisons = 0;
        copies = 0;
        swaps = 0;
    }

    // Bubble Sort
    public void bubbleSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1 - i; j++) {
                comparisons++;
                if (arr[j] > arr[j + 1]) {
                    swaps++;
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }

    // Selection Sort
    public void selectionSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                comparisons++;
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                swaps++;
                int temp = arr[i];
                arr[i] = arr[minIndex];
                arr[minIndex] = temp;
            }
        }
    }

    // Insertion Sort
    public void insertionSort(int[] arr) {
        int n = arr.length;
        for (int i = 1; i < n; i++) {
            int key = arr[i];
            int j = i - 1;
            while (j >= 0 && arr[j] > key) {
                comparisons++;
                copies++;
                arr[j + 1] = arr[j];
                j--;
            }
            comparisons++; // Final comparison when the loop exits
            arr[j + 1] = key;
            copies++; // Copy the key into its correct position
        }
    }

    // Getters for counters
    public int getComparisons() {
        return comparisons;
    }

    public int getCopies() {
        return copies;
    }

    public int getSwaps() {
        return swaps;
    }
}

public class problem4 {
    public static void main(String[] args) {
        int[] sizes = {10000, 15000, 20000, 25000, 30000, 35000, 40000, 45000, 50000};
        Sorting_Analysis sorter = new Sorting_Analysis();

        System.out.println("Size\tBubble Sort (Comparisons/Copies/Swaps)\tSelection Sort (Comparisons/Copies/Swaps)\tInsertion Sort (Comparisons/Copies/Swaps)");
        for (int size : sizes) {
            int[] arr = sorter.generateRandomArray(size);

            // Bubble Sort
            sorter.resetCounters();
            int[] bubbleArr = arr.clone();
            sorter.bubbleSort(bubbleArr);
            String bubbleStats = sorter.getComparisons() + "/" + sorter.getCopies() + "/" + sorter.getSwaps();

            // Selection Sort
            sorter.resetCounters();
            int[] selectionArr = arr.clone();
            sorter.selectionSort(selectionArr);
            String selectionStats = sorter.getComparisons() + "/" + sorter.getCopies() + "/" + sorter.getSwaps();

            // Insertion Sort
            sorter.resetCounters();
            int[] insertionArr = arr.clone();
            sorter.insertionSort(insertionArr);
            String insertionStats = sorter.getComparisons() + "/" + sorter.getCopies() + "/" + sorter.getSwaps();

            System.out.println(size + "\t" + bubbleStats + "\t\t\t" + selectionStats + "\t\t\t" + insertionStats);
        }
    }
}