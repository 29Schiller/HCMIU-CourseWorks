import java.util.Scanner;

public class Particle_Behavior {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Please give the width and height value of the box: " );
            int width = scanner.nextInt();
            int height = scanner.nextInt();
            Box box = new Box(width, height);
    
            System.out.println("Please give the number of steps: " );
            int step = scanner.nextInt();
            box.printBorder();
    
            for (int i = 0; i< step; i++){
                box.visualize();
                
                for (int j = 0; j < box.countParticles(); j++) {
                    for (int k = j + 1; k < box.countParticles(); k++) {
                        Particle2D p1 = box.getParticles().get(j);
                        Particle2D p2 = box.getParticles().get(k);
    
                        if (box.checkCollision(p1, p2)) {
                            box.addParticle();
                        }
                    }
                }
            }
        }
    }
}