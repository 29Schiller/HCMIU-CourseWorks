import java.util.Random;
import java.util.Scanner;
////////////////////////////////////////////////////////////////
/// LinkQueue.java
// Demonstration of a queue using a 2-ended list
public class LinkQueueApp{
   public static void main(String[] args){

// --------------------------------------------------------------------------------------------------------------------------
      // Create a Scanner and Random object to read input and generate random numbers
      Scanner sc = new Scanner(System.in);
      Random random = new Random(); // Create a Random object
// --------------------------------------------------------------------------------------------------------------------------
      //Enter the number of calls before removal (N)
      System.out.print("Enter the number of calls before removal (N): ");
      int N = sc.nextInt(); 
      if (N <= 0) {
         System.out.println("Please enter a positive integer for N.");
         return;
      }
      System.out.println("N = " + N); // Display the value of N
// --------------------------------------------------------------------------------------------------------------------------
      //Enter the number of customers
      System.out.print("Enter the number of customers: ");
      int customer = sc.nextInt();
      if (customer <= 0) {
         System.out.println("Please enter a positive integer for customers.");
         return;
      }
// --------------------------------------------------------------------------------------------------------------------------
      //Enter the join rate (ms) of customers
      System.out.print("Enter the join rate of customers: ");
      int joinrate = sc.nextInt();
      if (joinrate < 1) {
         System.out.println("The least joining rate of customers is 1 second.");
         return;
      }
// --------------------------------------------------------------------------------------------------------------------------
      //Enter the Maximum service time (ms) for a customer
      System.out.print("Enter the max service time: ");
      int maxServiceTime = sc.nextInt();
      if (maxServiceTime > 5) {
         System.out.println("The maximum service time is 5 seconds.");
         return;
      }
// --------------------------------------------------------------------------------------------------------------------------

      LinkQueue theQueue = new LinkQueue(N); 
      
      System.out.println("Simulation started...");
      System.out.println("Customers will join the queue every " + joinrate + " ms.");
      System.out.println("Maximum service time for a customer is " + maxServiceTime + " ms.");
      System.out.println("Number of customers to be served: " + customer);
      System.out.println("// --------------------------------------------------------------");

      //---------------------------------------------------------------------------------------------------------------------
      // Simulate adding customers to the queue
      for (int i = 1; i <= customer; i++) {
         try {
            Thread.sleep(joinrate * 1000); // Simulate service time
         } catch (InterruptedException e) {
               System.out.println("Service interrupted.");
         }
         theQueue.insert(random.nextInt(100)); // Generate a random number and insert it into the queue
         System.out.println("Customer " + i + " added to the queue.");
      }
      
      System.out.println("Initial Queue:");
      theQueue.displayQueue();
      System.out.println("// --------------------------------------------------------------");

      //---------------------------------------------------------------------------------------------------------------------
      // Simulate serving customers
      while (!theQueue.isEmpty()) {
         long servedCustomer = theQueue.remove(); // Remove the next customer
         int serviceTime = random.nextInt(maxServiceTime) + 1; // Random service time between 1 and maximum seconds
         System.out.println("Customer " + servedCustomer + " is being served for " + serviceTime + " seconds.");

         try {
            Thread.sleep(serviceTime * 1000); // Simulate service time
         } catch (InterruptedException e) {
            System.out.println("Service interrupted.");
         }

         System.out.println(servedCustomer + " has been served.");
         System.out.println("Remaining Queue:");
         theQueue.displayQueue();
         System.out.println("// --------------------------------------------------------------");
      }
      System.out.println("All customers have been served.");
      System.out.println("Simulation ended.");
      System.out.println("// --------------------------------------------------------------");
   }  // end main()
}  // end class LinkQueueApp
////////////////////////////////////////////////////////////////
