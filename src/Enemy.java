import bagel.DrawOptions;
import bagel.Image;


public abstract class Enemy {

    private final Image enemy;
    private int step = 0;
    private int spawnDelay;

    public Enemy(int spawnDelay, String enemyImagePath){

        this.spawnDelay = spawnDelay;
        this.enemy = new Image(enemyImagePath);

    }

    /**
     *
     * @return the index (step) of the enemy in the path array
     */
    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public int getSpawnDelay() {
        return spawnDelay;
    }

    public void setTimeDelay(int spawnDelay) {
        this.spawnDelay = spawnDelay;
    }

    public void draw(Route route){
        this.enemy.draw(route.getLocation().x, route.getLocation().y, new DrawOptions().setRotation(route.getRotation()));
    }
}
