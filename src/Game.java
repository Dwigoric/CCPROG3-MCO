import java.util.ArrayList;
import java.util.Arrays;

/**
 * The Game class.
 * This class contains the main game loop.
 */
public class Game {
    private final Player player;
    private final Farm farm;
    private int day = 1;
    
    private final ArrayList<Seed> seedList = new ArrayList<>(Arrays.asList(
            new Seed("Turnip", "Root Crop", 2, 1, 2, 0,
                    1, 1, 2, 5, 6, 5),
            new Seed("Carrot", "Root Crop", 3, 1, 2, 0,
                    1, 1, 2, 10, 9, 7.5f),
            new Seed("Potato", "Root crop", 5, 3, 4, 1,
                    2, 1, 10, 20, 3, 12.5f),
            new Seed("Rose", "Flower", 1, 1, 2, 0,
                    1, 1, 1, 5, 5, 2.5f),
            new Seed("Tulips", "Flower", 2, 2, 3, 0,
                    1, 1, 1, 10, 9, 5),
            new Seed("Sunflower", "Flower", 3, 2, 3, 1,
                    2, 1, 1, 20, 19, 7.5f),
            new Seed("Mango", "Fruit tree", 10, 7, 7, 4,
                    4, 5, 10, 100, 8, 25),
            new Seed("Apple", "Fruit tree",10, 7, 7, 5,
                    5, 10, 15, 200, 5, 25)
    ));
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
     * Initializes the game.
     */
    public Game() {
        this.farm = new Farm(5, 10); // pede ba landscape or kailangan portrait

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
     * Gets the day.
     * @return  The day.
     */
    public int getDay() {
        return day;
    }
}
