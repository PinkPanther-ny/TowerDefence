import bagel.DrawOptions;
import bagel.Image;
import bagel.util.Rectangle;

import java.util.ArrayList;

public abstract class Enemy {

    private final Image enemy;
    private double step = 0;
    private int spawnDelay;
    private double speed;

    public Enemy(int spawnDelay, Image enemyImage){

        this.spawnDelay = spawnDelay;
        this.enemy = enemyImage;
    }


    /**
     *
     * @return the index (step) of the enemy in the path array
     */
    public double getStep() {
        return step;
    }

    public void setStep(double step) {
        this.step = step;
    }

    public double getSpeed() { return speed; }

    public void setSpeed(double speed) { this.speed = speed; }

    public int getSpawnDelay() {
        return spawnDelay;
    }

    public void setTimeDelay(int spawnDelay) {
        this.spawnDelay = spawnDelay;
    }

    public void draw(Route route){
        this.enemy.draw(route.getLocation().x, route.getLocation().y, new DrawOptions().setRotation(route.getRotation()));
    }

    public Rectangle getCollider(ArrayList<Route> Path) {
        return this.enemy.getBoundingBoxAt(Path.get((int)step).getLocation());
    }
}
