
public class Dice {
    private int point;

    public Dice( int point) {
        this.point = point;
    }

    public void roll(){
        point = (int) (Math.random() * 6 + 1);
    }

    public int getpoint() {
        return point;
    }
}   