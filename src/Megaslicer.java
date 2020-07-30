import bagel.Image;

public class Megaslicer extends Enemy{

    private static final Image megaSlicerImage = new Image("res\\images\\megaslicer.png");

    /**
     * Constructor for Megaslicer
     * @param spawnDelay the delay of spawn time between each slicer
     */

    public Megaslicer(int spawnDelay) {
        super(spawnDelay, megaSlicerImage);
        setSpeed(2*3/4.0);
    }
}
