public class Superslicer extends Enemy{

    private static String slicerImagePath = "res\\images\\superslicer.png";

    /**
     * Constructor for Superslicer
     * @param spawnDelay the delay of spawn time between each slicer
     */
    public Superslicer(int spawnDelay) {
        super(spawnDelay, slicerImagePath);
    }
}
