import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    private Player player;
    private Farm farm;
    private final ArrayList<Seed> seedList = new ArrayList<Seed>();

    public Game() {}

    public void advanceDay() {
        for (int i = 0; i < this.farm.getRows(); i++) {
            for (int j = 0; j < this.farm.getColumns(); j++) {
                Crop crop = this.farm.getTile(i, j).getCrop();

                if (crop != null) {
                    crop.addAge();
                }
            }
        }
    }

    public void displayActions() {
        System.out.println();
        System.out.println(" -----------------------------------------------------------------------");
        System.out.println("  plow | plant | water | fertilize | harvest | sleep | register | exit");
        System.out.println(" -----------------------------------------------------------------------");
        System.out.print("  > ");
    }

    public void initialize() {
        
        /* MCO Phase 1 Specifications */
        /* Farm is 1x1 */
        this.farm = new Farm(1, 1); 

        /* Turnip is the only crop */
        Seed turnip = new Seed("Turnip", "Root Crop", 2, 1, 0,
                   1, 2, 5, 6, 5);
        this.seedList.add(turnip);

        this.player = new Player(this.farm);
    }

    public void start() {
        int day = 1;
        boolean isQuit = false;

        Scanner sc = new Scanner(System.in);

        while(isQuit == false) {
            player.displayInfo(day);
            farm.displayFarm();
            this.displayActions();

            switch(sc.nextLine()) {
                case "plow":
                    if(player.plow(0, 0) == true) {
                        System.out.println("  [MESSAGE] Tile plowed");
                        sc.nextLine();
                    } else {
                        System.out.println("  [MESSAGE] Tile is already plowed");
                        sc.nextLine();
                    }
                    
                    break;
                case "plant":
                    if(player.plant(0, 0, seedList.get(0)) == true) {
                        System.out.println("  [MESSAGE] Turnip planted");
                        sc.nextLine();
                    } else {
                        System.out.println("  [MESSAGE] Cant plant");
                        sc.nextLine();
                    }

                    break;
                case "water":
                    if(player.water(0, 0) == true) {
                        System.out.println("  [MESSAGE] Crop watered");
                        sc.nextLine();
                    } else {
                        System.out.println("  [MESSAGE] No plant to water");
                        sc.nextLine();
                    }

                    break;
                case "fertilize":
                    if(player.fertilize(0, 0) == true) {
                        System.out.println("  [MESSAGE] Crop fertilized");
                        sc.nextLine();
                    } else {
                        System.out.println("  [MESSAGE] No plant to fertilize");
                        sc.nextLine();
                    }
                
                    break;
                case "harvest":
                    if(player.harvest(0, 0) == true) {
                        sc.nextLine();
                    } else {
                        System.out.println("  [MESSAGE] Cant harvest");
                        sc.nextLine();
                    }
                    
                    break;
                case "sleep":
                    this.advanceDay();
                    day++;

                    int minCost = 0;
                    for (Seed seed : seedList) {
                        if (minCost < seed.getCost()) {
                            minCost = seed.getCost();
                        }
                    }

                    System.out.println("  [MESSAGE] You slept");
                    if (!this.farm.hasCrop() && this.player.getObjectCoins() < minCost) {
                        isQuit = true;
                        System.out.println("  [GAME OVER] You have no crops growing left after today, and can no longer buy new seeds.");
                    }
                    else if (this.farm.isAllWithered()) {
                        isQuit = true;
                        System.out.println("  [GAME OVER] All your farm plots are filled with withered crops.");
                    }
                    sc.nextLine();

                    break;
                case "register":
                    System.out.println("  [!] You are currently registered as a " + this.player.getType().getTypeName() + ". Are you sure you want to upgrade? \"Yes\" to confirm.");
                    System.out.print("  > ");

                    if (sc.nextLine().equalsIgnoreCase("yes") && this.player.upgradeFarmer() == true) {
                        System.out.println("  [MESSAGE] You have successfully registered as " + this.player.getType().getTypeName() + "!");
                    }
                    else {
                        System.out.println("  [MESSAGE] Upgrade failed. Please check your funds and current experience.");
                    }
                    sc.nextLine();

                    break;
                case "exit":
                    isQuit = true;
                    
                    break;
                default:
                    /* none */
                    break;
            }
        }

        System.out.print("  [MESSAGE] Program Terminated");
        sc.close();
    }
}
