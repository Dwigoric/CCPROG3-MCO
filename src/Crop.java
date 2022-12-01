import java.util.Random;

import javax.swing.ImageIcon;

/**
 * The Crop class.
 */
public class Crop {
    private final Seed seed;

    private int age = 0;
    private int waterCount = 0;
    private int fertilizeCount = 0;
    private boolean isAlive = true;

    private final int produce;
    Random rand = new Random(System.currentTimeMillis());

    public static final ImageIcon IMG_TILE_TURNIP = new ImageIcon("res/tile_turnip.png");
    public static final ImageIcon IMG_TILE_CARROT = new ImageIcon("res/tile_carrot.png");
    public static final ImageIcon IMG_TILE_POTATO = new ImageIcon("res/tile_potato.png");
    public static final ImageIcon IMG_TILE_ROSE = new ImageIcon("res/tile_rose.png");
    public static final ImageIcon IMG_TILE_TULIPS = new ImageIcon("res/tile_tulips.png");
    public static final ImageIcon IMG_TILE_SUNFLOWER = new ImageIcon("res/tile_sunflower.png");
    public static final ImageIcon IMG_TILE_MANGO = new ImageIcon("res/tile_mango.png");
    public static final ImageIcon IMG_TILE_APPLE = new ImageIcon("res/tile_apple.png");

    public static final ImageIcon IMG_TILE_TURNIP_SELECTED = new ImageIcon("res/tile_turnip_selected.png");
    public static final ImageIcon IMG_TILE_CARROT_SELECTED = new ImageIcon("res/tile_carrot_selected.png");
    public static final ImageIcon IMG_TILE_POTATO_SELECTED = new ImageIcon("res/tile_potato_selected.png");
    public static final ImageIcon IMG_TILE_ROSE_SELECTED = new ImageIcon("res/tile_rose_selected.png");
    public static final ImageIcon IMG_TILE_TULIPS_SELECTED = new ImageIcon("res/tile_tulips_selected.png");
    public static final ImageIcon IMG_TILE_SUNFLOWER_SELECTED = new ImageIcon("res/tile_sunflower_selected.png");
    public static final ImageIcon IMG_TILE_MANGO_SELECTED = new ImageIcon("res/tile_mango_selected.png");
    public static final ImageIcon IMG_TILE_APPLE_SELECTED = new ImageIcon("res/tile_apple_selected.png");   

    /**
     * Constructs a new Crop.
     * @param seed  The seed of the crop.
     */
    public Crop(Seed seed) {
        this.seed = seed;

        /* Randomize number of produce */
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
     * Gets the water level of the crop.
     * @return  The water level of the crop.
     */
    public int getWaterCount () {
        return this.waterCount;
    }

    /**
     * Gets the fertilizer level of the crop.
     * @return  The fertilizer level of the crop.
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
