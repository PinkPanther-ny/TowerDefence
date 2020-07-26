import bagel.*;
import bagel.map.TiledMap;
import bagel.util.Point;
import bagel.util.Vector2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;


public class Game extends AbstractGame {

    private final ArrayList<Route> Path;
    private final ArrayList<Wave> Wave;
    private int waveNum;
    private final static String waveFilePath = "res\\levels\\waves.txt";
    private int activeNumber;
    private double spawnTime;
    private double delayTime;
    private int timeScale;

    private final TiledMap map = new TiledMap("res\\levels\\2.tmx");
    private final int FONT_SIZE = 48;
    private final Font conformableFont = new Font("res\\fonts\\conformable.otf", FONT_SIZE);
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

    /**
     *
     * @param map the map.tmx file which consist a polyline
     * @return an arraylist of step location on the path of the map
     */
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
            double rotationAngle = -Math.atan2(-y, x);
            Vector2 normVelocity = new Vector2(x, y).normalised();
            location = new Point(location.x + normVelocity.x, location.y + normVelocity.y);

            Path.add(new Route(location, rotationAngle));

            // Make a turn at next ploypoints
            if (Math.abs(location.distanceTo(PolyPoints[i+1])) < pixelRoundOffError) {
                i ++;
            }
        }
        return Path;
    }

    public ArrayList<Wave> getWaveInfo(String fileName){

        ArrayList<Wave> allWave = new ArrayList<>();
        try (BufferedReader br =
                     new BufferedReader(new FileReader(fileName))) {

            String text;
            String []line;
            EnemyFactory enemyFactory = new EnemyFactory();

            while ((text = br.readLine()) != null) {

                line = text.split(",");
                if (line.length==5) {

                    ArrayList<Enemy> enemySubWave = enemyFactory.getEnemy(
                            line[3],                        // Enemy name,
                            Integer.parseInt(line[4]),      // Enemy spawn time delay,
                            Integer.parseInt(line[2])       // Enemy number
                    );

                    allWave.add( new Wave(
                            Integer.parseInt(line[0]), // Wave number
                            enemySubWave               // Enemy array object
                            )
                    );

                }else{// add a delay wave
                    allWave.add( new Wave(
                            Integer.parseInt(line[0]), // Wave number
                            Integer.parseInt(line[2])  // Wave delay time
                            )
                    );
                }
                //Process each wave info line
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return allWave;
    }


    /**
     * Setup the game
     */
    public Game(){
        // Constructor
        timeScale = 15;
        Wave = getWaveInfo(waveFilePath);
        Path = getWholeRoute(map);
        waveNum = 0;
        activeNumber = 1;
        spawnTime = 0;
        delayTime = 0;
    }

    /**
     * Updates the game state approximately 60 times a second, potentially reading from input.
     * @param input The input instance which provides access to keyboard/mouse state information.
     */
    @Override
    protected void update(Input input) {

        map.draw(0, 0, 0, 0, Window.getWidth(), Window.getHeight());

        if (waveNum < Wave.size() && !Wave.get(waveNum).isDelay()) {


            ArrayList<Enemy> enemy = Wave.get(waveNum).getEnemy();

            int enemyNum = enemy.size();
            double timeDelay = enemy.get(0).getSpawnDelay();

            if (enemy.get(enemyNum-1).getStep() < Path.size()) {

                spawnTime += (1/60.0) * 1000 * timeScale;
                if (spawnTime>timeDelay && activeNumber!=enemyNum){

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
                    if((enemy.get(i)).getStep() < Path.size()) {
                        (enemy.get(i)).draw( Path.get( enemy.get(i).getStep() ) );
                        (enemy.get(i)).setStep(enemy.get(i).getStep()+timeScale);
                    }
                }


            }else{

                activeNumber = 0;
                spawnTime = 0;
                waveNum++;

            }

        }else{

            if (!(waveNum < Wave.size())){
                // End game
                Window.close();
            }else {
                // Wait delay
                double remain = Wave.get(waveNum).getWaveDelay() - delayTime;
                conformableFont.drawString("timescale:" + timeScale + "x", timeScaleLocation.x, timeScaleLocation.y, fontColor);
                conformableFont.drawString("Next wave comes in: " + (double) Math.round(remain / 100) / 10 + "seconds", timeScaleLocation.x + 200, timeScaleLocation.y, fontColor);
                delayTime += (1 / 60.0) * 1000 * timeScale;
                if (delayTime > Wave.get(waveNum).getWaveDelay() || input.wasPressed(Keys.S)) {
                    waveNum++;
                    delayTime = 0;
                }

                if (input.wasPressed(Keys.K) && timeScale - 1 >= 0) {
                    timeScale -= 1;
                }
                if (input.wasPressed(Keys.L)) {
                    timeScale += 1;
                }
            }

        }

    }
}

