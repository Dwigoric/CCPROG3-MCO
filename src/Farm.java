import java.util.ArrayList;

public class Farm {
    private final Tile[][] tiles;
    private final int rows;
    private final int columns;
    private final ArrayList<Seed> seedList = new ArrayList<>();

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

    public boolean pickaxe(int row, int column) {
        if (!this.tiles[row][column].isRock()) return false;
        this.tiles[row][column] = new Tile();
        return true;
    }

    public void shovel(int row, int column) {
        Crop crop = this.tiles[row][column].getCrop();

        if (crop == null) return;
        this.tiles[row][column] = new Tile();
    }

    public void advanceDay() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                Crop crop = this.tiles[i][j].getCrop();
                if (crop != null) crop.addAge();
            }
        }
    }

    public Seed findSeed(String seedName) {
        for (Seed seed : this.seedList) {
            if (seed.getName().equals(seedName)) return seed;
        }
        return null;
    }

    public ArrayList<Seed> getSeedList() {
        return this.seedList;
    }

    public Tile getTile(int row, int column) {
        return this.tiles[row][column];
    }

    public void displayFarm() {
        System.out.println("");

        for(int i = 0; i < this.rows; i++) {
            for(int j = 0; j < this.columns; j++) {
                Tile currTile = this.tiles[i][j];

                System.out.println(" +=============+");
                System.out.print(" +             +\n ");

                if(currTile.isRock() == true) {
                    System.out.println("rock");
                } else if (currTile.getCrop() != null){
                    System.out.println(currTile.getCrop().getSeed().getName());
                } else if (currTile.isPlowed() == true) {
                    System.out.println("plowed");
                } else {
                    System.out.println("unplowed");
                }

                System.out.println(" +             +");
                System.out.println(" +=============+");
            }

            System.out.println("");
        }
    }
}
