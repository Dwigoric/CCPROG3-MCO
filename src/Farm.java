public class Farm {
    private final Tile[][] tiles;
    private final int rows;
    private final int columns;

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

    public Tile getTile(int row, int column) {
        return this.tiles[row][column];
    }

    public int getRows() {
        return this.rows;
    }

    public int getColumns() {
        return this.columns;
    }

    //assumes farm has only one tile and turnip is the only seed
    public void displayFarm() {
        Tile currTile = this.tiles[0][0];

        System.out.println("\n");

        System.out.println(String.format(" %38s", "+-----------------+"));
        System.out.println(String.format(" %38s", "+                 +"));

        System.out.print(String.format(" %22s" , "+  "));

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
            if(currTile.isRock() == true) {
                System.out.print("     rock     ");
            } else if (currTile.isPlowed() == true) {
                System.out.print("    plowed    ");
            } else if (currTile.isPlowed() == false) {
                System.out.print("   unplowed   ");
            }
        }

        System.out.println(" +");

        System.out.println(String.format(" %38s", "+                 +"));
        System.out.println(String.format(" %38s", "+-----------------+"));

        System.out.println("\n");
    }
}
