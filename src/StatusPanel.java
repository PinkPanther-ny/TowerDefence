import bagel.DrawOptions;
import bagel.Font;
import bagel.Keys;
import bagel.map.TiledMap;
import bagel.util.Colour;
import bagel.util.Point;
import bagel.*;
import bagel.util.Rectangle;

import java.util.ArrayList;

public class StatusPanel {

    private final Point timeScaleLocation = new Point(200, 752);
    private int pauseTimeScale = -1;

    private final static int FONT_SIZE = 16;
    private final static Font panelFont = new Font("res\\fonts\\DejaVuSans-Bold.ttf", FONT_SIZE);
    private final static DrawOptions black = new DrawOptions().setBlendColour(Colour.BLACK);
    private final static DrawOptions red = new DrawOptions().setBlendColour(Colour.RED);
    private final static DrawOptions green = new DrawOptions().setBlendColour(0.1328,0.5429,0.1328);

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
    private final static Image shopAirSupport = new Image("res\\images\\airsupport.png");
    private final static Point SP_Location1 = new Point(36,10);
    private final static Point SP_Location2 = new Point(176,10);
    private final static Point SP_Location3 = new Point(316,10);


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

        drawShop(input, game);
        fastbackwardSign.drawFromTopLeft(FBS_Location.x, FBS_Location.y);
        fastforwardSign.drawFromTopLeft(FFS_Location.x, FFS_Location.y);
        if (input.wasPressed(Keys.N)) {
            game.setTimeScale(1);  // Normal speed
            pauseTimeScale = -1;
        }
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
        if (timeScale>1){
            panelFont.drawString("timescale>>" + timeScale + "x", timeScaleLocation.x, timeScaleLocation.y, green);
        }else if (timeScale==1){
            panelFont.drawString("timescale  :" + timeScale + "x", timeScaleLocation.x, timeScaleLocation.y, black);
        }else{
            continueSign.drawFromTopLeft(PS_Location.x, PS_Location.y);
            panelFont.drawString("Pause", timeScaleLocation.x, timeScaleLocation.y, red);
        }
        if (delayRemain>0){
            if (delayRemain<3000){
                panelFont.drawString("Next wave comes in " + (double)Math.round(delayRemain/100)/10 + " seconds..", timeScaleLocation.x+300, timeScaleLocation.y, red);
            }else {
                panelFont.drawString("Next wave comes in " + Math.round(delayRemain / 1000) + " seconds..", timeScaleLocation.x + 300, timeScaleLocation.y, black);
            }
        }
    }

    public void drawShop(Input input, Game game){

        shopPanel.drawFromTopLeft(SPP_Location.x, SPP_Location.y);
        shopTank.drawFromTopLeft(SP_Location1.x, SP_Location1.y);
        shopSuperTank.drawFromTopLeft(SP_Location2.x, SP_Location2.y);
        shopAirSupport.drawFromTopLeft(SP_Location3.x, SP_Location3.y);

        Point mouse = input.getMousePosition();
        ArrayList<Tower> tower = game.getTowers();

        if(input.wasReleased(MouseButtons.LEFT)) {
            if (tower.size() == 0) {
                addNewTower(mouse, game);

            } else {

                Tower currentTower = tower.get(tower.size() - 1);
                if(validRectangle(currentTower, mouse, game.getMap(), game.getBlockedProperty())) {
                    if (!currentTower.isPlacing()) {
                        addNewTower(mouse, game);

                    } else if (currentTower.isPlacing()) {

                        currentTower.setPlacing(false);
                        currentTower.setLocation(mouse);
                    }
                }
            }
        }
        drawTower(mouse, game.getTowers(), game.getMap(), game.getBlockedProperty());


    }

    public void addNewTower(Point mouse, Game game){
        if (SP_1.intersects(mouse)) {
            game.setTowers(new Tank());

        } else if (SP_2.intersects(mouse)) {
            game.setTowers(new SuperTank());
        } else if (SP_3.intersects(mouse)) {
            // Constructor for AirPlane
            game.setTowers(new Fighter());
        }

    }


    public void drawTower(Point mouse, ArrayList<Tower> Towers, TiledMap map, String BLOCKED_PROPERTY){
        DrawOptions drawOptions;
        for(Tower aTower: Towers){
            if (!aTower.isPlacing()){
                aTower.drawTower(aTower.getLocation(), new DrawOptions().setRotation(aTower.getRotation()));
            }else if(aTower.isPlacing()){

                if (validRectangle(aTower, mouse, map, BLOCKED_PROPERTY)) {
                    // Valid Location
                    drawOptions = new DrawOptions().setBlendColour(1,1,1,0.6);
                }else{
                    // Cannot place tower
                    drawOptions = new DrawOptions().setBlendColour(1,0.3,0.3,0.6);
                }
                aTower.drawTower(mouse, drawOptions);
            }
        }
    }

    private boolean validPoint(Point point, TiledMap map, String BLOCKED_PROPERTY) {
        boolean invalidX = point.x < 0 || point.x > Window.getWidth();
        boolean invalidY = point.y < 0 || point.y > Window.getHeight();
        boolean outOfBounds = invalidX || invalidY;
        if (outOfBounds) {
            return false;
        }
        return !map.getPropertyBoolean((int) point.x, (int) point.y, BLOCKED_PROPERTY, false);
    }


    /**
     *  Check five point of a Tower
     *  Four conner and the centre
     * @param tower The tower object that will be validated
     * @param mouse Current mouse position
     * @param BLOCKED_PROPERTY  tile customised property
     * @return boolean If the given Tower's location is valid on the map
     */
    private boolean validRectangle(Tower tower, Point mouse, TiledMap map, String BLOCKED_PROPERTY){
        tower.setCollider(mouse);
        Rectangle newBoundingBox = tower.getCollider();
        boolean validBR = validPoint(newBoundingBox.bottomRight(), map, BLOCKED_PROPERTY);
        boolean validTR = validPoint(newBoundingBox.topRight(), map, BLOCKED_PROPERTY);
        boolean validTL = validPoint(newBoundingBox.topLeft(), map, BLOCKED_PROPERTY);
        boolean validBL = validPoint(newBoundingBox.bottomLeft(), map, BLOCKED_PROPERTY);
        boolean validMS = validPoint(mouse, map, BLOCKED_PROPERTY);
        return validBL && validBR && validTL && validTR && validMS;
    }
}
