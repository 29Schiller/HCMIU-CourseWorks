public class TestResizeableCircle {
    public static void main(String[] args){
        Circle circle1 = new Circle(10);
        System.out.println(circle1.getArea());
        System.out.println(circle1.getPerimeter());

        Resizable circle1_resize = new ResizableCircle(30);
        System.out.println(circle1_resize.toString());
        circle1_resize.resize(200);
        System.out.println(circle1_resize.toString());
        
    }
}
