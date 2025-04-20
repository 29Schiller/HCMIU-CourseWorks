import java.util.ArrayList;

class TestPersonn {
    public static void main(String[] args) {
        ArrayList<Student> students = new ArrayList<>();
        ArrayList<Staff> staffs = new ArrayList<>();

        Student studentOne = new Student("Dat", "281/3 Binh Thanh", "IT", 2022, 200);
        students.add(studentOne);

        Student studentTwo = new Student("Phu", "284/5 Loi Hung", "CS", 2022, 200);
        students.add(studentTwo);

        Student studentThree = new Student("Thien", "33/281 D5", "DS", 2022, 200);
        students.add(studentThree);

        System.out.println(studentOne.toString());
        System.out.println(studentTwo.toString());
        System.out.println(studentThree.toString());
        System.out.println("The number of students are: " + students.size());

        studentOne.setAdress("308 duong so 3");
        System.out.println("The latest address of student one is: " + studentOne.getAdress());

        Staff staffOne = new Staff("Nam", "A2.302", "IU", 200);
        staffs.add(staffOne);

        Staff staffTwo = new Staff("Hieu", "A2.207", "IU", 250);
        staffs.add(staffTwo);

        System.out.println(staffOne.toString());
        System.out.println(staffTwo.toString());
        System.out.println("The number of staff are: " + staffs.size());

        staffOne.setAdress("LA1.607");
        System.out.println("The latest address of staff one is: " + staffOne.getAdress());
    }
}