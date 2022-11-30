/**
 * The Seed class.
 */
public record Seed(String name, int harvestTime, int waterNeeds, int waterLimit, int fertilizerNeeds,
                   int fertilizerLimit, int minProduce, int maxProduce, int cost, int baseSellingPrice, float expYield,
                   boolean isFlower, boolean isTree) {
    /**
     * The Seed class constructor.
     *
     * @param name             The name of the seed.
     * @param harvestTime      The harvest time of the seed.
     * @param waterNeeds       The minimum water level of the seed.
     * @param fertilizerNeeds  The minimum fertilizer level of the seed.
     * @param minProduce       The minimum produce of the seed.
     * @param maxProduce       The maximum produce of the seed.
     * @param cost             The cost of the seed.
     * @param baseSellingPrice The base value of the seed.
     * @param expYield         The experience gained from harvest the crop from this seed.
     * @param isFlower         Whether the seed is a flower.
     * @param isTree           Whether the seed is a tree.
     */
    public Seed {
    }

    /**
     * Gets the name of the seed.
     *
     * @return The name of the seed.
     */
    @Override
    public String name() {
        return name;
    }

    /**
     * Gets the harvest time of the seed.
     *
     * @return The harvest time of the seed.
     */
    @Override
    public int harvestTime() {
        return harvestTime;
    }

    /**
     * Gets the minimum water level of the seed.
     *
     * @return The minimum water level of the seed.
     */
    @Override
    public int waterNeeds() {
        return waterNeeds;
    }

    /**
     * Gets the water bonus limit of the seed.
     *
     * @return The water bonus limit of the seed.
     */
    @Override
    public int waterLimit() {
        return waterLimit;
    }

    /**
     * Gets the minimum fertilizer level of the seed.
     *
     * @return The minimum fertilizer level of the seed.
     */
    @Override
    public int fertilizerNeeds() {
        return fertilizerNeeds;
    }

    /**
     * Gets the fertilizer bonus limit of the seed.
     *
     * @return The fertilizer bonus limit of the seed.
     */
    @Override
    public int fertilizerLimit() {
        return fertilizerLimit;
    }

    /**
     * Gets the minimum produce of the seed.
     *
     * @return The minimum produce of the seed.
     */
    @Override
    public int minProduce() {
        return minProduce;
    }

    /**
     * Gets the maximum produce of the seed.
     *
     * @return The maximum produce of the seed.
     */
    @Override
    public int maxProduce() {
        return maxProduce;
    }

    /**
     * Gets the cost of the seed.
     *
     * @return The cost of the seed.
     */
    @Override
    public int cost() {
        return cost;
    }

    /**
     * Gets the base value of the seed.
     *
     * @return The base value of the seed.
     */
    @Override
    public int baseSellingPrice() {
        return baseSellingPrice;
    }

    /**
     * Gets the experience gained from harvest the crop from this seed.
     *
     * @return The experience gained from harvest the crop from this seed.
     */
    @Override
    public float expYield() {
        return expYield;
    }

    /**
     * Gets whether the seed is a tree.
     *
     * @return Whether the seed is a tree.
     */
    @Override
    public boolean isFlower() {
        return isFlower;
    }

    /**
     * Gets whether the seed is a tree.
     *
     * @return Whether the seed is a tree.
     */
    @Override
    public boolean isTree() {
        return isTree;
    }
}
