import java.util.ArrayList;

public class Wave {
    private int waveNum;
    private ArrayList<Enemy> Enemy;
    private int waveDelay = 0;
    private boolean isDelay;

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
        this.waveNum = waveNum;
        this.Enemy = Enemy;
        this.isDelay = false;
    }

    public Wave(int waveNum, int waveDelay){
        this.waveNum = waveNum;
        this.waveDelay = waveDelay;
        this.isDelay = true;
    }

}
