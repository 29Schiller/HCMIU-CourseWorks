import java.util.ArrayList;
import java.util.List;

public class Box {
    public int width;
    public int height;
    private List<Particle2D> Initial_particles;

    public Box(int width, int height) {
        this.width = width;
        this.height = height;
        this.Initial_particles = new ArrayList<>();
        // Initialize the box with 3 random particles
        for (int i = 0; i < 3; i++) {
            int x = (int) (Math.random() * (width - 2) +1);
            int y = (int) (Math.random() * (height - 2) +1);
            Particle2D particle = new Particle2D(x, y);
            Initial_particles.add(particle);
            System.out.println(particle); 
        }
    }

    // Print Border of the box
    public void printBorder() {
        // Print the top border
        for (int i = 0; i < width - 1 ; i++) {
            System.out.print("-");
        }
        System.out.println("-");

        // Print the inner grid (start): block code to visualize the point
        for (int j = 0; j < height - 2 ; j++) {
            System.out.print("|");
            for (int col = 0; col < width - 2 ; col++) {
                
                Particle2D matchingParticle = null;
                for (Particle2D particle : Initial_particles) {
                    if (col + 1 == particle.x && j + 1 == particle.y) {
                        matchingParticle = particle;
                        break;
                    }
                }
                if (matchingParticle != null){
                    System.out.print("*");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println("|");
        }
        // Print the inner grid (end): block code to visualize the point

        // Print the bottom border
        for (int i = 0; i < width - 1 ; i++) {
            System.out.print("-");
        }
        System.out.println("-");
    }

    public void addParticle() {
        int x = (int) (Math.random() * (width - 2) +1);
        int y = (int) (Math.random() * (height - 2) +1);
        Particle2D particle = new Particle2D(x, y);
        Initial_particles.add(particle);
    }

    public int countParticles() {
        return Initial_particles.size();
    }

    public List<Particle2D> getParticles() {
        return Initial_particles;
    }

    public boolean checkCollision(Particle2D p1, Particle2D p2) {
        int distance = (int) Math.sqrt(Math.pow(p1.getX() - p2.getX(), 2) + Math.pow(p1.getY() - p2.getY(), 2));
        if (distance <= 1) {
            return true;
        }
        return false;
    }

    public void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }


    public void visualize() {
        clearScreen();
    
        for (Particle2D particle : Initial_particles) {
            particle.moveRandom();
            
            if (particle.x == 0) {
                particle.move(1, 0);
            } else if (particle.x == width - 1) {
                particle.move(-1, 0);
            }

            if (particle.y == 0) {
                particle.move(0, 1);
            } else if (particle.y == height - 1) {
                particle.move(0, -1);
            }
        }
        
        for (Particle2D particle : Initial_particles) {
            System.out.println(particle);
        }

        System.out.print("-");
        for (int x = 1; x < width - 1; x++) {
            System.out.print("-");
        }
        System.out.println("-");
    
        for (int y = 1; y < height - 1; y++) {
            System.out.print("|");
            for (int x = 1; x < width - 1; x++) {
                boolean hasParticle = false;
    
                for (Particle2D particle : Initial_particles) {
                    if (particle.getX() == x && particle.getY() == y) {
                        System.out.print("*");
                        hasParticle = true;
                        break;
                    }
                }
    
                if (!hasParticle) {
                    System.out.print(" ");
                }
            }
            System.out.println("|");
        }
    
        System.out.print("-");
        for (int x = 1; x < width - 1; x++) {
            System.out.print("-");
        }
        System.out.println("-");
    
        System.out.println("Number of particles: " + countParticles());
        System.out.println();
    
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

