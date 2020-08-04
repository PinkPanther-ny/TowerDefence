import bagel.DrawOptions;
import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;


public abstract class Tower {

    private Image towerImage;

    private double coolDown;
    private double coolDownRemain;

    private double rotation;
    private Point TL_location;

    private boolean isPlacing;

    private Rectangle collider;
    private double radius;

    private int price;

    private final ArrayList<Projectile> Projectiles = new ArrayList<>();

    public Tower(){

        this.coolDownRemain = 0;
        this.isPlacing = true;

        this.readInfo();
        /*this.towerImage = new Image(towerImagePath);
        this.radius = radius;
        this.coolDown = coolDown;
        this.price = price;*/
    }

    public double getRotation() {
        return rotation;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    public Point getLocation() {
        return TL_location;
    }

    public void setLocation(Point TL_location) {
        this.TL_location = TL_location;
    }

    public void setPlacing(boolean placing) {
        isPlacing = placing;
    }

    public boolean isPlacing() {
        return isPlacing;
    }

    public void setCollider(Point point) {
        // Default set to centre point, but I need top-left point
        this.collider = this.towerImage.getBoundingBoxAt(new Point(
                point.x+this.towerImage.getWidth()*0.5,
                point.y+this.towerImage.getHeight()*0.5)
        );
    }

    public void drawTower(Point TL_location, DrawOptions drawOptions){
        setCollider(TL_location);
        this.towerImage.drawFromTopLeft(TL_location.x, TL_location.y, drawOptions);

    }

    public Rectangle getCollider() {
        return collider;
    }

    public double getRadius() {
        return radius;
    }

    public double getCoolDown() {
        return coolDown;
    }

    public double getCoolDownRemain() {
        return coolDownRemain;
    }

    public void setCoolDownRemain(double coolDownRemain) {
        this.coolDownRemain = coolDownRemain;
    }

    public ArrayList<Projectile> getProjectiles() {
        return Projectiles;
    }

    public void addProjectile(Projectile projectile) {
        this.Projectiles.add(projectile);
    }

    public int getPrice() {
        return price;
    }

    private void readInfo(){

        String type = null;
        if (this instanceof Tank){
            type = "TANK";
        }else if (this instanceof SuperTank){
            type = "SUPERTANK";
        }else if(this instanceof Fighter){
            type = "FIGHTER";
        }

        try (BufferedReader br =
                     new BufferedReader(new FileReader("res/levels/towers.txt"))) {

            String text;
            String []line;

            while ((text = br.readLine()) != null) {
                text = text.replaceAll("\\s+","");
                line = text.split(",");
                if (line[0].equalsIgnoreCase(type)) {

                    coolDown = Double.parseDouble(line[1]);
                    radius = Double.parseDouble(line[2]);
                    price = Integer.parseInt(line[3]);
                    towerImage = new Image(line[4]);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
