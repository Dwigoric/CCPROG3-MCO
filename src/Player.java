import java.util.ArrayList;
import java.util.Arrays;

public class Player {
    private final Farm farm;
    private int objectCoins = 100;
    private float experience = 0;
    private final ArrayList<FarmerType> farmerTypes = new ArrayList<>(Arrays.asList(
            new FarmerType("Farmer", 0, 0, 0, 0, 0, 0),
            new FarmerType("Registered Farmer", 5, 1, 1, 0, 0, 200),
            new FarmerType("Distinguished Farmer", 10, 2, 2, 1, 0, 300),
            new FarmerType("Legendary Farmer", 15, 4, 3, 2, 1, 400)
    ));
    private int farmerType = 0;

    public Player(Farm farm) {
        this.farm = farm;
    }

    public boolean plow(int row, int column) {
        if (this.farm.getTile(row, column).isPlowed()) return false;
        this.experience += 0.5f;
        return this.farm.getTile(row, column).plow();
    }

    public boolean pickaxe(int row, int column) {
        if (this.objectCoins < 50) return false;
        this.objectCoins -= 50;
        this.experience += 15;

        return this.farm.pickaxe(row, column);
    }

    public boolean shovel(int row, int column) {
        if (this.objectCoins < 7) return false;
        this.objectCoins -= 7;

        this.farm.shovel(row, column);
        this.experience += 2;
        return true;
    }

    public boolean water(int row, int column) {
        Tile tile = this.farm.getTile(row, column);
        if (!tile.isPlowed()) return false;

        Crop crop = tile.getCrop();
        if (crop == null) return false;

        crop.water();
        this.experience += 0.5f;
        return true;
    }

    public boolean fertilize(int row, int column) {
        Tile tile = this.farm.getTile(row, column);
        if (!tile.isPlowed()) return false;

        Crop crop = tile.getCrop();
        if (crop == null) return false;

        crop.fertilize();
        this.experience += 4;
        return true;
    }

    public boolean deductCoins(int amount) {
        if (amount > this.objectCoins) return false;
        this.objectCoins -= amount;
        return true;
    }

    public void addCoins(int amount) {
        this.objectCoins += amount;
    }

    public float getExperience() {
        return this.experience;
    }

    public void addExperience(float amount) {
        this.experience += amount;
    }

    public boolean upgradeFarmer() {
        FarmerType newType = this.farmerTypes.get(this.farmerType + 1);

        boolean check = this.farmerType < this.farmerTypes.size() &&
                newType.getLevelRequirement() >= this.getLevel() &&
                newType.getRegistrationFee() >= this.objectCoins;

        if (check) this.farmerType++;
        return check;
    }

    public int getLevel() {
        return (int)this.experience / 100;
    }

    public int getObjectCoins() {
        return this.objectCoins;
    }

    public void displayInfo() {
        System.out.println(" ==========================================================");

        System.out.println("Type: " + this.farmerTypes.get(farmerType).getTypeName() + 
                          " ObjectCoins: " + this.objectCoins + " XP: " + this.experience);

        System.out.println(" ==========================================================");
    }
}
