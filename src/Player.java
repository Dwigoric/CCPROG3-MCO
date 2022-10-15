public class Player {
    private float exp = 0;
    private int level = 0;
    private int typeLevel = 0;
    private int Objectcoins = 100;

    private String typeName[] = {"Farmer", "Registered Farmer",
                                 "Distinguished Farmer", "Legendary Farmer"};

    public Player() {}

    public void addExp(float amount) {
        this.exp+= amount;
    }

    public void upgradeType() {
        this.typeLevel++;
    }
}