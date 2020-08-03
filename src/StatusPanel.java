import bagel.*;
import bagel.map.TiledMap;
import bagel.util.Colour;
import bagel.util.Point;
import bagel.util.Rectangle;
import java.util.ArrayList;

public class StatusPanel {

    private final Point timeScaleLocation = new Point(200, 752);
    private int pauseTimeScale = -1;
    private final double soldTowerPrice = 0.8;  //percentage

    private final static int FONT_SIZE = 16;
    private final static Font panelFont = new Font("res\\fonts\\DejaVuSans-Bold.ttf", FONT_SIZE);

    private final static int GG_SIZE = 56;
    private final static Font GGFont = new Font("res\\fonts\\DejaVuSans-Bold.ttf", GG_SIZE);
    private final static int GG_SIZE2 = 30;
    private final static Font GGFont2 = new Font("res\\fonts\\DejaVuSans-Bold.ttf", GG_SIZE2);
    private final static String GG1 = "Game Over!";
    private final static String GG2 = "Tower defence game designed by Alvin\nPress ENTER to exit....";

    private final static int INS_SIZE = 14;
    private final static Font insFont = new Font("res\\fonts\\DejaVuSans-Bold.ttf", INS_SIZE);
    private final static DrawOptions black = new DrawOptions().setBlendColour(Colour.BLACK);
    private final static DrawOptions red = new DrawOptions().setBlendColour(Colour.RED);
    private final static DrawOptions green = new DrawOptions().setBlendColour(0.3,0.9,0.4);

    /* SCALE SIGN */
    private final static Image continueSign = new Image("res\\images\\continue1.png");
    private final static Image pauseSign = new Image("res\\images\\pause1.png");
    private final static Image fastforwardSign = new Image("res\\images\\ff.png");
    private final static Image fastbackwardSign = new Image("res\\images\\fb.png");

    private final static Point PS_Location = new Point(940, 20);
    private final static Point FBS_Location = new Point(150, 725);
    private final static Point FFS_Location = new Point(322, 725);

    /* SHOP PANEL */
    private final static Image shopPanel = new Image("res\\images\\buypanel.png");
    private final static Point SPP_Location = new Point(0,0); //shop panel location

    private final static Image shopTank = new Image("res\\images\\tank.png");
    private final static Image shopSuperTank = new Image("res\\images\\supertank.png");
    //private final static Image shopAirSupport = new Image("res\\images\\airsupport.png");
    private final static Image shopAirSupport = new Image("res\\images\\fighter.gif");

    private final static Point SP_Location1 = new Point(36,10);
    private final static Point SP_Location2 = new Point(176,10);
    private final static Point SP_Location3 = new Point(316,10);
    private final static Point SP_Location4 = new Point(48,90);

    private final static String instruction1 = "Press L/K to speed up / down the game";
    private final static String instruction2 = "Press N to set the timescale to Normal 1x";
    private final static String instruction3 = "Press P to pause / continue the game";
    private final static String instruction4 = "Press S to skip delay time between waves";
    private final static Point INS_Location1 = new Point(560,20);
    private final static Point INS_Location2 = new Point(560,40);
    private final static Point INS_Location3 = new Point(560,60);
    private final static Point INS_Location4 = new Point(560,80);
    private final static Point INS_Location5 = new Point(800,752);

    private final static Font MONEY_FONT = new Font("res\\fonts\\DejaVuSans-Bold.ttf", 25);
    private final static Font MONEY_FONT2 = new Font("res\\fonts\\DejaVuSans-Bold.ttf", 45);

    private final static Rectangle SP_1 = shopTank.getBoundingBoxAt(new Point(
            SP_Location1.x + 0.5*shopTank.getWidth(),
            SP_Location1.y + 0.5*shopTank.getHeight())
    );

    private final static Rectangle SP_2 = shopSuperTank.getBoundingBoxAt(new Point(
            SP_Location2.x + 0.5*shopSuperTank.getWidth(),
            SP_Location2.y + 0.5*shopSuperTank.getHeight())
    );

