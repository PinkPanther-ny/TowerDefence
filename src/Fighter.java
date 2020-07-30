public class Fighter extends Tower{

    private static String fighterImagePath = "res\\images\\Fighter.gif";

    public Fighter() {
        super(fighterImagePath, 600, 120, new MusicPlayer("res\\sound\\shoot1.wav"));
    }

}
