public class Crop {
    private String type;
    private final Tile tile;
    private final int harvestTime;
    private final int waterNeeds;
    private final int fertilizerNeeds;
    private final int productsProduced;
    private final int seedCost;
    private final int baseSellPrice;
    private final float expYield;

    private int currAge = 0;
    private int timesWatered = 0;
    private int timesFertilized = 0;
    private boolean isAlive = true;

    public Crop(Tile tile, String type, int harvestTime, int waterNeeds, int fertilizerNeeds,
                int productsProduced, int seedCost, int baseSellPrice, float expYield) {
        this.tile = tile;
        this.type = type;
        this.harvestTime = harvestTime;
        this.waterNeeds = waterNeeds;
        this.fertilizerNeeds = fertilizerNeeds;
        this.productsProduced = productsProduced;
        this.seedCost = seedCost;
        this.baseSellPrice = baseSellPrice;
        this.expYield = expYield;
    }

    public void addAge() {
        this.currAge++;

        if (this.currAge > harvestTime) isAlive = false;
    }

    public void water() {
        this.timesWatered++;
    }

    public void fertilize() {
        this.timesFertilized++;
    }

    public boolean isAlive() {
        return this.isAlive;
    }
}