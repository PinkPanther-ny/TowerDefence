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
    private final ArrayList<Tower> Towers;
    private ArrayList<Enemy> enemy;
    private int waveNum;
    private final static String waveFilePath = "res\\levels\\waves.txt";
    private int activeNumber;
    private double spawnTime;
    private double delayTime;
    private int timeScale = 1;

    private final TiledMap map = new TiledMap("res\\levels\\2.tmx");
    private static final String BLOCKED_PROPERTY = "blocked";
    private final StatusPanel statusPanel = new StatusPanel();

    /**
     * Entry point for Alvin's TD game
     *
     */
    public static void main(String[] args) {
        // Create new instance of game and run it
        Game game = new Game();
        game.run();
    }

    public int getTimeScale() {
        return timeScale;
    }

    public void setTimeScale(int timeScale) {
        this.timeScale = timeScale;
    }

    public void gameExit(){Window.close();System.exit(0);}


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

                }else{  // add a delay wave
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
        Wave = getWaveInfo(waveFilePath);
        Path = getWholeRoute(map);
        waveNum = 0;
        activeNumber = 1;
        spawnTime = 0;
        delayTime = 0;
        Towers = new ArrayList<>();
        enemy = new ArrayList<>();
    }
    public ArrayList<Tower> getTowers(){
        return this.Towers;
    }

    public ArrayList<Enemy> getEnemy() {
        return enemy;
    }

    public void setTowers(Tower towers) {
        Towers.add(towers);
    }

    public ArrayList<Route> getPath() {
        return Path;
    }

    public TiledMap getMap() {
        return map;
    }

    public String getBlockedProperty() {
        return BLOCKED_PROPERTY;
    }

    /**
     * Updates the game state approximately 60 times a second, potentially reading from input.
     * @param input The input instance which provides access to keyboard/mouse state information.
     */
    @Override
    protected void update(Input input) {

        map.draw(0, 0, 0, 0, Window.getWidth(), Window.getHeight());
        if (waveNum < Wave.size() && !Wave.get(waveNum).isDelay()) {


            enemy = Wave.get(waveNum).getEnemy();

            int enemyNum = enemy.size();
            double timeDelay = enemy.get(0).getSpawnDelay();

            if (enemy.get(enemyNum-1).getStep() < Path.size()) {

                spawnTime += (1/60.0) * 1000 * timeScale;
                if (spawnTime>timeDelay && activeNumber!=enemyNum){
                    activeNumber++;
                    spawnTime = 0;
                }

                for (int i=activeNumber-1;i>=0;i--) {
                    if((enemy.get(i)).getStep() < Path.size()) {
                        (enemy.get(i)).draw( Path.get( (int)enemy.get(i).getStep()) );
                        (enemy.get(i)).setStep(enemy.get(i).getStep()+timeScale*enemy.get(i).getSpeed());
                    }
                }
                statusPanel.drawPanel(input, timeScale,-1,this);
            }else{
                activeNumber = 0;
                spawnTime = 0;
                waveNum++;
            }

        }else{

            if (!(waveNum < Wave.size())){
                // End game
                gameExit();
            }else {
                // Wait delay
                double delayRemain = Wave.get(waveNum).getWaveDelay() - delayTime;

                delayTime += (1 / 60.0) * 1000 * timeScale;
                if (delayRemain < 0 || input.wasPressed(Keys.S)) {
                    waveNum++;
                    delayTime = 0;
                }

                statusPanel.drawPanel(input, timeScale, delayRemain, this);

            }

        }



    }
}

