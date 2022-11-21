import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;

import javax.swing.ImageIcon;
import javax.swing.border.EmptyBorder;

public class GameView {
    private JFrame mainFrame;
    private JPanel mainPanel;

    private JPanel farmPanel;
    private JPanel southPanel;
    private JPanel actionPanel;

    // Player information panel elements
    private JLabel levelAndExperienceLbl = new JLabel("Level Experience");
    private JLabel objectCoinsLbl = new JLabel("objectCoins");
    private JLabel farmerTypeLbl = new JLabel("Farmer");
    private JLabel dayLbl = new JLabel("Day");

    // Farm panel elements
    private ImageIcon imgTileUnplowed = new ImageIcon("res/tile_unplowed.png");
    private ImageIcon imgTilePlowed = new ImageIcon("res/tile_plowed.png");
    private ImageIcon imgActions = new ImageIcon("res/button_action.png");

    JButton[][] farmTilesBtn = new JButton[5][10];
    ActionListener farmTileListener;
    
    public GameView() {
        this.mainFrame = new JFrame("MyFarm");

        this.mainFrame.setSize(1200, 675);
        this.mainFrame.setResizable(false);
        this.mainFrame.setLocationRelativeTo(null);
        
        this.mainFrame.setLayout(new BorderLayout(5,5));
        this.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Panel that contains all the panels
        this.mainPanel = new JPanel(new BorderLayout());
        this.mainPanel.setBackground(Color.decode("#313131"));
        this.mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Information panel
        initializeInfoPanel();

        // Farm panel
        initializeFarmPanel();

        // Action panel (buttons) replaces south panel when there is a valid action in the tile)
        this.actionPanel = new JPanel(new FlowLayout());
        this.actionPanel.setPreferredSize(new Dimension(1200, 100));
        actionPanel.setOpaque(false);

        this.southPanel = new JPanel(new BorderLayout());
        this.southPanel.setPreferredSize(new Dimension(1200, 100));
        this.southPanel.setOpaque(false);

        this.mainPanel.add(this.southPanel, BorderLayout.PAGE_END);

        this.mainFrame.add(this.mainPanel, BorderLayout.CENTER);
        this.mainFrame.setVisible(true);
    }

    public void initializeInfoPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel leftPanel = new JPanel(new BorderLayout());
        JPanel rightPanel = new JPanel(new BorderLayout());

        mainPanel.setPreferredSize(new Dimension(1180, 31));
        mainPanel.setBackground(Color.decode("#414141"));
        mainPanel.setOpaque(true);

        leftPanel.setPreferredSize(new Dimension(600,31));
        leftPanel.setOpaque(false);

        rightPanel.setOpaque(false);

        leftPanel.add(this.levelAndExperienceLbl, BorderLayout.PAGE_START);
        leftPanel.add(this.objectCoinsLbl, BorderLayout.CENTER);

        rightPanel.add(this.farmerTypeLbl, BorderLayout.PAGE_START);
        rightPanel.add(this.dayLbl, BorderLayout.CENTER);

        mainPanel.add(leftPanel, BorderLayout.LINE_START);
        mainPanel.add(rightPanel, BorderLayout.LINE_END);

        this.mainPanel.add(mainPanel, BorderLayout.PAGE_START);
    }

    public void initializeFarmPanel() {
        this.farmPanel = new JPanel(new GridLayout(5, 10));
        this.farmPanel.setBorder(new EmptyBorder(10, 0, 10, 0));
        this.farmPanel.setOpaque(false);

        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 10; j++) {
                farmTilesBtn[i][j] = new JButton();
                farmTilesBtn[i][j].setPreferredSize(new Dimension(118, 104));
                farmTilesBtn[i][j].setBorderPainted(false);

                farmTilesBtn[i][j].addActionListener(farmTileListener);
                farmTilesBtn[i][j].putClientProperty("location", new int[] {i, j});
                
                this.farmPanel.add(farmTilesBtn[i][j]);
            }
        }

        this.mainPanel.add(farmPanel, BorderLayout.CENTER);
    }

    public void addButton(ActionListener e) {
        JButton button = new JButton();
        button.addActionListener(e);

        this.actionPanel.add(button);

        if (this.southPanel.isDisplayable()) {
            this.mainFrame.remove(this.southPanel);
            this.mainFrame.add(this.actionPanel, BorderLayout.PAGE_END);
        }
    }

    public void hideResetActionPanel() {
        this.actionPanel.removeAll();
        this.mainFrame.remove(this.actionPanel);
        this.mainFrame.add(this.southPanel, BorderLayout.PAGE_END);
    }

    public void updateSouthPanel() {
        this.southPanel.validate();
        this.southPanel.repaint();
    }

    public void setLevel(int level, float experience) {
        this.levelAndExperienceLbl.setText("Level: " + level + " (" + experience + ")");
    }

    public void setObjectCoins(float objectCoins) {
        this.objectCoinsLbl.setText("ObjectCoins: " + objectCoins);
    }

    public void setFarmerType(FarmerType farmerType) {
        this.farmerTypeLbl.setText(farmerType.getTypeName());
    }

    public void setDay(int day) {
        this.dayLbl.setText("Day " + day);
    }

    public void changeFarmTileListener(ActionListener e, int row, int col) {
        // pede ba to magcheck ng isa lang?
        for (ActionListener actionListener : this.farmTilesBtn[row][col].getActionListeners()) {
            this.farmTilesBtn[row][col].removeActionListener(actionListener);
        }

        this.farmTilesBtn[row][col].addActionListener(e);
    }

    // temp
    public void setTileText(String s, int row, int col) {
        this.farmTilesBtn[row][col].setText(s);
    }
}
