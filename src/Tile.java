/**
 * The Tile class.
 */
public class Tile {
    private boolean isPlowed = false;
    private boolean hasRock = false;
    private boolean isOccupiedByTree = false;
    private Crop crop = null;

    public Tile() {}

    public Tile(boolean isRock) {
        this.hasRock = isRock;
    }

    /**
     * Plows the tile.
     * @return  True if the tile was successfully plowed, false otherwise.
     */
    public boolean plow() {
        if (this.isPlowed == true || this.hasRock == true) {
            return false;
        }

        this.isPlowed = true;
        return true;
    }

    /**
     * Plants a seed on the tile.
     * @param seed  The seed to plant.
     * @return  True if the seed was successfully planted, false otherwise.
     */
    public boolean plant(Seed seed) {
        if (!this.isPlowed || this.crop != null) {
            return false;
        }

        this.crop = new Crop(seed);
        return true;
    }

    /**
     * Harvests (removes) the crop on the tile.
     */
    public void harvest() {
        this.isPlowed = false;
        this.crop = null;
    }

    public void pickaxe() {
        this.hasRock = false;
    }

    /**
     * Sets whether the tile is occupied by a tree.
     * @param state    True if the tile is occupied by a tree, false otherwise.
     */
    public void setTreeState(boolean state) {
        this.isOccupiedByTree = state;
    }

    /**
     * Gets whether the tile is occupied by a tree.
     * @return  True if the tile is occupied by a tree, false otherwise.
     */
    public boolean isOccupiedByTree() {
        return isOccupiedByTree;
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