    private final static Rectangle SP_3 = shopAirSupport.getBoundingBoxAt(new Point(
            SP_Location3.x + 0.5*shopAirSupport.getWidth(),
            SP_Location3.y + 0.5*shopAirSupport.getHeight())
    );

    public void drawPanel(Input input, int timeScale, double delayRemain, Game game){
        Rectangle PS_Rect = pauseSign.getBoundingBoxAt(new Point(PS_Location.x + pauseSign.getWidth()*0.5, PS_Location.y + pauseSign.getHeight()*0.55));
        Rectangle FFS_Rect = fastforwardSign.getBoundingBoxAt(new Point(FFS_Location.x + fastforwardSign.getWidth()*0.5, FFS_Location.y + fastforwardSign.getHeight()*0.5));
        Rectangle FBS_Rect = fastbackwardSign.getBoundingBoxAt(new Point(FBS_Location.x + fastbackwardSign.getWidth()*0.5, FBS_Location.y + fastbackwardSign.getHeight()*0.5));


        /* -------------------------- SHOPPING PANEL --------------------------------*/


        shopPanel.drawFromTopLeft(SPP_Location.x, SPP_Location.y);


        shopTank.drawFromTopLeft(SP_Location1.x, SP_Location1.y);
        if (game.getMoney()>=new Tank().getPrice()) {
            MONEY_FONT.drawString(new Tank().getPrice() + "$", SP_Location4.x, SP_Location4.y);
        }else{
            MONEY_FONT.drawString(new Tank().getPrice() + "$", SP_Location4.x, SP_Location4.y, new DrawOptions().setBlendColour(Colour.RED));
        }

        shopSuperTank.drawFromTopLeft(SP_Location2.x, SP_Location2.y);
        if (game.getMoney()>=new SuperTank().getPrice()) {
            MONEY_FONT.drawString(new SuperTank().getPrice() + "$", SP_Location4.x + 136, SP_Location4.y);
        }else{
            MONEY_FONT.drawString(new SuperTank().getPrice() + "$", SP_Location4.x + 136, SP_Location4.y, new DrawOptions().setBlendColour(Colour.RED));
        }

        shopAirSupport.drawFromTopLeft(SP_Location3.x, SP_Location3.y);
        if (game.getMoney()>=new Fighter().getPrice()) {
            MONEY_FONT.drawString(new Fighter().getPrice() + "$", SP_Location4.x + 285, SP_Location4.y);
        }else{
            MONEY_FONT.drawString(new Fighter().getPrice() + "$", SP_Location4.x + 285, SP_Location4.y, new DrawOptions().setBlendColour(Colour.RED));
        }

        MONEY_FONT2.drawString((int)game.getMoney() + " $", 420, 50);

        /* -------------------------- INSTRUCTION PANEL --------------------------------*/


        insFont.drawString(instruction1,INS_Location1.x, INS_Location1.y);
        insFont.drawString(instruction2,INS_Location2.x, INS_Location2.y);
        insFont.drawString(instruction3,INS_Location3.x, INS_Location3.y);
        insFont.drawString(instruction4,INS_Location4.x, INS_Location4.y);

        if (game.getHealth() <= 5){
            panelFont.drawString("Health : " + game.getHealth(), INS_Location5.x, INS_Location5.y, new DrawOptions().setBlendColour(Colour.RED));
        }else {
            panelFont.drawString("Health : " + game.getHealth(), INS_Location5.x, INS_Location5.y);
        }

        fastbackwardSign.drawFromTopLeft(FBS_Location.x, FBS_Location.y);
        fastforwardSign.drawFromTopLeft(FFS_Location.x, FFS_Location.y);

        placingTower(input, game);
        drawProjectile(game);

        if (input.wasPressed(Keys.N)) {
            game.setTimeScale(1);  // Normal speed
            pauseTimeScale = -1;
        }

        // is not pause, so is valid to modify timescale using K and L
        if (pauseTimeScale==-1) {
            if(timeScale!=0) {
                pauseSign.drawFromTopLeft(PS_Location.x, PS_Location.y);
            }
            if (    (input.wasPressed(Keys.K)||
                    (input.wasPressed(MouseButtons.LEFT) && FBS_Rect.intersects(input.getMousePosition())))
                    && game.getTimeScale() - 1 >= 0) {
                game.setTimeScale(game.getTimeScale() - 1);
            }

            if (input.wasPressed(Keys.L)||
                    (input.wasPressed(MouseButtons.LEFT) && FFS_Rect.intersects(input.getMousePosition()) )) {
                game.setTimeScale(game.getTimeScale() + 1);
            }
        }
        if (timeScale>1){
            panelFont.drawString("timescale>>" + timeScale + "x", timeScaleLocation.x, timeScaleLocation.y, green);
        }else if (timeScale==1){
            panelFont.drawString("timescale  :" + timeScale + "x", timeScaleLocation.x, timeScaleLocation.y, black);
        }else{
            continueSign.drawFromTopLeft(PS_Location.x, PS_Location.y);
            panelFont.drawString("Pause", timeScaleLocation.x, timeScaleLocation.y, red);
        }

        if (input.wasPressed(Keys.P) || (input.wasPressed(MouseButtons.LEFT) && PS_Rect.intersects(input.getMousePosition()))) {
            if (timeScale == 0 && pauseTimeScale==-1){
                game.setTimeScale(1);
            }else{
                if (pauseTimeScale==-1) {
                    pauseTimeScale = game.getTimeScale();
                    game.setTimeScale(0);
                }else{
                    game.setTimeScale(pauseTimeScale);
                    pauseTimeScale = -1;
                }
            }
        }

        //  Wave information

        panelFont.drawString("Wave: " + game.getWaveNumber(), timeScaleLocation.x - 190, timeScaleLocation.y, black);
        if (delayRemain > 0) {
            if (delayRemain < 3000) {
                panelFont.drawString("Next wave comes in " + (double) Math.round(delayRemain / 100) / 10 + " seconds..", timeScaleLocation.x + 300, timeScaleLocation.y, red);
            } else {
                panelFont.drawString("Next wave comes in " + Math.round(delayRemain / 1000) + " seconds..", timeScaleLocation.x + 300, timeScaleLocation.y, black);
            }
        }

    }

