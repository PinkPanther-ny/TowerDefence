import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

public class Projectile {
    private final Image projectileImage;
    private final double speed;

    private Point location;
    private double direction;
    private Enemy target;

    /*
    public Projectile(Image image, Point location, double speed, double direction){
        this.projectileImage = image;
        this.location = location;
        this.speed = speed;
        this.direction = direction;

    }*/
    public Projectile(Image image, double speed, Enemy target, Point location, double direction){
        this.projectileImage = image;
        this.speed = speed;
        this.target = target;
        this.location = location;
        this.direction = direction;
    }
    public Rectangle getCollider() {
        return this.projectileImage.getBoundingBoxAt(this.location);
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

    public void setTarget(Enemy target) {
        this.target = target;
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
