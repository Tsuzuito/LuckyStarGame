import sound.SoundManager;
import updater.UpdateChecker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URI;

public class MenuPanel extends JPanel implements ActionListener {

    private final PanelManager manager;
    private final Image bgSprite;

    private final JButton button = new JButton("Start Game!");
    private final JLabel versionNumberLabel = new JLabel(UpdateChecker.getVersion());
    private final JButton updateAvailableButton = new JButton("Update available");

    public MenuPanel(PanelManager manager){
        this.manager = manager;

        setBackground(Color.black);
        setLayout(new BorderLayout());

        // I don't understand layouts...........

        //---------
        bgSprite = new ImageIcon(getClass().getResource("LSGameStartScreen.png")).getImage();
        ImageIcon startButtonSprite = new ImageIcon(getClass().getResource("Untitled-2.png"));

        //---------
        button.setPreferredSize(new Dimension(200, 60));
        button.setIcon(startButtonSprite);
        button.addActionListener(this);
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                System.out.println("mouse entered");
                SoundManager.playSound("/LSopening1s.wav");
            }
            @Override
            public void mouseExited(MouseEvent e){
                System.out.println("mouse exited");
            }
        });

        //---------
        updateAvailableButton.setPreferredSize(new Dimension(140, 25));
        updateAvailableButton.setVisible(false);
        updateAvailableButton.addActionListener(this);

        versionNumberLabel.setVerticalAlignment(SwingConstants.BOTTOM);

        //---------
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        centerPanel.add(button);

        //---------
        JPanel updateButtonWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        updateButtonWrapper.setOpaque(false);

        updateButtonWrapper.setPreferredSize(new Dimension(140, 25));
        updateButtonWrapper.add(updateAvailableButton);

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.setOpaque(false);
        southPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        southPanel.add(versionNumberLabel, BorderLayout.WEST);
        southPanel.add(updateButtonWrapper, BorderLayout.CENTER);

        //---------
        add(centerPanel, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);

        checkUpdatesAsync();
    }

    private void checkUpdatesAsync(){
        // creating new backgrounds thread
        // If we don't do this, the app will remain pending until we receive a response.
        new Thread(() -> {
            try {
                String currentVersion = UpdateChecker.getVersion();
                String latestVersion = UpdateChecker.fetchLatestVersion();

                System.out.println("App version is: " + currentVersion);
                System.out.println("Latest GitHub version is: " + latestVersion);

                //comparison
                if(UpdateChecker.isUpdateAvailable(currentVersion, latestVersion)){
                    //Execute the code if an update is available
                    SwingUtilities.invokeLater(() -> updateAvailableButton.setVisible(true));
                    System.out.println("Update button is shown");
                }
            } catch (Exception e){
                System.err.println("Failed to check update: " + e.getMessage());
            }
        }).start(); //start Thread
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(bgSprite, 0, 0, getWidth(), getHeight(), this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == button){ manager.show("gameSelect"); }
        if(e.getSource() == updateAvailableButton){ openWebPage(); }
    }

    private void openWebPage(){
        try{
            if(Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)){
                Desktop.getDesktop().browse(new URI("https://github.com/Tsuzuito/LuckyStarGame/releases"));
            }
        } catch (Exception e){
            System.err.println("Failed to open browser: " + e.getMessage());
        }
    }
}