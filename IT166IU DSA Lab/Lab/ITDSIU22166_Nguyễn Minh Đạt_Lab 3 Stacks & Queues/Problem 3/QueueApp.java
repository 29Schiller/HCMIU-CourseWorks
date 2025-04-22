// Queue.java
// demonstrates queue
// to run this program: C>java QueueApp
////////////////////////////////////////////////////////////////
import java.util.Random;

class Queue{
   private final int maxSize;
   private final long[] queArray;
   private int front;
   private int rear;
   private int nItems;
//--------------------------------------------------------------
   public Queue(int s){          // constructor
      maxSize = s;
      queArray = new long[maxSize];
      front = 0;
      rear = -1;
      nItems = 0;
   }
//--------------------------------------------------------------
   public void insert(long j) {   // put item at rear of queue
      if(isFull()) {
         System.out.println("Queue is full. Cannot insert " + j);
         return;
      }
      if(rear == maxSize-1)     // deal with wraparound
         rear = -1;
      rear++;                   // increment rear in either case
      queArray[rear] = j;
      nItems++;
   }
//--------------------------------------------------------------
   public long remove() {         // take item from front of queue
      if (isEmpty()) {
         System.out.println("Queue is empty. Cannot remove.");
         return -1;            // Return sentinel value or could throw exception
      }
      long temp = queArray[front];
      queArray[front] = 0;
      if(front == maxSize-1) {  // deal with wraparound (fixed from maxSize)
         front = 0;
      } else {
         front++;
      }
      nItems--;
      return temp;
   } 
//--------------------------------------------------------------
   public long peekFront(){      // peek at front of queue
      if(isEmpty()){
         System.out.println("Queue is empty. Cannot peek.");
      }
      return queArray[front];
   }
//--------------------------------------------------------------
   public boolean isEmpty(){    // true if queue is empty
      return (nItems==0);
   }
//--------------------------------------------------------------
   public boolean isFull(){     // true if queue is full
      return (nItems==maxSize);
   }
//--------------------------------------------------------------
   public int size(){          // number of items in queue
      return nItems;
   }
//--------------------------------------------------------------
   // Processing time removal
   public long removeWithProcessingTime(int processingTime) {
      if (isEmpty()) {
         System.out.println("Queue is empty. Cannot remove.");
         return -1;
      }

      if (processingTime <= 0) {
         return remove();
      }

      try {
         Thread.sleep(processingTime * 1000L); // Simulate processing time
      } catch (InterruptedException e) {}
      return remove();
   }
//--------------------------------------------------------------
   public void displayQueue() {
      System.out.print("Queue Array: ");
      for (int i = 0; i < maxSize; i++) {
         System.out.print(queArray[i] + " ");
      }
      System.out.println();
      System.out.println("Front Index: " + front);
      System.out.println("Rear Index: " + rear);
      System.out.println("Number of Items: " + nItems);
      System.out.print("Queue Contents: ");

      if(isEmpty()) {
         System.out.println("Empty");
         return;
      }
      
      // Correct display handling wraparound
      if (rear >= front) {
         for (int i = front; i <= rear; i++) {
            System.out.print(queArray[i] + " ");
         }
      } else {
         for (int i = front; i < maxSize; i++) {
            System.out.print(queArray[i] + " ");
         }
         for (int i = 0; i <= rear; i++) {
            System.out.print(queArray[i] + " ");
         }
      }
      System.out.println();
   }
//----------------------------------------------------------------    
}  // end class Queue

////////////////////////////////////////////////////////////////

public class QueueApp{
   public static void main(String[] args) {
      Queue theQueue = new Queue(5);

      // Test empty queue
      System.out.println("--- Test Empty Queue ---");
      theQueue.displayQueue();
      theQueue.remove();
      theQueue.peekFront();

      // Test inserting and removing
      System.out.println("\n--- Test Insert and Remove ---");
      theQueue.insert(10);
      theQueue.insert(20);
      theQueue.insert(30);
      theQueue.displayQueue();
      theQueue.remove();
      theQueue.remove();
      theQueue.displayQueue();
      theQueue.insert(40);
      theQueue.insert(50);
      theQueue.insert(60); // Test full queue
      theQueue.displayQueue();

      // Test remove with processing time
      System.out.println("\n--- Test Remove with Processing Time ---");
      Queue timeQueue = new Queue(5);
      timeQueue.insert(10);
      timeQueue.insert(20);
      System.out.println("Queue Array before remove with Processing Time: ");
      timeQueue.displayQueue();
      System.out.println("Removing with 2 seconds processing time: " + timeQueue.removeWithProcessingTime(2));
      timeQueue.displayQueue();
      System.out.println("Removing with next 2 seconds processing time: " + timeQueue.removeWithProcessingTime(2));
      timeQueue.displayQueue();

      // Simulation
      System.out.println("\n--- Simulation ---");
      simulateCustomerQueue(10, 1, 5, 2); // queueSize, minServiceTime, maxServiceTime, arrivalRate
   }

   public static void simulateCustomerQueue(int queueSize, int minServiceTime, int maxServiceTime, int arrivalRate) {
      Queue customerQueue = new Queue(queueSize);
      Random random = new Random();
      long currentTime = 0;
      long nextArrivalTime = 0;
      int customerId = 1;

      while (currentTime < 20) { // simulate for 20 seconds
            if (currentTime >= nextArrivalTime) {
              if (!customerQueue.isFull()) {
                  System.out.println(currentTime + ": Customer " + customerId + " arrived.");
                  customerQueue.insert(customerId++);
                  nextArrivalTime = currentTime + random.nextInt(arrivalRate) + 1; // Random arrival
              } else {
                  System.out.println(currentTime + ": Customer arrived but queue is full.");
              }
          }

            if (!customerQueue.isEmpty()) {
              int serviceTime = random.nextInt(maxServiceTime - minServiceTime + 1) + minServiceTime;
              System.out.println(currentTime + ": Serving customer " + customerQueue.peekFront() + " for " + serviceTime + " seconds.");
              customerQueue.removeWithProcessingTime(serviceTime);

              currentTime += serviceTime;
          } else {
              currentTime++; // If queue is empty, increment time.
          }
      }
  }
}