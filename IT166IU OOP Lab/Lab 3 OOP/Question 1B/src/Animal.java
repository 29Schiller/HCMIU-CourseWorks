// Abstract superclass Animal
abstract class Animal {
    private String name;

    public Animal(String nameAnimal) {
        this.name = nameAnimal;
    }

    // Abstract method move()
    public abstract void greet();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}