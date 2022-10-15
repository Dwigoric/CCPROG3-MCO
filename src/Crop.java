public class Crop {
    private String type;
    private int harvestTime;
    private int waterNeeds;
    private int fertilizerNeeds;
    private int productsProduced;
    private int seedCost;
    private int baseSellPrice;
    private float expYield;

    private int currAge = 0;
    private int timesWatered = 0;
    private int timesFertilized = 0;
    private boolean isAlive = true;

    public Crop(String type, int harvestTime, int waterNeeds, int fertilizerNeeds,
                int productsProduced, int seedCost, int baseSellPrice, float expYield) {
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

        if(this.currAge > harvestTime) {
            isAlive = false;
        }
    }

    public void water() {
        this.timesWatered++;
    }

    public void fertilize() {
        this.timesFertilized++;
    }
}