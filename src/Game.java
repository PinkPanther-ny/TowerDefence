import bagel.*;
import bagel.map.TiledMap;
import bagel.util.Point;
import bagel.util.Vector2;

public class Game extends AbstractGame {
    private final int polyPointNumber;
    private final TiledMap map;
    private final Image slicer;
    private final Point[] Polygons;
    private final int pixelRoundOffError = 4;  // Cannot exactly equal to polygon's position
    private int timeScale;
    private Point []path;
    private double []rotation;
    private Enemy []enemy;
    private final int enemyNumber = 5;
    private int activeNumber;
    private double spawnTime;
    private int totalStep;

    private final Font conformableFont;
    private static final int FONT_SIZE = 48;
    private final DrawOptions  blackColor = new DrawOptions().setBlendColour(0,0,0);
    private Point timeScaleFontLocation = new Point(0.4*Window.getWidth(),0.95*Window.getHeight());
    /**
     * Entry point for Bagel game
     *
     * Explore the capabilities of Bagel: https://people.eng.unimelb.edu.au/mcmurtrye/bagel-doc/
     */
    public static void main(String[] args) {
        // Create new instance of game and run it
        new Game().run();
    }

    /**
     * Setup the game
     */
    public Game(){
        // Constructor
        enemy = new Enemy[enemyNumber];

        for (int i=0;i<enemyNumber;i++){
            enemy[i] = new Enemy(0);
        }

        map = new TiledMap("res/levels/1.tmx");
        slicer = new Image("res/images/slicer.png");
        conformableFont = new Font("res/fonts/conformable.otf", FONT_SIZE);
        timeScale = 1;

        polyPointNumber = map.getAllPolylines().get(0).size();
        Polygons = new Point[polyPointNumber];
        int index = 0;
        for (Point point : map.getAllPolylines().get(0)){
            // Access each Point of the polyline here
            Polygons[index] = point;
            index++;
        }

        // Load the whole route
        Point location = new Point(Polygons[0].x, Polygons[0].y);

        Point tmpLocation = new Point(Polygons[0].x, Polygons[0].y);
        
        int i=0;
        totalStep = 0;
        while(i < polyPointNumber - 1){

            Vector2 normalisedVelocity = new Vector2(Polygons[i + 1].x - Polygons[i].x, Polygons[i+1].y - Polygons[i].y).normalised();

            tmpLocation = new Point(tmpLocation.x + normalisedVelocity.x, tmpLocation.y + normalisedVelocity.y);

            if (Math.abs(tmpLocation.distanceTo(Polygons[i+1])) < pixelRoundOffError) {
                i ++;
            }
            totalStep++;
        }

        path = new Point[totalStep];
        rotation = new double[totalStep];
        index = 0;
        i=0;

        while(i < polyPointNumber - 1){

            double rotationAngle = -Math.atan2(Polygons[i].y - Polygons[i+1].y,Polygons[i + 1].x - Polygons[i].x);

            Vector2 normalisedVelocity = new Vector2(Polygons[i + 1].x - Polygons[i].x, Polygons[i+1].y - Polygons[i].y).normalised();
            location = new Point(location.x + normalisedVelocity.x, location.y + normalisedVelocity.y);

            path[index] = location;
            rotation[index] = rotationAngle;

            if (Math.abs(location.distanceTo(Polygons[i+1])) < pixelRoundOffError) {
                i ++;
            }
            index++;
        }

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

            conformableFont.drawString("timescale:" + timeScale + "x", timeScaleFontLocation.x, timeScaleFontLocation.y, blackColor);
            if (input.wasPressed(Keys.K) && timeScale-1>=0) {
                timeScale -= 1;
            }
            if (input.wasPressed(Keys.L)){
                timeScale += 1;
            }

            DrawOptions angle = new DrawOptions();



            for (int i=0;i<activeNumber;i++) {
                if(enemy[i].getStep()<totalStep) {
                    angle.setRotation(rotation[enemy[i].getStep()]);
                    slicer.draw(path[enemy[i].getStep()].x, path[enemy[i].getStep()].y, angle);
                    enemy[i].setStep(enemy[i].getStep()+timeScale);
                }
            }

        } else {

            Window.close();

        }

    }
}

