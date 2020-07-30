public class Tank extends Tower{

    private static String tankImagePath = "res\\images\\tank.png";
    public Tank() {
        super(tankImagePath, 1000, 150, new MusicPlayer("res\\sound\\shoot1.wav"));
    }

}
