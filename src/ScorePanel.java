import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ScorePanel extends JPanel implements ActionListener {

    private final PanelManager manager;

    private final GameSaveManager saveManager;

    private final JButton backButton = new JButton("Back");
    private final JButton deleteData = new JButton("Delete Data");


    private JList<String> snakeScoreHistory = new JList<>();
    private JLabel snakeBestLabel = new JLabel("Snake Best: 0");
    private JScrollPane scrollPaneSnake = new JScrollPane(snakeScoreHistory);

    private final JLabel spaceBestLabel = new JLabel("Space Invaders Best: 0");

    private final Image bgSprite;

    //wip
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

        snakeBestLabel.setBounds(10,100,100,100);
        add(snakeBestLabel);

        scrollPaneSnake.setBounds(10,100,100,100);
        add(scrollPaneSnake);

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

    public void updateScoreDisplay(){
        AppSaveData data = saveManager.getSaveData();
        String[] textLines = new String[data.getSnake().getHistory().size()];

        for(int i = 0; i < data.getSnake().getHistory().size(); i++){
            ScoreEntry entry = data.getSnake().getHistory().get(i);
            textLines[i] = entry.getScore() + " Points. " + entry.getDate();
        }

        snakeScoreHistory.setListData(textLines);
        snakeBestLabel.setText("best: " + data.getSnake().getBestScore());
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
