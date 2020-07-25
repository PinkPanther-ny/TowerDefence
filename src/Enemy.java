import bagel.DrawOptions;
import bagel.Image;


public abstract class Enemy {

    private final Image enemy;
    private int step;
    private boolean isExit;
    private boolean isDead;

    public Enemy(int step, String enemyImagePath){

        this.step = step;
        this.enemy = new Image(enemyImagePath);

    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public boolean isDead() {
        return isDead;
    }

    public boolean isExit() {
        return isExit;
    }

    public void draw(Route route){
        this.enemy.draw(route.getLocation().x, route.getLocation().y, new DrawOptions().setRotation(route.getRotation()));
    }
}
