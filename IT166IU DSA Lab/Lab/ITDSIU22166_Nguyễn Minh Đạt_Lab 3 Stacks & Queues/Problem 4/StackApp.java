// stack.java
// demonstrates stacks
// to run this program: C>java StackApp
////////////////////////////////////////////////////////////////
class StackX{   
   private final int maxSize;        // size of stack array
   private final long[] stackArray;
   private int top;            // top of stack
//--------------------------------------------------------------
   public StackX(int s){        // constructor
      
      maxSize = s;             // set array size
      stackArray = new long[maxSize];  // create array
      top = -1;                // no items yet
   }
//--------------------------------------------------------------
   public void push(long j) {       // put item on top of stack
      if (isFull()) {
         System.out.println("Stack is full. Cannot push " + j);
         return;
      }
      stackArray[++top] = j;       // increment top, insert item
   }
//--------------------------------------------------------------
   public long pop() {             // take item from top of stack
      if (isEmpty()) {
         System.out.println("Stack is empty. Cannot pop.");
         return -1; // Or throw an exception
      }
      return stackArray[top--];     // access item, decrement top
   }
//--------------------------------------------------------------
   public long peek() {             // peek at top of stack
      if (isEmpty()) {
         System.out.println("Stack is empty. Cannot peek.");
         return -1; // Or throw an exception
      }
      return stackArray[top];
   }
//--------------------------------------------------------------
   public boolean isEmpty(){    // true if stack is empty
      return (top == -1);
   }
//--------------------------------------------------------------
   public boolean isFull(){     // true if stack is full
      return (top == maxSize-1);
   }
//--------------------------------------------------------------
   public void displayStackArray() {
      System.out.print("Stack Array: ");
      for (int i = 0; i < maxSize; i++) {
         System.out.print(stackArray[i] + " ");
      }
      System.out.println();
   }
//--------------------------------------------------------------
   public void displayStack() {
      System.out.print("Stack (top to bottom): ");
      for (int i = top; i >= 0; i--) {
         System.out.print(stackArray[i] + " ");
      }
      System.out.println();
   }
//--------------------------------------------------------------
}  // end class StackX
////////////////////////////////////////////////////////////////
public class StackApp {
   public static void main(String[] args) {
       StackX theStack = new StackX(5); // make new stack

       System.out.println("Initial State:");
       theStack.displayStackArray();
       theStack.displayStack();

       theStack.push(20); // push items onto stack
       System.out.println("\nAfter pushing 20:");
       theStack.displayStackArray();
       theStack.displayStack();

       theStack.push(40);
       System.out.println("\nAfter pushing 40:");
       theStack.displayStackArray();
       theStack.displayStack();

       theStack.push(60);
       System.out.println("\nAfter pushing 60:");
       theStack.displayStackArray();
       theStack.displayStack();

       theStack.push(80);
       System.out.println("\nAfter pushing 80:");
       theStack.displayStackArray();
       theStack.displayStack();

       System.out.println("Start to pop out the elements:");
       while (!theStack.isEmpty()) { // until it's empty,
           long value = theStack.pop(); // delete item from stack
           System.out.print(value + " "); // display it
           System.out.println("\nAfter popping " + value + ":");
           theStack.displayStackArray();
           theStack.displayStack();
       } // end while
       System.out.println("");
   } // end main()
} // end class StackApp
////////////////////////////////////////////////////////////////
