import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

public class SettingsOverlay extends JPanel {

    private final Image ishigiru;

    public SettingsOverlay(){
        setOpaque(false);
        setLayout(new GridBagLayout());

        addMouseListener(new MouseAdapter() {});

        JPanel settingsDialog = new JPanel();
        settingsDialog.setPreferredSize(new Dimension(400,400));
        settingsDialog.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        settingsDialog.add(new JLabel("settings menu"));

        JButton closeSettings = new JButton("close");

        closeSettings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });

        settingsDialog.add(closeSettings);

        add(settingsDialog);

        ishigiru = new ImageIcon(getClass().getResource("Screenshot 2026-10-09 175957.png")).getImage();


    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        //RGBA
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    @Override
    protected void paintChildren(Graphics g) {
        super.paintChildren(g);


        if (ishigiru != null) {
            g.drawImage(ishigiru, 170, 500, null);
        }
    }
}
