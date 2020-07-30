public class SuperTank extends Tower{

    private static String SuperTankImagePath = "res\\images\\superTank.png";

    public SuperTank() {
        super(SuperTankImagePath, 500, 200, new MusicPlayer("res\\sound\\shoot1.wav"));
    }

}
