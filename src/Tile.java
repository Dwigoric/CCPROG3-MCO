/**
 * The Tile class.
 */
public class Tile {
    private boolean isPlowed = false;
    private boolean hasRock = false;
    private Crop crop = null;

    public Tile() {}

    public Tile(boolean isRock) {
        this.hasRock = isRock;
    }

    /**
     * Plows the tile.
     */
    public void plow() {
        this.isPlowed = true;
    }

    /**
     * Plants a seed on the tile.
     * @param seed  The seed to plant.
     */
    public void plant(Seed seed) {
        this.crop = new Crop(seed);
    }

    /**
     * Harvests (removes) the crop on the tile.
     */
    public void harvest() {
        this.isPlowed = false;
        this.crop = null;
    }

    /**
     * Uses the pickaxe on the tile.
     */
    public void pickaxe() {
        this.hasRock = false;
    }

    /**
     * Whether the tile is plowed.
     * @return  True if the tile is plowed, false otherwise.
     */
    public boolean isPlowed() {
        return this.isPlowed;
    }

    /**
     * Whether the tile has a rock.
     * @return  True if the tile has a rock, false otherwise.
     */
    public boolean hasRock() {
        return this.hasRock;
    }

    /**
     * Gets the crop on the tile.
     * @return  The crop on the tile.
     */
    public Crop getCrop() {
        return this.crop;
    }
}