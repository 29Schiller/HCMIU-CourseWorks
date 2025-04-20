import java.util.*;

public class Question2{
    public static void main(String []argh) {
        Map<String, Integer> phonebook = new HashMap<>();

        Scanner sc = new Scanner(System.in);
        int cases = sc.nextInt();
        sc.nextLine();

        for (int i = 0;i < cases; i++){
            String name = sc.nextLine();
            int phoneNumber = sc.nextInt();
            sc.nextLine();
            phonebook.put(name, phoneNumber);
        }
        while (sc.hasNext()){
            String query = sc.nextLine();
            if (phonebook.containsKey(query)){
                System.out.println(query + "=" + phonebook.get(query));
            }else {
                System.out.println("Not found");
            }
        }
        sc.close();
    }
}