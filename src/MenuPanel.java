import sound.SoundManager;
import updater.UpdateChecker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URI;

public class MenuPanel extends JPanel implements ActionListener {

    private final PanelManager manager;
    private final Image bgSprite;

    private final JButton startButton = new JButton();
    private final JButton gitHubButton = new JButton();

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

        ImageIcon originalIcon = new ImageIcon(getClass().getResource("github-logo.png"));
        Image scaledImage = originalIcon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        ImageIcon githubButtonSprite = new ImageIcon(scaledImage);


        //---------
        startButton.setPreferredSize(new Dimension(200, 60));
        startButton.setIcon(startButtonSprite);
        startButton.addActionListener(this);
        startButton.addMouseListener(new MouseAdapter() {
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
        //
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        centerPanel.add(startButton);

        //----------
        gitHubButton.setPreferredSize(new Dimension(25, 25));
        gitHubButton.addActionListener(this);
        gitHubButton.setIcon(githubButtonSprite);

        gitHubButton.setContentAreaFilled(false);
        gitHubButton.setBorderPainted(false);
        gitHubButton.setFocusPainted(false);

        updateAvailableButton.setPreferredSize(new Dimension(140, 25));
        updateAvailableButton.setVisible(false);
        updateAvailableButton.addActionListener(this);

        //App version panel (left)
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPanel.setOpaque(false);
        leftPanel.add(versionNumberLabel);

        //update button panel (center)
        JPanel middlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        middlePanel.setOpaque(false);
        middlePanel.add(updateAvailableButton);

        //GitHub link panel (Right)
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setOpaque(false);
        rightPanel.add(gitHubButton);

        //Combine 3 panel
        JPanel southPanel = new JPanel(new GridLayout(1, 3));
        southPanel.setOpaque(false);
        southPanel.setBorder(BorderFactory.createEmptyBorder(0,10,10,10));

        southPanel.add(leftPanel);
        southPanel.add(middlePanel);
        southPanel.add(rightPanel);

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
        if(e.getSource() == startButton){ manager.show("gameSelect"); }

        if(e.getSource() == updateAvailableButton){ openWebPage("update"); }
        if(e.getSource() == gitHubButton) { openWebPage("repo"); }
    }

    private void openWebPage(String webPageName){
        try{
            if(Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)){
                if(webPageName.equalsIgnoreCase("update")){
                    Desktop.getDesktop().browse(new URI("https://github.com/Tsuzuito/LuckyStarGame/releases"));
                } else if (webPageName.equalsIgnoreCase("repo")) {
                    Desktop.getDesktop().browse(new URI("https://github.com/Tsuzuito/LuckyStarGame"));
                }
            }
        } catch (Exception e){
            System.err.println("Failed to open browser: " + e.getMessage());
        }
    }
}