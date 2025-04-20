public class Test{
    public static void main(String[] args) {
        Circle circle1 = new Circle(10);
        System.out.println(circle1.getArea());
        System.out.println(circle1.getPerimeter());
        System.out.println(circle1.toString());

        Rectangle rectangle1 = new Rectangle(10, 30);
        System.out.println(rectangle1.getArea());
        System.out.println(rectangle1.getPerimeter()); 
        System.out.println(rectangle1.toString());

    }
}