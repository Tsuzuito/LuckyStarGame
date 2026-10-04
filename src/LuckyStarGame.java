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
    }
}
