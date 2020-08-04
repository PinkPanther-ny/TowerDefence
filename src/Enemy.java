import bagel.DrawOptions;
import bagel.Drawing;
import bagel.Image;
import bagel.util.Colour;
import bagel.util.Point;

import java.io.BufferedReader;
import java.io.FileReader;

public abstract class Enemy {

    private double health;
    private double speed;
    private double reward;
    private Image enemy;

    private double step = 0;
    private final int spawnDelay;


    private final double totalHealth;

    public Enemy(int spawnDelay){

        this.spawnDelay = spawnDelay;
        /*
        this.enemy = enemyImage;
        this.health = 1.9*health;
        this.reward = 2.2*reward;
        this.speed = 1.2 * speed;
        */
        this.readInfo();
        this.totalHealth = this.health;
    }

    public double getReward() {
        return reward;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        if (health < 0){
            this.health = 0;
        }else {
            this.health = health;
        }
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

    public int getSpawnDelay() {
        return spawnDelay;
    }

    public void draw(Route route){

        double healthBarHeight = 6;
        Drawing.drawRectangle(
                new Point(
                        route.getLocation().x - enemy.getWidth()/2,
                        route.getLocation().y - enemy.getHeight()/2 - healthBarHeight),
                enemy.getWidth() * health/totalHealth, healthBarHeight, Colour.GREEN
        );

        Drawing.drawRectangle(
                new Point(
                        route.getLocation().x - enemy.getWidth()/2 + enemy.getWidth() * health/totalHealth,
                        route.getLocation().y - enemy.getHeight()/2 - healthBarHeight),
                enemy.getWidth() * (totalHealth-health)/totalHealth, healthBarHeight, Colour.RED
        );
        /* Draw the frame */
        Drawing.drawLine(
                new Point(
                        route.getLocation().x - enemy.getWidth()/2,
                        route.getLocation().y - enemy.getHeight()/2 - healthBarHeight),
                new Point(
                        route.getLocation().x + enemy.getWidth()/2,
                        route.getLocation().y - enemy.getHeight()/2 - healthBarHeight),
                3, Colour.BLACK
        );
        Drawing.drawLine(
                new Point(
                        route.getLocation().x - enemy.getWidth()/2,
                        route.getLocation().y - enemy.getHeight()/2),
                new Point(
                        route.getLocation().x + enemy.getWidth()/2,
                        route.getLocation().y - enemy.getHeight()/2),
                3, Colour.BLACK
        );
        Drawing.drawLine(
                new Point(
                        route.getLocation().x - enemy.getWidth()/2,
                        route.getLocation().y - enemy.getHeight()/2 - healthBarHeight),
                new Point(
                        route.getLocation().x - enemy.getWidth()/2,
                        route.getLocation().y - enemy.getHeight()/2),
                3, Colour.BLACK
        );
        Drawing.drawLine(
                new Point(
                        route.getLocation().x + enemy.getWidth()/2,
                        route.getLocation().y - enemy.getHeight()/2 - healthBarHeight),
                new Point(
                        route.getLocation().x + enemy.getWidth()/2,
                        route.getLocation().y - enemy.getHeight()/2),
                3, Colour.BLACK
        );

        this.enemy.draw(route.getLocation().x, route.getLocation().y, new DrawOptions().setRotation(route.getRotation()));

    }

    private void readInfo(){

        String type = null;
        if (this instanceof Slicer){
            type = "SLICER";
        }else if (this instanceof Superslicer){
            type = "SUPERSLICER";
        }else if(this instanceof Megaslicer){
            type = "MEGASLICER";
        }else if(this instanceof Apexslicer){
            type = "APEXSLICER";
        }else if(this instanceof Zombie){
            type = "ZOMBIE";
        }else if(this instanceof Boss){
            type = "BOSS";
        }

        try (BufferedReader br =
                     new BufferedReader(new FileReader("res/levels/enemies.txt"))) {

            String text;
            String []line;

            while ((text = br.readLine()) != null) {
                text = text.replaceAll("\\s+","");
                line = text.split(",");
                if (line[0].equalsIgnoreCase(type)) {

                    health = Double.parseDouble(line[1]);
                    speed = Double.parseDouble(line[2]);
                    reward = Double.parseDouble(line[3]);
                    enemy = new Image(line[4]);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
