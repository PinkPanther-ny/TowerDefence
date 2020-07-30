import bagel.Image;

public class Zombie extends Enemy{

    private static final Image zombieImage = new Image("res\\images\\zombie.png");

    /**
     * Constructor for Zombie
     * @param spawnDelay the delay of spawn time between each Zombie
     */

    public Zombie(int spawnDelay) {
        super(spawnDelay, zombieImage);
        setSpeed(1);
    }
}
