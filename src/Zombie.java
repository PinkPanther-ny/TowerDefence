public class Zombie extends Enemy{

    private static String slicerImagePath = "res\\images\\zombie.png";

    /**
     * Constructor for Zombie
     * @param spawnDelay the delay of spawn time between each Zombie
     */

    public Zombie(int spawnDelay) {

        super(spawnDelay, slicerImagePath);
        setSpeed(1);
    }
}
