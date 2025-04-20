// priorityQ.java
// demonstrates priority queue
// to run this program: C>java PriorityQApp
////////////////////////////////////////////////////////////////
class PriorityQ{
   // array in sorted order, from max at 0 to min at size-1
   private final int maxSize;
   private final long[] queArray;
   private int nItems;
   private int rear;
//-------------------------------------------------------------
   public PriorityQ(int s){          // constructor
      maxSize = s;
      queArray = new long[maxSize];
      nItems = 0;
      rear = -1;
   }
//-------------------------------------------------------------
   public void insert(long item){    // insert item
      int j;
      if(nItems==0){                       // if no items,
         queArray[nItems++] = item;// insert at 0
      } else if (nItems == maxSize) {
         System.out.println("Queue is full"); //Checking if the array is full
      } else {                             // if items,
         for(j=nItems-1; j>=0; j--){        // start at end,
            if( item > queArray[j] )      // if new item larger,
               queArray[j+1] = queArray[j]; // shift upward
            else                          // if smaller,
               break;                     // done shifting
         }  // end for loop
         queArray[j+1] = item;            // insert it
         nItems++;
      }  // end else (nItems > 0)
   }  // end insert()
//-------------------------------------------------------------
   public void insertRear(long item){    // insert item at the rear
      if(isFull()) {
         System.out.println("Queue is full. Cannot insert " + item);
         return;
      }
      if(rear == maxSize-1)     // deal with wraparound
         rear = -1;
      rear++;                   // increment rear in either case
      queArray[rear] = item;
      nItems++;
   }  // end insertRear()
//-------------------------------------------------------------
   public long remove()             // remove minimum item
      { return queArray[--nItems]; }
//-------------------------------------------------------------
   public long peekMin()            // peek at minimum item
      { return queArray[nItems-1]; }
//-------------------------------------------------------------
   public boolean isEmpty()         // true if queue is empty
      { return (nItems==0); }
//-------------------------------------------------------------
   public boolean isFull()          // true if queue is full
      { return (nItems == maxSize); }
//-------------------------------------------------------------
   public void display() {
      System.out.print("Queue (front to rear): ");
      for(int i = 0; i < nItems; i++) {
         System.out.print(queArray[i] + " ");
      }
      System.out.println();
   }
//-------------------------------------------------------------

}  // end class PriorityQ
////////////////////////////////////////////////////////////////
public class PriorityQApp {
   public static void main(String[] args) {
      PriorityQ thePQ = new PriorityQ(5);
      PriorityQ taskQueue = new PriorityQ(5);

      System.out.print("Inserting 30:");
      thePQ.insert(30);
      thePQ.display();
      
      System.out.print("Inserting 50:");
      thePQ.insert(50);
      thePQ.display();
      
      System.out.print("Inserting 10:");
      thePQ.insert(10);
      thePQ.display();
      
      System.out.print("Inserting 40:");
      thePQ.insert(40);
      thePQ.display();
      
      System.out.print("Inserting 20:");
      thePQ.insert(20);
      thePQ.display();

      System.out.print("Inserting 20:");
      thePQ.insert(20);

      System.out.println("\nRemoving items:");
      while (!thePQ.isEmpty()) {
         long item = thePQ.remove();
         System.out.print("Removed " + item + ": ");
         thePQ.display();
      }
      System.out.println("");

      // Simulate adding tasks with priorities (lower number = higher priority)
      System.out.println("Adding items with InsertRear:");
      taskQueue.insertRear(3);  // Task with priority 3
      taskQueue.display();
      taskQueue.insertRear(1);  // Task with priority 1
      taskQueue.display();
      taskQueue.insertRear(5);  // Task with priority 5
      taskQueue.display();
      taskQueue.insertRear(2);  // Task with priority 2
      taskQueue.display();
      taskQueue.insertRear(2);  // Task with priority 2
      taskQueue.display();

      // Simulate processing tasks
      System.out.println("\nRemoving Items:");
      while (!taskQueue.isEmpty()) {
         long task = taskQueue.remove(); 
         System.out.print("Removed " + task + ": ");
         taskQueue.display();
      }
   }
}  // end class PriorityQApp
////////////////////////////////////////////////////////////////
