public class Cat extends Animal{
    

    public Cat(String nameAnimal){
        super(nameAnimal);
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

    @Override
    public void greet() {
        // TODO Auto-generated method stub
        System.out.println("Meow");
    }

}