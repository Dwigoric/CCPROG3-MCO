public class Tile {
    private boolean isPlowed = false;
    private boolean hasRock = false;
    private Crop crop = null;

    public Tile() {}

    public Tile(boolean isRock) {
        this.hasRock = isRock;
    }

    public boolean plow() {
        if (this.isPlowed == true || this.hasRock == true) {
            return false;
        }

        this.isPlowed = true;
        return true;
    }

    public boolean plant(Seed seed) {
        if (this.isPlowed == false || this.crop != null) {
            return false;
        }

        this.crop = new Crop(seed);
        return true;
    }

    public void harvest() {
        this.isPlowed = false;
        this.crop = null;
    }

    public boolean isPlowed() {
        return this.isPlowed;
    }

    public boolean hasRock() {
        return this.hasRock;
    }

    public Crop getCrop() {
        return this.crop;
    }
}