    public void placingTower(Input input, Game game){

        int alignToGrid = 16;
        Point mouse = input.getMousePosition();
        Point alignPoint = new Point(alignToGrid *(int)(mouse.x/ alignToGrid), alignToGrid *(int)(mouse.y/ alignToGrid));

        ArrayList<Tower> tower = game.getTowers();

        if(input.wasReleased(MouseButtons.LEFT)) {
            if (tower.size() == 0) {
                addNewTower(mouse, game);

            } else {

                Tower currentTower = tower.get(tower.size() - 1);
                if (!currentTower.isPlacing()) {

                    // Not isPlacing status, so add a new Tower to the list
                    addNewTower(mouse, game);

                } else if (validRectangleOnMap(currentTower, mouse, game.getMap(), game.getBlockedProperty(), tower) &&
                        currentTower.isPlacing()) {
                    game.setMoney(game.getMoney() - currentTower.getPrice());
                    //  IsPlacing status, mouse is clicked, and position valid, so set it down.
                    currentTower.setPlacing(false);
                    currentTower.setLocation(alignPoint);
                }

            }
        }
        drawTower(alignPoint, game.getTowers(), game.getMap(), game.getBlockedProperty(), input, game);
        //drawTower(mouse, game.getTowers(), game.getMap(), game.getBlockedProperty(), input);

    }
    public void addNewTower(Point mouse, Game game){
        if (SP_1.intersects(mouse) && game.getMoney() >= new Tank().getPrice()) {
            game.setTowers(new Tank());

        } else if (SP_2.intersects(mouse) && game.getMoney() >= new SuperTank().getPrice()) {
            game.setTowers(new SuperTank());
        } else if (SP_3.intersects(mouse) && game.getMoney() >= new Fighter().getPrice()) {
            // Constructor for AirPlane
            game.setTowers(new Fighter());
        }

    }
    public void drawTower(Point mouse, ArrayList<Tower> Towers, TiledMap map, String BLOCKED_PROPERTY, Input input, Game game){
        ArrayList<Tower> towerSellList = new ArrayList<>();
        for(Tower aTower: Towers){
            if (!aTower.isPlacing()){


                if(input.isDown(MouseButtons.LEFT) && aTower.getCollider().intersects(mouse) &&
                        !(Towers.get(Towers.size()-1).isPlacing())){
                    drawRange(aTower, mouse, null , input);    // Show the Range of fire

                    if (input.isDown(MouseButtons.RIGHT)) {
                        towerSellList.add(aTower);
                    }

                }
                // Draw tower and search if enemy inside the shooting radius and rotate tower towards the leading enemy in the path
                aTower.setRotation( getAngle(aTower, game.getEnemy(), game.getPath()) );
                aTower.drawTower(aTower.getLocation(), new DrawOptions().setRotation(aTower.getRotation()));

                /* Add shooting projectile */
                shootEnemy(aTower, game.getEnemy(), game.getPath(), game.getTimeScale());


            }else if(aTower.isPlacing()){

                if(input.wasReleased(MouseButtons.RIGHT)){Towers.remove(Towers.size() - 1); break;}// Cancel placing

                // Valid Location to draw tower
                drawRange(aTower, mouse, validRectangleOnMap(aTower, mouse, map, BLOCKED_PROPERTY, Towers) , input);
            }
        }

        for (Tower soldTower: towerSellList){
            game.setMoney(game.getMoney() + soldTowerPrice * soldTower.getPrice());
            Towers.remove(soldTower);
        }

    }

