import java.util.Random;

import javax.swing.ImageIcon;

/**
 * The Crop class.
 */
public class Crop {
    /**
     * The Seed instance the crop is using.
     */
    private final Seed seed;

    /**
     * The age of the crop in days.
     */
    private int age = 0;
    /**
     * How many times the crop has been watered.
     */
    private int waterCount = 0;
    /**
     * How many times the crop has been fertilized.
     */
    private int fertilizeCount = 0;
    /**
     * Whether the crop is alive.
     */
    private boolean isAlive = true;

    /**
     * The amount of produce the crop will yield when harvested.
     */
    private final int produce;

    /**
     * The image icon if the crop is a turnip.
     */
    public static final ImageIcon IMG_TILE_TURNIP = new ImageIcon("res/tile_turnip.png");
    /**
     * The image icon if the crop is a carrot.
     */
    public static final ImageIcon IMG_TILE_CARROT = new ImageIcon("res/tile_carrot.png");
    /**
     * The image icon if the crop is a potato.
     */
    public static final ImageIcon IMG_TILE_POTATO = new ImageIcon("res/tile_potato.png");
    /**
     * The image icon if the crop is a rose.
     */
    public static final ImageIcon IMG_TILE_ROSE = new ImageIcon("res/tile_rose.png");
    /**
     * The image icon if the crop is a tulips.
     */
    public static final ImageIcon IMG_TILE_TULIPS = new ImageIcon("res/tile_tulips.png");
    /**
     * The image icon if the crop is a sunflower.
     */
    public static final ImageIcon IMG_TILE_SUNFLOWER = new ImageIcon("res/tile_sunflower.png");
    /**
     * The image icon if the crop is a mango tree.
     */
    public static final ImageIcon IMG_TILE_MANGO = new ImageIcon("res/tile_mango.png");
    /**
     * The image icon if the crop is an apple tree.
     */
    public static final ImageIcon IMG_TILE_APPLE = new ImageIcon("res/tile_apple.png");

    /**
     * The image icon if the crop is a turnip and is selected.
     */
    public static final ImageIcon IMG_TILE_TURNIP_SELECTED = new ImageIcon("res/tile_turnip_selected.png");
    /**
     * The image icon if the crop is a carrot and is selected.
     */
    public static final ImageIcon IMG_TILE_CARROT_SELECTED = new ImageIcon("res/tile_carrot_selected.png");
    /**
     * The image icon if the crop is a potato and is selected.
     */
    public static final ImageIcon IMG_TILE_POTATO_SELECTED = new ImageIcon("res/tile_potato_selected.png");
    /**
     * The image icon if the crop is a rose and is selected.
     */
    public static final ImageIcon IMG_TILE_ROSE_SELECTED = new ImageIcon("res/tile_rose_selected.png");
    /**
     * The image icon if the crop is a tulip and is selected.
     */
    public static final ImageIcon IMG_TILE_TULIPS_SELECTED = new ImageIcon("res/tile_tulips_selected.png");
    /**
     * The image icon if the crop is a sunflower and is selected.
     */
    public static final ImageIcon IMG_TILE_SUNFLOWER_SELECTED = new ImageIcon("res/tile_sunflower_selected.png");
    /**
     * The image icon if the crop is a mango tree and is selected.
     */
    public static final ImageIcon IMG_TILE_MANGO_SELECTED = new ImageIcon("res/tile_mango_selected.png");
    /**
     * The image icon if the crop is an apple tree and is selected.
     */
    public static final ImageIcon IMG_TILE_APPLE_SELECTED = new ImageIcon("res/tile_apple_selected.png");   

    /**
     * Constructs a new Crop.
     * @param seed  The seed of the crop.
     */
    public Crop(Seed seed) {
        this.seed = seed;

        /* Randomize number of produce */
        Random rand = new Random(System.currentTimeMillis());
        this.produce = rand.nextInt(seed.maxProduce() + 1 - seed.minProduce()) + seed.minProduce();
    }

    /**
     * Adds age to the crop.
     */
    public void addAge() {
        this.age++;
        
        /* Crop does not meet water/fertilize requirement on harvest day */
        if (this.age == this.seed.harvestTime() && (this.waterCount < this.seed.waterNeeds() || this.fertilizeCount < this.seed.fertilizerNeeds())) {
            this.isAlive = false;

        /* Crop age exceeds harvest time */
        } else if (this.age > this.seed.harvestTime()) {
            this.isAlive = false;
        }
    }

    /**
     * Adds water to the crop.
     */
    public void water() {
        this.waterCount++;
    }

    /**
     * Adds fertilizer to the crop.
     */
    public void fertilize() {
        this.fertilizeCount++;
    }

    /**
     * Gets the seed of the crop.
     * @return  The seed of the crop.
     */
    public Seed getSeed() {
        return this.seed;
    }

    /**
     * Gets the age of the crop.
     * @return  The age of the crop.
     */
    public int getAge() {
        return this.age;
    }

    /**
     * Gets the water count of the crop.
     * @return  The water count of the crop.
     */
    public int getWaterCount () {
        return this.waterCount;
    }

    /**
     * Gets the fertilizer count of the crop.
     * @return  The fertilizer count of the crop.
     */
    public int getFertilizeCount () {
        return this.fertilizeCount;
    }

    /**
     * Gets whether the crop is alive or not.
     * @return  True if the crop is alive, false otherwise.
     */
    public boolean isAlive() {
        return this.isAlive;
    }

    /**
     * Gets the amount of produce the crop will yield when harvested.
     * @return  The amount of produce the crop will yield when harvested.
     */
    public int getProduce() {
        return this.produce;
    }

    /**
     * Gets whether the crop is ready to be harvested or not.
     * @return  True if the crop is ready to be harvested, false otherwise.
     */
    public boolean isHarvestReady() {
        return this.isAlive && this.age == this.seed.harvestTime() &&
               this.waterCount >= this.seed.waterNeeds() && this.fertilizeCount >= this.seed.fertilizerNeeds();
    }
}
