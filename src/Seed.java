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

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getHarvestTime() {
        return harvestTime;
    }

    public int getWaterNeeds() {
        return waterNeeds;
    }

    public int getWaterLimit() {
        return waterLimit;
    }

    public int getFertilizerNeeds() {
        return fertilizerNeeds;
    }

    public int getFertilizerLimit() {
        return fertilizerLimit;
    }

    public int getMinProduce() {
        return minProduce;
    }

    public int getMaxProduce() {
        return maxProduce;
    }

    public int getCost() {
        return cost;
    }

    public int getBaseSellingPrice() {
        return baseSellingPrice;
    }

    public float getExpYield() {
        return expYield;
    }
}
