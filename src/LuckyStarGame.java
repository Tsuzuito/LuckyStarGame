import javax.swing.*;

public class LuckyStarGame {

    public static void main(String[] args) {

        JFrame frame = new JFrame("LSGame");

        PanelManager manager = new PanelManager();
        frame.add(manager.getContainer());

        frame.setSize(800,600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
