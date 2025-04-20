import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        scanner.close();
        //b)
        MyPair<String, Integer> pair1 = new MyPair<>("Anders", 13);

        //c)
        MyPair<String, Double> pair2 = new MyPair<>("Phoenix", 39.7);

        /* d) Assigning a value of type MyPair<String, Double> to a variable of type MyPair<String, Integer> 
            is not allowed, as the types of the second components are different.
         */

        //e)
        MyPair<String, Integer>[] grades = new MyPair[5];
        grades[0] = new MyPair<>("John", 85);
        grades[1] = new MyPair<>("Sara", 92);
        grades[2] = new MyPair<>("Mike", 78);

        //f)
        for (MyPair<String, Integer> grade : grades) {
            System.out.println(grade);
        }// Values of unassigned array elements will be null.
        
        //g)
        MyPair<MyPair<Integer, Integer>, String> appointment =
        new MyPair<>(new MyPair<>(1, 2), "Meeting with team");
        // The type of appointment.Fst.Snd is Integer.

    }

    public static class MyPair<T, U> {
        public final T Fst;
        public final U Snd;

       //Constructor
        public MyPair(T Fst,U Snd){
            this.Fst=Fst;
            this.Snd=Snd;
        }

        //h) Method Swap
        public MyPair<U, T> Swap() {
            return new MyPair<>(this.Snd, this.Fst);
        }
        public T getFst(){return Fst;}

        public U getSnd(){return Snd;}
        
        @Override
        public String toString() {
            return "(" + Fst + ", " + Snd + ")";
        } 
    }
}