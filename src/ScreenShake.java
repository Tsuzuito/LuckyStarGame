import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class ScreenShake extends JPanel implements ActionListener {

    private static final String SHAKE = "shake";
    private static final String STOP_SHAKE = "stop shake";

    private Timer timer;

    private int screenPosX;
    private int screenPosY;

    private Window parentWindow;

    private final Random random = new Random();

    public ScreenShake(){
        timer = new Timer(4, this);

        Action userInputSHAKE = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timer.start();
            }
        };

        Action userInputSTOPSHAKE = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timer.stop();
                resetWindowPosition();
            }
        };

        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("G"), SHAKE);
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released G"), STOP_SHAKE);

        getActionMap().put(SHAKE, userInputSHAKE);
        getActionMap().put(STOP_SHAKE, userInputSTOPSHAKE);

    }

    public void setScreenCurrentLocation(Point pos){
        this.screenPosX = pos.x;
        this.screenPosY = pos.y;
    }

    private void resetWindowPosition(){
        if (parentWindow != null) {
            parentWindow.setLocation(screenPosX, screenPosY);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (parentWindow == null) {
            parentWindow = SwingUtilities.getWindowAncestor(this);
        }

        if (parentWindow != null) {
            int offsetX = random.nextInt(16);
            int offsetY = random.nextInt(16);

            parentWindow.setLocation(screenPosX + offsetX, screenPosY + offsetY);
        }

    }
}
