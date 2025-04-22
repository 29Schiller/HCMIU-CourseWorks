public class Student
{
    private final String fname, lname;
    private final int grade;
    
    public Student(String fname, String lname, int grade)
    {
        this.fname = fname;
        this.lname = lname;
        this.grade = grade;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public int getGrade() {
        return grade;
    }

    @Override
    public String toString()
    {
        return fname + " " + lname + "\t" + grade;
    }
}
