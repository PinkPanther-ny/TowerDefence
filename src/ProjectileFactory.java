import bagel.util.Point;

public class ProjectileFactory {
    public Projectile getProjectile(Tower tower, Enemy target, Point location, double direction){



        if (tower.getClass() == Tank.class){
            return new TankProjectile(target, location, direction);
        }
        if (tower.getClass() == SuperTank.class){
            return new SuperTankProjectile(target, location, direction);
        }

        return new TankProjectile(target, location, direction);
        // return null;
    }

}
