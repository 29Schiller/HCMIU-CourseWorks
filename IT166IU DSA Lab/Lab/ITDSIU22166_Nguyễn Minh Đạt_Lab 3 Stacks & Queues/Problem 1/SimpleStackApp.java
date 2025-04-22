import java.util.Stack;

public class SimpleStackApp {

    public static String decimalToOctal(int decimalNum) {
        if (decimalNum == 0) {
            return "0";
        }

        StringBuilder octal = new StringBuilder();
        while (decimalNum > 0) {
            octal.append(decimalNum % 8);
            decimalNum /= 8;
        }

        return octal.reverse().toString();
    }

    public static Stack<Integer> concatenateStacks(Stack<Integer> stack1, Stack<Integer> stack2) {
        Stack<Integer> result = new Stack<>();
        Stack<Integer> temp1 = new Stack<>();
        Stack<Integer> temp2 = new Stack<>();

        // Reverse stack1 into temp1
        while (!stack1.isEmpty()) {
            temp1.push(stack1.pop());
        }

        // Reverse temp1 into result (effectively keeping original order)
        while (!temp1.isEmpty()) {
            result.push(temp1.pop());
        }

        // Reverse stack2 into temp2
        while (!stack2.isEmpty()) {
            temp2.push(stack2.pop());
        }

        // Reverse temp2 into result (again, keeping original order)
        while (!temp2.isEmpty()) {
            result.push(temp2.pop());
        }

        return result;
    }

    public static boolean stacksIdentical(Stack<Integer> stack1, Stack<Integer> stack2) {
        if (stack1.size() != stack2.size()) {
            return false;
        }

        Stack<Integer> temp1 = new Stack<>();
        Stack<Integer> temp2 = new Stack<>();
        boolean identical = true;

        while (!stack1.isEmpty()) {
            temp1.push(stack1.pop());
            temp2.push(stack2.pop());
        }

        while (!temp1.isEmpty()) {
            int val1 = temp1.pop();
            int val2 = temp2.pop();
            stack1.push(val1); //restore original stack order.
            stack2.push(val2); //restore original stack order.

            if (val1 != val2) {
                identical = false;
                break;
            }
        }
        return identical;
    }

    public static void main(String[] args) {
        int decimalNumber = 255;
        String octalResult = decimalToOctal(decimalNumber);
        System.out.println("Decimal " + decimalNumber + " in octal is: " + octalResult);

        System.out.println("---------------------------------");
        Stack<Integer> stackA = new Stack<>();
        stackA.push(1);
        stackA.push(2);
        stackA.push(3);

        Stack<Integer> stackB = new Stack<>();
        stackB.push(4);
        stackB.push(5);
        stackB.push(6);

        System.out.println("Stack A: " + stackA);
        System.out.println("Stack B: " + stackB);

        Stack<Integer> concatenatedStack = concatenateStacks(stackA, stackB);
        System.out.println("Concatenated stack: " + concatenatedStack);

        System.out.println("---------------------------------");
        Stack<Integer> stackC = new Stack<>();
        stackC.push(7);
        stackC.push(8);
        stackC.push(9);

        Stack<Integer> stackD = new Stack<>();
        stackD.push(7);
        stackD.push(8);
        stackD.push(9);

        Stack<Integer> stackE = new Stack<>();
        stackE.push(7);
        stackE.push(8);
        stackE.push(10);
        System.out.println("Stack C: " + stackC);
        System.out.println("Stack D: " + stackD);
        System.out.println("Stack E: " + stackE);
        System.out.println("Stack C and D are identical: " + stacksIdentical(stackC, stackD));
        System.out.println("Stack C and E are identical: " + stacksIdentical(stackC, stackE));
    }
}