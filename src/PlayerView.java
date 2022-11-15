import java.awt.Dimension;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class PlayerView {
    private JPanel playerPanel;
    private JLabel typeLbl;

    public PlayerView() {
        this.playerPanel = new JPanel();
        this.playerPanel.setPreferredSize(new Dimension(1200, 100));
        this.playerPanel.setBackground(Color.decode("#2C3639"));

        this.typeLbl = new JLabel();
        this.typeLbl.setFont(new Font("Serif", Font.PLAIN, 100));
        this.typeLbl.setForeground(Color.decode("#FFFFFF"));

        this.playerPanel.add(this.typeLbl);
    }

    public JPanel getPanel() {
        return this.playerPanel;
    }

    public void setTypeLbl(String text) {
        this.typeLbl.setText(text);
    }
}
