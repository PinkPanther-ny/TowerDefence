import bagel.DrawOptions;
import bagel.Image;
import bagel.Input;
import bagel.util.Point;
import bagel.util.Rectangle;


public class Tower {

    private Image towerImage;
    private double coolDown;

    private double rotation;
    private Point TL_location;

    private boolean isPlacing;

    private Rectangle collider;
    private double radius;

    public Tower(String towerImagePath, double coolDown, double radius){

        this.towerImage = new Image(towerImagePath);
        this.coolDown = coolDown;
        this.rotation = 0;
        this.radius = radius;
        this.TL_location = new Point(100,100);
        this.isPlacing = true;
        //this.collider = this.towerImage.getBoundingBox();
        this.collider = new Rectangle(new Point(0,0), 10,10);
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
}
