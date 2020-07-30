import bagel.Image;
import bagel.util.Point;

public class TankProjectile extends Projectile{

    private static Image tankProjectileImage = new Image("res\\images\\tank_projectile.png");

    public TankProjectile(Enemy target, Point location, double direction) {
        super(tankProjectileImage,4, target, location, direction);
    }
}
