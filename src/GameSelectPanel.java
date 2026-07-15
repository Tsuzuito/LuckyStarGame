import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameSelectPanel extends JPanel implements ActionListener {

    private final PanelManager manager;
    private final Image bgSprite;

    //Snake
    private final JButton konataGameButton = new JButton("Snake");
    //space invaders
    private final JButton kagamiGameButton = new JButton("...");
    //paddle game
    private final JButton tsukasaGameButton = new JButton("...");
    //Memory
    private final JButton miyukiGameButton = new JButton("...");

    private final JButton backToMenu = new JButton("Back");
    private final JButton scoreMenu = new JButton("Scores");

    public GameSelectPanel(PanelManager manager) {
        this.manager = manager;

        setBackground(Color.black);
        bgSprite = new ImageIcon(getClass().getResource("LCGameGameSelectScreen.png")).getImage();

        setLayout(new BorderLayout());

        //group of buttons (games)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setOpaque(false);

        JPanel exitPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10 , 10));
        exitPanel.setOpaque(false);

        Dimension btnSize = new Dimension(100, 50);

        //set button size
        konataGameButton.setPreferredSize(btnSize);
        kagamiGameButton.setPreferredSize(btnSize);
        tsukasaGameButton.setPreferredSize(btnSize);
        miyukiGameButton.setPreferredSize(btnSize);
        backToMenu.setPreferredSize(btnSize);
        scoreMenu.setPreferredSize(btnSize);

        //add button on panel
        buttonPanel.add(konataGameButton);
        buttonPanel.add(kagamiGameButton);
        buttonPanel.add(tsukasaGameButton);
        buttonPanel.add(miyukiGameButton);
        exitPanel.add(backToMenu);
        exitPanel.add(scoreMenu);

        konataGameButton.addActionListener(this);
        kagamiGameButton.addActionListener(this);
        tsukasaGameButton.addActionListener(this);
        miyukiGameButton.addActionListener(this);
        backToMenu.addActionListener(this);
        scoreMenu.addActionListener(this);

        //temp
        kagamiGameButton.setEnabled(false);
        tsukasaGameButton.setEnabled(false);
        miyukiGameButton.setEnabled(false);

        add(buttonPanel, BorderLayout.SOUTH);
        add(exitPanel, BorderLayout.WEST);
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(bgSprite, 0, 0, getWidth(), getHeight(), this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
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
    }
}