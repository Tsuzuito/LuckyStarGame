import javax.swing.*;
import java.awt.*;

public class PanelManager {

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel container = new JPanel(cardLayout);

    public PanelManager(){
        container.add(new MenuPanel(this), "menu");
        container.add(new GameSelectPanel(this), "gameSelect");
        container.add(new SnakeGamePanel(this), "gameSnake");
        container.add(new ScorePanel(this), "scoreMenu");
    }

    public JPanel getContainer() {
        return container;
    }

    public void show(String name) {
        cardLayout.show(container, name);
    }
}