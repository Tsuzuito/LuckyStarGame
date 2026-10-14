import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

public class SettingsOverlay extends JPanel {

    private Image ishigiru;

    public SettingsOverlay(){
        setOpaque(false);
        setLayout(new GridBagLayout());

        addMouseListener(new MouseAdapter() {});

        ishigiru = new ImageIcon(getClass().getResource("Screenshot 2026-10-09 175957.png")).getImage();

        //main window
        JPanel settingsDialog = new JPanel();
        settingsDialog.setPreferredSize(new Dimension(500,300));
        settingsDialog.setLayout(new BorderLayout());

        //Close button
        JButton closeButton = new JButton("X");
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

        add(settingsDialog);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, 0, getWidth(), getHeight());

        if(ishigiru != null){
            int x = (getWidth() - ishigiru.getWidth(this)) / 2;
            g.drawImage(ishigiru, x, getHeight()-120, null);
        }
    }
}
