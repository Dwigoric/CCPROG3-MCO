public class Farm {
    private final Tile[][] Tiles = new Tile[1][1];

    public Farm() {
        Tiles[0][0] = new Tile();
    }

    public void displayFarm() {
        System.out.println(" +==========+");
        System.out.println(" +          +");
        System.out.print(" +" + Tiles[0][0].getState());
        
        if(Tiles[0][0].getCrop() != null)
            System.out.println("(" + Tiles[0][0].getCrop().getCurrWater() + "|" + Tiles[0][0].getCrop().getCurrFertilize());

        System.out.println(" +          +");
        System.out.println(" +==========+");
    }

    public Tile getTile(int row, int column) {
        return Tiles[row][column];
    }
}
