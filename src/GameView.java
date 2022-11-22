import java.awt.Color;
import java.awt.Component;
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
    private JLabel levelAndExperienceLbl = new JLabel();
    private JLabel objectCoinsLbl = new JLabel();
    private JLabel farmerTypeLbl = new JLabel();
    private JLabel dayLbl = new JLabel();

    private JButton sleepBtn;
    private JButton upgradeFarmerBtn;

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

        // Action panel (buttons) replaces south panel when there is a valid action in the tile
        initializeBottomPanel();
        this.updateBottomPanel();

        //this.mainPanel.add(this.southPanel, BorderLayout.PAGE_END);

        this.mainFrame.add(this.mainPanel, BorderLayout.CENTER);
        this.mainFrame.setVisible(true);
    }

    public void initializeInfoPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel leftPanel = new JPanel(new BorderLayout());
        JPanel leftLeftPanel = new JPanel(new BorderLayout());
        JPanel leftRightPanel = new JPanel(new BorderLayout());
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        this.sleepBtn = new JButton("Sleep");
        this.upgradeFarmerBtn = new JButton("Upgrade Farmer");

        mainPanel.setPreferredSize(new Dimension(1180, 31));
        mainPanel.setBackground(Color.decode("#414141"));
        mainPanel.setOpaque(true);

        leftPanel.setPreferredSize(new Dimension(600,31));
        leftPanel.setOpaque(false);

        leftLeftPanel.setPreferredSize(new Dimension(300,31));
        leftLeftPanel.setOpaque(false);

        leftRightPanel.setPreferredSize(new Dimension(300,31));
        leftRightPanel.setOpaque(false);

        rightPanel.setOpaque(false);

        leftLeftPanel.add(this.levelAndExperienceLbl, BorderLayout.PAGE_START);
        leftLeftPanel.add(this.objectCoinsLbl, BorderLayout.CENTER);

        leftRightPanel.add(this.farmerTypeLbl, BorderLayout.PAGE_START);
        leftRightPanel.add(this.dayLbl, BorderLayout.CENTER);

        leftPanel.add(leftLeftPanel, BorderLayout.LINE_START);
        leftPanel.add(leftRightPanel, BorderLayout.LINE_END);

        this.sleepBtn.setPreferredSize(new Dimension(292,25));
        this.upgradeFarmerBtn.setPreferredSize(new Dimension(292,25));

        rightPanel.setPreferredSize(new Dimension(600,25));
        rightPanel.add(sleepBtn);
        rightPanel.add(upgradeFarmerBtn);

        mainPanel.add(leftPanel, BorderLayout.LINE_START);
        mainPanel.add(rightPanel, BorderLayout.LINE_END);

        this.mainPanel.add(mainPanel, BorderLayout.PAGE_START);
    }

    public void initializeFarmPanel() {
        this.farmPanel = new JPanel(new GridLayout(5, 10));
        this.farmPanel.setBorder(new EmptyBorder(10, 0, 0, 0));
        this.farmPanel.setBackground(Color.decode("#313131"));

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

    public void initializeBottomPanel() {
        this.actionPanel = new JPanel(new FlowLayout());
        this.actionPanel.setPreferredSize(new Dimension(1200, 100));
        this.actionPanel.setBackground(Color.decode("#313131"));
        this.actionPanel.setOpaque(true);

        this.southPanel = new JPanel(new BorderLayout());
        this.southPanel.setPreferredSize(new Dimension(1200, 100));
        this.southPanel.setBackground(Color.decode("#313131"));
        this.southPanel.setOpaque(true);
    }

    public void updateBottomPanel() {
        Component[] componentList = this.actionPanel.getComponents();
        boolean hasButton = false;

        for(Component c : componentList) {
            if (c instanceof JButton) {
                hasButton = true;
            }
        }

        if (hasButton) {
            this.mainFrame.remove(this.southPanel);
            this.mainFrame.add(this.actionPanel, BorderLayout.PAGE_END);
        } else {
            this.mainFrame.remove(this.actionPanel);
            this.mainFrame.add(this.southPanel, BorderLayout.PAGE_END);
        }

        this.mainFrame.revalidate();
        this.mainFrame.repaint();
    }

    public void changeFarmTileListener(ActionListener e, int row, int col) {
        for (ActionListener actionListener : this.farmTilesBtn[row][col].getActionListeners()) {
            this.farmTilesBtn[row][col].removeActionListener(actionListener);
        }

        this.farmTilesBtn[row][col].addActionListener(e);
    }

    public void initializeMiscListener(ActionListener s, ActionListener e) {
        this.sleepBtn.addActionListener(s);
        this.upgradeFarmerBtn.addActionListener(e);
    }

    public void resetActionPanel() {
        this.actionPanel.removeAll();
    }

    public void addActionButton(ActionListener e, String name) {
        JButton actionBtn = new JButton(name);

        actionBtn.setPreferredSize(new Dimension(104, 104));
        actionBtn.addActionListener(e);
        this.actionPanel.add(actionBtn);
    }

    public void updatePlayerInfo(int level, float experience, float objectCoins, FarmerType farmerType, int day) {
        this.levelAndExperienceLbl.setText("Level: " + level + " (" + experience + ")");
        this.objectCoinsLbl.setText("ObjectCoins: " + objectCoins);
        this.farmerTypeLbl.setText(farmerType.getTypeName());
        this.dayLbl.setText("Day " + day);
    }

    // temp
    public void setTileText(String s, int row, int col) {
        this.farmTilesBtn[row][col].setText(s);
    }
}
