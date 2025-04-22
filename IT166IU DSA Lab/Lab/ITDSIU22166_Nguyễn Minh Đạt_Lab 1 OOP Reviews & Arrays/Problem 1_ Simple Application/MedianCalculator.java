import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class MedianCalculator {

    public static double calculateMedian(List<? extends Number> numbers) {
        if (numbers == null || numbers.isEmpty()) {
            throw new IllegalArgumentException("Input list cannot be null or empty.");
        }

        List<Double> doubleNumbers = new ArrayList<>(); // Use Double to handle both int and float
        for (Number number : numbers) {
            doubleNumbers.add(number.doubleValue());
        }

        Collections.sort(doubleNumbers); // Sort the list

        int size = doubleNumbers.size();
        if (size % 2 == 0) { // Even number of elements
            double middle1 = doubleNumbers.get(size / 2 - 1);
            double middle2 = doubleNumbers.get(size / 2);
            return (middle1 + middle2) / 2.0;
        } else { // Odd number of elements
            return doubleNumbers.get(size / 2);
        }
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            List<Double> inputList = new ArrayList<>();

            System.out.println("Enter the numbers (enter 'done' to finish):");

            while (true) {
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("done")) {
                    break;
                }
                try {
                    double number = Double.parseDouble(input);
                    inputList.add(number);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a number or 'done'.");
                }
            }

            if (inputList.isEmpty()) {
                try {
                    double median = calculateMedian(inputList);
                    System.out.println("Median: " + median);
                } catch (IllegalArgumentException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        }
    }
}