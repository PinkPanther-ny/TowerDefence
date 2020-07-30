import bagel.Image;

public class Slicer extends Enemy{

    private static final Image slicerImage = new Image("res\\images\\slicer.png");

    /**
     * Constructor for Slicer
     * @param spawnDelay the delay of spawn time between each slicer
     */

    public Slicer(int spawnDelay) {
        super(spawnDelay, slicerImage);
        setSpeed(2);
    }
}
