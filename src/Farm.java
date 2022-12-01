import java.util.ArrayList;

/**
 * The Farm class.
 */
public class Farm {
    private final int rows;
    private final int columns;
    private final Tile[][] tiles;

    /**
     * Creates a new Farm.
     * @param rows      The number of rows in the farm.
     * @param columns   The number of columns in the farm.
     * @param rocks     The list of coordinates of tiles that have rocks.
     */
    public Farm(int rows, int columns, ArrayList<Integer[]> rocks) {
        this.tiles = new Tile[rows][columns];
        this.rows = rows;
        this.columns = columns;

        int currRock = 0;

        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.columns; j++) {
                boolean isRock = currRock < rocks.size() &&
                        rocks.get(currRock)[0] == i && rocks.get(currRock)[1] == j;

                this.tiles[i][j] = new Tile(isRock);
                if (isRock) currRock++;
            }
        }
    }

    /**
     * Reset tile
     * @param row       The row of the tile.
     * @param column    The column of the tile.
     */
    public void resetTile(int row, int column) {
        this.tiles[row][column] = new Tile();
    }

    /**
     * Checks whether the farm has any crops.
     * @return  True if the farm has any crops, false otherwise.
     */
    public boolean hasCrop() {
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.columns; j++) {
                if (this.tiles[i][j].getCrop() != null) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Checks whether the farm has no available plots and all crops are dead.
     * @return  True if the farm has no available plots and all crops are dead, false otherwise.
     */
    public boolean isAllWithered() {
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.columns; j++) {
                Crop crop = this.tiles[i][j].getCrop();

                if (crop == null || crop.isAlive()) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Checks whether the tile is empty.
     * @param row       The row of the tile.
     * @param column    The column of the tile.
     * @return  True if the tile is empty, false otherwise.
     */
    public boolean isEmpty(int row, int column) {
        Tile tile = this.tiles[row][column];

        return !tile.hasRock() && tile.getCrop() == null;
    }

    /**
     * Checks whether a tree can be planted on the specified tile.
     * @param row       The row of the tile.
     * @param column    The column of the tile.
     * @return  True if a tree can be planted on the specified tile, false otherwise.
     */
    public boolean canPlantTree(int row, int column) {
        if (row == 0 || column == 0 || row == this.rows - 1 || column == this.columns - 1) return false;

        return this.isEmpty(row - 1, column - 1) &&
                this.isEmpty(row - 1, column) &&
                this.isEmpty(row - 1, column + 1) &&
                this.isEmpty(row, column - 1) &&
                this.isEmpty(row, column + 1) &&
                this.isEmpty(row + 1, column - 1) &&
                this.isEmpty(row + 1, column) &&
                this.isEmpty(row + 1, column + 1);
    }

    /**
     * Gets the number of rows in the farm.
     * @return  The number of rows in the farm.
     */
    public int getRows() {
        return this.rows;
    }

    /**
     * Gets the number of columns in the farm.
     * @return  The number of columns in the farm.
     */
    public int getColumns() {
        return this.columns;
    }

    /**
     * Gets a tile in the farm.
     * @param row       The row of the tile.
     * @param column    The column of the tile.
     * @return  The tile at the specified row and column.
     */
    public Tile getTile(int row, int column) {
        return this.tiles[row][column];
    }
}
