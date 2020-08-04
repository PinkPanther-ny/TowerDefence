import java.util.ArrayList;

public class Wave {
    private final int waveNumber;
    private ArrayList<Enemy> Enemy;
    private int waveDelay = 0;
    private final boolean isDelay;

    public ArrayList<Enemy> getEnemy() {
        return Enemy;
    }

    public boolean isDelay() {
        return isDelay;
    }

    public int getWaveDelay() {
        return waveDelay;
    }

    public Wave(int waveNum, ArrayList<Enemy> Enemy){
        this.waveNumber = waveNum;
        this.Enemy = Enemy;
        this.isDelay = false;
    }

    public Wave(int waveNum, int waveDelay){
        this.waveNumber = waveNum;
        this.waveDelay = waveDelay;
        this.isDelay = true;
    }

    public int getWaveNumber() {
        return waveNumber;
    }


}
