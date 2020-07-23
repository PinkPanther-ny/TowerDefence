import bagel.DrawOptions;
import bagel.Image;

public class Enemy {

    private final Image slicer;
    private int step;

    public Enemy(int step){
        this.step = step;
        this.slicer = new Image("res/images/slicer.png");
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public void draw(Route route){

        this.slicer.draw(route.getLocation().x, route.getLocation().y, new DrawOptions().setRotation(route.getRotation()));
    }
}
