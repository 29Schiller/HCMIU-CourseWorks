// Implementation subclass BigDog
public class BigDog extends Dog {
    public BigDog(String name) {
        super(name);
    }

    @Override
    public void greet() {
        System.out.println("Wooow");
    }

    @Override
    public void greet(Dog anotherDog) {
        System.out.println("Woooooow");
    }
    
    public void greet(BigDog anotherDog) {
        System.out.println("Wooooooooow");
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