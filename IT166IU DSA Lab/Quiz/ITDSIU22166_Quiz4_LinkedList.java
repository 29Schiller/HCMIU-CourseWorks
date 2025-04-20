/*  Quiz 4: Linked list problem
    Given a linked list, rotate the list to the right by k places.
    NOTE: If you're late (when assignment is closed on Bb) but it's not 4pm yet, you can send your file via my contact information (outlook, zalo, facebook).
          Otherwise, your file won't be graded except you have valid reasons.

Example 1:
    Input: list = [1,2,3,4,5], k = 1
    Output: [5,1,2,3,4]

Example 2:
    Input: list = [0,1,2], k = 2
    Output: [1,2,0]
    Input: list = [0,1,2], k = 3
    Output: [0,1,2]
    Input: list = [0,1,2], k = 4
    Output: [2,0,1]
*/

import java.util.LinkedList;
import java.util.Scanner;

public class ITDSIU22166_Quiz4_LinkedList {
    // Provided code (you can modify this but make sure your code works, and you have enough time to do it)
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.println("Enter the number of elements in the list: ");
            int n = sc.nextInt();

            LinkedList<Integer> list = new LinkedList<>();
            System.out.println("Enter the elements:");
            for (int i = 0; i < n; i++) {
                list.add(sc.nextInt());
            }

            System.out.println("Enter k: ");
            int k = sc.nextInt();

            System.out.println("Rotated List: " + Solution(list, k));
        }
    }

    public static LinkedList<Integer> Solution(LinkedList<Integer> list, int k) {
        if (list == null || list.isEmpty() || k == 0) {
            return list;
        }

        int size = list.size();
        k = k % size; // Handle k larger than list size

        if (k == 0) {
            return list;
        }

        for (int i = 0; i < k; i++) {
            int last = list.removeLast();
            list.addFirst(last);
        }

        return list;
    }
}

//Time complexity: The time complexity of the LINKED LIST is O(n), where n is the size of the linked list
