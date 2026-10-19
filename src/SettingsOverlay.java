import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

public class SettingsOverlay extends JPanel {

    private Image ishigiru;
    private SettingsManager settingsManager;

    public SettingsOverlay(SettingsManager settingsManager){
        this.settingsManager = settingsManager;

        setOpaque(false);
        setLayout(new GridBagLayout());

        addMouseListener(new MouseAdapter() {});

        ishigiru = new ImageIcon(getClass().getResource("Screenshot 2026-10-09 175957.png")).getImage();

        //main window
        JPanel settingsDialog = new JPanel();
        settingsDialog.setPreferredSize(new Dimension(500,300));
        settingsDialog.setLayout(new BorderLayout());

        //Close button
        JButton closeButton = new JButton();
        closeButton.setPreferredSize(new Dimension(25,25));
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });

        //settings label
        JLabel settingsLabel = new JLabel("Settings");
        settingsLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        topPanel.add(settingsLabel, BorderLayout.CENTER);
        topPanel.add(closeButton, BorderLayout.EAST);

        settingsDialog.add(topPanel, BorderLayout.NORTH);

        actualSettings(settingsDialog);
        add(settingsDialog);
    }

    private void actualSettings(JPanel settingsDialog){
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Volume Slider
        final int VOLUME_MIN = 0;
        final int VOLUME_MAX = 100;
        int VOLUME_CURRENT = settingsManager.getVolumeValue();

        JSlider sfxVolumeSlider = new JSlider(JSlider.HORIZONTAL,
                VOLUME_MIN, VOLUME_MAX, VOLUME_CURRENT);

        // Markers for Slider
        sfxVolumeSlider.setMajorTickSpacing(20);
        sfxVolumeSlider.setMinorTickSpacing(5);
        sfxVolumeSlider.setPaintTicks(true);
        sfxVolumeSlider.setPaintLabels(true);

        sfxVolumeSlider.addChangeListener(e -> {
            JSlider source = (JSlider) e.getSource();
            //set value only after release
            if (!source.getValueIsAdjusting()) {
                int value = source.getValue();
                settingsManager.setVolumeValue(value);
            }
        });

        JLabel volumeLabel = new JLabel("SFX Volume");
        volumeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        sfxVolumeSlider.setAlignmentX(Component.LEFT_ALIGNMENT);

        centerPanel.add(volumeLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        centerPanel.add(sfxVolumeSlider);

        settingsDialog.add(centerPanel, BorderLayout.CENTER);

        //Save button
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> { settingsManager.saveSettings(); });

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        bottomPanel.add(saveButton);

        settingsDialog.add(bottomPanel, BorderLayout.SOUTH);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        //RGBA
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, 0, getWidth(), getHeight());

        if(ishigiru != null){
            int x = (getWidth() - ishigiru.getWidth(this)) / 2;
            g.drawImage(ishigiru, x, getHeight()-120, null);
        }
    }
}
