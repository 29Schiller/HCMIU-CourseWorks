public class Courses{
    private String ID;
    private String name;
    private int Lecture_credits;
    private int Lab_credits;

    public Courses(String ID, String name, int lecture_credits, int lab_credits) {
        this.ID = ID;
        this.name = name.toUpperCase();
        this.Lecture_credits = lecture_credits;
        this.Lab_credits = lab_credits;
    }
    public String getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public int getLecture_credits() {
        return Lecture_credits;
    }

    public int getLab_credits() {
        return Lab_credits;
    }
}