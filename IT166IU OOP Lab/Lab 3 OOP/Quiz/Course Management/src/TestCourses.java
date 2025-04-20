import java.util.Objects;
import java.util.Scanner;
import java.util.HashSet;

public class TestCourses {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {

            HashSet<String> ListID = new HashSet<>();

            System.out.print("Enter the numbers of courses: ");
            int coursesQuantity = scanner.nextInt();
            for (int i = 0; i < coursesQuantity; i++) {
                

                // ID of the course
                System.out.print("Enter the ID of the Course: ");
                String ID = scanner.next();
                if (Objects.requireNonNull(ID, "ID cannot be null").isEmpty()) {
                    throw new IllegalArgumentException("ID must not be empty.");
                }
                while (true) {
                    if (ListID.contains(ID)) {
                        System.out.println("Warning. The ID must be unique.");
                        System.out.print("Enter the ID of the Course: ");
                        ID = scanner.next();
                    } else {
                        ListID.add(ID);
                        System.out.println("Added new ID.");
                        break;
                    }
                }
                
                // Name of the course
                System.out.print("Enter the name of the Course: ");
                String name = scanner.next();
                
                // Lecture's Credits
                System.out.print("Enter the Lecture's credits of the Course: ");
                int lecture_credits = scanner.nextInt();
                while (lecture_credits <=0) {
                    System.out.println("Warning. The credits must be greater than zero. Please try again.");
                    System.out.print("Enter the Lecture's credits of the Course: ");
                    lecture_credits = scanner.nextInt();
                }              
                
                // Lab's credits
                System.out.print("Enter the Lab's credits of the Course: ");
                int lab_credits = scanner.nextInt();
                while (lab_credits <=0) {
                    System.out.println("Warning. The credits must be greater than zero. Please try again.");
                    System.out.print("Enter the Lab's credits of the Course: ");
                    lab_credits = scanner.nextInt();
                }
                
                Courses course = new Courses(ID, name, lecture_credits, lab_credits);
                System.out.printf("This is the Course %d%n",i+1);
                System.out.println("Course's name: " + course.getName());
                System.out.println("ID: " + course.getID());
                System.out.println("Lecture Credits: " + course.getLecture_credits());
                System.out.println("Lab Credits: " + course.getLab_credits());
            }
        }
    }
}