    private boolean validPoint(Point point, TiledMap map, String BLOCKED_PROPERTY) {
        boolean invalidX = point.x <= 0 || point.x >= Window.getWidth();
        boolean invalidY = point.y <= 0 || point.y >= Window.getHeight();
        boolean outOfBounds = invalidX || invalidY;
        if (outOfBounds) {
            return false;
        }
        return !map.getPropertyBoolean((int) point.x, (int) point.y, BLOCKED_PROPERTY, false);
    }
    /**
     *  Check conner and centre of a Tower
     * @param tower The tower object that will be validated
     * @param mouse Current mouse position
     * @param BLOCKED_PROPERTY  tile customised property
     * @return boolean If the given Tower's location is valid on the map
     */
    private boolean validRectangleOnMap(Tower tower, Point mouse, TiledMap map, String BLOCKED_PROPERTY, ArrayList<Tower> Towers){

        tower.setCollider(mouse);
        Rectangle newBoundingBox = tower.getCollider();
        try {
            boolean validBR = validPoint(newBoundingBox.bottomRight(), map, BLOCKED_PROPERTY);
            boolean validTR = validPoint(newBoundingBox.topRight(), map, BLOCKED_PROPERTY);
            boolean validTL = validPoint(newBoundingBox.topLeft(), map, BLOCKED_PROPERTY);
            boolean validBL = validPoint(newBoundingBox.bottomLeft(), map, BLOCKED_PROPERTY);
            boolean validCT = validPoint(newBoundingBox.centre(), map, BLOCKED_PROPERTY);

            boolean validHBR = validPoint(getMidPoint(newBoundingBox.bottomRight(),newBoundingBox.centre()), map, BLOCKED_PROPERTY);
            boolean validHTR = validPoint(getMidPoint(newBoundingBox.topRight(),newBoundingBox.centre()), map, BLOCKED_PROPERTY);
            boolean validHTL = validPoint(getMidPoint(newBoundingBox.topLeft(),newBoundingBox.centre()), map, BLOCKED_PROPERTY);
            boolean validHBL = validPoint(getMidPoint(newBoundingBox.bottomLeft(),newBoundingBox.centre()), map, BLOCKED_PROPERTY);

            return validBL && validBR && validTL && validTR && validCT && validHBR && validHTR && validHTL && validHBL &&
                    validRectangleCollider(tower, Towers);
        }catch(Exception NullPointerException){return false;}

    }
    private boolean validRectangleCollider(Tower tower, ArrayList<Tower> Towers){
        Rectangle currentBoundingBox = tower.getCollider();

        for(Tower anotherTower:Towers) {
            if (anotherTower.getCollider().intersects(currentBoundingBox) && !tower.equals(anotherTower)) {
                return false;
            }
        }
        return true;
    }
    private Point getMidPoint(Point p1, Point p2){
        return new Point((p1.x+p2.x)/2.0, (p1.y+p2.y)/2.0);
    }

