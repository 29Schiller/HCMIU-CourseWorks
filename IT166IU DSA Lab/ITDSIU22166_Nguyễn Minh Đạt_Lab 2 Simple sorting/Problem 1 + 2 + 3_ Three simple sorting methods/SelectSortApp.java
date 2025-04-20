// selectSort.java
// demonstrates selection sort
// to run this program: C>java SelectSortApp
////////////////////////////////////////////////////////////////
class ArraySel
   {
   private final long[] a;                 // ref to array a
   private int nElems;               // number of data items
//--------------------------------------------------------------
   public ArraySel(int max)          // constructor
      {
      a = new long[max];                 // create the array
      nElems = 0;                        // no items yet
      }
//--------------------------------------------------------------
   public void insert(long value)    // put element into array
      {
      a[nElems] = value;             // insert it
      nElems++;                      // increment size
      }
//--------------------------------------------------------------
   public void display()             // displays array contents
      {
      for(int j=0; j<nElems; j++)       // for each element,
         System.out.print(a[j] + " ");  // display it
      System.out.println("");
      }
//--------------------------------------------------------------
   public void selectionSort(){
      int out, in, min;
      int totalComparisons = 0; // Total number of comparisons

      for(out=0; out<nElems-1; out++){    // outer loop
         min = out;                       // minimum
         int comparisonCount = 0;         // Reset comparison count for each outer loop iteration     

         for(in=out+1; in<nElems; in++){  // inner loop
            comparisonCount++; // Increment comparison count
            totalComparisons++; // Increment total comparisons

            if(a[in] < a[min]){           // if min greater,
                min = in;                 // we have a new min
            }
            // Display the array after each inner loop iteration
            System.out.print("After inner loop (outer loop=" + out + ", inner loop=" + in + "): ");
            display();
         }  // End for inner loop           
         // Swap only if the minimum element is not already in its correct position
         if (min != out) {
            System.out.println("Swapping " + a[out] + " and " + a[min]);
            swap(out, min); // Swap the minimum element with the first element of the unsorted portion
        } else {
            System.out.println("No swap needed for " + a[out]);
        }
        // Display the array after each outer loop iteration
        System.out.print("After outer loop (out=" + out + "): ");
        display();
        System.out.println("Comparisons in this iteration: " + comparisonCount);
      }  // end for(out)
      System.out.println("--------------------------------------------------------------");
      // Display total number of comparisons
      System.out.println("Total number of comparisons: " + totalComparisons);
      // Estimate time complexity
      System.out.println("Estimated time complexity: O(n^2)");
   }  // end selectionSort()
//--------------------------------------------------------------
   private void swap(int one, int two)
      {
      long temp = a[one];
      a[one] = a[two];
      a[two] = temp;
      }
//--------------------------------------------------------------
   }  // end class ArraySel
////////////////////////////////////////////////////////////////
public class SelectSortApp
   {
   public static void main(String[] args)
      {
      int maxSize = 100;            // array size
      ArraySel arr;                 // reference to array
      arr = new ArraySel(maxSize);  // create the array

      arr.insert(77);               // insert 10 items
      arr.insert(99);
      arr.insert(44);
      arr.insert(55);
      arr.insert(22);
      arr.insert(88);
      arr.insert(11);
      arr.insert(00);
      arr.insert(66);
      arr.insert(33);
      
      System.out.println("The orignial array:");
      arr.display();                // display original array

      arr.selectionSort();          // selection-sort them

      System.out.println("The sorted array:");
      arr.display();                // display sorted array 
      }  // end main()
   }  // end class SelectSortApp
////////////////////////////////////////////////////////////////
