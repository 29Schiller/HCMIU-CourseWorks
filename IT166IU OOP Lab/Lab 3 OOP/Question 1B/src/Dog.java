// Implementation subclass Dog
public class Dog extends Animal {

    public Dog(String name) {
        super(name);
    }

    @Override
    public void greet() {
        System.out.println("Woof");
    }

    public void greet(Dog anotherDog) {
        System.out.println("Woooof");
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return super.getName();
    }

    @Override
    public void setName(String name) {
        // TODO Auto-generated method stub
        super.setName(name);
    }

    
}