import bagel.util.Point;

public class Route {

    private Point location;
    private double rotation;

    public double getRotation() {
        return rotation;
    }

    public Point getLocation() {
        return location;
    }

    public Route(Point location, double rotation) {
        this.location = location;
        this.rotation = rotation;
    }
}
