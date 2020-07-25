public class Slicer extends Enemy{

    private static final String slicerImagePath = "res/images/slicer.png";


    /**
     * Constructor for Slicer
     * @param step the location of the slicer in the whole path
     */
    public Slicer(int step) {
        super(step, slicerImagePath);
    }
}
