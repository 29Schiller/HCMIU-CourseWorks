package Quiz; //please delete this package line when you paste those code in your file
/*  Quiz 3: Stack problem
    Given a string s containing just the characters '(', ')', '{', '}', '[' and ']', determine if the input string is valid.
    An input string is valid if:
        Open brackets must be closed by the same type of brackets.
        Open brackets must be closed in the correct order.
        Every close bracket has a corresponding open bracket of the same type.

Example 1:
    Input: s = "()[]{}"
    Output: true

Example :2
    Input: s = "(]"
    Output: false

Example 3:
    Input: s = "([])"
    Output: true
*/

import java.util.Scanner;
import java.util.Stack;

public class Quiz3_Stack {
    // Provided code (you can modify this but make sure your code works, and you have enough time to do it)
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter a string of brackets: ");
        String s = sc.next();
        System.out.println("Result: " + Solution(s));
    }

    public static boolean Solution(String s) {
        Stack<Character> stack = new Stack<>(); // Initialize a stack to store opening brackets

        // Iterate through each character in the string
        for (char c : s.toCharArray()) {
            // If the character is an opening bracket, push it onto the stack
            if (c == '(' || c == '{' || c == '[') {
                stack.push(c);
            }
            // Checking for a closing bracket
            else if (c == ')' || c == '}' || c == ']') {
                // If the stack is empty, there's no matching opening bracket
                if (stack.isEmpty()) {
                    return false;
                }
                // Pop the top of the stack and check if it matches the closing bracket
                char top = stack.pop();
                if ((c == ')' && top != '(') || 
                    (c == '}' && top != '{') || 
                    (c == ']' && top != '[')) {
                    return false;
                }
            }
        }
        // If the stack is empty, all brackets were matched
        return stack.isEmpty();
    }
}

//Time complexity:O(n), where n is the length of the input string s
