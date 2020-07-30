import bagel.Image;
import bagel.util.Point;

public class SuperTankProjectile extends Projectile{

    private static Image superTankProjectileImage = new Image("res\\images\\supertank_projectile.png");

    public SuperTankProjectile(Enemy target, Point location, double direction) {
        super(superTankProjectileImage,10, target, location, direction);
    }
}
