import spaceInvaders.SpaceInvadersLogic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SpaceInvadersGamePanel extends JPanel implements ActionListener {

    private static final int IFW = JComponent.WHEN_IN_FOCUSED_WINDOW;
    private static final String MOVE_LEFT = "move left";
    private static final String MOVE_RIGHT = "move right";
    private static final String STOP_RIGHT= "stop right";
    private static final String STOP_LEFT= "stop left";

    private final PanelManager manager;
    private final GameSaveManager saveManager;

    private final int gridSize = 16;
    private final Image bgSprite;
    private Timer timer;

    private final ImageIcon shipSprite = new ImageIcon(getClass().getResource("testicon16x16.png"));

    private JButton exitButton = new JButton("Exit");

    SpaceInvadersLogic logic = new SpaceInvadersLogic();

    public SpaceInvadersGamePanel(PanelManager manager, GameSaveManager saveManager){
        this.manager = manager;
        this.saveManager = saveManager;

        bgSprite = new ImageIcon(getClass().getResource("LCGameSnakeGame.png")).getImage();

        setBackground(Color.BLACK);
        setLayout(null);

        JPanel exitPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,10 ,10));
        exitPanel.setOpaque(false);

        Dimension btnSize = new Dimension(100,50);
        exitButton.setPreferredSize(btnSize);
        exitButton.addActionListener(this);

        exitPanel.setBounds(0, 0, 120, 70);
        exitPanel.add(exitButton);

        add(exitPanel);

        //-------------
        timer = new Timer(100, this);

        Action userInputRIGHT = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logic.setMovingRight();
            }
        };

        Action userInputLEFT = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logic.setMovingLeft();
            }
        };

        Action userInputReleaseLEFT = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logic.stopMovingLeft();
            }
        };

        Action userInputReleaseRIGHT = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logic.stopMovingRight();
            }
        };

        getInputMap(IFW).put(KeyStroke.getKeyStroke("D"), MOVE_RIGHT);
        getInputMap(IFW).put(KeyStroke.getKeyStroke("A"), MOVE_LEFT);
        getInputMap(IFW).put(KeyStroke.getKeyStroke("released D"), STOP_RIGHT);
        getInputMap(IFW).put(KeyStroke.getKeyStroke("released A"), STOP_LEFT);

        getActionMap().put(MOVE_RIGHT, userInputRIGHT);
        getActionMap().put(MOVE_LEFT, userInputLEFT);
        getActionMap().put(STOP_RIGHT, userInputReleaseRIGHT);
        getActionMap().put(STOP_LEFT, userInputReleaseLEFT);


        this.addComponentListener(new java.awt.event.ComponentAdapter(){
            @Override
            public void componentShown(java.awt.event.ComponentEvent e){
                timer.start();
            }

            @Override
            public void componentHidden(java.awt.event.ComponentEvent e){
                timer.stop();
            }
        });
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        g.drawImage(bgSprite, 0, 0, getWidth(), getHeight(), this);

        if (shipSprite != null) {
            shipSprite.paintIcon(this, g, logic.getPlayerXpos(), logic.getPlayerYpos());

        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        logic.tick();

        if(e.getSource() == exitButton){
            manager.show("gameSelect");
        }
        repaint();
    }
}
