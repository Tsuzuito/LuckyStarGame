import sound.SoundManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MenuPanel extends JPanel implements ActionListener {

    private final PanelManager manager;
    private final Image bgSprite;
    private final JButton button = new JButton("Start Game!");

    public MenuPanel(PanelManager manager){
        this.manager = manager;

        setBackground(Color.black);
        bgSprite = new ImageIcon(getClass().getResource("LCGameStartScreen.png")).getImage();
        ImageIcon startButtonSprite = new ImageIcon(getClass().getResource("Untitled-2.png"));

        setLayout(new GridBagLayout());

        button.setPreferredSize(new Dimension(200, 60));
        button.setIcon(startButtonSprite);
        add(button);
        button.addActionListener(this);
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(bgSprite, 0, 0, getWidth(), getHeight(), this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == button){
            SoundManager.playSound("/funnyclinksound.wav");
            manager.show("gameSelect");

        }
    }
}
