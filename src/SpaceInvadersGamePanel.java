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
    private static final String SHOOT_FIRE= "shoot fire";
    private static final String STOP_SHOOT_FIRE= "stop shoot fire";

    private static final String RESTART= "restart";

    private final ImageIcon shipSprite = new ImageIcon(getClass().getResource("testicon16x16.png"));
    private final ImageIcon laserSprite = new ImageIcon(getClass().getResource("testicon16x16_2.png"));
    private final ImageIcon enemiesSprite = new ImageIcon(getClass().getResource("testicon16x16_3.png"));

    private final PanelManager manager;

    private final GameSaveManager saveManager;

    private final int gridSize = 16;
    private final Image bgSprite;
    private Timer timer;


    private JButton backButton = new JButton("Exit");
    private final JLabel scoreLabel = new JLabel();

    SpaceInvadersLogic spaceInvadersLogic = new SpaceInvadersLogic();

    //debug
    JLabel debugLabel = new JLabel();

    public SpaceInvadersGamePanel(PanelManager manager, GameSaveManager saveManager){
        this.manager = manager;
        this.saveManager = saveManager;
        spaceInvadersLogic.reset();

        debugLabel.setBounds(5,545,100,10);
        add(debugLabel);

        scoreLabel.setBounds(400,1,100,10);
        add(scoreLabel);

        bgSprite = new ImageIcon(getClass().getResource("LCGameSnakeGame.png")).getImage();

        setBackground(Color.BLACK);
        setLayout(null);

        JPanel exitPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,10 ,10));
        exitPanel.setOpaque(false);

        Dimension btnSize = new Dimension(100,50);
        backButton.setPreferredSize(btnSize);
        backButton.addActionListener(this);

        exitPanel.setBounds(0, 0, 120, 70);
        exitPanel.add(backButton);

        add(exitPanel);

        //-------------
        timer = new Timer(100, this);

        Action userInputRIGHT = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                spaceInvadersLogic.setMovingRight();
            }
        };

        Action userInputLEFT = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                spaceInvadersLogic.setMovingLeft();
            }
        };

        Action userInputReleaseLEFT = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                spaceInvadersLogic.stopMovingLeft();
            }
        };

        Action userInputReleaseRIGHT = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                spaceInvadersLogic.stopMovingRight();
            }
        };

        Action userInputShootFire = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                spaceInvadersLogic.setShootFire();
            }
        };

        Action userInputStopShootFire = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                spaceInvadersLogic.stopShootFire();
            }
        };

        Action userInputRestart = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                spaceInvadersLogic.reset();
            }
        };

        getInputMap(IFW).put(KeyStroke.getKeyStroke("D"), MOVE_RIGHT);
        getInputMap(IFW).put(KeyStroke.getKeyStroke("A"), MOVE_LEFT);
        getInputMap(IFW).put(KeyStroke.getKeyStroke("released D"), STOP_RIGHT);
        getInputMap(IFW).put(KeyStroke.getKeyStroke("released A"), STOP_LEFT);

        getInputMap(IFW).put(KeyStroke.getKeyStroke("SPACE"), SHOOT_FIRE);
        getInputMap(IFW).put(KeyStroke.getKeyStroke("released SPACE"), STOP_SHOOT_FIRE);

        getInputMap(IFW).put(KeyStroke.getKeyStroke("R"), RESTART);

        getActionMap().put(MOVE_RIGHT, userInputRIGHT);
        getActionMap().put(MOVE_LEFT, userInputLEFT);
        getActionMap().put(STOP_RIGHT, userInputReleaseRIGHT);
        getActionMap().put(STOP_LEFT, userInputReleaseLEFT);

        getActionMap().put(SHOOT_FIRE, userInputShootFire);
        getActionMap().put(STOP_SHOOT_FIRE, userInputStopShootFire);

        getActionMap().put(RESTART, userInputRestart);


        this.addComponentListener(new java.awt.event.ComponentAdapter(){
            @Override
            public void componentShown(java.awt.event.ComponentEvent e){
                timer.start();
            }

            @Override
            public void componentHidden(java.awt.event.ComponentEvent e){
                timer.stop();
                spaceInvadersLogic.reset();
            }
        });
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        g.drawImage(bgSprite, 0, 0, getWidth(), getHeight(), this);

        if (shipSprite != null) {
            shipSprite.paintIcon(this, g, spaceInvadersLogic.getPlayerXpos(), spaceInvadersLogic.getPlayerYpos());

        }

        if(laserSprite != null){
            for(Point p : spaceInvadersLogic.getLasers()){
                laserSprite.paintIcon(this, g, p.x, p.y);
            }
        }

        if(enemiesSprite != null){
            for(Point p : spaceInvadersLogic.getEnemies()){
                enemiesSprite.paintIcon(this, g, p.x, p.y);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()== backButton){ manager.show("gameSelect"); return; }

        if(!spaceInvadersLogic.getIsGameOver()){
            spaceInvadersLogic.tick();
            repaint();
            scoreLabel.setText("score: " + spaceInvadersLogic.score);

            //debug
            debugLabel.setText("tick: " + spaceInvadersLogic.tickCounter + "\n");
        } else {
            timer.stop();
            saveManager.registerNewScore("spaceInvaders", spaceInvadersLogic.score);
        }
    }
}
