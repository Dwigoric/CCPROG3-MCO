import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.ImageIcon;

/**
 * The Game class.
 * This class stores important game information.
 */
public class Game {
    /**
     * The current day of the game.
     */
    private int day = 1;
    /**
     * The Player instance the game is using.
     */
    private Player player;
    /**
     * The Farm instance the game is using.
     */
    private Farm farm;

    /**
     * The list of coordinates that initially have rocks (every game restart).
     */
    private final ArrayList<Integer[]> rocks;

    /**
     * The list of seeds the game is using.
     */
    private final ArrayList<Seed> seeds = new ArrayList<>(Arrays.asList(
            new Seed("Turnip", 2, 1, 2, 0, 1,
                    1, 2, 5, 6, 5, false, false),
            new Seed("Carrot", 3, 1, 2, 0, 1,
                    1, 2, 10, 9, 7.5f, false, false),
            new Seed("Potato", 5, 3, 4, 1, 2,
                    1, 10, 20, 3, 12.5f, false, false),
            new Seed("Rose", 1, 1, 2, 0, 1,
                    1, 1, 5, 5, 2.5f, true, false),
            new Seed("Tulips", 2, 2, 3, 0, 1,
                    1, 1, 10, 9, 5, true, false),
            new Seed("Sunflower", 3, 2, 3, 1, 2,
                    1, 1, 20, 19, 7.5f, true, false),
            new Seed("Mango", 10, 7, 7, 4, 4,
                    5, 10, 100, 8, 25, false,true),
            new Seed("Apple",10, 7, 7, 5, 5,
                    10, 15, 200, 5, 25, false, true)
    ));

    /**
     * The list of available farmer types.
     */
    private final ArrayList<FarmerType> farmerTypes = new ArrayList<>(Arrays.asList(
            new FarmerType("Farmer", 0, 0, 0,
                    0, 0, 0),
            new FarmerType("Registered Farmer", 5, 1, 1,
                    0, 0, 200),
            new FarmerType("Distinguished Farmer", 10, 2, 2,
                    1, 0, 300),
            new FarmerType("Legendary Farmer", 15, 4, 3,
                    2, 1, 400)
    ));

    /**
     * The image icon for the sleep button.
     */
    public static final ImageIcon IMG_BUTTON_SLEEP = new ImageIcon("res/button_sleep.png");
    /**
     * The image icon for the upgrade button.
     */
    public static final ImageIcon IMG_BUTTON_UPGRADE = new ImageIcon("res/button_upgrade.png");
    /**
     * The image icon for the book button.
     */
    public static final ImageIcon IMG_BUTTON_BOOK = new ImageIcon("res/button_book.png");
    /**
     * The image icon for the table inside the book frame.
     */
    public static final ImageIcon IMG_TABLE = new ImageIcon("res/table.png");
    /**
     * The image icon for the restart button.
     */
    public static final ImageIcon IMG_BUTTON_RESTART = new ImageIcon("res/button_restart.png");
    /**
     * The image icon for the exit button.
     */
    public static final ImageIcon IMG_BUTTON_EXIT = new ImageIcon("res/button_exit.png");

    /**
     * Initializes the game.
     * @param rocks     The list of coordinates of tiles that have rocks.
     */
    public Game(ArrayList<Integer[]> rocks) {
        this.rocks = rocks;
        this.farm = new Farm(5, 10, this.rocks);
        this.player = new Player(this.farm, farmerTypes.get(0));
    }

    /**
     * Advances the day.
     * This method is called at the end of each day.
     * It advances the day, and updates the crops.
     */
    public void advanceDay() {
        this.day++;

        for (int i = 0; i < this.farm.getRows(); i++) {
            for (int j = 0; j < this.farm.getColumns(); j++) {
                Crop crop = this.farm.getTile(i, j).getCrop();

                if (crop != null) {
                    crop.addAge();
                }
            }
        }
    }

    /**
     * Resets the game by replacing the farm and player of new instances respectively and resetting the day.
     */
    public void resetGame() {
        this.day = 1;
        this.farm = new Farm(5, 10, this.rocks);
        this.player = new Player(this.farm, farmerTypes.get(0));
    }

    /**
     * Gets the day.
     * @return  The day.
     */
    public int getDay() {
        return day;
    }

    /**
     * Gets the player.
     * @return  The player.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Gets the farm.
     * @return  The farm.
     */
    public Farm getFarm() {
        return farm;
    }

    /**
     * Gets a seed from the seed list.
     * @param index The index of the seed.
     * @return    The seed at the specified index.
     */
    public Seed getSeed(int index) {
        return seeds.get(index);
    }

    /**
     * Gets the farmer type list.
     * @return  The farmer type list.
     */
    public ArrayList<FarmerType> getFarmerTypeList() {
        return this.farmerTypes;
    }
}
