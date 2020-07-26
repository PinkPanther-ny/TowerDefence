import java.util.ArrayList;

public class EnemyFactory {

    public ArrayList<Enemy> getEnemy(String enemyType, int spawnDelay, int enemyNum){

        if(enemyType == null){
            return null;
        }

        if(enemyType.equalsIgnoreCase("SLICER")){
            ArrayList<Enemy> Slicer = new ArrayList<>();
            for (int i=0;i<enemyNum;i++){
                Slicer.add(new Slicer(spawnDelay));
            }
            return Slicer;
        }

        if(enemyType.equalsIgnoreCase("MEGASLICER")){
            ArrayList<Enemy> Megaslicer = new ArrayList<>();
            for (int i=0;i<enemyNum;i++){
                Megaslicer.add(new Megaslicer(spawnDelay));
            }
            return Megaslicer;
        }

        if(enemyType.equalsIgnoreCase("SUPERSLICER")){
            ArrayList<Enemy> Superslicer = new ArrayList<>();
            for (int i=0;i<enemyNum;i++){
                Superslicer.add(new Superslicer(spawnDelay));
            }
            return Superslicer;
        }

        if(enemyType.equalsIgnoreCase("APEXSLICER")) {
            ArrayList<Enemy> Apexslicer = new ArrayList<>();
            for (int i = 0; i < enemyNum; i++) {
                Apexslicer.add(new Apexslicer(spawnDelay));
            }
            return Apexslicer;
        }
        return null;
    }
}
