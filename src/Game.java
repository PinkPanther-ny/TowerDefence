import bagel.*;
import bagel.map.TiledMap;
import bagel.util.Point;
import bagel.util.Vector2;

public class Game extends AbstractGame {

    private final int enemyNumber = 5;
    private Enemy []enemy;
    private int totalStep;
    private Route[] path;
    private int activeNumber;
    private double spawnTime;
    private int timeScale;

    private final TiledMap map = new TiledMap("res/levels/1.tmx");;
    private final int FONT_SIZE = 48;
    private final Font conformableFont = new Font("res/fonts/conformable.otf", FONT_SIZE);
    private final DrawOptions  fontColor = new DrawOptions().setBlendColour(0,0,0);
    private final Point timeScaleLocation = new Point(0.4*Window.getWidth(),0.95*Window.getHeight());

    /**
     * Entry point for Alvin's TD game
     *
     */
    public static void main(String[] args) {
        // Create new instance of game and run it
        new Game().run();
    }

    public Route[] getWholeRoute(TiledMap map){

        Route[] path;
        int polyPointNumber = map.getAllPolylines().get(0).size();
        Point[] Polygons = new Point[polyPointNumber];
        int pixelRoundOffError = 4;  // Cannot exactly equal to polygon's position

        int index = 0;
        for (Point point : map.getAllPolylines().get(0)){
            // Access each Point of the polyline here
            Polygons[index] = point;
            index++;
        }

        // Load the whole route
        Point location = new Point(Polygons[0].x, Polygons[0].y);

        int i=0;
        totalStep = 0;
        while(i < polyPointNumber - 1){

            Vector2 normalisedVelocity = new Vector2(Polygons[i + 1].x - Polygons[i].x, Polygons[i+1].y - Polygons[i].y).normalised();

            location = new Point(location.x + normalisedVelocity.x, location.y + normalisedVelocity.y);

            if (Math.abs(location.distanceTo(Polygons[i+1])) < pixelRoundOffError) {
                i ++;
            }
            totalStep++;
        }


        path = new Route[totalStep];
        location = new Point(Polygons[0].x, Polygons[0].y);
        index = 0;
        i=0;

        while(i < polyPointNumber - 1){

            double rotationAngle = -Math.atan2(Polygons[i].y - Polygons[i+1].y,Polygons[i + 1].x - Polygons[i].x);

            Vector2 normalisedVelocity = new Vector2(Polygons[i + 1].x - Polygons[i].x, Polygons[i+1].y - Polygons[i].y).normalised();
            location = new Point(location.x + normalisedVelocity.x, location.y + normalisedVelocity.y);

            path[index] = new Route(location, rotationAngle);

            if (Math.abs(location.distanceTo(Polygons[i+1])) < pixelRoundOffError) {
                i ++;
            }
            index++;
        }

        return path;
    }
    /**
     * Setup the game
     */
    public Game(){
        // Constructor
        timeScale = 1;
        enemy = new Enemy[enemyNumber];
        for (int i=0;i<enemyNumber;i++){
            enemy[i] = new Enemy(0);
        }

        path = getWholeRoute(map);

        spawnTime = System.currentTimeMillis();
        activeNumber = 0;
    }

    /**
     * Updates the game state approximately 60 times a second, potentially reading from input.
     * @param input The input instance which provides access to keyboard/mouse state information.
     */
    @Override
    protected void update(Input input) {

        map.draw(0, 0, 0, 0, Window.getWidth(), Window.getHeight());

        if (enemy[enemyNumber-1].getStep()<totalStep) {

            spawnTime += (1/60.0) * 1000 * timeScale;
            if (spawnTime>5000 && activeNumber!=enemyNumber){
                activeNumber++;
                spawnTime = 0;

            }


            if (input.wasPressed(Keys.K) && timeScale-1>=0) {
                timeScale -= 1;
            }
            if (input.wasPressed(Keys.L)){
                timeScale += 1;
            }
            conformableFont.drawString("timescale:" + timeScale + "x", timeScaleLocation.x, timeScaleLocation.y, fontColor);

            for (int i=0;i<activeNumber;i++) {
                if(enemy[i].getStep()<totalStep) {
                    
                    enemy[i].draw(path[enemy[i].getStep()]);
                    enemy[i].setStep(enemy[i].getStep()+timeScale);
                }
            }

        } else {
            Window.close();

        }

    }
}

