//********************************************************************
//  GasMileage.java       Author: Lewis/Loftus
//
//  Demonstrates the use of the Scanner class to read numeric data.
//********************************************************************

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class GasMileageEnhanced {

   public static void main(String[] args) {
       Scanner scan = new Scanner(System.in);
       List<Car> cars = new ArrayList<>();

       while (true) {
           System.out.println("Enter car info (car miles gallons) or 'done':");
           String input = scan.nextLine(); // Read the entire line

           if (input.equalsIgnoreCase("done")) {
               break; // Exit loop if user types "done"
           }

           try {
               String[] parts = input.split(" "); // Split input by spaces
               if (parts.length != 3) {
                   throw new IllegalArgumentException("Invalid input. Enter car, miles, gallons.");
               }

               String carName = parts[0];
               double miles = getValidDoubleInput(scan, "miles");
               double gallons = getValidDoubleInput(scan, "gallons");

               cars.add(new Car(carName, miles, gallons));

           } catch (IllegalArgumentException e) {
               System.out.println("Error: " + e.getMessage());
           }

       }

       // Calculate and display MPG for each car
       double totalMPG = 0;
       for (Car car : cars) {
           double mpg = car.calculateMPG();
           System.out.printf("%s: %.2f MPG\n", car.getName(), mpg);
           totalMPG += mpg;
       }

       // Calculate and display overall average MPG
       if (!cars.isEmpty()) {
           double averageMPG = totalMPG / cars.size();
           System.out.printf("\nOverall Average MPG: %.2f\n", averageMPG);
       } else {
           System.out.println("No car data entered.");
       }

       scan.close();
   }

   // Input validation method
   private static double getValidDoubleInput(Scanner scan, String fieldName) {
       while (true) {
           try {
               double value = scan.nextDouble();
               scan.nextLine(); // Consume newline left by nextDouble
               if (value <= 0) {
                   throw new IllegalArgumentException(fieldName + " must be positive.");
               }
               return value;
           } catch (java.util.InputMismatchException e) {
               System.out.println("Invalid input. Enter a number for " + fieldName + ".");
               scan.next(); // Clear the invalid input
           } catch (IllegalArgumentException e) {
               System.out.println("Error: " + e.getMessage());
           }
       }
   }

   private static class Car {
       private String name;
       private double miles;
       private double gallons;

       public Car(String name, double miles, double gallons) {
           this.name = name;
           this.miles = miles;
           this.gallons = gallons;
       }

       public String getName() {
           return name;
       }

       public double calculateMPG() {
           if (gallons <= 0) {
               throw new IllegalArgumentException("Gallons must be positive.");
           }
           return miles / gallons;
       }
   }
}