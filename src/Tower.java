import bagel.DrawOptions;
import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

import java.util.ArrayList;


public class Tower {

    private Image towerImage;

    private final double coolDown;
    private double coolDownRemain;

    private double rotation;
    private Point TL_location;

    private boolean isPlacing;

    private Rectangle collider;
    private double radius;


    private static MusicPlayer sound;

    private final ArrayList<Projectile> Projectiles = new ArrayList<>();

    public Tower(String towerImagePath, double coolDown, double radius, MusicPlayer sound){

        this.towerImage = new Image(towerImagePath);
        this.coolDown = coolDown;
        this.coolDownRemain = 0;
        this.radius = radius;
        this.isPlacing = true;
        this.sound = sound;
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

    public void drawTower(Point TL_location){
        setCollider(TL_location);
        this.towerImage.drawFromTopLeft(TL_location.x, TL_location.y);

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

    public void removeProjectile(ArrayList<Projectile> projectile, int index) {
        this.Projectiles.remove(index);
    }


    public MusicPlayer getSound(){
        return sound;
    }
}
