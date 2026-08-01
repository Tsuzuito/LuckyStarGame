import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ScorePanel extends JPanel implements ActionListener {

    private final PanelManager manager;

    private final GameSaveManager saveManager;

    private final JButton backButton = new JButton("Back");

    private final JLabel snakeBestLabel = new JLabel("Snake Best: 0");
    private final JLabel spaceBestLabel = new JLabel("Space Invaders Best: 0");

    private final Image bgSprite;

    //wip
    public ScorePanel(PanelManager manager, GameSaveManager saveManager){
        this.manager = manager;
        this.saveManager = saveManager;

        AppSaveData data = saveManager.getSaveData();

        setBackground(Color.black);
        bgSprite = new ImageIcon(getClass().getResource("sddefault.jpg")).getImage();

        //Exit button
        JPanel exitPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10 , 10));
        exitPanel.setOpaque(false);

        Dimension btnSize = new Dimension(100, 50);
        backButton.setPreferredSize(btnSize);
        backButton.addActionListener(this);
        exitPanel.add(backButton);

        exitPanel.setBounds(0, 0, 120, 70);
        add(exitPanel);


    }



    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
//        g.drawImage(bgSprite, 0, 0, getWidth(), getHeight(), this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()== backButton){ manager.show("gameSelect"); }

    }
}
