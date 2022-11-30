import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

import javax.swing.border.EmptyBorder;

/**
 * The GameView class.
 */
public class GameView {
    private final JFrame mainFrame;
    private final JPanel mainPanel;

    private JPanel feedbackPanel;
    private JPanel actionPanel;

    // Player information panel elements
    private final JLabel levelAndExperienceLbl = new JLabel();
    private final JLabel objectCoinsLbl = new JLabel();
    private final JLabel farmerTypeLbl = new JLabel();
    private final JLabel dayLbl = new JLabel();

    private final JButton sleepBtn = new JButton();
    private final JButton upgradeFarmerBtn = new JButton();
    private final JButton bookBtn = new JButton();

    // Farm panel elements
    JButton[][] farmTilesBtn = new JButton[5][10];
    ActionListener farmTileListener;

    // GUI elements
    // Info GUI
    private final ImageIcon imgBtnSleep = new ImageIcon("res/button_sleep.png");
    private final ImageIcon imgBtnUpgrade = new ImageIcon("res/button_upgrade.png");
    private final ImageIcon imgBtnBook = new ImageIcon("res/button_book.png");

    // Farm GUI
    private final ImageIcon imgTileUnplowed = new ImageIcon("res/tile_unplowed.png");
    private final ImageIcon imgTileUnplowedSelected = new ImageIcon("res/tile_unplowed_selected.png");
    private final ImageIcon imgTilePlowed = new ImageIcon("res/tile_plowed.png");
    private final ImageIcon imgTilePlowedSelected = new ImageIcon("res/tile_plowed_selected.png");
    private final ImageIcon imgTileRock = new ImageIcon("res/tile_rock.png");
    private final ImageIcon imgTileRockSelected = new ImageIcon("res/tile_rock_selected.png");
    private final ImageIcon imgTileWithered = new ImageIcon("res/tile_withered.png");
    private final ImageIcon imgTileWitheredSelected = new ImageIcon("res/tile_withered_selected.png");

    private final ImageIcon imgTileTurnip = new ImageIcon("res/tile_turnip.png");
    private final ImageIcon imgTileTurnipSelected = new ImageIcon("res/tile_turnip_selected.png");
    private final ImageIcon imgTileCarrot = new ImageIcon("res/tile_carrot.png");
    private final ImageIcon imgTileCarrotSelected = new ImageIcon("res/tile_carrot_selected.png");
    private final ImageIcon imgTilePotato = new ImageIcon("res/tile_potato.png");
    private final ImageIcon imgTilePotatoSelected = new ImageIcon("res/tile_potato_selected.png");
    private final ImageIcon imgTileRose = new ImageIcon("res/tile_rose.png");
    private final ImageIcon imgTileRoseSelected = new ImageIcon("res/tile_rose_selected.png");
    private final ImageIcon imgTileTulips = new ImageIcon("res/tile_tulips.png");
    private final ImageIcon imgTileTulipsSelected = new ImageIcon("res/tile_tulips_selected.png");
    private final ImageIcon imgTileSunflower = new ImageIcon("res/tile_sunflower.png");
    private final ImageIcon imgTileSunflowerSelected = new ImageIcon("res/tile_sunflower_selected.png");
    private final ImageIcon imgTileMango = new ImageIcon("res/tile_mango.png");
    private final ImageIcon imgTileMangoSelected = new ImageIcon("res/tile_mango_selected.png");
    private final ImageIcon imgTileApple = new ImageIcon("res/tile_apple.png");
    private final ImageIcon imgTileAppleSelected = new ImageIcon("res/tile_apple_selected.png");

    // Action Buttons GUI
    private final ImageIcon imgWater = new ImageIcon("res/button_water.png");
    private final ImageIcon imgFertilize = new ImageIcon("res/button_fertilize.png");
    private final ImageIcon imgHarvest = new ImageIcon("res/button_harvest.png");
    private final ImageIcon imgPlow = new ImageIcon("res/button_plow.png");
    private final ImageIcon imgShovel = new ImageIcon("res/button_shovel.png");
    private final ImageIcon imgPickaxe = new ImageIcon("res/button_pickaxe.png");

    private final ImageIcon imgBtnTurnip = new ImageIcon("res/button_turnip.png");
    private final ImageIcon imgBtnCarrot = new ImageIcon("res/button_carrot.png");
    private final ImageIcon imgBtnPotato = new ImageIcon("res/button_potato.png");
    private final ImageIcon imgBtnRose = new ImageIcon("res/button_rose.png");
    private final ImageIcon imgBtnTulips = new ImageIcon("res/button_tulips.png");
    private final ImageIcon imgBtnSunflower = new ImageIcon("res/button_sunflower.png");
    private final ImageIcon imgBtnMango = new ImageIcon("res/button_mango.png");
    private final ImageIcon imgBtnApple = new ImageIcon("res/button_apple.png");
    
