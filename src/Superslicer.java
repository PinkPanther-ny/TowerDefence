import bagel.Image;

public class Superslicer extends Enemy{

    private static final Image superSlicerImage = new Image("res\\images\\superslicer.png");

    /**
     * Constructor for Superslicer
     * @param spawnDelay the delay of spawn time between each slicer
     */

    public Superslicer(int spawnDelay) {
        super(spawnDelay, superSlicerImage);
        setSpeed(2*3/4.0);
    }
}
