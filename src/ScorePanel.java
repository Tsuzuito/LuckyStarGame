import dataSaving.AppSaveData;
import dataSaving.GameSaveManager;
import dataSaving.ScoreEntry;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ScorePanel extends JPanel implements ActionListener {

    private final PanelManager manager;

    private final GameSaveManager saveManager;

    private final JButton backButton = new JButton("Back");
    private final JButton deleteData = new JButton("Delete Data");

    //snake
    private JList<String> snakeScoreHistory = new JList<>();
    private JLabel snakeBestLabel = new JLabel("Snake Best: 0");
    private JScrollPane scrollPaneSnake = new JScrollPane(snakeScoreHistory);

    //space invaders
    private JList<String> spaceInvadersScoreHistory = new JList<>();
    private final JLabel spaceBestLabel = new JLabel("Space Invaders Best: 0");
    private JScrollPane scrollPaneSpace = new JScrollPane(spaceInvadersScoreHistory);

    //

    private final Image bgSprite;

    public ScorePanel(PanelManager manager, GameSaveManager saveManager){
        this.manager = manager;
        this.saveManager = saveManager;

        setLayout(null);
        setBackground(Color.black);
        bgSprite = new ImageIcon(getClass().getResource("sddefault.jpg")).getImage();

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBounds(0, 0, 800, 70);

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));

        Dimension btnSize = new Dimension(100, 50);

        backButton.setPreferredSize(btnSize);
        backButton.addActionListener(this);
        leftPanel.add(backButton);

        deleteData.setPreferredSize(btnSize);
        deleteData.addActionListener(this);
        rightPanel.add(deleteData);

        topPanel.add(leftPanel, BorderLayout.WEST);
        topPanel.add(rightPanel, BorderLayout.EAST);

        add(topPanel);

        //snake
        snakeBestLabel.setBounds(10,40,100,100);
        add(snakeBestLabel);

        scrollPaneSnake.setBounds(10,100,160,200);
        add(scrollPaneSpace);

        //space invaders
        spaceBestLabel.setBounds(200,40,100,100);
        add(spaceBestLabel);

        scrollPaneSpace.setBounds(200,100,160,200);
        add(scrollPaneSnake);

        //

        this.addComponentListener(new java.awt.event.ComponentAdapter(){
            @Override
            public void componentShown(java.awt.event.ComponentEvent e){
                updateScoreDisplay();
            }

            @Override
            public void componentHidden(java.awt.event.ComponentEvent e){

            }
        });
    }

    public void updateScoreDisplay() {
        AppSaveData data = saveManager.getSaveData();
        //
        if (data == null) { return; }

        // update snake
        if (data.getSnake() != null) {
            var snakeHistory = data.getSnake().getHistory();
            String[] snakeLines = new String[snakeHistory.size()];
            for (int i = 0; i < snakeHistory.size(); i++) {
                ScoreEntry entry = snakeHistory.get(i);
                snakeLines[i] = entry.getScore() + " Points. " + entry.getDate();
            }
            snakeScoreHistory.setListData(snakeLines);
            snakeBestLabel.setText("Snake Best: " + data.getSnake().getBestScore());
        }

        // update space
        if (data.getSpaceInvaders() != null) {
            var spaceHistory = data.getSpaceInvaders().getHistory();
            String[] spaceLines = new String[spaceHistory.size()];
            for (int i = 0; i < spaceHistory.size(); i++) {
                ScoreEntry entry = spaceHistory.get(i);
                spaceLines[i] = entry.getScore() + " Points. " + entry.getDate();
            }
            spaceInvadersScoreHistory.setListData(spaceLines);
            spaceBestLabel.setText("Space Best: " + data.getSpaceInvaders().getBestScore());
        }
    }


    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(bgSprite, 0, 0, getWidth(), getHeight(), this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()== backButton){ manager.show("gameSelect"); }
        if(e.getSource()== deleteData){ saveManager.deleteUserData(); updateScoreDisplay();}

    }
}
