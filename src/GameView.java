import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class GameView {
    private JFrame mainFrame;

    private JPanel mainPanel;
    private JPanel northPanel;
    private JPanel centerPanel;
    private JPanel southPanel;
    private JPanel ActionPanel;

    public GameView() {
        this.mainFrame = new JFrame("MyFarm");

		this.mainFrame.setSize(1200, 675);
        this.mainFrame.setResizable(false);
        this.mainFrame.setLocationRelativeTo(null);
        this.mainFrame.setLayout(new BorderLayout(5,5));
        this.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.mainPanel = new JPanel(new BorderLayout());
        this.mainPanel.setBackground(Color.decode("#3F4E4F"));
        this.mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));


        this.mainFrame.setVisible(true);
    }

    public void addPanel(JPanel panel) {
        this.mainFrame.add(panel);
    }

    public void update() {
        this.mainFrame.repaint();
    }
}
