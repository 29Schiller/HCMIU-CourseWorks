class Person {
    private final String firstName;
    private final String lastName;
    private final int grade;

    //-----------------------------------------------------------
    public Person(String last, String first, int a) {   // constructor
        lastName = last;
        firstName = first;
        grade = a;
    }

    //-----------------------------------------------------------
    // Getters
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getGrade() {
        return grade;
    }
    //-----------------------------------------------------------
    public void displayPerson() {
        System.out.print("   Last name: " + lastName);
        System.out.print(", First name: " + firstName);
        System.out.println(", Grade: " + grade);
    }
}

class ArrayInOb { // ref to array a
    private final Person[] a;
    private int nElems;               // number of data items

    //--------------------------------------------------------------
    public ArrayInOb(int max) {           // constructor
        a = new Person[max];              // create the array
        nElems = 0;                       // no items yet
    }

    //--------------------------------------------------------------
    public void insert(String last, String first, int grade) { // put person into array
        a[nElems] = new Person(last, first, grade);
        nElems++;                         // increment size
    }

    //--------------------------------------------------------------
    public void display() {               // displays array contents
        for (int j = 0; j < nElems; j++)  // for each element,
            a[j].displayPerson();         // display it
    }

    //--------------------------------------------------------------
    // Method to sort by first name (Insertion Sort)
    public void sortByFirstName() {
        for (int i = 1; i < nElems; i++) {
            Person key = a[i];
            int j = i - 1;
            while (j >= 0 && a[j].getFirstName().compareTo(key.getFirstName()) > 0) {
                a[j + 1] = a[j];
                j--;
            }
            a[j + 1] = key;
        }
    }

    // Method to sort by last name (Insertion Sort)
    public void sortByLastName() {
        for (int i = 1; i < nElems; i++) {
            Person key = a[i];
            int j = i - 1;
            while (j >= 0 && a[j].getLastName().compareTo(key.getLastName()) > 0) {
                a[j + 1] = a[j];
                j--;
            }
            a[j + 1] = key;
        }
    }

    // Method to sort by grade (Insertion Sort)
    public void sortByGrade() {
        for (int i = 1; i < nElems; i++) {
            Person key = a[i];
            int j = i - 1;
            while (j >= 0 && a[j].getGrade() > key.getGrade()) {
                a[j + 1] = a[j];
                j--;
            }
            a[j + 1] = key;
        }
    }
}

public class Person_Sort {
    // Main method
    public static void main(String[] args) {
        int maxSize = 100;             // array size
        ArrayInOb arr;                 // reference to array
        arr = new ArrayInOb(maxSize);  // create the array

        arr.insert("Evans", "Patty", 24);
        arr.insert("Smith", "Doc", 59);
        arr.insert("Smith", "Lorraine", 37);
        arr.insert("Smith", "Paul", 37);
        arr.insert("Yee", "Tom", 43);
        arr.insert("Hashimoto", "Sato", 21);
        arr.insert("Stimson", "Henry", 29);
        arr.insert("Velasquez", "Jose", 72);
        arr.insert("Vang", "Minh", 22);
        arr.insert("Creswell", "Lucinda", 18);

        // Display original array
        System.out.println("Original Array:");
        arr.display();                 // display items

        // Sort by first name
        System.out.println("\nSorted by First Name:");
        arr.sortByFirstName();
        arr.display();

        // Sort by last name
        System.out.println("\nSorted by Last Name:");
        arr.sortByLastName();
        arr.display();

        // Sort by grade
        System.out.println("\nSorted by Grade:");
        arr.sortByGrade();
        arr.display();
    }
}