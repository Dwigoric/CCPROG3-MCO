import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

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

    private JButton sleepBtn = new JButton("Sleep");
    private JButton upgradeFarmerBtn = new JButton("Upgrade Farmer");

    // Farm panel elements
    JButton[][] farmTilesBtn = new JButton[5][10];
    ActionListener farmTileListener;

    // GUI elements
    // Farm GUI
    private ImageIcon imgTileUnplowed = new ImageIcon("res/tile_unplowed.png");
    private ImageIcon imgTileUnplowedSelected = new ImageIcon("res/tile_unplowed_selected.png");
    private ImageIcon imgTilePlowed = new ImageIcon("res/tile_plowed.png");
    private ImageIcon imgTileRock = new ImageIcon("res/tile_rock.png");
    private ImageIcon imgTileWithered = new ImageIcon("res/tile_withered.png");

    private ImageIcon imgTileTurnip = new ImageIcon("res/tile_turnip.png");
    private ImageIcon imgTileCarrot = new ImageIcon("res/tile_carrot.png");
    private ImageIcon imgTilePotato = new ImageIcon("res/tile_potato.png");
    private ImageIcon imgTileRose = new ImageIcon("res/tile_rose.png");
    private ImageIcon imgTileTulips = new ImageIcon("res/tile_tulips.png");
    private ImageIcon imgTileSunflower = new ImageIcon("res/tile_sunflower.png");
    private ImageIcon imgTileMango = new ImageIcon("res/tile_mango.png");
    private ImageIcon imgTileApple = new ImageIcon("res/tile_apple.png");

    // Action Buttons GUI
    private ImageIcon imgWater = new ImageIcon("res/button_water.png");
    private ImageIcon imgFertilize = new ImageIcon("res/button_fertilize.png");
    private ImageIcon imgHarvest = new ImageIcon("res/button_harvest.png");
    private ImageIcon imgPlow = new ImageIcon("res/button_plow.png");
    private ImageIcon imgShovel = new ImageIcon("res/button_shovel.png");
    private ImageIcon imgPickaxe = new ImageIcon("res/button_pickaxe.png");

    private ImageIcon imgBtnTurnip = new ImageIcon("res/button_turnip.png");
    private ImageIcon imgBtnCarrot = new ImageIcon("res/button_carrot.png");
    private ImageIcon imgBtnPotato = new ImageIcon("res/button_potato.png");
    private ImageIcon imgBtnRose = new ImageIcon("res/button_rose.png");
    private ImageIcon imgBtnTulips = new ImageIcon("res/button_tulips.png");
    private ImageIcon imgBtnSunflower = new ImageIcon("res/button_sunflower.png");
    private ImageIcon imgBtnMango = new ImageIcon("res/button_mango.png");
    private ImageIcon imgBtnApple = new ImageIcon("res/button_apple.png");
    
    public GameView() {
        this.mainFrame = new JFrame("MyFarm");

        this.mainFrame.setSize(1200, 675);
        this.mainFrame.setResizable(false);
        this.mainFrame.setLocationRelativeTo(null);
        this.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        this.mainFrame.setLayout(new BorderLayout(5,5));

        // Panel that contains all the panels
        this.mainPanel = new JPanel(new BorderLayout());
        this.mainPanel.setBackground(Color.decode("#313131"));
        this.mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        initializeInfoPanel();
        initializeFarmPanel();

        // Action panel (panel with buttons) replaces south panel (blank panel) when there is a valid action in the tile
        initializeBottomPanel();
        this.updateBottomPanel();

        this.mainFrame.add(this.mainPanel, BorderLayout.CENTER);
        this.mainFrame.setVisible(true);
    }

    public void initializeInfoPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel leftPanel = new JPanel(new BorderLayout());
        JPanel leftLeftPanel = new JPanel(new BorderLayout());
        JPanel leftRightPanel = new JPanel(new BorderLayout());

        JPanel rightPanel = new JPanel(new GridBagLayout());

        this.levelAndExperienceLbl.setForeground(Color.WHITE);
        this.objectCoinsLbl.setForeground(Color.WHITE);
        this.farmerTypeLbl.setForeground(Color.WHITE);
        this.dayLbl.setForeground(Color.WHITE);

        mainPanel.setPreferredSize(new Dimension(1180, 31));
        mainPanel.setBackground(Color.decode("#414141"));
        mainPanel.setOpaque(true);

        leftLeftPanel.setPreferredSize(new Dimension(300,31));
        leftLeftPanel.setOpaque(false);

        leftLeftPanel.add(this.levelAndExperienceLbl, BorderLayout.PAGE_START);
        leftLeftPanel.add(this.objectCoinsLbl, BorderLayout.CENTER);

        leftRightPanel.setPreferredSize(new Dimension(300,31));
        leftRightPanel.setOpaque(false);

        leftRightPanel.add(this.farmerTypeLbl, BorderLayout.PAGE_START);
        leftRightPanel.add(this.dayLbl, BorderLayout.CENTER);

        leftPanel.setPreferredSize(new Dimension(600,31));
        leftPanel.setOpaque(false);

        leftPanel.add(leftLeftPanel, BorderLayout.LINE_START);
        leftPanel.add(leftRightPanel, BorderLayout.LINE_END);

        this.sleepBtn.setPreferredSize(new Dimension(292,25));
        this.upgradeFarmerBtn.setPreferredSize(new Dimension(292,25));

        rightPanel.setPreferredSize(new Dimension(600,25));
        rightPanel.setOpaque(false);

        rightPanel.add(sleepBtn);
        rightPanel.add(upgradeFarmerBtn);

        mainPanel.add(leftPanel, BorderLayout.LINE_START);
        mainPanel.add(rightPanel, BorderLayout.LINE_END);

        this.mainPanel.add(mainPanel, BorderLayout.PAGE_START);
    }

    public void initializeFarmPanel() {
        this.farmPanel = new JPanel(new GridLayout(5, 10));
        this.farmPanel.setBorder(new EmptyBorder(10, 0, 5, 0));
        this.farmPanel.setBackground(Color.decode("#313131"));

        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 10; j++) {
                farmTilesBtn[i][j] = new JButton();
                farmTilesBtn[i][j].addActionListener(farmTileListener);
                farmTilesBtn[i][j].putClientProperty("location", new int[] {i, j});
                farmTilesBtn[i][j].putClientProperty("isSelected", false);
                
                farmTilesBtn[i][j].setIcon(scaleImage(imgTileUnplowed, 116, 94));
                farmTilesBtn[i][j].setBorderPainted(false);
                
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

    public void updatePlayerInfo(int level, float experience, float objectCoins, FarmerType farmerType, int day) {
        this.levelAndExperienceLbl.setText("Level: " + level + " (" + experience + ")");
        this.objectCoinsLbl.setText("ObjectCoins: " + objectCoins);
        this.farmerTypeLbl.setText(farmerType.getTypeName());
        this.dayLbl.setText("Day " + day);
    }

    public void updateBottomPanel() {
        Component[] componentList = this.actionPanel.getComponents();
        boolean hasButton = false;

        for(Component c : componentList) {
            if (c instanceof JButton) {
                hasButton = true;
                break;
            }
        }

        if (hasButton) {
            this.mainPanel.remove(this.southPanel);
            this.mainPanel.add(this.actionPanel, BorderLayout.PAGE_END);
        } else {
            this.mainPanel.remove(this.actionPanel);
            this.mainPanel.add(this.southPanel, BorderLayout.PAGE_END);
        }

        this.mainFrame.revalidate();
        this.mainFrame.repaint();
    }

    public void resetActionPanel() {
        this.actionPanel.removeAll();
    }

    public void changeMiscListener(ActionListener s, ActionListener e) {
        this.sleepBtn.addActionListener(s);
        this.upgradeFarmerBtn.addActionListener(e);
        this.upgradeFarmerBtn.setEnabled(false);
    }

    public void changeFarmTileListener(ActionListener e, int row, int col) {
        for (ActionListener actionListener : this.farmTilesBtn[row][col].getActionListeners()) {
            this.farmTilesBtn[row][col].removeActionListener(actionListener);
        }

        this.farmTilesBtn[row][col].addActionListener(e);
    }

    public void addActionButton(ActionListener e, String name) {
        JButton actionBtn = new JButton();

        actionBtn.setBorderPainted(false);
        actionBtn.setMargin(new Insets(0, 0, 0, 0));
        actionBtn.setPreferredSize(new Dimension(95, 95));
        actionBtn.addActionListener(e);

        switch (name) {
            case "water":
                actionBtn.setIcon(scaleImage(imgWater, 95, 95));
                break;
            case "fertilizer":
                actionBtn.setIcon(scaleImage(imgFertilize, 95, 95));
                break;
            case "harvest":
                actionBtn.setIcon(scaleImage(imgHarvest, 95, 95));
                break;
            case "plow":
                actionBtn.setIcon(scaleImage(imgPlow, 95, 95));
                break;
            case "shovel":
                actionBtn.setIcon(scaleImage(imgShovel, 95, 95));
                break;
            case "pickaxe":
                actionBtn.setIcon(scaleImage(imgPickaxe, 95, 95));
                break;
                case "Turnip":
                actionBtn.setIcon(scaleImage(imgBtnTurnip, 116, 94));
                break;
            case "Carrot":
                actionBtn.setIcon(scaleImage(imgBtnCarrot, 116, 94));
                break;
            case "Potato":
                actionBtn.setIcon(scaleImage(imgBtnPotato, 116, 94));
                break;
            case "Rose":
                actionBtn.setIcon(scaleImage(imgBtnRose, 116, 94));
                break;
            case "Tulips":
                actionBtn.setIcon(scaleImage(imgBtnTulips, 116, 94));
                break;
            case "Sunflower":
                actionBtn.setIcon(scaleImage(imgBtnSunflower, 116, 94));
                break;
            case "Mango":
                actionBtn.setIcon(scaleImage(imgBtnMango, 116, 94));
                break;
            case "Apple":
                actionBtn.setIcon(scaleImage(imgBtnApple, 116, 94));
                break;
            default:
                // none
                break;
        }
        
        this.actionPanel.add(actionBtn);
    }

    public void setTileIcon(String s, int row, int col) {
        if ((boolean) this.farmTilesBtn[row][col].getClientProperty("isSelected")) {

        } else {
            switch(s) {
                case "unplowed":
                    this.farmTilesBtn[row][col].setIcon(scaleImage(imgTileUnplowed, 116, 94));
                    break;
                case "plowed":
                    this.farmTilesBtn[row][col].setIcon(scaleImage(imgTilePlowed, 116, 94));
                    break;
                case "rock":
                    this.farmTilesBtn[row][col].setIcon(scaleImage(imgTileRock, 116, 94));
                    break;
                case "withered":
                    this.farmTilesBtn[row][col].setIcon(scaleImage(imgTileWithered, 116, 94));
                    break;
                case "Turnip":
                    this.farmTilesBtn[row][col].setIcon(scaleImage(imgTileTurnip, 116, 94));
                    break;
                case "Carrot":
                    this.farmTilesBtn[row][col].setIcon(scaleImage(imgTileCarrot, 116, 94));
                    break;
                case "Potato":
                    this.farmTilesBtn[row][col].setIcon(scaleImage(imgTilePotato, 116, 94));
                    break;
                case "Rose":
                    this.farmTilesBtn[row][col].setIcon(scaleImage(imgTileRose, 116, 94));
                    break;
                case "Tulips":
                    this.farmTilesBtn[row][col].setIcon(scaleImage(imgTileTulips, 116, 94));
                    break;
                case "Sunflower":
                    this.farmTilesBtn[row][col].setIcon(scaleImage(imgTileSunflower, 116, 94));
                    break;
                case "Mango":
                    this.farmTilesBtn[row][col].setIcon(scaleImage(imgTileMango, 116, 94));
                    break;
                case "Apple":
                    this.farmTilesBtn[row][col].setIcon(scaleImage(imgTileApple, 116, 94));
                    break;
            }
        }
    }

    private Icon scaleImage(ImageIcon i, int width, int height) {
        return new ImageIcon((i.getImage()).getScaledInstance(width, height, java.awt.Image.SCALE_DEFAULT));
    }

    // temp
    public void setTileText(String s, int row, int col) {
        this.farmTilesBtn[row][col].setText(s);
    }

    public void setUpgradeFarmerButtonEnabled(boolean enabled) {
        this.upgradeFarmerBtn.setEnabled(enabled);
    }

    /* FOR END GAME MECHANICS */
    public void endGame(String reason, ActionListener restartListener) {
        this.mainPanel.removeAll();

        // Set up end game panel
        JPanel endGamePanel = new JPanel(new GridBagLayout());
        endGamePanel.setBackground(Color.decode("#313131"));

        JLabel endGameLbl = new JLabel(reason);
        endGameLbl.setForeground(Color.decode("#bdc3c7"));
        endGameLbl.setFont(new Font("Arial", Font.BOLD, 30));

        endGamePanel.add(endGameLbl);

        // Add end game text
        this.mainPanel.add(endGamePanel, BorderLayout.CENTER);


        // Set up button panel
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBackground(Color.decode("#313131"));

        // Restart button
        JButton restartBtn = new JButton("Restart");
        restartBtn.addActionListener(restartListener);
        buttonPanel.add(restartBtn);

        // Exit button
        JButton exitBtn = new JButton("Exit");
        exitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        buttonPanel.add(exitBtn);

        // Add button panel
        this.mainPanel.add(buttonPanel, BorderLayout.PAGE_END);

        this.mainFrame.revalidate();
        this.mainFrame.repaint();
    }

    public void reset() {
        this.mainPanel.removeAll();
        this.initializeInfoPanel();
        this.initializeFarmPanel();
        this.initializeBottomPanel();
        this.updateBottomPanel();
    }
}
