public class LinkQueue {
    private final FirstLastList theList;
    private int callCounter; // Counter to track the number of calls
    private int N;     // Number of calls before removal

    //--------------------------------------------------------------
    public LinkQueue(int N) { // Constructor with N as a parameter
        theList = new FirstLastList(); // Make a 2-ended list
        this.N = N;
        this.callCounter = 0;
    }

    //--------------------------------------------------------------
    public boolean isEmpty() { // true if queue is empty
        return theList.isEmpty();
    }

    //--------------------------------------------------------------
    public void insert(long j) { // Insert, rear of queue
        theList.insertLast(j);
    }

    //--------------------------------------------------------------
    public long removeN() { // Remove item after N calls
        callCounter++;
        if (callCounter == N) {
            callCounter = 0; // Reset the counter
            return theList.deleteFirst(); // Remove the front item
        }
        throw new IllegalStateException("Remove not allowed until " + N + " calls.");
    }

    //--------------------------------------------------------------
    public void displayQueue() {
        System.out.print("Queue (front-->rear): ");
        theList.displayList();
    }
    //--------------------------------------------------------------
    public long remove() { // Remove item, front of queue
        return theList.deleteFirst(); // Remove the front item
    }
    //--------------------------------------------------------------
    public long peek() { // Peek at the front item
        return theList.first.dData; // Return the data of the first item
    }
    //--------------------------------------------------------------
    public void move() { // Move to the next item
        theList.first = theList.first.next; // Move the first pointer to the next item
    }
    //--------------------------------------------------------------
    public long getCallCounter() { // Get the current call counter
        return callCounter;
    }
    //--------------------------------------------------------------
    public int getN() { // Get the value of N
        return N;
    }
    //--------------------------------------------------------------
    public void setCallCounter(int callCounter) { // Set the call counter
        this.callCounter = callCounter;
    }
    //--------------------------------------------------------------
    public void setN(int N) { // Set the value of N
        this.N = N;
    }
    //--------------------------------------------------------------
    public int size() { // Return the size of the queue
        return theList.size(); // Delegate to the underlying list
    }
    //--------------------------------------------------------------
    public void resetQueue() { // Reset the queue
        while (!isEmpty()) {
            remove(); // Remove all elements
        }
        callCounter = 0; // Reset the call counter
    }
    //--------------------------------------------------------------

}