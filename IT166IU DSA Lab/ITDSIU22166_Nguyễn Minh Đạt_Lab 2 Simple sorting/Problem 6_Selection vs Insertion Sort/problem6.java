class ArrayIns{
    private final long[] a;                 // ref to array a
    private int nElems;               // number of data items
//--------------------------------------------------------------
    public ArrayIns(int max){          // constructor
        a = new long[max];                 // create the array
        nElems = 0;                        // no items yet
    }
//--------------------------------------------------------------
    public void insert(long value){    // put element into array
        a[nElems] = value;             // insert it
        nElems++;                      // increment size
    }
//--------------------------------------------------------------
    public void display(){             // displays array contents
        for(int j=0; j<nElems; j++)       // for each element,
            System.out.print(a[j] + " ");  // display it
        System.out.println("");
    }
//--------------------------------------------------------------
    private void swap(int one, int two){
        long temp = a[one];
        a[one] = a[two];
        a[two] = temp;
    }
//--------------------------------------------------------------
    public void insertionSort(){
        int in, out;

        for(out=1; out<nElems; out++){     // out is dividing line
            long temp = a[out];            // remove marked item
            in = out;                      // start shifts at out
            while(in>0 && a[in-1] >= temp){ // until one is smaller,
                a[in] = a[in-1];            // shift item to right
                --in;                       // go left one position
            }
            a[in] = temp;                  // insert marked item
            // Display the array after each pass of the outer loop
            System.out.print("After outer loop (out=" + out + "): ");
         display();
        }  // end for loop
    }  // end insertionSort()
//--------------------------------------------------------------
    public void selectionSort(){
        int out, in, min;

        for(out=0; out<nElems-1; out++){   // outer loop
            min = out;                     // minimum
            for(in=out+1; in<nElems; in++) // inner loop
                if(a[in] < a[min] )         // if min greater,
                    min = in;               // we have a new min
            swap(out, min);                // swap them
        // Display the array after each outer loop iteration
        System.out.print("After outer loop (out=" + out + "): ");
        display();
        }  // end for(out)
    }  // end selectionSort()
//--------------------------------------------------------------
}  // end class ArrayIns

////////////////////////////////////////////////////////////////

public class problem6 {
    public static void main(String[] args){

        int maxSize = 9;            // array size
        ArrayIns arr;                 // reference to array
        ArrayIns arr_2;                 // reference to array

        arr = new ArrayIns(maxSize);  // create the array
        arr_2 = new ArrayIns(maxSize);  // create the array

        arr.insert(6);               // insert 9 items
        arr.insert(23);
        arr.insert(33);
        arr.insert(12);
        arr.insert(5);
        arr.insert(15);
        arr.insert(7);
        arr.insert(27);
        arr.insert(2);

        arr_2.insert(6);               // insert 9 items
        arr_2.insert(23);
        arr_2.insert(33);
        arr_2.insert(12);
        arr_2.insert(5);
        arr_2.insert(15);
        arr_2.insert(7);
        arr_2.insert(27);
        arr_2.insert(2);

        System.out.println("Original Array:");
        arr.display();                  // display items

        System.out.println("//--------------------------------------------------------------");
        System.out.println("Selection Sort Array 1:");
        arr.selectionSort();            // selection-sort them

        System.out.println("//--------------------------------------------------------------");
        System.out.println("Insertion Sort Array 2:");
        arr_2.insertionSort();          // Insertion-sort them
 
        System.out.println("//--------------------------------------------------------------");
        System.out.println("Array has been sorted:");
        arr.display();                  // display them again
    }  // end main()
}
