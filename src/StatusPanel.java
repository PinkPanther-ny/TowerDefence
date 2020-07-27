import bagel.DrawOptions;
import bagel.Font;
import bagel.Keys;
import bagel.util.Colour;
import bagel.util.Point;
import bagel.*;
import bagel.util.Rectangle;

public class StatusPanel {

    private final static int FONT_SIZE = 16;
    private final static Font panelFont = new Font("res\\fonts\\DejaVuSans-Bold.ttf", FONT_SIZE);
    private final static DrawOptions black = new DrawOptions().setBlendColour(Colour.BLACK);
    private final static DrawOptions red = new DrawOptions().setBlendColour(Colour.RED);
    private final static DrawOptions green = new DrawOptions().setBlendColour(0.1328,0.5429,0.1328);

    private final static Image continueSign = new Image("res\\images\\continue.png");
    private final static Image pauseSign = new Image("res\\images\\pause.png");
    private final static Point PS_Location = new Point(940, 28);

    private final static Image fastforwardSign = new Image("res\\images\\ff.png");
    private final static Image fastbackwardSign = new Image("res\\images\\fb.png");
    private final static Point FBS_Location = new Point(150, 725);
    private final static Point FFS_Location = new Point(322, 725);

    private final static Rectangle PS_Rect = new Rectangle(PS_Location, 64,64);
    private final static Rectangle FFS_Rect = new Rectangle(FFS_Location, 44,44);
    private final static Rectangle FBS_Rect = new Rectangle(FBS_Location, 44,44);

    private final Point timeScaleLocation = new Point(200, 752);
    private int pauseTimeScale = -1;

    public void drawPanel(Input input, int timeScale, double delayRemain, Game game){
        fastbackwardSign.drawFromTopLeft(FBS_Location.x, FBS_Location.y);
        fastforwardSign.drawFromTopLeft(FFS_Location.x, FFS_Location.y);
        if (input.wasPressed(Keys.N)) {
            game.setTimeScale(1);  // Normal speed
            pauseTimeScale = -1;
        }
        if (pauseTimeScale==-1) {
            if(timeScale!=0) {
                pauseSign.drawFromTopLeft(PS_Location.x, PS_Location.y);
            }
            if (    (input.wasPressed(Keys.K)||
                    (input.wasPressed(MouseButtons.LEFT) && FBS_Rect.intersects(input.getMousePosition())))
                    && game.getTimeScale() - 1 >= 0) {
                game.setTimeScale(game.getTimeScale() - 1);
            }
            if (input.wasPressed(Keys.L)||
                    (input.wasPressed(MouseButtons.LEFT) && FFS_Rect.intersects(input.getMousePosition()) )) {

                game.setTimeScale(game.getTimeScale() + 1);
            }
        }
        if (input.wasPressed(Keys.P) || (input.wasPressed(MouseButtons.LEFT) && PS_Rect.intersects(input.getMousePosition()))) {
            if (timeScale == 0 && pauseTimeScale==-1){
                game.setTimeScale(1);
            }else{
                if (pauseTimeScale==-1) {
                    pauseTimeScale = game.getTimeScale();
                    game.setTimeScale(0);
                }else{
                    game.setTimeScale(pauseTimeScale);
                    pauseTimeScale = -1;
                }
            }
        }
        if (timeScale>1){
            panelFont.drawString("timescale>>" + timeScale + "x", timeScaleLocation.x, timeScaleLocation.y, green);
        }else if (timeScale==1){
            panelFont.drawString("timescale  :" + timeScale + "x", timeScaleLocation.x, timeScaleLocation.y, black);
        }else{
            continueSign.drawFromTopLeft(PS_Location.x, PS_Location.y);
            panelFont.drawString("Pause", timeScaleLocation.x, timeScaleLocation.y, red);
        }
        if (delayRemain>0){
            if (delayRemain<3000){
                panelFont.drawString("Next wave comes in " + (double)Math.round(delayRemain/100)/10 + " seconds..", timeScaleLocation.x+300, timeScaleLocation.y, red);
            }else {
                panelFont.drawString("Next wave comes in " + Math.round(delayRemain / 1000) + " seconds..", timeScaleLocation.x + 300, timeScaleLocation.y, black);
            }
        }
    }
}