    private double getAngle(Tower tower, ArrayList<Enemy> enemies, ArrayList<Route> Path){
        double rotationAngle = 2*Math.PI;
        double x, y;
        Point p1 = tower.getCollider().centre();
        Point p2;
        int maxStep=0;
        for(Enemy enemy: enemies){
            if((int) enemy.getStep()<Path.size()) {

                if (Path.get((int) enemy.getStep()).getLocation().distanceTo(tower.getLocation()) <= tower.getRadius()) {
                    p2 = Path.get((int) enemy.getStep()).getLocation();
                    x = p2.x - p1.x;
                    y = p2.y - p1.y;

                    if ((int) enemy.getStep() > maxStep) {
                        maxStep = (int) enemy.getStep();
                        rotationAngle = -Math.atan2(-y, x) + Math.PI / 2;
                    }
                }
            }
        }
        return rotationAngle == 2*Math.PI?tower.getRotation():rotationAngle;
    }

    private void shootEnemy(Tower tower, ArrayList<Enemy> enemies, ArrayList<Route> Path, int timeScale){
        Point towerCentre = tower.getCollider().centre();
        double towerColliderRadius = tower.getCollider().bottomRight().y - tower.getCollider().centre().y;

        int maxStep = 0;
        Enemy farEnemy = null;
        for(Enemy enemy: enemies){

            if ((int) enemy.getStep()<Path.size() && Path.get((int) enemy.getStep()).getLocation().distanceTo(tower.getLocation()) <= tower.getRadius()) {
                if (maxStep<(int) enemy.getStep()){
                    maxStep = (int) enemy.getStep();
                    farEnemy = enemy;
                }
            }
        }

        if(tower.getCoolDownRemain()<=0 && farEnemy!=null){

            Point spawnProjectile = new Point(towerCentre.x + towerColliderRadius * Math.cos(tower.getRotation() - Math.PI / 2), towerCentre.y + towerColliderRadius * Math.sin(tower.getRotation() - Math.PI / 2));

            // Engage
            //Drawing.drawCircle(spawnProjectile, 4, Colour.RED);

            //game.getProjectiles().add(new Projectile())
            tower.addProjectile(new ProjectileFactory().getProjectile(tower, farEnemy, spawnProjectile, 0));
            tower.setCoolDownRemain(tower.getCoolDown());

        }

        tower.setCoolDownRemain( tower.getCoolDownRemain() - 1/60.0 * 1000 * timeScale );
    }

