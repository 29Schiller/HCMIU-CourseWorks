public class Student extends Person {
    public String program;
    public int year;
    public int fee;
    
    public Student(String name, String adress, String program, int year, int fee) {
        super(name, adress);
        this.program = program;
        this.year = year;
        this.fee = fee;
    }
    public String getProgram() {
        return program;
    }
    
    public void setProgram(String program) {
        this.program = program;
    }
    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }
    public int getFee() {
        return fee;
    }
    public void setFee(int fee) {
        this.fee = fee;
    }
    //Override toString() method
    @Override
    public String toString(){
        return "Student[Person[name = " + name + ", adress = " + adress + "], Program = " + program +  ", year = " + year + "], fee = " + fee + "]";
    }
    @Override
    public String getAdress() {
        //TODO Auto-generated method stub
        return super.getAdress();
    }
    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return super.getName();
    }
    @Override
    public void setAdress(String adress) {
        // TODO Auto-generated method stub
        super.setAdress(adress);
    }
}