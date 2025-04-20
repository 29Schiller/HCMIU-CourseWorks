public class Staff extends Person {
    public String school;
    public double pay;

    public Staff(String name, String adress, String school, double pay) {
        super(name, adress);
        this.school = school;
        this.pay = pay;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public double getPay() {
        return pay;
    }

    public void setPay(double pay) {
        this.pay = pay;
    }

    //Override toString() method
    @Override
    public String toString(){
        return "Staff[Person[name = " + name + ", adress = " + adress + "], School = " + school +  ", Pay = " + pay + "]";
    
    }

    @Override
    public String getAdress() {
        // TODO Auto-generated method stub
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
