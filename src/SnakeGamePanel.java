import snake.SnakeFood;
import snake.SnakeLogic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//KeyListener sucks
public class SnakeGamePanel extends JPanel implements ActionListener {

    //work in progress
    private final PanelManager manager;
    private Timer timer;
    private final int gridSize = 16;
    private final Image bgSprite;

    private final GameSaveManager saveManager;

    private static final int IFW = JComponent.WHEN_IN_FOCUSED_WINDOW;
    private static final String MOVE_UP = "move up";
    private static final String MOVE_DOWN = "move down";
    private static final String MOVE_LEFT = "move left";
    private static final String MOVE_RIGHT = "move right";

    private final ImageIcon snakeSprite = new ImageIcon(getClass().getResource("testicon16x16.png"));
    private final ImageIcon foodSprite = new ImageIcon(getClass().getResource("testicon16x16_2.png"));

    private final JButton backButton = new JButton("Back");

    private final JLabel scoreLabel = new JLabel();

    SnakeLogic snake = new SnakeLogic();

    //debug
    JLabel debugLabel = new JLabel();

    public SnakeGamePanel(PanelManager manager, GameSaveManager saveManager){
        this.manager = manager;
        this.saveManager = saveManager;

        //x, y, width, height
        debugLabel.setBounds(1,1,100,10);
        add(debugLabel);

        scoreLabel.setBounds(400,1,100,10);
        add(scoreLabel);

        setLayout(null);
        setBackground(Color.black);
        bgSprite = new ImageIcon(getClass().getResource("LCGameSnakeGame.png")).getImage();
        timer = new Timer(400, this);

        //Exit button
        JPanel exitPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10 , 10));
        exitPanel.setOpaque(false);

        Dimension btnSize = new Dimension(100, 50);
        backButton.setPreferredSize(btnSize);
        exitPanel.add(backButton);
        backButton.addActionListener(this);

        exitPanel.setBounds(0, 0, 120, 70);
        add(exitPanel);

        Action userInputUP = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                snake.setDirectionUP();
            }
        };

        Action userInputDOWN = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                snake.setDirectionDOWN();
            }
        };

        Action userInputLEFT = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                snake.setDirectionLEFT();
            }
        };

        Action userInputRIGHT = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                snake.setDirectionRIGHT();
            }
        };

        //(key, action)
        getInputMap(IFW).put(KeyStroke.getKeyStroke("W"), MOVE_UP);
        getInputMap(IFW).put(KeyStroke.getKeyStroke("A"), MOVE_LEFT);
        getInputMap(IFW).put(KeyStroke.getKeyStroke("S"), MOVE_DOWN);
        getInputMap(IFW).put(KeyStroke.getKeyStroke("D"), MOVE_RIGHT);

        //(action, actionMethod)
        getActionMap().put(MOVE_UP, userInputUP);
        getActionMap().put(MOVE_DOWN, userInputDOWN);
        getActionMap().put(MOVE_LEFT, userInputLEFT);
        getActionMap().put(MOVE_RIGHT, userInputRIGHT);

        this.addComponentListener(new java.awt.event.ComponentAdapter(){
            @Override
            public void componentShown(java.awt.event.ComponentEvent e){
                snake.updateDimensions(getWidth(), getHeight());
                snake.reset();
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

        int padding = 16 * 4;
        Graphics g2d = (Graphics) g;
        g2d.setColor(new Color(161, 161, 161));

        //point 1 -> x1,y2
        //point 2 -> x2,y2
        for(int x = padding; x <= getWidth() - padding; x += gridSize){
            g2d.drawLine(x, padding , x, getHeight() - padding - 1);
        }

        for(int y = padding; y <= getHeight() - padding; y += gridSize){
            g2d.drawLine(padding, y, getWidth() - padding, y);
        }

        if (snakeSprite != null) {
            for(Point p : snake.getSnake()){
                snakeSprite.paintIcon(this, g, p.x, p.y);
            }
        }
        if (foodSprite != null) {
            SnakeFood food = snake.getFood();
            foodSprite.paintIcon(this, g,
                    food.getxPos(),
                    food.getyPos());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource()== backButton){ manager.show("gameSelect"); }

        if(!snake.isGameOver()){
            snake.tick();
            repaint();
            scoreLabel.setText("score: " + snake.score);

            //debug
            debugLabel.setText("tick: " + snake.tickCounter + "\n");
        } else {
            timer.stop();
            if(saveManager.getBestScore() < snake.score){
                saveManager.saveGame(snake.score);
            }

        }
    }
}