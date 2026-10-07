import dataSaving.GameSaveManager;
import snake.SnakeFood;
import snake.SnakeLogic;
import snake.SnakeWalls;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//KeyListener still sucks
public class SnakeGamePanel extends JPanel implements ActionListener {

    private final PanelManager manager;
    private final Timer timer;
    private final int gridSize = 16;
    private final Image bgSprite;

    private final GameSaveManager saveManager;

    private static final int IFW = JComponent.WHEN_IN_FOCUSED_WINDOW;
    private static final String MOVE_UP = "move up";
    private static final String MOVE_DOWN = "move down";
    private static final String MOVE_LEFT = "move left";
    private static final String MOVE_RIGHT = "move right";

    private static final String RESTART= "restart";

    private final ImageIcon snakeSprite = new ImageIcon(getClass().getResource("testicon16x16.png"));
    private final ImageIcon foodSprite = new ImageIcon(getClass().getResource("testicon16x16_2.png"));
    private final ImageIcon wallSprite = new ImageIcon(getClass().getResource("testicon16x16_3.png"));

    private final JButton backButton = new JButton("Back");
    private final JLabel scoreLabel = new JLabel();

    SnakeLogic snakeLogic = new SnakeLogic();

    //debug
    JLabel debugLabel = new JLabel();

    public SnakeGamePanel(PanelManager manager, GameSaveManager saveManager){
        this.manager = manager;
        this.saveManager = saveManager;

        debugLabel.setBounds(5,545,100,10);
        add(debugLabel);

        //x, y, width, height
        scoreLabel.setBounds(400,1,100,10);
        add(scoreLabel);

        setLayout(null);
        setBackground(Color.black);
        bgSprite = new ImageIcon(getClass().getResource("LSGameSnakeGame.png")).getImage();
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
                snakeLogic.setDirectionUP();
            }
        };

        Action userInputDOWN = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                snakeLogic.setDirectionDOWN();
            }
        };

        Action userInputLEFT = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                snakeLogic.setDirectionLEFT();
            }
        };

        Action userInputRIGHT = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                snakeLogic.setDirectionRIGHT();
            }
        };

        Action userInputRestart = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                snakeLogic.reset();
            }
        };

        //(key, action)
        getInputMap(IFW).put(KeyStroke.getKeyStroke("W"), MOVE_UP);
        getInputMap(IFW).put(KeyStroke.getKeyStroke("A"), MOVE_LEFT);
        getInputMap(IFW).put(KeyStroke.getKeyStroke("S"), MOVE_DOWN);
        getInputMap(IFW).put(KeyStroke.getKeyStroke("D"), MOVE_RIGHT);

        getInputMap(IFW).put(KeyStroke.getKeyStroke("R"), RESTART);

        //(action, actionMethod)
        getActionMap().put(MOVE_UP, userInputUP);
        getActionMap().put(MOVE_DOWN, userInputDOWN);
        getActionMap().put(MOVE_LEFT, userInputLEFT);
        getActionMap().put(MOVE_RIGHT, userInputRIGHT);

        getActionMap().put(RESTART, userInputRestart);

        this.addComponentListener(new java.awt.event.ComponentAdapter(){
            @Override
            public void componentShown(java.awt.event.ComponentEvent e){
                timer.start();
            }

            @Override
            public void componentHidden(java.awt.event.ComponentEvent e){
                timer.stop();
                snakeLogic.reset();
            }
        });
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(bgSprite, 0, 0, getWidth(), getHeight(), this);

        //Calculate grid columns rows and dimensions based on window size
        int padding = 16 * 4;
        int cols = (getWidth()  - 2 * padding) / gridSize;
        int rows = (getHeight() - 2 * padding) / gridSize;
        int gridWidth  = cols * gridSize;
        int gridHeight = rows * gridSize;

        //Setup graphics for grid lines
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(new Color(161, 161, 161));

        //Draw vertical grid lines

        //draws a line connecting the points
        //from (x1, y1) to (x2, y2)
        for (int i = 0; i <= cols; i++) {
            int x = padding + i * gridSize;
            g2d.drawLine(x, padding, x, padding + gridHeight);
        }

        //Draw horizontal grid lines
        for (int i = 0; i <= rows; i++) {
            int y = padding + i * gridSize;
            g2d.drawLine(padding, y, padding + gridWidth, y);
        }

        if (snakeSprite != null) {
            for(Point p : snakeLogic.getSnake()){
                snakeSprite.paintIcon(this, g, p.x, p.y);
            }
        }
        if (foodSprite != null) {
            SnakeFood food = snakeLogic.getFood();
            for(Point foodPoint : food.getFoodPosition()){
                foodSprite.paintIcon(this, g, foodPoint.x, foodPoint.y);
            }
        }
        if(wallSprite != null) {
            SnakeWalls walls = snakeLogic.getWalls();
            for(Point wallPoint : walls.getWalls()){
                wallSprite.paintIcon(this, g, wallPoint.x, wallPoint.y);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()== backButton){ manager.show("gameSelect"); return; }

        if(!snakeLogic.isGameOver()){
            snakeLogic.tick();
            repaint();
            scoreLabel.setText("score: " + snakeLogic.getScore());

            //debug
            debugLabel.setText("tick: " + snakeLogic.tickCounter);
        } else {
            timer.stop();
            saveManager.registerNewScore("snake", snakeLogic.getScore());
        }
    }
}