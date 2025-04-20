import java.util.ArrayList;
import java.util.List;

public class MyList<T> {
    private List<T> values;

    public MyList(){
        values= new ArrayList<>();
    }

    void add(T value){
        values.add(value);
    }
    void remove(T value){
        values.remove(value);
    }
    @Override
    public String toString() {
        return "("+values+")";
    } 
}
