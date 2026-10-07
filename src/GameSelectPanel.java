import sound.SoundManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameSelectPanel extends JPanel implements ActionListener {

    private final PanelManager manager;
    private Timer timer;

    private final Image bgSprite;
    private Image characterSprite;

    private double characterX = -500;
    private final int TARGET_X = 50;
    private final double ANIMATION_SPEED = 0.15;

    //Snake (konata)
    private final JButton konataGameButton = new JButton("Snake");
    //Space invaders (Kagami)
    private final JButton kagamiGameButton = new JButton("Space");
    //paddle game (Tsukasa)
    private final JButton tsukasaGameButton = new JButton("Paddle \ngame");
    //memory matching (Miyuki)
    private final JButton miyukiGameButton = new JButton("memory \nmatching");


    private final JButton backToMenu = new JButton("Back");
    private final JButton scoreMenu = new JButton("Scores");

    public GameSelectPanel(PanelManager manager) {
        this.manager = manager;

        // base settings
        setBackground(Color.black);
        bgSprite = new ImageIcon(getClass().getResource("LSGameGameSelectScreen.png")).getImage();
        setLayout(new BorderLayout());


        timer = new Timer(4, this);
        this.addComponentListener(new java.awt.event.ComponentAdapter(){
            @Override
            public void componentShown(java.awt.event.ComponentEvent e){


            }

            @Override
            public void componentHidden(java.awt.event.ComponentEvent e){
                timer.stop();
                characterX = -500;
            }
        });


        setUpMenu();
    }

    private void setUpMenu(){
        // Panels and size
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setOpaque(false);

        JPanel exitPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        exitPanel.setOpaque(false);

        Dimension btnSize = new Dimension(100, 50);

        //Snake (konata)
        konataGameButton.setPreferredSize(btnSize);
        konataGameButton.addActionListener(this);
        buttonPanel.add(konataGameButton);
        konataGameButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                System.out.println("mouse entered (Snake)");
                characterSprite = new ImageIcon(getClass().getResource("first1.png")).getImage();
                characterX = -500;
                timer.start();
            }
            @Override
            public void mouseExited(MouseEvent e){
                System.out.println("mouse exited (Snake)");


            }
        });

        //Space invaders (Kagami)
        kagamiGameButton.setPreferredSize(btnSize);
        kagamiGameButton.addActionListener(this);
        buttonPanel.add(kagamiGameButton);
        kagamiGameButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                System.out.println("mouse entered (Space Invaders)");
                characterSprite = new ImageIcon(getClass().getResource("first2.png")).getImage();
                characterX = -500;
                timer.start();

            }
            @Override
            public void mouseExited(MouseEvent e){
                System.out.println("mouse exited (Space Invaders)");

            }
        });

        //paddle game (Tsukasa)
        tsukasaGameButton.setPreferredSize(btnSize);
        tsukasaGameButton.addActionListener(this);
        tsukasaGameButton.setEnabled(false);
        buttonPanel.add(tsukasaGameButton);

        //memory matching (Miyuki)
        miyukiGameButton.setPreferredSize(btnSize);
        miyukiGameButton.addActionListener(this);
        miyukiGameButton.setEnabled(false);
        buttonPanel.add(miyukiGameButton);

        // Back to menu
        backToMenu.setPreferredSize(btnSize);
        backToMenu.addActionListener(this);
        exitPanel.add(backToMenu);

        // Score Menu
        scoreMenu.setPreferredSize(btnSize);
        scoreMenu.addActionListener(this);
        exitPanel.add(scoreMenu);

        //
        add(buttonPanel, BorderLayout.SOUTH);
        add(exitPanel, BorderLayout.WEST);
    }

    public void gameCharacterPreviewAnimation(){
        //easing and moving to a target
        if(characterX < TARGET_X){
            characterX += (TARGET_X - characterX) * ANIMATION_SPEED;
        }
        //stop. If character position less than 1 pixel it will equivalent TARGET_X (50)
        if(Math.abs(TARGET_X - characterX) < 1){
            characterX = TARGET_X;
        }
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        g2d.drawImage(bgSprite, 0, 0, null);

        if(characterSprite != null){
            g2d.drawImage(characterSprite, (int) characterX, 100, null);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == timer){
            gameCharacterPreviewAnimation();
            repaint();
            return;
        }

        if(e.getSource()== backToMenu){
            manager.show("menu");
        }
        if(e.getSource()== scoreMenu){
            manager.show("scoreMenu");
        }

        //-------------------------
        if(e.getSource() == konataGameButton){
            manager.show("gameSnake");
        }
        if(e.getSource() == kagamiGameButton){
            manager.show("gameSpaceInvaders");
        }
        if(e.getSource() == tsukasaGameButton){

        }
        if(e.getSource() == miyukiGameButton){

        }
    }
}