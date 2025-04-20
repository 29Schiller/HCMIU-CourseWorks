
public class Test {
    public static void main(String[] args) {
        // Test Shape
        Shape shape1 = new Shape();
        System.out.println(shape1.getColor());
        System.out.println(shape1.isFilled());
        System.out.println(shape1.toString());
        shape1.setColor("green");
        shape1.setFilled(false);
        System.out.println(shape1.getColor());
        System.out.println(shape1.isFilled());
        System.out.println(shape1.toString());
        //

        //Test Circle
        Circle circle1 = new Circle();
        System.out.println(circle1.getRadius());
        System.out.println(circle1.getArea());
        System.out.println(circle1.getRadius());

        Circle circle2 = new Circle(5);
        System.out.println(circle2.getRadius());
        System.out.println(circle2.getArea());
        System.out.println(circle2.getRadius());

        Circle circle3 = new Circle(5,"green",false);
        System.out.println(circle3.getRadius());
        System.out.println(circle3.getArea());
        System.out.println(circle3.getRadius());
        //

        //Test Rectangle
        Rectangle rectangle1 = new Rectangle();
        System.out.println(rectangle1.toString());
        System.out.println(rectangle1.getArea());
        System.out.println(rectangle1.getPerimeter());

        Rectangle rectangle2 = new Rectangle(5,10);
        System.out.println(rectangle2.toString());
        System.out.println(rectangle2.getArea());
        System.out.println(rectangle2.getPerimeter());

        Rectangle rectangle3 = new Rectangle(5,10,"green",false);
        System.out.println(rectangle3.toString());
        System.out.println(rectangle3.getArea());
        System.out.println(rectangle3.getPerimeter());
        //

        // Test Square
        Square square1 = new Square();
        System.out.println(square1.toString());
        
        Square square2 = new Square(10);
        System.out.println(square2.toString());
        
        Square square3 = new Square(10,"green",false);
        System.out.println(square3.toString());

        //
    }
}
