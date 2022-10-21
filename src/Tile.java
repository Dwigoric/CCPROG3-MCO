import java.util.Random;

public class Tile {
    private String state = "unplowed";
    private Crop crop;

    Random rand = new Random();

    public Tile() {}

    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;

        if(state != "unplowed" && state != "plowed") {
            this.crop = new Crop("Root Crop", 2, 1, 0, rand.nextInt(2) + 1, 5, 6, 5);
        }
    }

    public Crop getCrop () {
        return this.crop;
    }

    public void resetTile() {
        this.state = "unplowed";
        this.crop = null;
    }
}