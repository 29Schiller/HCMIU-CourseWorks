public class Cat extends Mamal {

    public Cat(String nameAnimal, String nameMamal, String nameCat) {
        super(nameAnimal, nameMamal);
        this.name = nameCat;
    }

    public void greet() {
        System.out.println("Meow");
    }

    @Override
    public String toString() {
        return "Cat[Mamal[Animal[name= " + name + "]]]";
    }

    
}