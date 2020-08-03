import bagel.Image;
import bagel.util.Point;


public class BulletProjectile extends Projectile{

    private static final Image bulletProjectileImage = new Image("res\\images\\bullet.png");

    public BulletProjectile(Enemy target, Point location, double direction) {
        super(bulletProjectileImage,15, target, location, direction, 0.8);
    }
}
