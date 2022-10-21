import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    private Player player;
    private Farm farm;
    private final ArrayList<Seed> seedList = new ArrayList<Seed>();

    public Game() {}

    public void displayActions(int day) {
        System.out.println("");
        System.out.println(" ==========================================================");
        System.out.println(" Day " + day + " plow | plant | water | fertilize | harvest | sleep");
        System.out.println(" ==========================================================");
        System.out.print(" [] ");
    }

    public void initialize(int rows, int columns) {
        this.farm = new Farm(rows, columns);
        Seed turnip = new Seed("Turnip", "Root Crop", 2, 1, 0, 1, 2, 5, 6, 5);

        this.player = new Player(farm);
        this.seedList.add(turnip); // Turnip only in MCO 1
    }

    public void start() {
         int day = 1;
         boolean isQuit = false;
         Tile currTile = farm.getTile(0, 0);

        Scanner sc = new Scanner(System.in);

        while(isQuit == false) {
            player.displayInfo();
            farm.displayFarm();
            this.displayActions(day);

            switch(sc.nextLine()) {
                case "pw":
                    if(currTile.plow() == true) {
                        System.out.println(" Tile is plowed");
                        sc.nextLine();
                    } else {
                        System.out.println(" Tile is already plowed");
                        sc.nextLine();
                    }
                    
                    break;
                
                case "pt":
                    if(currTile.isPlowed() == true && currTile.getCrop() == null) {
                        currTile.plant(seedList.get(0)); // temp
                        player.deductCoins(seedList.get(0).getCost());

                        System.out.println(" Turnip planted");
                        sc.nextLine();
                    } else {
                        System.out.println(" Cant plant");
                        sc.nextLine();
                    }

                    break;
                case "wr":
                    if(currTile.getCrop() != null) {
                        currTile.getCrop().water();
                        System.out.println(" Crop watered");
                        sc.nextLine();
                    } else {
                        System.out.println(" No plant to water");
                        sc.nextLine();
                    }

                    break;
                case "fe":
                    if(currTile.getCrop() != null) {
                        currTile.getCrop().fertilize();
                        System.out.println(" Crop fertilized");
                        sc.nextLine();
                    } else {
                        System.out.println(" No plant to fertilize");
                        sc.nextLine();
                    }
                
                    break;
                case "ht":
                    if(currTile.getCrop() == null) {
                        System.out.println(" No plant to harvest");
                        sc.nextLine();
                    } else if(currTile.getCrop().isAlive() == false) {
                        System.out.println(" Plant died");
                        sc.nextLine();
                    } else if(currTile.getCrop().isHarvestReady() == true) {
                        player.addCoins(currTile.getCrop().getProduce() * currTile.getCrop().getSeed().getBaseSellingPrice());
                        player.addExperience(currTile.getCrop().getProduce() * currTile.getCrop().getSeed().getExpYield());
                        currTile.HarvestCrop();
                        
                        System.out.println("Plant harvested");
                        sc.nextLine();
                    }
                    
                    break;
                case "sp":
                    this.farm.advanceDay();
                    day++;

                    System.out.println("You slept");
                    sc.nextLine();
                    break;
                default:
                    break;
            }
        }

        System.out.print("Program Terminated");

        sc.close();
    }
}
