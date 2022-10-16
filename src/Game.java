import java.util.Scanner;

public class Game {
    private Player player;

    public Game() {}

    public void start() {
        int flag = 1;
        Scanner sc = new Scanner(System.in);
        Farm farm = new Farm();
        
        System.out.print("[ENTER NAME] ");
        this.player = new Player(sc.nextLine(), farm);

        do {
            player.getFarm().displayFarm();
            player.displayInfo();

            System.out.println("\n ENTER LETTER IN BRACKETS");
            System.out.println(" [P]LOW  [B]UY A TURNIP [P]LANT SEED"); 
            System.out.println(" [W]ATER [F]ERTILIZE    [H]ARVEST [A]DVANCE DAY\n ");

            switch(sc.nextLine()) {
                case "P":
                case "p":
                    if(player.getFarm().getTile(0, 0).getState() == "unplowed") {
                        player.plowTile(0, 0);
                    } else {
                        System.out.println("TILE IS ALREADY PLOWED");
                    }
                    
                    break;
                case "B":
                case "b":
                    System.out.print("[NUMBER OF TURNIP] ");

                    break;
                case "end":
                    flag = 0;
                    break;
            }
        } while(flag != 0);

        sc.close();
    }
}
