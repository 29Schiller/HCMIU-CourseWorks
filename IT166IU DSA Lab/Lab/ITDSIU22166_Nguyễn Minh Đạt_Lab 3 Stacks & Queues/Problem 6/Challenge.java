import java.util.LinkedList;

// Deque class implementation
class Deque {
    private final LinkedList<Integer> D;
    public Deque() { D = new LinkedList<>(); }

    public void pushFront(int x) { D.addFirst(x); }
    public void pushBack(int x) { D.addLast(x); }
    public Integer popFront() {
        if (!D.isEmpty()) {
            return D.removeFirst();
        }
        else { 
            System.out.println("Array is empty. Can not push");
            return null;
        }
    }
    public Integer popBack() {
        if (!D.isEmpty()) {
            return D.removeLast();
        }
        else { 
            System.out.println("Array is empty. Can not push");
            return null;
        }    
    }
    public boolean isEmpty() {
        return D.isEmpty();
    }
    public LinkedList<Integer> getDeque() {
        return D;
    }
}

// Queue class implementation
class Queue {
    private final LinkedList<Integer> Q;

    public Queue() { Q = new LinkedList<>(); }
    public void enqueue(int x) { Q.addLast(x); }

    public Integer dequeue() {
        if (!Q.isEmpty()) {
            return Q.removeFirst();
        }
        else { 
            System.out.println("Array is empty. Can not push");
            return null;
        }
    }
    public boolean isEmpty() { return Q.isEmpty(); }
    public LinkedList<Integer> getQueue() { return Q; }
}

// Example Usage (Optional)
public class Challenge {
    public static void main(String[] args) {
        Deque D = new Deque();
        Queue Q = new Queue();
        for (int i = 1 ; i <= 8 ; i++) { 
            D.pushBack(i);
        }
        System.out.println("Deque D: " + D.getDeque()); 
        System.out.println("Queue Q: " + Q.getQueue()); 
        
        for (int i = 0 ; i < 3 ; i++) { 
            Q.enqueue(D.popFront());
        }
        for (int i = 0 ; i < 3 ; i++) { 
            Q.enqueue(D.popBack());
        }
        for (int i = 0 ; i < 2 ; i++) { 
            Q.enqueue(D.popFront());
        }
        for (int i = 0 ; i < 3 ; i++) { 
            D.pushBack(Q.dequeue());
        }
        for (int i = 0 ; i < 3 ; i++) { 
            Q.enqueue(D.popBack());
        }
        while (!Q.isEmpty()){
            D.pushFront(Q.dequeue());
        }
        System.out.println("Deque D: " + D.getDeque()); 
        System.out.println("Queue Q: " + Q.getQueue());
        System.out.println("+-----+Extended Problem+-----+");
        Deque A = new Deque();
        Queue B = new Queue();
        for (int i = 1 ; i <= 8 ; i++) { 
            A.pushBack(i);
        }
        while (!A.isEmpty()){
            B.enqueue(A.popBack());
        }
        while (!B.isEmpty()){
            A.pushBack(B.dequeue());
        }
        System.out.println("Deque A: " + A.getDeque()); 
        System.out.println("Queue B: " + B.getQueue()); 
    }
}