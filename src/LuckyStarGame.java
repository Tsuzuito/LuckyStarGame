import javax.swing.*;
import java.awt.*;

public class LuckyStarGame {

    public static void main(String[] args){

        JFrame frame = new JFrame("LSGame");

        PanelManager manager = new PanelManager();
        JComponent content = manager.getContainer();

        content.setPreferredSize(new Dimension(800,600));

        frame.add(content);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        Point screenPos = new Point(frame.getLocation());
        System.out.println("Window default pos: \nX: " + screenPos.x + "\nY: " + screenPos.y);

        ScreenShake shake = new ScreenShake();
        frame.add(shake);

        frame.setLocation(screenPos);

        shake.setScreenCurrentLocation(screenPos);

        //We need a JComponent because if we set the window size using frame.setSize
        //it will be the size of the ENTIRE window including the system borders rather than just the game area

        final SettingsManager settingsManager = new SettingsManager();
        SettingsOverlay settingsOverlayPane = new SettingsOverlay(settingsManager);
        frame.setGlassPane(settingsOverlayPane);
    }
}
