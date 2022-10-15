public class Tile {
    private Farm farm;
    private boolean plowed = false;
    private Crop crop = null;
    private Rock rock = null;

    public Tile(Farm farm) {
        this.farm = farm;
    }

    public void plow() {
        this.plowed = true;
    }

    public void shovel() {
        this.crop = null;
        this.plowed = false;
    }

    public void pickaxe() {
        this.rock = null;
    }

    public boolean isPlowed() {
        return this.plowed;
    }

    public Crop getCrop() {
        return this.crop;
    }

    public Rock getRock() {
        return this.rock;
    }
}