import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

import javax.swing.border.EmptyBorder;

/**
 * The GameView class.
 */
public class GameView {
    /**
     * The frame of the game.
     */
    private final JFrame mainFrame;
    /**
     * The main panel of the game.
     * This panel contains all other panels.
     */
    private final JPanel mainPanel;
    /**
     * The frame of the book.
     */
    private final JFrame bookFrame = new JFrame("Book");

    /**
     * The panel containing user feedback when a crop is harvested.
     */
    private JPanel feedbackPanel;
    /**
     * The panel containing the action buttons.
     */
    private JPanel actionPanel;

    // Player information panel elements
    /**
     * The label containing the player's level and experience.
     */
    private final JLabel levelAndExperienceLbl = new JLabel();
    /**
     * The label containing the player's objectcoins.
     */
    private final JLabel objectCoinsLbl = new JLabel();
    /**
     * The label containing the player's farmer type.
     */
    private final JLabel farmerTypeLbl = new JLabel();
    /**
     * The label containing the current day.
     */
    private final JLabel dayLbl = new JLabel();

    /**
     * The button responsible for advancing the day.
     */
    private final JButton sleepBtn = new JButton();
    /**
     * The button responsible for upgrading the farmer type.
     */
    private final JButton upgradeFarmerBtn = new JButton();
    /**
     * The button responsible for showing the book.
     */
    private final JButton bookBtn = new JButton();

    // Farm panel elements
    /**
     * The 2D array of buttons representing the farm tiles.
     */
    JButton[][] farmTilesBtn = new JButton[5][10];

    /**
     * Constructor for the GameView class
     */
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

        initializeTopPanel();
        initializeFarmPanel();

        // Action panel (panel with buttons) replaces south panel (blank panel) when there is a valid action in the tile
        initializeBottomPanel();
        this.updateBottomPanel();

        this.mainFrame.add(this.mainPanel, BorderLayout.CENTER);
        this.mainFrame.setVisible(true);

        // Book frame
        JLabel tableLbl = new JLabel();

        tableLbl.setIcon(scaleImage(Game.IMG_TABLE, 900, 600));