    private void drawProjectile(Game game){

        ArrayList<Tower> Towers = game.getTowers();
        ArrayList<Enemy> Enemy = game.getEnemy();

        for(Tower tower: Towers) {
            if(tower.isPlacing()){continue;}
            ArrayList<Projectile> projectilesRemoveList = new ArrayList<>();
            for (Projectile projectile :tower.getProjectiles()){

                if (Enemy.contains( projectile.getTarget() )){

                    // If already hit
                    //if (projectile.getCollider().intersects( projectile.getTarget().getCollider(game.getPath()) )){
                    if ( (int)projectile.getTarget().getStep() >= game.getPath().size() ||
                            (projectile.getLocation()).distanceTo( (game.getPath().get( (int)projectile.getTarget().getStep() )).getLocation()) < 15*game.getTimeScale() ){

                        // if enemy dead, remove
                        // always remove projectile if hit
                        projectile.getTarget().setHealth(projectile.getTarget().getHealth() - projectile.getDamage());
                        if(projectile.getTarget().getHealth()<=0) {

                            game.setMoney( game.getMoney() + projectile.getTarget().getReward());
                            Enemy.remove(projectile.getTarget());
                            game.setActiveNumber(game.getActiveNumber() - 1);
                        }

                        projectilesRemoveList.add(projectile);
                        continue;
                    }

                    // If still tracking enemy
                    //  Update projectile location and direction to a target

                    // Enemy is alive
                    // shoot em

                    projectile.setLocation( new Point(
                            projectile.getLocation().x + Math.pow(game.getTimeScale(), 0.5) * projectile.getSpeed()*( Math.cos(projectile.getDirection())) ,
                            projectile.getLocation().y + Math.pow(game.getTimeScale(), 0.5) * projectile.getSpeed()*( Math.sin(projectile.getDirection())) )
                            );


                    Point p1 = projectile.getLocation();
                    Point p2 = game.getPath().get((int)projectile.getTarget().getStep()).getLocation();

                    projectile.setDirection(-Math.atan2(p1.y - p2.y, p2.x - p1.x));


                }else {
                    //  Update projectile location and direction without a target
                    projectile.setLocation( new Point(
                            projectile.getLocation().x + game.getTimeScale() * projectile.getSpeed()*( Math.cos(projectile.getDirection())) ,
                            projectile.getLocation().y + game.getTimeScale() * projectile.getSpeed()*( Math.sin(projectile.getDirection())) )
                    );

                    if(
                            projectile.getLocation().x < 0 || projectile.getLocation().x > Window.getWidth() ||
                            projectile.getLocation().y < 0 || projectile.getLocation().y > Window.getHeight()
                    ){
                        projectilesRemoveList.add(projectile);
                    }

                }

                projectile.draw();
            }

            tower.getProjectiles().removeAll(projectilesRemoveList);

        }

    }

    public void drawGG(){

        GGFont.drawString(GG1, Window.getWidth()/2.0 - GGFont.getWidth(GG1)/2.0, Window.getHeight()/2.0);
        GGFont2.drawString(GG2, Window.getWidth()/2.0 - GGFont.getWidth(GG1)/2.0, Window.getHeight()/2.0 +50);

    }

    public void drawRange(Tower tower, Point mouse, Boolean isValid, Input input){
        if (isValid==null) {
            Drawing.drawCircle(tower.getCollider().centre(), tower.getRadius(), new Colour(0.1328,0.1328,0.5429, 0.5));

            String sellString = "Hold and right click to sell at :  ";
            String sellString2 = Math.round(tower.getPrice()*soldTowerPrice) + "$";
            insFont.drawString(sellString, input.getMouseX() - 0.5 * insFont.getWidth(sellString + sellString2), input.getMouseY() - 25, new DrawOptions().setBlendColour(1,1,1,0.7));
            insFont.drawString(sellString2, input.getMouseX() - 0.5 * insFont.getWidth(sellString + sellString2) + insFont.getWidth(sellString), input.getMouseY() - 25, new DrawOptions().setBlendColour(0.2,1,0.2,0.7));

        }else if (isValid) {
            // Valid Location
            Drawing.drawCircle(tower.getCollider().centre(), tower.getRadius(), new Colour(0.1328,0.5429,0.1328, 0.5));

            tower.drawTower(mouse, new DrawOptions().setBlendColour(1,1,1,0.45));
        }else{
            // Cannot place tower
            Drawing.drawCircle(tower.getCollider().centre(), tower.getRadius(), new Colour(0.5429,0.1328,0.1328, 0.5));
            tower.drawTower(mouse, new DrawOptions().setBlendColour(1,0.15,0.15,0.45));
        }

        // DEBUG draw the collider
        // Drawing.drawRectangle(mouse, tower.getCollider().right()-tower.getCollider().left(),tower.getCollider().bottom()-tower.getCollider().top(),Colour.BLACK);
    }


}
