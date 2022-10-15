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

    }

    public void fertilizeCrop(int row, int column) {

    }

    public void plowTile(int row, int column) {

    }

    public void shovelTile(int row, int column) {

    }

    public void pickaxeTile(int row, int column) {

    }

    public int getCoins() {
        return objectCoins;
    }

    public Farm getFarm() {
        return farm;
    }
}