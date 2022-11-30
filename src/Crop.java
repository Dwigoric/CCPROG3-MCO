import java.util.Random;

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
