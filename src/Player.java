import java.util.ArrayList;

import javax.swing.ImageIcon;

/**
 * The Player class.
 */
public class Player {
    /**
     * The Farm instance that the player is using.
     */
    private final Farm farm;

    /**
     * The amount of Objectcoins the player has.
     */
    private float objectCoins = 100;
    /**
     * The amount of experience the player has.
     */
    private float experience = 0;
    /**
     * The current level of the player.
     */
    private int level = 0;

    /**
     * The index of the current farmer type of the player based on the list of farmer types.
     */
    private int farmerTypeLevel = 0;
    /**
     * The current farmer type of the player.
     */
    private FarmerType farmerType;

    /**
     * The image icon of the button to water a crop.
     */
    public static final ImageIcon IMG_BUTTON_WATER = new ImageIcon("res/button_water.png");
    /**
     * The image icon of the button to fertilize a crop.
     */
    public static final ImageIcon IMG_BUTTON_FERTILIZE = new ImageIcon("res/button_fertilize.png");
    /**
     * The image icon of the button to harvest a crop.
     */
    public static final ImageIcon IMG_BUTTON_HARVEST = new ImageIcon("res/button_harvest.png");
    /**
     * The image icon of the button to plow a tile.
     */
    public static final ImageIcon IMG_BUTTON_PLOW = new ImageIcon("res/button_plow.png");
    /**
     * The image icon of the button to shovel a tile.
     */
    public static final ImageIcon IMG_BUTTON_SHOVEL = new ImageIcon("res/button_shovel.png");
    /**
     * The image icon of the button to pickaxe a tile with a rock.
     */
    public static final ImageIcon IMG_BUTTON_PICKAXE = new ImageIcon("res/button_pickaxe.png");

    /**
     * The image icon of the button to plant a turnip.
     */
    public static final ImageIcon IMG_BUTTON_TURNIP = new ImageIcon("res/button_turnip.png");
    /**
     * The image icon of the button to plant a carrot.
     */
    public static final ImageIcon IMG_BUTTON_CARROT = new ImageIcon("res/button_carrot.png");
    /**
     * The image icon of the button to plant a potato.
     */
    public static final ImageIcon IMG_BUTTON_POTATO = new ImageIcon("res/button_potato.png");
    /**
     * The image icon of the button to plant a rose.
     */
    public static final ImageIcon IMG_BUTTON_ROSE = new ImageIcon("res/button_rose.png");
    /**
     * The image icon of the button to plant a tulip.
     */
    public static final ImageIcon IMG_BUTTON_TULIPS = new ImageIcon("res/button_tulips.png");
    /**
     * The image icon of the button to plant a sunflower.
     */
    public static final ImageIcon IMG_BUTTON_SUNFLOWER = new ImageIcon("res/button_sunflower.png");
    /**
     * The image icon of the button to plant a mango tree.
     */
    public static final ImageIcon IMG_BUTTON_MANGO = new ImageIcon("res/button_mango.png");
    /**
     * The image icon of the button to plant an apple tree.
     */
    public static final ImageIcon IMG_BUTTON_APPLE = new ImageIcon("res/button_apple.png");

    /**
     * The Player class constructor.
     * @param farm          The farm owned by the player.
     * @param farmerType    The farmer type of the player.
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
        this.farm.getTile(row, column).plant(seed);
        this.objectCoins -= seed.cost() - this.farmerType.seedCostReduction();
    }

    /**
     * Waters a tile.
     * @param row       The row of the tile.
     * @param column    The column of the tile.
     */
    public void water(int row, int column) {
        this.farm.getTile(row, column).getCrop().water();
        this.addExperience(0.5f);
    }

    /**
     * Fertilizes a tile.
     * @param row       The row of the tile.
     * @param column    The column of the tile.
     */
    public void fertilize(int row, int column) {
        this.objectCoins -= 10;
        this.farm.getTile(row, column).getCrop().fertilize();
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
        Crop crop = tile.getCrop();

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
        this.farm.getTile(row, column).pickaxe();
        this.objectCoins -= 50;
        this.addExperience(15);
    }

    /**
     * Uses the shovel on a tile.
     * @param row       The row of the tile.
     * @param column    The column of the tile.
     */
    public void shovel(int row, int column) {
        this.objectCoins -= 7;

        if (!this.farm.getTile(row, column).hasRock()) this.farm.resetTile(row, column);
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
        this.farmerTypeLevel++;
        this.farmerType = farmerTypes.get(this.farmerTypeLevel);

        this.objectCoins -= this.farmerType.registrationFee();
    }

    /**
     * Checks if the player can upgrade their farmer type.
     * @param farmerTypes   The list of farmer types.
     * @return  True if the player can upgrade their farmer type, false otherwise.
     */
    public boolean canUpgradeFarmer(ArrayList<FarmerType> farmerTypes) {
        if (this.farmerTypeLevel == farmerTypes.size() - 1) {
            return false;
        }

        FarmerType newType = farmerTypes.get(this.farmerTypeLevel + 1);

        return this.level >= newType.levelRequirement() && this.objectCoins >= newType.registrationFee();
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
