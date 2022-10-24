/**
 * The Seed class.
 */
public class Seed {
    private final String name;
    private final String type;
    private final int harvestTime;
    private final int waterNeeds;
    private final int waterLimit;
    private final int fertilizerNeeds;
    private final int fertilizerLimit;
    private final int minProduce;
    private final int maxProduce;
    private final int cost;
    private final int baseSellingPrice;
    private final float expYield;

    /**
     * The Seed class constructor.
     * @param name              The name of the seed.
     * @param type              The type of the seed.
     * @param harvestTime       The harvest time of the seed.
     * @param waterNeeds        The minimum water level of the seed.
     * @param fertilizerNeeds   The minimum fertilizer level of the seed.
     * @param minProduce        The minimum produce of the seed.
     * @param maxProduce        The maximum produce of the seed.
     * @param cost              The cost of the seed.
     * @param baseSellingPrice  The base value of the seed.
     * @param expYield          The experience gained from harvest the crop from this seed.
     */
    public Seed(String name, String type, int harvestTime, int waterNeeds, int waterLimit, int fertilizerNeeds,
                int fertilizerLimit, int minProduce, int maxProduce, int cost, int baseSellingPrice, float expYield) {
        this.name = name;
        this.type = type;
        this.harvestTime = harvestTime;
        this.waterNeeds = waterNeeds;
        this.waterLimit = waterLimit;
        this.fertilizerNeeds = fertilizerNeeds;
        this.fertilizerLimit = fertilizerLimit;
        this.minProduce = minProduce;
        this.maxProduce = maxProduce;
        this.cost = cost;
        this.baseSellingPrice = baseSellingPrice;
        this.expYield = expYield;
    }

    /**
     * Gets the name of the seed.
     * @return  The name of the seed.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the type of the seed.
     * @return  The type of the seed.
     */
    public String getType() {
        return type;
    }

    /**
     * Gets the harvest time of the seed.
     * @return  The harvest time of the seed.
     */
    public int getHarvestTime() {
        return harvestTime;
    }

    /**
     * Gets the minimum water level of the seed.
     * @return  The minimum water level of the seed.
     */
    public int getWaterNeeds() {
        return waterNeeds;
    }

    /**
     * Gets the water bonus limit of the seed.
     * @return  The water bonus limit of the seed.
     */
    public int getWaterLimit() {
        return waterLimit;
    }

    /**
     * Gets the minimum fertilizer level of the seed.
     * @return  The minimum fertilizer level of the seed.
     */
    public int getFertilizerNeeds() {
        return fertilizerNeeds;
    }

    /**
     * Gets the fertilizer bonus limit of the seed.
     * @return  The fertilizer bonus limit of the seed.
     */
    public int getFertilizerLimit() {
        return fertilizerLimit;
    }

    /**
     * Gets the minimum produce of the seed.
     * @return  The minimum produce of the seed.
     */
    public int getMinProduce() {
        return minProduce;
    }

    /**
     * Gets the maximum produce of the seed.
     * @return  The maximum produce of the seed.
     */
    public int getMaxProduce() {
        return maxProduce;
    }

    /**
     * Gets the cost of the seed.
     * @return  The cost of the seed.
     */
    public int getCost() {
        return cost;
    }

    /**
     * Gets the base value of the seed.
     * @return  The base value of the seed.
     */
    public int getBaseSellingPrice() {
        return baseSellingPrice;
    }

    /**
     * Gets the experience gained from harvest the crop from this seed.
     * @return  The experience gained from harvest the crop from this seed.
     */
    public float getExpYield() {
        return expYield;
    }
}
