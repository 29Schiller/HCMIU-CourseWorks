public class Mamal extends Animal {
    public String name;

    public Mamal(String nameAnimal, String nameMamal) {
        super(nameAnimal);
        this.name = nameMamal;
    }

    @Override
    public String toString() {
        return "Mamal[Animal[name= " + name + "]]";
    }

    
}

