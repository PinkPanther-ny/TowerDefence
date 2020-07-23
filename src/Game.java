import bagel.*;
import bagel.map.TiledMap;
import bagel.util.Point;
import bagel.util.Vector2;
import java.util.ArrayList;


public class Game extends AbstractGame {

    private final int enemyNumber = 5;
    private int timeDelay = 2000;
    private final Enemy []Enemy;
    private final ArrayList<Route> Path;
    private int activeNumber;
    private double spawnTime;
    private int timeScale;

    private final TiledMap map = new TiledMap("res/levels/2.tmx");
    private final int FONT_SIZE = 48;
    private final Font conformableFont = new Font("res/fonts/conformable.otf", FONT_SIZE);
    private final DrawOptions fontColor = new DrawOptions().setBlendColour(0,0,0);
    private final Point timeScaleLocation = new Point(0.4*Window.getWidth(),0.95*Window.getHeight());

    /**
     * Entry point for Alvin's TD game
     *
     */
    public static void main(String[] args) {
        // Create new instance of game and run it
        new Game().run();
    }

    public ArrayList<Route> getWholeRoute(TiledMap map){

        ArrayList<Route> Path = new ArrayList<>();
        int polyPointNumber = map.getAllPolylines().get(0).size();
        Point[] PolyPoints = new Point[polyPointNumber];
        int pixelRoundOffError = 4;  // Cannot exactly equal to polypoints location

        int index = 0;
        for (Point point : map.getAllPolylines().get(0)){
            // Access each point of the polyline here
            PolyPoints[index] = point;
            index++;
        }

        // Load the whole route
        Point location = new Point(PolyPoints[0].x, PolyPoints[0].y);

        int i=0;

        while(i < polyPointNumber - 1){

            double x = PolyPoints[i + 1].x - PolyPoints[i].x, y = PolyPoints[i+1].y - PolyPoints[i].y;

            Vector2 normVelocity = new Vector2(x, y).normalised();
            location = new Point(location.x + normVelocity.x, location.y + normVelocity.y);
            
            double rotationAngle = -Math.atan2(-y, x);

            Path.add(new Route(location, rotationAngle));

            // Make a turn at next ploypoints
            if (Math.abs(location.distanceTo(PolyPoints[i+1])) < pixelRoundOffError) {
                i ++;
            }
        }

        return Path;
    }
    /**
     * Setup the game
     */
    public Game(){
        // Constructor
        timeScale = 1;
        Enemy = new Enemy[enemyNumber];
        for (int i=0;i<enemyNumber;i++){
            Enemy[i] = new Enemy(0);
        }

        Path = getWholeRoute(map);

        activeNumber = 0;
        spawnTime = System.currentTimeMillis();
    }

    /**
     * Updates the game state approximately 60 times a second, potentially reading from input.
     * @param input The input instance which provides access to keyboard/mouse state information.
     */
    @Override
    protected void update(Input input) {

        map.draw(0, 0, 0, 0, Window.getWidth(), Window.getHeight());

        if (Enemy[enemyNumber-1].getStep()<Path.size()) {

            spawnTime += (1/60.0) * 1000 * timeScale;
            if (spawnTime>timeDelay && activeNumber!=enemyNumber){
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

            for (int i=activeNumber-1;i>=0;i--) {
                if(Enemy[i].getStep()<Path.size()) {
                    
                    Enemy[i].draw(Path.get(Enemy[i].getStep()));
                    Enemy[i].setStep(Enemy[i].getStep()+timeScale);
                }
            }

        } else {
            Window.close();
        }

    }
}

