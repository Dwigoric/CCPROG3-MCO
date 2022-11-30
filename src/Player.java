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
        if (seed.cost() - this.farmerType.seedCostReduction() > this.objectCoins) {
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
        this.objectCoins -= seed.cost() - this.farmerType.seedCostReduction();
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

        this.objectCoins -= 10;
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
        WaterBonusLimit = crop.getSeed().waterLimit() + farmerType.waterBonusLimitIncrease();
        FertilizerBonusLimit = crop.getSeed().fertilizerLimit() + farmerType.fertilizerBonusLimitIncrease();

        HarvestTotal = crop.getProduce() * (crop.getSeed().baseSellingPrice() + farmerType.bonusEarnings());
        
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
        this.objectCoins += finalPrice;
        this.addExperience(crop.getSeed().expYield());
    
        tile.harvest();
        return finalPrice;
    }

    /**
     * Uses the pickaxe on a tile.
     * @param row       The row of the tile.
     * @param column    The column of the tile.
     */
    public void pickaxe(int row, int column) {
        if (this.farm.getTile(row, column).hasRock()) {
            this.farm.getTile(row, column).pickaxe();
            this.objectCoins -= 50;
            this.addExperience(15);
        }
    }

    /**
     * Uses the shovel on a tile.
     * @param row       The row of the tile.
     * @param column    The column of the tile.
     */
    public void shovel(int row, int column) {
        this.objectCoins -= 7;
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
     * Attempts to upgrade the player's farmer type.
     * @param farmerTypes   The list of farmer types.
     */
    public void upgradeFarmer(ArrayList<FarmerType> farmerTypes) {
        if (this.canUpgradeFarmer(farmerTypes)) {
            this.farmerTypeLevel++;
            this.farmerType = farmerTypes.get(this.farmerTypeLevel);

            this.objectCoins -= farmerTypes.get(this.farmerTypeLevel).registrationFee();
        }

    }

    /**
     * Checks if the player can upgrade their farmer type.
     * @param farmerTypes   The list of farmer types.
     * @return  true if the player can upgrade their farmer type, false otherwise.
     */
    public boolean canUpgradeFarmer(ArrayList<FarmerType> farmerTypes) {
        if (this.farmerTypeLevel == 3) {
            return false;
        }

        FarmerType newType = farmerTypes.get(this.farmerTypeLevel + 1);

        return this.farmerTypeLevel + 1 < farmerTypes.size() &&
                this.level >= newType.levelRequirement() && this.objectCoins >= newType.registrationFee();
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

    /**
     * Gets the current farmer type's level.
     * @return  The current farmer type's level.
     */
    public int getFarmerTypeLvl() {
        return this.farmerTypeLevel;
    }

    /**
     * Gets the current farmer type.
     * @return  The current farmer type.
     */
    public FarmerType getFarmerType() {
        return this.farmerType;
    }
}
