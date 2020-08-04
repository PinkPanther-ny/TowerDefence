import bagel.util.Point;

public class Route {

    private final Point location;
    private final double rotation;

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
