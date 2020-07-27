public class Apexslicer extends Enemy{

    private static String slicerImagePath = "res\\images\\apexslicer.png";

    /**
     * Constructor for Apexslicer
     * @param spawnDelay the delay of spawn time between each slicer
     */

    public Apexslicer(int spawnDelay) {
        super(spawnDelay, slicerImagePath);
        setSpeed(3/4.0);
    }
}
