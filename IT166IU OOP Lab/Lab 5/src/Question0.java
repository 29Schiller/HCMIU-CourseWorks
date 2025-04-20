import java.util.*;

public class Question0 {
    
    public static void main(String[] args) {
        //0.a
        System.out.println("Hello, World.");
        System.out.println("Hello, Java.");

        //0.b
        Scanner scan = new Scanner(System.in);
        int a = scan.nextInt();
        int b = scan.nextInt();
        int c = scan.nextInt();
        

        //print 42,100,125
        System.out.println(a);
        System.out.println(b);
        System.out.println(c);

        //0.c
        int N = scan.nextInt();
        if(N % 2 == 0){
            if (2 <= N && N <= 5) {
                System.out.println("Not Weird");
            }
            else if (6 <= N && N <= 20) {
                System.out.println("Weird");
            } else {
                System.out.println("Not Weird");
            }
        }
        else{
            System.out.println("Weird");
        }

        //0.d
        int N_d = scan.nextInt();
        for (int index = 0; index < 10; index++) {
            System.out.println(N_d + " x " + (index+1) + " = " + N_d*(index+1));
        }

        //0.e
        int N_e = scan.nextInt();
        String s=String.valueOf(N_e);
        if (N_e == Integer.parseInt(s)) {
            System.out.println("Good job");
        } else {
            System.out.println("Wrong answer.");
        }

        //0.f
        int n = scan.nextInt();
        int[] a = new int[n];
        for (int i =0;i <n;i++) {
            int N_f = scan.nextInt();
            a[i] = N_f;
        }
        for (int i = 0; i < a.length; i++) {
            System.out.println(a[i]);
        }

        //0.g: Optional (Inheritance)
        class Animal{
            void walk(){
                System.out.println("I am walking");
            }
        }
        class Bird extends Animal{
            void fly(){
                System.out.println("I am flying");
            }
            void sing(){
                System.out.println("I am singing");
            }
        }
        Bird bird = new Bird();
        bird.walk();
        bird.fly();
        bird.sing();

        //0.h: Optional (Abstract)
        abstract class Book{
            String title;
            abstract void setTitle(String s);
            String getTitle(){
                return title;
            }
        }
        class MyBook extends Book{
            @Override
            String getTitle() {
                return super.getTitle();
            }

            @Override
            void setTitle(String s) {
                this.title = s;
            }
        }
        String title= scan.nextLine();
		MyBook new_novel=new MyBook();
		new_novel.setTitle(title);
		System.out.println("The title is: "+new_novel.getTitle());
        scan.close();
    }
}
