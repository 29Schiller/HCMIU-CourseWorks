import java.util.Random;

class OrdArray {
    final long[] a;
        private int nElems;
        private int comparisons;
    
        public OrdArray(int max) {
            a = new long[max];
            nElems = 0;
            comparisons = 0;
        }
    
        public int size() {
            return nElems;
        }
    
        public int find(long searchKey) {
            int lowerBound = 0;
            int upperBound = nElems - 1;
            int curIn;
            comparisons = 0;
    
            while (true) {
                comparisons++;
                curIn = (lowerBound + upperBound) / 2;
                if (a[curIn] == searchKey)
                    return curIn;
                else if (lowerBound > upperBound)
                    return nElems;
                else {
                    if (a[curIn] < searchKey)
                        lowerBound = curIn + 1;
                    else
                        upperBound = curIn - 1;
                }
            }
        }
    
        public void insert(long value) {
            int j;
            for (j = 0; j < nElems; j++)
                if (a[j] > value)
                    break;
            for (int k = nElems; k > j; k--)
                a[k] = a[k - 1];
            a[j] = value;
            nElems++;
        }
    
        public boolean delete(long value) {
            int j = find(value);
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
    
        public int getComparisons() {
            return comparisons;
        }
    }
    
    class OrderedApp {
        public static void main(String[] args) {
            int maxSize = 100;
            OrdArray arr = new OrdArray(maxSize);
            Random random = new Random();
    
            for (int i = 0; i < 100; i++) {
                arr.insert(random.nextInt(1000));
            }
    
            int randomIndex = random.nextInt(100);
            long searchKey = arr.a[randomIndex];
        arr.find(searchKey);
        System.out.println("Comparisons for single find: " + arr.getComparisons());

        averageComparisons(100);
        averageComparisonsTrend();

        System.out.println("\nComplexity Comparison:");
        System.out.println("Linear Search (HighArrayApp): O(n)");
        System.out.println("Binary Search (OrderedApp): O(log n)");
    }

    public static void averageComparisons(int numTrials) {
        int maxSize = 100;
        long totalComparisons = 0;
        Random random = new Random();

        for (int trial = 0; trial < numTrials; trial++) {
            OrdArray arr = new OrdArray(maxSize);
            for (int i = 0; i < 100; i++) {
                arr.insert(random.nextInt(1000));
            }
            int randomIndex = random.nextInt(100);
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
            Random random = new Random();

            for (int trial = 0; trial < numTrials; trial++) {
                OrdArray arr = new OrdArray(size);
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