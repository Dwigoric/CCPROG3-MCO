import javax.swing.ImageIcon;

/**
 * The Tile class.
 */
public class Tile {
    /**
     * Whether the tile is plowed.
     */
    private boolean isPlowed = false;
    /**
     * Whether the tile has a rock.
     */
    private boolean hasRock;
    /**
     * The crop on the tile.
     */
    private Crop crop = null;

    /**
     * The image icon for the tile when it is unplowed.
     */
    public static final ImageIcon IMG_TILE_UNPLOWED = new ImageIcon("res/tile_unplowed.png");
    /**
     * The image icon for the tile when it is plowed.
     */
    public static final ImageIcon IMG_TILE_PLOWED = new ImageIcon("res/tile_plowed.png");
    /**
     * The image icon for the tile when it has a rock.
     */
    public static final ImageIcon IMG_TILE_ROCK = new ImageIcon("res/tile_rock.png");
    /**
     * The image icon for the tile when it has a withered crop.
     */
    public static final ImageIcon IMG_TILE_WITHERED = new ImageIcon("res/tile_withered.png");

    /**
     * The image icon for the tile when it is unplowed and selected.
     */
    public static final ImageIcon IMG_TILE_UNPLOWED_SELECTED = new ImageIcon("res/tile_unplowed_selected.png");
    /**
     * The image icon for the tile when it is plowed and selected.
     */
    public static final ImageIcon IMG_TILE_PLOWED_SELECTED = new ImageIcon("res/tile_plowed_selected.png");
    /**
     * The image icon for the tile when it has a rock and selected.
     */
    public static final ImageIcon IMG_TILE_ROCK_SELECTED = new ImageIcon("res/tile_rock_selected.png");
    /**
     * The image icon for the tile when it has a withered crop and selected.
     */
    public static final ImageIcon IMG_TILE_WITHERED_SELECTED = new ImageIcon("res/tile_withered_selected.png");

    /**
     * Constructs a new Tile with a rock.
     * @param hasRock   Whether the tile has a rock.
     */
    public Tile(boolean hasRock) {
        this.hasRock = hasRock;
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