        this.bookFrame.setSize(900, 600);
        this.bookFrame.setResizable(false);
        this.bookFrame.setLocationRelativeTo(null);
        this.bookFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        this.bookFrame.add(tableLbl);
    }

    /**
     * Initializes the top panel (player information panel).
     */
    public void initializeTopPanel() {
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

        this.sleepBtn.setPreferredSize(new Dimension(195,25));
        this.sleepBtn.setIcon(scaleImage(Game.IMG_BUTTON_SLEEP, 195, 25));

        this.upgradeFarmerBtn.setPreferredSize(new Dimension(195,25));
        this.upgradeFarmerBtn.setIcon(scaleImage(Game.IMG_BUTTON_UPGRADE, 195, 25));

        this.bookBtn.setPreferredSize(new Dimension(195,25));
        this.bookBtn.setIcon(scaleImage(Game.IMG_BUTTON_BOOK, 195, 25));

        rightPanel.setPreferredSize(new Dimension(600,25));
        rightPanel.setOpaque(false);

        rightPanel.add(sleepBtn);
        rightPanel.add(upgradeFarmerBtn);
        rightPanel.add(bookBtn);

        mainPanel.add(leftPanel, BorderLayout.LINE_START);
        mainPanel.add(rightPanel, BorderLayout.LINE_END);

        this.mainPanel.add(mainPanel, BorderLayout.PAGE_START);
    }

    /**
     * Initializes the farm panel (panel with the farm tiles).
     */
    public void initializeFarmPanel() {
        JPanel farmPanel = new JPanel(new GridLayout(5, 10));
        farmPanel.setBorder(new EmptyBorder(10, 0, 5, 0));
        farmPanel.setBackground(Color.decode("#313131"));

        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 10; j++) {
                farmTilesBtn[i][j] = new JButton();
                farmTilesBtn[i][j].putClientProperty("location", new int[] {i, j});
                farmTilesBtn[i][j].putClientProperty("is_SELECTED", false);
                
                farmTilesBtn[i][j].setIcon(scaleImage(Tile.IMG_TILE_UNPLOWED, 116, 94));
                farmTilesBtn[i][j].setBorderPainted(false);
                
                farmPanel.add(farmTilesBtn[i][j]);
            }
        }

        this.mainPanel.add(farmPanel, BorderLayout.CENTER);
    }

    /**
     * Initializes the bottom panel (panel with the action buttons).
     */
    public void initializeBottomPanel() {
        this.actionPanel = new JPanel(new FlowLayout());
        this.actionPanel.setPreferredSize(new Dimension(1200, 100));
        this.actionPanel.setBackground(Color.decode("#313131"));
        this.actionPanel.setOpaque(true);

        this.feedbackPanel = new JPanel(new FlowLayout());
        this.feedbackPanel.setPreferredSize(new Dimension(1200, 100));
        this.feedbackPanel.setBackground(Color.decode("#313131"));
        this.feedbackPanel.setOpaque(true);
    }

    /**
     * Initializes the listeners for the buttons at the top of the screen.
     * @param sleepListener     The listener for the sleep button.
     * @param upgradeListener   The listener for the upgrade button.
     * @param bookListener      The listener for the book button.
     */
    public void initializeMiscListener(ActionListener sleepListener, ActionListener upgradeListener, ActionListener bookListener) {
        this.sleepBtn.addActionListener(sleepListener);

        this.upgradeFarmerBtn.addActionListener(upgradeListener);

        this.bookBtn.addActionListener(bookListener);
    }

    /* TOP PANEL METHODS */

    /**
     * Updates the top panel (player information panel).
     * @param level             The level of the player.
     * @param experience        The experience of the player.
     * @param objectCoins       The object coins of the player.
     * @param farmerTypeList    The list of farmer types.
     * @param farmerTypeLevel   The level of the farmer type.
     * @param day               The day of the game.
     */
    public void updateTopPanel(int level, float experience, float objectCoins, ArrayList<FarmerType> farmerTypeList, int farmerTypeLevel, int day) {
        this.levelAndExperienceLbl.setText("Level: " + level + " (" + experience + ")");
        this.objectCoinsLbl.setText("ObjectCoins: " + objectCoins);

        FarmerType currFarmerType = farmerTypeList.get(farmerTypeLevel);

        if (farmerTypeLevel == 3) {
            this.farmerTypeLbl.setText(currFarmerType.typeName() + " (MAX)");
        } else {
            this.farmerTypeLbl.setText(currFarmerType.typeName() +
            " (Next: Lvl: " + farmerTypeList.get(farmerTypeLevel + 1).levelRequirement() +
            " /Cost: " + farmerTypeList.get(farmerTypeLevel + 1).registrationFee()  + ")");
        }

        this.dayLbl.setText("Day " + day);
    }

    /**
     * Updates whether the upgrade button is enabled.
     * @param enabled   Whether the upgrade button is enabled.
     */
    public void setUpgradeFarmerButtonEnabled(boolean enabled) {
        this.upgradeFarmerBtn.setEnabled(enabled);
    }

    /**
     * Creates a new frame for the book.
     */
    public void showBook() {
        bookFrame.setVisible(true);
    }

    /* FARM PANEL METHODS */

    /**
     * Updates the farm tile button's image.
     * @param state     The state of the farm tile.
     * @param row       The row of the farm tile button.
     * @param column    The column of the farm tile button.
     */
    public void setTileIcon(String state, int row, int column) {
        if ((boolean) this.farmTilesBtn[row][column].getClientProperty("is_SELECTED")) {
            switch (state) {
                case "unplowed" -> this.farmTilesBtn[row][column].setIcon(scaleImage(Tile.IMG_TILE_UNPLOWED_SELECTED, 116, 94));
                case "plowed" -> this.farmTilesBtn[row][column].setIcon(scaleImage(Tile.IMG_TILE_PLOWED_SELECTED, 116, 94));
                case "rock" -> this.farmTilesBtn[row][column].setIcon(scaleImage(Tile.IMG_TILE_ROCK_SELECTED, 116, 94));
                case "withered" -> this.farmTilesBtn[row][column].setIcon(scaleImage(Tile.IMG_TILE_WITHERED_SELECTED, 116, 94));
                case "Turnip" -> this.farmTilesBtn[row][column].setIcon(scaleImage(Crop.IMG_TILE_TURNIP_SELECTED, 116, 94));
                case "Carrot" -> this.farmTilesBtn[row][column].setIcon(scaleImage(Crop.IMG_TILE_CARROT_SELECTED, 116, 94));
                case "Potato" -> this.farmTilesBtn[row][column].setIcon(scaleImage(Crop.IMG_TILE_POTATO_SELECTED, 116, 94));
                case "Rose" -> this.farmTilesBtn[row][column].setIcon(scaleImage(Crop.IMG_TILE_ROSE_SELECTED, 116, 94));
                case "Tulips" -> this.farmTilesBtn[row][column].setIcon(scaleImage(Crop.IMG_TILE_TULIPS_SELECTED, 116, 94));
                case "Sunflower" -> this.farmTilesBtn[row][column].setIcon(scaleImage(Crop.IMG_TILE_SUNFLOWER_SELECTED, 116, 94));
                case "Mango" -> this.farmTilesBtn[row][column].setIcon(scaleImage(Crop.IMG_TILE_MANGO_SELECTED, 116, 94));
                case "Apple" -> this.farmTilesBtn[row][column].setIcon(scaleImage(Crop.IMG_TILE_APPLE_SELECTED, 116, 94));
            }
        } else {
            switch (state) {
                case "unplowed" -> this.farmTilesBtn[row][column].setIcon(scaleImage(Tile.IMG_TILE_UNPLOWED, 116, 94));
                case "plowed" -> this.farmTilesBtn[row][column].setIcon(scaleImage(Tile.IMG_TILE_PLOWED, 116, 94));
                case "rock" -> this.farmTilesBtn[row][column].setIcon(scaleImage(Tile.IMG_TILE_ROCK, 116, 94));
                case "withered" -> this.farmTilesBtn[row][column].setIcon(scaleImage(Tile.IMG_TILE_WITHERED, 116, 94));
                case "Turnip" -> this.farmTilesBtn[row][column].setIcon(scaleImage(Crop.IMG_TILE_TURNIP, 116, 94));
                case "Carrot" -> this.farmTilesBtn[row][column].setIcon(scaleImage(Crop.IMG_TILE_CARROT, 116, 94));
                case "Potato" -> this.farmTilesBtn[row][column].setIcon(scaleImage(Crop.IMG_TILE_POTATO, 116, 94));
                case "Rose" -> this.farmTilesBtn[row][column].setIcon(scaleImage(Crop.IMG_TILE_ROSE, 116, 94));
                case "Tulips" -> this.farmTilesBtn[row][column].setIcon(scaleImage(Crop.IMG_TILE_TULIPS, 116, 94));
                case "Sunflower" -> this.farmTilesBtn[row][column].setIcon(scaleImage(Crop.IMG_TILE_SUNFLOWER, 116, 94));
                case "Mango" -> this.farmTilesBtn[row][column].setIcon(scaleImage(Crop.IMG_TILE_MANGO, 116, 94));
                case "Apple" -> this.farmTilesBtn[row][column].setIcon(scaleImage(Crop.IMG_TILE_APPLE, 116, 94));
            }
        }
    }

    /**
     * Changes whether a farm tile button is selected.
     * @param oldRow        The row coordinate of the old selected farm tile button.
     * @param oldColumn     The column coordinate of the old selected farm tile button.
     * @param newRow        The row coordinate of the new selected farm tile button.
     * @param newColumn     The column coordinate of the new selected farm tile button.
     */
    public void setIsSelected(int oldRow, int oldColumn, int newRow, int newColumn) {
        if (oldRow != -1 && oldColumn != -1) farmTilesBtn[oldRow][oldColumn].putClientProperty("is_SELECTED", false);
        if (newRow != -1 && newColumn != -1) farmTilesBtn[newRow][newColumn].putClientProperty("is_SELECTED", true);
    }

    /**
     * Changes the listener of a farm tile button.
     * @param farmTileListener  The listener for the farm tile button.
     * @param row               The row of the farm tile button.
     * @param column            The column of the farm tile button.
     */
    public void changeFarmTileListener(ActionListener farmTileListener, int row, int column) {
        for (ActionListener actionListener : this.farmTilesBtn[row][column].getActionListeners()) {
            this.farmTilesBtn[row][column].removeActionListener(actionListener);
        }

        this.farmTilesBtn[row][column].addActionListener(farmTileListener);
    }

    /* BOTTOM PANEL METHODS */

    /**
     * Updates the bottom panel (panel with the action buttons).
     */
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
            this.mainPanel.remove(this.feedbackPanel);
            this.mainPanel.add(this.actionPanel, BorderLayout.PAGE_END);
        } else {
            this.mainPanel.remove(this.actionPanel);
            this.mainPanel.add(this.feedbackPanel, BorderLayout.PAGE_END);
        }

        this.mainFrame.revalidate();
        this.mainFrame.repaint();
    }

    /**
     * Resets the bottom panel (panel with the action buttons).
     */
    public void resetBottomPanel() {
        this.actionPanel.removeAll();
        this.feedbackPanel.removeAll();
    }

    /**
     * Adds a button to the bottom panel.
     * @param actionListener    The action listener of the button.
     * @param name              The name of the button.
     */
    public void addActionButton(ActionListener actionListener, String name) {
        JButton actionBtn = new JButton();

        actionBtn.setBorderPainted(false);
        actionBtn.setMargin(new Insets(0, 0, 0, 0));
        actionBtn.setPreferredSize(new Dimension(95, 95));
        actionBtn.addActionListener(actionListener);

        switch (name) {
            case "water" -> actionBtn.setIcon(scaleImage(Player.IMG_BUTTON_WATER, 95, 95));
            case "fertilizer" -> actionBtn.setIcon(scaleImage(Player.IMG_BUTTON_FERTILIZE, 95, 95));
            case "harvest" -> actionBtn.setIcon(scaleImage(Player.IMG_BUTTON_HARVEST, 95, 95));
            case "plow" -> actionBtn.setIcon(scaleImage(Player.IMG_BUTTON_PLOW, 95, 95));
            case "shovel" -> actionBtn.setIcon(scaleImage(Player.IMG_BUTTON_SHOVEL, 95, 95));
            case "pickaxe" -> actionBtn.setIcon(scaleImage(Player.IMG_BUTTON_PICKAXE, 95, 95));
            case "Turnip" -> actionBtn.setIcon(scaleImage(Player.IMG_BUTTON_TURNIP, 116, 94));
            case "Carrot" -> actionBtn.setIcon(scaleImage(Player.IMG_BUTTON_CARROT, 116, 94));
            case "Potato" -> actionBtn.setIcon(scaleImage(Player.IMG_BUTTON_POTATO, 116, 94));
            case "Rose" -> actionBtn.setIcon(scaleImage(Player.IMG_BUTTON_ROSE, 116, 94));
            case "Tulips" -> actionBtn.setIcon(scaleImage(Player.IMG_BUTTON_TULIPS, 116, 94));
            case "Sunflower" -> actionBtn.setIcon(scaleImage(Player.IMG_BUTTON_SUNFLOWER, 116, 94));
            case "Mango" -> actionBtn.setIcon(scaleImage(Player.IMG_BUTTON_MANGO, 116, 94));
            case "Apple" -> actionBtn.setIcon(scaleImage(Player.IMG_BUTTON_APPLE, 116, 94));
            default -> {
            }
            // none
        }
        
        this.actionPanel.add(actionBtn);
    }

    /**
     * Updates the feedback panel after harvesting.
     * @param finalPrice    The final price of the harvest.
     * @param crop          The crop that was harvested.
     */
    public void updateFeedbackPanel(float finalPrice, Crop crop){
        JLabel resultsLbl = new JLabel();
        resultsLbl.setForeground(Color.white);

        resultsLbl.setText("Harvested " + crop.getProduce() + " " + crop.getSeed().name() +
            " and sold for " + finalPrice + " ObjectCoins");

        
        this.feedbackPanel.add(resultsLbl);
    }

    /* FOR END GAME MECHANICS */

    /**
     * Show the end game screen.
     * @param reason            The reason why the game ended.
     * @param restartListener   The listener for the restart button.
     */
    public void showEndGame(String reason, ActionListener restartListener) {
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
        JButton restartBtn = new JButton();
        restartBtn.addActionListener(restartListener);
        restartBtn.setPreferredSize(new Dimension(195,25));
        restartBtn.setIcon(scaleImage(Game.IMG_BUTTON_RESTART, 195, 25));
        buttonPanel.add(restartBtn);

        // Exit button
        JButton exitBtn = new JButton();
        exitBtn.addActionListener(e -> System.exit(0));
        exitBtn.setPreferredSize(new Dimension(195,25));
        exitBtn.setIcon(scaleImage(Game.IMG_BUTTON_EXIT, 195, 25));
        buttonPanel.add(exitBtn);

        

        // Add button panel
        this.mainPanel.add(buttonPanel, BorderLayout.PAGE_END);

        this.mainFrame.revalidate();
        this.mainFrame.repaint();
    }

    /**
     * Resets the game view.
     */
    public void resetView() {
        this.mainPanel.removeAll();
        this.initializeTopPanel();
        this.initializeFarmPanel();
        this.initializeBottomPanel();
        this.updateBottomPanel();
    }

    /**
     * Scales an image using the given width and height.
     * @param image     The image to be scaled.
     * @param width     The width of the scaled image.
     * @param height    The height of the scaled image.
     * @return        The scaled image.
     */
    private Icon scaleImage(ImageIcon image, int width, int height) {
        return new ImageIcon((image.getImage()).getScaledInstance(width, height, java.awt.Image.SCALE_DEFAULT));
    }
}
