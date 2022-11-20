import java.util.ArrayList;

/**
 * The Player class.
 */
public class Player {
    private final Farm farm;

    private int level = 0;
    private float experience = 0;
    private float objectCoins = 100;

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
     * @return  True if the tile was successfully plowed, false otherwise.
     */
    public boolean plow(int row, int column) {
        System.out.println("test");
        if (this.farm.getTile(row, column).isPlowed()) {
            return false;
        }

        this.addExperience(0.5f);
        return this.farm.getTile(row, column).plow();
    }

    /**
     * Plants a seed on a tile.
     * @param row       The row of the tile.
     * @param column    The column of the tile.
     * @param seed      The seed to plant.
     * @return  True if the seed was successfully planted, false otherwise.
     */
    public boolean plant(int row, int column, Seed seed) {
        if (seed.getCost() > this.objectCoins) {
            return false;
        }

        Tile tile = this.farm.getTile(row, column);
        if (tile.isPlowed() == false) {
            return false;
        }

        Crop crop = tile.getCrop();
        if (crop != null) {
            return false;
        }

        tile.plant(seed);
        this.deductCoins(seed.getCost());
        return true;
    }

    /**
     * Waters a tile.
     * @param row       The row of the tile.
     * @param column    The column of the tile.
     * @return  True if the tile was successfully watered, false otherwise.
     */
    public boolean water(int row, int column) {
        Tile tile = this.farm.getTile(row, column);
        if (!tile.isPlowed()) {
            return false;
        }

        Crop crop = tile.getCrop();
        if (crop == null) {
            return false;
        }

        crop.water();
        this.addExperience(0.5f);
        return true;
    }

    /**
     * Fertilizes a tile.
     * @param row       The row of the tile.
     * @param column    The column of the tile.
     * @return  True if the tile was successfully fertilized, false otherwise.
     */
    public boolean fertilize(int row, int column) {
        if (this.objectCoins < 10) {
            return false;
        }

        Tile tile = this.farm.getTile(row, column);
        if (tile.isPlowed() == false) {
            return false;
        }

        Crop crop = tile.getCrop();
        if (crop == null) {
            return false;
        }

        this.deductCoins(10);
        crop.fertilize();
        this.addExperience(4.0f);
        return true;
    }

    /**
     * Harvests a crop from a tile, if applicable.
     * @param row       The row of the tile.
     * @param column    The column of the tile.
     * @return  True if the crop, if present, was successfully harvested, false otherwise.
     */
    public boolean harvest(int row, int column) {
        int WaterBonusLimit;
        int FertilizerBonusLimit;
        
        float HarvestTotal;
        float WaterBonus;
        float FertilizerBonus;

        Tile tile = this.farm.getTile(row, column);
        if (tile.getCrop() == null) {
            return false;
        }

        Crop crop = tile.getCrop();
        if (crop.isHarvestReady() == false) {
            return false;
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
        this.addCoins(HarvestTotal + WaterBonus + FertilizerBonus);
        this.addExperience(crop.getSeed().getExpYield());
    
        System.out.println("  [MESSAGE] Harvested " + tile.getCrop().getProduce() + " " + tile.getCrop().getSeed().getName()
                            + " and sold for " + HarvestTotal + WaterBonus + FertilizerBonus + " ObjectCoins");
        tile.harvest();
        return true;
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
     * @return  True if the coins were successfully deducted, false otherwise.
     */
    public boolean deductCoins(int amount) {
        if (amount > this.objectCoins) {
            return false;
        }

        this.objectCoins -= amount;
        return true;
    }

    /**
     * Tries to upgrade the current farmer type of the player.
     * @return  True if the farmer type was successfully upgraded, false otherwise.
     */
    public boolean upgradeFarmer(ArrayList<FarmerType> farmerTypes) {
        FarmerType newType = farmerTypes.get(this.farmerTypeLevel + 1);

        boolean check = this.farmerTypeLevel + 1 < farmerTypes.size() && 
                        this.level >= newType.getLevelRequirement() && this.objectCoins >= newType.getRegistrationFee();

        if (check == true) {
            this.farmerTypeLevel++;
            this.farmerType = farmerTypes.get(this.farmerTypeLevel);
        }

        return check;
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

    public FarmerType getFarmerType() {
        return this.farmerType;
    }
}
