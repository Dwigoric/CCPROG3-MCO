public class Farm {
    private final int rows;
    private final int columns;
    private final Tile[][] tiles;

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

    public int getRows() {
        return this.rows;
    }

    public int getColumns() {
        return this.columns;
    }

    public Tile getTile(int row, int column) {
        return this.tiles[row][column];
    }

    //assumes farm has only one tile and turnip is the only seed
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
