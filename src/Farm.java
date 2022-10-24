/**
 * The Farm class.
 */
public class Farm {
    /**
     * The number of rows in the farm.
     */
    private final int rows;
    /**
     * The number of columns in the farm.
     */
    private final int columns;
    /**
     * The plots (Tiles) in the farm.
     */
    private final Tile[][] tiles;

    /**
     * Creates a new Farm.
     * @param rows      The number of rows in the farm.
     * @param columns   The number of columns in the farm.
     */
    public Farm(int rows, int columns) {
        this.tiles = new Tile[rows][columns];
        this.rows = rows;
        this.columns = columns;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                this.tiles[i][j] = new Tile();
            }
        }
    }

    public boolean hasCrop() {
        // save this for after prototype
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.columns; j++) {
                if (this.tiles[i][j].getCrop() != null) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean isAllWithered() {
        // save this for after prototype
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

    /**
     * Displays the farm (assumption of only one tile), and Turnip is the only seed.
     */
    public void displayFarm() {
        Tile currTile = this.tiles[0][0];

        System.out.println("\n");

        System.out.printf(" %38s%n", "+-----------------+");
        System.out.printf(" %38s%n", "+                 +");

        System.out.printf(" %22s" , "+  ");

        if(currTile.getCrop() != null) {
            if(currTile.getCrop().isAlive() == true) {
                System.out.print(currTile.getCrop().getSeed().getName() + "  ");
                System.out.print("(" + currTile.getCrop().getWaterCount() + ")");
                System.out.print("(" + currTile.getCrop().getFertilizeCount() + ")");
            } else {
                System.out.print(currTile.getCrop().getSeed().getName() + "  ");
                System.out.print("(dead)");
            }
            
        } else {
            if(currTile.hasRock() == true) {
                System.out.print("     rock     ");
            } else if (currTile.isPlowed() == true) {
                System.out.print("    plowed    ");
            } else if (currTile.isPlowed() == false) {
                System.out.print("   unplowed   ");
            }
        }

        System.out.println(" +");

        System.out.printf(" %38s%n", "+                 +");
        System.out.printf(" %38s%n", "+-----------------+");

        System.out.println("\n");
    }
}