    // Table
    private final ImageIcon imgTable = new ImageIcon("res/table.png");

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
        this.sleepBtn.setIcon(scaleImage(imgBtnSleep, 195, 25));

        this.upgradeFarmerBtn.setPreferredSize(new Dimension(195,25));
        this.upgradeFarmerBtn.setIcon(scaleImage(imgBtnUpgrade, 195, 25));

        this.bookBtn.setPreferredSize(new Dimension(195,25));
        this.bookBtn.setIcon(scaleImage(imgBtnBook, 195, 25));

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
                farmTilesBtn[i][j].addActionListener(farmTileListener);
                farmTilesBtn[i][j].putClientProperty("location", new int[] {i, j});
                farmTilesBtn[i][j].putClientProperty("isSelected", false);
                
                farmTilesBtn[i][j].setIcon(scaleImage(imgTileUnplowed, 116, 94));
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
            case "water" -> actionBtn.setIcon(scaleImage(imgWater, 95, 95));
            case "fertilizer" -> actionBtn.setIcon(scaleImage(imgFertilize, 95, 95));
            case "harvest" -> actionBtn.setIcon(scaleImage(imgHarvest, 95, 95));
            case "plow" -> actionBtn.setIcon(scaleImage(imgPlow, 95, 95));
            case "shovel" -> actionBtn.setIcon(scaleImage(imgShovel, 95, 95));
            case "pickaxe" -> actionBtn.setIcon(scaleImage(imgPickaxe, 95, 95));
            case "Turnip" -> actionBtn.setIcon(scaleImage(imgBtnTurnip, 116, 94));
            case "Carrot" -> actionBtn.setIcon(scaleImage(imgBtnCarrot, 116, 94));
            case "Potato" -> actionBtn.setIcon(scaleImage(imgBtnPotato, 116, 94));
            case "Rose" -> actionBtn.setIcon(scaleImage(imgBtnRose, 116, 94));
            case "Tulips" -> actionBtn.setIcon(scaleImage(imgBtnTulips, 116, 94));
            case "Sunflower" -> actionBtn.setIcon(scaleImage(imgBtnSunflower, 116, 94));
            case "Mango" -> actionBtn.setIcon(scaleImage(imgBtnMango, 116, 94));
            case "Apple" -> actionBtn.setIcon(scaleImage(imgBtnApple, 116, 94));
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

    /**
     * Initializes the listeners for the buttons at the top of the screen.
     * @param sleepListener     The listener for the sleep button.
     * @param upgradeListener   The listener for the upgrade button.
     * @param bookListener      The listener for the book button.
     */
    public void initializeMiscListener(ActionListener sleepListener, ActionListener upgradeListener, ActionListener bookListener) {
        this.sleepBtn.addActionListener(sleepListener);

        this.upgradeFarmerBtn.addActionListener(upgradeListener);
        this.upgradeFarmerBtn.setEnabled(false);

        this.bookBtn.addActionListener(bookListener);
    }

    /**
     * Updates the upgrade button's listener.
     * @param upgradeListener   The listener for the upgrade button.
     */
    public void updateUpgradeListener(ActionListener upgradeListener) {
        this.upgradeFarmerBtn.addActionListener(upgradeListener);
    }

