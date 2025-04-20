public class Triangle {
    private int side1, side2, side3;

    public Triangle(int side1, int side2, int side3) {
        this.side1 = side1;
        this.side2 = side2;
        this.side3 = side3;
    }

    public int getSide1() {
        return side1;
    }

    public int getSide2() {
        return side2;
    }

    public int getSide3() {
        return side3;
    }

    public String verify() {
        if (side1 + side2 <= side3 || side1 + side3 <= side2 || side2 + side3 <= side1) {
            return "Not Triangle";
        } else if (side1 == side2 && side2 == side3) {
            return "Equilateral triangle";
        } else if (side1 == side2 || side2 == side3 || side1 == side3) {
            return "Isosceles triangle";
        } else {
            return "Scalene triangle";
        }
    }
}

