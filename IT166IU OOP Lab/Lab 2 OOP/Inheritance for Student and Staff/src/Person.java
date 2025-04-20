public class Person {
    public String name;
    public String adress;

    public Person(String name, String adress) {
        // write our code here
        this.name = name;
        this.adress =adress;
    }

    public String getName() {
        return name;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    //Override toString() method
    @Override
    public String toString(){
        return "Person[name = " + name + ", " + "adress = " + adress + "]";
    }
}
