import bagel.DrawOptions;
import bagel.Image;
import bagel.Input;
import bagel.util.Point;
import bagel.util.Rectangle;


public class Tower {

    private Image towerImage;
    private double coolDown;

    private double rotation;
    private Point location;

    private boolean isPlacing;

    private Rectangle collider;

    public Tower(String towerImagePath, double coolDown){

        this.towerImage = new Image(towerImagePath);
        this.coolDown = coolDown;
        this.rotation = 0;
        this.location = new Point(100,100);
        this.isPlacing = true;
        this.collider = this.towerImage.getBoundingBox();
    }

    public double getRotation() {
        return rotation;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public void setPlacing(boolean placing) {
        isPlacing = placing;
    }

    public boolean isPlacing() {
        return isPlacing;
    }

    public void setCollider(Point point) {
        this.collider = this.towerImage.getBoundingBoxAt(new Point(
                point.x,
                point.y)
        );
    }

    public void drawTower(Point location){
        setCollider(location);
        this.towerImage.draw(location.x, location.y);

    }
    public void drawTower(Point location, DrawOptions drawOptions){
        setCollider(location);
        this.towerImage.draw(location.x, location.y, drawOptions);

    }

    public Rectangle getCollider() {
        return collider;
    }
}
