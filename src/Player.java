import java.util.ArrayList;
import java.util.Arrays;

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

    public Player(Farm farm) {
        this.farm = farm;
    }

    public boolean plow(int row, int column) {
        if (this.farm.getTile(row, column).isPlowed()) {
            return false;
        }

        this.experience += 0.5f;
        return this.farm.getTile(row, column).plow();
    }

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
        this.experience += 0.5f;
        return true;
    }

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
        this.experience += 4.0;
        return true;
    }

    public boolean harvest(int row, int column) {
        float HarvestTotal;
        float WaterBonus;
        float FertilizerBonus;

        Tile tile = this.farm.getTile(row, column);
        if (tile.getCrop() == null) {
            return false;
        }

        Crop crop = tile.getCrop();
        if (crop.isAlive() == false) {
            return false;
        }

        if(crop.isHarvestReady() == false) {
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

    public void addExperience(float amount) {
        this.experience += amount;
        this.level = (int) this.experience / 100;
    }

    public void addCoins(int amount) {
        this.objectCoins += amount;
    }

    public boolean deductCoins(int amount) {
        if (amount > this.objectCoins) {
            return false;
        }

        this.objectCoins -= amount;
        return true;
    }

    public boolean upgradeFarmer() {
        FarmerType newType = this.farmerTypes.get(this.farmerType + 1);

        boolean check = this.farmerType < this.farmerTypes.size() &&
                this.getLevel() >= newType.getLevelRequirement() &&
                this.objectCoins >= newType.getRegistrationFee();

        if (check == true) this.farmerType++;
        return check;
    }

    public int getLevel() {
        return this.level;
    }

    public float getExperience() {
        return this.experience;
    }

    public FarmerType getType() {
        return this.farmerTypes.get(this.farmerType);
    }

    public int getObjectCoins() {
        return this.objectCoins;
    }

    public void displayInfo(int day) {
        String infoLine;

        infoLine = String.format("  Type: %s | ObjectCoins: %d | Level: %d | XP: %f | Day: %d", 
            this.farmerTypes.get(farmerType).getTypeName(), this.objectCoins, this.level, this.experience, day);

        System.out.println(" -----------------------------------------------------------------------");
        System.out.println(infoLine);
        System.out.println(" -----------------------------------------------------------------------");
    }
}
