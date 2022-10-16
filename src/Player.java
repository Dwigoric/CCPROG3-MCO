public class Player {
    private String name;
    private Farm farm;

    private int objectCoins = 100;
    private int level = 0;
    private float exp = 0;
    private int[] seedInventory = {0, 0, 0, 0, 0, 0, 0, 0};

    private int typeLevel = 0;
    private int[] typeBuff = {0, 0, 0, 0};
    private String typeName = "Farmer";

    public Player(String name, Farm farm) {
        this.name = name;
        this.farm = farm;
    }

    public void addExp(float amount) {
        this.exp+= amount;
    }

    public void upgradeType() {
        this.typeLevel++;

        switch(this.typeLevel) {
            case 1:
                this.typeName = "Registered Farmer";
                this.typeBuff[0] = 1;
                this.typeBuff[1] = -1;

                break;
            case 2:
                this.typeName = "Distinguished Farmer";
                this.typeBuff[0] = 2;
                this.typeBuff[1] = -2;
                this.typeBuff[2] = 1;

                break;
            case 3:
                this.typeName = "Legendary Farmer";
                this.typeBuff[0] = 4;
                this.typeBuff[1] = -3;
                this.typeBuff[2] = 2;
                this.typeBuff[3] = 1;

                break;
            default:
                // none
                break;
        }
    }

    public void plantSeed(int row, int column, String cropType) {
        this.farm.getTile(0, 0).setState(cropType);
    }
    // public boolean harvestCrop(int row, int column) {

    // }

    // public void waterCrop(int row, int column) {
    //     Crop crop = this.farm.getTile(row, column).getCrop();
    //     if (crop != null) crop.water();
    // }

    // public void fertilizeCrop(int row, int column) {
    //     Crop crop = this.farm.getTile(row, column).getCrop();
    //     if (crop != null) crop.fertilize();
    // }

    public void plowTile(int row, int column) {
        this.farm.getTile(row, column).setState("plowed");
    }

    // public boolean shovelTile(int row, int column) {
    //     if (this.objectCoins < 7) return false;
    //     this.objectCoins -= 7;

    //     this.farm.getTile(row, column).shovel();

    //     return true;
    // }

    // public boolean pickaxeTile(int row, int column) {
    //     if (this.objectCoins < 50) return false;
    //     this.objectCoins -= 50;

    //     Tile tile = this.farm.getTile(row, column);
    //     if (tile.getRock() == null) return false;
    //     tile.pickaxe();

    //     return true;
    // }

    public void displayInfo() {
        System.out.println(" [NAME] " + this.name);
        System.out.println(" [LEVEL] " + this.level);
        System.out.println(" [EXPERIENCE] " + this.exp);
        System.out.println(" [OBJECTCOINS] " + this.objectCoins);
        System.out.println(" [NUMBER OF TURNIP SEED] " + this.seedInventory[0]);
    }

    public int getCoins() {
        return objectCoins;
    }

    public Farm getFarm() {
        return farm;
    }
}