    /**
     * Updates whether the upgrade button is enabled.
     * @param enabled   Whether the upgrade button is enabled.
     */
    public void setUpgradeFarmerButtonEnabled(boolean enabled) {
        this.upgradeFarmerBtn.setEnabled(enabled);
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

    /**
     * Changes whether a farm tile button is selected.
     * @param row       The row of the farm tile button.
     * @param column    The column of the farm tile button.
     */
    public void setIsSelected(int row, int column) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 10; j++) {
                farmTilesBtn[i][j].putClientProperty("isSelected", false);
            }
        }

        farmTilesBtn[row][column].putClientProperty("isSelected", true);
    }

    /**
     * Updates the farm tile button's image.
     * @param state     The state of the farm tile.
     * @param row       The row of the farm tile button.
     * @param column    The column of the farm tile button.
     */
    public void setTileIcon(String state, int row, int column) {
        if ((boolean) this.farmTilesBtn[row][column].getClientProperty("isSelected")) {
            setTileIconImage(state, row, column,
                    imgTileUnplowedSelected, imgTilePlowedSelected, imgTileRockSelected, imgTileWitheredSelected,
                    imgTileTurnipSelected, imgTileCarrotSelected, imgTilePotatoSelected, imgTileRoseSelected,
                    imgTileTulipsSelected, imgTileSunflowerSelected, imgTileMangoSelected, imgTileAppleSelected
            );
        } else {
            setTileIconImage(state, row, column,
                    imgTileUnplowed, imgTilePlowed, imgTileRock, imgTileWithered, imgTileTurnip, imgTileCarrot,
                    imgTilePotato, imgTileRose, imgTileTulips, imgTileSunflower, imgTileMango, imgTileApple
            );
        }
    }

    /**
     * Helps the setTileIcon method.
     * @param state             The state of the farm tile.
     * @param row               The row of the farm tile button.
     * @param column            The column of the farm tile button.
     * @param imgTileUnplowed   The image of the farm tile button when it is unplowed and selected.
     * @param imgTilePlowed     The image of the farm tile button when it is plowed and selected.
     * @param imgTileRock       The image of the farm tile button when it is a rock and selected.
     * @param imgTileWithered   The image of the farm tile button when it is withered and selected.
     * @param imgTileTurnip     The image of the farm tile button when it is a turnip and selected.
     * @param imgTileCarrot     The image of the farm tile button when it is a carrot and selected.
     * @param imgTilePotato     The image of the farm tile button when it is a potato and selected.
     * @param imgTileRose       The image of the farm tile button when it is a rose and selected.
     * @param imgTileTulips     The image of the farm tile button when it is tulips and selected.
     * @param imgTileSunflower  The image of the farm tile button when it is a sunflower and selected.
     * @param imgTileMango      The image of the farm tile button when it is a mango and selected.
     * @param imgTileApple      The image of the farm tile button when it is an apple and selected.
     */
    private void setTileIconImage(String state, int row, int column,
                                  ImageIcon imgTileUnplowed, ImageIcon imgTilePlowed,
                                  ImageIcon imgTileRock, ImageIcon imgTileWithered,
                                  ImageIcon imgTileTurnip, ImageIcon imgTileCarrot,
                                  ImageIcon imgTilePotato, ImageIcon imgTileRose,
                                  ImageIcon imgTileTulips, ImageIcon imgTileSunflower,
                                  ImageIcon imgTileMango, ImageIcon imgTileApple)
    {
        switch (state) {
            case "unplowed" -> this.farmTilesBtn[row][column].setIcon(scaleImage(imgTileUnplowed, 116, 94));
            case "plowed" -> this.farmTilesBtn[row][column].setIcon(scaleImage(imgTilePlowed, 116, 94));
            case "rock" -> this.farmTilesBtn[row][column].setIcon(scaleImage(imgTileRock, 116, 94));
            case "withered" -> this.farmTilesBtn[row][column].setIcon(scaleImage(imgTileWithered, 116, 94));
            case "Turnip" -> this.farmTilesBtn[row][column].setIcon(scaleImage(imgTileTurnip, 116, 94));
            case "Carrot" -> this.farmTilesBtn[row][column].setIcon(scaleImage(imgTileCarrot, 116, 94));
            case "Potato" -> this.farmTilesBtn[row][column].setIcon(scaleImage(imgTilePotato, 116, 94));
            case "Rose" -> this.farmTilesBtn[row][column].setIcon(scaleImage(imgTileRose, 116, 94));
            case "Tulips" -> this.farmTilesBtn[row][column].setIcon(scaleImage(imgTileTulips, 116, 94));
            case "Sunflower" -> this.farmTilesBtn[row][column].setIcon(scaleImage(imgTileSunflower, 116, 94));
            case "Mango" -> this.farmTilesBtn[row][column].setIcon(scaleImage(imgTileMango, 116, 94));
            case "Apple" -> this.farmTilesBtn[row][column].setIcon(scaleImage(imgTileApple, 116, 94));
        }
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

    /**
     * Creates a new frame for the book.
     */
    public void showBook() {
        JFrame bookFrame;
        JLabel tableLbl = new JLabel();

        tableLbl.setIcon(scaleImage(imgTable, 1200, 675));

        bookFrame = new JFrame("Book");

        bookFrame.setSize(1200, 675);
        bookFrame.setResizable(false);
        bookFrame.setLocationRelativeTo(null);
        bookFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        bookFrame.add(tableLbl);
        bookFrame.setVisible(true);
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
        JButton restartBtn = new JButton("Restart");
        restartBtn.addActionListener(restartListener);
        buttonPanel.add(restartBtn);

        // Exit button
        JButton exitBtn = new JButton("Exit");
        exitBtn.addActionListener(e -> System.exit(0));
        buttonPanel.add(exitBtn);

        // Add button panel
        this.mainPanel.add(buttonPanel, BorderLayout.PAGE_END);

        this.mainFrame.revalidate();
        this.mainFrame.repaint();
    }

    /**
     * Resets the game view.
     */
    public void reset() {
        this.mainPanel.removeAll();
        this.initializeTopPanel();
        this.initializeFarmPanel();
        this.initializeBottomPanel();
        this.updateBottomPanel();
    }
}
