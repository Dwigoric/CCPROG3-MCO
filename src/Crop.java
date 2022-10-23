import java.util.Random;

public class Crop {
    private final Seed seed;

    private int age = 0;
    private int waterCount = 0;
    private int fertilizeCount = 0;
    private boolean alive = true;

    private int produce;
    Random rand = new Random(System.currentTimeMillis());

    public Crop(Seed seed) {
        this.seed = seed;

        /* Randomize number of produce */
        this.produce = rand.nextInt(seed.getMaxProduce() + 1 - seed.getMinProduce()) + seed.getMinProduce();
    }

    public void addAge() {
        if (this.age == this.seed.getHarvestTime()) {
            this.alive = false;
        }

        this.age++;
    }

    public void water() {
        this.waterCount++;
    }

    public void fertilize() {
        this.fertilizeCount++;
    }

    public Seed getSeed() {
        return this.seed;
    }

    public int getWaterCount () {
        return this.waterCount;
    }

    public int getFertilizeCount () {
        return this.fertilizeCount;
    }

    public boolean isAlive() {
        return this.alive;
    }

    public int getProduce() {
        return this.produce;
    }

    public boolean isHarvestReady() {
        return this.alive &&
               this.waterCount >= this.seed.getWaterNeeds() &&
               this.fertilizeCount >= this.seed.getFertilizerNeeds() &&
               this.age == this.seed.getHarvestTime();
    }
}
