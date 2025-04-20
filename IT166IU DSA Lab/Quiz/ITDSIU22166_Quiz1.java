/*  Quiz 1: Array problem
    You are given a large integer represented as an integer array digits, where each digits[i] is the ith digit of the integer.
    The digits are ordered from most significant to least significant in left-to-right order.
    The large integer does not contain any leading 0's.
    Increment the large integer by one and return the resulting array of digits.
Requirements:
    1. Solve this problem and learn something from it.
    2. Write explanation for your code as comments (e.g. how does your code work? what is your thought process? why did you solve it that way?),
you can add comments within your code or write a full explanation at the end, as long as others can understand your solution clearly.
    3. Time complexity? Explain.

Example 1:
    Input: digits = [4,3,2,1]
    Output: [4,3,2,2]
    Explanation: The array represents the integer 4321.
    Incrementing by one gives 4321 + 1 = 4322.
    Thus, the result should be [4,3,2,2].

Example 2:
    Input: digits = [9,9]
    Output: [1,0,0]
    Explanation: The array represents the integer 99.
    Incrementing by one gives 99 + 1 = 100.
    Thus, the result should be [1,0,0].
 */

import java.util.Arrays;
import java.util.Scanner;

public class Quiz1_Array {
    // Provided code (you can modify this but make sure your code works, and you have enough time to do it)
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.println("The size of array digits[]: ");
            int size = sc.nextInt();
            int[] digits = new int[size];

            System.out.println("Enter array elements: ");
            for (int i = 0; i < size; i++) {
                digits[i] = sc.nextInt();
            }
            System.out.println("Result: " + Arrays.toString(Solution(digits)));
        }
    }

    public static int[] Solution(int[] digits) {
        // Iterate from the least significant digit (rightmost) to the most significant digit (leftmost).
        for (int i = digits.length - 1; i >= 0; i--) {
            // Increment the current digit by 1.
            digits[i]++;
            // If the digit becomes less than 10, it means there's no carry-over to the next digit.
            if (digits[i] < 10) {
                return digits; // We can return the updated array immediately.
            }
            // If the digit becomes 10 or more, set it to 0 and continue the loop to handle the carry-over.
             digits[i] = 0;
        }

        // If the loop completes without returning, it means we had carry-over all the way to the most significant digit.
        // In this case, we need to create a new array with an additional digit at the beginning (for the carry-over).
        int[] result = new int[digits.length + 1];
        result[0] = 1; // Set the first digit of the new array to 1 (the carry-over).
        // The rest of the digits will be 0 by default in Java for a new int array.
        return result;
    }
}

//Time complexity:
//Since the loop iterates at most n times, and the operations inside the loop are constant time,
// the dominant factor determining the time complexity is the loop itself, which is O(n).