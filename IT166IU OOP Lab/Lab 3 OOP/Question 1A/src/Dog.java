public class Dog extends Mamal {
    
    public Dog(String nameAnimal, String nameMamal, String nameDog) {
        super(nameAnimal, nameMamal);
        this.name = nameDog;
    }

    public void greet() {
        System.out.println("Woof");
    }

    public void greet(Dog anotherDog) {
        System.out.println("Woooof");
    }

    @Override
    public String toString() {
        return "Dog [Mamal[Animal[name= " + name + "]]]";
    }

    
}