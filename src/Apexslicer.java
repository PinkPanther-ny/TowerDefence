import bagel.Image;

public class Apexslicer extends Enemy{

    private static final Image apexSlicerImage = new Image("res\\images\\apexslicer.png");

    /**
     * Constructor for Apexslicer
     * @param spawnDelay the delay of spawn time between each slicer
     */

    public Apexslicer(int spawnDelay) {
        super(spawnDelay, apexSlicerImage);
        setSpeed(3/4.0);
    }
}
