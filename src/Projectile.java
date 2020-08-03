import bagel.Image;
import bagel.util.Point;

public class Projectile {
    private final Image projectileImage;
    private final double speed;

    private Point location;
    private double direction;
    private final Enemy target;

    private final double damage;

    public double getDamage() {
        return damage;
    }

    public Projectile(Image image, double speed, Enemy target, Point location, double direction, double damage){
        this.projectileImage = image;
        this.speed = speed;
        this.target = target;
        this.location = location;
        this.direction = direction;
        this.damage = damage;
    }

    public double getDirection() {
        return direction;
    }

    public void setDirection(double direction) {
        this.direction = direction;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public Enemy getTarget() {
        return target;
    }

    public void draw(){
        this.projectileImage.draw(this.location.x, this.location.y);
    }

    public double getSpeed() {
        return speed;
    }
}
