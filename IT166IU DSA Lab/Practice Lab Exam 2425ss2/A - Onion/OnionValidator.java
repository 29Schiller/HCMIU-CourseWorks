import java.util.Scanner;
import java.util.Stack;

public class OnionValidator {
    public static boolean isValidOnion(String s) {
        Stack<Integer> stack = new Stack<>();
        int openCount = 0; // Count of '('
        int closeCount = 0; // Count of ')'
        int i = 0;
        int n = s.length();

        while (i < n) {
            char c = s.charAt(i);
            if (c == '(') {
                stack.push(i);
                openCount++;
                i++;
            } else if (c == ')') {
                if (stack.isEmpty()) {
                    return false; // Unmatched closing parenthesis
                }
                stack.pop();
                closeCount++;
                i++;
            } else if (Character.isLowerCase(c)) {
                // Letters must be inside parentheses (stack non-empty)
                if (stack.isEmpty()) {
                    return false; // Letters outside parentheses
                }
                i++;
            } else {
                return false; // Invalid character
            }
        }

        // Valid if all parentheses are matched, counts are equal, and string is fully consumed
        return stack.isEmpty() && openCount == closeCount && i == n;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder result = new StringBuilder();

        // Read input lines until no more input
        while (scanner.hasNextLine()) {
            String s = scanner.nextLine().trim();
            if (s.isEmpty()) {
                break; // Stop on empty line (if required by judge)
            }
            boolean isValid = isValidOnion(s);
            result.append(isValid ? "#True" : "#False").append(";");

        }

        // Print all results
        String[] results = result.toString().split(";");
        for (String res : results) {
            if (!res.isEmpty()) {
                System.out.println(res);
            }
        }
    }
}