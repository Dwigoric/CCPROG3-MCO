import java.util.ArrayList;

/**
 * The Player class.
 */
public class Player {
    private final Farm farm;

    private int level = 0;
    private float experience = 1000000;
    private float objectCoins = 1000000;

    private int farmerTypeLevel = 0;
    private FarmerType farmerType;

    /**
     * The Player class constructor.
     * @param farm The farm owned by the player.
     */
    public Player(Farm farm, FarmerType farmerType) {
        this.farm = farm;
        this.farmerType = farmerType;
    }

    /**
     * Plows a tile.
     * @param row       The row of the tile.
     * @param column    The column of the tile.
     */
    public void plow(int row, int column) {
        if (this.farm.getTile(row, column).isPlowed()) {
            return;
        }

        this.addExperience(0.5f);
        this.farm.getTile(row, column).plow();
    }

    /**
     * Plants a seed on a tile.
     * @param row       The row of the tile.
     * @param column    The column of the tile.
     * @param seed      The seed to plant.
     */
    public void plant(int row, int column, Seed seed) {
        if (seed.getCost() - this.farmerType.getSeedCostReduction() > this.objectCoins) {
            return;
        }

        if (seed.isTree() && !this.farm.canPlantTree(row, column)) {
            return;
        }

        Tile tile = this.farm.getTile(row, column);
        if (!tile.isPlowed() || tile.hasRock()) {
            return;
        }

        if (tile.getCrop() != null) {
            return;
        }

        tile.plant(seed);
        this.deductCoins(seed.getCost() - this.farmerType.getSeedCostReduction());
    }

    /**
     * Waters a tile.
     * @param row       The row of the tile.
     * @param column    The column of the tile.
     */
    public void water(int row, int column) {
        Tile tile = this.farm.getTile(row, column);
        if (!tile.isPlowed()) {
            return;
        }

        Crop crop = tile.getCrop();
        if (crop == null) {
            return;
        }

        crop.water();
        this.addExperience(0.5f);
    }

    /**
     * Fertilizes a tile.
     * @param row       The row of the tile.
     * @param column    The column of the tile.
     */
    public void fertilize(int row, int column) {
        if (this.objectCoins < 10) {
            return;
        }

        Tile tile = this.farm.getTile(row, column);
        if (!tile.isPlowed()) {
            return;
        }

        Crop crop = tile.getCrop();
        if (crop == null) {
            return;
        }

        this.deductCoins(10);
        crop.fertilize();
        this.addExperience(4.0f);
    }

    /**
     * Harvests a crop from a tile, if applicable.
     * @param row       The row of the tile.
     * @param column    The column of the tile.
     * @return  final price if crop was successfully harvested, 0 otherwise.
     */
    public float harvest(int row, int column) {
        int WaterBonusLimit;
        int FertilizerBonusLimit;
        
        float HarvestTotal;
        float WaterBonus;
        float FertilizerBonus;

        Tile tile = this.farm.getTile(row, column);
        if (tile.getCrop() == null) {
            return 0;
        }

        Crop crop = tile.getCrop();
        if (!crop.isHarvestReady()) {
            return 0;
        }

        /* Selling Price Computation */
        WaterBonusLimit = crop.getSeed().getWaterLimit() + farmerType.getWaterBonusLimitIncrease();
        FertilizerBonusLimit = crop.getSeed().getFertilizerLimit() + farmerType.getFertilizerBonusLimitIncrease();

        HarvestTotal = crop.getProduce() * (crop.getSeed().getBaseSellingPrice() + farmerType.getBonusEarnings());
        
        if(crop.getWaterCount() > WaterBonusLimit) {
            WaterBonus = HarvestTotal * 0.2f * (WaterBonusLimit - 1);
        } else {
            WaterBonus = HarvestTotal * 0.2f * (crop.getWaterCount() - 1);
        }
        
        if(crop.getFertilizeCount() > FertilizerBonusLimit) {
            FertilizerBonus = HarvestTotal * 0.5f * (FertilizerBonusLimit);
        } else {
            FertilizerBonus = HarvestTotal * 0.5f * (crop.getFertilizeCount());
        }

        /* Player Information Update */
        float finalPrice = (HarvestTotal + WaterBonus + FertilizerBonus) * (crop.getSeed().isFlower() ? 1.1f : 1.0f);
        this.addCoins(finalPrice);
        this.addExperience(crop.getSeed().getExpYield());
    
        tile.harvest();
        return finalPrice;
    }

    public void pickaxe(int row, int column) {
        if (this.farm.getTile(row, column).hasRock()) {
            this.farm.getTile(row, column).pickaxe();
            this.deductCoins(50);
            this.addExperience(15);
        }
    }

    public void shovel(int row, int column) {
        if (this.objectCoins < 7) {
            return;
        }

        this.deductCoins(7);
        this.farm.reset(row, column);
    }

    /**
     * Adds experience to the player.
     * @param amount    The amount of experience to add.
     */
    public void addExperience(float amount) {
        this.experience += amount;
        this.level = (int) this.experience / 100;
    }

    /**
     * Adds coins to the player.
     * @param amount    The amount of coins to add.
     */
    public void addCoins(float amount) {
        this.objectCoins += amount;
    }

    /**
     * Deducts coins from the player.
     * @param amount    The amount of coins to deduct.
     */
    public void deductCoins(float amount) {
        if (amount > this.objectCoins) {
            return;
        }

        this.objectCoins -= amount;
    }

    /**
     * Tries to upgrade the current farmer type of the player.
     */
    public void upgradeFarmer(ArrayList<FarmerType> farmerTypes) {
        if (this.canUpgradeFarmer(farmerTypes)) {
            this.farmerTypeLevel++;
            this.farmerType = farmerTypes.get(this.farmerTypeLevel);

            this.objectCoins -= farmerTypes.get(this.farmerTypeLevel).getRegistrationFee();
        }

    }

    public boolean canUpgradeFarmer(ArrayList<FarmerType> farmerTypes) {
        if (this.farmerTypeLevel == 3) {
            return false;
        }

        FarmerType newType = farmerTypes.get(this.farmerTypeLevel + 1);

        return this.farmerTypeLevel + 1 < farmerTypes.size() &&
                this.level >= newType.getLevelRequirement() && this.objectCoins >= newType.getRegistrationFee();
    }

    /**
     * Gets the current level of the player.
     * @return  The current level of the player.
     */
    public int getLevel() {
        return this.level;
    }

    /**
     * Gets the current experience of the player.
     * @return  The current experience of the player.
     */
    public float getExperience() {
        return this.experience;
    }

    /**
     * Gets the current coins of the player.
     * @return  The current coins of the player.
     */
    public float getObjectCoins() {
        return this.objectCoins;
    }

    public int getFarmerTypeLvl() {
        return this.farmerTypeLevel;
    }

    public FarmerType getFarmerType() {
        return this.farmerType;
    }
}
