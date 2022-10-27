import java.util.ArrayList;
import java.util.Arrays;

/**
 * The Player class.
 */
public class Player {
    private final Farm farm;

    private int level = 0;
    private float experience = 0;
    private int objectCoins = 100;
    private int farmerType = 0;
    private final ArrayList<FarmerType> farmerTypes = new ArrayList<>(Arrays.asList(
            new FarmerType("Farmer", 0, 0, 0, 0, 0, 0),
            new FarmerType("Registered Farmer", 5, 1, 1, 0, 0, 200),
            new FarmerType("Distinguished Farmer", 10, 2, 2, 1, 0, 300),
            new FarmerType("Legendary Farmer", 15, 4, 3, 2, 1, 400)
    ));

    /**
     * The Player class constructor.
     * @param farm The farm owned by the player.
     */
    public Player(Farm farm) {
        this.farm = farm;
    }

    /**
     * Plows a tile.
     * @param row       The row of the tile.
     * @param column    The column of the tile.
     * @return  True if the tile was successfully plowed, false otherwise.
     */
    public boolean plow(int row, int column) {
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
        this.deductCoins(10);

        Tile tile = this.farm.getTile(row, column);
        if (tile.isPlowed() == false) {
            return false;
        }

        Crop crop = tile.getCrop();
        if (crop == null) {
            return false;
        }

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
        HarvestTotal = crop.getProduce() * (crop.getSeed().getBaseSellingPrice() + farmerTypes.get(farmerType).getBonusEarnings());
        
        if(crop.getWaterCount() > crop.getSeed().getWaterLimit() + farmerTypes.get(farmerType).getWaterBonusLimitIncrease()) {
            WaterBonus = HarvestTotal * 0.2f * (crop.getSeed().getWaterLimit() + farmerTypes.get(farmerType).getWaterBonusLimitIncrease() - 1);
        } else {
            WaterBonus = HarvestTotal * 0.2f * (crop.getWaterCount() - 1);
        }
        
        if(crop.getFertilizeCount() > crop.getSeed().getFertilizerLimit() + farmerTypes.get(farmerType).getFertilizerBonusLimitIncrease()) {
            FertilizerBonus = HarvestTotal * 0.2f * (crop.getSeed().getFertilizerLimit() + farmerTypes.get(farmerType).getFertilizerBonusLimitIncrease());
        } else {
            FertilizerBonus = HarvestTotal * 0.5f * (crop.getFertilizeCount());
        }

        /* Player Information Update */
        this.addCoins((int)(HarvestTotal + WaterBonus + FertilizerBonus));
        this.addExperience(tile.getCrop().getProduce() * tile.getCrop().getSeed().getExpYield());
    
        System.out.println("  [MESSAGE] Harvested " + tile.getCrop().getProduce() + " " + tile.getCrop().getSeed().getName()
                            + " and sold for " + (int)(HarvestTotal + WaterBonus + FertilizerBonus) + " ObjectCoins");
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
    public void addCoins(int amount) {
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
    public boolean upgradeFarmer() {
        FarmerType newType = this.farmerTypes.get(this.farmerType + 1);

        boolean check = this.farmerType < this.farmerTypes.size() &&
                this.getLevel() >= newType.getLevelRequirement() &&
                this.objectCoins >= newType.getRegistrationFee();

        if (check == true) this.farmerType++;
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
     * Gets the farmer type of the player.
     * @return  The farmer type of the player.
     */
    public FarmerType getType() {
        return this.farmerTypes.get(this.farmerType);
    }

    /**
     * Gets the current coins of the player.
     * @return  The current coins of the player.
     */
    public int getObjectCoins() {
        return this.objectCoins;
    }

    /**
     * Displays the player's information.
     * @param day  The current day.
     */
    public void displayInfo(int day) {
        String infoLine;

        infoLine = String.format("  Type: %s | ObjectCoins: %d | Level: %d | XP: %f | Day: %d", 
            this.farmerTypes.get(farmerType).getTypeName(), this.objectCoins, this.level, this.experience, day);

        System.out.println(" -----------------------------------------------------------------------");
        System.out.println(infoLine);
        System.out.println(" -----------------------------------------------------------------------");
    }
}
