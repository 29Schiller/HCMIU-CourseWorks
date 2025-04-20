public class Rectangle {
    private int width;
    private int height;

    public Rectangle(int width, int height) {
        if (width < 0) {
            System.out.println("Warning. Input width is negative. Setting width to 1.");
            this.width = 1;
        } else {
            this.width = width;
        }
        if (height < 0) {
            System.out.println("Warning. Input height is negative. Setting height to 1.");
            this.height = 1;
        } else {
            this.height = height;
        }
    }

    public int getWidth() {
    return width;
    }
    public int getHeight() {
    return height;
    }
    public void visualize() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                System.out.print("*");
            }
            System.out.println();
        }
    }
    
}
