import java.util.*;

public class Question1 {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            int size = sc.nextInt();
            ArrayList<Integer> list = new ArrayList<>();
            for(int i = 0; i < size; i++){
                list.add(i, sc.nextInt());
            }
   
            int query = sc.nextInt();
            for(int i = 0; i < query; i++){
                String op = sc.next();
                if(op.equals("Insert")){
                    list.add(sc.nextInt(), sc.nextInt());
                }else if(op.equals("Delete")){
                    list.remove(sc.nextInt());
                }else{
                    System.out.println("Wrong Input");
                }
            }
            for(int element : list){
                System.out.print(element + " ");
            }
        }
    }  
}
