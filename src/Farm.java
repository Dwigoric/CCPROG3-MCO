import java.util.ArrayList;

public class Farm {
    private final int rows = 10;
    private final int columns = 5;
    private final ArrayList<ArrayList<Tile>> tiles = new ArrayList<ArrayList<Tile>>();
    private int day = 1;

    public Farm() {
        for (int i = 0; i < this.rows; i++) {
            ArrayList<Tile> row = new ArrayList<Tile>();
            for (int j = 0; j < this.columns; j++) row.add(new Tile(this));
            tiles.add(row);
        }
    }

    public void advanceDay() {
        this.day++;
        for (ArrayList<Tile> row : tiles) {
            for (Tile tile : row) {
                if (tile.getCrop() != null) {
                    tile.getCrop().addAge();
                }
            }
        }
    }

    public Tile getTile(int row, int column) {
        return this.tiles.get(row).get(column);
    }

    public int getDay() {
        return this.day;
    }
}
