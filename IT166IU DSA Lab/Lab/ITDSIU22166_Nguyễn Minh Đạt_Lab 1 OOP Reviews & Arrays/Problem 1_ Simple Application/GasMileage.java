//********************************************************************
//  GasMileage.java       Author: Lewis/Loftus
//
//  Demonstrates the use of the Scanner class to read numeric data.
//********************************************************************

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GasMileage{
   //-----------------------------------------------------------------
   //  Calculates fuel efficiency based on values entered by the
   //  user.
   //-----------------------------------------------------------------
    public static void main(String[] args) {
        List<Double> mpgList = new ArrayList<>();
        try (Scanner scan = new Scanner(System.in)) {
            int carCount = 0;
            
            while (true) {
                System.out.print("Enter miles (or 'q' to quit): ");
                String milesInput = scan.next();
               
                if (milesInput.equalsIgnoreCase("q")) {
                    break;
                }
                
                double miles;
                try {miles = Double.parseDouble(milesInput); // Parse the input string directly
                    if (miles < 0) {
                        System.out.println("Miles must be non-negative. Please try again.");
                        continue;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid miles input. Please enter a number or 'q'.");
                    continue;
                }
                
                double gallons;
                while (true) {
                    System.out.print("Enter gallons used: ");
                    try {
                        gallons = scan.nextDouble();
                        if (gallons <= 0) {
                            System.out.println("Gallons must be positive. Please try again.");
                        } else {
                            break;
                        }
                    } catch (java.util.InputMismatchException e) {
                        System.out.println("Invalid gallons input. Please enter a number.");
                        scan.next(); // Clear the invalid input
                    }
                }
                
                double mpg = (double) miles / gallons;
                mpgList.add(mpg);
                System.out.println("Car " + (++carCount) + " MPG: " + String.format("%.2f", mpg));
            }
            
            if (!mpgList.isEmpty()) {
                double totalMpg = 0;
                for (double mpg : mpgList) {
                    totalMpg += mpg;
                }
                double averageMpg = totalMpg / mpgList.size();
                System.out.println("\nOverall Average MPG: " + String.format("%.2f", averageMpg));
            }
        }
    }
}