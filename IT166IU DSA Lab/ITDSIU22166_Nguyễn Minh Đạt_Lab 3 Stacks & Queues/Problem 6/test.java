import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Queue;

public class test {
    public static void rearrange(Deque<Integer> D, Queue<Integer> Q) {
        // Step 1: Move first 3 elements (1,2,3) to Q
        Q.offer(D.removeFirst());  // 1
        Q.offer(D.removeFirst());  // 2
        Q.offer(D.removeFirst());  // 3
        // D: (4,5,6,7,8)
        // Q: (1,2,3)

        // Step 2: Get 4 and 5 out, we'll add them back in reverse order
        int four = D.removeFirst();  // 4
        int five = D.removeFirst(); // 5
        // D: (6,7,8)
        // Q: (1,2,3)

        // Step 3: Put 5 and 4 back in that order
        D.addFirst(five);  // 5
        D.addFirst(four);  // 4
        // D: (4,5,6,7,8)
        // Q: (1,2,3)

        // Step 4: Move 1,2,3 back to front of D
        D.addFirst(Q.poll());  // 3
        D.addFirst(Q.poll());  // 2
        D.addFirst(Q.poll());  // 1
        // D: (1,2,3,5,4,6,7,8)
        // Q: empty
    }

    // Test the function
    public static void main(String[] args) {
        Deque<Integer> D = new ArrayDeque<>();
        Queue<Integer> Q = new ArrayDeque<>();
        
        // Initialize deque with 1,2,3,4,5,6,7,8
        for (int i = 1; i <= 8; i++) {
            D.addLast(i);
        }
        
        System.out.println("Before: " + D);
        rearrange(D, Q);
        System.out.println("After: " + D);
    }
}