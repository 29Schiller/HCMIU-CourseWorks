import java.util.Random;
import java.util.Scanner;

class HighArray {
        final long[] a;
        private int nElems;
        private int comparisons;
    
        public HighArray(int max) {
            a = new long[max];
            nElems = 0;
            comparisons = 0;
        }
    
        public boolean find(long searchKey) {
            int j;
            comparisons = 0;
            for (j = 0; j < nElems; j++) {
                comparisons++;
                if (a[j] == searchKey)
                    break;
            }
            return j != nElems;
        }
    
        public void insert(long value) {
            a[nElems] = value;
            nElems++;
        }
    
        public boolean delete(long value) {
            int j;
            for (j = 0; j < nElems; j++)
                if (value == a[j])
                    break;
            if (j == nElems)
                return false;
            else {
                for (int k = j; k < nElems; k++)
                    a[k] = a[k + 1];
                nElems--;
                return true;
            }
        }
    
        public void display() {
            for (int j = 0; j < nElems; j++)
                System.out.print(a[j] + " ");
            System.out.println("");
        }
    
        public long getMax() {
            if (nElems == 0)
                return -1;
            long max = a[0];
            for (int i = 1; i < nElems; i++) {
                if (a[i] > max)
                    max = a[i];
            }
            return max;
        }
    
        public void noDups() {
            for (int i = 0; i < nElems; i++) {
                for (int j = i + 1; j < nElems; j++) {
                    if (a[i] == a[j]) {
                        a[j] = -1; // Mark duplicates with -1
                    }
                }
            }
            int newElems = 0;
            for (int i = 0; i < nElems; i++) {
                if (a[i] != -1) {
                    a[newElems] = a[i];
                    newElems++;
                }
            }
            nElems = newElems;
        }
    
        public int getComparisons() {
            return comparisons;
        }
    }
    
    class HighArrayApp {
        public static void main(String[] args) {
            try (Scanner scanner = new Scanner(System.in)) {
                System.out.print("Enter maximum array size: ");
                int maxSize = scanner.nextInt();
                HighArray arr = new HighArray(maxSize);
                Random random = new Random();
                
                System.out.print("Enter number of items to insert: ");
                int numItems = scanner.nextInt();
                for (int i = 0; i < numItems; i++) {
                    arr.insert(random.nextInt(1000)); // Insert random numbers
                }
                
                arr.display();
                
                System.out.print("Enter search key: ");
                int searchKey = scanner.nextInt();
                if (arr.find(searchKey))
                    System.out.println("Found " + searchKey);
                else
                    System.out.println("Can't find " + searchKey);
                
                System.out.println("Comparisons: " + arr.getComparisons());
                
                System.out.println("Max value: " + arr.getMax());
                
                arr.noDups();
                System.out.println("Array after removing duplicates:");
                arr.display();
                
                // Average comparisons
                averageComparisons(100);
                averageComparisonsTrend();
            }
        }
    
        public static void averageComparisons(int numTrials) {
            int maxSize = 100;
            HighArray arr = new HighArray(maxSize);
            Random random = new Random();
            long totalComparisons = 0;
    
            for (int trial = 0; trial < numTrials; trial++) {
                arr = new HighArray(maxSize);
                for (int i = 0; i < maxSize; i++) {
                    arr.insert(random.nextInt(1000));
                }
                int randomIndex = random.nextInt(maxSize);
                long searchKey = arr.a[randomIndex];
            arr.find(searchKey);
            totalComparisons += arr.getComparisons();
        }

        double average = (double) totalComparisons / numTrials;
        System.out.println("Average comparisons over " + numTrials + " trials: " + average);
    }

    public static void averageComparisonsTrend() {
        for (int size = 100; size <= 1000; size += 100) {
            int numTrials = 100;
            long totalComparisons = 0;
            for (int trial = 0; trial < numTrials; trial++) {
                HighArray arr = new HighArray(size);
                Random random = new Random();
                for (int i = 0; i < size; i++) {
                    arr.insert(random.nextInt(10000));
                }
                int randomIndex = random.nextInt(size);
                long searchKey = arr.a[randomIndex];
                arr.find(searchKey);
                totalComparisons += arr.getComparisons();
            }
            double average = (double) totalComparisons / numTrials;
            System.out.println("Average comparisons for size " + size + ": " + average);
        }
    }
}