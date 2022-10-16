public class Farm {
    private final Tile[][] Tiles = new Tile[1][1];

    public Farm() {
        Tiles[0][0] = new Tile();
    }

    public void displayFarm() {
        System.out.println(" +==========+");
        System.out.println(" +          +");

        if(Tiles[0][0].getState() == "unplowed") {
            System.out.println(" + UNPLOWED +");
        } else {
            System.out.println(" +  PLOWED  +");
        }

        System.out.println(" +          +");
        System.out.println(" +==========+");
    }

    public Tile getTile(int row, int column) {
        return Tiles[row][column];
    }
}
