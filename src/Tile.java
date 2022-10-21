public class Tile {
    private boolean plowed = false;
    private boolean rock = false;
    private Crop crop = null;

    public Tile() {}

    public Tile(boolean isRock) {
        this.rock = isRock;
    }

    public boolean plant(Seed seed) {
        if (!this.plowed || this.crop != null) {
            return false;
        }

        this.crop = new Crop(seed);
        return true;
    }

    public boolean harvest() {
        if (this.crop == null) {
            return false;
        }

        this.plowed = false;
        this.crop = null;
        return true;
    }

    public boolean plow() {
        if (this.plowed == true || this.rock == true) {
            return false;
        }
        this.plowed = true;
        return true;
    }

    public boolean isPlowed() {
        return this.plowed;
    }

    public boolean isRock() {
        return this.rock;
    }

    public Crop getCrop() {
        return this.crop;
    }

    public void HarvestCrop() {
        this.plowed = false;
        this.crop = null;
    }
}
