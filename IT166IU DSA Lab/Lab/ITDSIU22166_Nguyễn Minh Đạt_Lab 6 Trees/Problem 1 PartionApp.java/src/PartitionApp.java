////////////////////////////////////////////////////////////////
// Exercises:
// 1. Add counters for the number of comparisons and swaps and display
//    them after partitioning
// 2. Investigate the relationship between the index of partitioning,
//    the number of comparison, and the number of swaps.
// 3. Do Exercise 2 with different pivots: 
//    - beginning, end, or middle of the interval; 
//    - selected at random from the interval or from a larger interval;
//    - last item in the array.
// 4. Compute the average number of comparisons and swaps over 100 runs.
import java.util.Random;

public class PartitionApp {
   public static void main(String[] args) {
      int maxSize = 16;             // array size
      int runs = 100;
      long totalComparisons = 0;
      long totalSwaps = 0;

      for (int run = 0; run < runs; run++) {
         ArrayPar arr = new ArrayPar(maxSize); // create the array

         for(int j=0; j<maxSize; j++){  // fill array with random numbers
            long n = (int)(java.lang.Math.random()*199);
            arr.insert(n);
         }
         arr.display();                // display unsorted array

         // Test different pivot strategies
         long pivot = arr.getRandomPivot(); // Random pivot
         // long pivot_First = arr.getFirstElement(); // First element as pivot
         // long pivot_Last = arr.getLastElement(); // Last element as pivot
         // long pivot_Middle = arr.getMiddleElement(); // Middle element as pivot


         System.out.print("Pivot is " + pivot);
         int size = arr.size(); // partition array
         int partDex = arr.partitionIt(0, size-1, pivot);
         System.out.println(", Partition is at index " + partDex);
         arr.display();                // display partitioned array

         // Accumulate comparisons and swaps
         totalComparisons += arr.getComparisons();
         totalSwaps += arr.getSwaps();
      }
      // Display average comparisons and swaps
      System.out.println("Average Comparisons: " + (totalComparisons / runs));
      System.out.println("Average Swaps: " + (totalSwaps / runs));
   }  // end main()
}  // end class PartitionApp
////////////////////////////////////////////////////////////////


class ArrayPar {
   private final long[] theArray;          // ref to array theArray
   private int nElems;               // number of data items
   private long comparisons; // count of comparisons
   private long swaps; // count of swaps
   private Random random;
//--------------------------------------------------------------
   public ArrayPar(int max){          // constructor
      theArray = new long[max]; // create the array
      nElems = 0; // no items yet
      comparisons = 0; // initialize comparisons
      swaps = 0; // initialize swaps
      random = new Random(); // random number generator
   }
//--------------------------------------------------------------
   public void insert(long value){    // put element into array
      theArray[nElems] = value;      // insert it
      nElems++;                      // increment size
   }
//--------------------------------------------------------------
   public int size()                 // return number of items
      { return nElems; }
//--------------------------------------------------------------
   public void display(){             // displays array contents
      System.out.print("A=");
      for(int j=0; j<nElems; j++)    // for each element,
         System.out.print(theArray[j] + " ");  // display it
      System.out.println();
   }
//--------------------------------------------------------------
   public int partitionIt(int left, int right, long pivot) {
       int leftPtr = left - 1;           // right of first elem
       int rightPtr = right + 1;         // left of pivot
       while(true) {
          while (leftPtr < right && theArray[++leftPtr] < pivot) // find bigger item
             comparisons++;// (nop)

          while (rightPtr > left && theArray[--rightPtr] > pivot) // find smaller item
             comparisons++; // (nop)

          if(leftPtr >= rightPtr)        // if pointers cross,
             break;                      //    partition done
          else                           // not crossed, so
             swap(leftPtr, rightPtr);    //    swap elements
       }  // end while(true)
       return leftPtr;                   // return partition
   }  // end partitionIt()
//--------------------------------------------------------------
   public void swap(int dex1, int dex2){  // swap two elements
      long temp;
      temp = theArray[dex1];             // A into temp
      theArray[dex1] = theArray[dex2];   // B into A
      theArray[dex2] = temp;             // temp into B
      swaps++; // increment swap count
   }  // end swap()
   //--------------------------------------------------------------
   public long getComparisons() {
      return comparisons;
   }

   public long getSwaps() {
      return swaps;
   }

   public long getRandomPivot() {
      return theArray[random.nextInt(nElems)];
   }

   public long getFirstElement() {
      return theArray[0];
   }
   public long getLastElement() {
      return theArray[nElems - 1]; // return the last element
   }

   public long getMiddleElement() {
      return theArray[nElems / 2]; // return the middle element
   }
//--------------------------------------------------------------
}  // end class ArrayPar
///////////////////////////////////////////////////////////////