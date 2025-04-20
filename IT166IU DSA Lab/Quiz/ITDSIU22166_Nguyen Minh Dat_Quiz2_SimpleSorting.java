/*  Quiz 2: Array & Sorting problem
    You are given two integer arrays nums1 and nums2 (sorted in non-decreasing order),
and two integers m and n (the number of elements in nums1 and nums2 respectively).
    Merge nums1 and nums2 into a single array sorted in non-decreasing order.
    The final sorted array should not be returned by the function, but instead be stored inside the array nums1.
    So, nums1 has a length of m + n, where the first m elements denote the elements that should be merged,
and the last n elements are set to 0 and should be ignored (nums2 has a length of n).

Requirements:
    1. Solve this problem and learn something from it.
    2. Write explanation for your code as comments (e.g. how does your code work? what is your thought process? why did you solve it that way?),
you can add comments within your code or write a full explanation at the end, as long as others can understand your solution clearly.
    3. Time complexity? Explain.

Example 1:
    Input: nums1 = [1,2,3,0,0,0], m = 3, nums2 = [2,5,6], n = 3
    Output: [1,2,2,3,5,6]
    Explanation: The arrays we are merging are [1,2,3] and [2,5,6].
    The result of the merge is [1,2,2,3,5,6] with the underlined elements coming from nums1.

Example 2:
    Input: nums1 = [1], m = 1, nums2 = [], n = 0
    Output: [1]
    Explanation: The arrays we are merging are [1] and [].
    The result of the merge is [1].

Example 3:
    Input: nums1 = [0], m = 0, nums2 = [1], n = 1
    Output: [1]
    Explanation: The arrays we are merging are [] and [1].
    The result of the merge is [1].
    Note that because m = 0, there are no elements in nums1. The 0 is only there to ensure the merge result can fit in nums1.
 */

import java.util.Arrays;
import java.util.Scanner;

public class Quiz2_SimpleSorting {
    // Provided code (you can modify this but make sure your code works, and you have enough time to do it)
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            //Generate nums1[]
            System.out.println("The size of array nums1[]: ");
            int size1 = sc.nextInt();
            int[] nums1 = new int[size1];
            System.out.println("Enter array nums1[] elements: ");
            for (int i = 0; i < size1; i++) {
                nums1[i] = sc.nextInt();
            }

            //Generate nums2[]
            System.out.println("The size of array nums2[] (n): ");
            int n = sc.nextInt();
            int[] nums2 = new int[n];
            System.out.println("Enter array nums2[] elements: ");
            for (int i = 0; i < n; i++) {
                nums2[i] = sc.nextInt();
            }

            System.out.println("Enter the number of initialized elements in nums1 (m): ");
            int m = sc.nextInt();

            System.out.println("Result: " + Arrays.toString(Solution(nums1, m, nums2, n)));
        }
    }

    public static int[] Solution(int[] nums1, int m, int[] nums2, int n) {
        // Start from the end of nums1 (where the zeros are) and work backwards.
        int i = m - 1; // Index for the last element in the initialized part of nums1
        int j = n - 1; // Index for the last element in nums2
        int k = m + n - 1; // Index for where we will place the merged element in nums1

        // Merge nums1 and nums2 from the back
        while (j >= 0) {
            if (i >= 0 && i < nums1.length && nums1[i] > nums2[j]) {
                nums1[k--] = nums1[i--]; // Place the larger element from nums1
            } else {
                nums1[k--] = nums2[j--]; // Place the larger element from nums2 (or nums2 if nums1 is empty)
            }
        }

        return nums1;
    }
}

//Time complexity: O(m + n)
/* Inside the loop, we perform constant-time operations: comparisons, assignments, and decrements.
 * These operations do not depend on the size of the input arrays. Besides, the loop runs at most n times
 * and i is decremented at most m times.
 * The while loop (j >= 0) starts at n - 1 and run n times to process all elements of nums2. 
 * Next, i is only decremented when:  nums1[i] is greater than nums2[j], it starts at m - 1
 * and can not be decremented more than m times.
 * So, the total number of operations is proportional to m + n.
 */
