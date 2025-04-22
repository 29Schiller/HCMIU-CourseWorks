/*
 * Reading student records from a file, generating Student objects, counting and averaging
 * Suggested exercises:
 * - Use grade to determine the type of the student: excellent (> 89), ok [60,89], and failure (< 60)
 * - Define an enum type {excellent, ok, failure} and use it to print the student type
 * - Do counting and averaging within each student type (excellent, ok, and failure)
 * - Count students by using a static variable in class Student
 */
import java.io.*;
import java.util.*;

public class Students {
    public static void main(String[] args) throws IOException {
        String first_name, last_name;
        int grade, total = 0, count = 0;
        double average;

        int excellentCount = 0, okCount = 0, failureCount = 0;
        int excellentTotal = 0, okTotal = 0, failureTotal = 0;

        int numberOfStudents = 0;
        List<Student> studentList = new ArrayList<>(); // Store student objects
        List<String> lines = new ArrayList<>();

        // Read all lines into a List
        try (Scanner fileScanner = new Scanner(new File("students.txt"))) {
            while (fileScanner.hasNextLine()) {
                lines.add(fileScanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error: students.txt not found.");
            return;
        }

        // Iterate using a for loop
        for (String _ : lines) {
            numberOfStudents++;
        }

        try (Scanner fileInput = new Scanner(new File("students.txt"))) {
            for (int i = 0; i < numberOfStudents; i++) {
                if (fileInput.hasNext()) {
                    first_name = fileInput.next();
                    last_name = fileInput.next();
                    grade = fileInput.nextInt();

                    Student st = new Student(first_name, last_name, grade);
                    studentList.add(st); // Add to list

                    total += grade;
                    count++;

                    if (grade > 89) {
                        excellentCount++;
                        excellentTotal += grade;
                    } else if (grade >= 60) {
                        okCount++;
                        okTotal += grade;
                    } else {
                        failureCount++;
                        failureTotal += grade;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error: students.txt not found.");
            return;
        } catch (InputMismatchException e) {
            System.err.println("Error: students.txt is improperly formatted.");
            return;
        }

        // Sort by first name
        studentList.sort(Comparator.comparing(Student::getFname));
        System.out.println("\nStudents sorted by first name:");
        for (Student st : studentList) {
            System.out.println(st);
        }

        // Sort by last name
        studentList.sort(Comparator.comparing(Student::getLname));
        System.out.println("\nStudents sorted by last name:");
        for (Student st : studentList) {
            System.out.println(st);
        }

        // Sort by grade
        studentList.sort(Comparator.comparingInt(Student::getGrade).reversed());
        System.out.println("\nStudents sorted by grade (descending):");
        for (Student st : studentList) {
            System.out.println(st);
        }

        average = (double) total / count;
        System.out.println("\nThere are " + count + " students with average grade " + average);

        System.out.println("Excellent Students: " + excellentCount + ", Average: " + (excellentCount > 0 ? (double) excellentTotal / excellentCount : 0));
        System.out.println("OK Students: " + okCount + ", Average: " + (okCount > 0 ? (double) okTotal / okCount : 0));
        System.out.println("Failure Students: " + failureCount + ", Average: " + (failureCount > 0 ? (double) failureTotal / failureCount : 0));
    }
}