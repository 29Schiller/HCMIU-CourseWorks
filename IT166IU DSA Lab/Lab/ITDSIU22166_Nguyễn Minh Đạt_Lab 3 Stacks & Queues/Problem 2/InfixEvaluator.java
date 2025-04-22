import java.util.*;

public class InfixEvaluator {
    // Stack class for operators and numbers
    static class Stack<T> {
        private final ArrayList<T> items = new ArrayList<>();
        
        public void push(T item) { items.add(item); }
        public T pop() { return items.remove(items.size() - 1); }
        public T peek() { return items.get(items.size() - 1); }
        public boolean isEmpty() { return items.isEmpty(); }
    }

    // Check operator precedence
    private static int precedence(char op) {
        return switch (op) {
            case '+', '-' -> 1;
            case '*', '/' -> 2;
            default -> 0;
        };
    }

    // Perform arithmetic operation
    private static double applyOperator(double a, double b, char op) {
        switch (op) {
            case '+' -> {
                return a + b;
            }
            case '-' -> {
                return a - b;
            }
            case '*' -> {
                return a * b;
            }
            case '/' -> {
                return a / b;
            }
            default -> throw new IllegalArgumentException("Invalid operator: " + op);
        }
    }

    // Basic evaluation for single digits
    public static double evaluateBasic(String expression) {
        Stack<Double> values = new Stack<>();
        Stack<Character> operators = new Stack<>();

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            if (Character.isWhitespace(c)) continue;

            if (Character.isDigit(c)) {
                values.push((double)(c - '0'));
            } else if (c == '(') {
                operators.push(c);
            } else if (c == ')') {
                while (!operators.isEmpty() && operators.peek() != '(') {
                    double b = values.pop();
                    double a = values.pop();
                    char op = operators.pop();
                    values.push(applyOperator(a, b, op));
                }
                operators.pop(); // Remove '('
            } else if (c == '+' || c == '-' || c == '*' || c == '/') {
                while (!operators.isEmpty() && operators.peek() != '(' && 
                       precedence(operators.peek()) >= precedence(c)) {
                    double b = values.pop();
                    double a = values.pop();
                    char op = operators.pop();
                    values.push(applyOperator(a, b, op));
                }
                operators.push(c);
            }
        }

        while (!operators.isEmpty()) {
            double b = values.pop();
            double a = values.pop();
            char op = operators.pop();
            values.push(applyOperator(a, b, op));
        }

        return values.pop();
    }

    // Extended evaluation for multiple digits and variables
    public static double evaluateExtended(String expression, Map<String, Double> variables) {
        Stack<Double> values = new Stack<>();
        Stack<Character> operators = new Stack<>();
        int i = 0;

        while (i < expression.length()) {
            char c = expression.charAt(i);

            if (Character.isWhitespace(c)) {
                i++;
                continue;
            }

            // Handle multiple digit numbers
            if (Character.isDigit(c)) {
                StringBuilder num = new StringBuilder();
                while (i < expression.length() && Character.isDigit(expression.charAt(i))) {
                    num.append(expression.charAt(i));
                    i++;
                }
                values.push(Double.valueOf(num.toString()));
                continue;
            }
            
            // Handle variables
            if (Character.isLetter(c)) {
                StringBuilder var = new StringBuilder();
                while (i < expression.length() && Character.isLetterOrDigit(expression.charAt(i))) {
                    var.append(expression.charAt(i));
                    i++;
                }
                String varName = var.toString();
                if (!variables.containsKey(varName)) {
                    throw new IllegalArgumentException("Variable " + varName + " not defined");
                }
                values.push(variables.get(varName));
                continue;
            }

            switch (c) {
                case '(' -> operators.push(c);
                case ')' -> {
                    while (!operators.isEmpty() && operators.peek() != '(') {
                        double b = values.pop();
                        double a = values.pop();
                        char op = operators.pop();
                        values.push(applyOperator(a, b, op));
                    }   operators.pop();
                }
                case '+', '-', '*', '/' -> {
                    while (!operators.isEmpty() && operators.peek() != '(' &&
                            precedence(operators.peek()) >= precedence(c)) {
                        double b = values.pop();
                        double a = values.pop();
                        char op = operators.pop();
                        values.push(applyOperator(a, b, op));
                    }   operators.push(c);
                }
                default -> {
                }
            }
            i++;
        }

        while (!operators.isEmpty()) {
            double b = values.pop();
            double a = values.pop();
            char op = operators.pop();
            values.push(applyOperator(a, b, op));
        }

        return values.pop();
    }

    public static void main(String[] args) {
        // Test basic evaluation
        try (Scanner scanner = new Scanner(System.in)) {
            // Test basic evaluation
            System.out.println("Enter a single-digit infix expression (e.g., 2+3*(4-1)): ");
            String basicExpr = scanner.nextLine();
            try {
                double result = evaluateBasic(basicExpr);
                System.out.println("Result: " + result);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
            // Test extended evaluation
            System.out.println("\nEnter a multi-digit/variable infix expression (e.g., 123+56*abc-1): ");
            String extendedExpr = scanner.nextLine();
            Map<String, Double> variables = new HashMap<>();
            System.out.println("Enter variable values (name=value, one per line, empty line to finish):");
            while (true) {
                String input = scanner.nextLine();
                if (input.isEmpty()) break;
                String[] parts = input.split("=");
                variables.put(parts[0].trim(), Double.valueOf(parts[1].trim()));
            }
            try {
                double result = evaluateExtended(extendedExpr, variables);
                System.out.println("Result: " + result);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }    
        }
    }
}