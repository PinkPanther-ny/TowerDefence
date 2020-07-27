public class Slicer extends Enemy{

    private static String slicerImagePath = "res\\images\\slicer.png";

    /**
     * Constructor for Slicer
     * @param spawnDelay the delay of spawn time between each slicer
     */
    public Slicer(int spawnDelay) {
        super(spawnDelay, slicerImagePath);
        setSpeed(2);
    }
}
