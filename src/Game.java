import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * The Game class.
 * This class contains the main game loop.
 */
public class Game {
    private Player player;
    private Farm farm;
    
    private final ArrayList<Seed> seedList = new ArrayList<Seed>();
    private final ArrayList<FarmerType> farmerTypes = new ArrayList<>(Arrays.asList(
            new FarmerType("Farmer", 0, 0, 0, 0, 0, 0),
            new FarmerType("Registered Farmer", 5, 1, 1, 0, 0, 200),
            new FarmerType("Distinguished Farmer", 10, 2, 2, 1, 0, 300),
            new FarmerType("Legendary Farmer", 15, 4, 3, 2, 1, 400)
    ));

    /**
     * Advances the day.
     * This method is called at the end of each day.
     * It advances the day, and updates the crops.
     */
    public void advanceDay() {
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
     * Initializes the game.
     */
    public Game() {
        this.farm = new Farm(5, 10); // pede ba landscape or kailangan portrait
        
        this.player = new Player(this.farm, farmerTypes.get(0));

        /* Create seeds */
        Seed turnip = new Seed("Turnip", "Root Crop", 2, 1, 2, 0, 1,
            1, 2, 5, 6, 5);

        Seed carrot = new Seed("Carrot", "Root Crop", 3, 1, 2, 0, 1,
            1, 2, 10, 9, 7.5f);

        Seed potato = new Seed("Potato", "Root crop", 5, 3, 4, 1, 2,
            1, 10, 20, 3, 12.5f);

        Seed rose = new Seed("Rose", "Flower", 1, 1, 2, 0, 1,
            1, 1, 5, 5, 2.5f);

        Seed tulips = new Seed("Tulips", "Flower", 2, 2, 3, 0, 1,
            1, 1, 10, 9, 5);

        Seed sunflower = new Seed("Sunflower", "Flower", 3, 2, 3, 1, 2,
            1, 1, 20, 19, 7.5f);

        Seed mango = new Seed("Mango", "Fruit tree", 10, 7, 7, 4, 4,
            5, 10, 100, 8, 25);

        Seed apple = new Seed("Apple", "Fruit tree",10, 7, 7, 5, 5,
            10, 15, 200, 5, 25);
        
        /* Add seeds to list */
        this.seedList.add(turnip);
        this.seedList.add(carrot);
        this.seedList.add(potato);
        this.seedList.add(rose);
        this.seedList.add(tulips);
        this.seedList.add(sunflower);
        this.seedList.add(mango);
        this.seedList.add(apple);
    }
}
