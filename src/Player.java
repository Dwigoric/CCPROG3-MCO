import java.util.ArrayList;
import java.util.Arrays;

public class Player {
    private Farm farm;
    private int level = 0;
    private float exp = 0;
    private int objectCoins = 100;
    private int typeLevel = 0;

    private ArrayList<String> typeName = new ArrayList<String>(Arrays.asList(
            "Farmer",
            "Registered Farmer",
            "Distinguished Farmer",
            "Legendary Farmer"
    ));

    public void addExp(float amount) {
        this.exp += amount;
    }

    public void upgradeType() {
        this.typeLevel++;
    }

    public void buySeeds() {

    }

    public void plantSeed(String cropType) {

    }

    public boolean harvestCrop(int row, int column) {
        return false;
    }

    public void waterCrop(int row, int column) {
        Crop crop = this.farm.getTile(row, column).getCrop();
        if (crop != null) crop.water();
    }

    public void fertilizeCrop(int row, int column) {
        Crop crop = this.farm.getTile(row, column).getCrop();
        if (crop != null) crop.fertilize();
    }

    public void plowTile(int row, int column) {
        this.farm.getTile(row, column).plow();
    }

    public boolean shovelTile(int row, int column) {
        if (this.objectCoins < 7) return false;
        this.objectCoins -= 7;

        this.farm.getTile(row, column).shovel();

        return true;
    }

    public boolean pickaxeTile(int row, int column) {
        if (this.objectCoins < 50) return false;
        this.objectCoins -= 50;

        Tile tile = this.farm.getTile(row, column);
        if (tile.getRock() == null) return false;
        tile.pickaxe();

        return true;
    }

    public int getCoins() {
        return objectCoins;
    }

    public Farm getFarm() {
        return farm;
    }
}