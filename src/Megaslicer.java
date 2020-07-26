public class Megaslicer extends Enemy{

    private static String slicerImagePath = "res\\images\\megaslicer.png";

    /**
     * Constructor for Megaslicer
     * @param spawnDelay the delay of spawn time between each slicer
     */
    public Megaslicer(int spawnDelay) {
        super(spawnDelay, slicerImagePath);
    }
}
