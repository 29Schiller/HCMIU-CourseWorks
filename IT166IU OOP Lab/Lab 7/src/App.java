public class App {
    public static void main(String[] args) {
        int myArray[] = new int[7];
        try {
            System.out.println(myArray[9]); // Potential ArrayIndexOutOfBoundsException
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("The element 9 does not exist!");
        }
    }
}