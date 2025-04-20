import java.util.Arrays;
import java.util.Scanner;

public class ArrayToNumberConverter {

    public static int convertArrayToNumber(int[] array) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("Input array cannot be null or empty.");
        }
        int number = 0;
        boolean firstDigitNegative = false; // Track if the first digit is negative

        for (int i = 0; i < array.length; i++) {
            int digit = array[i];

            if (i == 0 && digit < 0) { // Check for negative first digit
                firstDigitNegative = true;
                digit = Math.abs(digit); // Make it positive for processing
            }

            if (digit < 0 || digit > 9) {
                throw new IllegalArgumentException("Invalid digit: " + digit + " at index " + i + ". Digits must be between 0 and 9 (or negative only for the first digit).");
            }

            number = number * 10 + digit;
        }

        if (firstDigitNegative) {
            number = -number; // Apply negative sign if the first digit was negative
        }
        return number;
    }

    public static int convertHexArrayToNumber(char[] array) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("Input array cannot be null or empty.");
        }

        int number = 0;
        for (int i = 0; i < array.length; i++) {
            char c = array[i];
            int digit;

            if (Character.isDigit(c)) {
                digit = Character.getNumericValue(c);
            } else if (c >= 'A' && c <= 'F') {
                digit = c - 'A' + 10;
            } else if (c >= 'a' && c <= 'f') {
                digit = c - 'a' + 10;
            } else {
                throw new IllegalArgumentException("Invalid hexadecimal character: " + c + " at index " + i);
            }

            number = number * 16 + digit;
        }
        return number;
    }

    public static void main(String[] args) {

        try (Scanner sc = new Scanner(System.in)) {
            System.out.println("Enter the size of the decimal array: ");
            int decimalSize = sc.nextInt();
            int[] decimalArray = new int[decimalSize];

            System.out.println("Enter decimal array elements (0-9): ");
            for (int i = 0; i < decimalSize; i++) {
                decimalArray[i] = sc.nextInt();
            }

            System.out.println("Enter the size of the hexadecimal array: ");
            int hexSize = sc.nextInt();
            char[] hexArray = new char[hexSize];

            System.out.println("Enter hexadecimal array elements (0-9, A-F): ");
            for (int i = 0; i < hexSize; i++) {
                String input = sc.next();
                if (input.length() != 1) {
                    throw new IllegalArgumentException("Invalid hex input. Enter single character (0-9, A-F)");
                }
                hexArray[i] = input.toUpperCase().charAt(0);
            }

            try {
                int decimalNumber = convertArrayToNumber(decimalArray);
                System.out.println("Decimal Conversion: " + Arrays.toString(decimalArray) + " -> " + decimalNumber);
            } catch (IllegalArgumentException e) {
                System.out.println("Decimal Conversion Error: " + e.getMessage());
            }

            try {
                int hexNumber = convertHexArrayToNumber(hexArray);
                System.out.println("Hexadecimal Conversion: " + Arrays.toString(hexArray) + " -> " + Integer.toHexString(hexNumber).toUpperCase() + " (Decimal: " + hexNumber + ")");
            } catch (IllegalArgumentException e) {
                System.out.println("Hexadecimal Conversion Error: " + e.getMessage());
            }

        }
    }
}