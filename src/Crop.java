public class Crop {    
    private final String type;
    private final int harvestTime;
    private final int waterNeeds;
    private final int fertilizerNeeds;
    private final int productsProduced;
    private final int cost;
    private final int baseSellPrice;
    private final float expYield;

    private int currAge = 0;
    private int timesWatered = 0;
    private int timesFertilized = 0;
    private boolean isAlive = true;

    public Crop(String type, int harvestTime, int waterNeeds, int fertilizerNeeds,
                int productsProduced, int cost, int baseSellPrice, float expYield) {
        this.type = type;
        this.harvestTime = harvestTime;
        this.waterNeeds = waterNeeds;
        this.fertilizerNeeds = fertilizerNeeds;
        this.productsProduced = productsProduced;
        this.cost = cost;
        this.baseSellPrice = baseSellPrice;
        this.expYield = expYield;
    }

    public void addAge() {
        this.currAge++;

        if (this.currAge > harvestTime) {
            this.isAlive = false;
        }
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